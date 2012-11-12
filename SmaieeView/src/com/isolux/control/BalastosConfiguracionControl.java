/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.bo.Balasto;
import com.isolux.dao.jmodbus.BalastoDAOJmodbus;
import com.isolux.dao.jmodbus.ElementoDAOJmobdus;
import com.isolux.dao.jmodbus.OperacionesElemento_Interface;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.utils.Validacion;
import com.isolux.view.PpalView;

/**
 *
 * @author Juan Camilo Canias Gómez
 */
public class BalastosConfiguracionControl extends ElementoDAOJmobdus implements OperacionesElemento_Interface, ElementoControl_Interface {

    Balasto balasto=new Balasto();
    
    public BalastosConfiguracionControl(DAOJmodbus dao) {
        super(dao);
    }

    

    public BalastosConfiguracionControl() {
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
        cleanView(ppalView);
        filterAddedElements(ppalView);
        String[] elementosDisponibles = elementosDisponibles(ppalView);
        Validacion.actualizarCombo(ppalView.getBalastoConfiguracion_jComboBox(), elementosDisponibles, Validacion.BALASTOS_NO_DISPONIBLES);
    }

    @Override
    public String[] elementosDisponibles(PpalView ppalView) {
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());
        BalastoConfigacionDAOJmodbus dAOJmodbus;
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
//        boolean state = false;
//        int balastNumber = balasto.getBalastNumber();
//
//        try {
//            //MODO Escritura
//            setSingleReg(0, 1);
//
//            //Init offset.
//            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.init.position"));
//            int[] balastArray = new int[Integer.parseInt(PropHandler.getProperty("balast.memory.size"))];
//
//            System.out.println("SAVE BALAST NUMBER: " + balastNumber);
//
//            balastArray[0] = balastNumber;                  //2000
//            balastArray[1] = balasto.getLevel();            //2001
//            balastArray[2] = balasto.getActivation();       //2002
//
//            //            //<editor-fold defaultstate="collapsed" desc="codigo antiguo">
////            int nameOffset = 3;
//            //            ArrayList<BigInteger> balastNameBytes = UtilsJmodbus.getNameBytesReverse(balasto.getName());
//            //            int size = balastNameBytes.size();
//            //            for (int i = 0; i < 5; i++) {
//            //                if (i < size) {
//            //                    balastArray[nameOffset] = balastNameBytes.get(i).intValue();
//            //                } else {
//            //                    balastArray[nameOffset] = 0;
//            //                }
//            //                nameOffset++;
//            //            }
//            //</editor-fold>
//
////            codifica el nombre y lo mete en el array
//            UtilsJmodbus.encriptarNombre(balastArray, 3, balasto.getName(), 5);
//
//            balastArray[8] = balasto.getDir();              //2008
//            balastArray[9] = balasto.getMin();              //2009
//            balastArray[10] = balasto.getMax();             //2010
//            balastArray[11] = balasto.getFt();              //2011
//            balastArray[12] = balasto.getFr();              //2012
//            balastArray[13] = balasto.getLf();              //2013
//            balastArray[14] = balasto.getLx();              //2014
//            balastArray[15] = balasto.getPot();             //2015
//
//
//
//
//            /**
//             * Numero dentro del array que corresponde al offset desde donde se
//             * empezarán a guardar los datos de los grupos
//             */
//            int gruposOffset = 16;
//            int tamReg = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
//            int[] gruposAfect = balasto.getGruposAfectados();
//            int cuantosElementos = 16;
//            gruposAfect = UtilsJmodbus.obtenerElementosAfectados(balastArray, gruposOffset, cuantosElementos, tamReg, 8, 8);
//            balasto.setGruposAfectados(gruposAfect);
//
//
//
//
//            //Save array
//            dao.setRegValue(initOffset, balastArray);
//            addBalast(balastNumber);// agrega el indice a la lista de balastros en memoria
//
//            //MODO
//            setSingleReg(0, 0);
//
//            System.out.println("Balast No.:" + balastNumber + " saved.");
//            state = true;
//        } catch (Exception e) {
//            state = false;
//            e.printStackTrace();
//        }

       
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

//        BalastoDAOJmodbus dao=new BalastoDAOJmodbus(new DAOJmodbus());


        Balasto selectedBalast = BalastoDAOJmodbus.readBalast(Integer.parseInt(num));
        ppalView.getBalastoDir_jTextField().setText(String.valueOf(selectedBalast.getDir()));
        ppalView.getBalastoMin_jTextField().setText(String.valueOf(selectedBalast.getMin()));
        ppalView.getBalastoMax_jTextField().setText(String.valueOf(selectedBalast.getMax()));
        ppalView.getBalastoFT_jTextField().setText(String.valueOf(selectedBalast.getFt()));
        ppalView.getBalastoFR_jTextField().setText(String.valueOf(selectedBalast.getFr()));
        ppalView.getBalastoLF_jTextField().setText(String.valueOf(selectedBalast.getLf()));
        ppalView.getBalastoLX_jTextField().setText(String.valueOf(selectedBalast.getLx()));
        ppalView.getBalastoPot_jTextField().setText(String.valueOf(selectedBalast.getPot()));
        ppalView.getjLabel41().setText(num);
//        ppalView.getBalastoNum_jComboBox().setSelectedIndex(0);

        gruposPert(num);
        ecenasPert(num);


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

    /**
     * Método que limpia o selecciona todas las escenas.
     *
     * @param ppalView
     * @param act boolean que representa si se seleccionan o se deseleccionan
     * todas las escenas
     */
    private void seleccionEscenas(PpalView ppalView, boolean act) {
        ppalView.getSliderConValor1().getCheckBox().setSelected(act);
        ppalView.getSliderConValor2().getCheckBox().setSelected(act);
        ppalView.getSliderConValor3().getCheckBox().setSelected(act);
        ppalView.getSliderConValor4().getCheckBox().setSelected(act);
        ppalView.getSliderConValor5().getCheckBox().setSelected(act);
        ppalView.getSliderConValor6().getCheckBox().setSelected(act);
        ppalView.getSliderConValor7().getCheckBox().setSelected(act);
        ppalView.getSliderConValor8().getCheckBox().setSelected(act);
        ppalView.getSliderConValor9().getCheckBox().setSelected(act);
        ppalView.getSliderConValor10().getCheckBox().setSelected(act);
        ppalView.getSliderConValor11().getCheckBox().setSelected(act);
        ppalView.getSliderConValor12().getCheckBox().setSelected(act);
        ppalView.getSliderConValor13().getCheckBox().setSelected(act);
        ppalView.getSliderConValor14().getCheckBox().setSelected(act);
        ppalView.getSliderConValor15().getCheckBox().setSelected(act);
        ppalView.getSliderConValor16().getCheckBox().setSelected(act);

    }

    /**
     * Método que marca en la GUI los grupos a los que pertenece un balasto
     *
     * @param numBalasto balasto al cual se le van a buscar los grupos
     */
    public void gruposPert(String numBalasto) {
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Método que marca en la GUI las escenas a las que pertenece un balasto
     * estableciendo los valores del nivel
     *
     * @param numBalasto
     */
    private void ecenasPert(String numBalasto) {
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void buscarElementos(String numBalasto) {
    }
}