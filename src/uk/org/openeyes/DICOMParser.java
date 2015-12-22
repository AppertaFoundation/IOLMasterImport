/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.SpecificCharacterSet;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.TagUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import uk.org.openeyes.models.DicomFiles;

/**
 *
 * @author VEDELEKT
 */
public class DICOMParser {
    private PatientData Patient = new PatientData();
    
    private StudyData Study = new StudyData();
    
    private BiometryData Biometry = new BiometryData();
    
    private int LenseCount = -1;
    public boolean debug = true;
    
    private String CurrentSide = "R";
    private String LastSide = "";
    private String ACD = "";
    private String EyeStatus = "";
    
    private DatabaseFunctions database = new DatabaseFunctions();
    
    private SpecificCharacterSet CharacterSet = SpecificCharacterSet.DEFAULT;
    
    private DICOMLogger logger;
 
    public DICOMParser(boolean debugState, String configFile, DICOMLogger SystemLogger){
        this.logger = SystemLogger;
        this.debug = debugState;
                
        database.initSessionFactory(configFile, SystemLogger);
        debugMessage("Connection status: "+database.checkConnection());
    }
    
    public DatabaseFunctions getDatabase(){
        return this.database;
    }
    
    public StudyData getStudyData(){
        return this.Study;
    }
    
    public PatientData getPatientData(){
        return this.Patient;
    }
    
    public BiometryData getBiometryData(){
        return this.Biometry;
    }
     
    private String InvertSide(String Side){
        if(Side.equals("L")){
            return "R";
        }else{
            return "L";
        }
    }
    
    private void debugMessage(String message){
        if(this.debug){
            logger.addToRawOutput(message);
            //System.out.println(message);
        }
    }
    
    
    public DicomFiles searchDicomFile(String inputFile){
        File file = new File(inputFile);
        Criteria crit = database.session.createCriteria(DicomFiles.class);
        crit.add(Restrictions.eq("filename", inputFile));
        crit.add(Restrictions.eq("filesize", file.length()));
        crit.add(Restrictions.eq("filedate", new Date(file.lastModified())));
        
        if(! crit.list().isEmpty()){
            DicomFiles selectedFile = (DicomFiles) crit.list().get(0);
            return selectedFile;
        }else{
            DicomFiles newFile = new DicomFiles();
            newFile.setFilename(inputFile);
            newFile.setFilesize(file.length());
            newFile.setEntryDateTime(new Date());
            newFile.setProcessorId("JAVA_OE_IOLMaster");
            newFile.setFiledate(new Date(file.lastModified()));
            database.session.save(newFile);
            return newFile;
        }
        
    }
       
    public void parseDicomFile(String inputFile)  {
        
        Attributes attrs = new Attributes(false, 64);
        
        DicomInputStream dis = null;        
        try {
            dis = new DicomInputStream(new File(inputFile));
        } catch (IOException ex) {
            logger.systemExitWithLog(2, "Failed to open DICOM file, not a valid file or file not exists!", database);
            //System.exit(2);
        }
        
        dis.setDicomInputHandler(dis);
        try {
            attrs = dis.readDataset(-1, -1);
        } catch (IOException ex) {
            logger.systemExitWithLog(3, "Failed to read DICOM file, not a valid file or file not exists!", database);
        }
        
        readAttributes(attrs, "");
    }
    
