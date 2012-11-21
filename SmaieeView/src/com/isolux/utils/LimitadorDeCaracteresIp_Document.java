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
        try {

            if (str.equals(".")) {
            } else {
                Integer.parseInt(str);
            }

            StringBuilder texto = new StringBuilder(editor.getText());
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
