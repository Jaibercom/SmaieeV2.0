/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.dao.properties.PropHandler;
import com.isolux.bo.*;
import com.isolux.dao.jmodbus.BalastoDAOJmodbus;
import com.isolux.view.PpalView;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Juan Diego Toro
 */
public class BalastosControlJmodbus {
    
    
    /**
     * Gets the inserted balasts.
     */
    public void readBalastos(PpalView ppalView) {
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());
        HashMap<String, Balasto> balasts = ppalView.getBalasts();
        
        if (balasts == null || balasts.size() == 0) {
            ppalView.setBalasts(new HashMap<String, Balasto>());

            //Added balasts numbers.
            ArrayList<String> addedBalasts = PropHandler.getAddedBalasts(dao);

            //Balasts.
            for (String balastNumber : addedBalasts) {
                balasts.put(balastNumber, dao.readBalast(Integer.parseInt(balastNumber)));
            }
            ppalView.setBalasts(balasts);
            
        }
    }

    /**
     * Deletes a balast.
     */
    public void deleteBalast(PpalView ppalView) {
//        boolean connectionStatus = DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(true, ppalView);
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());
        if (true) {
            if (ppalView.getSelectedBalastNumber() != null && !ppalView.getSelectedBalastNumber().equals("")) {
                DefaultMutableTreeNode nodeToDelete = (DefaultMutableTreeNode) ppalView.getjTree1().getLastSelectedPathComponent();
                DefaultTreeModel treeModel = (DefaultTreeModel) ppalView.getjTree1().getModel();
                dao.deleteBalast(ppalView.getSelectedBalastNumber());
                treeModel.removeNodeFromParent(nodeToDelete);
                ppalView.getBalasts().remove(ppalView.getSelectedBalastNumber());
                ppalView.getjComboBox3().addItem(ppalView.getSelectedBalastNumber());
                cleanBalastosView(ppalView);
            }
        }
         refrescaVistaBalastos(ppalView);
    }

    /**
     * Send the balast to be saved or updated.
     */
    public void saveBalast(PpalView ppalView) {
//        boolean connectionStatus = DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(true, ppalView);
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());
        
        if (true) {

            boolean isUpdate = !ppalView.getjLabel41().getText().equals("#") || !ppalView.getSelectedBalastNumber().equals("");
            int balastNumber = !ppalView.getjLabel41().getText().equals("#") ? Integer.parseInt(ppalView.getjLabel41().getText()) : Integer.parseInt((String) ppalView.getjComboBox3().getSelectedItem());
            
            int level = Integer.parseInt(ppalView.getjTextField20().getText());
            int activation = 0; //Integer.parseInt(ppalView.getjTextField21().getText());
            String name = ppalView.getjTextField1().getText();
            if (name.length() > 10) {
                JOptionPane.showConfirmDialog(null, "El nombre es demasiado largo.\nDebe tener máximo 10 caracteres.", "Alerta", -1, JOptionPane.ERROR_MESSAGE);
                return;
            }

            int dir = Integer.parseInt(ppalView.getjTextField26().getText());
            int min = Integer.parseInt(ppalView.getjTextField27().getText());
            int max = Integer.parseInt(ppalView.getjTextField28().getText());
            int ft = Integer.parseInt(ppalView.getjTextField29().getText());
            int fr = Integer.parseInt(ppalView.getjTextField30().getText());
            int lf = Integer.parseInt(ppalView.getjTextField31().getText());
            int lx = Integer.parseInt(ppalView.getjTextField32().getText());
            int pot = Integer.parseInt(ppalView.getjTextField33().getText());


            ppalView.getStatusLabel().setText("Guardando balasto");
            Balasto balast = new Balasto(balastNumber, level, activation, name, dir, min, max, ft, fr, lf, lx, pot);

            //Saves the balast remotelly
            boolean resultado = dao.saveBalast(balast);
            HashMap<String, Balasto> ppalBalasts = ppalView.getBalasts();

            if (isUpdate) {

                //Update balast locally.
                ppalBalasts.remove(String.valueOf(balast.getBalastNumber()));
                ppalBalasts.put(String.valueOf(balast.getBalastNumber()), balast);

                if (resultado) {
                    ppalView.getStatusLabel().setText("Balasto actualizado.");
                } else {
                    ppalView.getStatusLabel().setText("Ocurrió un error guardando el balasto");
                }

                //Update name in the tree.
                TreePath path = ppalView.getjTree1().getNextMatch(PropHandler.getProperty("balast.menu.name"), 0, Position.Bias.Forward);
                MutableTreeNode balastNode = (MutableTreeNode) path.getLastPathComponent();
                Enumeration balasts = balastNode.children();
                while (balasts.hasMoreElements()) {
                    DefaultMutableTreeNode updatedBalast = (DefaultMutableTreeNode) balasts.nextElement();
                    String[] b = updatedBalast.getUserObject().toString().split(" - ");
                    if (b[0].equals(String.valueOf(balastNumber))) {
                        updatedBalast.setUserObject(b[0] + " - " + balast.getName());
                    }
                }

            } else {
                if (validateBalastoForm()) {
                    ppalBalasts.put(String.valueOf(balast.getBalastNumber()), balast);

                    if (resultado) {
                        ppalView.getStatusLabel().setText("Balasto guardado");
                    } else {
                        ppalView.getStatusLabel().setText("Ocurrió un error guardando el balasto balasto");
                    }

                    //Show balast in tree
                    DefaultTreeModel model = (DefaultTreeModel) ppalView.getjTree1().getModel();
                    TreePath path = ppalView.getjTree1().getNextMatch(PropHandler.getProperty("balast.menu.name"), 0, Position.Bias.Forward);
                    MutableTreeNode balastNode = (MutableTreeNode) path.getLastPathComponent();
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(String.valueOf(balast.getBalastNumber()) + " - " + String.valueOf(balast.getName()));
                    model.insertNodeInto(newNode, balastNode, balastNode.getChildCount());

                    //Remove balast from the list of available ones (jComboBox)
                    ppalView.getjComboBox3().removeItem(balastNumber);
                    ppalView.getjLabel41().setText(String.valueOf(balastNumber));
                }
            }
        }
         refrescaVistaBalastos(ppalView);
    }

    /**
     * Filter the used balasts.
     */
    public void filterAddedBalasts(PpalView ppalView) {
        if (!ppalView.getBalastsStauts()) {
            ppalView.getStatusLabel().setText("Leyendo balastos de la tarjeta...");
            readBalastos(ppalView);
            int startRow = 0;
            String prefix = PropHandler.getProperty("balast.menu.name");
            TreePath path = ppalView.getjTree1().getNextMatch(prefix, startRow, Position.Bias.Forward);

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent(); //TODO: nodo de balasts.
            DefaultTreeModel model = (DefaultTreeModel) ppalView.getjTree1().getModel();

            //Remove the used balast numbers from the list and add them to the menu.
            HashMap<String, Balasto> balasts = ppalView.getBalasts();
            Set<String> balastsKeys = balasts.keySet();
            for (String string : balastsKeys) {
                ppalView.getjComboBox3().removeItem(string);
                Balasto balasto = balasts.get(string);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(string + " - " + balasto.getName());
                model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
            }
            ppalView.setBalastsStauts(true);
            ppalView.getStatusLabel().setText("Balastos leidos.");
        }
    }

    /**
     * Shows the selected balast.
     */
    public void showSelectedBalasto(String balastNumber, PpalView ppalView) {
        Balasto selectedBalast = ppalView.getBalasts().get(balastNumber);

        ppalView.getjTextField1().setText(selectedBalast.getName());
        ppalView.getjTextField20().setText(String.valueOf(selectedBalast.getLevel()));
        ppalView.getjTextField26().setText(String.valueOf(selectedBalast.getDir()));
        ppalView.getjTextField27().setText(String.valueOf(selectedBalast.getMin()));
        ppalView.getjTextField28().setText(String.valueOf(selectedBalast.getMax()));
        ppalView.getjTextField29().setText(String.valueOf(selectedBalast.getFt()));
        ppalView.getjTextField30().setText(String.valueOf(selectedBalast.getFr()));
        ppalView.getjTextField31().setText(String.valueOf(selectedBalast.getLf()));
        ppalView.getjTextField32().setText(String.valueOf(selectedBalast.getLx()));
        ppalView.getjTextField33().setText(String.valueOf(selectedBalast.getPot()));
        ppalView.getjLabel41().setText(balastNumber);
        ppalView.getjComboBox3().setSelectedIndex(0);
    }

    /**
     * Clean values fror balasts form.
     */
    public void cleanBalastosView(PpalView ppalView) {
        ppalView.getjTextField1().setText("");
        ppalView.getjTextField20().setText("0");
        ppalView.getjTextField26().setText("0");
        ppalView.getjTextField27().setText("0");
        ppalView.getjTextField28().setText("0");
        ppalView.getjTextField29().setText("0");
        ppalView.getjTextField30().setText("0");
        ppalView.getjTextField31().setText("0");
        ppalView.getjTextField32().setText("0");
        ppalView.getjTextField33().setText("0");
        ppalView.getjLabel41().setText("#");

        ppalView.setSelectedBalastNumber("");
        ppalView.getjComboBox3().setSelectedIndex(0);
    }

    /**
     * Validates the information inside the balasts form.
     * @return 
     */
    public boolean validateBalastoForm() {
        /*
         * Validar:
         * - numero de balasto
         * - Nombre: no mas de 10 digitos
         * - demas valores.
         */


//        JOptionPane.showConfirmDialog(null, "1. Verifique el nombre. \nNo puede tener mas de 10 caracteres.\n2. Ingrese un numero de balasto.", "Alerta", -1, JOptionPane.ERROR_MESSAGE);
        return true;
    }

    /**
     * Refresca la vista de balastos cuando se ejecuta una operacion de escritura y borrado
     * @param ppalView 
     */
    private void refrescaVistaBalastos(PpalView ppalView) {
          cleanBalastosView(ppalView);
          readBalastos(ppalView);
          filterAddedBalasts(ppalView);
    }
    
    
    
}
