/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author VEDELEKT
 */
public class StudyData {
    private Calendar StudyDateTime = new GregorianCalendar();
    private String PhysicianName;
    private String SurgeonName;
    private String InstitutionName;
    private String StationName;             // unique station ID
    private String StudyID;         
    private String StudyInstanceID;
    private String SeriesInstanceID;
    private String DeviceManufacturer;
    private String DeviceModel;             // human readable machine model (eg. IOL Master 700)
    private String DeviceSoftwareVersion;
    private String DeviceType;
    private String Comments;
    private String ContentTime;
    private String ContentDate;
    private String AcquisitionDateTime;
    private String DeviceSerial;
    
    /**
     *
     * @param SDateTime
     */
    public void setStudyDateTime(String SDateTime){
        //System.out.println(SDateTime);
        //this.StudyDateTime.set(Integer.parseInt(SDateTime.substring(0,4)), Integer.parseInt(SDateTime.substring(4,6)), Integer.parseInt(SDateTime.substring(6,8)), Integer.parseInt(SDateTime.substring(8,10)), Integer.parseInt(SDateTime.substring(10,12)));
    }
    
    /**
     *
     * @return
     */
    public Calendar getStudyDateTime(){
        return this.StudyDateTime;
    }
    
    /**
     *
     * @param SPhysicianName
     */
    public void setPhysicianName(String SPhysicianName){
        this.PhysicianName = SPhysicianName;
    }
    
    /**
     *
     * @param SSurgeonName
     */
    public void setSurgeonName(String SSurgeonName){
        this.SurgeonName = SSurgeonName;
    }
    
    /**
     *
     * @return
     */
    public String getSurgeonName(){
        return this.SurgeonName;
    }

    /**
     *
     * @param SInstitutionName
     */
    public void setInstituionName(String SInstitutionName){
        this.InstitutionName = SInstitutionName;
    }
    
    /**
     *
     * @return
     */
    public String getInstituionName(){
        return this.InstitutionName;
    }
    
    /**
     *
     * @param SStationName
     */
    public void setStationName(String SStationName){
        this.StationName = SStationName;
    }
    
    /**
     *
     * @return
     */
    public String getStationName(){
        return this.StationName;
    }
    
    /**
     *
     * @param SStudyID
     */
    public void setStudyID(String SStudyID){
        this.StudyID = SStudyID;
    }
    
    /**
     *
     * @return
     */
    public String getStudyID(){
        return this.StudyID;
    }
    
    /**
     *
     * @param SStudyInstanceID
     */
    public void setStudyInstanceID(String SStudyInstanceID){
        this.StudyInstanceID = SStudyInstanceID;
    }
    
    /**
     *
     * @return
     */
    public String getStudyInstanceID(){
        return this.StudyInstanceID;
    }
    
    /**
     *
     * @param SSeriesInstanceID
     */
    public void setSeriesInstanceID(String SSeriesInstanceID){
        this.SeriesInstanceID = SSeriesInstanceID;
    }
    
    /**
     *
     * @return
     */
    public String getSeriesInstanceID(){
        return this.SeriesInstanceID;
    }
    
    /**
     *
     * @param SDeviceManufacturer
     */
    public void setDeviceManufacturer(String SDeviceManufacturer){
        this.DeviceManufacturer = SDeviceManufacturer;
    }
    
    /**
     *
     * @return
     */
    public String getDeviceManufacturer(){
        return this.DeviceManufacturer;
    }
    
    /**
     *
     * @param SDeviceModel
     */
    public void setDeviceModel(String SDeviceModel){
        this.DeviceModel = SDeviceModel;
    }
    
    /**
     *
     * @return
     */
    public String getDeviceModel(){
        return this.DeviceModel;
    }
    
    /**
     *
     * @param SDeviceSoftwareVersion
     */
    public void setDeviceSoftwareVersion(String SDeviceSoftwareVersion){
        this.DeviceSoftwareVersion = SDeviceSoftwareVersion;
    }
    
    /**
     *
     * @return
     */
    public String getDeviceSoftwareVersion(){
        return this.DeviceSoftwareVersion;
    }
   
    /**
     *
     * @param PComments
     */
    public void setComments(String PComments){
        this.Comments = PComments;
    }
    
    /**
     *
     * @return
     */
    public String getComments(){
        return this.Comments;
    }
    
    /**
     *
     * @param PContentTime
     */
    public void setContentTime(String PContentTime){
        this.ContentTime = PContentTime;
    }
    
    /**
     *
     * @param PContentDate
     */
    public void setContentDate(String PContentDate){
        this.ContentDate = PContentDate;
    }
    
    public void setAcquisitionDateTime(String AcDateTime){
        this.AcquisitionDateTime = AcDateTime;
    }
    
    public String getAcquisitionDateTime(){
        return this.AcquisitionDateTime;
    }
    
    public void setDeviceSerial(String DevSer){
        this.DeviceSerial = DevSer;
    }
    
    public String getDeviceSerial(){
        return this.DeviceSerial;
    }
    
    /**
     *
     * @return
     */
    public Calendar getContentDateTime(){
        GregorianCalendar contentDateTime = new GregorianCalendar();
        contentDateTime.set(Integer.parseInt(this.ContentDate.substring(0,4)), Integer.parseInt(this.ContentDate.substring(4,6))-1, Integer.parseInt(this.ContentDate.substring(6,8)), Integer.parseInt(this.ContentTime.substring(0,2)), Integer.parseInt(this.ContentTime.substring(2,4)), Integer.parseInt(this.ContentTime.substring(4,6)));
        return contentDateTime;
    }

    public void setDeviceType(String DeviceType){
        this.DeviceType = DeviceType;
    }
    
    public String getDeviceType(){
        return this.DeviceType;
    }
    
    /**
     *
     * @return
     */
    public String printStudyData(){
        String output;
        output = "--== Study data ==--\n";
        output += "Study date and time: "+this.StudyDateTime.get(Calendar.DAY_OF_MONTH)+"/"+this.StudyDateTime.get(Calendar.MONTH)+"/"+this.StudyDateTime.get(Calendar.YEAR)+" "+this.StudyDateTime.get(Calendar.HOUR_OF_DAY)+":"+this.StudyDateTime.get(Calendar.MINUTE)+"\n";
        output += "Study content date and time: "+this.ContentDate+" "+this.ContentTime+"\n";
        output += "Study physician: "+this.PhysicianName+"\n";
        output += "Study surgeon: "+this.SurgeonName+"\n";
        output += "Study instance ID: "+this.StudyInstanceID+"\n";
        output += "Series instance ID: "+this.SeriesInstanceID+"\n";
        output += "Study ID: "+this.StudyID+"\n";
        output += "Device name: "+this.InstitutionName+"\n";
        output += "Device ID: "+this.StationName+"\n";
        output += "Device Model: "+this.DeviceModel+"\n";
        output += "Device Manufacturer: "+this.DeviceManufacturer+"\n";
        output += "Device Software Version: "+this.DeviceSoftwareVersion+"\n";
        output += "Comments (Remark): "+this.Comments+"\n";
        output += "\n";
        return output;
    }
    
}
