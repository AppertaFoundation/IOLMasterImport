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

    /**
     * The name of the lens
     */
    protected String lensName;

    /**
     * The A constant used by SRK/T formula
     */
    protected double aConst;

    /**
     * The pACD constant used by HofferQ formula
     */
    protected double pACDConst;

    /**
     * The A0 constant used by Haigis suite formula
     */
    protected double A0;

    /**
     * The A1 constant used by Haigis suite formula
     */
    protected double A1;

    /**
     * The A2 constant used by Haigis suite formula
     */
    protected double A2;
    
     /**
     * Search for the specific pattern in the Aconstant string extracted from the PDF
     * 
     * @param AconstTxt The A constant string extracted from the PDF
     * @param p The pattern to search for in the A constant string
     */
    private double extractAconstFromString(String AconstTxt, Pattern p){
        Matcher m = p.matcher( AconstTxt );
        while( m.find() ){
            return Double.parseDouble(m.group(1));
        }
        return 0.0;
    }
    
    /**
     * Sets the A constants based on the input string. It's looking for matches:
     * A-Const: (.*) - SRK/T formula
     * pACD: (.*) - HofferQ formula
     * A0 (.*)  - Haigis
     * 
     * @param AconstTxt The input string extracted from the PDF file
     */
    public void setAconstants(String AconstTxt){
        Pattern p;
        String AconstValue = "";
        p = Pattern.compile("A-Const: (.*)",Pattern.MULTILINE);
        // SRK/T formula constant
        if(extractAconstFromString(AconstTxt, p) > 0.0){
            this.aConst = extractAconstFromString(AconstTxt, p);
        }else{
            p = Pattern.compile("A const.: (.*)",Pattern.MULTILINE);
            // SRK/T formula constant in software version 1.70.X
            if(extractAconstFromString(AconstTxt, p) > 0.0){
                this.aConst = extractAconstFromString(AconstTxt, p);
            }else{
                p = Pattern.compile("pACD: (.*)",Pattern.MULTILINE);    
                // Hoffer Q formula constant
                if(extractAconstFromString(AconstTxt, p) > 0.0){
                    this.pACDConst = extractAconstFromString(AconstTxt, p);
                }else{
                    p = Pattern.compile("A0: (.*)",Pattern.MULTILINE);
                    Matcher m = p.matcher( AconstTxt );
                    if(m.find()){
                        String[] aConstLines = AconstTxt.split("\n");
                        String[] aConsts = aConstLines[1].split("\\ ");
                        //System.out.println("AconstTxt: "+AconstTxt+" --- "+aConsts[0]+" - "+aConsts[1]+" - "+aConsts[2]);

                        // Haigis formula constants
                        this.A0 = Double.parseDouble(aConsts[0]);
                        this.A1 = Double.parseDouble(aConsts[1]);
                        this.A2 = Double.parseDouble(aConsts[2]);
                    }
                }
            }
        }
    }
    
    /**
     * Sets the lens name, and removing any new lines
     * 
     * @param name The name to set the variable to
     */
    public void setLensName(String name){
        this.lensName = name.replace("\n", " ");
    }
    
    /**
     * Returns the lens data as String
     */
    public String printLensData(){
        String output = "";
        output += "Lens name: "+this.lensName;
        output += "Aconst: "+this.aConst;
        output += "pACDconst: "+this.pACDConst;
        output += "A0: "+this.A0;
        output += "A1: "+this.A1;
        output += "A2: "+this.A2;
        return output;
    }
}
