/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import com.isolux.bo.*;
import com.isolux.dao.jmodbus.ConfiguracionDAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import com.isolux.utils.Validation;
import com.isolux.view.PpalView;
import com.isolux.view.threads.RefreshRTC;
import com.isolux.view.threads.ThreadManager;
import com.toedter.calendar.IDateEditor;
import java.awt.CardLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import net.wimpi.modbus.net.TCPMasterConnection;
import sun.misc.GC;

/**
 *
 * @author EAFIT
 */
public class GeneralControl {

    private RefreshRTC rtc;

    public GeneralControl() {
        this.rtc = new RefreshRTC();
       
    }

    /**
     * Show the available balasts.
     */
    public void showAvailableBalasts(JList show, JList remove, PpalView ppalView) {
        new BalastosControlJmodbus().readBalastos(ppalView);
        DefaultListModel modelo = new DefaultListModel();
        DefaultListModel cleanModelo = new DefaultListModel();
        show.setModel(modelo);
        remove.setModel(cleanModelo);

//        ArrayList<String> addedBalasts = PropHandler.getAddedBalasts(ppalView.getDao());
        HashMap<String, Balasto> balasts = ppalView.getBalasts();
        Set<String> addedBalasts = balasts.keySet();
        for (String balastNumber : addedBalasts) {
            Balasto balasto = balasts.get(balastNumber);
            modelo.addElement(balasto.getBalastNumber() + " - " + balasto.getName());
        }
        show.setModel(modelo);
        remove.removeAll();
    }

    /**
     * Show the available balasts.
     */
    public void showAvailableGroups(JList show, JList remove, PpalView ppalView) {
        new GroupsControl().readGroups(ppalView);
        DefaultListModel modelo = new DefaultListModel();
        DefaultListModel cleanModelo = new DefaultListModel();
        show.setModel(modelo);
        remove.setModel(cleanModelo);

        ArrayList<String> addedGroups = PropHandler.getAddedGroups(ppalView.getDao());
        for (String groupNumber : addedGroups) {
            Grupo group = ppalView.getGroups().get(groupNumber);
            modelo.addElement(group.getGroupNumber() + " - " + group.getName());
        }
        show.setModel(modelo);
        remove.removeAll();
    }

    /**
     * Show the available balasts.
     */
    public void showAvailableScenes(JList show, JList remove, PpalView ppalView) {
        new SceneControlJmodbus().readScenes(ppalView);
        DefaultListModel modelo = new DefaultListModel();
        DefaultListModel cleanModelo = new DefaultListModel();
        show.setModel(modelo);
        remove.setModel(cleanModelo);

        ArrayList<String> addedScenes = PropHandler.getAddedScenes(ppalView.getDao());
        for (String sceneNumber : addedScenes) {
            Escena scene = ppalView.getScenes().get(sceneNumber);
            modelo.addElement(scene.getNumeroEscena() + " - " + scene.getNombre());
        }
        show.setModel(modelo);
        remove.removeAll();
    }

