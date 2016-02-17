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
import uk.org.openeyes.models.OphinbiometrySurgeon;
import uk.org.openeyes.models.User;

/**
 *
 * @author VEDELEKT
 */
public class BiometryFunctions extends DatabaseFunctions{

    private DICOMLogger dicomLogger;
    
    public BiometryFunctions(DICOMLogger SystemLogger){
        this.dicomLogger = SystemLogger;
        
    }
    
    /**
     *
     * @param lensName the value of lensName
     * @param aConst the value of aConst
     */
    private OphinbiometryLenstypeLens searchForLensData(String lensName, Double aConst) {
        OphinbiometryLenstypeLens lensType = null;
        Criteria crit = session.createCriteria(OphinbiometryLenstypeLens.class);
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
            User selectedUser = searchStudyUser("");
            lensType.setCreatedUserId(selectedUser);
            lensType.setLastModifiedUserId(selectedUser);
            lensType.setCreatedDate(new Date());
            lensType.setLastModifiedDate(new Date());
            lensType.setDescription("(Created by IOL Master input)");
            lensType.setDisplayOrder(0);
            lensType.setDeleted(false);
            lensType.setComments("Imported values, please check! Remove this comment when confirmed!");
            lensType.setPositionId(0);
            session.save(lensType);
        }
        return lensType;
    }

    /**
     *
     * @param formulaName the value of formulaName
     */
    private OphinbiometryCalculationFormula searchForFormulaData(String formulaName) {
        OphinbiometryCalculationFormula formulaType = null;
        Criteria crit = session.createCriteria(OphinbiometryCalculationFormula.class);
        crit.add(Restrictions.eq("name", formulaName));
        if (!crit.list().isEmpty()) {
            if (crit.list().get(0) != null) {
                formulaType = (OphinbiometryCalculationFormula) crit.list().get(0);
            }
        } else {
            Criteria crit2 = session.createCriteria(OphinbiometryCalculationFormula.class);
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
            User selectedUser = searchStudyUser("");
            formulaType.setCreatedUserId(selectedUser);
            formulaType.setLastModifiedUserId(selectedUser);
            formulaType.setCreatedDate(new Date());
            formulaType.setLastModifiedDate(new Date());
            formulaType.setDisplayOrder(0);
            formulaType.setDeleted(false);
            session.save(formulaType);
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
     */
    private void setMeasurementData(EtOphinbiometryMeasurement basicMeasurementData) {
        BiometrySide sideData;
        BigDecimal SNR = BigDecimal.ZERO;
        
        if (basicMeasurementData.getEventId() == null) {
            basicMeasurementData.setEventId(importedBiometryEvent.getEventId());
            basicMeasurementData.setEyeId(new Eye(eventBiometry.getEyeId()));
            basicMeasurementData.setCreatedDate(new Date());
            basicMeasurementData.setCreatedUserId(selectedUser);
            basicMeasurementData.setLastModifiedDate(new Date());
            basicMeasurementData.setLastModifiedUserId(selectedUser);
        }
        
        // saving left side
        sideData = eventBiometry.getBiometryValue("L");
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
        sideData = eventBiometry.getBiometryValue("R");
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
     */
    private void createSelectionData() {
        EtOphinbiometrySelection newBasicSelectionData = new EtOphinbiometrySelection();
        newBasicSelectionData.setCreatedDate(new Date());
        newBasicSelectionData.setLastModifiedDate(new Date());
        newBasicSelectionData.setCreatedUserId(selectedUser);
        newBasicSelectionData.setLastModifiedUserId(selectedUser);
        newBasicSelectionData.setEventId(importedBiometryEvent.getEventId());
        newBasicSelectionData.setEyeId(new Eye(eventBiometry.getEyeId()));
        newBasicSelectionData.setIolPowerLeft(BigDecimal.ZERO);
        newBasicSelectionData.setIolPowerRight(BigDecimal.ZERO);
        newBasicSelectionData.setPredictedRefractionLeft(BigDecimal.ZERO);
        newBasicSelectionData.setPredictedRefractionRight(BigDecimal.ZERO);
        session.save(newBasicSelectionData);
    }

    /**
     *
     */
    private void createCalculationData() {
        EtOphinbiometryCalculation newBasicCalculationData = new EtOphinbiometryCalculation();
        newBasicCalculationData.setCreatedDate(new Date());
        newBasicCalculationData.setLastModifiedDate(new Date());
        newBasicCalculationData.setCreatedUserId(selectedUser);
        newBasicCalculationData.setLastModifiedUserId(selectedUser);
        newBasicCalculationData.setEventId(importedBiometryEvent.getEventId());
        newBasicCalculationData.setEyeId(new Eye(eventBiometry.getEyeId()));
        newBasicCalculationData.setFormulaIdLeft(new OphinbiometryCalculationFormula(1));
        newBasicCalculationData.setFormulaIdRight(new OphinbiometryCalculationFormula(1));
        newBasicCalculationData.setTargetRefractionLeft(BigDecimal.valueOf(eventBiometry.getBiometryValue("L").getTargetRef()));
        newBasicCalculationData.setTargetRefractionRight(BigDecimal.valueOf(eventBiometry.getBiometryValue("R").getTargetRef()));
        newBasicCalculationData.setComments(eventStudy.getComments() );
        session.save(newBasicCalculationData);
        addVersionTableData(newBasicCalculationData, newBasicCalculationData.getId());
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
    
    private EtOphinbiometryIolRefValues searchCurrentIolRefValues( OphinbiometryLenstypeLens lensType, 
                                        OphinbiometryCalculationFormula formulaType){
        // we need to check if there is a saved value in the database now, and merge the new values to the existing records
        if(importedBiometryEvent.getEventId() != null){
            Criteria crit = getSession().createCriteria(EtOphinbiometryIolRefValues.class);
        
            crit.add(Restrictions.eq("eventId",importedBiometryEvent.getEventId()));
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
    
    private OphinbiometrySurgeon searchSurgeon(String surgeonName){
        OphinbiometrySurgeon surgeonData = null;
        Criteria crit = getSession().createCriteria(OphinbiometrySurgeon.class); 
        crit.add(Restrictions.eq("name", surgeonName));
        List surgeons = crit.list();
        dicomLogger.addToRawOutput("Searching for surgeon "+surgeonName+"...");

        if(surgeons.isEmpty()){
            surgeonData = new OphinbiometrySurgeon();
            surgeonData.setName(surgeonName);
            session.save(surgeonData);
            dicomLogger.addToRawOutput("New surgeon has been created...");    
        }else{
            surgeonData = (OphinbiometrySurgeon) surgeons.get(0);
            dicomLogger.addToRawOutput("Surgeon is already exists, using exiting data...");    
        }
        return surgeonData;
    }
    
    private boolean isDataModified(){
        return eventBiometry.getBiometryValue("L").getisACDModified() || 
               eventBiometry.getBiometryValue("L").getisALModified() ||
               eventBiometry.getBiometryValue("L").getisKModified() ||
               eventBiometry.getBiometryValue("R").getisACDModified() || 
               eventBiometry.getBiometryValue("R").getisALModified() ||
               eventBiometry.getBiometryValue("R").getisKModified();
    }
    
    /**
     *
     */
    private void saveIolRefValues() {
        
        ArrayList<BiometryMeasurementData> storedBiometryMeasurementDataLeft = eventBiometry.getBiometryValue("L").getMeasurements();
        ArrayList<BiometryMeasurementData> storedBiometryMeasurementDataRight = eventBiometry.getBiometryValue("R").getMeasurements();
        Integer ArrayListSize;
        String ReferenceSide;
        
        // we need to find which side contains more data
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
            
            if(!rowData.isIOLREFEmpty()){
                // TODO: what is the A constant and emmetropia value here??
                lensType = searchForLensData(rowData.getLensName(), rowData.getAConst());
                formulaType = searchForFormulaData(rowData.getFormulaName());

                // we search for current values
                EtOphinbiometryIolRefValues iolRefValues = null;
                iolRefValues = searchCurrentIolRefValues(lensType, formulaType);    

                if(isDataModified()){
                    if(iolRefValues != null){
                        iolRefValues.setActive(false);
                        session.saveOrUpdate(iolRefValues);
                        iolRefValues = null;
                    }
                    dicomLogger.addToRawOutput("Measurement data modified manually (AL, K or ACD values), creating new IOL REF record...");
                }

                boolean isNewIolRefValues = false;
                if( iolRefValues == null){
                    isNewIolRefValues = true;
                    iolRefValues = new EtOphinbiometryIolRefValues();
                }

                iolRefValues.setCreatedUserId(selectedUser);
                iolRefValues.setLastModifiedUserId(selectedUser);
                iolRefValues.setCreatedDate(new Date());
                iolRefValues.setLastModifiedDate(new Date());
                iolRefValues.setEventId(importedBiometryEvent.getEventId());
                iolRefValues.setEyeId(new Eye(eventBiometry.getEyeId()));
                iolRefValues.setFormulaId(formulaType);
                iolRefValues.setLensId(lensType);
                iolRefValues.setConstant(BigDecimal.valueOf(rowData.getAConst()));
                iolRefValues.setSurgeonId(searchSurgeon(eventStudy.getSurgeonName()));
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
                session.saveOrUpdate(iolRefValues);
                
                addVersionTableData(iolRefValues, iolRefValues.getId());
                
                formulaType = null;
                lensType = null;
            }
        }
    }

    private EtOphinbiometryMeasurement getMeasurementId(){
        Criteria crit = getSession().createCriteria(EtOphinbiometryMeasurement.class);

        crit.add(Restrictions.eq("eventId", importedBiometryEvent.getEventId()));
        EtOphinbiometryMeasurement currentMeasurement = (EtOphinbiometryMeasurement) crit.list().get(0);        
        return currentMeasurement;
    }
    
    /**
     *
     * @param IOLStudy the value of IOLStudy
     * @param IOLBiometry the value of IOLBiometry
     */
    public void processBiometryEvent(StudyData IOLStudy, BiometryData IOLBiometry) throws ParseException {

        setEventStudy(IOLStudy);
        //System.out.println("Study data has been set successfully");
        dicomLogger.addToRawOutput("Study data has been set successfully");
        setEventBiometry(IOLBiometry);
        //System.out.println("Biometry data set successfully");
        dicomLogger.addToRawOutput("Biometry data has been set successfully");
        this.setSelectedUser();
        //System.out.println("User selected successfully");
        dicomLogger.addToRawOutput("User data has been set successfully");

        selectActiveEpisode();
        importedBiometryEvent = processImportedEvent();
        EtOphinbiometryMeasurement basicMeasurementData;
        if (isNewEvent) {
            basicMeasurementData = new EtOphinbiometryMeasurement();
            this.createSelectionData();
            this.createCalculationData();
        }else{
            basicMeasurementData = getMeasurementId();
        }

        setMeasurementData(basicMeasurementData);
        //databaseFunctions.session.merge(basicMeasurementData);
        session.saveOrUpdate(basicMeasurementData);
        
        addVersionTableData(basicMeasurementData, basicMeasurementData.getId());
        
        
        this.saveIolRefValues();
        
        // we save the log entry for the import
        dicomLogger.getLogger().setStudyInstanceId(eventStudy.getStudyInstanceID());
        dicomLogger.getLogger().setSeriesInstanceId(eventStudy.getSeriesInstanceID());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        dicomLogger.getLogger().setStudyDatetime(df.parse(getStudyYMD(eventStudy.getStudyDateTime())));
        dicomLogger.getLogger().setStudyLocation(eventStudy.getInstituionName());
        dicomLogger.getLogger().setStationId(eventStudy.getStationName());
        dicomLogger.getLogger().setMachineManufacturer(eventStudy.getDeviceManufacturer());
        dicomLogger.getLogger().setMachineModel(eventStudy.getDeviceModel());
        dicomLogger.getLogger().setMachineSoftwareVersion(eventStudy.getDeviceSoftwareVersion());
        dicomLogger.getLogger().setReportType("biometry");
        dicomLogger.getLogger().setPatientNumber(selectedPatient.getHosNum());
        dicomLogger.getLogger().setImportDatetime(new Date());
        dicomLogger.getLogger().setImportType("F");

    }

    
    /**
     *
     *
     * @return OphinbiometryImportedEvents
     */
    private OphinbiometryImportedEvents processImportedEvent() {
        OphinbiometryImportedEvents importedEvent = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Criteria currentEvent = session.createCriteria(OphinbiometryImportedEvents.class);
        currentEvent.add(Restrictions.eq("studyId", eventStudy.getStudyInstanceID()));
        currentEvent.add(Restrictions.eq("seriesId", eventStudy.getSeriesInstanceID()));
        
        // we should check if event is deleted, and we should create a new one if yes
        currentEvent.add(Restrictions.sqlRestriction("event_id = (SELECT max(event_id) FROM ophinbiometry_imported_events WHERE study_id='"+eventStudy.getStudyInstanceID()+"' AND series_id='"+eventStudy.getSeriesInstanceID()+"')"));        
        if (!currentEvent.list().isEmpty()) {
            importedEvent = (OphinbiometryImportedEvents) currentEvent.list().get(0);
            // if the event is in deleted state we cerate a new record
            if(importedEvent.getEventId().getDeleted() == 0){
                dicomLogger.addToRawOutput("StudyID exists in database, merging with existing event...");

                isNewEvent = false;
                // we decide if the imported file content time is newer then the stored content date
                if(importedEvent.getContentDateTime() != null){
                    Calendar lastContentDateTime = new GregorianCalendar();
                    Calendar studyContentDateTime = new GregorianCalendar();
                    
                    try {
                        lastContentDateTime.setTime(df.parse(getSQLFormattedDate(importedEvent.getContentDateTime())));
                        studyContentDateTime.setTime(df.parse(getSQLFormattedDate(eventStudy.getContentDateTime().getTime())));
                    } catch (ParseException ex) {
                        Logger.getLogger(BiometryFunctions.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    if(studyContentDateTime.after(lastContentDateTime)){
                        try {
                            importedEvent.setContentDateTime(df.parse(getSQLFormattedDate(eventStudy.getContentDateTime().getTime())));
                        }catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        importedEvent.setIsMerged(true);
                        dicomLogger.addToRawOutput("Event content date and time is newer then the stored values, updating existing measuerements...");
                    }
                }
            }else{
                isNewEvent = true;
            }
        } 

        if(isNewEvent){
            Event newEvent = createNewEvent();
            importedEvent = new OphinbiometryImportedEvents();
            importedEvent.setDeviceName(eventStudy.getInstituionName());
            importedEvent.setDeviceId(eventStudy.getStationName());
            importedEvent.setDeviceManufacturer(eventStudy.getDeviceManufacturer());
            importedEvent.setDeviceModel(eventStudy.getDeviceModel());
            importedEvent.setDeviceSoftwareVersion(eventStudy.getDeviceSoftwareVersion());
            importedEvent.setStudyId(eventStudy.getStudyInstanceID());
            importedEvent.setSeriesId(eventStudy.getSeriesInstanceID());
            importedEvent.setPatientId(getSelectedPatient());
            importedEvent.setSurgeonName(eventStudy.getSurgeonName());
            try {
                importedEvent.setContentDateTime(df.parse(getSQLFormattedDate(eventStudy.getContentDateTime().getTime())));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            importedEvent.setEventId(newEvent);
            importedEvent.setCreatedDate(new Date());
            importedEvent.setLastModifiedDate(new Date());
            importedEvent.setCreatedUserId(selectedUser);
            importedEvent.setLastModifiedUserId(selectedUser);
            boolean isLinked = false;
            if (getSelectedEpisode() != null) {
                isLinked = true;
            }
            importedEvent.setIsLinked(isLinked);
            importedEvent.setIsMerged(false);
        }
        session.saveOrUpdate(importedEvent);

        return importedEvent;
    }
    
}
