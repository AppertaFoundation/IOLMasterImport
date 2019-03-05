/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

/**
 *
 * @author veta
 */
public class PDFFunctions extends PDFTextStripper{
    private Map<String, Coordinates> CoordMap = new HashMap<String, Coordinates>();
    private PDDocument PDFDoc = null;
    private StudyData Study;
    private int rowHeight = 9;
    private int blockHeight = 9;
    private int rowDiff = 0;
    
    /**
     * Instantiate a new PDFTextStripper object.
     *
     * @throws IOException If there is an error loading the properties.
     */
    public PDFFunctions(StudyData Study) throws IOException
    {
        this.Study = Study;
        addDataCoordinates();
    }   
    
    /**
     *
     * @param pdfBin
     */
    public void setPDFDoc(byte[] pdfBin){
        RandomAccessRead pdfData = new RandomAccessBuffer(pdfBin);
        PDFParser myPDFparser;
        PDFTextStripper pdfStripper;

        try {
            myPDFparser = new PDFParser(pdfData);
            myPDFparser.parse();
            
            pdfStripper = new PDFTextStripper();
            this.PDFDoc = myPDFparser.getPDDocument();
        } catch (IOException ex) {
            Logger.getLogger(PDFFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     *
     * @return
     */
    public int getMaxPageNum(){
        if(! this.PDFDoc.equals(null)){
            return PDFDoc.getNumberOfPages();
        }else{
            return -1;
        }
    }
    
    /**
     *
     * @param PDFDoc
     * @throws IOException
     */
    public void dumpPDFStructure(PDDocument PDFDoc) throws IOException{
        //PDFTextStripper stripper = new PDFFunctions(parser.Study);
	this.setSortByPosition( true );
	this.setStartPage( 0 );
	this.setEndPage( PDFDoc.getNumberOfPages() );
        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
        this.writeText(PDFDoc, dummy);
        
    }
    
    /**
     *
     * @param pageNum
     * @return
     */
    public PDPage getPDFPage(int pageNum){
        PDPage outPage = new PDPage();
        PDPageTree allPages;
        
        if(!PDFDoc.equals(null)){
            allPages = this.PDFDoc.getDocumentCatalog().getPages();
            outPage = allPages.get(pageNum);
        }
        return outPage;
    }
    
    /**
     *
     * @param PDFDoc
     * @throws IOException
     */
    public void dumpPDFContent(PDDocument PDFDoc) throws IOException{
        PDPageTree allPages;
        allPages = PDFDoc.getDocumentCatalog().getPages();
        
        PDPage currentPage;
        Iterator pages = allPages.iterator();
        while(pages.hasNext()){
            currentPage = (PDPage)pages.next();
            String pageTitle = getPageTitleIOLM700(currentPage);
            if(pageTitle.equals("IOL calculation") || pageTitle.equals("IOL calculation (Multiformula)")){
                System.out.println("Extracting calculation values");
                System.out.println(getTopLensFormulaNameIOLM700(currentPage));
                for(int i = 1;i<=4;i++){
                   System.out.println("Right "+i);
                   System.out.println(getMultiLensFormulaNamesIOLM700(currentPage, "R", i));
                   System.out.println(getMultiLensAValuesIOLM700(currentPage, "R", i));
                   System.out.println(dumpIOLREFValuesIOLM700(currentPage, "R", i));
                   System.out.println("Left "+i);
                   System.out.println(getMultiLensFormulaNamesIOLM700(currentPage, "L", i));
                   System.out.println(getMultiLensAValuesIOLM700(currentPage, "L", i));
                   System.out.println(dumpIOLREFValuesIOLM700(currentPage, "L", i));
                }
            }
        }        
    }
       
    /**
     *
     * @param page
     * @return
     * @throws IOException
     */
    public String getPageTitleIOLM700(PDPage page) throws IOException{
        String result = getTextArea(page, new Rectangle(getDataCoordinates("PageTitleIOLM700", "T", 1).x,getDataCoordinates("PageTitleIOLM700", "T", 1).y, 250,10)).trim();
        //System.out.println("PageTitle: "+result);
        return result;
    }
    
    /**
     *
     * @param page
     * @return
     * @throws IOException
     */
    public String getTopLensFormulaNameIOLM700(PDPage page)throws IOException{
        String result = normaliseLensName(getTextArea(page, new Rectangle(getDataCoordinates("TopLensFormulaNameIOLM700", "T", 1).x,getDataCoordinates("TopLensFormulaNameIOLM700", "T", 1).y,300,10)));
        //System.out.println("TopLensFormulaName: "+result);
        return result;
    }
    
    /**
     *
     * @param page
     * @param side
     * @return
     * @throws IOException
     */
    public String getTargetRefractionIOLM700(PDPage page, String side) throws IOException{
        Rectangle area = new Rectangle(getDataCoordinates("TargetRefractionIOLM700", side, 1).x, getDataCoordinates("TargetRefractionIOLM700", side, 1).y, 110, 10);
        String result = getTextArea(page, area).trim();
        //System.out.println("TargetRefraction: "+side+" "+result);
        return result;
    }

    /**
     *
     * @param page
     * @param side
     * @return
     * @throws IOException
     */
    public String getLVCModeIOLM700(PDPage page, String side) throws IOException{
        Rectangle area = new Rectangle(getDataCoordinates("LVCModeIOLM700", side, 1).x, getDataCoordinates("LVCModeIOLM700", side, 1).y, 200, 10);
        String result = getTextArea(page, area).trim();
        //System.out.println("LVCMode: "+side+" "+result);
        return result;
    }
    
    /**
     *
     * @param page
     * @param side
     * @return
     * @throws IOException
     */
    public String getEyeStatusIOLM700(PDPage page, String side) throws IOException{
        Rectangle area = new Rectangle(getDataCoordinates("EyeStatusIOLM700", side, 1).x, getDataCoordinates("EyeStatusIOLM700", side, 1).y, 200, 10);
        String result = getTextArea(page, area).trim();
        //System.out.println("EyeStatus: "+side+" "+result);
        return result;
    }
    
    /**
     *
     * @param page
     * @param side
     * @return
     * @throws IOException
     */
    public String getK1Side(PDPage page, String side)throws IOException{
        Rectangle area = new Rectangle(getDataCoordinates("K1IOLM700", side, 1).x, getDataCoordinates("K1IOLM700", side, 1).y, 27, 10);
        String result = getTextArea(page, area).trim();
        //System.out.println("K1Side: "+side+" "+result);
        return result.replace("---","0");
    }
    
    /**
     *
     * @param page
     * @param side
     * @return
     * @throws IOException
     */
    public String getAxisK1Side(PDPage page, String side)throws IOException{
        Rectangle area = new Rectangle(getDataCoordinates("AxisK1IOLM700", side, 1).x, getDataCoordinates("AxisK1IOLM700", side, 1).y, 19, 10);
        String result = getTextArea(page, area).trim();
        //System.out.println("AxisK1Side: "+side+" "+result);
        result = result.replace("º","").replace("°","").trim().replace("---","0");
        return ("".equals(result) ? "0" : result);
    }
    
    /**
     *
     * @param page
     * @param side
     * @return
     * @throws IOException
     */
    public String getK2Side(PDPage page, String side)throws IOException{
        Rectangle area = new Rectangle(getDataCoordinates("K2IOLM700", side, 1).x, getDataCoordinates("K2IOLM700", side, 1).y, 27, 10);
        String result = getTextArea(page, area).trim();
        //System.out.println("K2Side: "+side+" "+result);
        return result.replace("---","0");
    }
    
    /**
     *
     * @param page
     * @param side
     * @return
     * @throws IOException
     */
    public String getAxisK2Side(PDPage page, String side)throws IOException{
        Rectangle area = new Rectangle(getDataCoordinates("AxisK2IOLM700", side, 1).x, getDataCoordinates("AxisK2IOLM700", side, 1).y, 19, 10);
        String result = getTextArea(page, area).trim();
        //System.out.println("AxisK2Side: "+side+" "+result);
        result = result.replace("º","").replace("°","").trim().replace("---","0");
        return ("".equals(result) ? "0" : result);
    }
    
    /**
     *
     * @param page
     * @param side
     * @return
     * @throws IOException
     */
    public String getALSide(PDPage page, String side)throws IOException{
        Rectangle area = new Rectangle(getDataCoordinates("ALIOLM700", side, 1).x, getDataCoordinates("ALIOLM700", side, 1).y, 25, 10);
        String result = getTextArea(page, area).trim();
        //System.out.println("ALSide: "+side+" "+result);
        return result.replace("---","0");
    }
    
    /**
     *
     * @param page
     * @param side
     * @return
     * @throws IOException
     */
    public String getACDSide(PDPage page, String side)throws IOException{
        Rectangle area = new Rectangle(getDataCoordinates("ACDIOLM700", side, 1).x, getDataCoordinates("ACDIOLM700", side, 1).y, 25, 10);
        String result = getTextArea(page, area).trim();
        //System.out.println("ACDSide: "+side+" "+result);
        return result.replace("---","0");
    }
    
    public boolean checkMultiLensIOLTitle(PDPage page, String side, int position) throws IOException{
        Coordinates coord = getDataCoordinates("MultiLensIOLTitleIOLM700", side, position);
        String IOLTitle = getTextArea(page, new Rectangle(coord.x, coord.y, 50, 9));
        //System.out.println("IOL TITLE: |"+IOLTitle+"|");
        if(IOLTitle.trim().equals("IOL (D)")){
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param page
     * @param side
     * @param position
     * @return
     * @throws IOException
     */
    public String getMultiLensFormulaNamesIOLM700(PDPage page, String side, int position) throws IOException{
        return normaliseLensName(getTextArea(page, getRectangleMultiLensFormulaNamesIOLM700(side, position)));
    }
    
    private Rectangle getRectangleMultiLensFormulaNamesIOLM700(String side, int position){
        Coordinates coord = getDataCoordinates("MultiLensFormulaNamesIOLM700", side, position);
        return new Rectangle(coord.x, coord.y, 120, 30);
    }

    public String getMultiLensNamesIOLM700(PDPage page, String side, int position) throws IOException{
        return normaliseLensName(getTextArea(page, getRectangleMultiLensNamesIOLM700(side, position)));
    }

    private Rectangle getRectangleMultiLensNamesIOLM700(String side, int position){
        Coordinates coord = getDataCoordinates("MultiLensNamesIOLM700", side, position);
        return new Rectangle(coord.x, coord.y, 115, 21);
    }

    public String getMultiFormulaNamesIOLM700(PDPage page, String side, int position) throws IOException{
        return normaliseFormulaName(getTextArea(page, getRectangleMultiFormulaNamesIOLM700(side, position)));
    }

    private Rectangle getRectangleMultiFormulaNamesIOLM700(String side, int position){
        Coordinates coord = getDataCoordinates("MultiFormulaNamesIOLM700", side, position);
        return new Rectangle(coord.x, coord.y, 115, 8);
    }
    
    /**
     *
     * @param page
     * @param side
     * @param position
     * @return
     * @throws IOException
     */
    public String getMultiLensAValuesIOLM700(PDPage page, String side, int position) throws IOException{
        //System.out.println("MultiLensAValuesIOLM700 "+side+"_"+position+":: "+getTextArea(page, getRectangleMultiLensAValuesIOLM700(side, position)));
        return getTextArea(page, getRectangleMultiLensAValuesIOLM700(side, position));
    }
    
    private Rectangle getRectangleMultiLensAValuesIOLM700(String side, int position){
        Coordinates coord = getDataCoordinates("MultiLensAValuesIOLM700", side, position);
         return new Rectangle(coord.x, coord.y, 120, 20);
    }
    
    /***
     * Returns one specific IOL or REF value
     * 
     * @param page
     * @param side
     * @param position
     * @param type
     * @param row
     * @return
     * @throws IOException 
     */
    
    public Double getIOLREFValueRowIOLM700(PDPage page, String side, int position, String type, int row) throws IOException{
        Coordinates coords;
        if(type.equals("IOL")){
            coords = getDataCoordinates("MultiLensIOLStartIOLM700", side, position);
        }else{
            coords = getDataCoordinates("MultiLensREFStartIOLM700", side, position);
        }

        String textArea = getTextArea(page, new Rectangle(coords.x, coords.y + (row * this.rowHeight) + (row * this.rowDiff), 50, this.blockHeight)).trim();

        Double result = null;
        //System.out.println("<<<<<<<"+side+"::"+position+":::"+type+"::"+row+">>>"+textArea);
        //System.out.println("Coords:"+coords.x+","+coords.y+"+"+((row * this.rowHeight)+(row * this.rowDiff))+",50,"+ this.blockHeight );
        if (!"---".equals(textArea)) {
            result = Double.parseDouble(textArea);
        }

        return result;
    }
    
    /**
     *
     * @param page
     * @param side
     * @param position
     * @return
     * @throws IOException
     */
    public String dumpIOLREFValuesIOLM700(PDPage page, String side, int position) throws IOException{
        // IOL values
        String output = "";
        for(int k=0;k<5;k++){
            output += "IOL: "+getIOLREFValueRowIOLM700(page, side, position, "IOL", k)+" - ";
            output += "REF: "+getIOLREFValueRowIOLM700(page, side, position, "REF", k)+"\n";
        }
        return output;
    }

    private String dumpTestRectangle(PDPage page, int x, int y, int w, int h) throws IOException{
        return getTextArea(page, new Rectangle(x, y, w, h));
    }
    
    /**
     * We need to check the main software version to determine the coordinates as the printouts are slightly different.
     * @return 
     */
    
    protected String checkMainVersion()
    {
        String version = Study.getDeviceSoftwareVersion();
    
        return version.substring(0, 4);
    }
    
    private void addDataCoordinates(){

        if(this.checkMainVersion().equals("1.70")){
            this.rowHeight = 9;
            this.blockHeight = 9;
            this.rowDiff = 1;
            
            CoordMap.put("PageTitleIOLM700_T_1", new Coordinates(195,260));
            CoordMap.put("TopLensFormulaNameIOLM700_T_1", new Coordinates(50,180));

            CoordMap.put("MultiLensFormulaNamesIOLM700_L_1",new Coordinates(308, 430));
            CoordMap.put("MultiLensFormulaNamesIOLM700_L_2",new Coordinates(436, 430));
            CoordMap.put("MultiLensFormulaNamesIOLM700_L_3",new Coordinates(308, 575));
            CoordMap.put("MultiLensFormulaNamesIOLM700_L_4",new Coordinates(436, 575));
            CoordMap.put("MultiLensFormulaNamesIOLM700_R_1",new Coordinates(48, 430));
            CoordMap.put("MultiLensFormulaNamesIOLM700_R_2",new Coordinates(179, 430));
            CoordMap.put("MultiLensFormulaNamesIOLM700_R_3",new Coordinates(48, 575));
            CoordMap.put("MultiLensFormulaNamesIOLM700_R_4",new Coordinates(179, 575));
            
            CoordMap.put("MultiLensAValuesIOLM700_L_1", new Coordinates(308, 470));
            CoordMap.put("MultiLensAValuesIOLM700_L_2", new Coordinates(439, 470));
            CoordMap.put("MultiLensAValuesIOLM700_L_3", new Coordinates(308, 605));
            CoordMap.put("MultiLensAValuesIOLM700_L_4", new Coordinates(439, 605));
            CoordMap.put("MultiLensAValuesIOLM700_R_1", new Coordinates(48, 470));
            CoordMap.put("MultiLensAValuesIOLM700_R_2", new Coordinates(183, 470));
            CoordMap.put("MultiLensAValuesIOLM700_R_3", new Coordinates(48, 605));
            CoordMap.put("MultiLensAValuesIOLM700_R_4", new Coordinates(183, 605));

            CoordMap.put("MultiLensIOLTitleIOLM700_L_1", new Coordinates(314, 489));
            CoordMap.put("MultiLensIOLTitleIOLM700_L_2", new Coordinates(438, 489));
            CoordMap.put("MultiLensIOLTitleIOLM700_L_3", new Coordinates(314, 632));
            CoordMap.put("MultiLensIOLTitleIOLM700_L_4", new Coordinates(438, 632));
            CoordMap.put("MultiLensIOLTitleIOLM700_R_1", new Coordinates(47, 489));
            CoordMap.put("MultiLensIOLTitleIOLM700_R_2", new Coordinates(182, 489));
            CoordMap.put("MultiLensIOLTitleIOLM700_R_3", new Coordinates(47, 632));
            CoordMap.put("MultiLensIOLTitleIOLM700_R_4", new Coordinates(182, 632));
            
            CoordMap.put("MultiLensIOLStartIOLM700_L_1", new Coordinates(314, 498));
            CoordMap.put("MultiLensIOLStartIOLM700_L_2", new Coordinates(438, 498));
            CoordMap.put("MultiLensIOLStartIOLM700_L_3", new Coordinates(314, 641));
            CoordMap.put("MultiLensIOLStartIOLM700_L_4", new Coordinates(438, 641));
            CoordMap.put("MultiLensIOLStartIOLM700_R_1", new Coordinates(47, 498));
            CoordMap.put("MultiLensIOLStartIOLM700_R_2", new Coordinates(182, 498));
            CoordMap.put("MultiLensIOLStartIOLM700_R_3", new Coordinates(47, 641));
            CoordMap.put("MultiLensIOLStartIOLM700_R_4", new Coordinates(182, 641));

            CoordMap.put("MultiLensREFStartIOLM700_L_1", new Coordinates(366, 498));
            CoordMap.put("MultiLensREFStartIOLM700_L_2", new Coordinates(489, 498));
            CoordMap.put("MultiLensREFStartIOLM700_L_3", new Coordinates(365, 641));
            CoordMap.put("MultiLensREFStartIOLM700_L_4", new Coordinates(489, 641));
            CoordMap.put("MultiLensREFStartIOLM700_R_1", new Coordinates(103, 498));
            CoordMap.put("MultiLensREFStartIOLM700_R_2", new Coordinates(233, 498));
            CoordMap.put("MultiLensREFStartIOLM700_R_3", new Coordinates(103, 641));
            CoordMap.put("MultiLensREFStartIOLM700_R_4", new Coordinates(233, 641));

            CoordMap.put("TargetRefractionIOLM700_L_1", new Coordinates(310, 390));
            CoordMap.put("TargetRefractionIOLM700_R_1", new Coordinates(30, 390));

            CoordMap.put("LVCModeIOLM700_L_1", new Coordinates(295, 408));
            CoordMap.put("LVCModeIOLM700_R_1", new Coordinates(15, 408));

            CoordMap.put("EyeStatusIOLM700_L_1", new Coordinates(295, 400));
            CoordMap.put("EyeStatusIOLM700_R_1", new Coordinates(15, 400));

            CoordMap.put("ALIOLM700_L_1", new Coordinates(335,320));
            CoordMap.put("ALIOLM700_R_1", new Coordinates(77,320));

            CoordMap.put("ACDIOLM700_L_1", new Coordinates(335,330));
            CoordMap.put("ACDIOLM700_R_1", new Coordinates(77,330));

            CoordMap.put("K1IOLM700_L_1", new Coordinates(335,360));
            CoordMap.put("K1IOLM700_R_1", new Coordinates(77,360));
            CoordMap.put("AxisK1IOLM700_L_1", new Coordinates(388,360));
            CoordMap.put("AxisK1IOLM700_R_1", new Coordinates(132,360));

            CoordMap.put("K2IOLM700_L_1", new Coordinates(484,360));
            CoordMap.put("K2IOLM700_R_1", new Coordinates(227,360));
            CoordMap.put("AxisK2IOLM700_L_1", new Coordinates(540,360));
            CoordMap.put("AxisK2IOLM700_R_1", new Coordinates(283,360));
        
        }else if(this.checkMainVersion().equals("1.80")){
            this.rowHeight = 9;
            this.blockHeight = 9;
            this.rowDiff = 1;

            CoordMap.put("PageTitleIOLM700_T_1", new Coordinates(185,235));

            CoordMap.put("MultiLensNamesIOLM700_L_1",new Coordinates(318, 444));
            CoordMap.put("MultiLensNamesIOLM700_L_2",new Coordinates(444, 444));
            CoordMap.put("MultiLensNamesIOLM700_L_3",new Coordinates(318, 571));
            CoordMap.put("MultiLensNamesIOLM700_L_4",new Coordinates(444, 571));
            CoordMap.put("MultiLensNamesIOLM700_R_1",new Coordinates(62, 444));
            CoordMap.put("MultiLensNamesIOLM700_R_2",new Coordinates(189, 444));
            CoordMap.put("MultiLensNamesIOLM700_R_3",new Coordinates(62, 571));
            CoordMap.put("MultiLensNamesIOLM700_R_4",new Coordinates(189, 571));

            CoordMap.put("MultiFormulaNamesIOLM700_L_1",new Coordinates(318, 465));
            CoordMap.put("MultiFormulaNamesIOLM700_L_2",new Coordinates(444, 465));
            CoordMap.put("MultiFormulaNamesIOLM700_L_3",new Coordinates(318, 593));
            CoordMap.put("MultiFormulaNamesIOLM700_L_4",new Coordinates(444, 593));
            CoordMap.put("MultiFormulaNamesIOLM700_R_1",new Coordinates(62, 465));
            CoordMap.put("MultiFormulaNamesIOLM700_R_2",new Coordinates(189, 465));
            CoordMap.put("MultiFormulaNamesIOLM700_R_3",new Coordinates(62, 593));
            CoordMap.put("MultiFormulaNamesIOLM700_R_4",new Coordinates(189, 593));

            CoordMap.put("MultiLensAValuesIOLM700_L_1", new Coordinates(315, 474));
            CoordMap.put("MultiLensAValuesIOLM700_L_2", new Coordinates(441, 474));
            CoordMap.put("MultiLensAValuesIOLM700_L_3", new Coordinates(315, 602));
            CoordMap.put("MultiLensAValuesIOLM700_L_4", new Coordinates(441, 602));
            CoordMap.put("MultiLensAValuesIOLM700_R_1", new Coordinates(59, 474));
            CoordMap.put("MultiLensAValuesIOLM700_R_2", new Coordinates(186, 474));
            CoordMap.put("MultiLensAValuesIOLM700_R_3", new Coordinates(59, 602));
            CoordMap.put("MultiLensAValuesIOLM700_R_4", new Coordinates(186, 602));

            CoordMap.put("MultiLensIOLStartIOLM700_L_1", new Coordinates(321, 498));
            CoordMap.put("MultiLensIOLStartIOLM700_L_2", new Coordinates(448, 498));
            CoordMap.put("MultiLensIOLStartIOLM700_L_3", new Coordinates(321, 626));
            CoordMap.put("MultiLensIOLStartIOLM700_L_4", new Coordinates(448, 626));
            CoordMap.put("MultiLensIOLStartIOLM700_R_1", new Coordinates(66, 498));
            CoordMap.put("MultiLensIOLStartIOLM700_R_2", new Coordinates(193, 498));
            CoordMap.put("MultiLensIOLStartIOLM700_R_3", new Coordinates(66, 626));
            CoordMap.put("MultiLensIOLStartIOLM700_R_4", new Coordinates(193, 626));

            CoordMap.put("MultiLensREFStartIOLM700_L_1", new Coordinates(371, 498));
            CoordMap.put("MultiLensREFStartIOLM700_L_2", new Coordinates(498, 498));
            CoordMap.put("MultiLensREFStartIOLM700_L_3", new Coordinates(371, 626));
            CoordMap.put("MultiLensREFStartIOLM700_L_4", new Coordinates(498, 626));
            CoordMap.put("MultiLensREFStartIOLM700_R_1", new Coordinates(115, 498));
            CoordMap.put("MultiLensREFStartIOLM700_R_2", new Coordinates(243, 498));
            CoordMap.put("MultiLensREFStartIOLM700_R_3", new Coordinates(115, 626));
            CoordMap.put("MultiLensREFStartIOLM700_R_4", new Coordinates(243, 626));

            CoordMap.put("TargetRefractionIOLM700_L_1", new Coordinates(310, 325));
            CoordMap.put("TargetRefractionIOLM700_R_1", new Coordinates(55, 325));

            CoordMap.put("LVCModeIOLM700_L_1", new Coordinates(310, 314));
            CoordMap.put("LVCModeIOLM700_R_1", new Coordinates(55, 314));

            CoordMap.put("EyeStatusIOLM700_L_1", new Coordinates(310, 291));
            CoordMap.put("EyeStatusIOLM700_R_1", new Coordinates(55, 291));

            CoordMap.put("ALIOLM700_L_1", new Coordinates(333,350));
            CoordMap.put("ALIOLM700_R_1", new Coordinates(78,350));

            CoordMap.put("ACDIOLM700_L_1", new Coordinates(333,362));
            CoordMap.put("ACDIOLM700_R_1", new Coordinates(78,362));

            CoordMap.put("K1IOLM700_L_1", new Coordinates(474,395));
            CoordMap.put("K1IOLM700_R_1", new Coordinates(218,395));
            CoordMap.put("AxisK1IOLM700_L_1", new Coordinates(535,395));
            CoordMap.put("AxisK1IOLM700_R_1", new Coordinates(279,395));

            CoordMap.put("K2IOLM700_L_1", new Coordinates(474,407));
            CoordMap.put("K2IOLM700_R_1", new Coordinates(218,407));
            CoordMap.put("AxisK2IOLM700_L_1", new Coordinates(535,407));
            CoordMap.put("AxisK2IOLM700_R_1", new Coordinates(279,407));

        }else{
            
            CoordMap.put("PageTitleIOLM700_T_1", new Coordinates(200,270));
            CoordMap.put("TopLensFormulaNameIOLM700_T_1", new Coordinates(50,180));
            
            CoordMap.put("MultiLensFormulaNamesIOLM700_L_1",new Coordinates(317, 460));
            CoordMap.put("MultiLensFormulaNamesIOLM700_L_2",new Coordinates(441, 460));
            CoordMap.put("MultiLensFormulaNamesIOLM700_L_3",new Coordinates(317, 590));
            CoordMap.put("MultiLensFormulaNamesIOLM700_L_4",new Coordinates(441, 590));
            CoordMap.put("MultiLensFormulaNamesIOLM700_R_1",new Coordinates(50, 460));
            CoordMap.put("MultiLensFormulaNamesIOLM700_R_2",new Coordinates(185, 460));
            CoordMap.put("MultiLensFormulaNamesIOLM700_R_3",new Coordinates(50, 590));
            CoordMap.put("MultiLensFormulaNamesIOLM700_R_4",new Coordinates(185, 590));

            CoordMap.put("MultiLensAValuesIOLM700_L_1", new Coordinates(317, 495));
            CoordMap.put("MultiLensAValuesIOLM700_L_2", new Coordinates(441, 495));
            CoordMap.put("MultiLensAValuesIOLM700_L_3", new Coordinates(317, 620));
            CoordMap.put("MultiLensAValuesIOLM700_L_4", new Coordinates(441, 620));
            CoordMap.put("MultiLensAValuesIOLM700_R_1", new Coordinates(50, 495));
            CoordMap.put("MultiLensAValuesIOLM700_R_2", new Coordinates(185, 495));
            CoordMap.put("MultiLensAValuesIOLM700_R_3", new Coordinates(50, 620));
            CoordMap.put("MultiLensAValuesIOLM700_R_4", new Coordinates(185, 620));

            CoordMap.put("MultiLensIOLStartIOLM700_L_1", new Coordinates(317, 532));
            CoordMap.put("MultiLensIOLStartIOLM700_L_2", new Coordinates(441, 532));
            CoordMap.put("MultiLensIOLStartIOLM700_L_3", new Coordinates(317, 660));
            CoordMap.put("MultiLensIOLStartIOLM700_L_4", new Coordinates(441, 660));
            CoordMap.put("MultiLensIOLStartIOLM700_R_1", new Coordinates(50, 532));
            CoordMap.put("MultiLensIOLStartIOLM700_R_2", new Coordinates(185, 532));
            CoordMap.put("MultiLensIOLStartIOLM700_R_3", new Coordinates(50, 660));
            CoordMap.put("MultiLensIOLStartIOLM700_R_4", new Coordinates(185, 660));

            CoordMap.put("MultiLensREFStartIOLM700_L_1", new Coordinates(367, 532));
            CoordMap.put("MultiLensREFStartIOLM700_L_2", new Coordinates(491, 532));
            CoordMap.put("MultiLensREFStartIOLM700_L_3", new Coordinates(367, 660));
            CoordMap.put("MultiLensREFStartIOLM700_L_4", new Coordinates(491, 660));
            CoordMap.put("MultiLensREFStartIOLM700_R_1", new Coordinates(105, 532));
            CoordMap.put("MultiLensREFStartIOLM700_R_2", new Coordinates(235, 532));
            CoordMap.put("MultiLensREFStartIOLM700_R_3", new Coordinates(105, 660));
            CoordMap.put("MultiLensREFStartIOLM700_R_4", new Coordinates(235, 660));

            CoordMap.put("TargetRefractionIOLM700_L_1", new Coordinates(310, 410));
            CoordMap.put("TargetRefractionIOLM700_R_1", new Coordinates(30, 410));

            CoordMap.put("LVCModeIOLM700_L_1", new Coordinates(300, 433));
            CoordMap.put("LVCModeIOLM700_R_1", new Coordinates(20, 433));

            CoordMap.put("EyeStatusIOLM700_L_1", new Coordinates(300, 430));
            CoordMap.put("EyeStatusIOLM700_R_1", new Coordinates(20, 430));

            CoordMap.put("ALIOLM700_L_1", new Coordinates(340,320));
            CoordMap.put("ALIOLM700_R_1", new Coordinates(80,320));

            CoordMap.put("ACDIOLM700_L_1", new Coordinates(340,330));
            CoordMap.put("ACDIOLM700_R_1", new Coordinates(80,330));

            CoordMap.put("K1IOLM700_L_1", new Coordinates(340,360));
            CoordMap.put("K1IOLM700_R_1", new Coordinates(80,360));
            CoordMap.put("AxisK1IOLM700_L_1", new Coordinates(397,360));
            CoordMap.put("AxisK1IOLM700_R_1", new Coordinates(140,360));

            CoordMap.put("K2IOLM700_L_1", new Coordinates(340,370));
            CoordMap.put("K2IOLM700_R_1", new Coordinates(80,370));
            CoordMap.put("AxisK2IOLM700_L_1", new Coordinates(397,370));
            CoordMap.put("AxisK2IOLM700_R_1", new Coordinates(140,370));
        }
    }
    
    
    private Coordinates getDataCoordinates(String dataname, String side, int position){
        // side possible values: L = left, R=right, T=top
        String index = dataname+'_'+side+'_'+position;
        return (Coordinates) CoordMap.get(index);
    }
            
    private String getTextArea(PDPage page, Rectangle titleArea) throws IOException{
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition( true );
        stripper.addRegion( "area", titleArea );
        stripper.extractRegions( page );
        return stripper.getTextForRegion( "area" );
    }

    private String normaliseLensName(String lensName) {
        String result = lensName.replaceAll("(\\r|\\n)", " ");  // Replace any LF/CR characters with space
        result = result.replaceAll(" +", " ");  // Replace any consecutive spaces with a single space
        return result.trim();
    }

    private String normaliseFormulaName(String formulaName) {
        String result = formulaName.replace("- ", "");
        result = result.replace(" -", "");
        return result.trim();
    }
    
    /**
     * Override the default functionality of PDFTextStripper.
     * @param string
     * @param textPositions
     * @throws java.io.IOException
    */
    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
    {
        for (TextPosition text : textPositions)
        {
            System.out.println( (char)27 + "[0m String[" + text.getXDirAdj() + "," +
                    text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale=" +
                    text.getXScale() + " height=" + text.getHeightDir() + " space=" +
                    text.getWidthOfSpace() + " width=" +
                    text.getWidthDirAdj() + "] " + (char)27 + "[34;43m \t" + text.getUnicode() +" ");
        }
    }
}