    /**
     * Tree selection. Controla la seleccion del elemento en el arbol de
     * jerarquia
     */
    public void treeSelection(PpalView ppalView, RealTimeControl realCtrl) {
        //Stops current threads.
//        ppalView.getThreadManager().stopAllCurrentThreads();// aqui se puede estar presentando el problema de la grabada, porque se paran todos los hilos.


        DefaultMutableTreeNode node = (DefaultMutableTreeNode) ppalView.getjTree1().getLastSelectedPathComponent();

        if (node == null) {
            return;
        }

        //SELECTION OF MAIN MENUS.
        try {
            CardLayout cl = (CardLayout) (ppalView.getPanelPpal().getLayout());
            CardLayout clIns = (CardLayout) (ppalView.getPanelConfEntradas().getLayout());
            boolean isNode = false;
            char opc = ' ';

            DefaultMutableTreeNode parentNodeText = (DefaultMutableTreeNode) node.getParent();
            String parentText = (String) parentNodeText.getUserObject();

            //If it's a in
            if (parentText.equals(PropHandler.getProperty("in.menu.name"))) {
                new InsControl().inSetType(node.getUserObject().toString(), ppalView);
                opc = ppalView.getMenuParents().get(node.getUserObject());
                isNode = false;
            } else if (parentText.equals(PropHandler.getProperty("btns.menu.name"))) {
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                String sel = parentNode.getUserObject().toString();
                new InsControl().inSetType(sel, ppalView);
                opc = ppalView.getMenuParents().get(parentNode.getUserObject());
                isNode = true;
            } else if (parentText.equals(PropHandler.getProperty("ftcl.menu.name"))) {
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                String sel = parentNode.getUserObject().toString();
                new InsControl().inSetType(sel, ppalView);
                opc = ppalView.getMenuParents().get(parentNode.getUserObject());
                isNode = true;
            } else if (parentText.equals(PropHandler.getProperty("sensors.menu.name"))) {
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                String sel = parentNode.getUserObject().toString();
                new InsControl().inSetType(sel, ppalView);
                opc = ppalView.getMenuParents().get(parentNode.getUserObject());
                isNode = true;
            }


            if (parentText.equals("SMAIEE")) {
                String sel = node.getUserObject().toString();
                opc = ppalView.getMenuParents().get(node.getUserObject());
                isNode = false;
            } else if (!parentText.equals(PropHandler.getProperty("in.menu.name"))) {
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                String sel = parentNode.getUserObject().toString();
                opc = ppalView.getMenuParents().get(parentNode.getUserObject());
                isNode = true;
            }


            switch (opc) {
                case 'c':
                    cl.show(ppalView.getPanelPpal(), "card3"); //Configuracion
                    break;

                case 'b':
                    if (isNode) {
                        String balastNumber = (String) node.getUserObject();
                        ppalView.setSelectedBalastNumber(balastNumber.split(" - ")[0]);
                        new BalastosControlJmodbus().showSelectedBalasto(ppalView.getSelectedBalastNumber(), ppalView);
                    } else {
                        BalastosControlJmodbus balastoCtrl = new BalastosControlJmodbus();
                        balastoCtrl.cleanBalastosView(ppalView);
                        balastoCtrl.filterAddedBalasts(ppalView);
                        //Verificamos que la conexion este activa
//                        TCPMasterConnection con = null;
////                        con.isConnected();


                    }
                    cl.show(ppalView.getPanelPpal(), "card2"); //Balastos
                    break;

                case 'g':
                    GroupsControl groupCtrl = new GroupsControl();
                    if (isNode) {
                        groupCtrl.cleanGroupView(ppalView);
                        String groupNumber = (String) node.getUserObject();
                        ppalView.setSelectedGroupNumber(groupNumber.split(" - ")[0]);
                        groupCtrl.showSelectedGroup(ppalView.getSelectedGroupNumber(), ppalView);
                    } else {
                        groupCtrl.cleanGroupView(ppalView);
                        groupCtrl.showAvailableBalasts(ppalView);
                        groupCtrl.filterAddedGroups(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card4"); //Grupos
                    break;

                case 's':
                    SceneControlJmodbus sceneCtrl = new SceneControlJmodbus();
                    if (isNode) {
                        sceneCtrl.cleanSceneView(ppalView);
                        String sceneNumber = (String) node.getUserObject();
                        ppalView.setSelectedSceneNumber(sceneNumber.split(" - ")[0]);
                        sceneCtrl.showSelectedScene(ppalView.getSelectedSceneNumber(), ppalView);
                    } else {
                        sceneCtrl.cleanSceneView(ppalView);
                        sceneCtrl.showAvailableSceneBalasts(ppalView);
                        sceneCtrl.filterAddedScenes(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card5"); //Escenas
                    break;

                case 'i':
//                    cl.show(panelPpal, "card6"); //Entradas
                    break;

                case 'e':
                    EventControl eventCtrl = new EventControl();
                    if (isNode) {
                        eventCtrl.cleanEventView(ppalView);
                        String eventNumber = (String) node.getUserObject();
                        ppalView.setSelectedEventNumber(eventNumber.split(" - ")[0]);
                        eventCtrl.showSelectedEvent(ppalView.getSelectedEventNumber(), ppalView);
                    } else {
                        eventCtrl.cleanEventView(ppalView);
                        eventCtrl.showAvailableEvents(ppalView);
                        eventCtrl.filterAddedEvent(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card7"); //Eventos
                    break;

                case 't':
                    cl.show(ppalView.getPanelPpal(), "card9"); //Configuracion en tiempo real
                    break;

                case 'd':
                    cl.show(ppalView.getPanelPpal(), "card10"); //Configuracion dali
                    break;

                //ins
                case 'a':
                    InsControl inCtrl = new InsControl();
                    if (isNode) {
                        inCtrl.cleanInView(ppalView);
                        ppalView.setSelectedInNumber((String) node.getUserObject());
                        inCtrl.showSelectedIn(ppalView.getSelectedInNumber(), ppalView);
                    } else {
                        inCtrl.cleanInView(ppalView);
                        inCtrl.filterAddedIn(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card6");
                    clIns.show(ppalView.getPanelConfEntradas(), "card2"); //Botoneras
                    break;
                case 'f':
                    InsControl inCtrlF = new InsControl();
                    if (isNode) {
                        inCtrlF.cleanInView(ppalView);
                        ppalView.setSelectedInNumber((String) node.getUserObject());
                        inCtrlF.showSelectedIn(ppalView.getSelectedInNumber(), ppalView);
                    } else {
                        inCtrlF.cleanInView(ppalView);
                        inCtrlF.filterAddedIn(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card6");
                    clIns.show(ppalView.getPanelConfEntradas(), "card3"); //Fotoceldas
                    break;
                case 'h':
                    InsControl inCtrlS = new InsControl();
                    if (isNode) {
                        inCtrlS.cleanInView(ppalView);
                        ppalView.setSelectedInNumber((String) node.getUserObject());
                        inCtrlS.showSelectedIn(ppalView.getSelectedInNumber(), ppalView);
                    } else {
                        inCtrlS.cleanInView(ppalView);
                        inCtrlS.filterAddedIn(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card6");
                    clIns.show(ppalView.getPanelConfEntradas(), "card4"); //Sensores
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ppalView, "Error cargando elementos desde la interfaz: " + e.getMessage(), null, 2);
            System.out.println("Error cargando los elementos desde la interfaz: ");
            e.printStackTrace();
        }

    }

    /**
     * Creates the principal elements of the menu.
     */
    public void menuParents(PpalView ppalView) {
        ppalView.setMenuParents(new HashMap<String, Character>());
        ppalView.getMenuParents().put(PropHandler.getProperty("config.menu.name"), 'c');
        ppalView.getMenuParents().put(PropHandler.getProperty("balast.menu.name"), 'b');
        ppalView.getMenuParents().put(PropHandler.getProperty("group.menu.name"), 'g');
        ppalView.getMenuParents().put(PropHandler.getProperty("scenes.menu.name"), 's');
        ppalView.getMenuParents().put(PropHandler.getProperty("in.menu.name"), 'i');
        ppalView.getMenuParents().put(PropHandler.getProperty("events.menu.name"), 'e');
        ppalView.getMenuParents().put(PropHandler.getProperty("realTime.menu.name"), 't');
        ppalView.getMenuParents().put(PropHandler.getProperty("dali.menu.name"), 'd');

        //ins
        ppalView.getMenuParents().put(PropHandler.getProperty("btns.menu.name"), 'a');
        ppalView.getMenuParents().put(PropHandler.getProperty("ftcl.menu.name"), 'f');
        ppalView.getMenuParents().put(PropHandler.getProperty("sensors.menu.name"), 'h');

    }

    /**
     * Creates a formatter
     *
     * @param s
     * @return
     */
    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
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
     * Validates an hour.
     *
     * @param hour
     * @return
     */
    public boolean hourValidator(String hour) {
        boolean match = false;
        Pattern pattern = Pattern.compile("[0-9]{1,2}\\:[0-9]{2}\\:[0-9]{2}");
        Matcher matcher = pattern.matcher(hour);

        try {
            String[] hourArray = hour.split(":");
            int h = Integer.parseInt(hourArray[0]);
            int m = Integer.parseInt(hourArray[1]);
            int s = Integer.parseInt(hourArray[2]);

            if ((matcher.find()) && (matcher.group().equals(hour))
                    && (h <= 23) && (m >= 0 && m <= 60) && (s >= 0 && s <= 60)) {
                match = true;
            } else {
                match = false;
            }
        } catch (Exception e) {
            match = false;
        }

        return match;
    }

    /**
     * Shows the connection status.
     */
    public void updateConnectionStatus(boolean status, PpalView ppalView) {
        if (status) {
            ppalView.getjLabel45().setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/connection.jpg")));
        } else {
            ppalView.getjLabel45().setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/noconnection.jpg")));
        }
    }

    /**
     * Enable or disable the static ip configuration.
     */
    public void enableIpConfig(PpalView ppalView) {
        if (ppalView.getCbIsStaticConfiguration().isSelected()) {
            ppalView.getFieldIp().setEnabled(true);
            ppalView.getFieldGateway().setEnabled(true);
            ppalView.getFieldPort().setEnabled(true);
            ppalView.getFieldMask().setEnabled(true);
            ppalView.getFieldPuerto().setEnabled(true);

            ppalView.getLabelDns().setEnabled(true);
            ppalView.getLabelGateway().setEnabled(true);
            ppalView.getLabelIp().setEnabled(true);
            ppalView.getLabelMascara().setEnabled(true);
            ppalView.getLabelPuerto().setEnabled(true);


        } else {
            ppalView.getFieldIp().setEnabled(false);
            ppalView.getFieldGateway().setEnabled(false);
            ppalView.getFieldPort().setEnabled(false);
            ppalView.getFieldMask().setEnabled(false);
            ppalView.getFieldPuerto().setEnabled(false);

            ppalView.getLabelDns().setEnabled(false);
            ppalView.getLabelGateway().setEnabled(false);
            ppalView.getLabelIp().setEnabled(false);
            ppalView.getLabelMascara().setEnabled(false);
            ppalView.getLabelPuerto().setEnabled(false);
        }
    }

    /**
     * Save general configuration.
     */
    public void saveConfiguration(PpalView ppalView) {

//        boolean connectionStatus = DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        updateConnectionStatus(true, ppalView);
        if (true) {

            try {
                ConfiguracionDAOJmodbus confDao = new ConfiguracionDAOJmodbus(ppalView.getDao());

                //RECOLECT INFORMATION
                int dia = 0;
                int diaSemana = 0;
                int mes = 0;
                int ano = 0;
                int h = 0;
                int m = 0;
                int s = 0;

                boolean isMaster = true; //True if master, false if slave

                String ip = "";
                String port = "";
                String mask = "";
                String gateway = "";


                //Date and time.
                IDateEditor ide = ppalView.getjDateChooser1().getDateEditor();
                Date fechaDate = ide.getDate();

                String hora = ppalView.getjFormattedTextField1().getText();
                String fechaString = "";

                if (fechaDate != null) {
                    Calendar calendar = Calendar.getInstance();

                    fechaString = new SimpleDateFormat("dd/MM/yyyy").format(fechaDate);
                    String[] fechaArray = fechaString.split("/");
                    dia = new Integer(fechaArray[0]).shortValue();
                    mes = new Integer(fechaArray[1]).shortValue();
                    ano = new Integer(fechaArray[2]).shortValue();

                    calendar.set(ano, mes - 1, dia);
                    diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

                    if (!hourValidator(hora)) {
                        Validation.showAlertMessage("Hora invalida.\nIntente de nuevo.");
                        return;
                    }

                    String[] hourArray = hora.split(":");
                    h = new Integer(hourArray[0]).shortValue();
                    m = new Integer(hourArray[1]).shortValue();
                    s = new Integer(hourArray[2]).shortValue();

                } else {
                    Validation.showAlertMessage("Fecha invalida.\nIntente de nuevo.");
                    return;
                }

                //Rol
                if (ppalView.getRbIsMaster().isSelected()) {
                    isMaster = true;
                }
                if (ppalView.getRbIsSlave().isSelected()) {
                    isMaster = false;
                }

                //Network configuration
                if (ppalView.getCbIsStaticConfiguration().isSelected()) {
                    ip = ppalView.getFieldIp().getText();
                    if (!ipValidator(ip)) {
                        Validation.showAlertMessage("Ip invalida.\nIntente de nuevo.");
                        return;
                    }

                    port = ppalView.getFieldPuerto().getText();
                    if (!portValidator(port)) {
                        Validation.showAlertMessage("Puerto invalido.\nIntente de nuevo.");
                        return;
                    }

                    mask = ppalView.getFieldMask().getText();
                    if (!ipValidator(mask)) {
                        Validation.showAlertMessage("Mascara invalida.\nIntente de nuevo.");
                        return;
                    }

                    gateway = ppalView.getFieldGateway().getText();
                    if (!ipValidator(gateway)) {
                        Validation.showAlertMessage("Gateway invalido.\nIntente de nuevo.");
                        return;
                    }
                }


                //STORE EVERYTHING
                //Date and time
                Fecha fecha = new Fecha(dia, diaSemana, mes, ano, h, m, s);
                confDao.saveDate(fecha);


                //Rol
                String rol = isMaster ? "0" : "1";
                confDao.saveRol(rol);


                //Network configuration
                String type = ppalView.getCbIsStaticConfiguration().isSelected() ? "1" : "0";
                if (ppalView.getCbIsStaticConfiguration().isSelected()) {
                    confDao.saveNetworkConf(type, ip, mask, gateway, port);
                }

            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, "Ocurrio un error!!!."
                        + "\nIntente de nuevo.", "Alerta", -1, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setRunMode(PpalView ppalView) {
        ConfiguracionDAOJmodbus cDao = new ConfiguracionDAOJmodbus(ppalView.getDao());
        cDao.setSingleReg(0, 0);
    }

    /**
     * Shows the configuration view.
     */
    public void initConfiguration(PpalView ppalView) {
//        stopConfigurationViewData();
        CardLayout cl = (CardLayout) (ppalView.getPanelPpal().getLayout());
        ppalView.getThreadManager().stopAllCurrentThreads();
        ppalView.getThreadManager().startThreadIfTerminated(ThreadManager.RTC_REFRESHING);
        cl.show(ppalView.getPanelPpal(), "card3"); //Configuracion
    }
//    /**
//     * Loads the data to be showed in the view
//     */
//    public void loadConfigurationViewData(PpalView ppalView) {
//        rtc.setPpalView(ppalView);
//        Thread showBalasts = rtc;
//        showBalasts.start();
//    }
//    
//    public void continueConfigurationViewData(PpalView ppalView) {
//        setRunMode(ppalView);
//        ppalView.getRealCtrl().stopRealTimeChecking();
//        Thread.State state = rtc.getState();
//        if (state.equals(Thread.State.TERMINATED)) {
//            rtc = new RefreshRTC();
//            loadConfigurationViewData(ppalView);
//        }
//    }
//    
//    /**
//     * Stop the refreshing
//     */
//    public void stopConfigurationViewData() {
//        Thread.State state = rtc.getState();
//        if (!state.equals(Thread.State.TERMINATED)) {
//            rtc.interrupt();
//        }
//    }

   /**
    * 
    * @param ppalView
    * @param a
    * @param b
    * @param c
    * @param d
    * @param f
    * @return 
    */
    public Boolean cargaInicial(PpalView ppalView,BalastosControlJmodbus a,GroupsControl b,SceneControlJmodbus c,EventControl d, InsControl f) {
//        BalastosControlJmodbus a = new BalastosControlJmodbus();
//        GroupsControl b = new GroupsControl();
//        SceneControlJmodbus c =new SceneControlJmodbus();
//        EventControl d = new EventControl();
//        InsControl f = new InsControl();
        
        try {
            a.refrescaVistaBalastos(ppalView);
            b.refrescarVistaGrupos(ppalView);
            c.refrescarVistaEscenas(ppalView);
            d.refrescaEventos(ppalView);
            f.refrescarVistaEntradas(ppalView);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        
    }
}
