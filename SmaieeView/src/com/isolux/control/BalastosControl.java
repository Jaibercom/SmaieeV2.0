/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.bo.*;
import com.isolux.dao.jmodbus.BalastoDAOJmodbus;
import com.isolux.dao.jmodbus.OperacionesElemento_Interface;
import com.isolux.dao.properties.PropHandler;
import com.isolux.utils.Validacion;
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
public class BalastosControl implements OperacionesElemento_Interface, ElementoControl_Interface {

    /**
     * Gets the inserted balasts. Obtiene los balastos insertadod, y los muestra
     * en el
     */
    @Override
    public void readElements(PpalView ppalView) {
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
    @Override
    public void deleteElement(PpalView ppalView) {
//        boolean connectionStatus = DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(true, ppalView);
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());
        if (true) {
            if (ppalView.getSelectedBalastNumber() != null && !ppalView.getSelectedBalastNumber().equals("")) {
                DefaultMutableTreeNode nodeToDelete = (DefaultMutableTreeNode) ppalView.getArbol_jTree().getLastSelectedPathComponent();
                DefaultTreeModel treeModel = (DefaultTreeModel) ppalView.getArbol_jTree().getModel();
                dao.deleteBalast(ppalView.getSelectedBalastNumber());
                treeModel.removeNodeFromParent(nodeToDelete);
                ppalView.getBalasts().remove(ppalView.getSelectedBalastNumber());
                ppalView.getBalastoNum_jComboBox().addItem(ppalView.getSelectedBalastNumber());
                cleanView(ppalView);
            }
        }
        refrescarVista(ppalView);

    }

    /**
     * Send the balast to be saved or updated.
     */
    @Override
    public void saveElement(PpalView ppalView) {
//        boolean connectionStatus = DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(true, ppalView);
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());

