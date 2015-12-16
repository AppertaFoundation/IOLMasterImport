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
    private double AxisK2;
    private double DeltaK;
    private double DeltaKAxis;
    private double RefractionSphere = 0;
    private double RefractionDelta = 0;
    private double RefractionAxis = 0;
    private Integer EyeStatus = -1;
    private double AL;      // axial length
    private double ACD = 0;
    private BigDecimal SNR;
    private BigDecimal SNRMin = BigDecimal.ZERO;
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
    
    public void setAxisK2(String BAxisK2){
        this.AxisK2 = Double.parseDouble(BAxisK2);
    }
    
    public double getAxisK2(){
        return this.AxisK2;
    }
    
    public void setDeltaK(String DeltaK){
        this.DeltaK = Double.parseDouble(DeltaK);
    }
    
    public double getDeltaK(){
        return this.DeltaK;
    }
    
    public void setDeltaKAxis(String DeltaKAxis){
        this.DeltaKAxis = Double.parseDouble(DeltaKAxis);
    }
    
    public double getDeltaKAxis(){
        return this.DeltaKAxis;
    }

    public void setRefractionSphere(String RefractionSphere){
        if(this.RefractionSphere == 0){
            this.RefractionSphere = Double.parseDouble(RefractionSphere);
        }
    }
    
    public double getRefractionSphere(){
        return this.RefractionSphere;
    }
    
    public void setRefractionDelta(String RefractionDelta){
        if(this.RefractionDelta == 0){
            this.RefractionDelta = Double.parseDouble(RefractionDelta);
        }
    }
    
    public double getRefractionDelta(){
        return this.RefractionDelta;
    }

    public void setRefractionAxis(String RefractionAxis){
        if(this.RefractionAxis == 0){
            this.RefractionAxis = Double.parseDouble(RefractionAxis);
        }
    }
    
    public double getRefractionAxis(){
        return this.RefractionAxis;
    }    

    public void setEyeStatus(String EyeStatus){
        this.EyeStatus = Integer.parseInt(EyeStatus);
    }
    
    public Integer getEyeStatus(){
        return this.EyeStatus;
    }

    public void setACD(String BACD){
        //if(this.ACD == 0){
            this.ACD = Double.parseDouble(BACD);
        //}
    }
    
    public double getACD(){
        return this.ACD;
    }
    
    public void setAL(String BAL){
        this.AL = Double.parseDouble(BAL);
    }
    
    public double getAL(){
        return this.AL;
    }

    public void setSNRMin(String BSNRMin){
        if(this.SNRMin == null || this.SNRMin.equals(BigDecimal.ZERO) || BigDecimal.valueOf(Double.parseDouble(BSNRMin)).compareTo(this.SNRMin) == -1 ){
            this.SNRMin = BigDecimal.valueOf(Double.parseDouble(BSNRMin));
        }
    }
    
    public BigDecimal getSNRMin(){
        return this.SNRMin;
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
    
    public String printBiometrySide(String sideName){
       String output;
       output = sideName+" K1: "+this.K1+"\n";
       output += sideName+" K2: "+this.K2+"\n";
       output += sideName+" Axis K1: "+this.AxisK1+"\n";
       output += sideName+" Axis K2: "+this.AxisK2+"\n";
       output += sideName+" Delta K: "+this.DeltaK+"\n";
       output += sideName+" Delta K Axis: "+this.DeltaKAxis+"\n";
       output += sideName+" Refraction sphere: "+this.RefractionSphere+"\n";
       output += sideName+" Refraction delta: "+this.RefractionDelta+"\n";
       output += sideName+" Refraction axis: "+this.RefractionAxis+"\n";
       output += sideName+" Eye status: "+this.EyeStatus+"\n";
       output += sideName+" ACD: "+this.ACD+"\n";
       output += sideName+" Axial length: "+this.AL+"\n";
       output += sideName+" SNR: "+this.SNR+"\n";
       output += sideName+" SNR Min: "+this.SNRMin+"\n";
       output += "\n";
       for(BiometryMeasurementData LensData: Measurements){
           output += LensData.printLenses();
       }
       //System.out.println("Predicted refraction: "+this.PredictedREF);
       return output;
    }
}
