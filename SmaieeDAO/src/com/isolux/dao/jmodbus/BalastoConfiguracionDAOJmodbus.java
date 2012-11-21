/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;

/**
 * Clase que se encarga de las operaciones de configuracion de los balastos
 *
 * @author Juan Camilo Cañas Gómez
 */
public class BalastoConfiguracionDAOJmodbus extends BalastoDAOJmodbus {

    public BalastoConfiguracionDAOJmodbus(DAOJmodbus dao) {
        super(dao);
    }

    @Override
    public String[] elementosSinGrabar() {
//          int[] addedBalastsCardArray = BalastoDAOJmodbus.getAddedBalastsCardArray();
        int[] balastosEnRed = getBalastosEnRed();
        String[] ele = new String[balastosEnRed.length];

        for (int i = 0; i < balastosEnRed.length; i++) {
            ele[i] = Integer.toString(balastosEnRed[i]);
        }

        return ele; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Método que obtiene desde el buffer del SMAIEE la información de los balastos
     * que se encuentran en la red.
     * @return 
     */
    private int[] getBalastosEnRed() {
        
        OperacionesBalastoConfiguracionDaoJmodbus ob=OperacionesBalastoConfiguracionDaoJmodbus.getInstancia();
        ob.setDao(getDao());
        ob.balastosEnRed();
        int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
        int initOffset = Integer.parseInt(PropHandler.getProperty("balastosenred"));
        int usedRegisters = Integer.parseInt(PropHandler.getProperty("balast.memory.registers"));
        int tamReg = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
        return UtilsJmodbus.getElementosEnMemoriaInt(numBalastos, getDao(), initOffset, usedRegisters, tamReg);
    }
}
