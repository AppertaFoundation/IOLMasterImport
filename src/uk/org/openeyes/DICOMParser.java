/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
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
    private final BiometrySide BiometryLeft = new BiometrySide();
    private final BiometrySide BiometryRight = new BiometrySide();

    
    private int LenseCount = -1;
    public boolean debug = true;
    private String APIconfigFile = "";
    
    private String CurrentSide = "R";
    private String LastSide = "";
    private String ACD = "";
    private String EyeStatus = "";

    
    private DatabaseFunctions database = new DatabaseFunctions();
    
    private SpecificCharacterSet CharacterSet = SpecificCharacterSet.DEFAULT;
    
    private DICOMLogger logger;
 
    public DICOMParser(boolean debugState, String configFile, DICOMLogger SystemLogger, String APIconfigFile){
        this.logger = SystemLogger;
        this.debug = debugState;
        this.APIconfigFile = APIconfigFile;
                
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
        
        Attributes attrs = new Attributes();
        
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
        
        collectPatientData(attrs);
        collectStudyData(attrs);
        
        collectBiometryData(attrs);
        
        //readAttributes(attrs, "");
    }
    
   
    private Integer getTagInteger(String tag){
        return Integer.decode("0x"+tag);
    }
    
    private String getSideFromSequence(Sequence inputSequence){
        int[] sequenceTags;
        if(!inputSequence.isEmpty()){
            for(int i = 0; i<inputSequence.size(); i++){
                Attributes memberAttr = (Attributes) inputSequence.get(i);
                return getSideFromAttributes(memberAttr);
            }
        }
        return "";
    }
    
    private String getSideFromAttributes(Attributes inputAttrs){
        int[] sequenceTags;
        sequenceTags = inputAttrs.tags();
        for( int tag : sequenceTags){
            if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*08")){
                return VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString();
            }
        }
        return "";
    }
    
    private Double getDoubleValueFromSequence(String hexTagSequence, String hexTagValue, String side, Attributes Attrs){
        Sequence Seq = Attrs.getSequence(getTagInteger(hexTagSequence));
        for(int ks=0; ks<Seq.size();ks++){
            Attributes AttrData = (Attributes) Seq.get(ks);
            CurrentSide = getSideFromAttributes(AttrData);
            if(CurrentSide == null || CurrentSide.equals("")){
                CurrentSide = getSideFromAttributes(Attrs);
            }
            if(CurrentSide.equals(side)){
                return VR.FD.toDouble(AttrData.getValue(getTagInteger(hexTagValue)), false, 0, 0);
            }
        }
        return 0.0;
    }
    
    private String getStringValueFromSequence(String hexTagSequence, String hexTagValue, String side, Attributes Attrs){
        Sequence Seq = Attrs.getSequence(getTagInteger(hexTagSequence));
        for(int ks=0; ks<Seq.size();ks++){
            Attributes AttrData = (Attributes) Seq.get(ks);
            if(!side.equals("")){
                CurrentSide = getSideFromAttributes(AttrData);
                if(CurrentSide == null || CurrentSide.equals("")){
                    CurrentSide = getSideFromAttributes(Attrs);
                }
                if(CurrentSide.equals(side)){                
                    return VR.PN.toStrings(AttrData.getValue(getTagInteger(hexTagValue)), true, CharacterSet).toString();
                }
            }else{
                return VR.PN.toStrings(AttrData.getValue(getTagInteger(hexTagValue)), true, CharacterSet).toString();
            }
        }
        return "";
    }

    
    private void setMinSnrForSides(Attributes Attrs){
        Sequence basicMeasurement = Attrs.getSequence(getTagInteger("771B1030"));
        for(int bm =0; bm<basicMeasurement.size(); bm++){
            Attributes basicMeasurementData = (Attributes) basicMeasurement.get(bm);
            // AL mean value: 771B1043
            // SNR mean value: 771B1044
            CurrentSide = getSideFromAttributes(basicMeasurementData);
               
            // Sequence of single axial length measurements, we need to extract minSNR from here!
            Sequence ALSeq = basicMeasurementData.getSequence(getTagInteger("771B1031"));
            for(int as=0; as<ALSeq.size();as++){
                Attributes ALData = (Attributes) ALSeq.get(as);
                //debugMessage(CurrentSide+" Measured SNR (SNRMin): "+VR.FD.toDouble(ALData.getValue(getTagInteger("771B100C")), false, 0, 0));
                if(CurrentSide.equals("L")){
                    BiometryLeft.setSNRMin(VR.FD.toDouble(ALData.getValue(getTagInteger("771B100C")), false, 0, 0));
                }else{
                    BiometryRight.setSNRMin(VR.FD.toDouble(ALData.getValue(getTagInteger("771B100C")), false, 0, 0));
                }
            }
        }
    }
    
    private void collectPatientData(Attributes Attrs){
        CharacterSet = SpecificCharacterSet.valueOf(VR.CS.toStrings(Attrs.getValue(getTagInteger("00080005")), true, SpecificCharacterSet.DEFAULT).toString());
        Patient.setPatientName(Attrs.getString(getTagInteger("00100010")));
        Patient.setPatientID(Attrs.getString(getTagInteger("00100020")));
        Patient.setPatientBirth(Attrs.getString(getTagInteger("00100030")));
        Patient.setPatientGender(Attrs.getString(getTagInteger("00100040")).charAt(0));
    }
    
    private void collectStudyData(Attributes Attrs){
        Study.setComments(Attrs.getString(getTagInteger("00104000")));
        Study.setStudyDateTime(Attrs.getString(getTagInteger("00080021")) + Attrs.getString(getTagInteger("00080031")));
        Study.setContentTime(Attrs.getString(getTagInteger("00080033")));
        Study.setContentDate(Attrs.getString(getTagInteger("00080023")));
        Study.setPhysicianName(Attrs.getString(getTagInteger("00080090")));
        Study.setInstituionName(Attrs.getString(getTagInteger("00080080")));
        Study.setStationName(Attrs.getString(getTagInteger("00081010")));
        Study.setDeviceManufacturer(Attrs.getString(getTagInteger("00080070")));
        Study.setDeviceModel(Attrs.getString(getTagInteger("00081090")));
        Study.setDeviceSoftwareVersion(Attrs.getString(getTagInteger("00181020")));
        Study.setStudyInstanceID(Attrs.getString(getTagInteger("0020000D")));
        Study.setSeriesInstanceID(Attrs.getString(getTagInteger("0020000E")));
        Study.setStudyID(Attrs.getString(getTagInteger("00200010")));
        if(Attrs.contains(getTagInteger("771B102C"))){
            Study.setSurgeonName(VR.PN.toStrings(Attrs.getValue(getTagInteger("771B102C")), true, CharacterSet).toString());
        }
    }
    
    private void collectMeasuredValues(Attributes Attrs){
        setMinSnrForSides(Attrs);
        
        BiometryLeft.setAL(getDoubleValueFromSequence("771B1030","771B1043","L",Attrs));
        BiometryLeft.setK1(getDoubleValueFromSequence("771B1032","771B104A","L",Attrs));
        BiometryLeft.setK2(getDoubleValueFromSequence("771B1032","771B104D","L",Attrs));
        BiometryLeft.setAxisK2(getDoubleValueFromSequence("771B1032","771B104E","L",Attrs));
        
        BiometryRight.setAL(getDoubleValueFromSequence("771B1030","771B1043","R",Attrs));
        BiometryRight.setK1(getDoubleValueFromSequence("771B1032","771B104A","R",Attrs));
        BiometryRight.setK2(getDoubleValueFromSequence("771B1032","771B104D","R",Attrs));
        BiometryRight.setAxisK2(getDoubleValueFromSequence("771B1032","771B104E","R",Attrs));

    }
    
    private void collectCommonMeasuredValues(Attributes Attrs){
        if(Attrs.contains(getTagInteger("771B1032"))){
            BiometryLeft.setAxisK1(getDoubleValueFromSequence("771B1032","771B104B","L",Attrs));
            BiometryLeft.setDeltaKAxis(getDoubleValueFromSequence("771B1032","771B104B","L",Attrs));
            BiometryLeft.setDeltaK(getDoubleValueFromSequence("771B1032","771B104F","L",Attrs));

            BiometryRight.setAxisK1(getDoubleValueFromSequence("771B1032","771B104B","R",Attrs));
            BiometryRight.setDeltaKAxis(getDoubleValueFromSequence("771B1032","771B104B","R",Attrs));
            BiometryRight.setDeltaK(getDoubleValueFromSequence("771B1032","771B104F","R",Attrs));
        }
        // check for SNR
        if(Attrs.contains(getTagInteger("771B1030"))){
            BiometryLeft.setSNR(getDoubleValueFromSequence("771B1030","771B1044","L",Attrs));
            BiometryRight.setSNR(getDoubleValueFromSequence("771B1030","771B1044","R",Attrs));                
        }
    }
    
    private void collectMeasuredValuesFromFormulaSeq(Attributes Attrs, String side){
        BiometrySide sideData;
        if(side.equals("L")){
            sideData = BiometryLeft;
        }else{
            sideData = BiometryRight;
        }
        
        sideData.setAL(getDoubleValueFromSequence("771B1002","771B100B",side,Attrs));
        sideData.setisALModified(getStringValueFromSequence("771B1002","771B1045",side,Attrs));
        // TODO: SNR exists only in seq 1030!!!
        sideData.setK1(getDoubleValueFromSequence("771B1002","771B1020",side,Attrs));
        sideData.setisKModified(getStringValueFromSequence("771B1002","771B1046",side,Attrs));
        sideData.setK2(getDoubleValueFromSequence("771B1002","771B1021",side,Attrs));
        sideData.setAxisK2(getDoubleValueFromSequence("771B1002","771B1013",side,Attrs));
        sideData.setACD(getDoubleValueFromSequence("771B1002","771B1026",side,Attrs));
        sideData.setisACDModified(getStringValueFromSequence("771B1002","771B1048",side,Attrs));
        sideData.setTargetRef(getDoubleValueFromSequence("771B1002","771B1029",side,Attrs));
        sideData.setRefractionSphere(getDoubleValueFromSequence("771B1002","771B1040",side,Attrs));
        sideData.setRefractionDelta(getDoubleValueFromSequence("771B1002","771B1041",side,Attrs));
        sideData.setRefractionAxis(getDoubleValueFromSequence("771B1002","771B1042",side,Attrs));
        sideData.setEyeStatus(getStringValueFromSequence("771B1002","771B1025",side,Attrs));

    }
    
    private String selectSequenceTag(Attributes Attrs){
        String sequenceTag = "";
        if(Attrs.contains(getTagInteger("771B1001"))){
            sequenceTag = "771B1001";
        }else if(Attrs.contains(getTagInteger("771B1036"))){
            sequenceTag = "771B1036";
        }else if(Attrs.contains(getTagInteger("771B1037"))){
            sequenceTag = "771B1037";
        }
        return sequenceTag;
    }
    
    private void collectMeasuredValuesFromCalculation(Attributes Attrs){
        // the sequence structure is different, with different TAG numbers!!!
        String sequenceTag = selectSequenceTag(Attrs);
        
        Sequence CalcSeq = Attrs.getSequence(getTagInteger(sequenceTag));
        if(CalcSeq != null && !CalcSeq.isEmpty()){
            if(sequenceTag.equals("771B1001")){
                for(int cs = 0; cs < CalcSeq.size(); cs++ ){
                    collectMeasuredValuesFromFormulaSeq(CalcSeq.get(cs), getSideFromAttributes(CalcSeq.get(cs)));
                }
            }else{
                Attributes CalcAttrs = (Attributes) CalcSeq.get(0);
                Sequence FormulaSeq = CalcAttrs.getSequence(getTagInteger("771B1001"));
                if(FormulaSeq != null && !FormulaSeq.isEmpty()){
                    for(int fs = 0; fs < FormulaSeq.size(); fs++ ){
                        collectMeasuredValuesFromFormulaSeq(FormulaSeq.get(fs), getSideFromAttributes(FormulaSeq.get(fs)));
                    }
                }
            }
        }
    }
    
    
    private void collectCalculationValuesFromSeq(Attributes Attrs, String side, String inFormulaName, String inLensName){
        String FormulaName;
        String LensName;
        BiometrySide sideData;
        if(side.equals("L")){
            sideData = BiometryLeft;
        }else{
            sideData = BiometryRight;
        }
        //debugMessage(":: "+side+"::"+inFormulaName+"::"+inLensName);
        CurrentSide = getSideFromAttributes(Attrs);
        if(side.equals(CurrentSide)){
            Sequence CalcSeq = Attrs.getSequence(getTagInteger("771B1003"));
            if(CalcSeq != null && !CalcSeq.isEmpty()){
                for(int cs=0; cs<CalcSeq.size(); cs++){
                    Attributes CalcAttrs = CalcSeq.get(cs);
                    if(inFormulaName.equals("")){
                        FormulaName = VR.PN.toStrings(CalcAttrs.getValue(getTagInteger("771B1006")), true, CharacterSet).toString();
                        LensName=inLensName;
                    }else{
                        LensName = VR.PN.toStrings(CalcAttrs.getValue(getTagInteger("771B1006")), true, CharacterSet).toString();
                        FormulaName = inFormulaName;
                    }
                    // we add new data set
                    sideData.addCalculations();
                    //debugMessage("Index: "+sideData.getMeasurementsIndex());
                    sideData.setFormulaName(FormulaName, sideData.getMeasurementsIndex());
                    sideData.setLensesName(LensName, sideData.getMeasurementsIndex());
                    sideData.setLenseEmmetropia(VR.FD.toDouble(CalcAttrs.getValue(getTagInteger("771B102B")), false, 0, 0), sideData.getMeasurementsIndex());
                    sideData.setLenseAConst(VR.FD.toDouble(CalcAttrs.getValue(getTagInteger("771B1007")), false, 0, 0), sideData.getMeasurementsIndex());
                    Sequence IOLCalcSeq = CalcAttrs.getSequence(getTagInteger("771B1005"));
                    if(IOLCalcSeq != null && !IOLCalcSeq.isEmpty()){
                        for(int iols=0; iols<IOLCalcSeq.size(); iols++){
                            Attributes IOLCalcAttrs = IOLCalcSeq.get(iols);
                            sideData.setLenseIOL(VR.FD.toDouble(IOLCalcAttrs.getValue(getTagInteger("771B102A")), false, 0, 0), sideData.getMeasurementsIndex());
                            sideData.setLenseREF(VR.FD.toDouble(IOLCalcAttrs.getValue(getTagInteger("771B1028")), false, 0, 0), sideData.getMeasurementsIndex());
                        }
                    }
                }
            }
        }
        
    }
    
    private void collectCalculationValuesSequenceSide(Attributes Attrs, String side, String sequenceTag){
        String LensName = "";
        String FormulaName = "";

        if(!sequenceTag.equals("771B1001")){
            // single formula, multi lense
            // TODO similar solution required as for the side!!!
            Study.setSurgeonName(getStringValueFromSequence(sequenceTag,"771B102C","",Attrs));
            Sequence innerSeq = Attrs.getSequence(getTagInteger(sequenceTag));
            if(innerSeq != null && !innerSeq.isEmpty()){
                for(int is=0; is<innerSeq.size();is++){
                    Attributes innerAttrs = innerSeq.get(is);
                    //dumpDCMStructure(innerAttrs);
                    FormulaName = VR.PN.toStrings(innerAttrs.getValue(getTagInteger("771B1009")), true, CharacterSet).toString();
                    Sequence CalcSeq = innerAttrs.getSequence(getTagInteger("771B1001"));
                    if(CalcSeq != null && !CalcSeq.isEmpty()){
                        for(int cs=0; cs<CalcSeq.size(); cs++){
                            //dumpDCMStructure(CalcSeq.get(cs));
                            collectCalculationValuesFromSeq(CalcSeq.get(cs), side, FormulaName, LensName);
                        }
                    }   
                }
            }
        }else{
            // single lens multi formula
            LensName = VR.PN.toStrings(Attrs.getValue(getTagInteger("771B100A")), true, CharacterSet).toString();
            Sequence CalcSeq = Attrs.getSequence(getTagInteger("771B1001"));
            if(CalcSeq != null && !CalcSeq.isEmpty()){
                for(int cs=0; cs<CalcSeq.size(); cs++){
                    //dumpDCMStructure(CalcSeq.get(cs));
                    collectCalculationValuesFromSeq(CalcSeq.get(cs), side, FormulaName, LensName);
                }
            }
        }


    }

    private void collectCalculationValues(Attributes Attrs){
        
        if(Attrs.contains(getTagInteger("771B1036"))){
            debugMessage("Collecting data from 771B1036");
            collectCalculationValuesSequenceSide(Attrs, "L", "771B1036");
            collectCalculationValuesSequenceSide(Attrs, "R", "771B1036");
        }
        if(Attrs.contains(getTagInteger("771B1037"))){
            debugMessage("Collecting data from 771B1037");
            collectCalculationValuesSequenceSide(Attrs, "L", "771B1037");
            collectCalculationValuesSequenceSide(Attrs, "R", "771B1037");            
        }
        if(Attrs.contains(getTagInteger("771B1001"))){
            debugMessage("Collecting data from 771B1001");
            collectCalculationValuesSequenceSide(Attrs, "L", "771B1001");
            collectCalculationValuesSequenceSide(Attrs, "R", "771B1001");
        }
    }
    
    private void dumpDCMStructure(Attributes Attrs){
        int[] biometryTags = Attrs.tags();
        for( int tag : biometryTags){
            Integer level = Attrs.getLevel();
            String indent = "";
            for(int i=0;i<level;i++){
                indent += ">";
            }
            debugMessage(indent+" "+TagUtils.toHexString(tag)+" :: "+tag);

            if(Attrs.getVR(tag).toString().equals("SQ")){
                Sequence seq = Attrs.getSequence(tag);
                if(seq != null && !seq.isEmpty()){
                    for(int s=0; s<seq.size();s++){
                        dumpDCMStructure(seq.get(s));
                    }
                }
            }
        }
    }
    
    private void collectBiometryData(Attributes Attrs){
        
        /* ---- IOLMaster 500 SQ tags ---
        *  IOL_Measured_Values sequence: 771Bxx30 (first page)
        *       >> Sequence of single axial length measurements: 771Bxx31
        *  Sequence of keratometry values:  771Bxx32  >> Sequence of single keratometry measurements 771Bxx33
        *  Sequence of anterior chamber depth values: 771Bxx34
        *  Sequence of white-to-white values measured for one eye: 771Bxx35 >> Sequence of single white-to-white measurements: 771Bxx3B
        *  Sequence of standard formula IOL calculations for 4 different IOL types with a sequence of 7 calculations each, may contain up to 6 items: 771Bxx36
        *  Sequence of sandard formula IOL calculations for one eye: 771Bxx01 >> Container of measurement values used for calculation: 771Bxx02 
        *                                                         + >> Sequence of standard formula calculation results for 4 different IOL types: 771Bxx03
        *                                                                       >> Sequence of IOL calculation results for IOL as pair of lens power and residual refraction: 771Bxx05
        */
        
        // data priority: if we have calculation data we should extract all values from there
        //dumpDCMStructure(Attrs);
        
        if(Attrs.contains(getTagInteger("771B1032"))){
            // Axis K1, DeltaK and SNR values
            collectCommonMeasuredValues(Attrs);
        }
        
        if(Attrs.contains(getTagInteger("771B1036")) || Attrs.contains(getTagInteger("771B1037")) || Attrs.contains(getTagInteger("771B1001"))){
            debugMessage("Calculation sequence exists, extracting values");
            if(Attrs.contains(getTagInteger("771B1030"))){
                setMinSnrForSides(Attrs);
            }
            collectMeasuredValuesFromCalculation(Attrs);
            collectCalculationValues(Attrs);
        } else if(Attrs.contains(getTagInteger("771B1030"))){
            debugMessage("IOL_Measured_Values sequence exists, extracting values");
            collectMeasuredValues(Attrs);
        }else{
            debugMessage("No basic measurement data found");
        }

        Biometry.setSideData("L", BiometryLeft);
        Biometry.setSideData("R", BiometryRight);

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
            // we try to search through the API, and if that one is successfull then try to search again
            // the reason of this is to check if the patient is already exists in the PAS and 
            
            logger.addToRawOutput("Patient not exists, starting API search...");
            if(APIconfigFile.equals("")){
                logger.addToRawOutput("No API config file specified, skipping API search...");
                // search for patient data has been failed - need to print and log!!
                logger.getLogger().setPatientNumber(Patient.getPatientID());
                logger.systemExitWithLog(4, "Cannot find patient data, file processing failed! \nSearched for: \n"+Patient.getDetails(), database);
                return false;
            }else{
                APIUtils API = new APIUtils(APIconfigFile);
                //API.setHost();

                try {
                    int APIstatus = API.searchPatient(Patient.getPatientID());
                    //System.out.println("API status CODE: "+APIstatus);
                    //System.out.println(API.getResponse());

                    logger.addToRawOutput("API return status: "+APIstatus);                
                    // OK: 200
                    if( APIstatus == 200){
                        // try the patient search again
                        logger.addToRawOutput("Waiting for 10 seconds before search again...");
                        try {
                            Thread.sleep(10000);
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        database.searchPatient(Patient.getPatientID(), Patient.getPatientGender(), Patient.getPatientBirth());
                    }
                } catch (ConnectException ex) {
                    Logger.getLogger(DICOMParser.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(database.getSelectedPatient() != null){
                    // we try to search again
                    BiometryProcessor.processBiometryEvent(Study,  Biometry, database);
                }else{
                    // search for patient data has been failed - need to print and log!!
                    logger.getLogger().setPatientNumber(Patient.getPatientID());
                    logger.systemExitWithLog(4, "Cannot find patient data, file processing failed! \nSearched for: \n"+Patient.getDetails(), database);
                    return false;
                }
            }
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
