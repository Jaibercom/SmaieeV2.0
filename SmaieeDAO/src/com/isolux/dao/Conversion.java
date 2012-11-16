/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao;

import com.isolux.dao.jmodbus.UtilsJmodbus;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de utilidades. Encargada de realizar tareas repetitivas de
 * conversiones.
 *
 * @author Juan Camilo Canias Gómez
 */
public class Conversion {

    public static int LITTLEENDIAN = 1;
    public static int BIGENDIAN = 2;
    private int modoEscritura;

    public int getModoEscritura() {
        return modoEscritura;
    }

    public void setModoEscritura(int modoEscritura) {
        this.modoEscritura = modoEscritura;
    }

    /**
     * Convierte un numero entero a la representacion en binario en Integer[]
     *
     * @param num
     * @param modo es la forma en la que se va a representar elk arreglo que se
     * devuelve. Usa las constantes LITTLEENDIAN o BIGENDIAN
     * @return Integer[] que contiene la representacion
     */
    public static int[] intToBinaryArray(int num) {
        
        
        String binaryString = Integer.toBinaryString(num);
        StringBuilder bin1=new StringBuilder(binaryString);
        String bin = Utils.getCeros(binaryString,8);
        int[] s = new int[bin.length()];
        for (int i = 0; i < binaryString.length();i++) {
            String df = Character.toString(bin.charAt(i));
            int parseInt = Integer.parseInt(df);
            s[i]=parseInt;
        }
        
        
        return s;
    }

    /**
     * Método que convierte de un binario a decimal
     *
     * @param num numero binario
     * @return String que representa el numero decimal
     */
    private static String binarioToDecimal(String num) {
        try {
            int i = Integer.parseInt(num, 2);
            String dec = Integer.toString(i);
            return dec;
        } catch (NumberFormatException numberFormatException) {
            Logger.getLogger(Conversion.class.getName()).log(Level.WARNING, "PROBLEMA DE CONVERSION.", numberFormatException);
            return null;
        }
    }

    /**
     *
     * @param num
     * @return
     */
    public static Integer binarioAEntero(String num) {
        String bD = binarioToDecimal(num);
        int parseInt = Integer.parseInt(bD);
        return parseInt;

    }

    /**
     * Método que convierte de un array de enteros de 1 y 0 a un entero
     *
     * @param array Array entero de 1's y 0's
     * @return entero que representa el
     */
    public static int integerArrayToInt(int[] array) {
        StringBuilder binario = new StringBuilder();

        for (Integer elemento : array) {
            binario.append(elemento);

        }
        int enetero = binarioAEntero(binario.reverse().toString());
        return enetero;

    }

    /**
     * Método que convierte un array de enteros que contienen un binario a 2
     * array de enteros que contienen la parte menos significativa y la parte
     * mas significativa de del array de enteros (binario) original
     *
     * @param arrayOriginal
     * @return Vector que contiene las dos partes del array binario. en la
     * posición 0 se encuentra la parte menos significativa de la cadena de 16
     * bits y en la posición 1 del vector la parte más significativa.
     */
    public static Vector<int[]> integerArrayTo2IntegerArrayBinarios(int[] arrayOriginal) {

        Vector<int[]> arrays = new Vector();
        int[] grupomenoss = new int[8];
        int[] grupoMass = new int[8];

        int j = 0;
        for (int i = 7; i >= 0; i--) {
            grupomenoss[j] = arrayOriginal[i];
            j++;
        }

        j = 0;
        for (int i = 15; i >= 8; i--) {
            grupoMass[j] = arrayOriginal[i];
            j++;
        }
        arrays.addElement(grupomenoss);
        arrays.addElement(grupoMass);

        return arrays;
    }
}
