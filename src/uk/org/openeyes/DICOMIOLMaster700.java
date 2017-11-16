/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDPage;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;

/**
 *
 * @author veta
 */
public class DICOMIOLMaster700 extends IOLMasterAbstract{
    private PDFFunctions PDFHelper;
    private List<PDPage> calculationPages = new ArrayList<PDPage>();
   
    /**
     *
     * @param mainParser
     */
    public DICOMIOLMaster700(DICOMParser mainParser) throws IOException
    {
        this.parser = mainParser;
        parser.Study.setDeviceType("IOLM700");
        this.PDFHelper = new PDFFunctions(parser.Study);
    }

    
    /**
     *
     * @param Attrs
     */
    public void collectData(Attributes Attrs){
        
        /* ---- IOLMaster 700 SQ tags ---
        *  IOL_Measured_Values sequence: 771Bxx30 (first page)
        *       >> Sequence of single axial length measurements: 771Bxx31
        *  Sequence of keratometry values:  771Bxx32  >> Sequence of single keratometry measurements 771Bxx33
        *  Sequence of anterior chamber depth values: 771Bxx34
        *  Sequence of white-to-white values measured for one eye: 771Bxx35 >> Sequence of single white-to-white measurements: 771Bxx3B
        *  Calculations are stored in an encapsulated PDF file stored in tag 00420011
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
        }else
        {            
            parser.debugMessage("Extracting IOL Measured Values from PDF");            
            try {
                collectMeasurementValuesPDF(Attrs);
            } catch (IOException ex) {
                Logger.getLogger(DICOMIOLMaster700.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(Attrs.contains(parser.getTagInteger("00420011"))){
            parser.debugMessage("Calculation sequence exists, extracting values");
            if(Attrs.contains(parser.getTagInteger("771B1030"))){
                setMinSnrForSides(Attrs);
            }
            //collectMeasuredValuesFromCalculation(Attrs);
            collectCalculationValues(Attrs);
        }else{
            parser.debugMessage("No basic measurement data found");
        }

        parser.Biometry.setSideData("L", parser.BiometryLeft);
        parser.Biometry.setSideData("R", parser.BiometryRight);

    }
    
    /**
     *
     * @param Attrs
     */
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
    
    private String getFormulaLensName(String fullText, String FormulaLens){
        Pattern p;
        if(FormulaLens.equals("F")){
            p = Pattern.compile("Formula: (.*)",Pattern.MULTILINE);
        }else{
            p = Pattern.compile("IOL: (.*)",Pattern.MULTILINE);
        }
        Matcher m = p.matcher( fullText );
        while( m.find() ) {
            return m.group(1);
        }
        return "";
    }
    
    private String testAconst(Pattern p, String fullText){
        Matcher m = p.matcher( fullText );
        while( m.find() ){
            return m.group(1);
        }
        return "";
    }
    
    private Double getAconstValue(String AconstTxt){
        Pattern p;
        String AconstValue = "";
        p = Pattern.compile("A-Const: (.*)",Pattern.MULTILINE);
        // SRK/T formula constant
        AconstValue = testAconst(p, AconstTxt);

        if(!AconstValue.equals("")){
            return Double.parseDouble(AconstValue);
        }else{
            p = Pattern.compile("A const.: (.*)",Pattern.MULTILINE);
            // SRK/T formula constant in software version 1.70.X
            AconstValue = testAconst(p, AconstTxt);
            
            if(!AconstValue.equals("")){
                return Double.parseDouble(AconstValue);
            }else{
                p = Pattern.compile("pACD: (.*)",Pattern.MULTILINE);    
                // Hoffer Q formula constant
                AconstValue = testAconst(p, AconstTxt);
                if(!AconstValue.equals("")){
                    return Double.parseDouble(AconstValue);
                }else{
                    p = Pattern.compile("A0: (.*)",Pattern.MULTILINE);
                    // Haigis formula constants
                    //!!!! TODO: need to work on Haigis here!!! As we have 3 constants and the text is 2 lines!!!
                    if(!AconstValue.equals("")){
                        return Double.parseDouble(AconstValue);
                    }else{
                        return 0.0;
                    }
                }
            }
        }
    }
    
    private String getEyeStatus(PDPage page, String side) throws IOException{
        Pattern p;
        String eyeStatus = PDFHelper.getEyeStatusIOLM700(page, side);
        p = Pattern.compile("LS: (.*); ",Pattern.MULTILINE);
        Matcher m = p.matcher( eyeStatus );
        while( m.find() ){ // should be always 1 match!
            return m.group(1);
        }
        return "Unknown";
    }
    
    private Double getTargetRefraction(PDPage page, String side) throws IOException{
        Pattern p;
        String targetRef = PDFHelper.getTargetRefractionIOLM700(page, side);

        p = Pattern.compile("Target ref.: (.*) D",Pattern.MULTILINE);
        Matcher m = p.matcher( targetRef );
        while( m.find() ){ // should be always 1 match!
            return Double.parseDouble(m.group(1));
        }
        return 0.0; // plano
    }
    
    private boolean checkCalculationResults(String FormulaName, String AconstTxt, BiometrySide sideData, int index){
        BiometryLensData lensData = new BiometryLensData();
        lensData.setAconstants(AconstTxt);
        return sideData.compareIOLREFvalues( parser.biometryHelper.getCalculatedValues(FormulaName, lensData, sideData), index );
    }
    
    public void collectMeasuredValues(Attributes Attrs, String side){
        setMinSnrForSides(Attrs);
        BiometrySide sideData;
        BiometrySide sideDataPDF = new BiometrySide();
        
        try {
            sideDataPDF = getMeasurementValuesPdfSide(Attrs, side);
        } catch (IOException ex) {
            Logger.getLogger(DICOMIOLMaster700.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(side.equals("L")){
            sideData = parser.BiometryLeft;
        }else{
            sideData = parser.BiometryRight;
        }
        
        if(parser.getDoubleValueFromSequence("771B1030","771B1043",side,Attrs) != null){
            sideData.setAL(parser.getDoubleValueFromSequence("771B1030","771B1043",side,Attrs));
        }else
        {
            parser.debugMessage("Extracting manual AL value from PDF");            
            sideData.setAL(sideDataPDF.getAL());
            sideData.setisALModified("YES");
        }

        
        
        if(parser.getDoubleValueFromSequence("771B1032","771B104A",side,Attrs) != null){
            sideData.setK1(parser.getDoubleValueFromSequence("771B1032","771B104A",side,Attrs));
        }else{
            parser.debugMessage("Extracting manual K1 value from PDF");            
            sideData.setK1(sideDataPDF.getK1());
            sideData.setisKModified("YES");
        }
        
        if(parser.getDoubleValueFromSequence("771B1032","771B104D",side,Attrs) != null){
            sideData.setK2(parser.getDoubleValueFromSequence("771B1032","771B104D",side,Attrs));
        }else{
            parser.debugMessage("Extracting manual K2 value from PDF"); 
            sideData.setK2(sideDataPDF.getK2());
            sideData.setisKModified("YES");
        }
        
        if(parser.getDoubleValueFromSequence("771B1032","771B104B",side,Attrs) != null){
            sideData.setAxisK1(parser.getDoubleValueFromSequence("771B1032","771B104B",side,Attrs));
        }else{
            parser.debugMessage("Extracting manual K1 axis value from PDF"); 
            sideData.setAxisK1(sideDataPDF.getAxisK1());
        }
        
        if(parser.getDoubleValueFromSequence("771B1032","771B104E",side,Attrs) != null){
            sideData.setAxisK2(parser.getDoubleValueFromSequence("771B1032","771B104E",side,Attrs));
        }else{
            parser.debugMessage("Extracting manual K2 axis value from PDF");
            sideData.setAxisK2(sideDataPDF.getAxisK2());
        }
        
        if(parser.getDoubleValueFromSequence("771B1034","771B100E",side,Attrs) != null){
            sideData.setACD(parser.getDoubleValueFromSequence("771B1034","771B100E",side,Attrs));
        }else{
            parser.debugMessage("Extracting manual ACD value from PDF");
            sideData.setACD(sideDataPDF.getACD());
            sideData.setisACDModified("YES");
        }
    }   
    
    private BiometrySide getMeasurementValuesPdfSide(Attributes Attrs, String side) throws IOException{
        BiometrySide sideData = new BiometrySide();
        
        if(calculationPages.isEmpty()){
            getCalculationPages(Attrs);
        }
        for(PDPage page : calculationPages){
            sideData.setK1(Double.parseDouble(PDFHelper.getK1Side(page, side)));
            sideData.setAxisK1(Double.parseDouble(PDFHelper.getAxisK1Side(page, side)));
            sideData.setK2(Double.parseDouble(PDFHelper.getK2Side(page, side)));
            sideData.setAxisK2(Double.parseDouble(PDFHelper.getAxisK2Side(page, side)));
            sideData.setAL(Double.parseDouble(PDFHelper.getALSide(page, side)));
            sideData.setACD(Double.parseDouble(PDFHelper.getACDSide(page, side)));
        }
        return sideData;
    }
    
    /**
     *
     * @param page
     * @param side
     * @throws IOException
     */
    private void collectMeasurementValuesPdfSide(PDPage page, String side) throws IOException{
        BiometrySide sideData;
        if(side.equals("L")){
            sideData = parser.BiometryLeft;
        }else{
            sideData = parser.BiometryRight;
        }
        if(PDFHelper.getPageTitleIOLM700(page).equals("IOL calculation") || PDFHelper.getPageTitleIOLM700(page).equals("IOL calculation (Multiformula)"))
        {
            sideData.setK1(Double.parseDouble(PDFHelper.getK1Side(page, side)));
            sideData.setAxisK1(Double.parseDouble(PDFHelper.getAxisK1Side(page, side)));
            sideData.setK2(Double.parseDouble(PDFHelper.getK2Side(page, side)));
            sideData.setAxisK2(Double.parseDouble(PDFHelper.getAxisK2Side(page, side)));
            sideData.setAL(Double.parseDouble(PDFHelper.getALSide(page, side)));
            sideData.setACD(Double.parseDouble(PDFHelper.getACDSide(page, side)));
            sideData.setisALModified("YES");
            sideData.setisACDModified("YES");
            sideData.setisKModified("YES");
        }
    }
    
    /**
     *
     * @param page
     * @param side
     * @throws IOException
     */
    private void collectCalculationValuesPDFSide(PDPage page, String side) throws IOException{
        String mainFormulaName = "";
        String mainLensName = "";
        String FormulaName = "";
        String LensName = "";
        String CalcType = "";
        BiometrySide sideData;
        boolean isPageCalculation = false;
        
        if(side.equals("L")){
            sideData = parser.BiometryLeft;
        }else{
            sideData = parser.BiometryRight;
        }
        
        if(PDFHelper.getPageTitleIOLM700(page).equals("IOL calculation")){
            // single formula multi lens    
            CalcType = "F";
            mainFormulaName = getFormulaLensName(PDFHelper.getTopLensFormulaNameIOLM700(page), CalcType);
            isPageCalculation = true;
        }else if(PDFHelper.getPageTitleIOLM700(page).equals("IOL calculation (Multiformula)")){
            // multi formula singe lens
            CalcType = "L";
            mainLensName = getFormulaLensName(PDFHelper.getTopLensFormulaNameIOLM700(page), CalcType);
            isPageCalculation = true;
        }
        
        if(isPageCalculation){
            System.out.println("Page is calculation");
            sideData.setTargetRef(getTargetRefraction(page, side));
            sideData.setEyeStatus(parser.biometryHelper.getEyeStatusFromSting(getEyeStatus(page, side)).toString());
            for(int pos=1; pos< 5; pos++){
                String FormulaLens = PDFHelper.getMultiLensFormulaNamesIOLM700(page, side, pos);
                if(FormulaLens.length() > 2){
                    if(CalcType.equals("F")){
                        LensName = FormulaLens;
                        FormulaName = mainFormulaName;
                    }else{
                        FormulaName = FormulaLens;
                        LensName = mainLensName;
                    }
                    sideData.addCalculations();
                    sideData.setFormulaName(FormulaName, sideData.getMeasurementsIndex() );
                    sideData.setLensName(LensName, sideData.getMeasurementsIndex() );
                    String AconstTxt = PDFHelper.getMultiLensAValuesIOLM700(page, side, pos);
                    sideData.setLensAConst( getAconstValue(AconstTxt), sideData.getMeasurementsIndex());
                    for(int calc=0; calc<5; calc++){
                        Double iolValue = PDFHelper.getIOLREFValueRowIOLM700(page, side, pos, "IOL", calc);
                        Double refValue = PDFHelper.getIOLREFValueRowIOLM700(page, side, pos, "REF", calc);

                        if (iolValue != null && refValue != null) {
                            sideData.setLensIOL(iolValue, sideData.getMeasurementsIndex());
                            sideData.setLensREF(refValue, sideData.getMeasurementsIndex());
                        }
                    }
                    
                    // we need to check the values by calculating using our methods
                    if(checkCalculationResults(FormulaName, AconstTxt, sideData, sideData.getMeasurementsIndex())){
                        System.out.println("Formula calculation check OK for side: "+side+" position: "+sideData.getMeasurementsIndex()+" formula: "+FormulaName+" lens: "+LensName);
                    }else{
                        System.out.println("Formula calculation check FAILED for side: "+side+" position: "+sideData.getMeasurementsIndex()+" formula: "+FormulaName+" lens: "+LensName);
                    }
                    
                }
            }
        }
        
        
    }
    
    private byte[] getPDFBinary(Attributes Attrs) throws IOException
    {
        byte[] pdfBytes = null;
        if(Attrs.contains(parser.getTagInteger("00420011"))){
            pdfBytes = Attrs.getBytes(parser.getTagInteger("00420011"));
        }
        return pdfBytes;
    }
    
    private void getCalculationPages(Attributes Attrs) throws IOException
    {
        PDPage currentPage;
        
        PDFHelper.setPDFDoc(this.getPDFBinary(Attrs));
        int maxPageNum = PDFHelper.getMaxPageNum();
                
        for(int p=0; p<maxPageNum; p++){
            currentPage = PDFHelper.getPDFPage(p);
            if(PDFHelper.getPageTitleIOLM700(currentPage).equals("IOL calculation") || PDFHelper.getPageTitleIOLM700(currentPage).equals("IOL calculation (Multiformula)")){
                calculationPages.add(currentPage);
            }
        }
    }
    
    public void collectMeasurementValuesPDF(Attributes Attrs) throws IOException{
        if(calculationPages.isEmpty()){
            getCalculationPages(Attrs);
        }
        
        for (PDPage currentPage : calculationPages) {
            collectMeasurementValuesPdfSide(currentPage, "L");
            collectMeasurementValuesPdfSide(currentPage, "R");
        }
    }
    
    /**
     *
     * @param Attrs
     */
    public void collectCalculationValues(Attributes Attrs){
        // we can extract the calculation values from the encapsulated PDF file if that's exist
        
        if(Attrs.contains(parser.getTagInteger("00420011"))){
            byte[] pdfBytes;
            String fileType;
            String fileName;
            PDPage currentPage;
                        
            try {
                pdfBytes = Attrs.getBytes(parser.getTagInteger("00420011"));
                fileType = Attrs.getString(parser.getTagInteger("00420012"));
                fileName = parser.inputFileName.substring(0, parser.inputFileName.length()-4);
                
                //TODO: to be able to set in configuration where to save PDF files and also to save PDF file or not
                // removed until we can configure it
                
                // parser.saveBinaryDataToFile(pdfBytes, fileType, fileName);
                
                // loop through pages and extract calculation from each page
                PDFHelper.setPDFDoc(pdfBytes);
                int maxPageNum = PDFHelper.getMaxPageNum();
                
                for(int p=0; p<maxPageNum; p++){
                    currentPage = PDFHelper.getPDFPage(p);
                    collectCalculationValuesPDFSide(currentPage, "L");
                    collectCalculationValuesPDFSide(currentPage, "R");
                }
                
            } catch (IOException ex) {
                Logger.getLogger(DICOMIOLMaster700.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
