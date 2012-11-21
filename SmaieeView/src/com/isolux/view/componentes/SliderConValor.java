/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.view.componentes;

import com.isolux.utils.LimitadorDeCaracteresNum_InputVerifier;
import com.isolux.utils.LimitadorDeCaracteres_Document;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author Juan Camilo Canias
 */
public class SliderConValor extends javax.swing.JPanel {

    private String label;
    private Integer numElemento;
    private Integer valorMaximo;
   LimitadorDeCaracteresNum_InputVerifier verifier=new LimitadorDeCaracteresNum_InputVerifier(100);

   

   
   
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        this.checkBox.setText(label);
    }

    
    
    public Integer getValor() {
        return this.jSlider.getValue();
               
    }

    public void setValor(Integer valor) {
        this.valorMaximo = valor;
        jSlider.setValue(valor);
        valor_TextField.setText(valor.toString());
    }
    
    

    public Integer getNumElemento() {
        return numElemento;
    }

    public void setNumElemento(int numElemento) {
        this.numElemento = numElemento;
    }
    
    

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public JSlider getjSlider() {
        return jSlider;
    }

    public JTextField getValor_TextField() {
        return valor_TextField;
    }

    /**
     * Creates new form SliderConValor
     */
    public SliderConValor() {

        initComponents();
    }

    public SliderConValor(Integer numElemento) {
        
        this.numElemento = numElemento;
//        initComponents();
        this.checkBox.setText(numElemento.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        checkBox = new javax.swing.JCheckBox();
        jSlider = new javax.swing.JSlider();
        valor_TextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        checkBox.setText("#");
        checkBox.setToolTipText("Seleccione si el balasto actual pertenece a este grupo");
        checkBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        checkBox.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        checkBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                checkBoxPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(checkBox, gridBagConstraints);

        jSlider.setMajorTickSpacing(50);
        jSlider.setMinorTickSpacing(10);
        jSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        jSlider.setPaintTicks(true);
        jSlider.setToolTipText("Nivel del balasto en la escena seleccionada");
        jSlider.setValue(0);
        jSlider.setPreferredSize(new java.awt.Dimension(10, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jSlider, gridBagConstraints);

        valor_TextField.setToolTipText("Valor numérico");
        valor_TextField.setInheritsPopupMenu(true);
        valor_TextField.setInputVerifier(new LimitadorDeCaracteresNum_InputVerifier(100));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), valor_TextField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(valor_TextField, gridBagConstraints);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void checkBoxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_checkBoxPropertyChange
        
        jSlider.setEnabled(checkBox.isSelected());
        if (checkBox.isSelected()==false) {
            valor_TextField.setText("0");
            
        }
        valor_TextField.setEnabled(checkBox.isSelected());
        
    }//GEN-LAST:event_checkBoxPropertyChange
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkBox;
    private javax.swing.JSlider jSlider;
    private javax.swing.JTextField valor_TextField;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public Integer getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Integer valorMaximo) {
        this.valorMaximo = valorMaximo;
    }
}
