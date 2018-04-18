/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;
import java.util.Date;
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
public class DICOMParser extends DICOMCommonFunctions{
    private PatientData Patient = new PatientData();

    /**
     *
     */
    protected StudyData Study = new StudyData();

    /**
     *
     */
    protected final BiometryData Biometry = new BiometryData();

    /**
     *
     */
    protected final BiometrySide BiometryLeft = new BiometrySide();

    /**
     *
     */
    protected final BiometrySide BiometryRight = new BiometrySide();


    private String APIconfigFile = "";

    /**
     *
     */
    protected String CurrentSide = "R";

    /**
     *
     */
    protected SpecificCharacterSet CharacterSet = SpecificCharacterSet.DEFAULT;

    /**
     *
     */
    protected BiometryFunctions biometryHelper;


    /**
     *  Initiate the parser
     * @param debugState
     * @param configFile
     * @param SystemLogger
     * @param APIconfigFile
     */
    public void initParser(boolean debugState, String configFile, DICOMLogger SystemLogger, String APIconfigFile, String hosNumRegex, String hosNumPad){
        this.logger = SystemLogger;
        this.debug = debugState;
        this.APIconfigFile = APIconfigFile;
        this.hosNumRegex = hosNumRegex;
        this.hosNumPad = hosNumPad;

        biometryHelper = new BiometryFunctions(logger);
        biometryHelper.initSessionFactory(configFile, SystemLogger);
        debugMessage("Connection status: "+biometryHelper.checkConnection());
    }

    /**
     *
     * @return
     */
    public StudyData getStudyData(){
        return this.Study;
    }

    /**
     *
     * @return
     */
    public PatientData getPatientData(){
        return this.Patient;
    }

    /**
     *
     * @return
     */
    public BiometryData getBiometryData(){
        return this.Biometry;
    }

    /**
     *
     * @param message
     */
    protected void debugMessage(String message){
        if(this.debug){
            logger.addToRawOutput(message);
            //System.out.println(message);
        }
    }

