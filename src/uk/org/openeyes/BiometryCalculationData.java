/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * A class for storing calculation values (IOL and Refraction values based on Lens and Formula)
 * 
 * @author vetusko
 */
public class BiometryCalculationData {
    private String LensName;
    private String FormulaName;
    private Double AConst;
    private Double Emmetropia = 0.0;
    private List<Double> IOL = new ArrayList<Double>();
    private List<Double> REF = new ArrayList<Double>();
    
    /**
     *
     * @param LName
     */
    public void setLensName(String LName){
        this.LensName = LName;
    }
    
    /**
     *
     * @return
     */
    public String getLensName(){
        return this.LensName;
    }
    
    /**
     *
     * @param LName
     */
    public void setFormulaName(String LName){
        this.FormulaName = LName;
    }
    
    /**
     *
     * @return
     */
    public String getFormulaName(){
        return this.FormulaName;
    }
    
    /**
     *
     * @param LAConst
     */
    public void setAConst(Double LAConst){
        this.AConst = LAConst;
    }
    
    /**
     *
     * @return
     */
    public Double getAConst(){
        return this.AConst;
    }
    
    /**
     *
     * @param LEmmetropia
     */
    public void setEmmetropia(Double LEmmetropia){
        this.Emmetropia = LEmmetropia;
    }
    
    /**
     *
     * @return
     */
    public Double getEmmetropia(){
        return this.Emmetropia;
    }
    
    /**
     *
     * @param LIOL
     */
    public void setIOL(Double LIOL){
        this.IOL.add(LIOL);
    }
    
    /**
     *
     * @return
     */
    public List<Double> getIOL(){
        return this.IOL;
    }
    
    /**
     *
     * @param LREF
     */
    public void setREF(Double LREF){
        this.REF.add(LREF);
    }
    
    /**
     *
     * @return
     */
    public List<Double> getREF(){
        return this.REF;
    }
    
    /**
     *
     * @return
     */
    public String getIOLREFJSON(){
        JSONObject exportJSON = new JSONObject();
        JSONArray dataIOL = new JSONArray();
        for( Double valueIOL : IOL){
            dataIOL.add(valueIOL);
        }
        exportJSON.put("IOL", dataIOL);
        JSONArray dataREF = new JSONArray();
        for( Double valueREF : REF){
            dataREF.add(valueREF);
        }
        exportJSON.put("REF", dataREF);
        return exportJSON.toJSONString();
    }
    
    /**
     *
     * @param toCompare
     * @return
     */
    public boolean compareMeasurementData(BiometryCalculationData toCompare){

        double tolerance = 0.02;
        //System.out.println("CALC:"+toCompare.getIOLREFJSON());
        //System.out.println("EXTR:"+this.getIOLREFJSON());
        List<Double> compareIOL = new ArrayList<Double>();
        List<Double> compareREF = new ArrayList<Double>();
        compareIOL = toCompare.getIOL();
        compareREF = toCompare.getREF();
        if(this.IOL.size() > 0 && compareIOL.size() == this.IOL.size()){
            for(int k=0; k<this.IOL.size(); k++){
                if(Math.abs(this.IOL.get(k) - compareIOL.get(k)) > tolerance){
                    return false;
                }
                if(Math.abs(this.REF.get(k) - compareREF.get(k)) > tolerance){
                    return false;
                }
            } 
            return true;      
        }
        return false;
    }
    
    /**
     *
     * @return
     */
    public String printLensData(){
        String output;
        output = "Lens name: "+this.LensName+"\n";
        output += "Formula name: "+this.FormulaName+"\n";
        output += "A constant: "+this.AConst+"\n";
        output += "Emmetropia: "+this.Emmetropia+"\n";
        int n = 0;
        for (Double iolValue : this.IOL) {
            output += n+". REF "+this.REF.get(n) + " - IOL "+n+": "+iolValue+"\n";
            n++;
	}
        return output;
    }
    
}
