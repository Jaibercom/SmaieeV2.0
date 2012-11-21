/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

import javax.swing.text.AttributeSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sebastian Rodriguez
 */
public class LimitadorDeCaracteresIp_DocumentTest {
    
    public LimitadorDeCaracteresIp_DocumentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertString method, of class LimitadorDeCaracteresIp_Document.
     */
    @Test
    public void testInsertString() throws Exception {
        System.out.println("insertString");
        int offs = 0;
        String str = "";
        AttributeSet a = null;
        LimitadorDeCaracteresIp_Document instance = null;
        instance.insertString(offs, str, a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
