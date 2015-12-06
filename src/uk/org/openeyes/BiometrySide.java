/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 *
 * @author VEDELEKT
 */
public class BiometrySide {
    private double K1;
    private double K2;
    private double AxisK1;
    private double AL;      // axial length
    private BigDecimal SNR;
    private ArrayList<BiometryMeasurementData> Measurements = new ArrayList<>();

    public void setK1(String BK1){
        if(K1 == 0){
            this.K1 = Double.parseDouble(BK1);
        }
    }
    
    public double getK1(){
        return this.K1;
    }
    
    public void setK2(String BK2){
        this.K2 = Double.parseDouble(BK2);
    }
    
    public double getK2(){
        return this.K2;
    }

    public void setAxisK1(String BAxisK1){
        this.AxisK1 = Double.parseDouble(BAxisK1);
    }
    
    public double getAxisK1(){
        return this.AxisK1;
    }
    
    public void setAL(String BAL){
        this.AL = Double.parseDouble(BAL);
    }
    
    public double getAL(){
        return this.AL;
    }
    
    public void setSNR(String BSNR){
        this.SNR = BigDecimal.valueOf(Double.parseDouble(BSNR));
    }
    
    public BigDecimal getSNR(){
        return this.SNR;
    }
    
    // add new lenses to the selected side
    public void setLenses(String LName){
        if(Measurements == null){
            Measurements = new ArrayList<>();    
        }
        Measurements.add(new BiometryMeasurementData());
    }
    
    public void setLensesName(String LName, Integer LNum){
        Measurements.get(LNum).setLenseName(LName);
    }
    
    public void setFormulaName(String LName, Integer LNum){
        Measurements.get(LNum).setFormulaName(LName);
    }
    
    public void setLenseAConst(String LAConst, Integer LNum){
        Measurements.get(LNum).setAConst(LAConst);
    }
    
    public void setLenseEmmetropia(String LEmmetropia, Integer LNum){
        Measurements.get(LNum).setEmmetropia(LEmmetropia);
    }
    
    public void setLenseIOL(String LIOL, Integer LNum){
        Measurements.get(LNum).setIOL(LIOL);
    }
    
    public void setLenseREF(String LREF, Integer LNum){
        Measurements.get(LNum).setREF(LREF);
    }
    
    public ArrayList<BiometryMeasurementData> getMeasurements(){
        return this.Measurements;
    }
    
    public void setMeasurements(ArrayList<BiometryMeasurementData> LMeasurements){
        this.Measurements = LMeasurements;
    }
    
    // check if this side have any particular data
    // if all values are in default than we return false
    public boolean isSideHaveData(){
        if( this.K1 == 0.0 && this.K2 == 0.0 && this.AxisK1 == 0.0 && this.AL == 0.0 && this.Measurements.size() == 0){
            return false;
        }else{
            return true;
        }
    }
    
    public String printBiometrySide(){
       String output;
       output = "K1: "+this.K1+"\n";
       output += "K2: "+this.K2+"\n";
       output += "Axis K1: "+this.AxisK1+"\n";
       output += "Axial length: "+this.AL+"\n";
       output += "SNR: "+this.SNR+"\n";
       output += "\n";
       for(BiometryMeasurementData LensData: Measurements){
           output += LensData.printLenses();
       }
       //System.out.println("Predicted refraction: "+this.PredictedREF);
       return output;
    }
}
