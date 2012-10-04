package com.isolux.control;

import com.isolux.bo.*;
import com.isolux.dao.jmodbus.EscenaDAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import com.isolux.utils.Validation;
import com.isolux.view.PpalView;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class SceneControlJmodbus {
    
    
    /**
     * Saves a scene.
     */
    public void saveScene(PpalView ppalView) {
        new GeneralControl().updateConnectionStatus(true, ppalView);
        if (true) {

            ppalView.getStatusLabel().setText("Guardando escena");
            boolean isUpdate = !ppalView.getjLabel61().getText().equals("#");
            int sceneNumber = !ppalView.getjLabel61().getText().equals("#") ? Integer.parseInt(ppalView.getjLabel61().getText()) : Integer.parseInt((String) ppalView.getjComboBox6().getSelectedItem());

            int balastsMaxNumber = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
            int[] balastsLevels = new int[balastsMaxNumber];
            ListModel selectedBalasts = ppalView.getBalastosAfectados_jList().getModel();
            for (int i = 0; i < selectedBalasts.getSize(); i++) {
                String item = selectedBalasts.getElementAt(i).toString();
                String[] level = item.split(" - ");
              balastsLevels[Integer.parseInt(item.split(" - ")[0])] = Integer.parseInt(level[level.length-1]);
//                balastsLevels[Integer.parseInt(item.split(" - ")[0])] = Integer.parseInt(level[level.length - 1]);
                
            }

            Escena newScene = new Escena(sceneNumber, 1, ppalView.getNombreEscenaJTextField().getText(), balastsLevels, getSelectedSceneBalasts(ppalView));

            //Saves the balast remotelly
            EscenaDAOJmodbus dao = new EscenaDAOJmodbus(ppalView.getDao());
            boolean resultado = dao.saveScene(newScene);

            if (isUpdate) {
                //Update balast locally.
                ppalView.getScenes().remove(String.valueOf(newScene.getNumeroEscena()));
                ppalView.getScenes().put(String.valueOf(newScene.getNumeroEscena()), newScene);

                if (resultado) {
                    ppalView.getStatusLabel().setText("Escena actualizada.");
                } else {
                    ppalView.getStatusLabel().setText("Ocurrió un error guardando la escena");
                }

                //Update name in the tree.
                TreePath path = ppalView.getjTree1().getNextMatch(PropHandler.getProperty("scenes.menu.name"), 0, Position.Bias.Forward);
                MutableTreeNode balastNode = (MutableTreeNode) path.getLastPathComponent();
                Enumeration groups = balastNode.children();
                while (groups.hasMoreElements()) {
                    DefaultMutableTreeNode updatedScene = (DefaultMutableTreeNode) groups.nextElement();
                    String[] b = updatedScene.getUserObject().toString().split(" - ");
                    if (b[0].equals(String.valueOf(sceneNumber))) {
                        updatedScene.setUserObject(b[0] + " - " + newScene.getNombre());
                    }
                }

            } else {
                if (new BalastosControlJmodbus().validateBalastoForm()) {
                    ppalView.getScenes().put(String.valueOf(newScene.getNumeroEscena()), newScene);

                    if (resultado) {
                        ppalView.getStatusLabel().setText("Escena guardada");
                    } else {
                        ppalView.getStatusLabel().setText("Ocurrió un error guardando la escena.");
                    }

                    //Show balast in tree
                    DefaultTreeModel model = (DefaultTreeModel) ppalView.getjTree1().getModel();
                    TreePath path = ppalView.getjTree1().getNextMatch(PropHandler.getProperty("scenes.menu.name"), 0, Position.Bias.Forward);
                    MutableTreeNode balastNode = (MutableTreeNode) path.getLastPathComponent();
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(String.valueOf(newScene.getNumeroEscena()) + " - " + newScene.getNombre());
                    model.insertNodeInto(newNode, balastNode, balastNode.getChildCount());

                    //Remove balast from the list of available ones (jComboBox)
                    ppalView.getjLabel61().setText(String.valueOf(sceneNumber));
                }
            }
        }
    }

    /**
     * Deletes a scene.
     */
    public void deleteScene(PpalView ppalView) {
//        boolean connectionStatus = DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(true, ppalView);
        if (true) {
            EscenaDAOJmodbus dao = new EscenaDAOJmodbus(ppalView.getDao());
            if (ppalView.getSelectedSceneNumber() != null && !ppalView.getSelectedSceneNumber().equals("")) {
                DefaultMutableTreeNode nodeToDelete = (DefaultMutableTreeNode) ppalView.getjTree1().getLastSelectedPathComponent();
                DefaultTreeModel treeModel = (DefaultTreeModel) ppalView.getjTree1().getModel();
                dao.deleteScene(ppalView.getSelectedSceneNumber());
                treeModel.removeNodeFromParent(nodeToDelete);
                ppalView.getScenes().remove(ppalView.getSelectedSceneNumber());
                cleanSceneView(ppalView);
            }
        }
    }

    /**
     * Shows the selected group.
     */
    public void showSelectedScene(String sceneNumber, PpalView ppalView) {
        Escena selectedScene = ppalView.getScenes().get(sceneNumber);
        HashMap<String, Balasto> balasts = ppalView.getBalasts();

        ppalView.getNombreEscenaJTextField().setText(selectedScene.getNombre());
//        ppalView.getjTextField24().setText(String.valueOf(selectedScene.getActivacion()));
        ppalView.getjLabel61().setText(sceneNumber);

        //Afected balasts
        DefaultListModel sceneBalastsL = new DefaultListModel();
        int[] selectedBalasts = selectedScene.getBalastosAfectados();
        int[] selectedBalastsLevels = selectedScene.getNivelBalasto();
        ArrayList sel = new ArrayList();
        for (int i = 0; i < selectedBalasts.length; i++) {
            if (selectedBalasts[i] == 1) {
                Balasto sce = balasts.get(String.valueOf(i));
                sceneBalastsL.addElement(sce.getBalastNumber() + " - " + sce.getName() + ": " + selectedBalastsLevels[i]);
                sel.add(String.valueOf(i));
            }
        }
        ppalView.getBalastosAfectados_jList().setModel(sceneBalastsL);

        //Available balasts
        DefaultListModel modelo = new DefaultListModel();
        ArrayList<String> addedBalasts = PropHandler.getAddedBalasts(ppalView.getDao());
        for (String balastNumber : addedBalasts) {
            if (!sel.contains(balastNumber)) {
                Balasto balasto = balasts.get(balastNumber);
                modelo.addElement(balasto.getBalastNumber() + " - " + balasto.getName());
            }
        }
        ppalView.getjList4().setModel(modelo);
    }

    /**
     * Gets the inserted scenes.
     */
    public void readScenes(PpalView ppalView) {
        if (ppalView.getScenes() == null || ppalView.getScenes().size() == 0) {
            EscenaDAOJmodbus dao = new EscenaDAOJmodbus(ppalView.getDao());
            ppalView.setScenes(new HashMap<String, Escena>());

            //Added balasts numbers.
            ArrayList<String> addedScenes = PropHandler.getAddedScenes(ppalView.getDao());

            //Scene.
            for (String sceneNumber : addedScenes) {
                ppalView.getScenes().put(sceneNumber, dao.readScene(Integer.parseInt(sceneNumber)));
            }
        }
    }

    /**
     * Clean values fror group form.
     */
    public void cleanSceneView(PpalView ppalView) {
        ppalView.getjComboBox6().setSelectedIndex(0);
        ppalView.getjLabel61().setText("#");
        ppalView.getjLabel19().setText("#");
        ppalView.getNombreEscenaJTextField().setText("");
        ppalView.getjTextField23().setText("0");
        ppalView.setSelectedSceneNumber("");

        DefaultListModel model = new DefaultListModel();
        ppalView.getjList4().setModel(model);
        ppalView.getBalastosAfectados_jList().setModel(model);
    }

    /**
     * Filter the used groups (add existing groups to the menu).
     */
    public void filterAddedScenes(PpalView ppalView) {
        if (!ppalView.getSceneStauts()) {
            ppalView.getStatusLabel().setText("Leyendo escenas de la tarjeta...");
            readScenes(ppalView);
            int startRow = 0;
            String prefix = PropHandler.getProperty("scenes.menu.name");
            TreePath path = ppalView.getjTree1().getNextMatch(prefix, startRow, Position.Bias.Forward);

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            ArrayList<String> addedScenes = PropHandler.getAddedScenes(ppalView.getDao());
            DefaultTreeModel model = (DefaultTreeModel) ppalView.getjTree1().getModel();

            //Remove the used balast numbers from the list and add them to the menu.
            for (String string : addedScenes) {
                Escena scene = ppalView.getScenes().get(string);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(string + " - " + scene.getNombre());
                model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
            }
            ppalView.setSceneStauts(true);
            ppalView.getStatusLabel().setText("Escenas leidas.");
        }
    }

    /**
     * Show the available balasts.
     */
    public void showAvailableSceneBalasts(PpalView ppalView) {
        new BalastosControlJmodbus().readBalastos(ppalView);
        ppalView.setSceneBalasts(ppalView.getBalasts());
        DefaultListModel modelo = new DefaultListModel();

        ArrayList<String> addedBalasts = PropHandler.getAddedBalasts(ppalView.getDao());
        for (String balastNumber : addedBalasts) {
            Balasto balasto = ppalView.getSceneBalasts().get(balastNumber);
            modelo.addElement(balasto.getBalastNumber() + " - " + balasto.getName());
        }
        ppalView.getjList4().setModel(modelo);
    }

    /**
     * Get a string with the selected balasts.
     */
    private int[] getSelectedSceneBalasts(PpalView ppalView) {
        String selected = new String();
        int[] selectedBalasts = new int[Integer.parseInt(PropHandler.getProperty("balast.max.number"))];

        for (int i = 0; i < ppalView.getBalastosAfectados_jList().getModel().getSize(); i++) {
            selected += ppalView.getBalastosAfectados_jList().getModel().getElementAt(i).toString().split(" - ")[0] + ",";
        }
        String[] selectedIdx = selected.split(",");
        for (int i = 0; i < selectedIdx.length; i++) {
            selectedBalasts[Integer.parseInt(selectedIdx[i])] = 1;
        }

        return selectedBalasts;
    }

    /**
     * Select a scene balast.
     */
    public void selectEsceneBalast(PpalView ppalView) {
        String[] balasto = ppalView.getBalastosAfectados_jList().getSelectedValue().toString().split(": ");
        String balastNumber = ppalView.getBalastosAfectados_jList().getSelectedValue().toString().split(" - ")[0];
        if (balasto.length > 1) {
            ppalView.getjLabel19().setText(balastNumber);
            ppalView.getjTextField23().setText(balasto[balasto.length - 1]);
        } else {
            ppalView.getjLabel19().setText(balastNumber);
            ppalView.getjTextField23().setText("0");
        }
    }

    /**
     * Update scene balast value.
     */
    public void updateEsceneBalastLevel(PpalView ppalView) {
        String level = ppalView.getjTextField23().getText();
        String[] selectedBalast = ppalView.getBalastosAfectados_jList().getSelectedValue().toString().split(": ");
        String selectedBalastIdx = ppalView.getjLabel19().getText();
        DefaultListModel model = new DefaultListModel();

        String newValue = selectedBalast[0] + ": " + level;

        //Insert the modified balast value in the list.
        if (!selectedBalastIdx.equals("#")) {
            ListModel selected = ppalView.getBalastosAfectados_jList().getModel();
            for (int i = 0; i < selected.getSize(); i++) {

                if (selected.getElementAt(i).toString().split(" - ")[0].equals(selectedBalastIdx)) {
                    model.addElement(newValue);
                } else {
                    model.addElement(selected.getElementAt(i).toString());
                }
            }
            ppalView.getBalastosAfectados_jList().setModel(model);
        } else {
            Validation.showAlertMessage("Seleccione un balasto");
        }

    }
}
