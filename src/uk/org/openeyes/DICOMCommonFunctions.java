/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.SpecificCharacterSet;
import org.dcm4che3.data.VR;
import org.dcm4che3.util.TagUtils;

/**
 *
 * @author veta
 */
public class DICOMCommonFunctions {
    /**
     * Debug: based on command line argument -d, if it's passed to the JAVA it will run in debug mode
     */
    public boolean debug = true;
    
    /**
     *
     */
    protected SpecificCharacterSet CharacterSet = SpecificCharacterSet.DEFAULT;

    /**
     *
     */
    protected String CurrentSide = "R";

    /**
     *
     */
    protected DICOMLogger logger;
    
    /**
     *
     */
    protected String inputFileName;
    
    /**
     *
     * @param tag
     * @return
     */
    public Integer getTagInteger(String tag){
        return Integer.decode("0x"+tag);
    }

   private String getSideFromSequence(Sequence inputSequence){
        if(!inputSequence.isEmpty()){
            for(int i = 0; i<inputSequence.size(); i++){
                Attributes memberAttr = (Attributes) inputSequence.get(i);
                return getSideFromAttributes(memberAttr);
            }
        }
        return "";
    }
    
    /**
     *
     * @param inputAttrs
     * @return
     */
    protected String getSideFromAttributes(Attributes inputAttrs){
        int[] sequenceTags;
        sequenceTags = inputAttrs.tags();
        for( int tag : sequenceTags){
            if( TagUtils.toHexString(TagUtils.elementNumber(tag)).matches("(?i).*08")){
                return VR.CS.toStrings(inputAttrs.getValue(tag), false, CharacterSet).toString();
            }
        }
        return "";
    }
    
    /**
     *
     * @param hexTagSequence
     * @param hexTagValue
     * @param side
     * @param Attrs
     * @return
     */
    protected Double getDoubleValueFromSequence(String hexTagSequence, String hexTagValue, String side, Attributes Attrs){
        if(Attrs.contains(getTagInteger(hexTagSequence))){
            Sequence Seq = Attrs.getSequence(getTagInteger(hexTagSequence));
            for(int ks=0; ks<Seq.size();ks++){
                Attributes AttrData = (Attributes) Seq.get(ks);
                CurrentSide = getSideFromAttributes(AttrData);
                if(CurrentSide == null || CurrentSide.equals("")){
                    CurrentSide = getSideFromAttributes(Attrs);
                }
                if(CurrentSide.equals(side) && AttrData.getValue(getTagInteger(hexTagValue)) != null){
                    return VR.FD.toDouble(AttrData.getValue(getTagInteger(hexTagValue)), false, 0, 0);
                }
            }
        }
        return 0.0;
    }
    
    /**
     *
     * @param hexTagSequence
     * @param hexTagValue
     * @param side
     * @param Attrs
     * @return
     */
    protected String getStringValueFromSequence(String hexTagSequence, String hexTagValue, String side, Attributes Attrs){
        Sequence Seq = Attrs.getSequence(getTagInteger(hexTagSequence));
        for(int ks=0; ks<Seq.size();ks++){
            Attributes AttrData = (Attributes) Seq.get(ks);
            if(!side.equals("")){
                CurrentSide = getSideFromAttributes(AttrData);
                if(CurrentSide == null || CurrentSide.equals("")){
                    CurrentSide = getSideFromAttributes(Attrs);
                }
                if(CurrentSide.equals(side)){                
                    return VR.PN.toStrings(AttrData.getValue(getTagInteger(hexTagValue)), true, CharacterSet).toString();
                }
            }else{
                return VR.PN.toStrings(AttrData.getValue(getTagInteger(hexTagValue)), true, CharacterSet).toString();
            }
        }
        return "";
    }    
    
    /**
     *
     * @param message
     */
    protected void debugMessage(String message){
        if(this.debug){
            logger.addToRawOutput(message);
            //System.out.println(message);
        }
    } 
    
    /**
     *
     * @param fileType
     * @return
     */
    protected String getFileExtension(String fileType){
       
        if(fileType.equals("application/pdf")){
            return "pdf";
        }else if (fileType.equals("application/jpeg")){
            return "jpg";
        }else{
            return "bin";
        }
    }
    
    /**
     *
     * @param binaryData
     * @param fileType
     * @param fileName
     * @return
     */
    protected boolean saveBinaryDataToFile(byte[] binaryData, String fileType, String fileName){
       
        try {
            FileOutputStream binfos = new FileOutputStream(fileName+"."+getFileExtension(fileType));
            binfos.write(binaryData);
            binfos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DICOMCommonFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DICOMCommonFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return false;
    }
    
    /**
     *
     * @param binData
     * @return
     */
    protected String parsePDFData(byte[] binData){
            
        PDFParser parser = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;
        String parsedText = "";

        RandomAccessRead pdfData = new RandomAccessBuffer(binData);
        try {
            parser = new PDFParser(pdfData);
            parser.parse();
            //cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = parser.getPDDocument();
            if(debug){
                PDFFunctions PDFHelper = new PDFFunctions();
                debugMessage("<<<<<<< PDFDATA >>>>>>>>\n");
                //PDFHelper.dumpPDFStructure(pdDoc);
                PDFHelper.dumpPDFContent(pdDoc);
                debugMessage("<<<<<<< /PDFDATA >>>>>>>>\n");
            }
            parsedText = pdfStripper.getText(pdDoc);

            //debugMessage("<<<<<<< PDFDATA >>>>>>>>\n"+parsedText+"<<<<<<< /PDFDATA >>>>>>>>\n");            
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                e.printStackTrace();
            }

        }
        return parsedText;
    }
    
    /**
     *
     * @param fileName
     * @return
     */
    protected String parsePDFDataFromFile(String fileName){
              
        Path path = Paths.get(fileName);
        byte[] binData;
        try {
            binData = Files.readAllBytes(path);
            return parsePDFData(binData);
        } catch (IOException ex) {
            Logger.getLogger(DICOMCommonFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "File not found";
    }
}
