/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import java.util.ArrayList;

/**
 *  Interface de las operaciones comunes de los datos jmodbus
 * @author Juan Camilo Canias
 */
public interface OperacionesElemento_Interface {
    
    /**
     * Método que selecciona los elementos disponibles en la tarjeta para ser grabados.
     * @return Integer[] de elementos que representan los elementos que no se han grabado aún
     * en la tarjeta.
     */
    public String[] elementosSinGrabar();
   
}