        if (true) {

            boolean isUpdate = !ppalView.getjLabel41().getText().equals("#") || !ppalView.getSelectedBalastNumber().equals("");
            int balastNumber = !ppalView.getjLabel41().getText().equals("#") ? Integer.parseInt(ppalView.getjLabel41().getText()) : Integer.parseInt((String) ppalView.getBalastoNum_jComboBox().getSelectedItem());

            int level = Integer.parseInt(ppalView.getjTextField20().getText());
            int activation = 0; //Integer.parseInt(ppalView.getjTextField21().getText());
            String name = ppalView.getjTextField1().getText();
            if (name.length() > 10) {
                JOptionPane.showConfirmDialog(null, "El nombre es demasiado largo.\nDebe tener máximo 10 caracteres.", "Alerta", -1, JOptionPane.ERROR_MESSAGE);
                return;
            }

            int dir = Integer.parseInt(ppalView.getBalastoDir_jTextField().getText());
            int min = Integer.parseInt(ppalView.getBalastoMin_jTextField().getText());
            int max = Integer.parseInt(ppalView.getBalastoMax_jTextField().getText());
            int ft = Integer.parseInt(ppalView.getBalastoFT_jTextField().getText());
            int fr = Integer.parseInt(ppalView.getBalastoFR_jTextField().getText());
            int lf = Integer.parseInt(ppalView.getBalastoLF_jTextField().getText());
            int lx = Integer.parseInt(ppalView.getBalastoLX_jTextField().getText());
            int pot = Integer.parseInt(ppalView.getBalastoPot_jTextField().getText());


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
                TreePath path = ppalView.getArbol_jTree().getNextMatch(PropHandler.getProperty("balast.menu.name"), 0, Position.Bias.Forward);
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
                    DefaultTreeModel model = (DefaultTreeModel) ppalView.getArbol_jTree().getModel();
                    TreePath path = ppalView.getArbol_jTree().getNextMatch(PropHandler.getProperty("balast.menu.name"), 0, Position.Bias.Forward);
                    MutableTreeNode balastNode = (MutableTreeNode) path.getLastPathComponent();
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(String.valueOf(balast.getBalastNumber()) + " - " + String.valueOf(balast.getName()));
                    model.insertNodeInto(newNode, balastNode, balastNode.getChildCount());

                    //Remove balast from the list of available ones (jComboBox)
                    ppalView.getBalastoNum_jComboBox().removeItem(balastNumber);
                    ppalView.getjLabel41().setText(String.valueOf(balastNumber));
                }
            }
        }
        refrescarVista(ppalView);
    }

    @Override
    public void filterAddedElements(PpalView ppalView) {
        if (!ppalView.getBalastsStauts()) {
            ppalView.getStatusLabel().setText("Leyendo balastos de la tarjeta...");
            readElements(ppalView);
            int startRow = 0;
            String prefix = PropHandler.getProperty("balast.menu.name");
            TreePath path = ppalView.getArbol_jTree().getNextMatch(prefix, startRow, Position.Bias.Forward);

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent(); //TODO: nodo de balasts.
            DefaultTreeModel model = (DefaultTreeModel) ppalView.getArbol_jTree().getModel();

            //Remove the used balast numbers from the list and add them to the menu.
            HashMap<String, Balasto> balasts = ppalView.getBalasts();
            Set<String> balastsKeys = balasts.keySet();
            for (String string : balastsKeys) {
                ppalView.getBalastoNum_jComboBox().removeItem(string);
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
    @Override
    public void showSelectedElement(String balastNumber, PpalView ppalView) {
        Balasto selectedBalast = ppalView.getBalasts().get(balastNumber);

        ppalView.getjTextField1().setText(selectedBalast.getName());
        ppalView.getjTextField20().setText(String.valueOf(selectedBalast.getLevel()));
        ppalView.getBalastoDir_jTextField().setText(String.valueOf(selectedBalast.getDir()));
        ppalView.getBalastoMin_jTextField().setText(String.valueOf(selectedBalast.getMin()));
        ppalView.getBalastoMax_jTextField().setText(String.valueOf(selectedBalast.getMax()));
        ppalView.getBalastoFT_jTextField().setText(String.valueOf(selectedBalast.getFt()));
        ppalView.getBalastoFR_jTextField().setText(String.valueOf(selectedBalast.getFr()));
        ppalView.getBalastoLF_jTextField().setText(String.valueOf(selectedBalast.getLf()));
        ppalView.getBalastoLX_jTextField().setText(String.valueOf(selectedBalast.getLx()));
        ppalView.getBalastoPot_jTextField().setText(String.valueOf(selectedBalast.getPot()));
        ppalView.getjLabel41().setText(balastNumber);
        ppalView.getBalastoNum_jComboBox().setSelectedIndex(0);
    }

    /**
     * Clean values fror balasts form.
     */
    @Override
    public void cleanView(PpalView ppalView) {
        ppalView.getjTextField1().setText("");
        ppalView.getjTextField20().setText("0");
        ppalView.getBalastoDir_jTextField().setText("0");
        ppalView.getBalastoMin_jTextField().setText("0");
        ppalView.getBalastoMax_jTextField().setText("0");
        ppalView.getBalastoFT_jTextField().setText("0");
        ppalView.getBalastoFR_jTextField().setText("0");
        ppalView.getBalastoLF_jTextField().setText("0");
        ppalView.getBalastoLX_jTextField().setText("0");
        ppalView.getBalastoPot_jTextField().setText("0");
        ppalView.getjLabel41().setText("#");

        ppalView.setSelectedBalastNumber("");
        ppalView.getBalastoNum_jComboBox().setSelectedIndex(0);
        elementosDisponibles(ppalView);
    }

    /**
     * Validates the information inside the balasts form.
     *
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
     * Refresca la vista de balastos cuando se ejecuta una operacion de
     * escritura y borrado
     *
     * @param ppalView
     */
    @Override
    public void refrescarVista(PpalView ppalView) {
        cleanView(ppalView);
        filterAddedElements(ppalView);
        String[] elementosDisponibles = elementosDisponibles(ppalView);
        Validacion.actualizarCombo(ppalView.getBalastoNum_jComboBox(), elementosDisponibles, Validacion.BALASTOS_DISPONIBLES);
        ppalView.getBalastoNombreSmaiee_jTextField().requestFocusInWindow();

    }

    @Override
    public String[] elementosDisponibles(PpalView ppalView) {
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());
        String[] ses;
        ses = dao.elementosSinGrabar();

        return ses;
    }

    @Override
    public String[] elementosSinGrabar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
