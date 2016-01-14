/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import uk.org.openeyes.models.DicomEyeStatus;
import uk.org.openeyes.models.EtOphinbiometryCalculation;
import uk.org.openeyes.models.EtOphinbiometryIolRefValues;
import uk.org.openeyes.models.EtOphinbiometryMeasurement;
import uk.org.openeyes.models.EtOphinbiometrySelection;
import uk.org.openeyes.models.Event;
import uk.org.openeyes.models.Eye;
import uk.org.openeyes.models.OphinbiometryCalculationFormula;
import uk.org.openeyes.models.OphinbiometryImportedEvents;
import uk.org.openeyes.models.OphinbiometryLenstypeLens;
import uk.org.openeyes.models.User;

/**
 *
 * @author VEDELEKT
 */
public class BiometryFunctions {

    private DICOMLogger dicomLogger;
    
    public BiometryFunctions(DICOMLogger SystemLogger){
        this.dicomLogger = SystemLogger;
    }
    
    /**
     *
     * @param lensName the value of lensName
     * @param aConst the value of aConst
     * @param databaseFunctions the value of databaseFunctions
     */
    private OphinbiometryLenstypeLens searchForLensData(String lensName, Double aConst, DatabaseFunctions databaseFunctions) {
        OphinbiometryLenstypeLens lensType = null;
        Criteria crit = databaseFunctions.session.createCriteria(OphinbiometryLenstypeLens.class);
        crit.add(Restrictions.eq("name", lensName));
        if (!crit.list().isEmpty()) {
            if (crit.list().get(0) != null) {
                lensType = (OphinbiometryLenstypeLens) crit.list().get(0);
            }
        }
        if (lensType == null) {
            
            lensType = new OphinbiometryLenstypeLens();
            lensType.setName(lensName);
            lensType.setAcon(BigDecimal.valueOf(aConst));
            User selectedUser = databaseFunctions.searchStudyUser("");
            lensType.setCreatedUserId(selectedUser);
            lensType.setLastModifiedUserId(selectedUser);
            lensType.setCreatedDate(new Date());
            lensType.setLastModifiedDate(new Date());
            lensType.setDescription("(Created by IOL Master input)");
            lensType.setDisplayOrder(0);
            lensType.setDeleted(false);
            lensType.setComments("Imported values, please check! Remove this comment when confirmed!");
            lensType.setPositionId(0);
            databaseFunctions.session.save(lensType);
        }
        return lensType;
    }

    /**
     *
     * @param formulaName the value of formulaName
     * @param databaseFunctions the value of databaseFunctions
     */
    private OphinbiometryCalculationFormula searchForFormulaData(String formulaName, DatabaseFunctions databaseFunctions) {
        OphinbiometryCalculationFormula formulaType = null;
        Criteria crit = databaseFunctions.session.createCriteria(OphinbiometryCalculationFormula.class);
        crit.add(Restrictions.eq("name", formulaName));
        if (!crit.list().isEmpty()) {
            if (crit.list().get(0) != null) {
                formulaType = (OphinbiometryCalculationFormula) crit.list().get(0);
            }
        } else {
            Criteria crit2 = databaseFunctions.session.createCriteria(OphinbiometryCalculationFormula.class);
            crit2.add(Restrictions.eq("name", formulaName.replace("\u00ae", "")));
            if (!crit2.list().isEmpty()) {
                if (crit2.list().get(0) != null) {
                    formulaType = (OphinbiometryCalculationFormula) crit2.list().get(0);
                }
            }
        }
        if (formulaType == null) {
            formulaType = new OphinbiometryCalculationFormula();
            formulaType.setName(formulaName);
            User selectedUser = databaseFunctions.searchStudyUser("");
            formulaType.setCreatedUserId(selectedUser);
            formulaType.setLastModifiedUserId(selectedUser);
            formulaType.setCreatedDate(new Date());
            formulaType.setLastModifiedDate(new Date());
            formulaType.setDisplayOrder(0);
            formulaType.setDeleted(false);
            databaseFunctions.session.save(formulaType);
        }
        return formulaType;
    }

