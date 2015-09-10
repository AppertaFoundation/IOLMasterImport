
package uk.org.openeyes;

import java.io.File;
import java.io.IOException;
import java.io.Console;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import java.util.Iterator;
import org.apache.commons.cli.HelpFormatter;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.SpecificCharacterSet;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.TagUtils;

/**
 * @author Tamas Vedelek <vetusko@gmail.com>
 */
public class OE_IOLMasterImport  {

    /** default number of characters per line */
    private static final int DEFAULT_WIDTH = 78;

    private int width = DEFAULT_WIDTH;
    
    private PatientData Patient = new PatientData();
    
    private StudyData Study = new StudyData();
    
    private BiometryData Biometry = new BiometryData();
    
    private int LenseCount = -1;
    private boolean debug = true;
    
    private String CurrentSide = "R";
    private String LastSide = "";
    
    private DatabaseFunctions database;
    
    private SpecificCharacterSet CharacterSet = SpecificCharacterSet.DEFAULT;
    
    private String InvertSide(String Side){
        if(Side.equals("L")){
            return "R";
        }else{
            return "L";
        }
    }
    
    private void debugMessage(String message){
        if(this.debug){
            System.out.println(message);
        }
    }
    
    public final int getWidth() {
        return width;
    }

    public final void setWidth(int width) {
        if (width < 40)
            throw new IllegalArgumentException();
        this.width = width;
    }

    public void parse(DicomInputStream dis) throws IOException {
        Attributes attrs = new Attributes(false, 64);
        
        
        /* Original code */
        dis.setDicomInputHandler(dis);
        attrs = dis.readDataset(-1, -1);
        //                */
        readAttributes(attrs, "");
       
    }
    
   
    protected void readAttributes(Attributes inputAttrs, String sequenceTag){
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
                debugMessage(TagUtils.toHexString(TagUtils.groupNumber(tag))+"::"+TagUtils.toHexString(TagUtils.elementNumber(tag))+" - "+inputAttrs.getVR(tag)+"::"+inputAttrs.getValue(tag));
                // collecting patient data
                //debugMessage("--== Patient data ==--");
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
                    // patient sex
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00000040")){
                        Patient.setPatientGender(VR.CS.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString().charAt(0));
                        //debugMessage("Patient's sex: "+VR.CS.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                    }
                    // patient comments
                    if(TagUtils.toHexString(TagUtils.elementNumber(tag)).equals("00004000")){
                        Patient.setPatientComments(VR.LT.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
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
                            debugMessage("We are in seqence: "+sequenceTag.toString());
                            // we need to do this, because in the measurement sequence the IOL side is the last element!!!
                            if( sequenceTag.matches("(?i).*01")){
                                Biometry.setSideData(CurrentSide);
                            }
                            debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, null).toString());
                        }
                        
                        // IOL type (can be LENSES or FORMULA !!!!)
                        if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*04")){
                            debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString());
                            IOLType = VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString();
                            // capitalize the string because we will use it as a function name later!!!
                            IOLType = IOLType.charAt(0)+IOLType.substring(1).toLowerCase();
                           //debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, null));
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
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*09")){
                            Study.setFormulaName(VR.PN.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                            //debugMessage("Physician's name: "+VR.PN.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                        }
                        
                        // lense name (top)
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*0A")){
                            Study.setLenseName(VR.PN.toStrings(inputAttrs.getValue(tag), true, CharacterSet).toString());
                            //debugMessage("Physician's name: "+VR.PN.toStrings(inputAttrs.getValue(tag), true, SpecificCharacterSet.DEFAULT));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*43")){
                           Biometry.setBiometryValue("AL", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
                        }
                        
                        if(TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*44")){
                           Biometry.setBiometryValue("SNR", CurrentSide, String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
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
                        }
                        
                        
                        if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*04")
                                ||TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*5D")){
                            //debugMessage(VR.CS.toStrings(inputAttrs.getValue(tag), false, SpecificCharacterSet.DEFAULT).toString());
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
                                    debugMessage(VR.LO.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString());
                                    Biometry.setBiometryValueNum(IOLType+"Name", "U", VR.LO.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString() , this.LenseCount);
                                }
                            }

                            // A constant
                            if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*07")){
                                if(this.LenseCount > -1){
                                    debugMessage(String.valueOf(VR.FD.toDouble(inputAttrs.getValue(tag), false, 0, 0)));
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
    
    protected void readSequence(Sequence inputSeq, String sequenceTag) {
        Iterator<Attributes> sequenceIterator;
        sequenceIterator = inputSeq.iterator();
    
        debugMessage("SQ Tag: "+sequenceTag);
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
        
    /**
     *
     * @param options
     */
    private static void printHelp(Options options) {

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("annotator-app [options], where [options] can be any of:", options);
        System.exit(0);
    } 

    // main
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        	
        if (args.length == 0) {
            System.out.println("Error, please specify at least a filename with -f, or try -h");
            System.exit(1);
        }
	
        OE_IOLMasterImport main = new OE_IOLMasterImport();
        String inputFile = "";
        String configFile = "";

        main.database = new DatabaseFunctions();

        Options options = new Options();
        Option optionHelp = new Option("h", "help", false,
                            "Display help.");
        Option optionFileName = new Option("f", "file", true,
                            "Specify a filename to parse.");
        Option optionConfigFile = new Option("c", "config", true,
                            "Specify a Hibernate config file. The default can be found in resources/hibernate.cfg.xml");
        options.addOption(optionHelp);
        options.addOption(optionFileName);
        options.addOption(optionConfigFile);

        CommandLineParser parser = new PosixParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help") || cmd.hasOption('h')) {
                main.printHelp(options);
            }
            if (cmd.hasOption("f") || cmd.hasOption("file")) {
                inputFile = cmd.getOptionValue("file");
            }
            if (cmd.hasOption("c") || cmd.hasOption("config")) {
                configFile = cmd.getOptionValue("config");
            }

            main.database.initSessionFactory(configFile);
            main.debugMessage("Connection status: "+main.database.checkConnection());

            if(inputFile.equals("")){
                inputFile = "test/data/input_test.dcm";    // original IOLMaster file with multi lense
                //String fname = "test/data/new_input_test.dcm";  // new IOLMaster file with multi formula
                //String fname = "test/data/compressed.dcm";    // compressed original file
                //String fname = "test/data/vfa_test.dcm";      // visual fields dicom file
            }

            DicomInputStream dis =
                        new DicomInputStream(new File(inputFile));
            main.parse(dis);
            main.Patient.printPatientData();
            main.database.searchPatient(main.Patient.getPatientID(), main.Patient.getPatientGender(), main.Patient.getPatientBirth());
            if(main.database.getSelectedPatient() != null){
                main.database.selectActiveEpisode();
                if(main.database.getSelectedEpisode() != null){
                    // we have a selected episode, we can create an event there
                    main.database.createBiometryEvent(main.Study.getStudyDateTime(), main.Study, main.Biometry, true);

                }else{
                    // we need to create data without episode_id!!!
                    main.database.createBiometryEvent(main.Study.getStudyDateTime(), main.Study, main.Biometry, false);

                }
            }
            main.Study.printStudyData();
            main.Biometry.printBiometryData();
            main.database.closeSessionFactory();
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            System.exit(2);
        }
    }


}