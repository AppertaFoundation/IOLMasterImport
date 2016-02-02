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
import uk.org.openeyes.models.Episode;
import uk.org.openeyes.models.Patient;

/**
 *
 * @author VEDELEKT
 */
public class DatabaseFunctionsTest {
    
    public DatabaseFunctionsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of initSessionFactory method, of class DatabaseFunctions.
     */
    @Test
    public void testInitSessionFactory() {
        System.out.println("initSessionFactory");
        String configFile = "";
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.initSessionFactory(configFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkConnection method, of class DatabaseFunctions.
     */
    @Test
    public void testCheckConnection() {
        System.out.println("checkConnection");
        DatabaseFunctions instance = new DatabaseFunctions();
        boolean expResult = false;
        boolean result = instance.checkConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeSessionFactory method, of class DatabaseFunctions.
     */
    @Test
    public void testCloseSessionFactory() {
        System.out.println("closeSessionFactory");
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.closeSessionFactory();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSelectedPatient method, of class DatabaseFunctions.
     */
    @Test
    public void testGetSelectedPatient() {
        System.out.println("getSelectedPatient");
        DatabaseFunctions instance = new DatabaseFunctions();
        Patient expResult = null;
        Patient result = instance.getSelectedPatient();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchPatient method, of class DatabaseFunctions.
     */
    @Test
    public void testSearchPatient() {
        System.out.println("searchPatient");
        String hosNum = "";
        char gender = ' ';
        Calendar birthDate = null;
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.searchPatient(hosNum, gender, birthDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectActiveEpisode method, of class DatabaseFunctions.
     */
    @Test
    public void testSelectActiveEpisode() {
        System.out.println("selectActiveEpisode");
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.selectActiveEpisode();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSelectedEpisode method, of class DatabaseFunctions.
     */
    @Test
    public void testGetSelectedEpisode() {
        System.out.println("getSelectedEpisode");
        DatabaseFunctions instance = new DatabaseFunctions();
        Episode expResult = null;
        Episode result = instance.getSelectedEpisode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStudyYMD method, of class DatabaseFunctions.
     */
    @Test
    public void testGetStudyYMD() {
        System.out.println("getStudyYMD");
        Calendar studyDate = null;
        DatabaseFunctions instance = new DatabaseFunctions();
        String expResult = "";
        String result = instance.getStudyYMD(studyDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBiometryEvent method, of class DatabaseFunctions.
     */
    @Test
    public void testCreateBiometryEvent() {
        System.out.println("createBiometryEvent");
        Calendar eventDate = null;
        StudyData IOLStudy = null;
        BiometryData IOLBiometry = null;
        Boolean withEpisode = null;
        DatabaseFunctions instance = new DatabaseFunctions();
        //instance.createBiometryEvent(eventDate, IOLStudy, IOLBiometry, withEpisode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logAuditData method, of class DatabaseFunctions.
     */
    @Test
    public void testLogAuditData() {
        System.out.println("logAuditData");
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.logAuditData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSession method, of class DatabaseFunctions.
     */
    @Test
    public void testSetSession() {
        System.out.println("setSession");
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.setSession();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTransaction method, of class DatabaseFunctions.
     */
    @Test
    public void testSetTransaction() {
        System.out.println("setTransaction");
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.setTransaction();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEventStudy method, of class DatabaseFunctions.
     */
    @Test
    public void testSetEventStudy() {
        System.out.println("setEventStudy");
        StudyData inputStudy = null;
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.setEventStudy(inputStudy);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEventBiometry method, of class DatabaseFunctions.
     */
    @Test
    public void testSetEventBiometry() {
        System.out.println("setEventBiometry");
        BiometryData inputBiometry = null;
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.setEventBiometry(inputBiometry);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSelectedUser method, of class DatabaseFunctions.
     */
    @Test
    public void testSetSelectedUser() {
        System.out.println("setSelectedUser");
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.setSelectedUser();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processBiometryEvent method, of class DatabaseFunctions.
     */
    @Test
    public void testProcessBiometryEvent() {
        System.out.println("processBiometryEvent");
        StudyData IOLStudy = null;
        BiometryData IOLBiometry = null;
        DatabaseFunctions instance = new DatabaseFunctions();
        instance.processBiometryEvent(IOLStudy, IOLBiometry, this);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
