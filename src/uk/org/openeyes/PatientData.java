/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private String PatientComments;
    private Calendar PatientBirth = new GregorianCalendar();
    
    public void setPatientName(String PName){
        this.PatientName = PName.replace("^^^", "").replace("^",", ");
    }
    
    public void setPatientID(String PID){
        this.PatientID = PID;
    }
    
    public void setPatientGender(char PGender){
        this.PatientGender = PGender;
    }

    public void setPatientComments(String PComments){
        this.PatientComments = PComments;
    }

    public void setPatientBirth(String PBirth) {
        this.PatientBirth.set(Integer.parseInt(PBirth.substring(0,4)), Integer.parseInt(PBirth.substring(4,6)), Integer.parseInt(PBirth.substring(6,8)));
    }

    public String getPatientID(){
        return this.PatientID;
    }
    
    public char getPatientGender(){
        return this.PatientGender;
    }
    
    public Calendar getPatientBirth(){
        //return this.PatientBirth.get(Calendar.YEAR)+"-"+this.PatientBirth.get(Calendar.MONTH)+"-"+this.PatientBirth.get(Calendar.DAY_OF_MONTH);
        return PatientBirth;
    }
    
    public void printPatientData(){
        System.out.println("--== Patient data ==--");
        System.out.println("Patient's name: "+this.PatientName);
        System.out.println("Patient's hospital number: "+this.PatientID);
        System.out.println("Patient's gender: "+this.PatientGender);
        System.out.println("Patient's birth date: "+this.PatientBirth.get(Calendar.DAY_OF_MONTH)+"/"+this.PatientBirth.get(Calendar.MONTH)+"/"+this.PatientBirth.get(Calendar.YEAR));
        System.out.println("Comments: "+this.PatientComments);
        System.out.println("");
    }
    
}
