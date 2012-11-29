/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.bo.*;
import com.isolux.dao.jmodbus.EntradaDAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import com.isolux.utils.Validacion;
import com.isolux.view.PpalView;
import com.isolux.view.ViewUtils;
import java.util.ArrayList;
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
public class EntradaControl implements ElementoControl_Interface {

    /**
     * Select the in views.
     */
//    public void inSelection(PpalView ppalView) {
//        CardLayout cl = (CardLayout) (ppalView.getPanelConfEntradas().getLayout());
//
//        String opc = (String) ppalView.getjComboBox1().getSelectedItem();
//        opc = opc.toLowerCase();
//        switch (opc.charAt(0)) {
//            case 'b':
//                cl.show(ppalView.getPanelConfEntradas(), "card2"); //Escenas
//                break;
//
//            case 'f':
//                cl.show(ppalView.getPanelConfEntradas(), "card3"); //Entradas
//                break;
//
//            case 's':
//                cl.show(ppalView.getPanelConfEntradas(), "card4"); //Eventos
//                break;
//        }
//    }
    /**
     * Saves an in.
     */
    @Override
    public void saveElement(PpalView ppalView) {
        boolean connectionStatus = true; //DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(connectionStatus, ppalView);
        if (connectionStatus) {

            ppalView.getStatusLabel().setText("Guardando entrada");
            //in number
            boolean isUpdate = !ppalView.getjLabel63().getText().equals("#");

            int inNumber = ppalView.getjLabel63().getText().equals("#") ? Integer.parseInt((String) ppalView.getEntradaNumero_jComboBox().getSelectedItem()) : Integer.parseInt(ppalView.getjLabel63().getText());
//        int balastNumber = !jLabel63.getText().equals("#") ? Integer.parseInt(jLabel63.getText()) : Integer.parseInt((String) jComboBox4.getSelectedItem());
//        int balastNumber = !jLabel63.getText().equals("#") ? Integer.parseInt(jLabel63.getText()) : Integer.parseInt((String) jComboBox4.getSelectedItem());

            int activacion = 1;
            int tipo = ppalView.getInType();
            int comp1 = ppalView.getjRadioButton1().isSelected() ? 1 : ppalView.getjRadioButton2().isSelected() ? 2 : 0; //Mantenido = 1 o momentaneo = 2
            int comp2 = ppalView.getjRadioButton3().isSelected() ? 1 : ppalView.getjRadioButton4().isSelected() ? 2 : 0; //NA = 1, o NC = 2
            int nivelON = ppalView.getjTextField5().getText().equals("0") ? Integer.parseInt(ppalView.getjTextField34().getText()) : Integer.parseInt(ppalView.getjTextField5().getText());
            int NivelOFF = ppalView.getjTextField6().getText().equals("0") ? Integer.parseInt(ppalView.getjTextField35().getText()) : Integer.parseInt(ppalView.getjTextField6().getText());
            int tiempoRetardo = ppalView.getjTextField7().getText().equals("0") ? Integer.parseInt(ppalView.getjTextField36().getText()) : Integer.parseInt(ppalView.getjTextField7().getText());
            int ganancia = Integer.parseInt(ppalView.getjTextField10().getText());

            int nivIlumXvoltio = Integer.parseInt(ppalView.getEntradaFotoceldaNivelIlum_jTextField().getText());
            int nivelDeseado = Integer.parseInt(ppalView.getEntradaFotoceldaNivelDeseado_jTextField().getText());

            int tipoSalida = ppalView.getInOutType();
            int[] balastosAfectados = ppalView.getInOutType() == ViewUtils.getIntProperty("in.out.type.balast") ? getSelectedInItems(ppalView)
                    : new int[Integer.parseInt(PropHandler.getProperty("balast.max.number"))];
            int[] gruposAfectados = ppalView.getInOutType() == ViewUtils.getIntProperty("in.out.type.group") ? getSelectedInItems(ppalView)
                    : new int[Integer.parseInt(PropHandler.getProperty("group.max.number"))];
            int[] escenasAfecadas = ppalView.getInOutType() == ViewUtils.getIntProperty("in.out.type.scene") ? getSelectedInItems(ppalView)
                    : new int[Integer.parseInt(PropHandler.getProperty("scene.max.number"))];
            int valorAdc = 0;

            Entrada newIn = new Entrada(
                    inNumber,
                    activacion,
                    tipo,
                    comp1,
                    comp2,
                    nivelON,
                    NivelOFF,
                    tiempoRetardo,
                    ganancia,
                    nivIlumXvoltio,
                    nivelDeseado,
                    tipoSalida,
                    balastosAfectados,
                    gruposAfectados,
                    escenasAfecadas,
                    valorAdc);

            //Saves the balast remotelly
            boolean resultado = false;

            EntradaDAOJmodbus jDao = new EntradaDAOJmodbus(ppalView.getDao());
            resultado = jDao.saveElement(newIn);
            if (isUpdate) {
                //Update balast locally.
                ppalView.getIns().remove(String.valueOf(newIn.getNumeroEntrada()));
                ppalView.getIns().put(String.valueOf(newIn.getNumeroEntrada()), newIn);

                if (resultado) {
                    ppalView.getStatusLabel().setText("Entrada actualizada.");
                } else {
                    ppalView.getStatusLabel().setText("Ocurrió un error guardando la entrada");
                }

//            //Update name in the tree.
//            String btnText = PropHandler.getProperty("scenes.menu.name");
//            String ftcldText = PropHandler.getProperty("scenes.menu.name");
//            String sensorText = PropHandler.getProperty("scenes.menu.name");
//
//            String parentNodeText = inType == ViewUtils.getIntProperty("in.type.btns") ? btnText
//                    : inType == ViewUtils.getIntProperty("in.type.ftcld") ? ftcldText
//                    : inType == ViewUtils.getIntProperty("in.type.sensor") ? sensorText : "";
//
//            TreePath path = jTree1.getNextMatch(parentNodeText, 0, Position.Bias.Forward);
//            MutableTreeNode balastNode = (MutableTreeNode) path.getLastPathComponent();
//            Enumeration groups = balastNode.children();
//
//            while (groups.hasMoreElements()) {
//                DefaultMutableTreeNode updatedScene = (DefaultMutableTreeNode) groups.nextElement();
//                String[] b = updatedScene.getUserObject().toString().split(" - ");
//                if (b[0].equals(String.valueOf(inNumber))) {
//                    updatedScene.setUserObject(b[0] + " - " + newIn.get);
//                }
//            }

            } else {
                if (new BalastosControl().validateBalastoForm()) {
                    ppalView.getIns().put(String.valueOf(newIn.getNumeroEntrada()), newIn);

                    if (resultado) {
                        ppalView.getStatusLabel().setText("Entrada guardada");
                    } else {
                        ppalView.getStatusLabel().setText("Ocurrió un error guardando la entrada.");
                    }

                    //Update name in the tree.
                    String btnText = PropHandler.getProperty("btns.menu.name");
                    String ftcldText = PropHandler.getProperty("ftcl.menu.name");
                    String sensorText = PropHandler.getProperty("sensors.menu.name");

                    String parentNodeText = ppalView.getInType() == ViewUtils.getIntProperty("in.type.btns") ? btnText
                            : ppalView.getInType() == ViewUtils.getIntProperty("in.type.ftcld") ? ftcldText
                            : ppalView.getInType() == ViewUtils.getIntProperty("in.type.sensor") ? sensorText : "";

                    //Show balast in tree
                    DefaultTreeModel model = (DefaultTreeModel) ppalView.getArbol_jTree().getModel();
                    TreePath path = ppalView.getArbol_jTree().getNextMatch(parentNodeText, 0, Position.Bias.Forward);
                    MutableTreeNode balastNode = (MutableTreeNode) path.getLastPathComponent();
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(String.valueOf(newIn.getNumeroEntrada()));
                    model.insertNodeInto(newNode, balastNode, balastNode.getChildCount());

                    ppalView.getjLabel63().setText(String.valueOf(inNumber));
//                            ppalView.getjComboBox4().removeItem(inNumber);
                }
            }

//            if (nivIlumXvoltio > 0) {
//                if (nivelDeseado <= (nivIlumXvoltio * 10)) {
//                } else {
//                    Validation.showAlertMessage("El nivel deseado debe ser menor a 10 veces el nivel de iluminacion por voltio.");
//                }
//            } else {
//                Validation.showAlertMessage("El nivel de iluminacion por voltio no puede ser cero.");
//            }
        }
        refrescarVista(ppalView);
    }

