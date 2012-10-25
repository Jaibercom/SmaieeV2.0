/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

import com.isolux.view.PpalView;
import javax.swing.JOptionPane;

/**
 *
 * @author EAFIT
 */
public class Validation {
    
    //Confirmation dialog title
    private static final String VALIDATION = "Verificar";
    private static final String ERROR_MSG = "Error";
    private static final String WARNING_MSG = "Alerta";
    
    //Confirmation dialog type.
    private static final int WARNING_DIALOG = JOptionPane.WARNING_MESSAGE;
    private static final int ERROR_DIALOG = JOptionPane.ERROR;
    
    /**
     * Shows an error message.
     * @param message 
     */
    public static void showAlertMessage(String message) {
        JOptionPane.showConfirmDialog(null, message, "Alerta", -1, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Shows an error message.
     * @param message 
     */
    public static void showMessage(String message, String title, int type) {
        JOptionPane.showConfirmDialog(null, message, title, -1, type);
    }
    
    
    /**
     * Validates the balasts view.
     * @param ppalView
     * @return 
     */
    public static boolean validateBalastsView(PpalView ppalView){
        String validationResult = "Verificar:\n";
        boolean result = true;
        
        if (!ppalView.getjTextField1().getText().isEmpty()) { //nombre
            validationResult += "- Ingrese el nombre\n";
            result = false;
        }
        
        if (!ppalView.getjTextField20().getText().isEmpty()) { //nivel
            
        }
        if (!ppalView.getjTextField26().getText().isEmpty() || !ppalView.getjTextField26().getText().equals("0")) { //Dir
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
     * @param ppalView
     * @return 
     */
    public static boolean validateConfigurationView(PpalView ppalView){
        
        return true;
    }
    
     /**
     * Validates the group view.
     * @param ppalView
     * @return 
     */
    public static boolean validateGroupsView(PpalView ppalView){
        
        return true;
    }
    
     /**
     * Validates the scenes view.
     * @param ppalView
     * @return 
     */
    public static boolean validateScenesView(PpalView ppalView){
        
        return true;
    }
    
     /**
     * Validates the buttons view.
     * @param ppalView
     * @return 
     */
    public static boolean validateButtonsView(PpalView ppalView){
        
        return true;
    }
    
    /**
     * Validates the fotocelda view.
     * @param ppalView
     * @return 
     */
    public static boolean validateFotoceldaView(PpalView ppalView){
        
        return true;
    }
    
    /**
     * Validates the sensor view.
     * @param ppalView
     * @return 
     */
    public static boolean validateSensorView(PpalView ppalView){
        
        return true;
    }
    
    /**
     * Validates the events view.
     * @param ppalView
     * @return 
     */
    public static boolean validateEventsView(PpalView ppalView){
        
        return true;
    }
}
