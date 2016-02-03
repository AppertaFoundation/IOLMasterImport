/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

/**
 *
 * @author VEDELEKT
 */
public class BiometryData {
    private BiometrySide Left = new BiometrySide();
    private BiometrySide Right = new BiometrySide();
    
    public void setSideData(String Side, BiometrySide SideData){
        if(Side.equals("L")){
            this.Left = SideData;
        }else{
            this.Right = SideData;
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
