/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VEDELEKT
 */
public class BiometryData {
    private final BiometrySide Left = new BiometrySide();
    private final BiometrySide Right = new BiometrySide();
    private final BiometrySide Unknown = new BiometrySide(); // because some sequences contain information about the side at the end of the sequence we use this side for collecting data temporary
    
    public void setSideData(String Side){
        BiometrySide WhichSide;
        if(Side.equals("L")){
            WhichSide = Left;
        }else{
            WhichSide = Right;
        }
        // we copy the results to the specified side
        WhichSide.setMeasurements(Unknown.getMeasurements());
        // and remove everything from the Unknown, so we can use it for data collection again
        Unknown.setMeasurements(null);
    }
            
    public void setBiometryValue(String ValueName, String Side, String Value){
        BiometrySide WhichSide;
        switch (Side) {
            case "U":
                WhichSide = Unknown;
                break;
            case "L":
                WhichSide = Left;
                break;
            default:
                WhichSide = Right;
                break;
        }
        
        Method method = null;
        try {
            method = Class.forName("uk.org.openeyes.BiometrySide").getMethod("set"+ValueName, new Class[] {String.class});
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(BiometryData.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            method.invoke(WhichSide, Value); // 4 is the argument to pass to the method
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BiometryData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setBiometryValueNum(String ValueName, String Side, String Value, int Position){
        BiometrySide WhichSide;
        switch (Side) {
            case "U":
                WhichSide = Unknown;
                break;
            case "L":
                WhichSide = Left;
                break;
            default:
                WhichSide = Right;
                break;
        }

        Method method = null;
        try {
            method = Class.forName("uk.org.openeyes.BiometrySide").getMethod("set"+ValueName, new Class[] {String.class, Integer.class});
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(BiometryData.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            method.invoke(WhichSide, Value, Position); // 4 is the argument to pass to the method
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BiometryData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BiometrySide getBiometryValue(String Side){
        if(Side.equals("L")){
            return this.Left;
        }else{
            return this.Right;
        }
    }
    
    // return the integer value of the eye_id based on biometry data
    public Integer getEyeId(){
        // OpenEyes eye_id:
        // 1- Left
        // 2- Right
        // 3- Both
        if( Left.isSideHaveData() && Right.isSideHaveData()){
            return 3;
        }else if(Left.isSideHaveData() && !Right.isSideHaveData()){
            return 1;
        }else if(Right.isSideHaveData() && !Left.isSideHaveData()){
            return 2;
        }else{
            return 0;
        }
    }
    
    public void printBiometryData(){
        System.out.println("--== Biometry data ==--");
        System.out.println("-- Left side: ");
        Left.printBiometrySide();
        System.out.println("-- Right side: ");
        Right.printBiometrySide();
    }
}
