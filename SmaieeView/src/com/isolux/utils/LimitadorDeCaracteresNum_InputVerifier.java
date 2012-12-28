/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase verificadora de numeros.
 *
 * @author Juan Camilo Canas Gómez
 */
public class LimitadorDeCaracteresNum_InputVerifier extends InputVerifier {

    Integer limiteMaximo;
    Integer numCaracteres;

    public LimitadorDeCaracteresNum_InputVerifier(Integer limiteMaximo) {
        this.limiteMaximo = limiteMaximo;
    }

    public Integer getNumCaracteres() {
        return numCaracteres;
    }

    public void setNumCaracteres(Integer numCaracteres) {
        this.numCaracteres = numCaracteres;
    }

    @Override
    public boolean verify(JComponent editor) {
        boolean verificado = false;

        if (editor instanceof JTextField) {

            String texto = ((JTextField) editor).getText();

            try {
                int numero = Integer.parseInt(texto);

                if (numero > limiteMaximo*10) {
                    throw new NumberFormatException();

                } else {
                    
                    editor.setForeground(Color.black);
                    
                    verificado = true;

                }


            } catch (NumberFormatException ex) {
                verificado = false;
                Logger.getLogger(LimitadorDeCaracteresNum_InputVerifier.class.getName()).log(Level.WARNING, null, ex.getMessage());
                
                
                editor.setForeground(Color.red);
                ((JTextField)editor).selectAll();
                JOptionPane.showMessageDialog(null, "El campo debe contener un numero entre 0 y " + limiteMaximo*10, "Error", JOptionPane.WARNING_MESSAGE);
                
            }

        }
        return verificado;
    }
}
