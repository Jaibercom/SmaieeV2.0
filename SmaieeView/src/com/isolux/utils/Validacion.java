/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

import com.isolux.view.PpalView;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author EAFIT
 */
public class Validacion {

    //Confirmation dialog title
    private static final String VALIDATION = "Verificar";
    private static final String ERROR_MSG = "Error";
    private static final String WARNING_MSG = "Alerta";
    //Confirmation dialog type.
    private static final int WARNING_DIALOG = JOptionPane.WARNING_MESSAGE;
    private static final int ERROR_DIALOG = JOptionPane.ERROR;
    /**
     *Balastos disponibles son los balastos que no han sido creados aun en el smaiee
     * y por tanto probablemente no estén configurados. Son los que se representan con 0
     */
    public static final int BALASTOS_DISPONIBLES = 1;
    /**
     *Balastos no disponibles son los balastos que ya han sido creados en el smaiee. Son los que se representan con 1.
     * 
     */
    public static final int BALASTOS_NO_DISPONIBLES = 2;
    /**
     *Se usa para indicar que se quieren filtrar todos los balastos, incluyendo 
     * disponibles y no disponibles (0 y 1 respectivamente)
     */
    public static final int BALASTOS_TODOS = 3;

    /**
     * Shows an error message.
     *
     * @param message
     */
    public static void showAlertMessage(String message) {
        JOptionPane.showConfirmDialog(null, message, "Alerta", -1, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Shows an error message.
     *
     * @param message
     */
    public static void showMessage(String message, String title, int type) {
        JOptionPane.showConfirmDialog(null, message, title, -1, type);
    }

    /**
     * Validates the balasts view.
     *
     * @param ppalView
     * @return
     */
    public static boolean validateBalastsView(PpalView ppalView) {
        String validationResult = "Verificar:\n";
        boolean result = true;

        if (!ppalView.getjTextField1().getText().isEmpty()) { //nombre
            validationResult += "- Ingrese el nombre\n";
            result = false;
        }

        if (!ppalView.getjTextField20().getText().isEmpty()) { //nivel
        }
        if (!ppalView.getBalastoDir_jTextField().getText().isEmpty() || !ppalView.getBalastoDir_jTextField().getText().equals("0")) { //Dir
            validationResult += "- Dir: Debe especificar la dirección\n";
            result = false;
        }
        if (!ppalView.getjTextField27().getText().isEmpty()) { //Min
        }
        if (!ppalView.getjTextField28().getText().isEmpty()) { //Max
        }
        if (!ppalView.getjTextField29().getText().isEmpty()) { //FT
        }
        if (!ppalView.getjTextField30().getText().isEmpty()) { //FR
        }
        if (!ppalView.getjTextField31().getText().isEmpty()) { //LF
        }
        if (!ppalView.getjTextField32().getText().isEmpty()) { //LX
        }
        if (!ppalView.getjTextField33().getText().isEmpty()) { //Pot
        }
        if (ppalView.getBalastoNum_jComboBox().getSelectedIndex() == 0) { //No. balasto
            validationResult += "- Debe seleccionar un número de balasto\n";
            result = false;
        }

        showMessage(validationResult, VALIDATION, WARNING_DIALOG);
        return result;
    }

    /**
     * Validates the configuration view.
     *
     * @param ppalView
     * @return
     */
    public static boolean validateConfigurationView(PpalView ppalView) {

        return true;
    }

    /**
     * Validates the group view.
     *
     * @param ppalView
     * @return
     */
    public static boolean validateGroupsView(PpalView ppalView) {

        return true;
    }

    /**
     * Validates the scenes view.
     *
     * @param ppalView
     * @return
     */
    public static boolean validateScenesView(PpalView ppalView) {

        return true;
    }

    /**
     * Validates the buttons view.
     *
     * @param ppalView
     * @return
     */
    public static boolean validateButtonsView(PpalView ppalView) {

        return true;
    }

    /**
     * Validates the fotocelda view.
     *
     * @param ppalView
     * @return
     */
    public static boolean validateFotoceldaView(PpalView ppalView) {

        return true;
    }

    /**
     * Validates the sensor view.
     *
     * @param ppalView
     * @return
     */
    public static boolean validateSensorView(PpalView ppalView) {

        return true;
    }

    /**
     * Validates the events view.
     *
     * @param ppalView
     * @return
     */
    public static boolean validateEventsView(PpalView ppalView) {

        return true;
    }

    /**
     * Método que actualiza el combobox que se le pase por parámetros con la
     * lista de los elementos no se han grabado aun en la tarjeta, como por
     * ejemplo los balastros que todavia no se han grabado en la tarjeta.
     *
     * @param combo ComboBox para ser procesado
     * @param elementosDisponibles Lista de elementos que aun no se han grabado
     * en la tarjeta
     * @return ComboBox procesado
     */
    public static JComboBox<String> actualizarCombo(JComboBox combo, String[] elementosDisponibles, int disponibilidadBalasto) {

        Vector<String> valores = new Vector<String>();
        for (int i = 0; i < elementosDisponibles.length; i++) {
            switch (disponibilidadBalasto) {
                case BALASTOS_DISPONIBLES:
                    if (elementosDisponibles[i].equals("0")) {
                        valores.add(Integer.toString(i));
                    }

                    break;

                case BALASTOS_NO_DISPONIBLES:
                    if (elementosDisponibles[i].equals("1")) {
                        valores.add(Integer.toString(i));
                    }
                    break;
                case BALASTOS_TODOS:
                    valores.add(Integer.toString(i));
                    break;
            }
        }
        DefaultComboBoxModel model = new DefaultComboBoxModel(valores);
        combo.setModel(model);


        return combo;

    }
}
