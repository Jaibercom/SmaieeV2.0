/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

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
        cleanView(ppalView);
        filterAddedElements(ppalView);
        String[] elementosDisponibles = elementosDisponibles(ppalView);
        Validacion.actualizarCombo(ppalView.getBalastoConfiguracion_jComboBox(), elementosDisponibles, Validacion.BALASTOS_NO_DISPONIBLES);
    }

    @Override
    public String[] elementosDisponibles(PpalView ppalView) {
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());
        String[] ses;
        ses = dao.elementosSinGrabar();

        return ses;
    }

    @Override
    public void cleanView(PpalView ppalView) {

        ppalView.getBalastoNum_jComboBox().setSelectedIndex(0);
        ppalView.getBalastoDir_jTextField().setText("0");
        ppalView.getBalastoMin_jTextField().setText("0");
        ppalView.getBalastoFR_jTextField().setText("0");
        ppalView.getBalastoFT_jTextField().setText("0");
        ppalView.getBalastoLF_jTextField().setText("0");
        ppalView.getBalastoLX_jTextField().setText("0");
        ppalView.getBalastoMax_jTextField().setText("0");
        seleccionGrupos(ppalView, false);
        seleccionEscenas(ppalView, false);


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
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void showSelectedElement(String num, PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Método que limpia los grupos
     *
     * @param ppalView
     */
    private void seleccionGrupos(PpalView ppalView, boolean act) {

        ppalView.getGrupo_jCheckBox1().setSelected(act);
        ppalView.getGrupo_jCheckBox2().setSelected(act);
        ppalView.getGrupo_jCheckBox3().setSelected(act);
        ppalView.getGrupo_jCheckBox4().setSelected(act);
        ppalView.getGrupo_jCheckBox5().setSelected(act);
        ppalView.getGrupo_jCheckBox6().setSelected(act);
        ppalView.getGrupo_jCheckBox7().setSelected(act);
        ppalView.getGrupo_jCheckBox8().setSelected(act);
        ppalView.getGrupo_jCheckBox9().setSelected(act);
        ppalView.getGrupo_jCheckBox10().setSelected(act);
        ppalView.getGrupo_jCheckBox11().setSelected(act);
        ppalView.getGrupo_jCheckBox12().setSelected(act);
        ppalView.getGrupo_jCheckBox13().setSelected(act);
        ppalView.getGrupo_jCheckBox14().setSelected(act);
        ppalView.getGrupo_jCheckBox15().setSelected(act);
        ppalView.getGrupo_jCheckBox16().setSelected(act);



    }

    private void seleccionEscenas(PpalView ppalView, boolean b) {
        ppalView.getSliderConValor1().getCheckBox().setSelected(b);
        ppalView.getSliderConValor2().getCheckBox().setSelected(b);
        ppalView.getSliderConValor3().getCheckBox().setSelected(b);
        ppalView.getSliderConValor4().getCheckBox().setSelected(b);
        ppalView.getSliderConValor5().getCheckBox().setSelected(b);
        ppalView.getSliderConValor6().getCheckBox().setSelected(b);
        ppalView.getSliderConValor7().getCheckBox().setSelected(b);
        ppalView.getSliderConValor8().getCheckBox().setSelected(b);
        ppalView.getSliderConValor9().getCheckBox().setSelected(b);
        ppalView.getSliderConValor10().getCheckBox().setSelected(b);
        ppalView.getSliderConValor11().getCheckBox().setSelected(b);
        ppalView.getSliderConValor12().getCheckBox().setSelected(b);
        ppalView.getSliderConValor13().getCheckBox().setSelected(b);
        ppalView.getSliderConValor14().getCheckBox().setSelected(b);
        ppalView.getSliderConValor15().getCheckBox().setSelected(b);
        ppalView.getSliderConValor16().getCheckBox().setSelected(b);
        
    }
}