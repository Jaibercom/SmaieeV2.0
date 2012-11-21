/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

/**
 *  Interface de las operaciones comunes de los datos jmodbus
 * @author Juan Camilo Cañas
 */
public interface OperacionesElemento_Interface {
    
    /**
     * Método que selecciona los elementos disponibles en la tarjeta para ser grabados.
     * @return String[] de elementos que representan los elementos que no se han grabado aún
     * en la tarjeta.
     */
    public String[] elementosSinGrabar();
   
}
