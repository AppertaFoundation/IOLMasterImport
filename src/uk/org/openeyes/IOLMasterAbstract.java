/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.VR;

/**
 *
 * @author veta
 * Interface for all IOLMaster related import classes
 */

abstract class IOLMasterAbstract {
    
    public DICOMParser parser;
       
    public abstract void collectData(Attributes Attrs);
    
    public void collectCommonMeasuredValues(Attributes Attrs, String side){
        BiometrySide sideData;
        if(side.equals("L")){
            sideData = parser.BiometryLeft;
        }else{
            sideData = parser.BiometryRight;
        }
        if(parser.getDoubleValueFromSequence("771B1032","771B104B",side,Attrs) != null){
            sideData.setAxisK1(parser.getDoubleValueFromSequence("771B1032","771B104B",side,Attrs));
            //sideData.setDeltaKAxis(parser.getDoubleValueFromSequence("771B1032","771B104B",side,Attrs));
            //sideData.setDeltaK(parser.getDoubleValueFromSequence("771B1032","771B104F",side,Attrs));
        }else{
            sideData.setAxisK1(0.0);
        }
        
        // check for SNR
        if((parser.getDoubleValueFromSequence("771B1030","771B1044",side,Attrs) != null)){
            sideData.setSNR(parser.getDoubleValueFromSequence("771B1030","771B1044",side,Attrs));
        }else{
            sideData.setSNR(0.0);
        }
    }
    
    public void collectMeasuredValuesFromFormulaSeq(Attributes Attrs, String side){
        BiometrySide sideData;
        if(side.equals("L")){
            sideData = parser.BiometryLeft;
        }else{
            sideData = parser.BiometryRight;
        }
        sideData.setisALModified(parser.getStringValueFromSequence("771B1002","771B1045",side,Attrs));
        sideData.setisKModified(parser.getStringValueFromSequence("771B1002","771B1046",side,Attrs));
        sideData.setisACDModified(parser.getStringValueFromSequence("771B1002","771B1048",side,Attrs));
        
        if(sideData.getisALModified()){
            sideData.setAL(parser.getDoubleValueFromSequence("771B1002","771B100B",side,Attrs));
        }
        if(sideData.getisACDModified()){
            sideData.setACD(parser.getDoubleValueFromSequence("771B1002","771B1026",side,Attrs));
        }
        if(sideData.getisKModified()){     
            sideData.setK1(parser.getDoubleValueFromSequence("771B1002","771B1020",side,Attrs));
            sideData.setK2(parser.getDoubleValueFromSequence("771B1002","771B1021",side,Attrs));
            sideData.setAxisK1(parser.getDoubleValueFromSequence("771B1002","771B1014",side,Attrs));
            sideData.setAxisK2(parser.getDoubleValueFromSequence("771B1002","771B1013",side,Attrs));
        }
        sideData.setRefractionSphere(parser.getDoubleValueFromSequence("771B1002","771B1040",side,Attrs));
        sideData.setRefractionDelta(parser.getDoubleValueFromSequence("771B1002","771B1041",side,Attrs));
        sideData.setRefractionAxis(parser.getDoubleValueFromSequence("771B1002","771B1042",side,Attrs));
        sideData.setTargetRef(parser.getDoubleValueFromSequence("771B1002","771B1029",side,Attrs));
        sideData.setEyeStatus(parser.getStringValueFromSequence("771B1002","771B1025",side,Attrs));
    }
    
    public String selectSequenceTag(Attributes Attrs){
        String sequenceTag = "";
        if(Attrs.contains(parser.getTagInteger("771B1001"))){
            sequenceTag = "771B1001";
        }else if(Attrs.contains(parser.getTagInteger("771B1036"))){
            sequenceTag = "771B1036";
        }else if(Attrs.contains(parser.getTagInteger("771B1037"))){
            sequenceTag = "771B1037";
        }
        return sequenceTag;
    }
        
    public void collectMeasuredValues(Attributes Attrs, String side){
        setMinSnrForSides(Attrs);
        BiometrySide sideData;
        
        if(side.equals("L")){
            sideData = parser.BiometryLeft;
        }else{
            sideData = parser.BiometryRight;
        }
        sideData.setAL(parser.getDoubleValueFromSequence("771B1030","771B1043",side,Attrs));
        sideData.setK1(parser.getDoubleValueFromSequence("771B1032","771B104A",side,Attrs));
        sideData.setK2(parser.getDoubleValueFromSequence("771B1032","771B104D",side,Attrs));
        sideData.setAxisK2(parser.getDoubleValueFromSequence("771B1032","771B104E",side,Attrs));
        sideData.setACD(parser.getDoubleValueFromSequence("771B1034","771B100E",side,Attrs));
      
    }   
        
    public void setMinSnrForSides(Attributes Attrs){
        if(Attrs.contains(parser.getTagInteger("771B1030"))){
            Sequence basicMeasurement = Attrs.getSequence(parser.getTagInteger("771B1030"));
            for(int bm =0; bm<basicMeasurement.size(); bm++){
                Attributes basicMeasurementData = (Attributes) basicMeasurement.get(bm);
                // AL mean value: 771B1043
                // SNR mean value: 771B1044
                parser.CurrentSide = parser.getSideFromAttributes(basicMeasurementData);

                // Sequence of single axial length measurements, we need to extract minSNR from here!
                Sequence ALSeq = basicMeasurementData.getSequence(parser.getTagInteger("771B1031"));
                for(int as=0; as<ALSeq.size();as++){
                    Attributes ALData = (Attributes) ALSeq.get(as);
                    //parser.debugMessage(parser.CurrentSide+" Measured SNR (SNRMin): "+VR.FD.toDouble(ALData.getValue(parser.getTagInteger("771B100C")), false, 0, 0));
                    if(parser.CurrentSide.equals("L")){
                        parser.BiometryLeft.setSNRMin(VR.FD.toDouble(ALData.getValue(parser.getTagInteger("771B100C")), false, 0, 0));
                    }else{
                        parser.BiometryRight.setSNRMin(VR.FD.toDouble(ALData.getValue(parser.getTagInteger("771B100C")), false, 0, 0));
                    }
                }
            }
        }
    }
    
    public abstract void collectMeasuredValuesFromCalculation(Attributes Attrs);
    
    public abstract void collectCalculationValues(Attributes Attrs);
    
}
