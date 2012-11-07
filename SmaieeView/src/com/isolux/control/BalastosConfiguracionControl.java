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
import com.isolux.utils.Validacion;
import com.isolux.view.PpalView;

/**
 *
 * @author Juan Camilo Canias Gómez
 */
public class BalastosConfiguracionControl implements OperacionesElemento_Interface, ElementoControl_Interface {

    PpalView ppalView;

    public PpalView getPpalView() {
        return ppalView;
    }

    public void setPpalView(PpalView ppalView) {
        this.ppalView = ppalView;
    }

    public BalastosConfiguracionControl() {

       

    }

    public void leerBalasto() {
    }

    public void mostrarInformacion(Elemento elemento) {
        Balasto balasto = (Balasto) elemento;

    }

    private void cargarComboBox() {
        String[] elementosDisponibles = elementosSinGrabar();
        // configuramos el combobox
        Validacion.actualizarCombo(ppalView.getBalastoConfiguracion_jComboBox(), elementosDisponibles, Validacion.BALASTOS_TODOS);

    }

    public void refrescaVista(PpalView ppalView) {
//        String[] elementosSinGrabar = elementosSinGrabar();
//        Validacion.actualizarCombo(ppalView.getBalastoNum_jComboBox(), elementosSinGrabar, Validacion.BALASTOS_DISPONIBLES);

    }

    @Override
    public String[] elementosSinGrabar() {

        BalastoDAOJmodbus dao1 = new BalastoDAOJmodbus(new DAOJmodbus());
        int[] addedBalastsCardArray = dao1.getAddedBalastsCardArray();
        String[] ele = new String[addedBalastsCardArray.length];

        for (int i = 0; i < addedBalastsCardArray.length; i++) {
            ele[i] = Integer.toString(addedBalastsCardArray[i]);
        }

        return ele;

    }

    @Override
    public void refrescarVista(PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] elementosDisponibles(PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cleanView(PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveElement(PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteElement(PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void readElements(PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void filterAddedElements(PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void showSelectedElement(String num, PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
