/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.bo.*;
import com.isolux.dao.jmodbus.*;
import com.isolux.dao.modbus.DAOJamod;
import com.isolux.dao.properties.PropHandler;
import com.isolux.utils.Validacion;
import com.isolux.view.PpalView;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author EAFIT
 */
public class GrupoControl implements ElementoControl_Interface{

    /**
     * Saves a group.
     */
    @Override
    public void saveElement(PpalView ppalView) {
        boolean connectionStatus = true; //DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(connectionStatus, ppalView);
        if (connectionStatus) {

            ppalView.getStatusLabel().setText("Guardando grupo");
            boolean isUpdate = !ppalView.getjLabel58().getText().equals("#");
//            int groupNumber = ppalView.getjLabel58().getText().equals("#") ? PropHandler.getGroupNumber() : Integer.parseInt(ppalView.getjLabel58().getText());
            int groupNumber = !ppalView.getjLabel58().getText().equals("#") ? Integer.parseInt(ppalView.getjLabel58().getText()) : Integer.parseInt((String) ppalView.getGruposNum_jComboBox().getSelectedItem());


//            Grupo newGroup = new Grupo(groupNumber, 0, ppalView.getjTextField3().getText(), getSelectedBalasts(ppalView));
            Grupo newGroup = new Grupo(groupNumber-1, 0, ppalView.getjTextField3().getText());

            //Saves the balast remotelly

            GrupoDAOJmodbus gDao = new GrupoDAOJmodbus(ppalView.getDao());
            boolean resultado = gDao.saveElement(newGroup);

            String numeAumentado=String.valueOf(newGroup.getGroupNumber()+1);
            
            if (isUpdate) {
                //Update balast locally.
                ppalView.getGroups().remove(numeAumentado);
                ppalView.getGroups().put(numeAumentado, newGroup);

                if (resultado) {
                    ppalView.getStatusLabel().setText("Grupo actualizado.");
                } else {
                    ppalView.getStatusLabel().setText("Ocurrió un error guardando el grupo");
                }

                //Update name in the tree.
                TreePath path = ppalView.getArbol_jTree().getNextMatch(PropHandler.getProperty("group.menu.name"), 0, Position.Bias.Forward);
                MutableTreeNode balastNode = (MutableTreeNode) path.getLastPathComponent();
                Enumeration groups = balastNode.children();
                while (groups.hasMoreElements()) {
                    DefaultMutableTreeNode updatedGroup = (DefaultMutableTreeNode) groups.nextElement();
                    String[] b = updatedGroup.getUserObject().toString().split(" - ");
                    if (b[0].equals(String.valueOf(groupNumber))) {
                        updatedGroup.setUserObject(b[0] + " - " + newGroup.getName());
                    }
                }

            } else {
                if (new BalastosControl().validateBalastoForm()) {
                    ppalView.getGroups().put(numeAumentado, newGroup);

                    if (resultado) {
                        ppalView.getStatusLabel().setText("Grupo guardado");
                    } else {
                        ppalView.getStatusLabel().setText("Ocurrió un error guardando el grupo.");
                    }

                    //Show group in tree
                    DefaultTreeModel model = (DefaultTreeModel) ppalView.getArbol_jTree().getModel();
                    TreePath path = ppalView.getArbol_jTree().getNextMatch(PropHandler.getProperty("group.menu.name"), 0, Position.Bias.Forward);
                    MutableTreeNode balastNode = (MutableTreeNode) path.getLastPathComponent();
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(numeAumentado + " - " + newGroup.getName());
                    model.insertNodeInto(newNode, balastNode, balastNode.getChildCount());

                    //Remove balast from the list of available ones (jComboBox)
                    ppalView.getjLabel58().setText(String.valueOf(groupNumber));
                }
            }
        }
        refrescarVista(ppalView);
    }

   
    @Override
    public void deleteElement(PpalView ppalView) {
        boolean connectionStatus = true; //DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(connectionStatus, ppalView);
        if (connectionStatus) {
            if (ppalView.getSelectedGroupNumber() != null && !ppalView.getSelectedGroupNumber().equals("")) {
                DefaultMutableTreeNode nodeToDelete = (DefaultMutableTreeNode) ppalView.getArbol_jTree().getLastSelectedPathComponent();
                DefaultTreeModel treeModel = (DefaultTreeModel) ppalView.getArbol_jTree().getModel();
                GrupoDAOJmodbus gDao = new GrupoDAOJmodbus(ppalView.getDao());
                
                String selectedGroupNumber = ppalView.getSelectedGroupNumber();
                int numGrupoSeleccionado= Integer.parseInt(selectedGroupNumber);
                numGrupoSeleccionado=numGrupoSeleccionado-1;
                selectedGroupNumber=selectedGroupNumber.valueOf(numGrupoSeleccionado);
                 
                
                gDao.deleteElement(selectedGroupNumber);
                treeModel.removeNodeFromParent(nodeToDelete);
                ppalView.getGroups().remove(ppalView.getSelectedGroupNumber());
                new GrupoControl().cleanView(ppalView);
            }
        }
        refrescarVista(ppalView);
    }

