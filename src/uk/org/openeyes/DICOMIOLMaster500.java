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
 */
public class DICOMIOLMaster500 extends IOLMasterAbstract{
    
    public DICOMIOLMaster500(DICOMParser mainParser){
        this.parser = mainParser;
    }

    public void collectData(Attributes Attrs){
        
        /* ---- IOLMaster 500 SQ tags ---
        *  IOL_Measured_Values sequence: 771Bxx30 (first page)
        *       >> Sequence of single axial length measurements: 771Bxx31
        *  Sequence of keratometry values:  771Bxx32  >> Sequence of single keratometry measurements 771Bxx33
        *  Sequence of anterior chamber depth values: 771Bxx34
        *  Sequence of white-to-white values measured for one eye: 771Bxx35 >> Sequence of single white-to-white measurements: 771Bxx3B
        *  Sequence of standard formula IOL calculations for 4 different IOL types with a sequence of 7 calculations each, may contain up to 6 items: 771Bxx36
        *  Sequence of sandard formula IOL calculations for one eye: 771Bxx01 >> Container of measurement values used for calculation: 771Bxx02 
        *                                                         + >> Sequence of standard formula calculation results for 4 different IOL types: 771Bxx03
        *                                                                       >> Sequence of IOL calculation results for IOL as pair of lens power and residual refraction: 771Bxx05
        */
        
        // data priority: if we have calculation data we should extract all values from there
        //dumpDCMStructure(Attrs);
        
        if(Attrs.contains(parser.getTagInteger("771B1032"))){
            parser.debugMessage("IOL_Measured_Values sequence exists, extracting values");            
            // Axis K1, DeltaK and SNR values
            collectCommonMeasuredValues(Attrs, "L");
            collectCommonMeasuredValues(Attrs, "R");
            // K1, K2, Axis, ACD, Delta K, etc.
            collectMeasuredValues(Attrs, "L");
            collectMeasuredValues(Attrs, "R");
        }
        
        if(Attrs.contains(parser.getTagInteger("771B1036")) || Attrs.contains(parser.getTagInteger("771B1037")) || Attrs.contains(parser.getTagInteger("771B1001"))){
            parser.debugMessage("Calculation sequence exists, extracting values");
            if(Attrs.contains(parser.getTagInteger("771B1030"))){
                setMinSnrForSides(Attrs);
            }
            collectMeasuredValuesFromCalculation(Attrs);
            collectCalculationValues(Attrs);
        }else{
            parser.debugMessage("No basic measurement data found");
        }

        parser.Biometry.setSideData("L", parser.BiometryLeft);
        parser.Biometry.setSideData("R", parser.BiometryRight);

    }
    
    public void collectMeasuredValuesFromCalculation(Attributes Attrs){
        // the sequence structure is different, with different TAG numbers!!!
        String sequenceTag = selectSequenceTag(Attrs);
        
        Sequence CalcSeq = Attrs.getSequence(parser.getTagInteger(sequenceTag));
        if(CalcSeq != null && !CalcSeq.isEmpty()){
            if(sequenceTag.equals("771B1001")){
                for(int cs = 0; cs < CalcSeq.size(); cs++ ){
                    collectMeasuredValuesFromFormulaSeq(CalcSeq.get(cs), parser.getSideFromAttributes(CalcSeq.get(cs)));
                }
            }else{
                Attributes CalcAttrs = (Attributes) CalcSeq.get(0);
                Sequence FormulaSeq = CalcAttrs.getSequence(parser.getTagInteger("771B1001"));
                if(FormulaSeq != null && !FormulaSeq.isEmpty()){
                    for(int fs = 0; fs < FormulaSeq.size(); fs++ ){
                        collectMeasuredValuesFromFormulaSeq(FormulaSeq.get(fs), parser.getSideFromAttributes(FormulaSeq.get(fs)));
                    }
                }
            }
        }
    }
        
