/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.io.DicomInputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author VEDELEKT
 */
public class OE_IOLMasterImportTest {
    
    public OE_IOLMasterImportTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getWidth method, of class OE_IOLMasterImport.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        OE_IOLMasterImport instance = new OE_IOLMasterImport();
        int expResult = 0;
        int result = instance.getWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWidth method, of class OE_IOLMasterImport.
     */
    @Test
    public void testSetWidth() {
        System.out.println("setWidth");
        int width = 0;
        OE_IOLMasterImport instance = new OE_IOLMasterImport();
        instance.setWidth(width);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parse method, of class OE_IOLMasterImport.
     */
    @Test
    public void testParse() throws Exception {
        System.out.println("parse");
        DicomInputStream dis = null;
        OE_IOLMasterImport instance = new OE_IOLMasterImport();
        instance.parse(dis);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readAttributes method, of class OE_IOLMasterImport.
     */
    @Test
    public void testReadAttributes() {
        System.out.println("readAttributes");
        Attributes inputAttrs = null;
        String sequenceTag = "";
        OE_IOLMasterImport instance = new OE_IOLMasterImport();
        instance.readAttributes(inputAttrs, sequenceTag);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readSequence method, of class OE_IOLMasterImport.
     */
    @Test
    public void testReadSequence() {
        System.out.println("readSequence");
        Sequence inputSeq = null;
        String sequenceTag = "";
        OE_IOLMasterImport instance = new OE_IOLMasterImport();
        instance.readSequence(inputSeq, sequenceTag);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class OE_IOLMasterImport.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        OE_IOLMasterImport.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