    /**
     * Deletes a scene.
     */
    @Override
    public void deleteElement(PpalView ppalView) {
        boolean connectionStatus = true; //DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(connectionStatus, ppalView);
        if (connectionStatus) {
            if (ppalView.getSelectedInNumber() != null && !ppalView.getSelectedInNumber().equals("")) {
                DefaultMutableTreeNode nodeToDelete = (DefaultMutableTreeNode) ppalView.getArbol_jTree().getLastSelectedPathComponent();
                DefaultTreeModel treeModel = (DefaultTreeModel) ppalView.getArbol_jTree().getModel();
                EntradaDAOJmodbus jDao = new EntradaDAOJmodbus(ppalView.getDao());
                jDao.deleteElement(ppalView.getSelectedInNumber());
                treeModel.removeNodeFromParent(nodeToDelete);
//                ppalView.getjComboBox4().addItem(ppalView.getSelectedInNumber());
                ppalView.getIns().remove(ppalView.getSelectedInNumber());
                cleanView(ppalView);
            } else {
                Validacion.showAlertMessage("Debe seleccionar una entrada primero!");
            }
        }
        refrescarVista(ppalView);
    }

    @Override
    public void showSelectedElement(String inNumber, PpalView ppalView) {
        Entrada selectedIn = ppalView.getIns().get(inNumber);
        ppalView.setInOutType(selectedIn.getTipoSalida());
        ppalView.setInType(selectedIn.getTipo());
        int prefixBtn = Integer.parseInt(PropHandler.getProperty("in.type.btns"));
        int prefixFtcld = Integer.parseInt(PropHandler.getProperty("in.type.ftcld"));
        int prefixSensor = Integer.parseInt(PropHandler.getProperty("in.type.sensor"));

        ppalView.getjLabel63().setText(inNumber);
        if (ppalView.getInType() == prefixBtn) {  //Botonera
            ppalView.getjRadioButton1().setSelected(selectedIn.getComportamiento1() == 1 ? true : false);
            ppalView.getjRadioButton2().setSelected(selectedIn.getComportamiento1() == 2 ? true : false);
            ppalView.getjRadioButton3().setSelected(selectedIn.getComportamiento2() == 1 ? true : false);
            ppalView.getjRadioButton4().setSelected(selectedIn.getComportamiento2() == 2 ? true : false);
            ppalView.getjTextField5().setText(String.valueOf(selectedIn.getNivelON()));
            ppalView.getjTextField6().setText(String.valueOf(selectedIn.getNivelOFF()));
            ViewUtils.setInOutType(ppalView.getjCheckBox16(), ppalView.getjCheckBox17(), ppalView.getjCheckBox18(), selectedIn.getTipoSalida());
            showInItems(ppalView.getjList14(), ppalView.getjList15(), selectedIn, ppalView);
        } else if (ppalView.getInType() == prefixFtcld) { //Fotocelda
            ppalView.getjTextField7().setText(String.valueOf(selectedIn.getTiempoRetardo()));
            ppalView.getjTextField10().setText(String.valueOf(selectedIn.getGanancia()));
            ppalView.getEntradaFotoceldaNivelIlum_jTextField().setText(String.valueOf((int) selectedIn.getNivIlumxvoltio()));

//            float nivelDeseado = ((((int) selectedIn.getNivIlumxvoltio()) * 10) * (int) selectedIn.getNivelDeseado()) / 100;
            float nivelDeseado=selectedIn.getNivelDeseado();
            ppalView.getEntradaFotoceldaNivelDeseado_jTextField().setText(String.valueOf((int) nivelDeseado));
            ViewUtils.setInOutType(ppalView.getjCheckBox10(), ppalView.getjCheckBox11(), ppalView.getFotoceldasEntradas_jCheckBox(), selectedIn.getTipoSalida());
            showInItems(ppalView.getjList8(), ppalView.getjList9(), selectedIn, ppalView);
        } else if (ppalView.getInType() == prefixSensor) {    //Sensor

            ppalView.getjTextField34().setText(String.valueOf(selectedIn.getNivelON()));
            ppalView.getjTextField35().setText(String.valueOf(selectedIn.getNivelOFF()));
            ppalView.getjTextField36().setText(String.valueOf(selectedIn.getTiempoRetardo()));

            ViewUtils.setInOutTypeSensors(ppalView.getjCheckBox19(), ppalView.getjCheckBox20(), selectedIn.getTipoSalida());
            showInItems(ppalView.getjList16(), ppalView.getjList17(), selectedIn, ppalView);
        }



    }