    public void collectCalculationValuesFromSeq(Attributes Attrs, String side, String inFormulaName, String inLensName){
        String FormulaName;
        String LensName;
        BiometrySide sideData;
        if(side.equals("L")){
            sideData = parser.BiometryLeft;
        }else{
            sideData = parser.BiometryRight;
        }
        //parser.debugMessage(":: "+side+"::"+inFormulaName+"::"+inLensName);
        parser.CurrentSide = parser.getSideFromAttributes(Attrs);
        if(side.equals(parser.CurrentSide)){
            Sequence CalcSeq = Attrs.getSequence(parser.getTagInteger("771B1003"));
            if(CalcSeq != null && !CalcSeq.isEmpty()){
                for(int cs=0; cs<CalcSeq.size(); cs++){
                    Attributes CalcAttrs = CalcSeq.get(cs);
                    if(inFormulaName.equals("")){
                        FormulaName = VR.PN.toStrings(CalcAttrs.getValue(parser.getTagInteger("771B1006")), true, parser.CharacterSet).toString();
                        LensName=inLensName;
                    }else{
                        LensName = VR.PN.toStrings(CalcAttrs.getValue(parser.getTagInteger("771B1006")), true, parser.CharacterSet).toString();
                        FormulaName = inFormulaName;
                    }
                    // we add new data set
                    sideData.addCalculations();
                    //parser.debugMessage("Index: "+sideData.getMeasurementsIndex());
                    sideData.setFormulaName(FormulaName, sideData.getMeasurementsIndex());
                    sideData.setLensName(LensName, sideData.getMeasurementsIndex());
                    sideData.setLensEmmetropia(VR.FD.toDouble(CalcAttrs.getValue(parser.getTagInteger("771B102B")), false, 0, 0), sideData.getMeasurementsIndex());
                    sideData.setLensAConst(VR.FD.toDouble(CalcAttrs.getValue(parser.getTagInteger("771B1007")), false, 0, 0), sideData.getMeasurementsIndex());
                    Sequence IOLCalcSeq = CalcAttrs.getSequence(parser.getTagInteger("771B1005"));
                    if(IOLCalcSeq != null && !IOLCalcSeq.isEmpty()){
                        for(int iols=0; iols<IOLCalcSeq.size(); iols++){
                            Attributes IOLCalcAttrs = IOLCalcSeq.get(iols);
                            sideData.setLensIOL(VR.FD.toDouble(IOLCalcAttrs.getValue(parser.getTagInteger("771B102A")), false, 0, 0), sideData.getMeasurementsIndex());
                            sideData.setLensREF(VR.FD.toDouble(IOLCalcAttrs.getValue(parser.getTagInteger("771B1028")), false, 0, 0), sideData.getMeasurementsIndex());
                        }
                    }
                }
            }
        }
        
    }
    
    public void collectCalculationValuesSequenceSide(Attributes Attrs, String side, String sequenceTag){
        String LensName = "";
        String FormulaName = "";

        if(!sequenceTag.equals("771B1001")){
            // single formula, multi lense
            // TODO similar solution required as for the side!!!
            parser.Study.setSurgeonName(parser.getStringValueFromSequence(sequenceTag,"771B102C","",Attrs));
            Sequence innerSeq = Attrs.getSequence(parser.getTagInteger(sequenceTag));
            if(innerSeq != null && !innerSeq.isEmpty()){
                for(int is=0; is<innerSeq.size();is++){
                    Attributes innerAttrs = innerSeq.get(is);
                    //dumpDCMStructure(innerAttrs);
                    FormulaName = VR.PN.toStrings(innerAttrs.getValue(parser.getTagInteger("771B1009")), true, parser.CharacterSet).toString();
                    Sequence CalcSeq = innerAttrs.getSequence(parser.getTagInteger("771B1001"));
                    if(CalcSeq != null && !CalcSeq.isEmpty()){
                        for(int cs=0; cs<CalcSeq.size(); cs++){
                            //dumpDCMStructure(CalcSeq.get(cs));
                            collectCalculationValuesFromSeq(CalcSeq.get(cs), side, FormulaName, LensName);
                        }
                    }   
                }
            }
        }else{
            // single lens multi formula
            LensName = VR.PN.toStrings(Attrs.getValue(parser.getTagInteger("771B100A")), true, parser.CharacterSet).toString();
            Sequence CalcSeq = Attrs.getSequence(parser.getTagInteger("771B1001"));
            if(CalcSeq != null && !CalcSeq.isEmpty()){
                for(int cs=0; cs<CalcSeq.size(); cs++){
                    //dumpDCMStructure(CalcSeq.get(cs));
                    collectCalculationValuesFromSeq(CalcSeq.get(cs), side, FormulaName, LensName);
                }
            }
        }


    }

    public void collectCalculationValues(Attributes Attrs){
        
        if(Attrs.contains(parser.getTagInteger("771B1036"))){
            parser.debugMessage("Collecting data from 771B1036");
            collectCalculationValuesSequenceSide(Attrs, "L", "771B1036");
            collectCalculationValuesSequenceSide(Attrs, "R", "771B1036");
        }
        if(Attrs.contains(parser.getTagInteger("771B1037"))){
            parser.debugMessage("Collecting data from 771B1037");
            collectCalculationValuesSequenceSide(Attrs, "L", "771B1037");
            collectCalculationValuesSequenceSide(Attrs, "R", "771B1037");            
        }
        if(Attrs.contains(parser.getTagInteger("771B1001"))){
            parser.debugMessage("Collecting data from 771B1001");
            collectCalculationValuesSequenceSide(Attrs, "L", "771B1001");
            collectCalculationValuesSequenceSide(Attrs, "R", "771B1001");
        }
    }
}
