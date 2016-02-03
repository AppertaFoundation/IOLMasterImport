/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.org.openeyes;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author VEDELEKT
 */
public class BiometryDataTest {
    
    public BiometryDataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of setSideData method, of class BiometryData.
     */
    @Test
    public void testSetSideData() {
        System.out.println("setSideData");
        String Side = "";
        BiometryData instance = new BiometryData();
        instance.setSideData(Side);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBiometryValue method, of class BiometryData.
     */
    @Test
    public void testSetBiometryValue() {
        System.out.println("setBiometryValue");
        String ValueName = "";
        String Side = "";
        String Value = "";
        BiometryData instance = new BiometryData();
        instance.setBiometryValue(ValueName, Side, Value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBiometryValueNum method, of class BiometryData.
     */
    @Test
    public void testSetBiometryValueNum() {
        System.out.println("setBiometryValueNum");
        String ValueName = "";
        String Side = "";
        String Value = "";
        int Position = 0;
        BiometryData instance = new BiometryData();
        instance.setBiometryValueNum(ValueName, Side, Value, Position);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBiometryValue method, of class BiometryData.
     */
    @Test
    public void testGetBiometryValue() {
        System.out.println("getBiometryValue");
        String Side = "";
        BiometryData instance = new BiometryData();
        BiometrySide expResult = null;
        BiometrySide result = instance.getBiometryValue(Side);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEyeId method, of class BiometryData.
     */
    @Test
    public void testGetEyeId() {
        System.out.println("getEyeId");
        BiometryData instance = new BiometryData();
        Integer expResult = null;
        Integer result = instance.getEyeId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printBiometryData method, of class BiometryData.
     */
    @Test
    public void testPrintBiometryData() {
        System.out.println("printBiometryData");
        BiometryData instance = new BiometryData();
        instance.printBiometryData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