    /**
     * Gets the inserted in.
     */
    @Override
    public void readElements(PpalView ppalView) {
            EntradaDAOJmodbus jDao = new EntradaDAOJmodbus(ppalView.getDao());
        ppalView.setIns(new HashMap<String, Entrada>());

        //Added in numbers.
        ArrayList<String> addedIns = PropHandler.getAddedIns(ppalView.getDao());

        //In.
        for (String inNumber : addedIns) {
            ppalView.getIns().put(inNumber, jDao.readElement(Integer.parseInt(inNumber)));
        }
    }

    /**
     * Clean values fror group form.
     */
    @Override
    public void cleanView(PpalView ppalView) {
        DefaultListModel model = new DefaultListModel();
        ppalView.getjLabel63().setText("#");
        ppalView.setSelectedInNumber("");
        ppalView.getEntradaNumero_jComboBox().setSelectedIndex(0);

        //Btnr
        ppalView.getjRadioButton1().setSelected(true);
        ppalView.getjRadioButton2().setSelected(false);
        ppalView.getjRadioButton3().setSelected(true);
        ppalView.getjRadioButton4().setSelected(false);
        ppalView.getjTextField5().setText("0");
        ppalView.getjTextField6().setText("0");
        ppalView.getjList14().setModel(model);
        ppalView.getjList15().setModel(model);
        ppalView.getjCheckBox16().setSelected(false);
        ppalView.getjCheckBox17().setSelected(false);
        ppalView.getjCheckBox18().setSelected(false);
        ppalView.getjCheckBox16().setEnabled(true);
        ppalView.getjCheckBox17().setEnabled(true);
        ppalView.getjCheckBox18().setEnabled(true);

        //Ftcld
        ppalView.getjTextField7().setText("0");
        ppalView.getEntradaFotoceldaNivelIlum_jTextField().setText("0");
        ppalView.getEntradaFotoceldaNivelDeseado_jTextField().setText("0");
        ppalView.getjTextField10().setText("0");
        ppalView.getjList8().setModel(model);
        ppalView.getjList9().setModel(model);
        ppalView.getjCheckBox10().setSelected(false);
        ppalView.getjCheckBox11().setSelected(false);
        ppalView.getFotoceldasEntradas_jCheckBox().setSelected(false);
        ppalView.getjCheckBox10().setEnabled(true);
        ppalView.getjCheckBox11().setEnabled(true);
        ppalView.getFotoceldasEntradas_jCheckBox().setEnabled(true);

        //Sensor
        ppalView.getjTextField34().setText("0");
        ppalView.getjTextField35().setText("0");
        ppalView.getjTextField36().setText("0");
        ppalView.getjCheckBox19().setSelected(false);
        ppalView.getjCheckBox20().setSelected(false);
        ppalView.getjList16().setModel(model);
        ppalView.getjList17().setModel(model);
        ppalView.getjCheckBox19().setEnabled(true);
        ppalView.getjCheckBox20().setEnabled(true);
    }

