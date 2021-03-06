/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.bo.*;
import com.isolux.dao.jmodbus.ConfiguracionDAOJmodbus;
import com.isolux.dao.jmodbus.UtilsJmodbus;
import com.isolux.dao.properties.PropHandler;
import com.isolux.dao.utils.SwingUtils;
import com.isolux.utils.Validacion;
import com.isolux.view.PpalView;
import com.isolux.view.threads.RefreshRTC;
import com.isolux.view.threads.ThreadManager;
import com.toedter.calendar.IDateEditor;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import javax.swing.tree.DefaultMutableTreeNode;

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
        new BalastosControl().readElements(ppalView);
        DefaultListModel modelo = new DefaultListModel();
        DefaultListModel cleanModelo = new DefaultListModel();
        show.setModel(modelo);
        remove.setModel(cleanModelo);

//        ArrayList<String> addedBalasts = PropHandler.getAddedBalasts(ppalView.getDao());
        HashMap<String, Balasto> balasts = ppalView.getBalasts();
        Set<String> addedBalasts = balasts.keySet();
        for (String balastNumber : addedBalasts) {
            Balasto balasto = balasts.get(balastNumber);
            modelo.addElement(balastNumber + " - " + balasto.getName());
        }
        show.setModel(modelo);
        remove.removeAll();
    }

    /**
     * Show the available balasts.
     */
    public void showAvailableGroups(JList show, JList remove, PpalView ppalView) {
        new GrupoControl().readElements(ppalView);
        DefaultListModel modelo = new DefaultListModel();
        DefaultListModel cleanModelo = new DefaultListModel();
        show.setModel(modelo);
        remove.setModel(cleanModelo);

        ArrayList<String> addedGroups = PropHandler.getAddedGroups(ppalView.getDao());
        HashMap<String, Grupo> gruposAgregados = ppalView.getGroups();


        for (String groupNumber : addedGroups) {

            int numero = (Integer.parseInt(groupNumber) + 1);
            String numeroAumentado = Integer.toString(numero);

            Grupo group = gruposAgregados.get(numeroAumentado);
            modelo.addElement(numeroAumentado + " - " + group.getName());
        }
        show.setModel(modelo);
        remove.removeAll();
    }

    /**
     * Show the available balasts.
     */
    public void showAvailableScenes(JList show, JList remove, PpalView ppalView) {
        new EscenaControl().readElements(ppalView);
        DefaultListModel modelo = new DefaultListModel();
        DefaultListModel cleanModelo = new DefaultListModel();
        show.setModel(modelo);
        remove.setModel(cleanModelo);

        ArrayList<String> addedScenes = PropHandler.getAddedScenes(ppalView.getDao());
        HashMap<String, Escena> escenasAgregadas = ppalView.getScenes();

        for (String sceneNumber : addedScenes) {
            
            int numero = (Integer.parseInt(sceneNumber) + 1);
            String numeroAumentado = Integer.toString(numero);
            
            Escena scene = escenasAgregadas.get(numeroAumentado);
            modelo.addElement(numeroAumentado + " - " + scene.getNombre());
        }
        show.setModel(modelo);
        remove.removeAll();
    }

    /**
     * Tree selection. Controla la selección del elemento en el árbol de
     * jerarquía
     */
    public void treeSelection(PpalView ppalView, RealTimeControl realCtrl) {
        //Stops current threads.
        ppalView.getThreadManager().stopAllCurrentThreads();// aqui se puede estar presentando el problema de la grabada, porque se paran todos los hilos.


        DefaultMutableTreeNode node = (DefaultMutableTreeNode) ppalView.getArbol_jTree().getLastSelectedPathComponent();

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
            String parentText;
            if (parentNodeText == null) {
                parentText = "SMAIEE";
            } else {
                parentText = (String) parentNodeText.getUserObject();
            }



            //If it's a in
            if (parentText.equals(PropHandler.getProperty("in.menu.name"))) {
                new EntradaControl().inSetType(node.getUserObject().toString(), ppalView);
                opc = ppalView.getMenuParents().get(node.getUserObject());
                isNode = false;
            } else if (parentText.equals(PropHandler.getProperty("btns.menu.name"))) {
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                String sel = parentNode.getUserObject().toString();
                new EntradaControl().inSetType(sel, ppalView);
                opc = ppalView.getMenuParents().get(parentNode.getUserObject());
                isNode = true;
            } else if (parentText.equals(PropHandler.getProperty("ftcl.menu.name"))) {
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                String sel = parentNode.getUserObject().toString();
                new EntradaControl().inSetType(sel, ppalView);
                opc = ppalView.getMenuParents().get(parentNode.getUserObject());
                isNode = true;
            } else if (parentText.equals(PropHandler.getProperty("sensors.menu.name"))) {
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                String sel = parentNode.getUserObject().toString();
                new EntradaControl().inSetType(sel, ppalView);
                opc = ppalView.getMenuParents().get(parentNode.getUserObject());
                isNode = true;
            }


            if (parentText.equals("SMAIEE")) {
                //                String op = node.getUserObject().toString();
                Character cha = ppalView.getMenuParents().get(node.getUserObject());
                if (cha != null) {

                    opc = ppalView.getMenuParents().get(node.getUserObject());

                } else {
                    opc = 'x';
                }

//               
                isNode = false;
            } else if (!parentText.equals(PropHandler.getProperty("in.menu.name"))) {
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                String sel = parentNode.getUserObject().toString();
                opc = ppalView.getMenuParents().get(parentNode.getUserObject());
//                opc='x';
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
                        new BalastosControl().showSelectedElement(ppalView.getSelectedBalastNumber(), ppalView);
                    } else {
                        BalastosControl balastoCtrl = new BalastosControl();
                        balastoCtrl.cleanView(ppalView);
                        balastoCtrl.filterAddedElements(ppalView);
                        //Verificamos que la conexion este activa
//                        TCPMasterConnection con = null;
////                        con.isConnected();


                    }
                    cl.show(ppalView.getPanelPpal(), "card2"); //Balastos
                    break;

                case 'g':
                    GrupoControl groupCtrl = new GrupoControl();
                    if (isNode) {
                        groupCtrl.cleanView(ppalView);
                        String groupNumber = (String) node.getUserObject();
                        ppalView.setSelectedGroupNumber(groupNumber.split(" - ")[0]);
                        groupCtrl.showSelectedElement(ppalView.getSelectedGroupNumber(), ppalView);
                    } else {
                        groupCtrl.cleanView(ppalView);
                        groupCtrl.showAvailableBalasts(ppalView);
                        groupCtrl.filterAddedElements(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card4"); //Grupos
                    break;

                case 's':
                    EscenaControl sceneCtrl = new EscenaControl();
                    if (isNode) {
                        sceneCtrl.cleanView(ppalView);
                        String sceneNumber = (String) node.getUserObject();
                        ppalView.setSelectedSceneNumber(sceneNumber.split(" - ")[0]);
                        sceneCtrl.showSelectedElement(ppalView.getSelectedSceneNumber(), ppalView);
                    } else {
                        sceneCtrl.cleanView(ppalView);
//                        sceneCtrl.showAvailableSceneBalasts(ppalView);
                        sceneCtrl.filterAddedElements(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card5"); //Escenas
                    break;

                case 'i':
//                    cl.show(panelPpal, "card6"); //Entradas
                    break;

                case 'e':
                    EventoControl eventCtrl = new EventoControl();
                    if (isNode) {
                        eventCtrl.cleanView(ppalView);
                        String eventNumber = (String) node.getUserObject();
                        ppalView.setSelectedEventNumber(eventNumber.split(" - ")[0]);
                        eventCtrl.showSelectedElement(ppalView.getSelectedEventNumber(), ppalView);
                    } else {
                        eventCtrl.cleanView(ppalView);
                        eventCtrl.showAvailableEvents(ppalView);
                        eventCtrl.filterAddedElements(ppalView);
                    }
                    eventCtrl.selectByDays(ppalView);
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
                    EntradaControl inCtrl = new EntradaControl();
                    if (isNode) {
                        inCtrl.cleanView(ppalView);
                        ppalView.setSelectedInNumber((String) node.getUserObject());
                        inCtrl.showSelectedElement(ppalView.getSelectedInNumber(), ppalView);
                    } else {
                        inCtrl.cleanView(ppalView);
                        inCtrl.filterAddedElements(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card6");
                    clIns.show(ppalView.getPanelConfEntradas(), "card2"); //Botoneras
                    break;
                case 'f':
                    EntradaControl inCtrlF = new EntradaControl();
                    if (isNode) {
                        inCtrlF.cleanView(ppalView);
                        ppalView.setSelectedInNumber((String) node.getUserObject());
                        inCtrlF.showSelectedElement(ppalView.getSelectedInNumber(), ppalView);
                    } else {
                        inCtrlF.cleanView(ppalView);
                        inCtrlF.filterAddedElements(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card6");
                    clIns.show(ppalView.getPanelConfEntradas(), "card3"); //Fotoceldas
                    break;
                case 'h':
                    EntradaControl inCtrlS = new EntradaControl();
                    if (isNode) {
                        inCtrlS.cleanView(ppalView);
                        ppalView.setSelectedInNumber((String) node.getUserObject());
                        inCtrlS.showSelectedElement(ppalView.getSelectedInNumber(), ppalView);
                    } else {
                        inCtrlS.cleanView(ppalView);
                        inCtrlS.filterAddedElements(ppalView);
                    }
                    cl.show(ppalView.getPanelPpal(), "card6");
                    clIns.show(ppalView.getPanelConfEntradas(), "card4"); //Sensores
                    break;

                case 'x':
//                     System.out.println("Se selecciono el padre del arbol");
//                    Object nodoPadre= ppalView.getArbol_jTree().getModel().getRoot();
//                    TreePath path =new TreePath(nodoPadre);
//                    boolean colapsado=ppalView.getArbol_jTree().isCollapsed(path); 
//                    
//                    ppalView.expandirArbol(ppalView.getArbol_jTree(), path, colapsado);
                    break;

                default:

                    break;
            }
        } catch (Exception e) {
            
            Logger.getLogger(GeneralControl.class.getName()).log(Level.SEVERE, "Error Cargando los elementos de la interfaz.", e);
            JOptionPane.showMessageDialog(ppalView,"Error cargando elementos desde la interfaz:\n" + e, "Error",JOptionPane.ERROR_MESSAGE);
            
            
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
    public boolean ipValidator(String ip) throws Exception {
        boolean match = false;
        Pattern pattern = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
//        Pattern pattern = Pattern.compile("([1-2][0-5]?[0-9]?\\.){3}([0-2][0-5]?[0-9]?)");

        Matcher matcher = pattern.matcher(ip);
        boolean find = matcher.find();
        boolean equals = matcher.group().equals(ip);
        if (find && equals) {
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
            ppalView.getGrabarIp_jButton().setEnabled(true);


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
            ppalView.getGrabarIp_jButton().setEnabled(false);
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
                        Validacion.showAlertMessage("Hora invalida.\nIntente de nuevo.");
                        return;
                    }

                    String[] hourArray = hora.split(":");
                    h = new Integer(hourArray[0]).shortValue();
                    m = new Integer(hourArray[1]).shortValue();
                    s = new Integer(hourArray[2]).shortValue();

                } else {
                    Validacion.showAlertMessage("Fecha invalida.\nIntente de nuevo.");
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
                    ip = ppalView.getIp_jTextField().getText();
                    if (!ipValidator(ip)) {
                        Validacion.showAlertMessage("Ip invalida.\nIntente de nuevo.");
                        return;
                    }

                    port = ppalView.getPuerto_jTextField().getText();
                    if (!portValidator(port)) {
                        Validacion.showAlertMessage("Puerto invalido.\nIntente de nuevo.");
                        return;
                    }

                    mask = ppalView.getFieldMask().getText();
                    if (!ipValidator(mask)) {
                        Validacion.showAlertMessage("Mascara invalida.\nIntente de nuevo.");
                        return;
                    }

                    gateway = ppalView.getFieldGateway().getText();
                    if (!ipValidator(gateway)) {
                        Validacion.showAlertMessage("Gateway invalido.\nIntente de nuevo.");
                        return;
                    }
                }else{ // es dinámica
                    
                }


                //STORE EVERYTHING
                //Date and time
                Fecha fecha = new Fecha(dia, diaSemana, mes, ano, h, m, s);
                confDao.saveDate(fecha);


                //Rol
                String rol = isMaster ? "0" : "1";
                confDao.saveRol(rol);


                //Save Network configuration
//                String type = ppalView.getCbIsStaticConfiguration().isSelected() ? "1" : "0";
                String type = ppalView.getCbIsStaticConfiguration().isSelected() ? "0" : "1";
//                if (ppalView.getCbIsStaticConfiguration().isSelected()) {
                    confDao.saveNetworkConf(type, ip, mask, gateway, port);


//                }
                JOptionPane.showMessageDialog(ppalView, "¡Guardada la configuracion exitosamente!");
            } catch (Exception e) {

                Logger.getLogger(GeneralControl.class.getName()).log(Level.SEVERE, "Error grabando la configuración general.", e);
                JOptionPane.showConfirmDialog(null, "Ocurrio un error!!!.\nIntente de nuevo.", "Alerta", -1, JOptionPane.ERROR_MESSAGE);



            }
        }
    }

    public void setRunMode(PpalView ppalView) throws Exception {
        ConfiguracionDAOJmodbus cDao = new ConfiguracionDAOJmodbus(ppalView.getDao());
        cDao.setSingleReg(0, 0);
    }

    /**
     * Shows the configuration view.
     */
    public void initConfiguration(PpalView ppalView) {
        try {
            //        stopConfigurationViewData();
                    setRunMode(ppalView);
                    CardLayout cl = (CardLayout) (ppalView.getPanelPpal().getLayout());
                    ThreadManager threadManager = ppalView.getThreadManager();
            //        threadManager.startThreadIfTerminated(ThreadManager.RTC_REFRESHING);
                    threadManager.stopAllCurrentThreads();
                     activarGuiConfigGeneral(ppalView, false);
                    threadManager.startThreadIfTerminated(ThreadManager.RTC_REFRESHING);
                    
                    cl.show(ppalView.getPanelPpal(), "card3"); //Configuracion

                    ppalView.getTabbedPane().setSelectedIndex(0);
        } catch (Exception ex) {
            Logger.getLogger(GeneralControl.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
       
    }
    
    /**
     * Desactiva los elementos de la interfaz de configuración general. Los pone en el estado por defecto
     * @param ppalView
     * @param activo si los activa o los desactiva.
     */   
    public void activarGuiConfigGeneral(PpalView ppalView, boolean activo){
        SwingUtils.setEnableContainer(ppalView.getPanelConfiguracion(), activo);
        ppalView.getConfigEnviar_jButton().setEnabled(true);
        ppalView.getConfigHoraSistema_jButton().setEnabled(true);
        ppalView.getConfigRedEstatica_JCheckbox().setEnabled(true);
        ppalView.getConfigRedDinamica_jCheckBox().setEnabled(true);
        seleccionarTipoDeConfiguracionDeRed(ppalView);
        
        this.enableIpConfig(ppalView);
        this.cargarVarolesIpConfig(ppalView);
        ppalView.seleccionarConfigRed();
        
    }
    
    /**
     * Selecciona desde la tarjeta el tipo de configuración de red en la GUI
     * @param ppalView 
     */
    private void seleccionarTipoDeConfiguracionDeRed(PpalView ppalView){
        ppalView.getConfigRedEstatica_JCheckbox().setSelected(false);
        ppalView.getConfigRedDinamica_jCheckBox().setSelected(false);
        int[] regValue = ppalView.getDao().getRegValue(200, 1);
        int type = regValue[0];
        if (type==1) { //es dinamico
            ppalView.getConfigRedDinamica_jCheckBox().setSelected(true);
            ppalView.getConfigRedEstatica_JCheckbox().setSelected(false);
            ppalView.getIp_jTextField().setEnabled(false);
            ppalView.getGateway_jTextField().setEnabled(false);
            ppalView.getPuerto_jTextField().setEnabled(false);
            ppalView.getMask_jTextField().setEnabled(false);
        }else{//es estatico
            ppalView.getConfigRedDinamica_jCheckBox().setSelected(false);
            ppalView.getConfigRedEstatica_JCheckbox().setSelected(true);
            ppalView.getIp_jTextField().setEnabled(true);
            ppalView.getGateway_jTextField().setEnabled(true);
            ppalView.getPuerto_jTextField().setEnabled(true);
            ppalView.getMask_jTextField().setEnabled(true);
        }
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
     * @param g
     * @return
     */
    public Boolean cargaInicial(PpalView ppalView, BalastosControl a, GrupoControl b, EscenaControl c, EventoControl d, EntradaControl f, BalastosConfiguracionControl g) {
//        BalastosControlJmodbus a = new BalastosControlJmodbus();
//        GroupsControl b = new GroupsControl();
//        SceneControlJmodbus c =new SceneControlJmodbus();
//        EventControl d = new EventControl();
//        InsControl f = new InsControl();

        try {
//            ppalView.getBarraProgreso_jProgressBar().setIndeterminate(true);
            a.refrescarVista(ppalView);

            b.refrescarVista(ppalView);
            c.refrescarVista(ppalView);
            d.refrescarVista(ppalView);
            g.refrescarVista(ppalView);
            f.refrescarVista(ppalView);

            cargarVarolesIpConfig(ppalView);
//            ppalView.getBarraProgreso_jProgressBar().setIndeterminate(false);

            return true;
        } catch (Exception e) {
            Logger.getLogger(GeneralControl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }


    }

    public void cargarVarolesIpConfig(PpalView ppalView) {
        try {
            //cargamos valores de ip, gateway, mascara, puerto
            String ip = com.isolux.properties.PropHandler.getProperty("general.ip");
            String mask = com.isolux.properties.PropHandler.getProperty("general.ip.mask");
            String gateway = com.isolux.properties.PropHandler.getProperty("general.ip.gateway");
            String puerto = com.isolux.properties.PropHandler.getProperty("general.port");

            if (ppalView.getConfigRedEstatica_JCheckbox().isSelected()) {
            ppalView.getIp_jTextField().setText(ip);
            ppalView.getMask_jTextField().setText(mask);
            ppalView.getGateway_jTextField().setText(gateway);
            ppalView.getPuerto_jTextField().setText(puerto);    
            }else{
                ppalView.getIp_jTextField().setText("");
            ppalView.getMask_jTextField().setText("");
            ppalView.getGateway_jTextField().setText("");
            ppalView.getPuerto_jTextField().setText("");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ppalView, "Configurando por primera vez");
        }

    }

    /**
     * Activa o desactiva todos los componentes de la interfaz
     *
     * @param c
     * @param activar
     */
    public void habilitarTodo(Container c, boolean activar) {
        SwingUtils.setEnableContainer(c, activar);
    }
}