    private void readAttributes(Attributes inputAttrs, String sequenceTag){
        int[] dcmTags;
        String StudyDate="";
        String StudyTime="";
        String IOLType = "";
                        
        dcmTags = inputAttrs.tags();
        
        // TODO: need to create an XML structure for this data extraction to make it more general!!!
        // Soultion 1: define the structure - we need: tag group, tag element, data type, object, data field (where to store and assign)
        // OR
        // Solution 2: create reader classes for all dicom types (biometryReader, visualFieldsReader, etc)
        
        for( int tag : dcmTags){
            if(inputAttrs.getVR(tag).toString().equals("SQ")){
                //debugMessage("Reading sequence "+tag);
                readSequence(inputAttrs.getSequence(tag), TagUtils.toHexString(TagUtils.elementNumber(tag)));
            }
            if( !inputAttrs.getValue(tag).toString().equals("")){
                //debugMessage(TagUtils.toHexString(TagUtils.groupNumber(tag))+"::"+TagUtils.toHexString(TagUtils.elementNumber(tag))+" - "+inputAttrs.getVR(tag)+"::"+inputAttrs.getValue(tag));
                // collecting patient data
                if( TagUtils.toHexString(TagUtils.groupNumber(tag)).equals("00000010")){
                    // patient name
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000010")){
                        Patient.setPatientName(VR.PN.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Patient's name: "+VR.PN.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT).toString().replace("^", " "));
                    }
                    // patient id
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000020")){
                        Patient.setPatientID(VR.LO.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Patient's ID: "+VR.LO.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }
                    // patient birth date
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000030")){
                        Patient.setPatientBirth(VR.DA.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Patient's birth date: "+VR.DA.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }
                    // patient gender
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000040")){
                        Patient.setPatientGender(VR.CS.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString().charAt(0));
                        //debugMessage("Patient's sex: "+VR.CS.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }
                    // patient comments
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00004000")){
                        Study.setComments(VR.LT.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Comments: "+VR.LT.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }

                }

                // collecting study data
                if( TagUtils.toHexString(TagUtils.groupNumber(tag)).equals("00000008")){
                    // character set
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000005")){
                        CharacterSet = SpecificCharacterSet.valueOf(VR.CS.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT).toString());
                    }
                    
                    // study date
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000020")){
                        //debugMessage("Study date: "+VR.DA.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                        StudyDate = VR.DA.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString();
                    }
                    // study time
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000030")){
                        //debugMessage("Study time: "+VR.TM.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                        StudyTime = VR.TM.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString();
                        if(!StudyDate.equals("") && !StudyTime.equals("")){
                            Study.setStudyDateTime(StudyDate + StudyTime);
                        }
                    }

                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000033")){
                        //debugMessage("<--------- Study content time: "+VR.TM.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        Study.setContentTime(VR.TM.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                    }
                    
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000023")){
                        //debugMessage("<--------- Study content time: "+VR.TM.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        Study.setContentDate(VR.TM.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                    }

                    // physician's name
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000090")){
                        Study.setPhysicianName(VR.PN.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Physician's name: "+VR.PN.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }
                    // institution name
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000080")){
                        Study.setInstituionName(VR.LO.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Institution name: "+VR.LO.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }
                    // station name
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00001010")){
                        Study.setStationName(VR.SH.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Station name: "+VR.SH.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }
                    
                    // device manufacturer
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000070")){
                        Study.setDeviceManufacturer(VR.LO.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Device manufacturer: "+VR.LO.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }
                    
                    // Device model name
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00001090")){
                        Study.setDeviceModel(VR.LO.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Device model name: "+VR.LO.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }

                }

                if( TagUtils.toHexString(TagUtils.groupNumber(tag)).equals("00000018")){
                    
                    // Device software version
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00001020")){
                        Study.setDeviceSoftwareVersion(VR.LO.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Device software version: "+VR.LO.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }
                }
                
                // collecting study data
                if( TagUtils.toHexString(TagUtils.groupNumber(tag)).equals("00000020")){
                   // study instance ID
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("0000000D")){
                        Study.setStudyInstanceID(VR.UI.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Study instance ID: "+VR.UI.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    } 
                    // study ID
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000010")){
                        Study.setStudyID(VR.SH.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        //debugMessage("Study ID: "+VR.SH.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    } 
                }

                // collecting measurement data
                if( TagUtils.toHexString(TagUtils.groupNumber(tag)).equals("0000771B")){
                        // eye side: R or L
                        if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*08")){
                            CurrentSide = VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString();
                            this.LenseCount = -1;
                            //debugMessage("We are in seqence: "+sequenceTag.toString());
                            // we need to do this, because in the measurement sequence the IOL side is the last element!!!
                            if( sequenceTag.matches("(?i).*01")){
                                Biometry.setSideData(CurrentSide);
                                //debugMessage("ACD: "+ACD+" Side: "+CurrentSide);
                                if(!ACD.equals("")){
                                    Biometry.setBiometryValue("ACD", CurrentSide, ACD);
                                }
                                if(!EyeStatus.equals("")){
                                    Biometry.setBiometryValue("EyeStatus", CurrentSide, EyeStatus);
                                }
                            }
                            //debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, null).toString());
                        }
                        
                        // IOL type (can be LENSES or FORMULA !!!!)
                        if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*04")){
                            //debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString());
                            IOLType = VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString();
                            // capitalize the string because we will use it as a function name later!!!
                            IOLType = IOLType.charAt(0)+IOLType.substring(1).toLowerCase();
                            //debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, null).toString());
                        }
                        
                        if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*06")){
                            //debugMessage(VR.LO.toStrings(inputAttrs.getValue(tag), false, null).toString());
                        }
                        
                        // surgeon name (top)
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*2C")){
                            Study.setSurgeonName(VR.PN.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                            //debugMessage("Physician's name: "+VR.PN.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                        }
                        
                        // formula name (top)
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*09") && (sequenceTag.matches("(?i).*36") || sequenceTag.matches("(?i).*37"))){
                            Study.setFormulaName(VR.PN.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                            //debugMessage("Sequence: "+sequenceTag.toString());                                   
                            //debugMessage("<------------- Formula name: "+VR.PN.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                        }
                      
                        // lense name (top)
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*0A")){
                            Study.setLenseName(VR.PN.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                            //debugMessage("<------------ Lense name: "+VR.PN.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                        }

                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*25")){
                            //debugMessage(VR.IS.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString()+"<----- Status / Sequence: ----->"+sequenceTag.toString());
                            EyeStatus = VR.IS.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString();
                            //Biometry.setBiometryValue("EyeStatus", CurrentSide, VR.IS.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                        }

                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*40")){
                           //debugMessage(String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                           Biometry.setBiometryValue("RefractionSphere", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*41")){
                           //debugMessage(String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                           Biometry.setBiometryValue("RefractionDelta", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*42")){
                            //debugMessage(String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                           Biometry.setBiometryValue("RefractionAxis", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*4F")){
                            //debugMessage(String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                            Biometry.setBiometryValue("DeltaK", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*43")){
                           Biometry.setBiometryValue("AL", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*44")){
                           Biometry.setBiometryValue("SNR", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }

                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*0C") && sequenceTag.matches("(?i).*31")){
                            Biometry.setBiometryValue("SNRMin", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));

                        }
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*4A")){
                           //debugMessage(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0));
                           Biometry.setBiometryValue("K1", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*4D")){
                           Biometry.setBiometryValue("K2", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*4B")){
                           Biometry.setBiometryValue("AxisK1", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                           Biometry.setBiometryValue("DeltaKAxis", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*4E")){
                           Biometry.setBiometryValue("AxisK2", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        
                        
                        if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*04")
                                ||TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*5D")){
                            //debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, SpecificCharacterSet.DEFAULT).toString());
                        }

                        // target refraction
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*29")){
                           //debugMessage(String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0))+" =========="+CurrentSide+"============ Seq: "+sequenceTag);
                           Biometry.setBiometryValue("TargetRef", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        // K values modified
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*45")){
                           //debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString()+" ++++++++++"+CurrentSide+"++++++++++ Seq: "+sequenceTag);
                           Biometry.setBiometryValue("isALModified", CurrentSide, VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString());
                        }
                        
                        // K values modified
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*46")){
                           //debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString()+" ++++++++++"+CurrentSide+"++++++++++ Seq: "+sequenceTag);
                           Biometry.setBiometryValue("isKModified", CurrentSide, VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString());
                        }
                        
                        // ACD values modified
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*48")){
                           debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString()+" ------------------------"+CurrentSide+"--------------- Seq: "+sequenceTag);
                           Biometry.setBiometryValue("isACDModified", CurrentSide, VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString());
                        }
                        
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*26") && sequenceTag.matches("(?i).*02")){
                           // the sequence *02 is inside a *01 sequence, and the IOL_laterity is stored at the end of the sequence, so we need to store this value, and handle it we we leave the *01 sequence
                            //debugMessage(String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0))+"<---- ACD / sequence: ---->"+sequenceTag.toString()+" Current side: "+CurrentSide);
                            ACD = String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0));
                            //Biometry.setBiometryValue("ACD", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        // we are inside the measurement sequence
                        if( sequenceTag.matches("(?i).*05")){
                            // IOL Power 
                            if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*28")){
                                if(this.LenseCount > -1){
                                    Biometry.setBiometryValueNum("LenseREF", "U", String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)), this.LenseCount);
                                }
                            }

                            // Predicted refraction
                            if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*2A")){
                                if(this.LenseCount > -1){
                                    Biometry.setBiometryValueNum("LenseIOL", "U", String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)), this.LenseCount);
                                }
                            }
                            
                            // eye side: R or L
                            //if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*08")){
                            //    CurrentSide = VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString();
                            //    Biometry.setSideData(CurrentSide);
                            //    debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, null).toString());
                            //}
                        }
                        
                        // we are inside a lens sequence
                        if( sequenceTag.matches("(?i).*03")){
                            // TODO: get a proper solution for sides here!!!
                            // Name of lenses

                            if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*06")){
                                if(this.LenseCount > -1){
                                    //debugMessage(VR.LO.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString());
                                    Biometry.setBiometryValueNum(IOLType+"Name", "U", VR.LO.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString() , this.LenseCount);
                                }
                            }

                            // A constant
                            if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*07")){
                                if(this.LenseCount > -1){
                                    //debugMessage(String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                                    Biometry.setBiometryValueNum("LenseAConst", "U", String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)), this.LenseCount);
                                }
                            } 
                            
                            // Emmetropia IOL
                            if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*2B")){
                                if(this.LenseCount > -1){
                                    Biometry.setBiometryValueNum("LenseEmmetropia", "U", String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)), this.LenseCount);
                                }
                            } 
                           
                        }
                        
                        // TODO: remove this part
                        // this is just for debug!!!
                        if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*0B") 
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*0C")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*0D")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*43")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*44")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*22")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*12")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*11")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*13")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*20")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*49")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*4A")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*4B")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*4D")
                                || TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*21")){
                            //debugMessage(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0));
                        }

                        
                }
            }
        }
    }   
    
    private void readSequence(Sequence inputSeq, String sequenceTag) {
        Iterator<Attributes> sequenceIterator;
        sequenceIterator = inputSeq.iterator();
    
        //debugMessage("SQ Tag: "+sequenceTag);
        if( sequenceTag.matches("(?i).*05")){
            this.LenseCount++;
            this.Biometry.setBiometryValue("Lenses", "U", "N/A");
        }

        while(sequenceIterator.hasNext()){
            Attributes sequenceData = sequenceIterator.next();
            //debugMessage(sequenceData.toString());
            this.readAttributes(sequenceData, sequenceTag);

        }
       
    }
    
    public boolean processParsedData() throws ParseException{
        // first we try to connect to the database
        // if this connection fails we suggest to check the hibernate.conf.xml file
        if(debug){
            debugMessage(Patient.printPatientData());
            debugMessage(Study.printStudyData());
            debugMessage(Biometry.printBiometryData());
        }
        
        database.searchPatient(Patient.getPatientID(), Patient.getPatientGender(), Patient.getPatientBirth());
        BiometryFunctions BiometryProcessor = new BiometryFunctions(logger);
        if(database.getSelectedPatient() != null){
            BiometryProcessor.processBiometryEvent(Study,  Biometry, database);
        }else{
            // search for patient data has been failed - need to print and log!!
            logger.getLogger().setPatientNumber(Patient.getPatientID());
            logger.systemExitWithLog(4, "Cannot find patient data, file processing failed! \nSearched for: \n"+Patient.getDetails(), database);
            return false;
        }


        
        logger.getLogger().setStatus("success");
        logger.saveLogEntry(database.session);
        database.session.flush();
        database.transaction.commit();
        database.session.close();
        
        database.closeSessionFactory();
        return true;
    }
}