    @Override
    public void filterAddedElements(PpalView ppalView) {
        try {
            if (!ppalView.getInStauts()) {
                ppalView.getStatusLabel().setText("Leyendo entradas de la tarjeta...");
                readElements(ppalView);
                int startRow = 0;
                String prefixBtn = PropHandler.getProperty("btns.menu.name");
                String prefixFtcld = PropHandler.getProperty("ftcl.menu.name");
                String prefixSensor = PropHandler.getProperty("sensors.menu.name");


                TreePath pathBtns = ppalView.getArbol_jTree().getNextMatch(prefixBtn, startRow, Position.Bias.Forward);
                TreePath pathFtcld = ppalView.getArbol_jTree().getNextMatch(prefixFtcld, startRow, Position.Bias.Forward);
                TreePath pathSensor = ppalView.getArbol_jTree().getNextMatch(prefixSensor, startRow, Position.Bias.Forward);

                DefaultMutableTreeNode btnsNode = null;
                if (pathBtns != null) {
                    btnsNode = (DefaultMutableTreeNode) pathBtns.getLastPathComponent();
                }
                DefaultMutableTreeNode ftcldNode = null;
                if (pathFtcld != null) {
                    ftcldNode = (DefaultMutableTreeNode) pathFtcld.getLastPathComponent();
                }
                DefaultMutableTreeNode sensorNode = null;
                if (pathSensor != null) {
                    sensorNode = (DefaultMutableTreeNode) pathSensor.getLastPathComponent();
                }

                ArrayList<String> addedIns = PropHandler.getAddedIns(ppalView.getDao());
                DefaultTreeModel model = (DefaultTreeModel) ppalView.getArbol_jTree().getModel();

                //Remove the used balast numbers from the list and add them to the menu.
                for (String string : addedIns) {
                    Entrada in = ppalView.getIns().get(string);
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(string);
//                ppalView.getjComboBox4().removeItem(string);
                    if (in.getTipo() == ViewUtils.getIntProperty("in.type.btns")) { //Botonera
                        model.insertNodeInto(newNode, btnsNode, btnsNode.getChildCount());
                    }
                    if (in.getTipo() == ViewUtils.getIntProperty("in.type.ftcld")) { //Fotocelda
                        model.insertNodeInto(newNode, ftcldNode, ftcldNode.getChildCount());
                    }
                    if (in.getTipo() == ViewUtils.getIntProperty("in.type.sensor")) { //Sensor
                        model.insertNodeInto(newNode, sensorNode, sensorNode.getChildCount());
                    }
                }
                ppalView.setInStauts(true);
                ppalView.getStatusLabel().setText("Entradas leidas.");
            }
        } catch (Exception e) {
            ppalView.setInStauts(false);
            ppalView.getStatusLabel().setText("Entradas no leidas.");
        }
    }