    /**
     * Shows the selected group.
     */
    @Override
    public void showSelectedElement(String groupNumber, PpalView ppalView) {
        Grupo selectedGroup = ppalView.getGroups().get(groupNumber);
        HashMap<String, Balasto> balasts = ppalView.getBalasts();

        ppalView.getjTextField3().setText(selectedGroup.getName());
        ppalView.getjLabel58().setText(groupNumber);

        //       //<editor-fold defaultstate="collapsed" desc="Codigo removido por lo de config de balastos">
        //Afected balasts
        //        DefaultListModel groupBalastsL = new DefaultListModel();
        //        int[] selectedBalasts = selectedGroup.getBalastosAfectados();
        //        ArrayList sel = new ArrayList();
        //        for (int i = 0; i < selectedBalasts.length; i++) {
        //            if (selectedBalasts[i] == 1) {
        //                Balasto bal = balasts.get(String.valueOf(i));
        //                groupBalastsL.addElement(bal.getBalastNumber() + " - " + balasts.get(String.valueOf(i)).getName());
        //                sel.add(String.valueOf(i));
        //            }
        //        }
        //        ppalView.getjList3().setModel(groupBalastsL);
    
        //Available balasts
//        DefaultListModel modelo = new DefaultListModel();
//        ArrayList<String> addedBalasts = PropHandler.getAddedBalasts(ppalView.getDao());
//        for (String balastNumber : addedBalasts) {
//            if (!sel.contains(balastNumber)) {
//                Balasto balasto = balasts.get(balastNumber);
//                modelo.addElement(balasto.getBalastNumber() + " - " + balasto.getName());
//            }
//        }
//        ppalView.getjList2().setModel(modelo);
            //</editor-fold>
    }

   
    @Override
    public void readElements(PpalView ppalView) {
        if (ppalView.getGroups() == null || ppalView.getGroups().size() == 0) {
            GrupoDAOJmodbus gDao = new GrupoDAOJmodbus(ppalView.getDao());
            ppalView.setGroups(new HashMap<String, Grupo>());

            
            ArrayList<String> addedGroups = PropHandler.getAddedGroups(ppalView.getDao());

            //Balasts.
            for (String groupNumber : addedGroups) {
                
                int numero=(Integer.parseInt(groupNumber)+1);
                String numeroAumentado=Integer.toString(numero);
                
                ppalView.getGroups().put(numeroAumentado, gDao.readElement(Integer.parseInt(groupNumber)));
            }
        }
    }

    
    @Override
    public void cleanView(PpalView ppalView) {
        ppalView.getGruposNum_jComboBox().setSelectedIndex(0);
        ppalView.getjLabel58().setText("#");
        ppalView.getjTextField3().setText("");
        ppalView.setSelectedGroupNumber("");

        DefaultListModel model = new DefaultListModel();
        ppalView.getjList2().setModel(model);
        ppalView.getjList3().setModel(model);
    }

