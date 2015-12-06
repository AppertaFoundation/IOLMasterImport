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
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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
        sideData.setK1("0");
        sideData.setK2("0");
        sideData.setAxisK1("0");
        sideData.setAL("0"); 
        sideData.setSNR("0");       
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
        basicMeasurementData.setSnrMinLeft(BigDecimal.ZERO);
        
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
        basicMeasurementData.setSnrMinRight(BigDecimal.ZERO);
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
        newBasicCalculationData.setTargetRefractionLeft(BigDecimal.ZERO);
        newBasicCalculationData.setTargetRefractionRight(BigDecimal.ZERO);
        databaseFunctions.session.save(newBasicCalculationData);
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
            if (rowData.getLenseName() != null && !rowData.getLenseName().equals("")) {
                System.out.println("Multi lense - single formula format...");
                lensType = searchForLensData(rowData.getLenseName(), rowData.getAConst(), databaseFunctions);
                formulaType = searchForFormulaData(databaseFunctions.eventStudy.getFormulaName(), databaseFunctions);
            } else if (rowData.getFormulaName() != null && !rowData.getFormulaName().equals("")) {
                System.out.println("Multi formula - singe lense format...");
                formulaType = searchForFormulaData(rowData.getFormulaName(), databaseFunctions);
                lensType = searchForLensData(databaseFunctions.eventStudy.getLenseName(), 0.0, databaseFunctions);
            }
            EtOphinbiometryIolRefValues iolRefValues = new EtOphinbiometryIolRefValues();
            iolRefValues.setCreatedUserId(databaseFunctions.selectedUser);
            iolRefValues.setLastModifiedUserId(databaseFunctions.selectedUser);
            iolRefValues.setCreatedDate(new Date());
            iolRefValues.setLastModifiedDate(new Date());
            iolRefValues.setEventId(databaseFunctions.importedBiometryEvent.getEventId());
            iolRefValues.setEyeId(new Eye(databaseFunctions.eventBiometry.getEyeId()));
            iolRefValues.setFormulaId(formulaType);
            iolRefValues.setLensId(lensType);
            if (ReferenceSide.equals("L")) {
                iolRefValues.setIolRefValuesLeft(rowData.getIOLREFJSON());
                iolRefValues.setEmmetropiaLeft(BigDecimal.valueOf(rowData.getEmmetropia()));
                if (storedBiometryMeasurementDataLeft.size() == storedBiometryMeasurementDataRight.size()) {
                    iolRefValues.setIolRefValuesRight(storedBiometryMeasurementDataRight.get(i).getIOLREFJSON());
                    iolRefValues.setEmmetropiaRight(BigDecimal.valueOf(storedBiometryMeasurementDataRight.get(i).getEmmetropia()));
                }
            } else {
                iolRefValues.setIolRefValuesRight(rowData.getIOLREFJSON());
                iolRefValues.setEmmetropiaRight(BigDecimal.valueOf(rowData.getEmmetropia()));
                if (storedBiometryMeasurementDataLeft.size() == storedBiometryMeasurementDataRight.size()) {
                    iolRefValues.setIolRefValuesLeft(storedBiometryMeasurementDataLeft.get(i).getIOLREFJSON());
                    iolRefValues.setEmmetropiaLeft(BigDecimal.valueOf(storedBiometryMeasurementDataLeft.get(i).getEmmetropia()));
                }
            }
            databaseFunctions.session.save(iolRefValues);
            formulaType = null;
            lensType = null;
        }
    }

    /**
     *
     * @param IOLStudy the value of IOLStudy
     * @param IOLBiometry the value of IOLBiometry
     * @param databaseFunctions the value of databaseFunctions
     */
    public void processBiometryEvent(StudyData IOLStudy, BiometryData IOLBiometry, DatabaseFunctions databaseFunctions) throws ParseException {
        databaseFunctions.setSession();
        databaseFunctions.setTransaction();
        databaseFunctions.setEventStudy(IOLStudy);
        System.out.println("Study data has been set successfully");
        dicomLogger.addToRawOutput("Study data has been set successfully");
        databaseFunctions.setEventBiometry(IOLBiometry);
        System.out.println("Biometry data set successfully");
        dicomLogger.addToRawOutput("Biometry data has been set successfully");
        databaseFunctions.setSelectedUser();
        System.out.println("User selected successfully");
        dicomLogger.addToRawOutput("User data has been set successfully");

        databaseFunctions.selectActiveEpisode();
        databaseFunctions.importedBiometryEvent = processImportedEvent(databaseFunctions);
        if (databaseFunctions.isNewEvent) {
            EtOphinbiometryMeasurement newBasicMeasurementData = new EtOphinbiometryMeasurement();
            setMeasurementData(newBasicMeasurementData, databaseFunctions);
            databaseFunctions.session.save(newBasicMeasurementData);
            this.createSelectionData(databaseFunctions);
            this.createCalculationData(databaseFunctions);
        }
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
        dicomLogger.saveLogEntry(databaseFunctions.session);
        databaseFunctions.transaction.commit();
        databaseFunctions.session.close();
    }

    /**
     *
     *
     * @return OphinbiometryImportedEvents
     */
    private OphinbiometryImportedEvents processImportedEvent(DatabaseFunctions databaseFunctions) {
        OphinbiometryImportedEvents importedEvent;
        Criteria currentEvent = databaseFunctions.session.createCriteria(OphinbiometryImportedEvents.class);
        currentEvent.add(Restrictions.eq("studyId", databaseFunctions.eventStudy.getStudyInstanceID()));
        if (!currentEvent.list().isEmpty()) {
            importedEvent = (OphinbiometryImportedEvents) currentEvent.list().get(0);
            databaseFunctions.isNewEvent = false;
        } else {
            Event newEvent = databaseFunctions.createNewEvent();
            importedEvent = new OphinbiometryImportedEvents();
            importedEvent.setDeviceName(databaseFunctions.eventStudy.getInstituionName());
            importedEvent.setDeviceId(databaseFunctions.eventStudy.getStationName());
            importedEvent.setDeviceManufacturer(databaseFunctions.eventStudy.getDeviceManufacturer());
            importedEvent.setDeviceModel(databaseFunctions.eventStudy.getDeviceModel());
            importedEvent.setDeviceSoftwareVersion(databaseFunctions.eventStudy.getDeviceSoftwareVersion());
            importedEvent.setStudyId(databaseFunctions.eventStudy.getStudyInstanceID());
            importedEvent.setPatientId(databaseFunctions.getSelectedPatient());
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
            databaseFunctions.session.save(importedEvent);
        }
        return importedEvent;
    }
    
}