    /**
     * Sets the in type.
     *
     * @param node
     */
    public void inSetType(String node, PpalView ppalView) {
        String btns = PropHandler.getProperty("btns.menu.name");
        String ftcld = PropHandler.getProperty("ftcl.menu.name");
        String sensor = PropHandler.getProperty("sensors.menu.name");

        if (node.equals(btns)) {
            ppalView.setInType(Integer.parseInt(PropHandler.getProperty("in.type.btns")));
        }
        if (node.equals(ftcld)) {
            ppalView.setInType(Integer.parseInt(PropHandler.getProperty("in.type.ftcld")));
        }
        if (node.equals(sensor)) {
            ppalView.setInType(Integer.parseInt(PropHandler.getProperty("in.type.sensor")));
        }
    }

    /**
     * Get a string with the selected items.
     */
    public int[] getSelectedInItems(PpalView ppalView) {
        String selected = new String();
        int arrayLength = 0;
        JList selectedList = getInSelectionList(ppalView);

        //Gets the array length
        if (ppalView.getInOutType() == ViewUtils.getIntProperty("in.out.type.balast")) { //balasts
            arrayLength = ViewUtils.getIntProperty("balast.max.number");
        } else if (ppalView.getInOutType() == ViewUtils.getIntProperty("in.out.type.group")) { //groups
            arrayLength = ViewUtils.getIntProperty("group.max.number");
        } else if (ppalView.getInOutType() == ViewUtils.getIntProperty("in.out.type.scene")) { //scenes
            arrayLength = ViewUtils.getIntProperty("scene.max.number");
        }

        //Array with selected items.
        int[] selectedItems = new int[arrayLength];

        //fill selected items array
        for (int i = 0; i < selectedList.getModel().getSize(); i++) {
            selected += selectedList.getModel().getElementAt(i).toString().split(" - ")[0] + ",";
        }
        String[] selectedIdx = selected.split(",");
        for (int i = 0; i < selectedIdx.length; i++) {
            selectedItems[Integer.parseInt(selectedIdx[i])] = 1;
        }

        return selectedItems;
    }

    /**
     * Gets the selection JList
     *
     * @return
     */
    private JList getInSelectionList(PpalView ppalView) {
        JList selectedList = new JList();
        if (ppalView.getInType() == ViewUtils.getIntProperty("in.type.btns")) { //Botonera
            selectedList = ppalView.getjList15();
        }
        if (ppalView.getInType() == ViewUtils.getIntProperty("in.type.ftcld")) { //Fotocelda
            selectedList = ppalView.getjList9();
        }
        if (ppalView.getInType() == ViewUtils.getIntProperty("in.type.sensor")) { //Sensor
            selectedList = ppalView.getjList17();
        }

        return selectedList;
    }

