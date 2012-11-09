/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.dao.modbus.DAOJmodbus;

/**
 *
 * @author Sebastian Rodriguez
 */
public class ElementoDAOJmobdus {

    private DAOJmodbus dao;

    public ElementoDAOJmobdus(DAOJmodbus dao) {
        this.dao = dao;
    }

    public ElementoDAOJmobdus() {
    }

    
    
    public DAOJmodbus getDao() {
        return dao;
    }

    public void setDao(DAOJmodbus dao) {
        this.dao = dao;
    }

    public void setSingleReg(int pos, int mode) {
        int[] values = {mode};
        dao.setRegValue(pos, values);
    }
}
