/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author veta
 */
public class BiometryLensData {
    protected String lensName;
    protected double aConst;
    protected double pACDConst;
    protected double A0;
    protected double A1;
    protected double A2;
    
    private double extractAconstFromString(String AconstTxt, Pattern p){
        Matcher m = p.matcher( AconstTxt );
        while( m.find() ){
            return Double.parseDouble(m.group(1));
        }
        return 0.0;
    }
    
    public void setAconstants(String AconstTxt){
        Pattern p;
        String AconstValue = "";
        p = Pattern.compile("A-Const: (.*)",Pattern.MULTILINE);
        // SRK/T formula constant
        if(extractAconstFromString(AconstTxt, p) > 0.0){
            this.aConst = extractAconstFromString(AconstTxt, p);
        }else{
            p = Pattern.compile("pACD: (.*)",Pattern.MULTILINE);    
            // Hoffer Q formula constant
            if(extractAconstFromString(AconstTxt, p) > 0.0){
                this.pACDConst = extractAconstFromString(AconstTxt, p);
            }else{
                String[] aConstLines = AconstTxt.split("\n");
                String[] aConsts = aConstLines[1].split("\\ ");
                //System.out.println(AconstTxt+" --- "+aConsts[0]+" - "+aConsts[1]+" - "+aConsts[2]);
                
                // Haigis formula constants
                this.A0 = Double.parseDouble(aConsts[0]);
                this.A1 = Double.parseDouble(aConsts[1]);
                this.A2 = Double.parseDouble(aConsts[2]);
            }
        }
    }
    
    public void printLensData(){
        System.out.println("Aconst: "+this.aConst);
        System.out.println("pACDconst: "+this.pACDConst);
        System.out.println("A0: "+this.A0);
        System.out.println("A1: "+this.A1);
        System.out.println("A2: "+this.A2);
    }
}