    /**
     * Show the in items.
     */
    public void showInItems(JList available, JList affected, Entrada selectedIn, PpalView ppalView) {
        int prefixBalast = Integer.parseInt(PropHandler.getProperty("in.out.type.balast"));
        int prefixGroup = Integer.parseInt(PropHandler.getProperty("in.out.type.group"));
        int prefixScene = Integer.parseInt(PropHandler.getProperty("in.out.type.scene"));
        HashMap<String, Balasto> balasts = ppalView.getBalasts();

        if (ppalView.getInOutType() == prefixBalast) {  //Balastos
            //Afected balasts
            new BalastosControl().readElements(ppalView);
            DefaultListModel inAffecBalasts = new DefaultListModel();
            int[] selectedBalasts = selectedIn.getBalastos();
            ArrayList sel = new ArrayList();
            for (int i = 0; i < selectedBalasts.length; i++) {
                if (selectedBalasts[i] == 1) {
                    Balasto sce = balasts.get(String.valueOf(i));
                    if (sce != null) {
                        inAffecBalasts.addElement(sce.getBalastNumber() + " - " + sce.getName());
                        sel.add(String.valueOf(i));
                    }
                }
            }
            affected.setModel(inAffecBalasts);

            //Available balasts
            DefaultListModel modelo = new DefaultListModel();
//            ArrayList<String> addedBalasts = PropHandler.getAddedBalasts(ppalView.getDao());


            Set<String> addedBalasts = balasts.keySet();
            for (String balastNumber : addedBalasts) {
                if (!sel.contains(balastNumber)) {
                    Balasto balasto = balasts.get(balastNumber);
                    modelo.addElement(balasto.getBalastNumber() + " - " + balasto.getName());
                }
            }
            available.setModel(modelo);
        } else if (ppalView.getInOutType() == prefixGroup) { //Grupos
            //Afected balasts
            new GrupoControl().readElements(ppalView);
            DefaultListModel groupBalasts = new DefaultListModel();
            int[] selectedGroups = selectedIn.getGrupos();
            ArrayList sel = new ArrayList();
            for (int i = 0; i < selectedGroups.length; i++) {
                if (selectedGroups[i] == 1) {
                    Grupo sce = ppalView.getGroups().get(String.valueOf(i));
                    if (sce != null) {
                        groupBalasts.addElement(sce.getGroupNumber() + " - " + sce.getName());
                        sel.add(String.valueOf(i));
                    }
                }
            }
            affected.setModel(groupBalasts);

            //Available balasts
            DefaultListModel modelo = new DefaultListModel();
            ArrayList<String> addedGroups = PropHandler.getAddedGroups(ppalView.getDao());
            for (String groupNumber : addedGroups) {
                if (sel.size() > 0 && !sel.contains(groupNumber)) {
                    Grupo grupo = ppalView.getGroups().get(groupNumber);
                    modelo.addElement(grupo.getGroupNumber() + " - " + grupo.getName());
                }
            }
            available.setModel(modelo);
        } else if (ppalView.getInOutType() == prefixScene) {    //Escenas
            //Afected balasts
            new EscenaControl().readElements(ppalView);
            DefaultListModel sceneBalastsL = new DefaultListModel();
            int[] selectedBalasts = selectedIn.getEscenas();
            ArrayList sel = new ArrayList();
            for (int i = 0; i < selectedBalasts.length; i++) {
                if (selectedBalasts[i] == 1) {
                    Escena sce = ppalView.getScenes().get(String.valueOf(i));
                    if (sce != null) {
                        sceneBalastsL.addElement(sce.getNumeroEscena() + " - " + sce.getNombre());
                        sel.add(String.valueOf(i));
                    }
                }
            }
            affected.setModel(sceneBalastsL);

            //Available balasts
            DefaultListModel modelo = new DefaultListModel();
            ArrayList<String> addedScenes = PropHandler.getAddedScenes(ppalView.getDao());
            for (String sceneNumber : addedScenes) {
                if (!sel.contains(sceneNumber)) {
                    Escena escena = ppalView.getScenes().get(sceneNumber);
                    modelo.addElement(escena.getNumeroEscena() + " - " + escena.getNombre());
                }
            }
            available.setModel(modelo);
        }
    }

//    /**
//     * Método que refresca la vista de las entradas en las operaciones de
//     * escritura y borrado.
//     *
//     * @param ppalView
//     */
//    public void refrescarVistaEntradas(PpalView ppalView) {
//        cleanInView(ppalView);
//        
////        filterAddedIn(ppalView);
//        
//    }
    @Override
    public void refrescarVista(PpalView ppalView) {
        cleanView(ppalView);
//        show
        filterAddedElements(ppalView);

        String[] elementosDisponibles = elementosDisponibles(ppalView);
        Validacion.actualizarCombo(ppalView.getEntradaNumero_jComboBox(), elementosDisponibles, Validacion.BALASTOS_DISPONIBLES);
    }

    @Override
    public String[] elementosDisponibles(PpalView ppalView) {
        EntradaDAOJmodbus dao = new EntradaDAOJmodbus(ppalView.getDao());
        String[] ses;
        ses = dao.elementosSinGrabar();

        return ses;
    }
}
