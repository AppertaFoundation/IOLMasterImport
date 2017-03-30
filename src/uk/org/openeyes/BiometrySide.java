/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private double TargetRef = 0;
    private BigDecimal SNR;
    private BigDecimal SNRMin = BigDecimal.ZERO;
    private boolean isALModified = false;
    private boolean isKModified = false;
    private boolean isACDModified = false;
    private ArrayList<BiometryCalculationData> Measurements = new ArrayList<>();

    /**
     *
     * @param BK1
     */
    public void setK1(Double BK1){
        this.K1 = BK1;
    }
    
    /**
     *
     * @return
     */
    public double getK1(){
        return this.K1;
    }
    
    /**
     *
     * @param BK2
     */
    public void setK2(Double BK2){
        this.K2 = BK2;
    }
    
    /**
     *
     * @return
     */
    public double getK2(){
        return this.K2;
    }

    /**
     *
     * @param BAxisK1
     */
    public void setAxisK1(Double BAxisK1){
        this.AxisK1 = BAxisK1;
    }
    
    /**
     *
     * @return
     */
    public double getAxisK1(){
        return this.AxisK1;
    }
    
    /**
     *
     * @param BAxisK2
     */
    public void setAxisK2(Double BAxisK2){
        this.AxisK2 = BAxisK2;
    }
    
    /**
     *
     * @return
     */
    public double getAxisK2(){
        return this.AxisK2;
    }
    
    /**
     *
     * @param DeltaK
     */
    public void setDeltaK(Double DeltaK){
        this.DeltaK = DeltaK;
    }
    
    /**
     *
     * @return
     */
    public double getDeltaK(){
        BigDecimal bd = new BigDecimal(Math.abs(this.K2-this.K1));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    /**
     *
     * @param TargetRef
     */
    public void setTargetRef(Double TargetRef){
        this.TargetRef = TargetRef;
    }
    
    /**
     *
     * @return
     */
    public double getTargetRef(){
        return this.TargetRef;
    }
    
    /**
     *
     * @param DeltaKAxis
     */
    public void setDeltaKAxis(Double DeltaKAxis){
        this.DeltaKAxis = DeltaKAxis;
    }
    
    /**
     *
     * @return
     */
    public double getDeltaKAxis(){
        double returnvalue = (this.K1 < this.K2) ? this.AxisK1 : this.AxisK2;
        return returnvalue;
    }

    /**
     *
     * @param RefractionSphere
     */
    public void setRefractionSphere(Double RefractionSphere){
        this.RefractionSphere = RefractionSphere;
    }
    
    /**
     *
     * @return
     */
    public double getRefractionSphere(){
        return this.RefractionSphere;
    }
    
    /**
     *
     * @param RefractionDelta
     */
    public void setRefractionDelta(Double RefractionDelta){
        this.RefractionDelta = RefractionDelta;
    }
    
    /**
     *
     * @return
     */
    public double getRefractionDelta(){
        return this.RefractionDelta;
    }

    /**
     *
     * @param RefractionAxis
     */
    public void setRefractionAxis(Double RefractionAxis){
        this.RefractionAxis = RefractionAxis;
    }
    
    /**
     *
     * @return
     */
    public double getRefractionAxis(){
        return this.RefractionAxis;
    }    

    /**
     *
     * @param EyeStatus
     */
    public void setEyeStatus(String EyeStatus){
        this.EyeStatus = Integer.parseInt(EyeStatus);
    }

    /**
     *
     * @return
     */
    public Integer getEyeStatus(){
        return this.EyeStatus;
    }

    /**
     *
     * @param BACD
     */
    public void setACD(Double BACD){
        this.ACD = BACD;
    }
    
    /**
     *
     * @return
     */
    public double getACD(){
        return this.ACD;
    }
    
    /**
     *
     * @param BAL
     */
    public void setAL(Double BAL){
        this.AL = BAL;
    }
    
    /**
     *
     * @return
     */
    public double getAL(){
        return this.AL;
    }

    /**
     *
     * @param BSNRMin
     */
    public void setSNRMin(Double BSNRMin){
        if(this.SNRMin == null || this.SNRMin.equals(BigDecimal.ZERO) || BigDecimal.valueOf(BSNRMin).compareTo(this.SNRMin) == -1 ){
            if(BSNRMin != 0.0){
                this.SNRMin = BigDecimal.valueOf(BSNRMin);
            }
        }
    }
    
    /**
     *
     * @return
     */
    public BigDecimal getSNRMin(){
        return this.SNRMin;
    }
    
    /**
     *
     * @param BSNR
     */
    public void setSNR(Double BSNR){
        this.SNR = BigDecimal.valueOf(BSNR);
    }
    
    /**
     *
     * @return
     */
    public BigDecimal getSNR(){
        return this.SNR;
    }
    
    // add new lens to the selected side

    /**
     *
     */
    public void addCalculations(){
        Measurements.add(new BiometryCalculationData());
    }
    
    /**
     *
     * @return
     */
    public Integer getMeasurementsIndex(){
        return Measurements.size()-1;
    }
    
    /**
     *
     * @param LName
     * @param LNum
     */
    public void setLensName(String LName, Integer LNum){
        Measurements.get(LNum).setLensName(LName);
    }
    
    /**
     *
     * @param LName
     * @param LNum
     */
    public void setFormulaName(String LName, Integer LNum){
        Measurements.get(LNum).setFormulaName(LName);
    }
    
    /**
     *
     * @param LAConst
     * @param LNum
     */
    public void setLensAConst(Double LAConst, Integer LNum){
        Measurements.get(LNum).setAConst(LAConst);
    }
    
    /**
     *
     * @param LEmmetropia
     * @param LNum
     */
    public void setLensEmmetropia(Double LEmmetropia, Integer LNum){
        Measurements.get(LNum).setEmmetropia(LEmmetropia);
    }
    
    /**
     *
     * @param LIOL
     * @param LNum
     */
    public void setLensIOL(Double LIOL, Integer LNum){
        Measurements.get(LNum).setIOL(LIOL);
    }
    
    /**
     *
     * @param LREF
     * @param LNum
     */
    public void setLensREF(Double LREF, Integer LNum){
        Measurements.get(LNum).setREF(LREF);
    }
    
    /**
     *
     * @return
     */
    public ArrayList<BiometryCalculationData> getMeasurements(){
        return this.Measurements;
    }
    
    /**
     *
     * @param LMeasurements
     */
    public void setMeasurements(ArrayList<BiometryCalculationData> LMeasurements){
        this.Measurements = LMeasurements;
    }
    
    /**
     *
     * @param isALModified
     */
    public void setisALModified(String isALModified){
        // this value is stored in the DICOM file as string 'YES'
        if(isALModified.equals("YES")){
            this.isALModified = true;
        }
    }
    
    /**
     *
     * @return
     */
    public boolean getisALModified(){
        return this.isALModified;
    }

    /**
     *
     * @param isKModified
     */
    public void setisKModified(String isKModified){
        // this value is stored in the DICOM file as string 'YES'
        if(isKModified.equals("YES")){
            this.isKModified = true;
        }
    }
    
    /**
     *
     * @return
     */
    public boolean getisKModified(){
        return this.isKModified;
    }
    
    /**
     *
     * @param isACDModified
     */
    public void setisACDModified(String isACDModified){
        // this value is stored in the DICOM file as string 'YES'
        if(isACDModified.equals("YES")){
            this.isACDModified = true;
        }
    }
    
    /**
     *
     * @return
     */
    public boolean getisACDModified(){
        return this.isACDModified;
    }
    
    
    // check if this side have any particular data
    // if all values are in default than we return false

    /**
     *
     * @return
     */
    public boolean isSideHaveData(){
        if( this.K1 == 0.0 && this.K2 == 0.0 && this.AxisK1 == 0.0 && this.AL == 0.0 && this.Measurements.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     *
     * @param controlData
     * @param index
     * @return
     */
    public boolean compareIOLREFvalues(BiometryCalculationData controlData, int index){
        return this.Measurements.get(index).compareMeasurementData(controlData);
    }
    
    /**
     *
     * @param sideName
     * @return
     */
    public String printBiometrySide(String sideName){
       String output;
       output = sideName+" K1: "+this.K1+"\n";
       output += sideName+" K2: "+this.K2+"\n";
       output += sideName+" Axis K1: "+this.AxisK1+"\n";
       output += sideName+" Axis K2: "+this.AxisK2+"\n";
       output += sideName+" Delta K: "+this.getDeltaK()+"\n";
       output += sideName+" Delta K Axis: "+this.getDeltaKAxis()+"\n";
       output += sideName+" Target refraction: "+this.TargetRef+"\n";
       output += sideName+" Refraction sphere: "+this.RefractionSphere+"\n";
       output += sideName+" Refraction delta: "+this.RefractionDelta+"\n";
       output += sideName+" Refraction axis: "+this.RefractionAxis+"\n";
       output += sideName+" Eye status: "+this.EyeStatus+"\n";
       output += sideName+" ACD: "+this.ACD+"\n";
       output += sideName+" Axial length: "+this.AL+"\n";
       output += sideName+" SNR: "+this.SNR+"\n";
       output += sideName+" SNR Min: "+this.SNRMin+"\n";
       output += sideName+" AL modified: "+this.isALModified+"\n";
       output += sideName+" K modified: "+this.isKModified+"\n";
       output += sideName+" ACD modified: "+this.isACDModified+"\n";
       output += "\n";
       for(BiometryCalculationData LensData: Measurements){
           output += LensData.printLensData();
       }
       //System.out.println("Predicted refraction: "+this.PredictedREF);
       return output;
    }
}
