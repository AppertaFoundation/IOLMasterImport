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
 *
 * @author VEDELEKT
 */
public class BiometryMeasurementData {
    private String LenseName;
    private String FormulaName;
    private Double AConst;
    private Double Emmetropia;
    private List<Double> IOL = new ArrayList<Double>();
    private List<Double> REF = new ArrayList<Double>();
    
   
    public void setLenseName(String LName){
        this.LenseName = LName;
    }
    
    public String getLenseName(){
        return this.LenseName;
    }
    
    public void setFormulaName(String LName){
        this.FormulaName = LName;
    }
    
    public String getFormulaName(){
        return this.FormulaName;
    }
    
    public void setAConst(String LAConst){
        this.AConst = Double.parseDouble(LAConst);
    }
    
    public Double getAConst(){
        return this.AConst;
    }
    
    public void setEmmetropia(String LEmmetropia){
        this.Emmetropia = Double.parseDouble(LEmmetropia);
    }
    
    public Double getEmmetropia(){
        return this.Emmetropia;
    }
    
    public void setIOL(String LIOL){
        this.IOL.add(Double.parseDouble(LIOL));
    }
    
    public List<Double> getIOL(){
        return this.IOL;
    }
    
    public void setREF(String LREF){
        this.REF.add(Double.parseDouble(LREF));
    }
    
    public List<Double> getREF(){
        return this.REF;
    }
    
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
    
    public String printLenses(){
        String output;
        output = "Lenses name: "+this.LenseName+"\n";
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
