/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author VEDELEKT
 */
public class BiometryMeasurementDataTest {
    
    public BiometryMeasurementDataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of setLenseName method, of class BiometryMeasurementData.
     */
    @Test
    public void testSetLenseName() {
        System.out.println("setLenseName");
        String LName = "";
        BiometryMeasurementData instance = new BiometryMeasurementData();
        instance.setLenseName(LName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLenseName method, of class BiometryMeasurementData.
     */
    @Test
    public void testGetLenseName() {
        System.out.println("getLenseName");
        BiometryMeasurementData instance = new BiometryMeasurementData();
        String expResult = "";
        String result = instance.getLenseName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFormulaName method, of class BiometryMeasurementData.
     */
    @Test
    public void testSetFormulaName() {
        System.out.println("setFormulaName");
        String LName = "";
        BiometryMeasurementData instance = new BiometryMeasurementData();
        instance.setFormulaName(LName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFormulaName method, of class BiometryMeasurementData.
     */
    @Test
    public void testGetFormulaName() {
        System.out.println("getFormulaName");
        BiometryMeasurementData instance = new BiometryMeasurementData();
        String expResult = "";
        String result = instance.getFormulaName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAConst method, of class BiometryMeasurementData.
     */
    @Test
    public void testSetAConst() {
        System.out.println("setAConst");
        String LAConst = "";
        BiometryMeasurementData instance = new BiometryMeasurementData();
        instance.setAConst(LAConst);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAConst method, of class BiometryMeasurementData.
     */
    @Test
    public void testGetAConst() {
        System.out.println("getAConst");
        BiometryMeasurementData instance = new BiometryMeasurementData();
        Double expResult = null;
        Double result = instance.getAConst();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEmmetropia method, of class BiometryMeasurementData.
     */
    @Test
    public void testSetEmmetropia() {
        System.out.println("setEmmetropia");
        String LEmmetropia = "";
        BiometryMeasurementData instance = new BiometryMeasurementData();
        instance.setEmmetropia(LEmmetropia);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmmetropia method, of class BiometryMeasurementData.
     */
    @Test
    public void testGetEmmetropia() {
        System.out.println("getEmmetropia");
        BiometryMeasurementData instance = new BiometryMeasurementData();
        Double expResult = null;
        Double result = instance.getEmmetropia();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIOL method, of class BiometryMeasurementData.
     */
    @Test
    public void testSetIOL() {
        System.out.println("setIOL");
        String LIOL = "";
        BiometryMeasurementData instance = new BiometryMeasurementData();
        instance.setIOL(LIOL);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIOL method, of class BiometryMeasurementData.
     */
    @Test
    public void testGetIOL() {
        System.out.println("getIOL");
        BiometryMeasurementData instance = new BiometryMeasurementData();
        List<Double> expResult = null;
        List<Double> result = instance.getIOL();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setREF method, of class BiometryMeasurementData.
     */
    @Test
    public void testSetREF() {
        System.out.println("setREF");
        String LREF = "";
        BiometryMeasurementData instance = new BiometryMeasurementData();
        instance.setREF(LREF);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getREF method, of class BiometryMeasurementData.
     */
    @Test
    public void testGetREF() {
        System.out.println("getREF");
        BiometryMeasurementData instance = new BiometryMeasurementData();
        List<Double> expResult = null;
        List<Double> result = instance.getREF();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIOLREFJSON method, of class BiometryMeasurementData.
     */
    @Test
    public void testGetIOLREFJSON() {
        System.out.println("getIOLREFJSON");
        BiometryMeasurementData instance = new BiometryMeasurementData();
        String expResult = "";
        String result = instance.getIOLREFJSON();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printLenses method, of class BiometryMeasurementData.
     */
    @Test
    public void testPrintLenses() {
        System.out.println("printLenses");
        BiometryMeasurementData instance = new BiometryMeasurementData();
        instance.printLenses();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
