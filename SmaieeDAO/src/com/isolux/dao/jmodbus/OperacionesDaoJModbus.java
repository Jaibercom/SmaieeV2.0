/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.bo.Elemento;
import java.util.ArrayList;

/**
 *
 * @author Sebastian Rodriguez
 */
public class OperacionesDaoJModbus implements OperacionesDaoJModbusInterface{

    @Override
    public void addElement(int num) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteElement(int num) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteElement(String num) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<String>[] getAddedElements(int num) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int[] getAddedCardArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Elemento readObject(int num) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean saveElement(Elemento element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