    /**
     *
     * @param inputFile
     * @return
     */
    public DicomFiles searchDicomFile(String inputFile){
        File file = new File(inputFile);
        Criteria crit = biometryHelper.session.createCriteria(DicomFiles.class);
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
            biometryHelper.session.save(newFile);
            return newFile;
        }

    }

    /**
     *
     * @param inputFile
     */
    public void parseDicomFile(String inputFile) throws IOException  {

        this.inputFileName = inputFile;

        Attributes attrs = new Attributes();

        DicomInputStream dis = null;
        try {
            dis = new DicomInputStream(new File(inputFile));
        } catch (IOException ex) {
            logger.systemExitWithLog(2, "Failed to open DICOM file, not a valid file or file not exists!", biometryHelper);
            //System.exit(2);
        }

        dis.setDicomInputHandler(dis);
        try {
            attrs = dis.readDataset(-1, -1);
        } catch (IOException ex) {
            logger.systemExitWithLog(3, "Failed to read DICOM file, not a valid file or file not exists!", biometryHelper);
        }

        //dumpDCMStructure(attrs);

        collectPatientData(attrs);
        collectStudyData(attrs);

        DICOMTools DicomTool = new DICOMTools();

        String TypeTag = attrs.getString(getTagInteger("00080016"));

        //System.out.println("+++++++++++++++++++++++++++++++++++++++"+TypeTag+"++++"+DicomTool.getDICOMType(TypeTag));

        Class importerClass = DicomTool.getDICOMType(TypeTag);

        if(importerClass.equals(DICOMIOLMaster500.class)){
            debugMessage("Importing IOLMaster 500");
            DICOMIOLMaster500 importer = new DICOMIOLMaster500(this);
            importer.collectData(attrs);
        }else if(importerClass.equals(DICOMIOLMaster700.class)){
            debugMessage("Importing IOLMaster 700");
            DICOMIOLMaster700 importer = new DICOMIOLMaster700(this);
            importer.collectData(attrs);
        }else if(importerClass.equals(DICOMHFAVF.class)){
            try {
                debugMessage("Importing HFA Visual Fields");
                DICOMHFAVF importer = new DICOMHFAVF(this);
                importer.collectData(attrs);
            } catch (IOException ex) {
                Logger.getLogger(DICOMParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(importerClass.equals(DICOMKOWA.class)){
            try {
                debugMessage("Importing KOWA");
                DICOMKOWA importer = new DICOMKOWA(this);
                importer.collectData(attrs);
            } catch (IOException ex) {
                Logger.getLogger(DICOMParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*
        try {
            Class importerClass = DicomTool.getDICOMType(TypeTag);
            Object DataParser = importerClass.newInstance();

            try {
                Method method = DataParser.getClass().getMethod("collectData", Attributes.class);
                method.invoke(DataParser, attrs);
            } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(DICOMParser.class.getName()).log(Level.SEVERE, null, ex);
            }

            //readAttributes(attrs, "");
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DICOMParser.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    private void collectPatientData(Attributes Attrs){
        //CharacterSet = SpecificCharacterSet.valueOf(VR.CS.toStrings(Attrs.getValue(getTagInteger("00080005")), true, SpecificCharacterSet.DEFAULT).toString());
        Patient.setPatientName(Attrs.getString(getTagInteger("00100010")));
        Patient.setPatientID(Attrs.getString(getTagInteger("00100020")));
        Patient.setPatientBirth(Attrs.getString(getTagInteger("00100030")));
        String gender = Attrs.getString(getTagInteger("00100040"));
        if(gender != null){
            Patient.setPatientGender(gender.charAt(0));
        }

    }

    private void collectStudyData(Attributes Attrs){
        String studyDate;
	String studyTime;

	studyDate = Attrs.getString(getTagInteger("00080021"));

        if (studyDate == null) {
		studyDate = Attrs.getString(getTagInteger("00080020"));
	}

	studyTime = Attrs.getString(getTagInteger("00080031"));

	if (studyTime == null) {
		studyTime = Attrs.getString(getTagInteger("00080030"));
	}

        Study.setComments(Attrs.getString(getTagInteger("00104000")));
        Study.setStudyDateTime(studyDate + studyTime);
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
        Study.setSopUID(Attrs.getString(getTagInteger("00080018")));
        Study.setAcquisitionDateTime(Attrs.getString(getTagInteger("0008002A")));
        Study.setDeviceSerial(Attrs.getString(getTagInteger("00181000")));
        if(Attrs.contains(getTagInteger("771B102C"))){
            Study.setSurgeonName(VR.PN.toStrings(Attrs.getValue(getTagInteger("771B102C")), true, CharacterSet).toString());
        }
    }

    /**
     *
     * @param s
     * @return
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }




    private void dumpDCMStructure(Attributes Attrs){
        debugMessage("--==< DATA STRUCTURE DUMP START >==--");
        int[] biometryTags = Attrs.tags();
        for( int tag : biometryTags){
            Integer level = Attrs.getLevel();
            String indent = "";
            for(int i=0;i<level;i++){
                indent += ">";
            }
            debugMessage(indent+" "+TagUtils.toHexString(tag)+" :: "+tag+" :: "+Attrs.getVR(tag));

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


    /**
     *
     * @return
     * @throws ParseException
     */
    public boolean processParsedData() throws ParseException{
        // first we try to connect to the database
        // if this connection fails we suggest to check the hibernate.conf.xml file
        if(debug){
            debugMessage(Patient.printPatientData());
            debugMessage(Study.printStudyData());
            debugMessage(Biometry.printBiometryData());
        }

        biometryHelper.searchPatient(Patient.getPatientID(), Patient.getPatientGender(), Patient.getPatientBirth(), this.hosNumRegex, this.hosNumPad);

        if(biometryHelper.getSelectedPatient() != null){
            biometryHelper.processBiometryEvent(Study,  Biometry);
        }else{
            // we try to search through the API, and if that one is successfull then try to search again
            // the reason of this is to check if the patient is already exists in the PAS and

            logger.addToRawOutput("Patient not exists, starting API search...");
            if(APIconfigFile.equals("")){
                logger.addToRawOutput("No API config file specified, skipping API search...");
                // search for patient data has been failed - need to print and log!!
                logger.getLogger().setPatientNumber(Patient.getPatientID());
                logger.systemExitWithLog(4, "Cannot find patient data, file processing failed! \nSearched for: \n"+Patient.getDetails(), biometryHelper);
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
                        biometryHelper.searchPatient(Patient.getPatientID(), Patient.getPatientGender(), Patient.getPatientBirth(), this.hosNumRegex, this.hosNumPad);
                    }
                } catch (ConnectException ex) {
                    Logger.getLogger(DICOMParser.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(biometryHelper.getSelectedPatient() != null){
                    // we try to search again
                    biometryHelper.processBiometryEvent(Study,  Biometry);
                }else{
                    // search for patient data has been failed - need to print and log!!
                    logger.getLogger().setPatientNumber(Patient.getPatientID());
                    logger.systemExitWithLog(4, "Cannot find patient data, file processing failed! \nSearched for: \n"+Patient.getDetails(), biometryHelper);
                    return false;
                }
            }
        }



        logger.getLogger().setStatus("success");
        logger.saveLogEntry(biometryHelper.session);
        biometryHelper.session.flush();
        biometryHelper.transaction.commit();
        biometryHelper.session.close();

        biometryHelper.closeSessionFactory();
        return true;
    }
}