    private BiometrySide setEmptySideData(){
        BiometrySide sideData = new BiometrySide();
        sideData.setK1(0.0);
        sideData.setK2(0.0);
        sideData.setAxisK1(0.0);
        sideData.setAL(0.0); 
        sideData.setSNR(0.0);       
        return  sideData;
    }
    
    /**
     *
     * @param basicMeasurementData the value of basicMeasurementData
     * @param databaseFunctions the value of databaseFunctions
     */
    private void setMeasurementData(EtOphinbiometryMeasurement basicMeasurementData, DatabaseFunctions databaseFunctions) {
        BiometrySide sideData;
        BigDecimal SNR = BigDecimal.ZERO;
        
        if (basicMeasurementData.getEventId() == null) {
            basicMeasurementData.setEventId(databaseFunctions.importedBiometryEvent.getEventId());
            basicMeasurementData.setEyeId(new Eye(databaseFunctions.eventBiometry.getEyeId()));
            basicMeasurementData.setCreatedDate(new Date());
            basicMeasurementData.setCreatedUserId(databaseFunctions.selectedUser);
            basicMeasurementData.setLastModifiedDate(new Date());
            basicMeasurementData.setLastModifiedUserId(databaseFunctions.selectedUser);
        }
        
        // saving left side
        sideData = databaseFunctions.eventBiometry.getBiometryValue("L");
        if(sideData == null){
            sideData = setEmptySideData();
        }
        basicMeasurementData.setK1Left(BigDecimal.valueOf(sideData.getK1()));
        basicMeasurementData.setK2Left(BigDecimal.valueOf(sideData.getK2()));
        basicMeasurementData.setAxisK1Left(BigDecimal.valueOf(sideData.getAxisK1()));
        basicMeasurementData.setAxialLengthLeft(BigDecimal.valueOf(sideData.getAL()));
        SNR = sideData.getSNR();
        if(SNR == null){
            SNR = BigDecimal.ZERO;
        }
        basicMeasurementData.setSnrLeft(SNR);
        basicMeasurementData.setSnrMinLeft(sideData.getSNRMin());
        basicMeasurementData.setK2AxisLeft(BigDecimal.valueOf(sideData.getAxisK2()));
        Double currentDeltaK = sideData.getDeltaK();
        
        // in the device is an IOLMaster 500 than we make sure that the value is negative
        // Based on documentation: Manufacturer’s model name of the equipment that produced the composite instances. Always “IOLMaster 500”.
        if(databaseFunctions.eventStudy.getDeviceModel().equals("IOLMaster 500") && currentDeltaK > 0){
            currentDeltaK = -1 * currentDeltaK;
        }
        basicMeasurementData.setDeltaKLeft(BigDecimal.valueOf(currentDeltaK));
        basicMeasurementData.setDeltaKAxisLeft(BigDecimal.valueOf(sideData.getDeltaKAxis()));
        basicMeasurementData.setAcdLeft(BigDecimal.valueOf(sideData.getACD()));
        basicMeasurementData.setRefractionSphereLeft(BigDecimal.valueOf(sideData.getRefractionSphere()));
        basicMeasurementData.setRefractionDeltaLeft(BigDecimal.valueOf(sideData.getRefractionDelta()));
        basicMeasurementData.setRefractionAxisLeft(BigDecimal.valueOf(sideData.getRefractionAxis()));
        DicomEyeStatus eyeStatusLeft = new DicomEyeStatus(sideData.getEyeStatus());
        basicMeasurementData.setEyeStatusLeft(eyeStatusLeft);
        basicMeasurementData.setKModifiedLeft(sideData.getisKModified());
        basicMeasurementData.setAlModifiedLeft(sideData.getisALModified());
        
        // saving right side
        sideData = databaseFunctions.eventBiometry.getBiometryValue("R");
        if(sideData == null){
            sideData = setEmptySideData();
        }
        basicMeasurementData.setK1Right(BigDecimal.valueOf(sideData.getK1()));
        basicMeasurementData.setK2Right(BigDecimal.valueOf(sideData.getK2()));
        basicMeasurementData.setAxisK1Right(BigDecimal.valueOf(sideData.getAxisK1()));
        basicMeasurementData.setAxialLengthRight(BigDecimal.valueOf(sideData.getAL()));
        SNR = sideData.getSNR();
        if(SNR == null){
            SNR = BigDecimal.ZERO;
        }
        basicMeasurementData.setSnrRight(SNR);
        basicMeasurementData.setSnrMinRight(sideData.getSNRMin());
        basicMeasurementData.setK2AxisRight(BigDecimal.valueOf(sideData.getAxisK2()));
        currentDeltaK = sideData.getDeltaK();
        
        // in the device is an IOLMaster 500 than we make sure that the value is negative
        // Based on documentation: Manufacturer’s model name of the equipment that produced the composite instances. Always “IOLMaster 500”.
        if(databaseFunctions.eventStudy.getDeviceModel().equals("IOLMaster 500") && currentDeltaK > 0){
            currentDeltaK = -1 * currentDeltaK;
        }
        basicMeasurementData.setDeltaKRight(BigDecimal.valueOf(currentDeltaK));
        basicMeasurementData.setDeltaKAxisRight(BigDecimal.valueOf(sideData.getDeltaKAxis()));
        basicMeasurementData.setAcdRight(BigDecimal.valueOf(sideData.getACD()));
        basicMeasurementData.setRefractionSphereRight(BigDecimal.valueOf(sideData.getRefractionSphere()));
        basicMeasurementData.setRefractionDeltaRight(BigDecimal.valueOf(sideData.getRefractionDelta()));
        basicMeasurementData.setRefractionAxisRight(BigDecimal.valueOf(sideData.getRefractionAxis()));
        DicomEyeStatus eyeStatusRight = new DicomEyeStatus(sideData.getEyeStatus());
        basicMeasurementData.setEyeStatusRight(eyeStatusRight);
        basicMeasurementData.setKModifiedRight(sideData.getisKModified());
        basicMeasurementData.setAlModifiedRight(sideData.getisALModified());
    }

