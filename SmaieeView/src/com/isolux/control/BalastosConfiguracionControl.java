/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.bo.Balasto;
import com.isolux.bo.Elemento;
import com.isolux.dao.jmodbus.BalastoDAOJmodbus;
import com.isolux.dao.jmodbus.OperacionesElemento_Interface;
import com.isolux.dao.modbus.DAOJmodbus;

/**
 *
 * @author Sebastian Rodriguez
 */
public class BalastosConfiguracionControl implements OperacionesElemento_Interface{

  

    public BalastosConfiguracionControl() {
        
        initcomponets();

    }

    public void leerBalasto() {
    }

    public void mostrarInformacion(Elemento elemento) {
        Balasto balasto = (Balasto) elemento;

    }

    private void initcomponets() {
        String[] elementosDisponibles = elementosSinGrabar();
        // configuramos el combobox
//        Validacion.actualizarCombo(ppalView.getBalastoConfiguracion_jComboBox(), elementosDisponibles, Validacion.BALASTOS_TODOS);

    }

    @Override
    public String[] elementosSinGrabar() {
         
        BalastoDAOJmodbus dao=new BalastoDAOJmodbus(new DAOJmodbus());
        int[] addedBalastsCardArray = dao.getAddedBalastsCardArray();
        String[] ele=new String[addedBalastsCardArray.length];
        
        for (int i = 0; i < addedBalastsCardArray.length; i++) {
            ele[i]=Integer.toString(addedBalastsCardArray[i]);
        }
        
        return ele;
        
    }
}
