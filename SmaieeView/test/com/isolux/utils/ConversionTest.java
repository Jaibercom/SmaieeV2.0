/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

import com.isolux.dao.Conversion;
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
public class ConversionTest {
    
    public ConversionTest() {
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
     * Test of getModoEscritura method, of class Conversion.
     */
    @Test
    public void testGetModoEscritura() {
        System.out.println("getModoEscritura");
        Conversion instance = new Conversion();
        int expResult = 0;
        int result = instance.getModoEscritura();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setModoEscritura method, of class Conversion.
     */
    @Test
    public void testSetModoEscritura() {
        System.out.println("setModoEscritura");
        int modoEscritura = 0;
        Conversion instance = new Conversion();
        instance.setModoEscritura(modoEscritura);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of intToBinaryarray method, of class Conversion.
     */
    @Test
    public void testIntToBinaryarray() {
       assertTrue(true);
        
        
        
    }

    /**
     * Test of binarioAEntero method, of class Conversion.
     */
    @Test
    public void testBinarioAEntero() {
        System.out.println("binarioAEntero");
//       assertTrue("La funcion funciona?",Conversion.binarioAEntero("10001011")==238);
        assertTrue("La funcion funciona?",Conversion.binarioAEntero("10001011")==139);
        assertTrue(Conversion.binarioAEntero("10100000100100111000010")==5261762);
        
    }

    /**
     * Test of integerArrayToInt method, of class Conversion.
     */
    @Test
    public void testIntegerArrayToInt() {
        System.out.println("integerArrayToInt");
        int[] array = {1,0,0,0,1,0,1,1};
        int expResult = 139;
        int result = Conversion.integerArrayToInt(array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testIntToBinaryArray(){
        System.out.println(" de entero a array binario");
        int[] resultado = {1,0,0,0,1,0,1,1};
//        int[] resultado = {1,1,0,1,0,0,0,1};
        int  numero = 139;
        int[] resultadoFuncion = Conversion.intToBinaryArray(numero);
        
        assertEquals(resultadoFuncion, resultado);
    }
    
    
}