    /**
     *
     * @param databaseFunctions the value of databaseFunctions
     */
    private void createSelectionData(DatabaseFunctions databaseFunctions) {
        EtOphinbiometrySelection newBasicSelectionData = new EtOphinbiometrySelection();
        newBasicSelectionData.setCreatedDate(new Date());
        newBasicSelectionData.setLastModifiedDate(new Date());
        newBasicSelectionData.setCreatedUserId(databaseFunctions.selectedUser);
        newBasicSelectionData.setLastModifiedUserId(databaseFunctions.selectedUser);
        newBasicSelectionData.setEventId(databaseFunctions.importedBiometryEvent.getEventId());
        newBasicSelectionData.setEyeId(new Eye(databaseFunctions.eventBiometry.getEyeId()));
        newBasicSelectionData.setIolPowerLeft(BigDecimal.ZERO);
        newBasicSelectionData.setIolPowerRight(BigDecimal.ZERO);
        newBasicSelectionData.setPredictedRefractionLeft(BigDecimal.ZERO);
        newBasicSelectionData.setPredictedRefractionRight(BigDecimal.ZERO);
        databaseFunctions.session.save(newBasicSelectionData);
    }

    /**
     *
     * @param databaseFunctions the value of databaseFunctions
     */
    private void createCalculationData(DatabaseFunctions databaseFunctions) {
        EtOphinbiometryCalculation newBasicCalculationData = new EtOphinbiometryCalculation();
        newBasicCalculationData.setCreatedDate(new Date());
        newBasicCalculationData.setLastModifiedDate(new Date());
        newBasicCalculationData.setCreatedUserId(databaseFunctions.selectedUser);
        newBasicCalculationData.setLastModifiedUserId(databaseFunctions.selectedUser);
        newBasicCalculationData.setEventId(databaseFunctions.importedBiometryEvent.getEventId());
        newBasicCalculationData.setEyeId(new Eye(databaseFunctions.eventBiometry.getEyeId()));
        newBasicCalculationData.setFormulaIdLeft(new OphinbiometryCalculationFormula(1));
        newBasicCalculationData.setFormulaIdRight(new OphinbiometryCalculationFormula(1));
        newBasicCalculationData.setTargetRefractionLeft(BigDecimal.valueOf(databaseFunctions.eventBiometry.getBiometryValue("L").getTargetRef()));
        newBasicCalculationData.setTargetRefractionRight(BigDecimal.valueOf(databaseFunctions.eventBiometry.getBiometryValue("R").getTargetRef()));
        newBasicCalculationData.setComments(databaseFunctions.eventStudy.getComments() );
        databaseFunctions.session.save(newBasicCalculationData);
    }

