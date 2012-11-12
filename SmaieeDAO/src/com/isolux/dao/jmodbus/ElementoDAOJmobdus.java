/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.dao.modbus.DAOJmodbus;

/**
 *
 * @author Juan Camilo CAñas Gómez
 */
public class ElementoDAOJmobdus {

    public static final int MODE_CONFIG=1;
    public static final int MODE_RUN=0;
    
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

    /**
     * Setea un registro de la tarjeta. Método usado en operaciones directas con la tarjeta
     * @param pos
     * @param value 
     */
    public void setSingleReg(int pos, int value) {
        int[] values = {value};
        dao.setRegValue(pos, values);
    }
    
    /**
     * Método que define el modo de la tarjeta. Se pueden usar las siguientes opciones:<br>
     * MODE_CONFIG<br>
     * MODE_RUN
     * @param mode puede usarse :<br>
     * MODE_CONFIG : El cual pone la tarjeta en modo de configuracion<br>
     * MODE_RUN : El cual pone la tarjeta en modo run.
     * 
     * 
     */
    public void setMode(int mode){
        setSingleReg(0, mode);
    }
    
    /**
     * Método que obtiene los valores del registro. 
     * @param pos
     * @param length
     * @return 
     */
    public int[] getRegValue(int pos, int length){
        int[] regValue = dao.getRegValue(pos, length);
        return regValue;
    }
}