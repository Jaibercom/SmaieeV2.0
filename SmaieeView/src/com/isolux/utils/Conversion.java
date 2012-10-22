/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

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
     * @param modo es la forma en la que se va a representar elk arreglo que se devuelve. 
     * Usa las constantes LITTLEENDIAN o BIGENDIAN
     * @return Integer[] que contiene la representacion 
     */
    public static Integer[] intToBinaryarray(Integer num, int modo) {
        Integer[] s =null;

        
        
        


        return s;
    }
}
