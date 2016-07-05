/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import static uk.org.openeyes.DICOMParser.hexStringToByteArray;

/**
 *
 * @author veta
 */

public class DICOMHFAVF {
    
    private DICOMParser parser;
    
    public DICOMHFAVF(DICOMParser mainParser){
        this.parser = mainParser;
    }


    public void collectData(Attributes Attrs) throws IOException{
        
        if(Attrs.contains(parser.getTagInteger("00420011"))){
            byte[] pdfbytes = Attrs.getBytes(parser.getTagInteger("00420011"));
            
            FileOutputStream pdffos = new FileOutputStream("d:\\work\\wombex\\WombexUK\\AcrossHealth\\"+Attrs.getString(parser.getTagInteger("00420010"))+".pdf");
            pdffos.write(pdfbytes);
            pdffos.close();

            PDFParser parser = null;
            PDDocument pdDoc = null;
            COSDocument cosDoc = null;
            PDFTextStripper pdfStripper;
            String parsedText = "";
            
            RandomAccessRead pdfData = new RandomAccessBuffer(pdfbytes);
            try {
                parser = new PDFParser(pdfData);
                parser.parse();
                cosDoc = parser.getDocument();
                pdfStripper = new PDFTextStripper();
                pdDoc = new PDDocument(cosDoc);
                parsedText = pdfStripper.getText(pdDoc);
                System.out.println(parsedText);
                debugMessage(parsedText);
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
            
        }
        
        // this is just a test function for HFA files
        // 2 main groups: 7717 and 0301 can be extracted
        if(Attrs.contains(parser.getTagInteger("03010010"))){
            debugMessage("Extracting 0301 group...");
            debugMessage("Test Type: "+Attrs.getString(parser.getTagInteger("03011000")));
            debugMessage("Test strategy: "+Attrs.getString(parser.getTagInteger("03011001")));
            debugMessage("Test Pattern: "+Attrs.getString(parser.getTagInteger("03011002")));
            debugMessage("Screening Mode: "+Attrs.getString(parser.getTagInteger("03011003")));
            
            debugMessage("Stimulus Color: "+Attrs.getString(parser.getTagInteger("03011004")));
            debugMessage("Stimulus Size: "+Attrs.getString(parser.getTagInteger("03011005")));
            debugMessage("Blue Yellow: "+Attrs.getString(parser.getTagInteger("03011006")));
            debugMessage("PDB Version: "+Attrs.getString(parser.getTagInteger("03011007")));
            
            debugMessage("HFA Raw Data: ");
            byte[] rawbytes;
            
            try {
                rawbytes = Attrs.getBytes(parser.getTagInteger("03011008"));
                // TODO: move this part into a function, and this is a hack now!!!
                byte[] correctedBytes = new byte[rawbytes.length-2];
                int j =0;
                for(int i=0; i<rawbytes.length-1; i++){
                    if(!String.format("%02X",rawbytes[i]).equals("04")){
                        correctedBytes[j] = rawbytes[i];
                        j++;
                    }
                }

                FileOutputStream fos = new FileOutputStream("d:\\work\\wombex\\WombexUK\\AcrossHealth\\byte_test_new.xml");
                fos.write(correctedBytes);
                fos.close();
                
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder;
                Document doc = null;
                String encapsulatedBinaryData = "";
                try {
                    dBuilder = dbFactory.newDocumentBuilder();
                    try {
                       doc = dBuilder.parse(new ByteArrayInputStream(correctedBytes));
                       //new FileInputStream("c:\\work\\wombex\\WombexUK\\AcrossHealth\\byte_test.xml"));
                       NodeList binaryNodes;
                       binaryNodes = doc.getElementsByTagName("xio:hfa_II_serial_binhex");
                       if(binaryNodes.getLength() > 0){
                           encapsulatedBinaryData = binaryNodes.item(0).getTextContent();
                       }

                    } catch (SAXException ex) {
                        Logger.getLogger(DICOMParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(DICOMParser.class.getName()).log(Level.SEVERE, null, ex);
                }
              
                debugMessage(encapsulatedBinaryData);
                
                /*FileOutputStream decf = new FileOutputStream("c:\\work\\wombex\\WombexUK\\AcrossHealth\\decompressed.bin");
                String todecompress = encapsulatedBinaryData.substring(413,500);
                debugMessage(todecompress);
                decompressor.decompress(new ByteArrayInputStream(todecompress.getBytes()), decf);
                decf.close();
                */
                extractEncapsulatedBinData(encapsulatedBinaryData);
                             

            } catch (IOException ex) {
                Logger.getLogger(DICOMParser.class.getName()).log(Level.SEVERE, null, ex);
            }
                       
            debugMessage(Attrs.getString(parser.getTagInteger("03011008")));
            
        }else if(Attrs.contains(parser.getTagInteger("77170010"))){
            debugMessage("Extracting 7717 group...");
            debugMessage("Test name: "+Attrs.getString(parser.getTagInteger("77171001")));
            debugMessage("Test strategy: "+Attrs.getString(parser.getTagInteger("77171002")));
            debugMessage("Stimulus Size: "+Attrs.getString(parser.getTagInteger("77171003")));
            debugMessage("Stimulus Color: "+Attrs.getString(parser.getTagInteger("77171004")));
            
            debugMessage("Background State: "+Attrs.getString(parser.getTagInteger("77171005")));
            debugMessage("Foveal Result: "+Attrs.getString(parser.getTagInteger("77171006")));
            debugMessage("Screening Mode: "+Attrs.getString(parser.getTagInteger("77171007")));
            debugMessage("Fixation Trials: "+Attrs.getString(parser.getTagInteger("77171008")));
            debugMessage("Fixation Errors: "+Attrs.getString(parser.getTagInteger("77171009")));
       
            debugMessage("False Positive Percent: "+Attrs.getString(parser.getTagInteger("77171010")));
            debugMessage("False Positive Trials : "+Attrs.getString(parser.getTagInteger("77171011")));
            debugMessage("False Positive Errors: "+Attrs.getString(parser.getTagInteger("77171012")));
            debugMessage("False Negative Percent: "+Attrs.getString(parser.getTagInteger("77171013")));
            debugMessage("False Negative Trials : "+Attrs.getString(parser.getTagInteger("77171014")));
            debugMessage("False Negative Errors: "+Attrs.getString(parser.getTagInteger("77171015")));
            debugMessage("Mean Deviation: "+Attrs.getString(parser.getTagInteger("77171016")));
            debugMessage("Mean Deviation Probability: "+Attrs.getString(parser.getTagInteger("77171017")));
            debugMessage("Pattern Standard Deviation: "+Attrs.getString(parser.getTagInteger("77171018")));
            debugMessage("Pattern Standard Deviation Probability: "+Attrs.getString(parser.getTagInteger("77171019")));
            debugMessage("Short Term Fluctuation: "+Attrs.getString(parser.getTagInteger("77171020")));
            debugMessage("Corrected Pattern Standard Deviation: "+Attrs.getString(parser.getTagInteger("77171021")));
            debugMessage("Corrected Pattern Standard Deviation Probability: "+Attrs.getString(parser.getTagInteger("77171022")));
            debugMessage("Glaucoma Hemifield Test: "+Attrs.getString(parser.getTagInteger("77171023")));
            
            debugMessage("Fixation Monitor: "+Attrs.getString(parser.getTagInteger("77171024")));
            debugMessage("Fixation Target: "+Attrs.getString(parser.getTagInteger("77171025")));
            debugMessage("Pupil Diameter (in mm): "+Attrs.getString(parser.getTagInteger("77171026")));
            debugMessage("Sphere: "+Attrs.getString(parser.getTagInteger("77171027")));
            debugMessage("Cylinder: "+Attrs.getString(parser.getTagInteger("77171028")));
            debugMessage("Axis: "+Attrs.getString(parser.getTagInteger("77171029")));
            debugMessage("Visual Acuity: "+Attrs.getString(parser.getTagInteger("77171030")));
            debugMessage("Short Term Fluctuation Probabilit: "+Attrs.getString(parser.getTagInteger("77171031")));
            debugMessage("Visual Field Index: "+Attrs.getString(parser.getTagInteger("77171034")));
            debugMessage("VFM Sequence:");
            if(Attrs.contains(parser.getTagInteger("77171040"))){
                Sequence Seq = Attrs.getSequence(parser.getTagInteger("77171040"));
                for(int sq=0; sq<Seq.size();sq++){
                    Attributes AttrData = (Attributes) Seq.get(sq);
                    debugMessage("> Private creator: "+AttrData.getString(parser.getTagInteger("77170010")));
                    debugMessage(">> Section Number: "+AttrData.getString(parser.getTagInteger("77171041")));
                    debugMessage(">> Section Value: "+AttrData.getString(parser.getTagInteger("77171042")));
                }
            }            
        }
    }
    
    private void extractEncapsulatedBinData(String inputData) throws IOException, FileNotFoundException{
        byte[] dataBytes = hexStringToByteArray(inputData);
        FileOutputStream fos;
        fos = new FileOutputStream("d:\\work\\wombex\\WombexUK\\AcrossHealth\\encapsulated_bin_data.bin");
        fos.write(dataBytes);
        fos.close();
    }
    
    protected void debugMessage(String message){
        System.out.println(message);
    }
    
}
