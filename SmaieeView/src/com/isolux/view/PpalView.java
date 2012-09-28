/*
 * ConfPpalView.java
 *
 * Created on 01-ago-2011, 17:47:34
 */
package com.isolux.view;

import com.isolux.bo.*;
import com.isolux.control.BalastosControlJmodbus;
import com.isolux.control.EventControl;
import com.isolux.control.GeneralControl;
import com.isolux.control.GroupsControl;
import com.isolux.control.InsControl;
import com.isolux.control.RealTimeControl;
import com.isolux.control.SceneControlJmodbus;
import com.isolux.dao.jmodbus.ConfiguracionDAOJmodbus;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import com.isolux.view.threads.ThreadManager;
import com.toedter.calendar.JDateChooser;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class PpalView extends javax.swing.JFrame {

    //GENERAL
    private static PpalView index;
    private Map<String, Character> menuParents;
    private String ip;
    private int port;
    
    //LOAD FLAGS
    private boolean balastsStauts = false;
    private boolean groupsStauts = false;
    private boolean sceneStauts = false;
    private boolean inStauts = false;
    private boolean eventStauts = false;
    private boolean showAreas = true;
    
    
    //LOADED ELEMENS
    private HashMap<String, Balasto> balasts;
    private HashMap<String, Grupo> groups;
    private HashMap<String, Escena> scenes;
    private HashMap<String, Entrada> ins;
    private HashMap<String, Evento> events;
    
    //SELECTIONS
    private String selectedBalastNumber;
    private String selectedGroupNumber;
    private String selectedSceneNumber;
    private String selectedInNumber;
    private String selectedEventNumber;
    
    //Added elements
    private HashMap<String, Balasto> groupBalasts;
    private HashMap<String, Balasto> sceneBalasts;
    private HashMap<String, Balasto> inBalasts;
    private HashMap<String, Grupo> inGroups;
    private HashMap<String, Escena> inScene;
    
    private HashMap<String, Balasto> eventBalasts;
    private HashMap<String, Grupo> eventGroups;
    private HashMap<String, Escena> eventScene;
    
    //Balasts values 
    private int[] sceneBalastsValues;
    private int[] eventBalastsValues;
    
    //in variables
    private int inType;
    private int inOutType;
    
    //Event variables
    private int eventOutType;

    
    //CONTROLLERS
    private BalastosControlJmodbus balastosCtrl;
    private EventControl eventCtrl;
    private GeneralControl generalCtrl;
    private GroupsControl groupsCtrl;
    private InsControl insCtrl;
    private RealTimeControl realCtrl;
    private SceneControlJmodbus sceneCtrl;
    
    //MODBUS DAO
    private DAOJmodbus dao;
    
    
    //Checking threads
    private ThreadManager threadManager;
    
    
    //Init Ctrls
    public void initControls(){
        this.balastosCtrl = new BalastosControlJmodbus();
        this.eventCtrl = new EventControl();
        this.generalCtrl = new GeneralControl();
        this.groupsCtrl = new GroupsControl();
        this.insCtrl = new InsControl();
        this.realCtrl = new RealTimeControl();
        this.sceneCtrl = new SceneControlJmodbus();
    }
    
    public void initAddedElements(){
        this.balasts = new HashMap<String, Balasto>();
        this.groups = new HashMap<String, Grupo>();
        this.scenes = new HashMap<String, Escena>();
        this.ins = new HashMap<String, Entrada>();
        this.events = new HashMap<String, Evento>();
    }
    
    //Init selections.
    public void initSelections() {
        selectedBalastNumber = "";
        selectedGroupNumber = "";
        selectedSceneNumber = "";
        selectedInNumber = "";
        selectedEventNumber = "";
    }
    
    public void initModbus() {
        this.dao = new DAOJmodbus();
    }
    
    public void getConnectionProperties(){
        this.ip = PropHandler.getProperty("general.ip");
        this.port = Integer.parseInt(PropHandler.getProperty("general.port"));
    }

    public void initInOutTypes(){
        inType = 0;
        inOutType = 0;
        eventOutType = 0;
    }
    
    public void initThreads(){
        this.threadManager = new ThreadManager(this);
    }
    
    
    
    
    /** Creates new form ConfPpalView */
    public PpalView() {
        applicationTheme();
        initControls();
        initModbus();
        initSelections();
        initAddedElements();
        new GeneralControl().menuParents(this);
        getConnectionProperties();
        initInOutTypes();
        initThreads();

        initComponents();
//        generalCtrl.loadConfigurationViewData(this);
        showAreas = true;
//        new RealTimeControl().refreshBalastsLevels(this);
    }

    
    
    /** 
     * GENERATED CODE
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        botoneraComportamiento = new javax.swing.ButtonGroup();
        botoneraTipoContacto = new javax.swing.ButtonGroup();
        confRol = new javax.swing.ButtonGroup();
        header = new javax.swing.JPanel();
        headerImage = new javax.swing.JLabel();
        statusBar = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        statusLabel = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        tabbedPane = new javax.swing.JTabbedPane();
        tabConfiguracion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        panelPpal = new javax.swing.JPanel();
        panelBienvenida = new javax.swing.JPanel();
        panelConfiguracion = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jButton24 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        cbIsStaticConfiguration = new javax.swing.JCheckBox();
        labelIp = new javax.swing.JLabel();
        labelMascara = new javax.swing.JLabel();
        labelGateway = new javax.swing.JLabel();
        fieldGateway = new javax.swing.JFormattedTextField();
        fieldMask = new javax.swing.JFormattedTextField();
        fieldIp = new javax.swing.JFormattedTextField();
        labelDns = new javax.swing.JLabel();
        fieldPort = new javax.swing.JFormattedTextField();
        labelPuerto = new javax.swing.JLabel();
        fieldPuerto = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        rbIsMaster = new javax.swing.JRadioButton();
        rbIsSlave = new javax.swing.JRadioButton();
        panelGrupos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jLabel39 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        panelEscenas = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList5 = new javax.swing.JList();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jButton39 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox();
        panelEntradas = new javax.swing.JPanel();
        panelConfEntradas = new javax.swing.JPanel();
        panelEntradaInit = new javax.swing.JPanel();
        panelBotonera = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jList14 = new javax.swing.JList();
        jScrollPane16 = new javax.swing.JScrollPane();
        jList15 = new javax.swing.JList();
        balastros_jCheckBox = new javax.swing.JCheckBox();
        grupos_jCheckBox = new javax.swing.JCheckBox();
        jCheckBox18 = new javax.swing.JCheckBox();
        jButton14 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton40 = new javax.swing.JButton();
        panelFotocelda = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jList8 = new javax.swing.JList();
        jScrollPane10 = new javax.swing.JScrollPane();
        jList9 = new javax.swing.JList();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jButton41 = new javax.swing.JButton();
        panelSensor = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jScrollPane17 = new javax.swing.JScrollPane();
        jList16 = new javax.swing.JList();
        jScrollPane18 = new javax.swing.JScrollPane();
        jList17 = new javax.swing.JList();
        jCheckBox19 = new javax.swing.JCheckBox();
        jCheckBox20 = new javax.swing.JCheckBox();
        jButton16 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jLabel66 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jTextField36 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        panelEventos = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        jList12 = new javax.swing.JList();
        jScrollPane14 = new javax.swing.JScrollPane();
        jList13 = new javax.swing.JList();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jButton48 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jButton17 = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        jButton43 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextField13 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        panelConfDali = new javax.swing.JPanel();
        panelBalastos = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel41 = new javax.swing.JLabel();
        tabMonitoreo = new javax.swing.JPanel();
        panelTiempoReal = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        jButton38 = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jSpinner3 = new javax.swing.JSpinner();
        jLabel56 = new javax.swing.JLabel();
        jButton44 = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        mainMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SMAIEE - Configuración DALI");

        header.setBackground(new java.awt.Color(0, 101, 137));

        headerImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/title.png"))); // NOI18N

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(headerImage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        statusBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel34.setText("Conexion");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        statusLabel.setText("#");

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/connection.jpg"))); // NOI18N
        jLabel45.setText("jLabel45");
        jLabel45.setMaximumSize(new java.awt.Dimension(33, 9));
        jLabel45.setMinimumSize(new java.awt.Dimension(33, 9));

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(306, 306, 306)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusBarLayout.createSequentialGroup()
                .addGroup(statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator2)
                        .addGroup(statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(statusLabel))
                .addContainerGap())
        );

        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                selectTab(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("SMAIEE");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Balastos");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Grupos");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Escenas");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Entradas");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Botoneras");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Fotoceldas");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sensores");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Eventos");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Dali");
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        panelPpal.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout panelBienvenidaLayout = new javax.swing.GroupLayout(panelBienvenida);
        panelBienvenida.setLayout(panelBienvenidaLayout);
        panelBienvenidaLayout.setHorizontalGroup(
            panelBienvenidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 558, Short.MAX_VALUE)
        );
        panelBienvenidaLayout.setVerticalGroup(
            panelBienvenidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 551, Short.MAX_VALUE)
        );

        panelPpal.add(panelBienvenida, "card8");

        panelConfiguracion.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Hora del sistema"));

        jDateChooser1.setEnabled(false);

        jLabel2.setText("Fecha");

        jLabel3.setText("Hora");

        jFormattedTextField1.setToolTipText("Formato hora: hh:mm");
        jFormattedTextField1.setEnabled(false);

        jButton24.setText("Configurar");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton24)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton24))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton22.setText("Enviar");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de red"));

        cbIsStaticConfiguration.setText("Configuración estática");
        cbIsStaticConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbIsStaticConfigurationActionPerformed(evt);
            }
        });

        labelIp.setText("IP");
        labelIp.setEnabled(false);

        labelMascara.setText("Máscara");
        labelMascara.setEnabled(false);

        labelGateway.setText("Gateway");
        labelGateway.setEnabled(false);

        fieldGateway.setEnabled(false);

        fieldMask.setEnabled(false);

        fieldIp.setEnabled(false);

        labelDns.setText("Puerto");
        labelDns.setEnabled(false);

        fieldPort.setToolTipText("Puerto por defecto: 502");
        fieldPort.setEnabled(false);

        labelPuerto.setText("Puerto");
        labelPuerto.setEnabled(false);

        fieldPuerto.setEnabled(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelIp)
                    .addComponent(labelMascara)
                    .addComponent(labelPuerto))
                .addGap(13, 13, 13)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldPuerto, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(fieldMask, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(fieldIp, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDns)
                    .addComponent(labelGateway))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldPort, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(fieldGateway, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addGap(171, 171, 171))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(cbIsStaticConfiguration)
                .addContainerGap(381, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbIsStaticConfiguration)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIp)
                    .addComponent(fieldIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDns)
                    .addComponent(fieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldMask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelGateway)
                    .addComponent(fieldGateway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMascara))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPuerto)
                    .addComponent(fieldPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Rol"));

        confRol.add(rbIsMaster);
        rbIsMaster.setText("Maestro");

        confRol.add(rbIsSlave);
        rbIsSlave.setSelected(true);
        rbIsSlave.setText("Esclavo");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbIsMaster)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbIsSlave)
                .addContainerGap(392, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbIsMaster)
                    .addComponent(rbIsSlave))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelConfiguracionLayout = new javax.swing.GroupLayout(panelConfiguracion);
        panelConfiguracion.setLayout(panelConfiguracionLayout);
        panelConfiguracionLayout.setHorizontalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton22)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelConfiguracionLayout.setVerticalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191)
                .addComponent(jButton22)
                .addContainerGap())
        );

        panelPpal.add(panelConfiguracion, "card3");

        panelGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Grupos"));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Balastos"));

        jButton3.setText("<");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText(">");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText(">>");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("<<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(jList2);

        jScrollPane4.setViewportView(jList3);

        jLabel39.setText("Disponibles");

        jLabel57.setText("Agregados");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(225, 225, 225)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel4.setText("Nombre");

        jButton6.setText("Enviar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Eliminar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel58.setText("#");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione uno", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));

        javax.swing.GroupLayout panelGruposLayout = new javax.swing.GroupLayout(panelGrupos);
        panelGrupos.setLayout(panelGruposLayout);
        panelGruposLayout.setHorizontalGroup(
            panelGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGruposLayout.createSequentialGroup()
                .addGroup(panelGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGruposLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGruposLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(30, 30, 30)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGruposLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)))
                .addContainerGap())
        );
        panelGruposLayout.setVerticalGroup(
            panelGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGruposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelGruposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        panelPpal.add(panelGrupos, "card4");

        panelEscenas.setBorder(javax.swing.BorderFactory.createTitledBorder("Escenas"));

        jLabel5.setText("Nivel");

        jLabel6.setText("Escena");

        jButton8.setText("Enviar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Eliminar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Balastos"));

        jButton10.setText("<");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText(">");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText(">>");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("<<");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jScrollPane5.setViewportView(jList4);

        jList5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                selectBalast(evt);
            }
        });
        jScrollPane6.setViewportView(jList5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel18.setText("Balasto:");

        jLabel19.setText("#");

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel61.setText("#");

        jButton39.setText("Actualizar nivel");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione una", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));

        javax.swing.GroupLayout panelEscenasLayout = new javax.swing.GroupLayout(panelEscenas);
        panelEscenas.setLayout(panelEscenasLayout);
        panelEscenasLayout.setHorizontalGroup(
            panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEscenasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelEscenasLayout.createSequentialGroup()
                        .addGroup(panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(jTextField23, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEscenasLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19))
                            .addComponent(jLabel61))
                        .addGap(18, 18, 18)
                        .addGroup(panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelEscenasLayout.createSequentialGroup()
                                .addComponent(jButton39)
                                .addGap(153, 153, 153))
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelEscenasLayout.createSequentialGroup()
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)))
                .addContainerGap())
        );
        panelEscenasLayout.setVerticalGroup(
            panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEscenasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEscenasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addContainerGap())
        );

        panelPpal.add(panelEscenas, "card5");

        panelEntradas.setBorder(javax.swing.BorderFactory.createTitledBorder("Entradas"));

        panelConfEntradas.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout panelEntradaInitLayout = new javax.swing.GroupLayout(panelEntradaInit);
        panelEntradaInit.setLayout(panelEntradaInitLayout);
        panelEntradaInitLayout.setHorizontalGroup(
            panelEntradaInitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );
        panelEntradaInitLayout.setVerticalGroup(
            panelEntradaInitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
        );

        panelConfEntradas.add(panelEntradaInit, "card5");

        panelBotonera.setBorder(javax.swing.BorderFactory.createTitledBorder("Botonera"));

        jLabel7.setText("Comportamiento");

        botoneraComportamiento.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Mantenido");

        botoneraComportamiento.add(jRadioButton2);
        jRadioButton2.setText("Momentaneo");

        jLabel8.setText("% ON");

        jLabel10.setText("% OFF");

        jTextField5.setText("0");

        jTextField6.setText("0");

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));

        jButton30.setText("<");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton31.setText(">");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setText(">>");
        jButton32.setMaximumSize(new java.awt.Dimension(41, 23));
        jButton32.setMinimumSize(new java.awt.Dimension(41, 23));
        jButton32.setPreferredSize(new java.awt.Dimension(41, 23));
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton33.setText("<<");
        jButton33.setMaximumSize(new java.awt.Dimension(41, 23));
        jButton33.setMinimumSize(new java.awt.Dimension(41, 23));
        jButton33.setPreferredSize(new java.awt.Dimension(41, 23));
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jScrollPane15.setViewportView(jList14);

        jScrollPane16.setViewportView(jList15);

        balastros_jCheckBox.setText("Balastos");
        balastros_jCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balastros_jCheckBoxActionPerformed(evt);
            }
        });

        grupos_jCheckBox.setText("Grupos");
        grupos_jCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grupos_jCheckBoxActionPerformed(evt);
            }
        });

        jCheckBox18.setText("Escena");
        jCheckBox18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton32, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(jButton31, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(jButton30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(balastros_jCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grupos_jCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox18)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(balastros_jCheckBox)
                    .addComponent(grupos_jCheckBox)
                    .addComponent(jCheckBox18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jButton31, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton30, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton32, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton14.setText("Enviar");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel17.setText("Tipo contacto");

        botoneraTipoContacto.add(jRadioButton3);
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("NA");

        botoneraTipoContacto.add(jRadioButton4);
        jRadioButton4.setText("NC");

        jButton40.setText("Eliminar");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotoneraLayout = new javax.swing.GroupLayout(panelBotonera);
        panelBotonera.setLayout(panelBotoneraLayout);
        panelBotoneraLayout.setHorizontalGroup(
            panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotoneraLayout.createSequentialGroup()
                .addGroup(panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotoneraLayout.createSequentialGroup()
                        .addGroup(panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBotoneraLayout.createSequentialGroup()
                                .addGroup(panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelBotoneraLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButton1))
                                    .addGroup(panelBotoneraLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel17)
                                        .addGap(32, 32, 32)
                                        .addComponent(jRadioButton3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton4)
                                    .addComponent(jRadioButton2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE))
                            .addGroup(panelBotoneraLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8)
                                .addGap(23, 23, 23)
                                .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                .addGap(77, 77, 77)
                                .addComponent(jLabel10)))
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                    .addGroup(panelBotoneraLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBotoneraLayout.createSequentialGroup()
                                .addComponent(jButton14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton40))
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelBotoneraLayout.setVerticalGroup(
            panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotoneraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBotoneraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton14)
                    .addComponent(jButton40)))
        );

        panelConfEntradas.add(panelBotonera, "card2");

        panelFotocelda.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Fotocelda")));

        jLabel11.setText("Tiempo de retardo");

        jLabel12.setText("Nivel ilum/voltio");

        jTextField7.setText("0");

        jTextField8.setText("0");

        jLabel13.setText("Nivel deseado");

        jTextField9.setText("0");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));

        jButton18.setText("<");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setText(">");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setText(">>");
        jButton20.setMaximumSize(new java.awt.Dimension(41, 23));
        jButton20.setMinimumSize(new java.awt.Dimension(41, 23));
        jButton20.setPreferredSize(new java.awt.Dimension(41, 23));
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setText("<<");
        jButton21.setMaximumSize(new java.awt.Dimension(41, 23));
        jButton21.setMinimumSize(new java.awt.Dimension(41, 23));
        jButton21.setPreferredSize(new java.awt.Dimension(41, 23));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jScrollPane9.setViewportView(jList8);

        jScrollPane10.setViewportView(jList9);

        jCheckBox10.setText("Balastos");
        jCheckBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox10ActionPerformed(evt);
            }
        });

        jCheckBox11.setText("Grupos");
        jCheckBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox11ActionPerformed(evt);
            }
        });

        jCheckBox12.setText("Escena");
        jCheckBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                            .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                            .addComponent(jButton18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                            .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jCheckBox10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox12)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox10)
                    .addComponent(jCheckBox11)
                    .addComponent(jCheckBox12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, 0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                        .addGap(7, 7, 7)
                        .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel14.setText("Ganancia");

        jTextField10.setText("0");

        jButton15.setText("Enviar");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel37.setText("seg");

        jButton41.setText("Eliminar");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFotoceldaLayout = new javax.swing.GroupLayout(panelFotocelda);
        panelFotocelda.setLayout(panelFotoceldaLayout);
        panelFotoceldaLayout.setHorizontalGroup(
            panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFotoceldaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelFotoceldaLayout.createSequentialGroup()
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton41))
                    .addGroup(panelFotoceldaLayout.createSequentialGroup()
                        .addGroup(panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField7)
                            .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel37)
                        .addGap(13, 13, 13)
                        .addGroup(panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField9)
                            .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelFotoceldaLayout.setVerticalGroup(
            panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFotoceldaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFotoceldaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15)
                    .addComponent(jButton41)))
        );

        panelConfEntradas.add(panelFotocelda, "card3");

        panelSensor.setBorder(javax.swing.BorderFactory.createTitledBorder("Sensor"));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));

        jButton34.setText("<");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jButton35.setText(">");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jButton36.setText(">>");
        jButton36.setMaximumSize(new java.awt.Dimension(41, 23));
        jButton36.setMinimumSize(new java.awt.Dimension(41, 23));
        jButton36.setPreferredSize(new java.awt.Dimension(41, 23));
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jButton37.setText("<<");
        jButton37.setMaximumSize(new java.awt.Dimension(41, 23));
        jButton37.setMinimumSize(new java.awt.Dimension(41, 23));
        jButton37.setPreferredSize(new java.awt.Dimension(41, 23));
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jScrollPane17.setViewportView(jList16);

        jScrollPane18.setViewportView(jList17);

        jCheckBox19.setText("Balastos");
        jCheckBox19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox19ActionPerformed(evt);
            }
        });

        jCheckBox20.setText("Grupos");
        jCheckBox20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton36, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(jButton35, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(jButton34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(jButton37, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jCheckBox19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox20)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox19)
                    .addComponent(jCheckBox20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jButton35, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton34, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton36, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton37, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton16.setText("Enviar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton42.setText("Eliminar");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jLabel66.setText("Nivel ON");

        jTextField34.setText("0");

        jLabel67.setText("Nivel OFF");

        jTextField35.setText("0");

        jLabel68.setText("Retardo");

        jTextField36.setText("0");

        javax.swing.GroupLayout panelSensorLayout = new javax.swing.GroupLayout(panelSensor);
        panelSensor.setLayout(panelSensorLayout);
        panelSensorLayout.setHorizontalGroup(
            panelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSensorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelSensorLayout.createSequentialGroup()
                        .addComponent(jButton16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton42))
                    .addGroup(panelSensorLayout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelSensorLayout.setVerticalGroup(
            panelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSensorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67)
                    .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68)
                    .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16)
                    .addComponent(jButton42)))
        );

        panelConfEntradas.add(panelSensor, "card4");

        jLabel62.setText("Entrada número:");

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel63.setText("#");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione uno", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEntradasLayout = new javax.swing.GroupLayout(panelEntradas);
        panelEntradas.setLayout(panelEntradasLayout);
        panelEntradasLayout.setHorizontalGroup(
            panelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelConfEntradas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEntradasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelEntradasLayout.setVerticalGroup(
            panelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEntradasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62)
                    .addComponent(jLabel63))
                .addGap(11, 11, 11)
                .addComponent(panelConfEntradas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPpal.add(panelEntradas, "card6");

        panelEventos.setBorder(javax.swing.BorderFactory.createTitledBorder("Eventos"));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));

        jButton26.setText("<");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setText(">");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setText(">>");
        jButton28.setMaximumSize(new java.awt.Dimension(41, 23));
        jButton28.setMinimumSize(new java.awt.Dimension(41, 23));
        jButton28.setPreferredSize(new java.awt.Dimension(41, 23));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton29.setText("<<");
        jButton29.setMaximumSize(new java.awt.Dimension(41, 23));
        jButton29.setMinimumSize(new java.awt.Dimension(41, 23));
        jButton29.setPreferredSize(new java.awt.Dimension(41, 23));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jScrollPane13.setViewportView(jList12);

        jList13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                selectElements(evt);
            }
        });
        jScrollPane14.setViewportView(jList13);

        jCheckBox13.setText("Balastos");
        jCheckBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox13ActionPerformed(evt);
            }
        });

        jCheckBox14.setText("Grupos");
        jCheckBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox14ActionPerformed(evt);
            }
        });

        jCheckBox15.setText("Escena");
        jCheckBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox15ActionPerformed(evt);
            }
        });

        jLabel21.setText("Nivel");

        jLabel22.setText("Balasto / Grupo:");

        jLabel23.setText("#");

        jTextField25.setText("0");

        jButton48.setText("Actualizar nivel");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jCheckBox13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton27, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                .addComponent(jButton26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(jButton48)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jButton48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox13)
                    .addComponent(jCheckBox14)
                    .addComponent(jCheckBox15))
                .addGap(5, 5, 5)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jButton27, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton26, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton28, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel20.setText("Nombre");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha y hora"));

        jLabel15.setText("Fecha");

        jLabel24.setText("Hora");

        jFormattedTextField2.setText("00:00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFormattedTextField2)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Días"));

        jLabel16.setText("Días");
        jLabel16.setEnabled(false);

        jCheckBox3.setText("L");
        jCheckBox3.setEnabled(false);

        jCheckBox4.setText("M");
        jCheckBox4.setEnabled(false);

        jCheckBox5.setText("M");
        jCheckBox5.setEnabled(false);

        jCheckBox6.setText("J");
        jCheckBox6.setEnabled(false);

        jCheckBox7.setText("V");
        jCheckBox7.setEnabled(false);

        jCheckBox8.setText("S");
        jCheckBox8.setEnabled(false);

        jCheckBox9.setText("D");
        jCheckBox9.setEnabled(false);

        jCheckBox2.setText("Repetir ciclo");
        jCheckBox2.setEnabled(false);

        jLabel25.setText("Hora");
        jLabel25.setEnabled(false);

        jFormattedTextField3.setText("00:00");
        jFormattedTextField3.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox2))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox9)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox4)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox8)
                    .addComponent(jCheckBox9))
                .addGap(8, 8, 8)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton17.setText("Enviar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel64.setText("#");

        jButton43.setText("Eliminar");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Por días");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jTextField13.setText("1");
        jTextField13.setToolTipText("De 0 a 1023");
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });

        jLabel36.setText("Número:");

        javax.swing.GroupLayout panelEventosLayout = new javax.swing.GroupLayout(panelEventos);
        panelEventos.setLayout(panelEventosLayout);
        panelEventosLayout.setHorizontalGroup(
            panelEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEventosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelEventosLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel64)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelEventosLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelEventosLayout.createSequentialGroup()
                        .addComponent(jButton17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton43)))
                .addContainerGap())
        );
        panelEventosLayout.setVerticalGroup(
            panelEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEventosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64)
                    .addComponent(jCheckBox1)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton43))
                .addContainerGap())
        );

        panelPpal.add(panelEventos, "card7");

        panelConfDali.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración Dali"));

        javax.swing.GroupLayout panelConfDaliLayout = new javax.swing.GroupLayout(panelConfDali);
        panelConfDali.setLayout(panelConfDaliLayout);
        panelConfDaliLayout.setHorizontalGroup(
            panelConfDaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );
        panelConfDaliLayout.setVerticalGroup(
            panelConfDaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );

        panelPpal.add(panelConfDali, "card10");

        panelBalastos.setBorder(javax.swing.BorderFactory.createTitledBorder("Balastos"));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("DALI"));

        jLabel26.setText("Dir");

        jLabel27.setText("Min");

        jLabel28.setText("Max");

        jLabel29.setText("FT");

        jLabel30.setText("FR");

        jLabel31.setText("LF");

        jLabel32.setText("LX");

        jLabel33.setText("Pot");

        jTextField26.setText("0");

        jTextField27.setText("0");

        jTextField28.setText("0");

        jTextField29.setText("0");

        jTextField30.setText("0");

        jTextField31.setText("0");

        jTextField32.setText("0");

        jTextField33.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField33, 0, 1, Short.MAX_VALUE)
                    .addComponent(jTextField32, 0, 1, Short.MAX_VALUE)
                    .addComponent(jTextField31, 0, 1, Short.MAX_VALUE)
                    .addComponent(jTextField30, 0, 1, Short.MAX_VALUE)
                    .addComponent(jTextField29, 0, 1, Short.MAX_VALUE)
                    .addComponent(jTextField28, 0, 1, Short.MAX_VALUE)
                    .addComponent(jTextField27, 0, 1, Short.MAX_VALUE)
                    .addComponent(jTextField26, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(281, Short.MAX_VALUE))
        );

        jLabel1.setText("Nombre");

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton23.setText("Eliminar");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jLabel9.setText("Nivel");

        jTextField20.setText("0");

        jLabel40.setText("Dirección Dali");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un número", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setText("#");

        javax.swing.GroupLayout panelBalastosLayout = new javax.swing.GroupLayout(panelBalastos);
        panelBalastos.setLayout(panelBalastosLayout);
        panelBalastosLayout.setHorizontalGroup(
            panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBalastosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBalastosLayout.createSequentialGroup()
                        .addGroup(panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9))
                        .addGap(32, 32, 32)
                        .addGroup(panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelBalastosLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel41)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelBalastosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton23)
                .addGap(398, 398, 398))
        );
        panelBalastosLayout.setVerticalGroup(
            panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBalastosLayout.createSequentialGroup()
                .addGroup(panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBalastosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 370, Short.MAX_VALUE)
                        .addGroup(panelBalastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton23)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelPpal.add(panelBalastos, "card2");

        javax.swing.GroupLayout tabConfiguracionLayout = new javax.swing.GroupLayout(tabConfiguracion);
        tabConfiguracion.setLayout(tabConfiguracionLayout);
        tabConfiguracionLayout.setHorizontalGroup(
            tabConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(577, Short.MAX_VALUE))
            .addGroup(tabConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabConfiguracionLayout.createSequentialGroup()
                    .addGap(157, 157, 157)
                    .addComponent(panelPpal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        tabConfiguracionLayout.setVerticalGroup(
            tabConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(tabConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tabConfiguracionLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelPpal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        tabbedPane.addTab("Configuración", tabConfiguracion);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione el área" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nombre", "Nivel"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectBalasto(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jLabel44.setText("Balastos por área");

        jButton38.setText("Imagen área");
        jButton38.setEnabled(false);
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel53.setText("Balasto:");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel54.setText("#");

        jLabel55.setText("Nivel");

        jSlider1.setForeground(new java.awt.Color(0, 0, 0));
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setToolTipText("Seleccione el nivel del balasto");
        jSlider1.setValue(0);
        jSlider1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                saveBalastState(evt);
            }
        });
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                selectSliderValue(evt);
            }
        });

        jLabel56.setBackground(new java.awt.Color(153, 153, 153));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/photo_not_available.jpg"))); // NOI18N

        jButton44.setText("Editar");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel65.setText("#");

        jButton46.setText("Eliminar área");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });

        jButton47.setText("Nueva área");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTiempoRealLayout = new javax.swing.GroupLayout(panelTiempoReal);
        panelTiempoReal.setLayout(panelTiempoRealLayout);
        panelTiempoRealLayout.setHorizontalGroup(
            panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTiempoRealLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTiempoRealLayout.createSequentialGroup()
                        .addComponent(jButton47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton46))
                    .addGroup(panelTiempoRealLayout.createSequentialGroup()
                        .addGroup(panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTiempoRealLayout.createSequentialGroup()
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton38))
                            .addGroup(panelTiempoRealLayout.createSequentialGroup()
                                .addComponent(jLabel53)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel54)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel65))
                            .addGroup(panelTiempoRealLayout.createSequentialGroup()
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTiempoRealLayout.createSequentialGroup()
                                    .addComponent(jLabel44)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTiempoRealLayout.setVerticalGroup(
            panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTiempoRealLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                    .addGroup(panelTiempoRealLayout.createSequentialGroup()
                        .addGroup(panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton38))
                        .addGap(18, 18, 18)
                        .addGroup(panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel53)
                            .addComponent(jLabel54)
                            .addComponent(jLabel65))
                        .addGap(15, 15, 15)
                        .addGroup(panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55)
                            .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE)
                        .addGroup(panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(jButton44))
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelTiempoRealLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton46)
                    .addComponent(jButton47))
                .addContainerGap())
        );

        javax.swing.GroupLayout tabMonitoreoLayout = new javax.swing.GroupLayout(tabMonitoreo);
        tabMonitoreo.setLayout(tabMonitoreoLayout);
        tabMonitoreoLayout.setHorizontalGroup(
            tabMonitoreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
            .addGroup(tabMonitoreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelTiempoReal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabMonitoreoLayout.setVerticalGroup(
            tabMonitoreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
            .addGroup(tabMonitoreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelTiempoReal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Monitoreo y control en tiempo real", tabMonitoreo);

        jMenu1.setText("Archivo");

        jMenuItem5.setText("Guardar en flash");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarEnFlash(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Leer flash");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readFromFlash(evt);
            }
        });
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator4);

        jMenuItem2.setText("Formatear memoria");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formatearMemoria(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator3);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Configuración");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectConfiguracion(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        mainMenuBar.add(jMenu1);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPane)
                    .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    /**
     * ELEMENT EVETNS
     */
    // <editor-fold defaultstate="collapsed" desc="Element events">
    
    /**
     * Tree selection.
     * @param evt 
     */
    protected void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
//        this.generalCtrl.treeSelection(this, realCtrl);
    }//GEN-LAST:event_jTree1ValueChanged

    /**
     * Load configuration view.
     * @param evt 
     */
    protected void selectConfiguracion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectConfiguracion
        this.generalCtrl.initConfiguration(this);
    }//GEN-LAST:event_selectConfiguracion

    /**
     * Select the ins
     * @param evt 
     */
    /**
     * Sets the value of the slider in the spinner
     * @param evt 
     */
    protected void selectSliderValue(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_selectSliderValue
        this.realCtrl.setSpinnerValue(this);
    }//GEN-LAST:event_selectSliderValue

    /**
     * Send balast
     * @param evt 
     */
    protected void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.balastosCtrl.saveBalast(this);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Show the selection file window. (Real time control).
     * @param evt 
     */
    protected void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        this.realCtrl.uploadAreaPicture(this);
    }//GEN-LAST:event_jButton38ActionPerformed

    /**
     * Static ip configuration
     */
    protected void cbIsStaticConfigurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbIsStaticConfigurationActionPerformed
        this.generalCtrl.enableIpConfig(this);
    }//GEN-LAST:event_cbIsStaticConfigurationActionPerformed

    /**
     * Enviar: Configuracion general
     * @param evt 
     */
    protected void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        jDateChooser1.setEnabled(false);
        jFormattedTextField1.setEnabled(false);
        this.generalCtrl.saveConfiguration(this);
        threadManager.startThreadIfTerminated(ThreadManager.RTC_REFRESHING);
    }//GEN-LAST:event_jButton22ActionPerformed

    /**
     * Delete balast.
     * @param evt 
     */
    protected void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        this.balastosCtrl.deleteBalast(this);
    }//GEN-LAST:event_jButton23ActionPerformed

    /**
     * Add balasts to the group.
     * @param evt 
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ViewUtils.addSelected(jList2, jList3);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * Remove a balast from the group.
     * @param evt 
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ViewUtils.remSelected(jList2, jList3);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * Add all balasts to the group.
     * @param evt 
     */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ViewUtils.addAll(jList2, jList3);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * Remove all balasts from the group.
     * @param evt 
     */
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ViewUtils.remAll(jList2, jList3);
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * Send the group.
     * @param evt 
     */
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.groupsCtrl.saveGroup(this);
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * Deletes a group
     * @param evt 
     */
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.groupsCtrl.deleteGroup(this);
    }//GEN-LAST:event_jButton7ActionPerformed

    /**
     * Add balasts to the scene.
     * @param evt 
     */
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        ViewUtils.addSelected(jList4, jList5);
    }//GEN-LAST:event_jButton11ActionPerformed

    /**
     * Remove selected balasts from the scene.
     * @param evt 
     */
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        ViewUtils.remSelected(jList4, jList5);
    }//GEN-LAST:event_jButton10ActionPerformed

    /**
     * Add all available balasts to the scene.
     * @param evt 
     */
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        ViewUtils.addAll(jList4, jList5);
    }//GEN-LAST:event_jButton12ActionPerformed

    /**
     * Remove all selected balasts from the scene.
     * @param evt 
     */
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        ViewUtils.remAll(jList4, jList5);
    }//GEN-LAST:event_jButton13ActionPerformed

    /**
     * Save a scene
     * @param evt 
     */
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.sceneCtrl.saveScene(this);
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * Deletes a scene.
     * @param evt 
     */
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        this.sceneCtrl.deleteScene(this);
    }//GEN-LAST:event_jButton9ActionPerformed

    /**
     * Select a scene balast
     * @param evt 
     */
    private void selectBalast(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectBalast
        this.sceneCtrl.selectEsceneBalast(this);
    }//GEN-LAST:event_selectBalast

    /**
     * Update scene balast level.
     * @param evt 
     */
    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        this.sceneCtrl.updateEsceneBalastLevel(this);
    }//GEN-LAST:event_jButton39ActionPerformed

    /**
     * Select element Btns (ins)
     * @param evt 
     */
    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        ViewUtils.addSelected(jList14, jList15);
    }//GEN-LAST:event_jButton31ActionPerformed

    /**
     * Remove selected element Btns (ins)
     * @param evt 
     */
    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        ViewUtils.remSelected(jList14, jList15);
    }//GEN-LAST:event_jButton30ActionPerformed

    /**
     * Select all the elemens. Btns (ins)
     * @param evt 
     */
    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        ViewUtils.addAll(jList14, jList15);
    }//GEN-LAST:event_jButton32ActionPerformed

    /**
     * Remove all the elemens. Btns (ins)
     * @param evt 
     */
    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        ViewUtils.remAll(jList14, jList15);
    }//GEN-LAST:event_jButton33ActionPerformed

    /**
     * Save btns.
     * @param evt 
     */
    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        this.insCtrl.saveIn(this);
    }//GEN-LAST:event_jButton14ActionPerformed

    /**
     * Select an element in the ftcld view.
     * @param evt 
     */
    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        ViewUtils.addSelected(jList8, jList9);
    }//GEN-LAST:event_jButton19ActionPerformed

    /**
     * Remove selected ftcld.
     * @param evt 
     */
    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        ViewUtils.remSelected(jList8, jList9);
    }//GEN-LAST:event_jButton18ActionPerformed

    /**
     * Add all items ftcld.
     * @param evt 
     */
    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        ViewUtils.addAll(jList8, jList9);
    }//GEN-LAST:event_jButton20ActionPerformed

    /**
     * Remove all items.
     * @param evt 
     */
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        ViewUtils.remAll(jList8, jList9);
    }//GEN-LAST:event_jButton21ActionPerformed

    /**
     * Select balasts items ftcld.
     * @param evt 
     */
    private void jCheckBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox10ActionPerformed
        inOutType = ViewUtils.selectCheks(jCheckBox10, jCheckBox11, jCheckBox12);
        this.generalCtrl.showAvailableBalasts(jList8, jList9, this);
    }//GEN-LAST:event_jCheckBox10ActionPerformed

     /**
     * Select group items ftcld.
     * @param evt 
     */
    private void jCheckBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox11ActionPerformed
        inOutType = ViewUtils.selectCheks(jCheckBox10, jCheckBox11, jCheckBox12);
        this.generalCtrl.showAvailableGroups(jList8, jList9, this);
    }//GEN-LAST:event_jCheckBox11ActionPerformed

     /**
     * Select scenes items ftcld.
     * @param evt 
     */
    private void jCheckBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox12ActionPerformed
        inOutType = ViewUtils.selectCheks(jCheckBox10, jCheckBox11, jCheckBox12);
        this.generalCtrl.showAvailableScenes(jList8, jList9, this);
    }//GEN-LAST:event_jCheckBox12ActionPerformed

    /**
     * Select balasts items Btns
     * @param evt 
     */
    private void balastros_jCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balastros_jCheckBoxActionPerformed
        inOutType = ViewUtils.selectCheks(balastros_jCheckBox, grupos_jCheckBox, jCheckBox18);
        this.generalCtrl.showAvailableBalasts(jList14, jList15, this);
    }//GEN-LAST:event_balastros_jCheckBoxActionPerformed

    /**
     * Select group items Btns.
     * @param evt 
     */
    private void grupos_jCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grupos_jCheckBoxActionPerformed
        inOutType = ViewUtils.selectCheks(balastros_jCheckBox, grupos_jCheckBox, jCheckBox18);
        this.generalCtrl.showAvailableGroups(jList14, jList15, this);
    }//GEN-LAST:event_grupos_jCheckBoxActionPerformed

    /**
     * Select scene items Btns.
     * @param evt 
     */
    private void jCheckBox18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox18ActionPerformed
        inOutType = ViewUtils.selectCheks(balastros_jCheckBox, grupos_jCheckBox, jCheckBox18);
        this.generalCtrl.showAvailableScenes(jList14, jList15, this);
    }//GEN-LAST:event_jCheckBox18ActionPerformed

    /**
     * Select balasts items sensor
     * @param evt 
     */
    private void jCheckBox19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox19ActionPerformed
        inOutType = ViewUtils.selectCheksSensor(jCheckBox19, jCheckBox20);
        this.generalCtrl.showAvailableBalasts(jList16, jList17, this);
    }//GEN-LAST:event_jCheckBox19ActionPerformed

    /**
     * Select item sensors.
     * @param evt 
     */
    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        ViewUtils.addSelected(jList16, jList17);
    }//GEN-LAST:event_jButton35ActionPerformed

    /**
     * Remove item sensors
     */
    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        ViewUtils.remSelected(jList16, jList17);
    }//GEN-LAST:event_jButton34ActionPerformed

    /**
     * Add all sensors.
     * @param evt 
     */
    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        ViewUtils.addAll(jList16, jList17);
    }//GEN-LAST:event_jButton36ActionPerformed

    /**
     * Remove all sensors.
     * @param evt 
     */
    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        ViewUtils.remAll(jList16, jList17);
    }//GEN-LAST:event_jButton37ActionPerformed

    /**
     * Select group items sensor
     * @param evt 
     */
    private void jCheckBox20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox20ActionPerformed
        inOutType = ViewUtils.selectCheksSensor(jCheckBox19, jCheckBox20);
        this.generalCtrl.showAvailableGroups(jList16, jList17, this);
    }//GEN-LAST:event_jCheckBox20ActionPerformed

    /**
     * Save sensor.
     * @param evt 
     */
    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        this.insCtrl.saveIn(this);
    }//GEN-LAST:event_jButton16ActionPerformed

    /**
     * Save ftcld
     * @param evt 
     */
    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        this.insCtrl.saveIn(this);
    }//GEN-LAST:event_jButton15ActionPerformed

    /**
     * Delete in: btns
     * @param evt
     */
    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        this.insCtrl.deleteIn(this);
    }//GEN-LAST:event_jButton40ActionPerformed

    /**
     * Delete in: ftcld
     * @param evt
     */
    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        this.insCtrl.deleteIn(this);
    }//GEN-LAST:event_jButton41ActionPerformed

    /**
     * Delete in: sensor
     * @param evt
     */
    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        this.insCtrl.deleteIn(this);
    }//GEN-LAST:event_jButton42ActionPerformed

    /**
     * Add select items. Events
     * @param evt 
     */
    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        ViewUtils.addSelected(jList12, jList13);
    }//GEN-LAST:event_jButton27ActionPerformed

    /**
     * Remove selected items. Events
     * @param evt 
     */
    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        ViewUtils.remSelected(jList12, jList13);
    }//GEN-LAST:event_jButton26ActionPerformed

    /**
     * Add all items. Events.
     * @param evt 
     */
    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        ViewUtils.addAll(jList12, jList13);
    }//GEN-LAST:event_jButton28ActionPerformed

    /**
     * Remove all items. Events.
     * @param evt 
     */
    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        ViewUtils.remAll(jList12, jList13);
    }//GEN-LAST:event_jButton29ActionPerformed

    /**
     * Show available balasts.
     * @param evt 
     */
    private void jCheckBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox13ActionPerformed
        eventOutType = ViewUtils.selectCheks(jCheckBox13, jCheckBox14, jCheckBox15);
        this.generalCtrl.showAvailableBalasts(jList12, jList13, this);
    }//GEN-LAST:event_jCheckBox13ActionPerformed

     /**
     * Show available groups.
     * @param evt 
     */
    private void jCheckBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox14ActionPerformed
        eventOutType = ViewUtils.selectCheks(jCheckBox13, jCheckBox14, jCheckBox15);
        this.generalCtrl.showAvailableGroups(jList12, jList13, this);
    }//GEN-LAST:event_jCheckBox14ActionPerformed

     /**
     * Show available scenes.
     * @param evt 
     */
    private void jCheckBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox15ActionPerformed
        eventOutType = ViewUtils.selectCheks(jCheckBox13, jCheckBox14, jCheckBox15);
        this.generalCtrl.showAvailableScenes(jList12, jList13, this);
    }//GEN-LAST:event_jCheckBox15ActionPerformed

    /**
     * Seleccionar por fecha o por dia.
     * @param evt 
     */
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        this.eventCtrl.selectByDays(this);
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    /**
     * Envio de evento.
     * @param evt 
     */
    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        this.eventCtrl.saveEvent(this);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    /**
     * Charge the available balasts.
     * @param evt 
     */
    private void selectTab(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_selectTab
//        generalCtrl.continueConfigurationViewData(this);
        this.realCtrl.showAreas(this);
    }//GEN-LAST:event_selectTab

    /**
     * Seleccionar un abalasto.
     * @param evt 
     */
    private void selectBalasto(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectBalasto
        this.realCtrl.seleccionarBalasto(this);
    }//GEN-LAST:event_selectBalasto

    
    /**
     * Add a new area
     * @param evt 
     */
    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        this.realCtrl.newArea(this);
    }//GEN-LAST:event_jButton47ActionPerformed

    /**
     * Select elements to set the values.
     * @param evt 
     */
    private void selectElements(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectElements
        this.eventCtrl.selectElements(this);
    }//GEN-LAST:event_selectElements

    /**
     * Actualizar nivel.
     * @param evt 
     */
    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        this.eventCtrl.updateLevel(this);
    }//GEN-LAST:event_jButton48ActionPerformed

    /**
     * Selecting an area
     */
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        this.realCtrl.selectArea(this);        
    }//GEN-LAST:event_jComboBox2ActionPerformed

    /**
     * Saves program in flash memory.
     * @param evt 
     */
    private void guardarEnFlash(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarEnFlash
        ConfiguracionDAOJmodbus.saveToFlash();
    }//GEN-LAST:event_guardarEnFlash

    /**
     * Read from flash.
     * @param evt 
     */
    private void readFromFlash(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readFromFlash
        ConfiguracionDAOJmodbus.readFromFlash();
    }//GEN-LAST:event_readFromFlash

    /**
     * Delete events.
     * @param evt 
     */
    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        this.eventCtrl.deleteEvent(this);
    }//GEN-LAST:event_jButton43ActionPerformed

    /**
     * Saves balast state.
     * @param evt 
     */
    private void saveBalastState(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveBalastState
        this.realCtrl.monitoreoBalasto(this);
    }//GEN-LAST:event_saveBalastState

    /**
     * Deletes the selected area.
     * @param evt 
     */
    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        this.realCtrl.deleteArea(this);
    }//GEN-LAST:event_jButton46ActionPerformed

    /**
     * Edit balasts per area.
     * @param evt 
     */
    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        this.realCtrl.areaBalasts(this);
    }//GEN-LAST:event_jButton44ActionPerformed

    
    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    /**
     * Erase the memory
     */
    private void formatearMemoria(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formatearMemoria
        ConfiguracionDAOJmodbus.eraseMemory();
    }//GEN-LAST:event_formatearMemoria

    /**
     * Configure date and time.
     * @param evt 
     */
    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        if (!jDateChooser1.isEnabled() && !jFormattedTextField1.isEnabled()) {
            jDateChooser1.setEnabled(true);
            jFormattedTextField1.setEnabled(true);
            threadManager.stopAllCurrentThreads();
        } else {
            jDateChooser1.setEnabled(false);
            jFormattedTextField1.setEnabled(false);
            threadManager.startThreadIfTerminated(ThreadManager.RTC_REFRESHING);
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        this.generalCtrl.treeSelection(this, realCtrl);
    }//GEN-LAST:event_jTree1MouseClicked

