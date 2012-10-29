/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.bo.Elemento;
import java.util.ArrayList;

/**
 * Interface que estandariza las operaciones que contiene un elemento de tipo
 * dao.
 *
 * @author Juan Camilo Canias Gómez
 */
public interface OperacionesDaoJModbusInterface {

    public void addElement(int num);

    public void deleteElement(int num);

    public boolean deleteElement(String num);

    public ArrayList<String>getAddedElements();

    public int[] getAddedCardArray();

    public Elemento readElement(int num);

    /**
     *
     * @param Elemento
     * @return
     */
    public boolean saveElement(Elemento element);
}