    /**
     * Filter the used groups (add existing groups to the menu).
     */
    @Override
    public void filterAddedElements(PpalView ppalView) {
        if (!ppalView.getGroupsStauts()) {
            ppalView.getStatusLabel().setText("Leyendo grupos de la tarjeta...");
            new GrupoControl().readElements(ppalView);
            int startRow = 0;
            String prefix = PropHandler.getProperty("group.menu.name");
            TreePath path = ppalView.getArbol_jTree().getNextMatch(prefix, startRow, Position.Bias.Forward);

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            ArrayList<String> addedGroups = PropHandler.getAddedGroups(ppalView.getDao());
            DefaultTreeModel model = (DefaultTreeModel) ppalView.getArbol_jTree().getModel();
            
            HashMap<String,Grupo> grupos=ppalView.getGroups();
            Set<String> grupoKeys =grupos.keySet();

            //Remove the used balast numbers from the list and add them to the menu.
//            for (String string : addedGroups) {
//                Grupo group = ppalView.getGroups().get(string);
//                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(string + " - " + group.getName());
//                model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
//            }

            for (String string : grupoKeys) {
                Grupo group = grupos.get(string);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(string + " - " + group.getName());
                model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
            }
            
            ppalView.setGroupsStauts(true);

            ppalView.getStatusLabel().setText("Grupos leidos.");
        }
    }

    /**
     * Show the available balasts.
     */
    public void showAvailableBalasts(PpalView ppalView) {
        new BalastosControl().readElements(ppalView);
        HashMap<String, Balasto> balasts = ppalView.getBalasts();
        ppalView.setGroupBalasts(balasts);
        DefaultListModel modelo = new DefaultListModel();

//        ArrayList<String> addedBalasts = PropHandler.getAddedBalasts(ppalView.getDao());
        Set<String> addedBalasts = balasts.keySet();
        for (String balastNumber : addedBalasts) {
            Balasto balasto = ppalView.getGroupBalasts().get(balastNumber);
            modelo.addElement(balasto.getBalastNumber() + " - " + balasto.getName());
        }
        ppalView.getjList2().setModel(modelo);
        ppalView.getjList2().setLayoutOrientation(JList.VERTICAL_WRAP);
    }

    /**
     * Get a string with the selected balasts.
     */
    public int[] getSelectedBalasts(PpalView ppalView) {
        String selected = new String();
        int[] selectedBalasts = new int[Integer.parseInt(PropHandler.getProperty("balast.max.number"))];

        for (int i = 0; i < ppalView.getjList3().getModel().getSize(); i++) {
            selected += ppalView.getjList3().getModel().getElementAt(i).toString().split(" - ")[0] + ",";
        }
        String[] selectedIdx = selected.split(",");
        for (int i = 0; i < selectedIdx.length; i++) {
            selectedBalasts[Integer.parseInt(selectedIdx[i])] = 1;
        }

        return selectedBalasts;
    }

    
    @Override
    public void refrescarVista(PpalView ppalView) {
        cleanView(ppalView);
        showAvailableBalasts(ppalView);
        filterAddedElements(ppalView);
        String[] elementosDisponibles = elementosDisponibles(ppalView);
        Validacion.actualizarCombo(ppalView.getGruposNum_jComboBox(), elementosDisponibles,Validacion.BALASTOS_DISPONIBLES);
        ppalView.getGruposNombreSmaiee_jTextField().requestFocusInWindow();

    }
    
    @Override
    public String[] elementosDisponibles(PpalView ppalView) {
        GrupoDAOJmodbus dao = new GrupoDAOJmodbus(ppalView.getDao());
        String[] ses;
        ses = dao.elementosSinGrabar();
      
        return ses;
    }

      
}
