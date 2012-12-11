/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Inicio.java
 *
 * Created on 08-sep-2011, 6:51:50
 */
package com.isolux.view;

import com.isolux.dao.modbus.DAOJamod;
import com.isolux.dao.properties.PropHandler;
import com.isolux.utils.LimitadorDeCaracteresNum_InputVerifier;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

/**
 *
 * @author EAFIT
 */
public class Inicio extends javax.swing.JFrame {

    Conectar conn=new Conectar();

    /**
     * Creates new form Inicio
     */
    public Inicio() {
        applicationTheme();
        initComponents();

        getConnectionInfo();
    }

    private void getConnectionInfo() {
        ipTextField.setText(PropHandler.getProperty("general.ip"));
        puertoInicio_jTextField.setText(PropHandler.getProperty("general.port"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        conectar_jButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ipTextField = new javax.swing.JFormattedTextField();
        puertoInicio_jTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio");
        setResizable(false);

        conectar_jButton.setText("Conectar");
        conectar_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectar_jButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 101, 137));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Estado: ");

        jLabel3.setText("Sin conexion");

        jLabel4.setText("Ip");

        ipTextField.setText("192.168.0.120");
        ipTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipTextFieldActionPerformed(evt);
            }
        });

        puertoInicio_jTextField.setText("503");
        puertoInicio_jTextField.setInputVerifier(new LimitadorDeCaracteresNum_InputVerifier(65535));
        puertoInicio_jTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puertoInicio_jTextFieldActionPerformed(evt);
            }
        });

        jLabel5.setText("Puerto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(puertoInicio_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(conectar_jButton))
                            .addComponent(ipTextField)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conectar_jButton)
                    .addComponent(jLabel5)
                    .addComponent(puertoInicio_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void conectar_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectar_jButtonActionPerformed
        try {
            conn=new Conectar();
            conn.execute();

        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_conectar_jButtonActionPerformed

    private void ipTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipTextFieldActionPerformed
        try {
            conn=new Conectar();
            conn.execute();

        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ipTextFieldActionPerformed

    private void puertoInicio_jTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puertoInicio_jTextFieldActionPerformed
       try {
            conn=new Conectar();
            conn.execute();

        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_puertoInicio_jTextFieldActionPerformed

    /**
     * Set the application theme
     */
    private void applicationTheme() {

        //jLayeredPane1.add(jPanel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(PpalView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Validates a ip addres.
     *
     * @param ip
     * @return
     */
    public boolean ipValidator(String ip) {
        boolean match = false;
        Pattern pattern = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        Matcher matcher = pattern.matcher(ip);

        if (matcher.find() && matcher.group().equals(ip)) {
            match = true;
        } else {
            match = false;
        }

        return match;
    }

    /**
     * Validates a port number.
     *
     * @param port
     * @return
     */
    public boolean portValidator(String port) {
        boolean match = false;
        Pattern pattern = Pattern.compile("[0-9]{1,5}");
        Matcher matcher = pattern.matcher(port);

        if (((matcher.find()) && (matcher.group().equals(port)))
                && ((Integer.parseInt(port) > 0) && (Integer.parseInt(port) <= 65535))) {
            match = true;
        } else {
            match = false;
        }

        return match;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            Inicio inicio = new Inicio();

            public void run() {
                inicio.setLocationRelativeTo(null);
                inicio.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton conectar_jButton;
    private javax.swing.JFormattedTextField ipTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField puertoInicio_jTextField;
    // End of variables declaration//GEN-END:variables

    private void conectar() {
        try {

            conectar_jButton.setEnabled(false);
            ipTextField.setEnabled(false);
            puertoInicio_jTextField.setEnabled(false);
            jLabel3.setText("Conectando...");

            String portString=this.puertoInicio_jTextField.getText();
            int port = Integer.parseInt(portString);
            
            String ip = ipTextField.getText();

            if (ipValidator(ip)) {
                //        System.out.println("Connection state: " + DAOJamod.testConnection(ip, port));

                PropHandler.setProperty("general.ip", ip);
                PropHandler.setProperty("general.port", portString);
                if (DAOJamod.testConnection(ip, port)) {
                    //TODO: Get info and give it to the ppal
                    //Getting info...
//                DAO4j.readMemory();

                    //Show the main view.
                    PpalView ppal = new PpalView();
                    ppal.setLocationRelativeTo(null);
                    ppal.setVisible(true);
//                    conn.cancel(true);
                    this.dispose(); //oculta la ventana actual (Inicio.java)
                    

                } else {


                    ConnectException ce = new ConnectException("La tarjeta no esta bien conectada");
                    throw ce;
                }

            }
        } catch (ConnectException ce) {
            conectar_jButton.setEnabled(true);
            ipTextField.setEnabled(true);
            puertoInicio_jTextField.setEnabled(true);
            jLabel3.setText("No se puede establecer una conexion!");
            JOptionPane.showMessageDialog(rootPane, "No se pudo conectar a la tarjeta. Revise su conexion " + ce.getLocalizedMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE,"No se pudo conectar",ce);


//            ce.printStackTrace();
        }
    }

    /**
     * Clase interna que se encarga de la conexion en un hilo separado.
     */
    private class Conectar extends SwingWorker<String, Boolean> {

        @Override
        protected String doInBackground() throws Exception {
            conectar();
            

//            try {
//
//                conectar_jButton.setEnabled(false);
//                ipTextField.setEnabled(false);
//                jLabel3.setText("Conectando...");
//
//                int port = 502;
//                String ip = ipTextField.getText();
//
//                if (ipValidator(ip)) {
//                    //        System.out.println("Connection state: " + DAOJamod.testConnection(ip, port));
//
//                    PropHandler.setProperty("general.ip", ip);
//                    PropHandler.setProperty("general.port", String.valueOf(port));
//                    if (DAOJamod.testConnection(ip, port)) {
//                        //TODO: Get info and give it to the ppal
//                        //Getting info...
////                DAO4j.readMemory();
//
//                        //Show the main view.
//                        PpalView ppal = new PpalView();
//                        ppal.setLocationRelativeTo(null);
//                        ppal.setVisible(true);
//                        ppal.setTitle(ppal.getTitle() + " - " + ip);
//
//                        dispose();
//
//                    } else {
//
//
//                        ConnectException ce = new ConnectException("La tarjeta no esta bien conectada");
//                        throw ce;
//                        
//                    }
//
//                }
//            } catch (ConnectException ce) {
//                try {
//                   
//                    ce.printStackTrace();
////                    conn.cancel(true);
////                    conn.finalize();
//                    conectar_jButton.setEnabled(true);
//                    ipTextField.setEnabled(true);
//                    jLabel3.setText("No se puede establecer una conexion!");
//                    JOptionPane.showMessageDialog(rootPane, "No se pudo conectar a la tarjeta. Revise su conexion " + ce.getLocalizedMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
//
//
//    //            ce.printStackTrace();
//                } catch (Throwable ex) {
//                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
//                    ex.printStackTrace();
//                }
//            }
//
//

//            this.cancel(true);
            return "Terminada";
        }

        @Override
        protected void done() {

            System.out.println("Terminado el hilo de conexion desde el done.");

//            dispose();

            try {
                System.out.println("Terminando...");

//                System.out.println("CANCELADO? " + conn.isCancelled());
//                System.out.println("Esta terminada? " + conn.isDone());

            } catch (Throwable ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
