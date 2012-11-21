/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Sebastian Rodriguez
 */
public class LimitadorDeCaracteresIp_Document extends PlainDocument {

    JTextField editor;
    private int numeroMaximoCaracteres;

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        
        StringBuilder texto = new StringBuilder(editor.getText());
        try {

            if (str.equals(".")) {
                //verificamos que el numero anterior al punto sea menos a 255
                String substring = texto.substring(texto.lastIndexOf("."), texto.length());
                try {
                    
                    if (Integer.parseInt(substring)<256) {
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Ingrese un numero menor a 255");
                        return;
                    }
                } catch (NumberFormatException numberFormatException) {
                    return;
                }
                
            } else {
                Integer.parseInt(str);
            }

            
            texto.append(str);


            if (texto.length() <= numeroMaximoCaracteres) {
                
            } else {
                
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una dirección ip válida.");
            return;
        }
        super.insertString(offs, str, a);
    }

    public LimitadorDeCaracteresIp_Document(JTextField editor, int numeroMaximoCaracteres) {
        this.editor = editor;
        this.numeroMaximoCaracteres = numeroMaximoCaracteres;

    }
}
