/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import java.util.Calendar;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author VEDELEKT
 */
public class PatientDataTest {
    
    public PatientDataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of setPatientName method, of class PatientData.
     */
    @Test
    public void testSetPatientName() {
        System.out.println("setPatientName");
        String PName = "";
        PatientData instance = new PatientData();
        instance.setPatientName(PName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPatientID method, of class PatientData.
     */
    @Test
    public void testSetPatientID() {
        System.out.println("setPatientID");
        String PID = "";
        PatientData instance = new PatientData();
        instance.setPatientID(PID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPatientGender method, of class PatientData.
     */
    @Test
    public void testSetPatientGender() {
        System.out.println("setPatientGender");
        char PGender = ' ';
        PatientData instance = new PatientData();
        instance.setPatientGender(PGender);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPatientComments method, of class PatientData.
     */
    @Test
    public void testSetPatientComments() {
        System.out.println("setPatientComments");
        String PComments = "";
        PatientData instance = new PatientData();
        instance.setPatientComments(PComments);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPatientBirth method, of class PatientData.
     */
    @Test
    public void testSetPatientBirth() {
        System.out.println("setPatientBirth");
        String PBirth = "";
        PatientData instance = new PatientData();
        instance.setPatientBirth(PBirth);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPatientID method, of class PatientData.
     */
    @Test
    public void testGetPatientID() {
        System.out.println("getPatientID");
        PatientData instance = new PatientData();
        String expResult = "";
        String result = instance.getPatientID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPatientGender method, of class PatientData.
     */
    @Test
    public void testGetPatientGender() {
        System.out.println("getPatientGender");
        PatientData instance = new PatientData();
        char expResult = ' ';
        char result = instance.getPatientGender();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPatientBirth method, of class PatientData.
     */
    @Test
    public void testGetPatientBirth() {
        System.out.println("getPatientBirth");
        PatientData instance = new PatientData();
        Calendar expResult = null;
        Calendar result = instance.getPatientBirth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printPatientData method, of class PatientData.
     */
    @Test
    public void testPrintPatientData() {
        System.out.println("printPatientData");
        PatientData instance = new PatientData();
        instance.printPatientData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
