/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

/**
 * A class for storing Biometry data values (top level).
 * Contains data for left and right sides.
 * 
 * @author vetusko
 */
public class BiometryData {
    private BiometrySide Left = new BiometrySide();
    private BiometrySide Right = new BiometrySide();
    
    /**
     * Attach a BiometrySide instance for one of the sides 
     * 
     * @param Side The name of the side, possible values: L / R
     * @param SideData The instance of a BiometrySide class to attach as side values
     */
    public void setSideData(String Side, BiometrySide SideData){
        if(Side.equals("L")){
            this.Left = SideData;
        }else{
            this.Right = SideData;
        }
    }
    
    /**
     *
     * @param Side
     * @return
     */
    public BiometrySide getBiometryValue(String Side){
        if(Side.equals("L")){
            return this.Left;
        }else{
            return this.Right;
        }
    }

    /**
     * Returns the integer value of the eye_id based on biometry data
     * 
     * @return Eye_id used in OE database
     */
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
    
    /**
     * A method to dump all values
     * 
     * @return A String containing all the Biometry data
     */
    public String printBiometryData(){
        String output;
        output = "--== Biometry data ==--\n";
        output += "-- Left side: \n";
        output += Left.printBiometrySide("Left");
        output += "-- Right side: \n";
        output += Right.printBiometrySide("Right");
        return output;
    }
}
