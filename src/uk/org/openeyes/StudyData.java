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
    private String Comments;
    private String ContentTime;
    private String ContentDate;
    
    public void setStudyDateTime(String SDateTime){
        //System.out.println(SDateTime);
        this.StudyDateTime.set(Integer.parseInt(SDateTime.substring(0,4)), Integer.parseInt(SDateTime.substring(4,6)), Integer.parseInt(SDateTime.substring(6,8)), Integer.parseInt(SDateTime.substring(8,10)), Integer.parseInt(SDateTime.substring(10,12)));
    }
    
    public Calendar getStudyDateTime(){
        return this.StudyDateTime;
    }
    
    public void setPhysicianName(String SPhysicianName){
        this.PhysicianName = SPhysicianName;
    }
    
    public void setSurgeonName(String SSurgeonName){
        this.SurgeonName = SSurgeonName;
    }
    
    public String getSurgeonName(){
        return this.SurgeonName;
    }

    public void setInstituionName(String SInstitutionName){
        this.InstitutionName = SInstitutionName;
    }
    
    public String getInstituionName(){
        return this.InstitutionName;
    }
    
    public void setStationName(String SStationName){
        this.StationName = SStationName;
    }
    
    public String getStationName(){
        return this.StationName;
    }
    
    public void setStudyID(String SStudyID){
        this.StudyID = SStudyID;
    }
    
    public String getStudyID(){
        return this.StudyID;
    }
    
    public void setStudyInstanceID(String SStudyInstanceID){
        this.StudyInstanceID = SStudyInstanceID;
    }
    
    public String getStudyInstanceID(){
        return this.StudyInstanceID;
    }
    
    public void setSeriesInstanceID(String SSeriesInstanceID){
        this.SeriesInstanceID = SSeriesInstanceID;
    }
    
    public String getSeriesInstanceID(){
        return this.SeriesInstanceID;
    }
    
    public void setDeviceManufacturer(String SDeviceManufacturer){
        this.DeviceManufacturer = SDeviceManufacturer;
    }
    
    public String getDeviceManufacturer(){
        return this.DeviceManufacturer;
    }
    
    public void setDeviceModel(String SDeviceModel){
        this.DeviceModel = SDeviceModel;
    }
    
    public String getDeviceModel(){
        return this.DeviceModel;
    }
    
    public void setDeviceSoftwareVersion(String SDeviceSoftwareVersion){
        this.DeviceSoftwareVersion = SDeviceSoftwareVersion;
    }
    
    public String getDeviceSoftwareVersion(){
        return this.DeviceSoftwareVersion;
    }
   
    public void setComments(String PComments){
        this.Comments = PComments;
    }
    
    public String getComments(){
        return this.Comments;
    }
    
    public void setContentTime(String PContentTime){
        this.ContentTime = PContentTime;
    }
    
    
    public void setContentDate(String PContentDate){
        this.ContentDate = PContentDate;
    }
    
    public Calendar getContentDateTime(){
        GregorianCalendar contentDateTime = new GregorianCalendar();
        contentDateTime.set(Integer.parseInt(this.ContentDate.substring(0,4)), Integer.parseInt(this.ContentDate.substring(4,6))-1, Integer.parseInt(this.ContentDate.substring(6,8)), Integer.parseInt(this.ContentTime.substring(0,2)), Integer.parseInt(this.ContentTime.substring(2,4)), Integer.parseInt(this.ContentTime.substring(4,6)));
        return contentDateTime;
    }

        
    public String printStudyData(){
        String output;
        output = "--== Study data ==--\n";
        output += "Study date and time: "+this.StudyDateTime.get(Calendar.DAY_OF_MONTH)+"/"+this.StudyDateTime.get(Calendar.MONTH)+"/"+this.StudyDateTime.get(Calendar.YEAR)+" "+this.StudyDateTime.get(Calendar.HOUR_OF_DAY)+":"+this.StudyDateTime.get(Calendar.MINUTE)+"\n";
        output += "Study content time: "+this.ContentTime+"\n";
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