//    private void startCheckingThread(java.awt.event.FocusEvent evt) {
//        this.realCtrl.checkBalasts(this);
//    }
//
//    private void stoptCheckingThread(java.awt.event.FocusEvent evt) {
//        this.realCtrl.stopCheckingBalasts(this);
//    }
    
    //</editor-fold>
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                index = new PpalView();
                index.setLocationRelativeTo(null);
                index.setVisible(true);
            }
        });
    }
    
    /*
     * Variable declarations
     */
    // <editor-fold defaultstate="collapsed" desc="Variable declarations">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox balastros_jCheckBox;
    private javax.swing.ButtonGroup botoneraComportamiento;
    private javax.swing.ButtonGroup botoneraTipoContacto;
    private javax.swing.JCheckBox cbIsStaticConfiguration;
    private javax.swing.ButtonGroup confRol;
    private javax.swing.JFormattedTextField fieldGateway;
    protected javax.swing.JFormattedTextField fieldIp;
    private javax.swing.JFormattedTextField fieldMask;
    private javax.swing.JFormattedTextField fieldPort;
    private javax.swing.JTextField fieldPuerto;
    private javax.swing.JCheckBox grupos_jCheckBox;
    private javax.swing.JPanel header;
    private javax.swing.JLabel headerImage;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox18;
    private javax.swing.JCheckBox jCheckBox19;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox20;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JComboBox jComboBox2;
    protected javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    protected com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    protected javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList12;
    private javax.swing.JList jList13;
    private javax.swing.JList jList14;
    private javax.swing.JList jList15;
    private javax.swing.JList jList16;
    private javax.swing.JList jList17;
    protected javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JList jList4;
    private javax.swing.JList jList5;
    private javax.swing.JList jList8;
    private javax.swing.JList jList9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JTable jTable1;
    protected javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField2;
    protected javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField25;
    protected javax.swing.JTextField jTextField26;
    protected javax.swing.JTextField jTextField27;
    protected javax.swing.JTextField jTextField28;
    protected javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    protected javax.swing.JTextField jTextField30;
    protected javax.swing.JTextField jTextField31;
    protected javax.swing.JTextField jTextField32;
    protected javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel labelDns;
    private javax.swing.JLabel labelGateway;
    private javax.swing.JLabel labelIp;
    private javax.swing.JLabel labelMascara;
    private javax.swing.JLabel labelPuerto;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JPanel panelBalastos;
    private javax.swing.JPanel panelBienvenida;
    private javax.swing.JPanel panelBotonera;
    private javax.swing.JPanel panelConfDali;
    private javax.swing.JPanel panelConfEntradas;
    private javax.swing.JPanel panelConfiguracion;
    private javax.swing.JPanel panelEntradaInit;
    private javax.swing.JPanel panelEntradas;
    private javax.swing.JPanel panelEscenas;
    private javax.swing.JPanel panelEventos;
    private javax.swing.JPanel panelFotocelda;
    private javax.swing.JPanel panelGrupos;
    private javax.swing.JPanel panelPpal;
    private javax.swing.JPanel panelSensor;
    private javax.swing.JPanel panelTiempoReal;
    private javax.swing.JRadioButton rbIsMaster;
    private javax.swing.JRadioButton rbIsSlave;
    private javax.swing.JPanel statusBar;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JPanel tabConfiguracion;
    private javax.swing.JPanel tabMonitoreo;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>

    
    
    //VIEW
    /**
     * Sets the application theme
     */
    public void applicationTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(PpalView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Encapsulates the select area event.
     * @param evt 
     */
    public void selectingAreaEncapsulating(java.awt.event.ActionEvent evt){
        jComboBox2ActionPerformed(evt);
    }

    /**
     * Sets the icon image of the application.
     * @return 
     */
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("images/icono.png"));
        return retValue;
    }
    
    
    
    
    
    /**
     * GET SETS
     */
    // <editor-fold defaultstate="collapsed" desc="GET / SETS">
    // Variables declaration - do not modify     
    public HashMap<String, Balasto> getBalasts() {
        return balasts;
    }

    public void setBalasts(HashMap<String, Balasto> balasts) {
        this.balasts = balasts;
    }

    public boolean getBalastsStauts() {
        return balastsStauts;
    }

    public void setBalastsStauts(boolean balastsStauts) {
        this.balastsStauts = balastsStauts;
    }

    public HashMap<String, Balasto> getEventBalasts() {
        return eventBalasts;
    }

    public void setEventBalasts(HashMap<String, Balasto> eventBalasts) {
        this.eventBalasts = eventBalasts;
    }

    public int[] getEventBalastsValues() {
        return eventBalastsValues;
    }

    public void setEventBalastsValues(int[] eventBalastsValues) {
        this.eventBalastsValues = eventBalastsValues;
    }

    public HashMap<String, Grupo> getEventGroups() {
        return eventGroups;
    }

    public void setEventGroups(HashMap<String, Grupo> eventGroups) {
        this.eventGroups = eventGroups;
    }

    public HashMap<String, Escena> getEventScene() {
        return eventScene;
    }

    public void setEventScene(HashMap<String, Escena> eventScene) {
        this.eventScene = eventScene;
    }

    public boolean getEventStauts() {
        return eventStauts;
    }

    public void setEventStauts(boolean eventStauts) {
        this.eventStauts = eventStauts;
    }

    public HashMap<String, Evento> getEvents() {
        return events;
    }

    public void setEvents(HashMap<String, Evento> events) {
        this.events = events;
    }

    public HashMap<String, Balasto> getGroupBalasts() {
        return groupBalasts;
    }

    public void setGroupBalasts(HashMap<String, Balasto> groupBalasts) {
        this.groupBalasts = groupBalasts;
    }

    public HashMap<String, Grupo> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, Grupo> groups) {
        this.groups = groups;
    }

    public boolean getGroupsStauts() {
        return groupsStauts;
    }

    public void setGroupsStauts(boolean groupsStauts) {
        this.groupsStauts = groupsStauts;
    }

    public HashMap<String, Balasto> getInBalasts() {
        return inBalasts;
    }

    public void setInBalasts(HashMap<String, Balasto> inBalasts) {
        this.inBalasts = inBalasts;
    }

    public HashMap<String, Grupo> getInGroups() {
        return inGroups;
    }

    public void setInGroups(HashMap<String, Grupo> inGroups) {
        this.inGroups = inGroups;
    }

    public int getInOutType() {
        return inOutType;
    }

    public void setInOutType(int inOutType) {
        this.inOutType = inOutType;
    }

    public HashMap<String, Escena> getInScene() {
        return inScene;
    }

    public void setInScene(HashMap<String, Escena> inScene) {
        this.inScene = inScene;
    }

    public boolean getInStauts() {
        return inStauts;
    }

    public void setInStauts(boolean inStauts) {
        this.inStauts = inStauts;
    }

    public int getInType() {
        return inType;
    }

    public void setInType(int inType) {
        this.inType = inType;
    }

    public HashMap<String, Entrada> getIns() {
        return ins;
    }

    public void setIns(HashMap<String, Entrada> ins) {
        this.ins = ins;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, Character> getMenuParents() {
        return menuParents;
    }

    public void setMenuParents(Map<String, Character> menuParents) {
        this.menuParents = menuParents;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public HashMap<String, Balasto> getSceneBalasts() {
        return sceneBalasts;
    }

    public void setSceneBalasts(HashMap<String, Balasto> sceneBalasts) {
        this.sceneBalasts = sceneBalasts;
    }

    public int[] getSceneBalastsValues() {
        return sceneBalastsValues;
    }

    public void setSceneBalastsValues(int[] sceneBalastsValues) {
        this.sceneBalastsValues = sceneBalastsValues;
    }

    public boolean getSceneStauts() {
        return sceneStauts;
    }

    public void setSceneStauts(boolean sceneStauts) {
        this.sceneStauts = sceneStauts;
    }

    public HashMap<String, Escena> getScenes() {
        return scenes;
    }

    public void setScenes(HashMap<String, Escena> scenes) {
        this.scenes = scenes;
    }

    public String getSelectedBalastNumber() {
        return selectedBalastNumber;
    }

    public void setSelectedBalastNumber(String selectedBalastNumber) {
        this.selectedBalastNumber = selectedBalastNumber;
    }

    public String getSelectedEventNumber() {
        return selectedEventNumber;
    }

    public void setSelectedEventNumber(String selectedEventNumber) {
        this.selectedEventNumber = selectedEventNumber;
    }

    public String getSelectedGroupNumber() {
        return selectedGroupNumber;
    }

    public void setSelectedGroupNumber(String selectedGroupNumber) {
        this.selectedGroupNumber = selectedGroupNumber;
    }

    public String getSelectedInNumber() {
        return selectedInNumber;
    }

    public void setSelectedInNumber(String selectedInNumber) {
        this.selectedInNumber = selectedInNumber;
    }

    public String getSelectedSceneNumber() {
        return selectedSceneNumber;
    }

    public void setSelectedSceneNumber(String selectedSceneNumber) {
        this.selectedSceneNumber = selectedSceneNumber;
    }

    public boolean getShowAreas() {
        return showAreas;
    }

    public void setShowAreas(boolean showAreas) {
        this.showAreas = showAreas;
    }

    public ButtonGroup getBotoneraComportamiento() {
        return botoneraComportamiento;
    }

    public void setBotoneraComportamiento(ButtonGroup botoneraComportamiento) {
        this.botoneraComportamiento = botoneraComportamiento;
    }

    public ButtonGroup getBotoneraTipoContacto() {
        return botoneraTipoContacto;
    }

    public void setBotoneraTipoContacto(ButtonGroup botoneraTipoContacto) {
        this.botoneraTipoContacto = botoneraTipoContacto;
    }

    public JCheckBox getCbIsStaticConfiguration() {
        return cbIsStaticConfiguration;
    }

    public void setCbIsStaticConfiguration(JCheckBox cbIsStaticConfiguration) {
        this.cbIsStaticConfiguration = cbIsStaticConfiguration;
    }

    public ButtonGroup getConfRol() {
        return confRol;
    }

    public void setConfRol(ButtonGroup confRol) {
        this.confRol = confRol;
    }

    public int getEventOutType() {
        return eventOutType;
    }

    public void setEventOutType(int eventOutType) {
        this.eventOutType = eventOutType;
    }

    public JFormattedTextField getFieldGateway() {
        return fieldGateway;
    }

    public void setFieldGateway(JFormattedTextField fieldGateway) {
        this.fieldGateway = fieldGateway;
    }

    public JFormattedTextField getFieldIp() {
        return fieldIp;
    }

    public void setFieldIp(JFormattedTextField fieldIp) {
        this.fieldIp = fieldIp;
    }

    public JFormattedTextField getFieldMask() {
        return fieldMask;
    }

    public void setFieldMask(JFormattedTextField fieldMask) {
        this.fieldMask = fieldMask;
    }

    public JFormattedTextField getFieldPort() {
        return fieldPort;
    }

    public void setFieldPort(JFormattedTextField fieldPort) {
        this.fieldPort = fieldPort;
    }

    public JTextField getFieldPuerto() {
        return fieldPuerto;
    }

    public void setFieldPuerto(JTextField fieldPuerto) {
        this.fieldPuerto = fieldPuerto;
    }

    public JPanel getHeader() {
        return header;
    }

    public void setHeader(JPanel header) {
        this.header = header;
    }

    public JLabel getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(JLabel headerImage) {
        this.headerImage = headerImage;
    }

    public static PpalView getIndex() {
        return index;
    }

    public static void setIndex(PpalView index) {
        PpalView.index = index;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    public void setjButton1(JButton jButton1) {
        this.jButton1 = jButton1;
    }

    public JButton getjButton10() {
        return jButton10;
    }

    public void setjButton10(JButton jButton10) {
        this.jButton10 = jButton10;
    }

    public JButton getjButton11() {
        return jButton11;
    }

    public void setjButton11(JButton jButton11) {
        this.jButton11 = jButton11;
    }

    public JButton getjButton12() {
        return jButton12;
    }

    public void setjButton12(JButton jButton12) {
        this.jButton12 = jButton12;
    }

    public JButton getjButton13() {
        return jButton13;
    }

    public void setjButton13(JButton jButton13) {
        this.jButton13 = jButton13;
    }

    public JButton getjButton14() {
        return jButton14;
    }

    public void setjButton14(JButton jButton14) {
        this.jButton14 = jButton14;
    }

    public JButton getjButton15() {
        return jButton15;
    }

    public void setjButton15(JButton jButton15) {
        this.jButton15 = jButton15;
    }

    public JButton getjButton16() {
        return jButton16;
    }

    public void setjButton16(JButton jButton16) {
        this.jButton16 = jButton16;
    }

    public JButton getjButton17() {
        return jButton17;
    }

    public void setjButton17(JButton jButton17) {
        this.jButton17 = jButton17;
    }

    public JButton getjButton18() {
        return jButton18;
    }

    public void setjButton18(JButton jButton18) {
        this.jButton18 = jButton18;
    }

    public JButton getjButton19() {
        return jButton19;
    }

    public void setjButton19(JButton jButton19) {
        this.jButton19 = jButton19;
    }

    public JButton getjButton2() {
        return jButton2;
    }

    public void setjButton2(JButton jButton2) {
        this.jButton2 = jButton2;
    }

    public JButton getjButton20() {
        return jButton20;
    }

    public void setjButton20(JButton jButton20) {
        this.jButton20 = jButton20;
    }

    public JButton getjButton21() {
        return jButton21;
    }

    public void setjButton21(JButton jButton21) {
        this.jButton21 = jButton21;
    }

    public JButton getjButton22() {
        return jButton22;
    }

    public void setjButton22(JButton jButton22) {
        this.jButton22 = jButton22;
    }

    public JButton getjButton23() {
        return jButton23;
    }

    public void setjButton23(JButton jButton23) {
        this.jButton23 = jButton23;
    }

    public JButton getjButton26() {
        return jButton26;
    }

    public void setjButton26(JButton jButton26) {
        this.jButton26 = jButton26;
    }

    public JButton getjButton27() {
        return jButton27;
    }

    public void setjButton27(JButton jButton27) {
        this.jButton27 = jButton27;
    }

    public JButton getjButton28() {
        return jButton28;
    }

    public void setjButton28(JButton jButton28) {
        this.jButton28 = jButton28;
    }

    public JButton getjButton29() {
        return jButton29;
    }

    public void setjButton29(JButton jButton29) {
        this.jButton29 = jButton29;
    }

    public JButton getjButton3() {
        return jButton3;
    }

    public void setjButton3(JButton jButton3) {
        this.jButton3 = jButton3;
    }

    public JButton getjButton30() {
        return jButton30;
    }

    public void setjButton30(JButton jButton30) {
        this.jButton30 = jButton30;
    }

    public JButton getjButton31() {
        return jButton31;
    }

    public void setjButton31(JButton jButton31) {
        this.jButton31 = jButton31;
    }

    public JButton getjButton32() {
        return jButton32;
    }

    public void setjButton32(JButton jButton32) {
        this.jButton32 = jButton32;
    }

    public JButton getjButton33() {
        return jButton33;
    }

    public void setjButton33(JButton jButton33) {
        this.jButton33 = jButton33;
    }

    public JButton getjButton34() {
        return jButton34;
    }

    public void setjButton34(JButton jButton34) {
        this.jButton34 = jButton34;
    }

    public JButton getjButton35() {
        return jButton35;
    }

    public void setjButton35(JButton jButton35) {
        this.jButton35 = jButton35;
    }

    public JButton getjButton36() {
        return jButton36;
    }

    public void setjButton36(JButton jButton36) {
        this.jButton36 = jButton36;
    }

    public JButton getjButton37() {
        return jButton37;
    }

    public void setjButton37(JButton jButton37) {
        this.jButton37 = jButton37;
    }

    public JButton getjButton38() {
        return jButton38;
    }

    public void setjButton38(JButton jButton38) {
        this.jButton38 = jButton38;
    }

    public JButton getjButton39() {
        return jButton39;
    }

    public void setjButton39(JButton jButton39) {
        this.jButton39 = jButton39;
    }

    public JButton getjButton4() {
        return jButton4;
    }

    public void setjButton4(JButton jButton4) {
        this.jButton4 = jButton4;
    }

    public JButton getjButton40() {
        return jButton40;
    }

    public void setjButton40(JButton jButton40) {
        this.jButton40 = jButton40;
    }

    public JButton getjButton41() {
        return jButton41;
    }

    public void setjButton41(JButton jButton41) {
        this.jButton41 = jButton41;
    }

    public JButton getjButton42() {
        return jButton42;
    }

    public void setjButton42(JButton jButton42) {
        this.jButton42 = jButton42;
    }

    public JButton getjButton43() {
        return jButton43;
    }

    public void setjButton43(JButton jButton43) {
        this.jButton43 = jButton43;
    }

    public JButton getjButton44() {
        return jButton44;
    }

    public void setjButton44(JButton jButton44) {
        this.jButton44 = jButton44;
    }

    public JButton getjButton46() {
        return jButton46;
    }

    public void setjButton46(JButton jButton46) {
        this.jButton46 = jButton46;
    }

    public JButton getjButton47() {
        return jButton47;
    }

    public void setjButton47(JButton jButton47) {
        this.jButton47 = jButton47;
    }

    public JButton getjButton48() {
        return jButton48;
    }

    public void setjButton48(JButton jButton48) {
        this.jButton48 = jButton48;
    }

    public JButton getjButton5() {
        return jButton5;
    }

    public void setjButton5(JButton jButton5) {
        this.jButton5 = jButton5;
    }

    public JButton getjButton6() {
        return jButton6;
    }

    public void setjButton6(JButton jButton6) {
        this.jButton6 = jButton6;
    }

    public JButton getjButton7() {
        return jButton7;
    }

    public void setjButton7(JButton jButton7) {
        this.jButton7 = jButton7;
    }

    public JButton getjButton8() {
        return jButton8;
    }

    public void setjButton8(JButton jButton8) {
        this.jButton8 = jButton8;
    }

    public JButton getjButton9() {
        return jButton9;
    }

    public void setjButton9(JButton jButton9) {
        this.jButton9 = jButton9;
    }

    public JCheckBox getjCheckBox1() {
        return jCheckBox1;
    }

    public void setjCheckBox1(JCheckBox jCheckBox1) {
        this.jCheckBox1 = jCheckBox1;
    }

    public JCheckBox getjCheckBox10() {
        return jCheckBox10;
    }

    public void setjCheckBox10(JCheckBox jCheckBox10) {
        this.jCheckBox10 = jCheckBox10;
    }

    public JCheckBox getjCheckBox11() {
        return jCheckBox11;
    }

    public void setjCheckBox11(JCheckBox jCheckBox11) {
        this.jCheckBox11 = jCheckBox11;
    }

    public JCheckBox getjCheckBox12() {
        return jCheckBox12;
    }

    public void setjCheckBox12(JCheckBox jCheckBox12) {
        this.jCheckBox12 = jCheckBox12;
    }

    public JCheckBox getjCheckBox13() {
        return jCheckBox13;
    }

    public void setjCheckBox13(JCheckBox jCheckBox13) {
        this.jCheckBox13 = jCheckBox13;
    }

    public JCheckBox getjCheckBox14() {
        return jCheckBox14;
    }

    public void setjCheckBox14(JCheckBox jCheckBox14) {
        this.jCheckBox14 = jCheckBox14;
    }

    public JCheckBox getjCheckBox15() {
        return jCheckBox15;
    }

    public void setjCheckBox15(JCheckBox jCheckBox15) {
        this.jCheckBox15 = jCheckBox15;
    }

    public JCheckBox getjCheckBox16() {
        return balastros_jCheckBox;
    }

    public void setjCheckBox16(JCheckBox jCheckBox16) {
        this.balastros_jCheckBox = jCheckBox16;
    }

    public JCheckBox getjCheckBox17() {
        return grupos_jCheckBox;
    }

    public void setjCheckBox17(JCheckBox jCheckBox17) {
        this.grupos_jCheckBox = jCheckBox17;
    }

    public JCheckBox getjCheckBox18() {
        return jCheckBox18;
    }

    public void setjCheckBox18(JCheckBox jCheckBox18) {
        this.jCheckBox18 = jCheckBox18;
    }

    public JCheckBox getjCheckBox19() {
        return jCheckBox19;
    }

    public void setjCheckBox19(JCheckBox jCheckBox19) {
        this.jCheckBox19 = jCheckBox19;
    }

    public JCheckBox getjCheckBox2() {
        return jCheckBox2;
    }

    public void setjCheckBox2(JCheckBox jCheckBox2) {
        this.jCheckBox2 = jCheckBox2;
    }

    public JCheckBox getjCheckBox20() {
        return jCheckBox20;
    }

    public void setjCheckBox20(JCheckBox jCheckBox20) {
        this.jCheckBox20 = jCheckBox20;
    }

    public JCheckBox getjCheckBox3() {
        return jCheckBox3;
    }

    public void setjCheckBox3(JCheckBox jCheckBox3) {
        this.jCheckBox3 = jCheckBox3;
    }

    public JCheckBox getjCheckBox4() {
        return jCheckBox4;
    }

    public void setjCheckBox4(JCheckBox jCheckBox4) {
        this.jCheckBox4 = jCheckBox4;
    }

    public JCheckBox getjCheckBox5() {
        return jCheckBox5;
    }

    public void setjCheckBox5(JCheckBox jCheckBox5) {
        this.jCheckBox5 = jCheckBox5;
    }

    public JCheckBox getjCheckBox6() {
        return jCheckBox6;
    }

    public void setjCheckBox6(JCheckBox jCheckBox6) {
        this.jCheckBox6 = jCheckBox6;
    }

    public JCheckBox getjCheckBox7() {
        return jCheckBox7;
    }

    public void setjCheckBox7(JCheckBox jCheckBox7) {
        this.jCheckBox7 = jCheckBox7;
    }

    public JCheckBox getjCheckBox8() {
        return jCheckBox8;
    }

    public void setjCheckBox8(JCheckBox jCheckBox8) {
        this.jCheckBox8 = jCheckBox8;
    }

    public JCheckBox getjCheckBox9() {
        return jCheckBox9;
    }

    public void setjCheckBox9(JCheckBox jCheckBox9) {
        this.jCheckBox9 = jCheckBox9;
    }

    public JComboBox getjComboBox2() {
        return jComboBox2;
    }

    public void setjComboBox2(JComboBox jComboBox2) {
        this.jComboBox2 = jComboBox2;
    }

    public JComboBox getjComboBox3() {
        return jComboBox3;
    }

    public void setjComboBox3(JComboBox jComboBox3) {
        this.jComboBox3 = jComboBox3;
    }

    public JComboBox getjComboBox4() {
        return jComboBox4;
    }

    public void setjComboBox4(JComboBox jComboBox4) {
        this.jComboBox4 = jComboBox4;
    }

    public JDateChooser getjDateChooser1() {
        return jDateChooser1;
    }

    public void setjDateChooser1(JDateChooser jDateChooser1) {
        this.jDateChooser1 = jDateChooser1;
    }

    public JDateChooser getjDateChooser2() {
        return jDateChooser2;
    }

    public void setjDateChooser2(JDateChooser jDateChooser2) {
        this.jDateChooser2 = jDateChooser2;
    }

    public JFormattedTextField getjFormattedTextField1() {
        return jFormattedTextField1;
    }

    public void setjFormattedTextField1(JFormattedTextField jFormattedTextField1) {
        this.jFormattedTextField1 = jFormattedTextField1;
    }

    public JFormattedTextField getjFormattedTextField2() {
        return jFormattedTextField2;
    }

    public void setjFormattedTextField2(JFormattedTextField jFormattedTextField2) {
        this.jFormattedTextField2 = jFormattedTextField2;
    }

    public JFormattedTextField getjFormattedTextField3() {
        return jFormattedTextField3;
    }

    public void setjFormattedTextField3(JFormattedTextField jFormattedTextField3) {
        this.jFormattedTextField3 = jFormattedTextField3;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel10() {
        return jLabel10;
    }

    public void setjLabel10(JLabel jLabel10) {
        this.jLabel10 = jLabel10;
    }

    public JLabel getjLabel11() {
        return jLabel11;
    }

    public void setjLabel11(JLabel jLabel11) {
        this.jLabel11 = jLabel11;
    }

    public JLabel getjLabel12() {
        return jLabel12;
    }

    public void setjLabel12(JLabel jLabel12) {
        this.jLabel12 = jLabel12;
    }

    public JLabel getjLabel13() {
        return jLabel13;
    }

    public void setjLabel13(JLabel jLabel13) {
        this.jLabel13 = jLabel13;
    }

    public JLabel getjLabel14() {
        return jLabel14;
    }

    public void setjLabel14(JLabel jLabel14) {
        this.jLabel14 = jLabel14;
    }

    public JLabel getjLabel15() {
        return jLabel15;
    }

    public void setjLabel15(JLabel jLabel15) {
        this.jLabel15 = jLabel15;
    }

    public JLabel getjLabel16() {
        return jLabel16;
    }

    public void setjLabel16(JLabel jLabel16) {
        this.jLabel16 = jLabel16;
    }

    public JLabel getjLabel17() {
        return jLabel17;
    }

    public void setjLabel17(JLabel jLabel17) {
        this.jLabel17 = jLabel17;
    }

    public JLabel getjLabel18() {
        return jLabel18;
    }

    public void setjLabel18(JLabel jLabel18) {
        this.jLabel18 = jLabel18;
    }

    public JLabel getjLabel19() {
        return jLabel19;
    }

    public void setjLabel19(JLabel jLabel19) {
        this.jLabel19 = jLabel19;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public JLabel getjLabel20() {
        return jLabel20;
    }

    public void setjLabel20(JLabel jLabel20) {
        this.jLabel20 = jLabel20;
    }

    public JLabel getjLabel21() {
        return jLabel21;
    }

    public void setjLabel21(JLabel jLabel21) {
        this.jLabel21 = jLabel21;
    }

    public JLabel getjLabel22() {
        return jLabel22;
    }

    public void setjLabel22(JLabel jLabel22) {
        this.jLabel22 = jLabel22;
    }

    public JLabel getjLabel23() {
        return jLabel23;
    }

    public void setjLabel23(JLabel jLabel23) {
        this.jLabel23 = jLabel23;
    }

    public JLabel getjLabel24() {
        return jLabel24;
    }

    public void setjLabel24(JLabel jLabel24) {
        this.jLabel24 = jLabel24;
    }

    public JLabel getjLabel25() {
        return jLabel25;
    }

    public void setjLabel25(JLabel jLabel25) {
        this.jLabel25 = jLabel25;
    }

    public JLabel getjLabel26() {
        return jLabel26;
    }

    public void setjLabel26(JLabel jLabel26) {
        this.jLabel26 = jLabel26;
    }

    public JLabel getjLabel27() {
        return jLabel27;
    }

    public void setjLabel27(JLabel jLabel27) {
        this.jLabel27 = jLabel27;
    }

    public JLabel getjLabel28() {
        return jLabel28;
    }

    public void setjLabel28(JLabel jLabel28) {
        this.jLabel28 = jLabel28;
    }

    public JLabel getjLabel29() {
        return jLabel29;
    }

    public void setjLabel29(JLabel jLabel29) {
        this.jLabel29 = jLabel29;
    }

    public JLabel getjLabel3() {
        return jLabel3;
    }

    public void setjLabel3(JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    public JLabel getjLabel30() {
        return jLabel30;
    }

    public void setjLabel30(JLabel jLabel30) {
        this.jLabel30 = jLabel30;
    }

    public JLabel getjLabel31() {
        return jLabel31;
    }

    public void setjLabel31(JLabel jLabel31) {
        this.jLabel31 = jLabel31;
    }

    public JLabel getjLabel32() {
        return jLabel32;
    }

    public void setjLabel32(JLabel jLabel32) {
        this.jLabel32 = jLabel32;
    }

    public JLabel getjLabel33() {
        return jLabel33;
    }

    public void setjLabel33(JLabel jLabel33) {
        this.jLabel33 = jLabel33;
    }

    public JLabel getjLabel34() {
        return jLabel34;
    }

    public void setjLabel34(JLabel jLabel34) {
        this.jLabel34 = jLabel34;
    }

    public JLabel getjLabel37() {
        return jLabel37;
    }

    public void setjLabel37(JLabel jLabel37) {
        this.jLabel37 = jLabel37;
    }
    
    public JLabel getjLabel39() {
        return jLabel39;
    }

    public void setjLabel39(JLabel jLabel39) {
        this.jLabel39 = jLabel39;
    }

    public JLabel getjLabel4() {
        return jLabel4;
    }

    public void setjLabel4(JLabel jLabel4) {
        this.jLabel4 = jLabel4;
    }

    public JLabel getjLabel40() {
        return jLabel40;
    }

    public void setjLabel40(JLabel jLabel40) {
        this.jLabel40 = jLabel40;
    }

    public JLabel getjLabel41() {
        return jLabel41;
    }

    public void setjLabel41(JLabel jLabel41) {
        this.jLabel41 = jLabel41;
    }

    public JLabel getjLabel44() {
        return jLabel44;
    }

    public void setjLabel44(JLabel jLabel44) {
        this.jLabel44 = jLabel44;
    }

    public JLabel getjLabel45() {
        return jLabel45;
    }

    public void setjLabel45(JLabel jLabel45) {
        this.jLabel45 = jLabel45;
    }

    public JLabel getjLabel5() {
        return jLabel5;
    }

    public void setjLabel5(JLabel jLabel5) {
        this.jLabel5 = jLabel5;
    }

    public JLabel getjLabel53() {
        return jLabel53;
    }

    public void setjLabel53(JLabel jLabel53) {
        this.jLabel53 = jLabel53;
    }

    public JLabel getjLabel54() {
        return jLabel54;
    }

    public void setjLabel54(JLabel jLabel54) {
        this.jLabel54 = jLabel54;
    }

    public JLabel getjLabel55() {
        return jLabel55;
    }

    public void setjLabel55(JLabel jLabel55) {
        this.jLabel55 = jLabel55;
    }

    public JLabel getjLabel56() {
        return jLabel56;
    }

    public void setjLabel56(JLabel jLabel56) {
        this.jLabel56 = jLabel56;
    }

    public JLabel getjLabel57() {
        return jLabel57;
    }

    public void setjLabel57(JLabel jLabel57) {
        this.jLabel57 = jLabel57;
    }

    public JLabel getjLabel58() {
        return jLabel58;
    }

    public void setjLabel58(JLabel jLabel58) {
        this.jLabel58 = jLabel58;
    }

    public JLabel getjLabel6() {
        return jLabel6;
    }

    public void setjLabel6(JLabel jLabel6) {
        this.jLabel6 = jLabel6;
    }

    public JLabel getjLabel61() {
        return jLabel61;
    }

    public void setjLabel61(JLabel jLabel61) {
        this.jLabel61 = jLabel61;
    }

    public JLabel getjLabel62() {
        return jLabel62;
    }

    public void setjLabel62(JLabel jLabel62) {
        this.jLabel62 = jLabel62;
    }

    public JLabel getjLabel63() {
        return jLabel63;
    }

    public void setjLabel63(JLabel jLabel63) {
        this.jLabel63 = jLabel63;
    }

    public JLabel getjLabel64() {
        return jLabel64;
    }

    public void setjLabel64(JLabel jLabel64) {
        this.jLabel64 = jLabel64;
    }

    public JLabel getjLabel65() {
        return jLabel65;
    }

    public void setjLabel65(JLabel jLabel65) {
        this.jLabel65 = jLabel65;
    }

    public JLabel getjLabel66() {
        return jLabel66;
    }

    public void setjLabel66(JLabel jLabel66) {
        this.jLabel66 = jLabel66;
    }

    public JLabel getjLabel67() {
        return jLabel67;
    }

    public void setjLabel67(JLabel jLabel67) {
        this.jLabel67 = jLabel67;
    }

    public JLabel getjLabel68() {
        return jLabel68;
    }

    public void setjLabel68(JLabel jLabel68) {
        this.jLabel68 = jLabel68;
    }

    public JLabel getjLabel7() {
        return jLabel7;
    }

    public void setjLabel7(JLabel jLabel7) {
        this.jLabel7 = jLabel7;
    }

    public JLabel getjLabel8() {
        return jLabel8;
    }

    public void setjLabel8(JLabel jLabel8) {
        this.jLabel8 = jLabel8;
    }

    public JLabel getjLabel9() {
        return jLabel9;
    }

    public void setjLabel9(JLabel jLabel9) {
        this.jLabel9 = jLabel9;
    }

    public JList getjList12() {
        return jList12;
    }

    public void setjList12(JList jList12) {
        this.jList12 = jList12;
    }

    public JList getjList13() {
        return jList13;
    }

    public void setjList13(JList jList13) {
        this.jList13 = jList13;
    }

    public JList getjList14() {
        return jList14;
    }

    public void setjList14(JList jList14) {
        this.jList14 = jList14;
    }

    public JList getjList15() {
        return jList15;
    }

    public void setjList15(JList jList15) {
        this.jList15 = jList15;
    }

    public JList getjList16() {
        return jList16;
    }

    public void setjList16(JList jList16) {
        this.jList16 = jList16;
    }

    public JList getjList17() {
        return jList17;
    }

    public void setjList17(JList jList17) {
        this.jList17 = jList17;
    }

    public JList getjList2() {
        return jList2;
    }

    public void setjList2(JList jList2) {
        this.jList2 = jList2;
    }

    public JList getjList3() {
        return jList3;
    }

    public void setjList3(JList jList3) {
        this.jList3 = jList3;
    }

    public JList getjList4() {
        return jList4;
    }

    public void setjList4(JList jList4) {
        this.jList4 = jList4;
    }

    public JList getjList5() {
        return jList5;
    }

    public void setjList5(JList jList5) {
        this.jList5 = jList5;
    }

    public JList getjList8() {
        return jList8;
    }

    public void setjList8(JList jList8) {
        this.jList8 = jList8;
    }

    public JList getjList9() {
        return jList9;
    }

    public void setjList9(JList jList9) {
        this.jList9 = jList9;
    }

    public JMenu getjMenu1() {
        return jMenu1;
    }

    public void setjMenu1(JMenu jMenu1) {
        this.jMenu1 = jMenu1;
    }

    public JMenuItem getjMenuItem1() {
        return jMenuItem1;
    }

    public void setjMenuItem1(JMenuItem jMenuItem1) {
        this.jMenuItem1 = jMenuItem1;
    }

    public JMenuItem getjMenuItem5() {
        return jMenuItem5;
    }

    public void setjMenuItem5(JMenuItem jMenuItem5) {
        this.jMenuItem5 = jMenuItem5;
    }

    public JMenuItem getjMenuItem6() {
        return jMenuItem6;
    }

    public void setjMenuItem6(JMenuItem jMenuItem6) {
        this.jMenuItem6 = jMenuItem6;
    }

    public JPanel getjPanel10() {
        return jPanel10;
    }

    public void setjPanel10(JPanel jPanel10) {
        this.jPanel10 = jPanel10;
    }

    public JPanel getjPanel11() {
        return jPanel11;
    }

    public void setjPanel11(JPanel jPanel11) {
        this.jPanel11 = jPanel11;
    }

    public JPanel getjPanel12() {
        return jPanel12;
    }

    public void setjPanel12(JPanel jPanel12) {
        this.jPanel12 = jPanel12;
    }

    public JPanel getjPanel13() {
        return jPanel13;
    }

    public void setjPanel13(JPanel jPanel13) {
        this.jPanel13 = jPanel13;
    }

    public JPanel getjPanel2() {
        return jPanel2;
    }

    public void setjPanel2(JPanel jPanel2) {
        this.jPanel2 = jPanel2;
    }

    public JPanel getjPanel3() {
        return jPanel3;
    }

    public void setjPanel3(JPanel jPanel3) {
        this.jPanel3 = jPanel3;
    }

    public JPanel getjPanel4() {
        return jPanel4;
    }

    public void setjPanel4(JPanel jPanel4) {
        this.jPanel4 = jPanel4;
    }

    public JPanel getjPanel5() {
        return jPanel5;
    }

    public void setjPanel5(JPanel jPanel5) {
        this.jPanel5 = jPanel5;
    }

    public JPanel getjPanel6() {
        return jPanel6;
    }

    public void setjPanel6(JPanel jPanel6) {
        this.jPanel6 = jPanel6;
    }

    public JPanel getjPanel7() {
        return jPanel7;
    }

    public void setjPanel7(JPanel jPanel7) {
        this.jPanel7 = jPanel7;
    }

    public JPanel getjPanel8() {
        return jPanel8;
    }

    public void setjPanel8(JPanel jPanel8) {
        this.jPanel8 = jPanel8;
    }

    public JPanel getjPanel9() {
        return jPanel9;
    }

    public void setjPanel9(JPanel jPanel9) {
        this.jPanel9 = jPanel9;
    }

    public JRadioButton getjRadioButton1() {
        return jRadioButton1;
    }

    public void setjRadioButton1(JRadioButton jRadioButton1) {
        this.jRadioButton1 = jRadioButton1;
    }

    public JRadioButton getjRadioButton2() {
        return jRadioButton2;
    }

    public void setjRadioButton2(JRadioButton jRadioButton2) {
        this.jRadioButton2 = jRadioButton2;
    }

    public JRadioButton getjRadioButton3() {
        return jRadioButton3;
    }

    public void setjRadioButton3(JRadioButton jRadioButton3) {
        this.jRadioButton3 = jRadioButton3;
    }

    public JRadioButton getjRadioButton4() {
        return jRadioButton4;
    }

    public void setjRadioButton4(JRadioButton jRadioButton4) {
        this.jRadioButton4 = jRadioButton4;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JScrollPane getjScrollPane10() {
        return jScrollPane10;
    }

    public void setjScrollPane10(JScrollPane jScrollPane10) {
        this.jScrollPane10 = jScrollPane10;
    }

    public JScrollPane getjScrollPane13() {
        return jScrollPane13;
    }

    public void setjScrollPane13(JScrollPane jScrollPane13) {
        this.jScrollPane13 = jScrollPane13;
    }

    public JScrollPane getjScrollPane14() {
        return jScrollPane14;
    }

    public void setjScrollPane14(JScrollPane jScrollPane14) {
        this.jScrollPane14 = jScrollPane14;
    }

    public JScrollPane getjScrollPane15() {
        return jScrollPane15;
    }

    public void setjScrollPane15(JScrollPane jScrollPane15) {
        this.jScrollPane15 = jScrollPane15;
    }

    public JScrollPane getjScrollPane16() {
        return jScrollPane16;
    }

    public void setjScrollPane16(JScrollPane jScrollPane16) {
        this.jScrollPane16 = jScrollPane16;
    }

    public JScrollPane getjScrollPane17() {
        return jScrollPane17;
    }

    public void setjScrollPane17(JScrollPane jScrollPane17) {
        this.jScrollPane17 = jScrollPane17;
    }

    public JScrollPane getjScrollPane18() {
        return jScrollPane18;
    }

    public void setjScrollPane18(JScrollPane jScrollPane18) {
        this.jScrollPane18 = jScrollPane18;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public JScrollPane getjScrollPane3() {
        return jScrollPane3;
    }

    public void setjScrollPane3(JScrollPane jScrollPane3) {
        this.jScrollPane3 = jScrollPane3;
    }

    public JScrollPane getjScrollPane4() {
        return jScrollPane4;
    }

    public void setjScrollPane4(JScrollPane jScrollPane4) {
        this.jScrollPane4 = jScrollPane4;
    }

    public JScrollPane getjScrollPane5() {
        return jScrollPane5;
    }

    public void setjScrollPane5(JScrollPane jScrollPane5) {
        this.jScrollPane5 = jScrollPane5;
    }

    public JScrollPane getjScrollPane6() {
        return jScrollPane6;
    }

    public void setjScrollPane6(JScrollPane jScrollPane6) {
        this.jScrollPane6 = jScrollPane6;
    }

    public JScrollPane getjScrollPane9() {
        return jScrollPane9;
    }

    public void setjScrollPane9(JScrollPane jScrollPane9) {
        this.jScrollPane9 = jScrollPane9;
    }

    public JSeparator getjSeparator1() {
        return jSeparator1;
    }

    public void setjSeparator1(JSeparator jSeparator1) {
        this.jSeparator1 = jSeparator1;
    }

    public JSeparator getjSeparator2() {
        return jSeparator2;
    }

    public void setjSeparator2(JSeparator jSeparator2) {
        this.jSeparator2 = jSeparator2;
    }

    public Separator getjSeparator3() {
        return jSeparator3;
    }

    public void setjSeparator3(Separator jSeparator3) {
        this.jSeparator3 = jSeparator3;
    }

    public JSlider getjSlider1() {
        return jSlider1;
    }

    public void setjSlider1(JSlider jSlider1) {
        this.jSlider1 = jSlider1;
    }

    public JSpinner getjSpinner3() {
        return jSpinner3;
    }

    public void setjSpinner3(JSpinner jSpinner3) {
        this.jSpinner3 = jSpinner3;
    }

    public JTable getjTable1() {
        return jTable1;
    }

    public void setjTable1(JTable jTable1) {
        this.jTable1 = jTable1;
    }

    public JTextField getjTextField1() {
        return jTextField1;
    }

    public void setjTextField1(JTextField jTextField1) {
        this.jTextField1 = jTextField1;
    }

    public JTextField getjTextField10() {
        return jTextField10;
    }

    public void setjTextField10(JTextField jTextField10) {
        this.jTextField10 = jTextField10;
    }

    public JTextField getjTextField11() {
        return jTextField11;
    }

    public void setjTextField11(JTextField jTextField11) {
        this.jTextField11 = jTextField11;
    }

    public JTextField getjTextField2() {
        return jTextField2;
    }

    public void setjTextField2(JTextField jTextField2) {
        this.jTextField2 = jTextField2;
    }

    public JTextField getjTextField20() {
        return jTextField20;
    }

    public void setjTextField20(JTextField jTextField20) {
        this.jTextField20 = jTextField20;
    }

    public JTextField getjTextField23() {
        return jTextField23;
    }

    public void setjTextField23(JTextField jTextField23) {
        this.jTextField23 = jTextField23;
    }

    public JTextField getjTextField25() {
        return jTextField25;
    }

    public void setjTextField25(JTextField jTextField25) {
        this.jTextField25 = jTextField25;
    }

    public JTextField getjTextField26() {
        return jTextField26;
    }

    public void setjTextField26(JTextField jTextField26) {
        this.jTextField26 = jTextField26;
    }

    public JTextField getjTextField27() {
        return jTextField27;
    }

    public void setjTextField27(JTextField jTextField27) {
        this.jTextField27 = jTextField27;
    }

    public JTextField getjTextField28() {
        return jTextField28;
    }

    public void setjTextField28(JTextField jTextField28) {
        this.jTextField28 = jTextField28;
    }

    public JTextField getjTextField29() {
        return jTextField29;
    }

    public void setjTextField29(JTextField jTextField29) {
        this.jTextField29 = jTextField29;
    }

    public JTextField getjTextField3() {
        return jTextField3;
    }

    public void setjTextField3(JTextField jTextField3) {
        this.jTextField3 = jTextField3;
    }

    public JTextField getjTextField30() {
        return jTextField30;
    }

    public void setjTextField30(JTextField jTextField30) {
        this.jTextField30 = jTextField30;
    }

    public JTextField getjTextField31() {
        return jTextField31;
    }

    public void setjTextField31(JTextField jTextField31) {
        this.jTextField31 = jTextField31;
    }

    public JTextField getjTextField32() {
        return jTextField32;
    }

    public void setjTextField32(JTextField jTextField32) {
        this.jTextField32 = jTextField32;
    }

    public JTextField getjTextField33() {
        return jTextField33;
    }

    public void setjTextField33(JTextField jTextField33) {
        this.jTextField33 = jTextField33;
    }

    public JTextField getjTextField34() {
        return jTextField34;
    }

    public void setjTextField34(JTextField jTextField34) {
        this.jTextField34 = jTextField34;
    }

    public JTextField getjTextField35() {
        return jTextField35;
    }

    public void setjTextField35(JTextField jTextField35) {
        this.jTextField35 = jTextField35;
    }

    public JTextField getjTextField36() {
        return jTextField36;
    }

    public void setjTextField36(JTextField jTextField36) {
        this.jTextField36 = jTextField36;
    }

    public JTextField getjTextField4() {
        return jTextField4;
    }

    public void setjTextField4(JTextField jTextField4) {
        this.jTextField4 = jTextField4;
    }

    public JTextField getjTextField5() {
        return jTextField5;
    }

    public void setjTextField5(JTextField jTextField5) {
        this.jTextField5 = jTextField5;
    }

    public JTextField getjTextField6() {
        return jTextField6;
    }

    public void setjTextField6(JTextField jTextField6) {
        this.jTextField6 = jTextField6;
    }

    public JTextField getjTextField7() {
        return jTextField7;
    }

    public void setjTextField7(JTextField jTextField7) {
        this.jTextField7 = jTextField7;
    }

    public JTextField getjTextField8() {
        return jTextField8;
    }

    public void setjTextField8(JTextField jTextField8) {
        this.jTextField8 = jTextField8;
    }

    public JTextField getjTextField9() {
        return jTextField9;
    }

    public void setjTextField9(JTextField jTextField9) {
        this.jTextField9 = jTextField9;
    }

    public JTree getjTree1() {
        return jTree1;
    }

    public void setjTree1(JTree jTree1) {
        this.jTree1 = jTree1;
    }

    public JLabel getLabelDns() {
        return labelDns;
    }

    public void setLabelDns(JLabel labelDns) {
        this.labelDns = labelDns;
    }

    public JLabel getLabelGateway() {
        return labelGateway;
    }

    public void setLabelGateway(JLabel labelGateway) {
        this.labelGateway = labelGateway;
    }

    public JLabel getLabelIp() {
        return labelIp;
    }

    public void setLabelIp(JLabel labelIp) {
        this.labelIp = labelIp;
    }

    public JLabel getLabelMascara() {
        return labelMascara;
    }

    public void setLabelMascara(JLabel labelMascara) {
        this.labelMascara = labelMascara;
    }

    public JLabel getLabelPuerto() {
        return labelPuerto;
    }

    public void setLabelPuerto(JLabel labelPuerto) {
        this.labelPuerto = labelPuerto;
    }

    public JMenuBar getMainMenuBar() {
        return mainMenuBar;
    }

    public void setMainMenuBar(JMenuBar menuBar) {
        this.mainMenuBar = menuBar;
    }

    public JPanel getPanelBalastos() {
        return panelBalastos;
    }

    public void setPanelBalastos(JPanel panelBalastos) {
        this.panelBalastos = panelBalastos;
    }

    public JPanel getPanelBienvenida() {
        return panelBienvenida;
    }

    public void setPanelBienvenida(JPanel panelBienvenida) {
        this.panelBienvenida = panelBienvenida;
    }

    public JPanel getPanelBotonera() {
        return panelBotonera;
    }

    public void setPanelBotonera(JPanel panelBotonera) {
        this.panelBotonera = panelBotonera;
    }

    public JPanel getPanelConfDali() {
        return panelConfDali;
    }

    public void setPanelConfDali(JPanel panelConfDali) {
        this.panelConfDali = panelConfDali;
    }

    public JPanel getPanelConfEntradas() {
        return panelConfEntradas;
    }

    public void setPanelConfEntradas(JPanel panelConfEntradas) {
        this.panelConfEntradas = panelConfEntradas;
    }

    public JPanel getPanelConfiguracion() {
        return panelConfiguracion;
    }

    public void setPanelConfiguracion(JPanel panelConfiguracion) {
        this.panelConfiguracion = panelConfiguracion;
    }

    public JPanel getPanelEntradaInit() {
        return panelEntradaInit;
    }

    public void setPanelEntradaInit(JPanel panelEntradaInit) {
        this.panelEntradaInit = panelEntradaInit;
    }

    public JPanel getPanelEntradas() {
        return panelEntradas;
    }

    public void setPanelEntradas(JPanel panelEntradas) {
        this.panelEntradas = panelEntradas;
    }

    public JPanel getPanelEscenas() {
        return panelEscenas;
    }

    public void setPanelEscenas(JPanel panelEscenas) {
        this.panelEscenas = panelEscenas;
    }

    public JPanel getPanelEventos() {
        return panelEventos;
    }

    public void setPanelEventos(JPanel panelEventos) {
        this.panelEventos = panelEventos;
    }

    public JPanel getPanelFotocelda() {
        return panelFotocelda;
    }

    public void setPanelFotocelda(JPanel panelFotocelda) {
        this.panelFotocelda = panelFotocelda;
    }

    public JPanel getPanelGrupos() {
        return panelGrupos;
    }

    public void setPanelGrupos(JPanel panelGrupos) {
        this.panelGrupos = panelGrupos;
    }

    public JPanel getPanelPpal() {
        return panelPpal;
    }

    public void setPanelPpal(JPanel panelPpal) {
        this.panelPpal = panelPpal;
    }

    public JPanel getPanelSensor() {
        return panelSensor;
    }

    public void setPanelSensor(JPanel panelSensor) {
        this.panelSensor = panelSensor;
    }

    public JPanel getPanelTiempoReal() {
        return panelTiempoReal;
    }

    public void setPanelTiempoReal(JPanel panelTiempoReal) {
        this.panelTiempoReal = panelTiempoReal;
    }

    public JRadioButton getRbIsMaster() {
        return rbIsMaster;
    }

    public void setRbIsMaster(JRadioButton rbIsMaster) {
        this.rbIsMaster = rbIsMaster;
    }

    public JRadioButton getRbIsSlave() {
        return rbIsSlave;
    }

    public void setRbIsSlave(JRadioButton rbIsSlave) {
        this.rbIsSlave = rbIsSlave;
    }

    public JPanel getStatusBar() {
        return statusBar;
    }

    public void setStatusBar(JPanel statusBar) {
        this.statusBar = statusBar;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public JPanel getTabConfiguracion() {
        return tabConfiguracion;
    }

    public void setTabConfiguracion(JPanel tabConfiguracion) {
        this.tabConfiguracion = tabConfiguracion;
    }

    public JPanel getTabMonitoreo() {
        return tabMonitoreo;
    }

    public void setTabMonitoreo(JPanel tabMonitoreo) {
        this.tabMonitoreo = tabMonitoreo;
    }

    public JComboBox getjComboBox5() {
        return jComboBox5;
    }

    public void setjComboBox5(JComboBox jComboBox5) {
        this.jComboBox5 = jComboBox5;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public JComboBox getjComboBox6() {
        return jComboBox6;
    }

    public void setjComboBox6(JComboBox jComboBox6) {
        this.jComboBox6 = jComboBox6;
    }

    public JTextField getjTextField13() {
        return jTextField13;
    }

    public void setjTextField13(JTextField jTextField13) {
        this.jTextField13 = jTextField13;
    }
    
    public DAOJmodbus getDao() {
        return dao;
    }

    public void setDao(DAOJmodbus dao) {
        this.dao = dao;
    }

    public GeneralControl getGeneralCtrl() {
        return generalCtrl;
    }

    public void setGeneralCtrl(GeneralControl generalCtrl) {
        this.generalCtrl = generalCtrl;
    }

    public RealTimeControl getRealCtrl() {
        return realCtrl;
    }

    public void setRealCtrl(RealTimeControl realCtrl) {
        this.realCtrl = realCtrl;
    }
    
    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public void setThreadManager(ThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    // End of variables declaration                   
    //</editor-fold>

    
    
}