    private JSONObject decodeJSONData(String IOLJSON){
        JSONParser parser=new JSONParser();
        JSONObject dataObj = null;
        try {
            dataObj = (JSONObject)parser.parse(IOLJSON);
            return dataObj;
            //JSONArray dataArray=(JSONArray)dataObj;
            //return dataArray;
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(BiometryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private String mergeIolRefValues(EtOphinbiometryIolRefValues currentIolRefValues, String newIolRefValues, String side){
        String IOLJSON = "";
        if(side.equals("L")){
            IOLJSON = currentIolRefValues.getIolRefValuesLeft();    
        }else if(side.equals("R")){
            IOLJSON = currentIolRefValues.getIolRefValuesRight();    
        }
        if(IOLJSON == null){
            return newIolRefValues;
        }else{
            JSONObject currentIOLRefData = decodeJSONData(IOLJSON);
            JSONObject newIOLRefData = decodeJSONData(newIolRefValues);
                        
            // merge logic based on IOL values
            JSONArray currentIOLArray = (JSONArray) currentIOLRefData.get("IOL");
            JSONArray currentREFArray = (JSONArray) currentIOLRefData.get("REF");
            JSONArray newIOLArray = (JSONArray) newIOLRefData.get("IOL");
            JSONArray newREFArray = (JSONArray) newIOLRefData.get("REF");
            
            JSONObject mergedIOLRefData = new JSONObject();
            
            for(Integer i=0; i<newIOLArray.size(); i++){
                if(!currentIOLArray.contains(newIOLArray.get(i))){
                    currentIOLArray.add(newIOLArray.get(i));
                    currentREFArray.add(newREFArray.get(i));
                }
            }
            mergedIOLRefData.put("IOL", currentIOLArray);
            mergedIOLRefData.put("REF", currentREFArray);
        
            return mergedIOLRefData.toJSONString();
        }
    }
    
    private EtOphinbiometryIolRefValues searchCurrentIolRefValues(  DatabaseFunctions databaseFunctions,         
                                        OphinbiometryLenstypeLens lensType, 
                                        OphinbiometryCalculationFormula formulaType){
        // we need to check if there is a saved value in the database now, and merge the new values to the existing records
        if(databaseFunctions.importedBiometryEvent.getEventId() != null){
            Criteria crit = databaseFunctions.getSession().createCriteria(EtOphinbiometryIolRefValues.class);
        
            crit.add(Restrictions.eq("eventId",databaseFunctions.importedBiometryEvent.getEventId()));
            crit.add(Restrictions.eq("lensId", lensType));
            crit.add(Restrictions.eq("formulaId", formulaType));
            List iolDataList = crit.list();
            if(!iolDataList.isEmpty()){
                EtOphinbiometryIolRefValues currentIOLValues = (EtOphinbiometryIolRefValues) iolDataList.get(0);
                return currentIOLValues;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    
    /**
     *
     * @param databaseFunctions the value of databaseFunctions
     */
    private void saveIolRefValues(DatabaseFunctions databaseFunctions) {
        
        ArrayList<BiometryMeasurementData> storedBiometryMeasurementDataLeft = databaseFunctions.eventBiometry.getBiometryValue("L").getMeasurements();
        ArrayList<BiometryMeasurementData> storedBiometryMeasurementDataRight = databaseFunctions.eventBiometry.getBiometryValue("R").getMeasurements();
        Integer ArrayListSize;
        String ReferenceSide;
        if (storedBiometryMeasurementDataLeft.size() > storedBiometryMeasurementDataRight.size()) {
            ArrayListSize = storedBiometryMeasurementDataLeft.size();
            ReferenceSide = "L";
        } else {
            ArrayListSize = storedBiometryMeasurementDataRight.size();
            ReferenceSide = "R";
        }
        OphinbiometryLenstypeLens lensType = null;
        OphinbiometryCalculationFormula formulaType = null;
        for (Integer i = 0; i < ArrayListSize; i++) {
            BiometryMeasurementData rowData;
            if (ReferenceSide.equals("L")) {
                rowData = storedBiometryMeasurementDataLeft.get(i);
            } else {
                rowData = storedBiometryMeasurementDataRight.get(i);
            }
            
            // Haigis-L is a special format!
            // TODO: what is the A constant and emmetropia value here??
            lensType = searchForLensData(rowData.getLenseName(), rowData.getAConst(), databaseFunctions);
            formulaType = searchForFormulaData(rowData.getFormulaName(), databaseFunctions);
            
            /*if(databaseFunctions.eventStudy.getFormulaName() != null && (databaseFunctions.eventStudy.getFormulaName().contains("Haigis-L") || (databaseFunctions.eventStudy.getFormulaName().contains("HofferQ") && rowData.getFormulaName() != null))){
                dicomLogger.addToRawOutput(databaseFunctions.eventStudy.getFormulaName()+" - Multi lens - single formula format...");
                lensType = searchForLensData(rowData.getFormulaName(), rowData.getAConst(), databaseFunctions);
                formulaType = searchForFormulaData(databaseFunctions.eventStudy.getFormulaName(), databaseFunctions);
            }else if (rowData.getLenseName() != null && !rowData.getLenseName().equals("")) {
                dicomLogger.addToRawOutput("Multi lens - single formula format...");
                lensType = searchForLensData(rowData.getLenseName(), rowData.getAConst(), databaseFunctions);
                formulaType = searchForFormulaData(databaseFunctions.eventStudy.getFormulaName(), databaseFunctions);
            } else if (rowData.getFormulaName() != null && !rowData.getFormulaName().equals("")) {
                dicomLogger.addToRawOutput("Multi formula - singe lens format...");
                formulaType = searchForFormulaData(rowData.getFormulaName(), databaseFunctions);
                lensType = searchForLensData(databaseFunctions.eventStudy.getLenseName(), 0.0, databaseFunctions);
            }*/
            // we search for current values
            EtOphinbiometryIolRefValues iolRefValues = searchCurrentIolRefValues(databaseFunctions, lensType, formulaType);
            
            boolean isNewIolRefValues = false;
            if( iolRefValues == null){
                isNewIolRefValues = true;
                iolRefValues = new EtOphinbiometryIolRefValues();
            }
            iolRefValues.setCreatedUserId(databaseFunctions.selectedUser);
            iolRefValues.setLastModifiedUserId(databaseFunctions.selectedUser);
            iolRefValues.setCreatedDate(new Date());
            iolRefValues.setLastModifiedDate(new Date());
            iolRefValues.setEventId(databaseFunctions.importedBiometryEvent.getEventId());
            iolRefValues.setEyeId(new Eye(databaseFunctions.eventBiometry.getEyeId()));
            iolRefValues.setFormulaId(formulaType);
            iolRefValues.setLensId(lensType);
            if (ReferenceSide.equals("L")) {
                if(isNewIolRefValues){
                    iolRefValues.setIolRefValuesLeft(rowData.getIOLREFJSON());
                }else{
                    iolRefValues.setIolRefValuesLeft(mergeIolRefValues(iolRefValues, rowData.getIOLREFJSON(), "L" ));
                }
                iolRefValues.setEmmetropiaLeft(BigDecimal.valueOf(rowData.getEmmetropia()));
                if (storedBiometryMeasurementDataLeft.size() == storedBiometryMeasurementDataRight.size()) {
                    if(isNewIolRefValues){    
                        iolRefValues.setIolRefValuesRight(storedBiometryMeasurementDataRight.get(i).getIOLREFJSON());
                    }else{
                        iolRefValues.setIolRefValuesRight(mergeIolRefValues(iolRefValues, storedBiometryMeasurementDataRight.get(i).getIOLREFJSON(),"R"));
                    }
                    iolRefValues.setEmmetropiaRight(BigDecimal.valueOf(storedBiometryMeasurementDataRight.get(i).getEmmetropia()));
                }
            } else {
                if(isNewIolRefValues){
                    iolRefValues.setIolRefValuesRight(rowData.getIOLREFJSON());
                }else{
                    iolRefValues.setIolRefValuesRight(mergeIolRefValues(iolRefValues, rowData.getIOLREFJSON(), "R" ));
                }
                iolRefValues.setEmmetropiaRight(BigDecimal.valueOf(rowData.getEmmetropia()));
                if (storedBiometryMeasurementDataLeft.size() == storedBiometryMeasurementDataRight.size()) {
                    if(isNewIolRefValues){
                        iolRefValues.setIolRefValuesLeft(storedBiometryMeasurementDataLeft.get(i).getIOLREFJSON());
                    }else{
                        iolRefValues.setIolRefValuesLeft(mergeIolRefValues(iolRefValues, storedBiometryMeasurementDataLeft.get(i).getIOLREFJSON(),"L"));
                    }
                    iolRefValues.setEmmetropiaLeft(BigDecimal.valueOf(storedBiometryMeasurementDataLeft.get(i).getEmmetropia()));
                }
            }
            databaseFunctions.session.saveOrUpdate(iolRefValues);
            formulaType = null;
            lensType = null;
        }
    }

    private EtOphinbiometryMeasurement getMeasurementId(DatabaseFunctions databaseFunctions){
        Criteria crit = databaseFunctions.getSession().createCriteria(EtOphinbiometryMeasurement.class);
        //Event currentEvent = (Event) databaseFunctions.importedBiometryEvent.getEventId();
        //System.out.println(databaseFunctions.importedBiometryEvent.getEventId().getId()+"<+++++++++++++++++++++++--------");
        crit.add(Restrictions.eq("eventId", databaseFunctions.importedBiometryEvent.getEventId()));
        EtOphinbiometryMeasurement currentMeasurement = (EtOphinbiometryMeasurement) crit.list().get(0);        
        return currentMeasurement;
    }
    
    /**
     *
     * @param IOLStudy the value of IOLStudy
     * @param IOLBiometry the value of IOLBiometry
     * @param databaseFunctions the value of databaseFunctions
     */
    public void processBiometryEvent(StudyData IOLStudy, BiometryData IOLBiometry, DatabaseFunctions databaseFunctions) throws ParseException {

        databaseFunctions.setEventStudy(IOLStudy);
        //System.out.println("Study data has been set successfully");
        dicomLogger.addToRawOutput("Study data has been set successfully");
        databaseFunctions.setEventBiometry(IOLBiometry);
        //System.out.println("Biometry data set successfully");
        dicomLogger.addToRawOutput("Biometry data has been set successfully");
        databaseFunctions.setSelectedUser();
        //System.out.println("User selected successfully");
        dicomLogger.addToRawOutput("User data has been set successfully");

        databaseFunctions.selectActiveEpisode();
        databaseFunctions.importedBiometryEvent = processImportedEvent(databaseFunctions);
        EtOphinbiometryMeasurement basicMeasurementData;
        if (databaseFunctions.isNewEvent) {
            basicMeasurementData = new EtOphinbiometryMeasurement();
            this.createSelectionData(databaseFunctions);
            this.createCalculationData(databaseFunctions);
        }else{
            basicMeasurementData = getMeasurementId(databaseFunctions);
        }

        setMeasurementData(basicMeasurementData, databaseFunctions);
        //databaseFunctions.session.merge(basicMeasurementData);
        databaseFunctions.session.saveOrUpdate(basicMeasurementData);
        
        
        this.saveIolRefValues(databaseFunctions);
        
        // we save the log entry for the import
        dicomLogger.getLogger().setStudyInstanceId(databaseFunctions.eventStudy.getStudyInstanceID());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        dicomLogger.getLogger().setStudyDatetime(df.parse(databaseFunctions.getStudyYMD(databaseFunctions.eventStudy.getStudyDateTime())));
        dicomLogger.getLogger().setStudyLocation(databaseFunctions.eventStudy.getInstituionName());
        dicomLogger.getLogger().setStationId(databaseFunctions.eventStudy.getStationName());
        dicomLogger.getLogger().setMachineManufacturer(databaseFunctions.eventStudy.getDeviceManufacturer());
        dicomLogger.getLogger().setMachineModel(databaseFunctions.eventStudy.getDeviceModel());
        dicomLogger.getLogger().setMachineSoftwareVersion(databaseFunctions.eventStudy.getDeviceSoftwareVersion());
        dicomLogger.getLogger().setReportType("biometry");
        dicomLogger.getLogger().setPatientNumber(databaseFunctions.selectedPatient.getHosNum());
        dicomLogger.getLogger().setImportDatetime(new Date());
        dicomLogger.getLogger().setImportType("F");

    }

    
    /**
     *
     *
     * @return OphinbiometryImportedEvents
     */
    private OphinbiometryImportedEvents processImportedEvent(DatabaseFunctions databaseFunctions) {
        OphinbiometryImportedEvents importedEvent = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Criteria currentEvent = databaseFunctions.session.createCriteria(OphinbiometryImportedEvents.class);
        currentEvent.add(Restrictions.eq("studyId", databaseFunctions.eventStudy.getStudyInstanceID()));
        
        // we should check if event is deleted, and we should create a new one if yes
        currentEvent.add(Restrictions.sqlRestriction("event_id = (SELECT max(event_id) FROM ophinbiometry_imported_events WHERE study_id='"+databaseFunctions.eventStudy.getStudyInstanceID()+"')"));        
        if (!currentEvent.list().isEmpty()) {
            importedEvent = (OphinbiometryImportedEvents) currentEvent.list().get(0);
            // if the event is in deleted state we cerate a new record
            if(importedEvent.getEventId().getDeleted() == 0){
                dicomLogger.addToRawOutput("StudyID exists in database, merging with existing event...");

                databaseFunctions.isNewEvent = false;
                // we decide if the imported file content time is newer then the stored content date
                if(importedEvent.getContentDateTime() != null){
                    Calendar lastContentDateTime = new GregorianCalendar();
                    Calendar studyContentDateTime = new GregorianCalendar();
                    
                    try {
                        lastContentDateTime.setTime(df.parse(databaseFunctions.getSQLFormattedDate(importedEvent.getContentDateTime())));
                        studyContentDateTime.setTime(df.parse(databaseFunctions.getSQLFormattedDate(databaseFunctions.eventStudy.getContentDateTime().getTime())));
                    } catch (ParseException ex) {
                        Logger.getLogger(BiometryFunctions.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    if(studyContentDateTime.after(lastContentDateTime)){
                        try {
                            importedEvent.setContentDateTime(df.parse(databaseFunctions.getSQLFormattedDate(databaseFunctions.eventStudy.getContentDateTime().getTime())));
                        }catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        importedEvent.setIsMerged(true);
                        dicomLogger.addToRawOutput("Event content date and time is newer then the stored values, updating existing measuerements...");
                    }
                }
            }else{
                databaseFunctions.isNewEvent = true;
            }
        } 

        if(databaseFunctions.isNewEvent){
            Event newEvent = databaseFunctions.createNewEvent();
            importedEvent = new OphinbiometryImportedEvents();
            importedEvent.setDeviceName(databaseFunctions.eventStudy.getInstituionName());
            importedEvent.setDeviceId(databaseFunctions.eventStudy.getStationName());
            importedEvent.setDeviceManufacturer(databaseFunctions.eventStudy.getDeviceManufacturer());
            importedEvent.setDeviceModel(databaseFunctions.eventStudy.getDeviceModel());
            importedEvent.setDeviceSoftwareVersion(databaseFunctions.eventStudy.getDeviceSoftwareVersion());
            importedEvent.setStudyId(databaseFunctions.eventStudy.getStudyInstanceID());
            importedEvent.setPatientId(databaseFunctions.getSelectedPatient());
            importedEvent.setSurgeonName(databaseFunctions.eventStudy.getSurgeonName());
            try {
                importedEvent.setContentDateTime(df.parse(databaseFunctions.getSQLFormattedDate(databaseFunctions.eventStudy.getContentDateTime().getTime())));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            importedEvent.setEventId(newEvent);
            importedEvent.setCreatedDate(new Date());
            importedEvent.setLastModifiedDate(new Date());
            importedEvent.setCreatedUserId(databaseFunctions.selectedUser);
            importedEvent.setLastModifiedUserId(databaseFunctions.selectedUser);
            boolean isLinked = false;
            if (databaseFunctions.getSelectedEpisode() != null) {
                isLinked = true;
            }
            importedEvent.setIsLinked(isLinked);
            importedEvent.setIsMerged(false);
        }
        databaseFunctions.session.saveOrUpdate(importedEvent);
        return importedEvent;
    }
    
}
