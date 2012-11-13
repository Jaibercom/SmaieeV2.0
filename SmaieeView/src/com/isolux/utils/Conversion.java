/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

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
    public static Integer[] intToBinaryarray(Integer num, int modo) {
        Integer[] s = null;






        return s;
    }

   /**
    * Método que convierte de un binario a decimal
    * @param num numero binario
    * @return String que representa el numero decimal
    */
    private static String binarioToDecimal(String num) {
        try {
            int i = Integer.parseInt(num, 2);
            String dec = Integer.toString(i);
            return dec;
        } catch (NumberFormatException numberFormatException) {
            Logger.getLogger(Conversion.class.getName()).log(Level.WARNING,"PROBLEMA DE CONVERSION.",numberFormatException);
            return null;
        }
    }
    
    /**
     *
     * @param num
     * @return
     */
    public static Integer binarioAEntero(String num){
        String bD = binarioToDecimal(num);
        int parseInt = Integer.parseInt(bD);
        return parseInt;
        
    }
}
