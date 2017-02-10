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
public class PatientData {
    private String PatientName;
    private String PatientID;
    private char PatientGender;
    private Calendar PatientBirth;
    
    /**
     *
     * @param PName
     */
    public void setPatientName(String PName){
        this.PatientName = PName.replace("^^^", "").replace("^",", ");
    }
    
    /**
     *
     * @param PID
     */
    public void setPatientID(String PID){
        this.PatientID = PID;
    }
    
    /**
     *
     * @param PGender
     */
    public void setPatientGender(char PGender){
        this.PatientGender = PGender;
    }

    /**
     *
     * @param PBirth
     */
    public void setPatientBirth(String PBirth) {
        this.PatientBirth = new GregorianCalendar(Integer.parseInt(PBirth.substring(0,4)), Integer.parseInt(PBirth.substring(4,6)) - 1, Integer.parseInt(PBirth.substring(6,8)));
    }

    /**
     *
     * @return
     */
    public String getPatientID(){
        return this.PatientID;
    }
    
    /**
     *
     * @return
     */
    public char getPatientGender(){
        return this.PatientGender;
    }
    
    /**
     *
     * @return
     */
    public Calendar getPatientBirth(){
        return PatientBirth;
    }

    private String displayDOB(){
        return this.PatientBirth.get(Calendar.DAY_OF_MONTH)+"/"+(this.PatientBirth.get(Calendar.MONTH) + 1)+"/"+this.PatientBirth.get(Calendar.YEAR);
    }
    
    /**
     *
     * @return
     */
    public String getDetails(){
        String output = "";
        output += "Patient's hospital number: "+this.PatientID+"\n";
        output += "Patient's gender: "+this.PatientGender+"\n";        
        output += "Patient's birth date: "+displayDOB()+"\n";
        return output;
    }
    
    /**
     *
     * @return
     */
    public String printPatientData(){
        String output;
        output = "--== Patient data ==--\n";
        output += "Patient's name: "+this.PatientName+"\n";
        output += "Patient's hospital number: "+this.PatientID+"\n";
        output += "Patient's gender: "+this.PatientGender+"\n";
        output += "Patient's birth date: "+displayDOB()+"\n";
        output += "\n";
        return output;
    }
    
}
