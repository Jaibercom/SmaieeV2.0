/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

/**
 *Clase padre de las clases DAOJModbus
 * @author Juan Camilo Cañas Gómez
 */
public abstract class OperacionesDaoJModbus extends ElementoDAOJmobdus implements OperacionesElemento_Interface, OperacionesDaoJModbusInterface{

    public OperacionesDaoJModbus() {
    }

    @Override
    public String[] elementosSinGrabar() {
         int[] elementsCardArray = getAddedCardArray();
        String[] ele=new String[elementsCardArray.length];
        
        for (int i = 0; i < elementsCardArray.length; i++) {
            ele[i]=Integer.toString(elementsCardArray[i]);
        }
        
        return ele;
    }

   
}
