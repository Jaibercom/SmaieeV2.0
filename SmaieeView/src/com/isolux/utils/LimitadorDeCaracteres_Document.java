/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Clase usada para limitar los caracteres de un texto a sólo numeros.
 *
 * @author Juan Camilo Canias Gómez
 */
public class LimitadorDeCaracteres_Document extends PlainDocument {

    JTextField editor;
    private int numeroMaximoCaracteres;
    private Integer valorMaximo;
    private Integer entero = null;
    private Integer valor = null;

 
    
    public Integer getValor() {
        return valor;
    }

    public LimitadorDeCaracteres_Document(JTextField editor, int numeroMaximoCaracteres, Integer valorMaximo) {
        this.editor = editor;
        this.numeroMaximoCaracteres = numeroMaximoCaracteres;
        this.valorMaximo = valorMaximo;
    }

    

    @Override
    public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
        String text = editor.getText();
//        Integer entero = null;
//        Integer valor = null;

        try {
            entero = Integer.parseInt(arg1);

        } catch (NumberFormatException numberFormatException) {
            entero = null;
        }

        try {
            String concat = text.concat(arg1);
            valor = Integer.parseInt(concat);
        } catch (NumberFormatException numberFormatException) {
            valor = 0;
        }


        if ((text.length() + arg1.length()) > this.numeroMaximoCaracteres || entero == null || valor > valorMaximo) {
            return;
        }
        super.insertString(arg0, arg1, arg2);
    }
}
