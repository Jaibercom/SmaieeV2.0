/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.bo.Balasto;
import com.isolux.dao.Conversion;
import com.isolux.dao.jmodbus.BalastoConfiguracionDAOJmodbus;
import com.isolux.dao.jmodbus.BalastoDAOJmodbus;
import com.isolux.dao.jmodbus.ElementoDAOJmobdus;
import com.isolux.dao.jmodbus.OperacionesBalastoConfiguracionDaoJmodbus;
import com.isolux.dao.jmodbus.OperacionesElemento_Interface;
import com.isolux.dao.jmodbus.UtilsJmodbus;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import com.isolux.hilos.ColaOperaciones;
import com.isolux.hilos.OperacionesDaoHilo;
import com.isolux.utils.Validacion;
import com.isolux.view.PpalView;
import com.isolux.view.componentes.SliderConValor;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Camilo Canias Gómez
 */
public class BalastosConfiguracionControl extends ElementoDAOJmobdus implements OperacionesElemento_Interface, ElementoControl_Interface {

    Balasto balasto = new Balasto();
    int bitsLectura = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
    ColaOperaciones cola = ColaOperaciones.getInstancia();// cola de operaciones de configuracion.
    private Vector<JCheckBox> gruposJCheckboxes = new Vector<JCheckBox>();
    private Vector<SliderConValor> escenasSliders= new Vector<SliderConValor>();
    
    

    public BalastosConfiguracionControl(DAOJmodbus dao) {
        super(dao);
        
    }

    public BalastosConfiguracionControl() {
    }

    @Override
    public String[] elementosSinGrabar() {

        BalastoDAOJmodbus dao1 = new BalastoDAOJmodbus(DAOJmodbus.getInstancia());
        int[] addedBalastsCardArray = dao1.getAddedBalastsCardArray();
        String[] ele = new String[addedBalastsCardArray.length];

        for (int i = 0; i < addedBalastsCardArray.length; i++) {
            ele[i] = Integer.toString(addedBalastsCardArray[i]);
        }

        return ele;

    }

    @Override
    public void refrescarVista(PpalView ppalView) {
        cleanView(ppalView);
        filterAddedElements(ppalView);
        String[] elementosDisponibles = elementosDisponibles(ppalView);
        Validacion.actualizarCombo(ppalView.getBalastoConfiguracion_jComboBox(), elementosDisponibles, Validacion.BALASTOS_NO_DISPONIBLES);
    }

    @Override
    public String[] elementosDisponibles(PpalView ppalView) {
//        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());

        BalastoConfiguracionDAOJmodbus dao = new BalastoConfiguracionDAOJmodbus(ppalView.getDao());

        String[] ses;
        ses = dao.elementosSinGrabar();

        return ses;
    }

    @Override
    public void cleanView(PpalView ppalView) {

        ppalView.getBalastoNum_jComboBox().setSelectedIndex(0);
        ppalView.getBalastoDir_jTextField().setText("0");
        ppalView.getBalastoMin_jTextField().setText("0");
        ppalView.getBalastoFR_jTextField().setText("0");
        ppalView.getBalastoFT_jTextField().setText("0");
        ppalView.getBalastoLF_jTextField().setText("0");
        ppalView.getBalastoLX_jTextField().setText("0");
        ppalView.getBalastoMax_jTextField().setText("0");
        seleccionGrupos(ppalView, false);
        seleccionEscenas(ppalView, false);


    }

    @Override
    public void saveElement(PpalView ppalView) {
        setDao(ppalView.getDao());
        boolean state = false;

        recojerDatos(ppalView);

        int balastNumber = balasto.getBalastNumber();

        try {


            //Init offset.
            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.init.position"));
            int[] balastArray = new int[Integer.parseInt(PropHandler.getProperty("balast.memory.size"))];

            System.out.println("SAVE BALAST NUMBER: " + balastNumber);

            balastArray[0] = balastNumber;                  //2000
            balastArray[1] = balasto.getLevel();            //2001
            balastArray[2] = balasto.getActivation();       //2002



//            codifica el nombre y lo mete en el array
            UtilsJmodbus.encriptarNombre(balastArray, 3, balasto.getName(), 5);

            balastArray[8] = balasto.getDir();              //2008
            balastArray[9] = balasto.getMin();              //2009
            balastArray[10] = balasto.getMax();             //2010
            balastArray[11] = balasto.getFt();              //2011
            balastArray[12] = balasto.getFr();              //2012
            balastArray[13] = balasto.getLf();              //2013
            balastArray[14] = balasto.getLx();              //2014
            balastArray[15] = balasto.getPot();             //2015


            //debemos convertir el array de enteros a num
            int[] gruposAfectMenosS = balasto.getGruposAfectados();

//vamos aqui            

//            int j=16;
//            int[] gruposAfect = balasto.getGruposAfectados();
//            for (int i = 0; i < 15; i++) {
//                 
//                 balastArray[j]=gruposAfect[i];
//                 j++;
//            }


            /**
             * Numero dentro del array que corresponde al offset desde donde se
             * empezarán a guardar los datos de los grupos //
             */
//            int gruposOffset = 16;
//            int tamReg = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
//            int[] gruposAfect = balasto.getGruposAfectados();
//            int cuantosElementos = 16;
//            gruposAfect = UtilsJmodbus.obtenerElementosAfectados(balastArray, gruposOffset, cuantosElementos, tamReg, 8, 8);
//            int[] grupomenoss = new int[8];
//            int[] grupoMass = new int[8];
//            System.arraycopy(gruposAfect, 8, grupomenoss, 0, bitsLectura);
//            System.arraycopy(gruposAfect, 8, grupoMass, 0, bitsLectura);
//            //<editor-fold defaultstate="collapsed" desc="Dividimos el arreglo gruposAfect en dos">
//            int j=0;
//            for (int i = 7; i >=0 ; i--) {
//                grupomenoss[j]=gruposAfect[i];
//                j++;
//            }
//            
//            j=0;
//            for (int i = 15; i >= 8; i--) {
//                grupoMass[j]=gruposAfect[i];
//                j++;
//            }
//            //</editor-fold>
//            Vector<int[]> partesDelBinario = Conversion.integerArrayTo2IntegerArrayBinarios(gruposAfect);
//            
//            balastArray[16] = Conversion.integerArrayToInt(partesDelBinario.elementAt(0));
//            balastArray[17] = Conversion.integerArrayToInt(partesDelBinario.elementAt(1));
            balastArray[16] = Conversion.integerArrayToInt(gruposAfectMenosS);

            /*
             * Cargamos la informacion de escenas
             */
//            int scenesOffset = 18;
//            int tamReg1 = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
//            int[] escenasAfect = balasto.getEscenasAfectadas();
//            int cuantosElementos1 = 16;
//            escenasAfect = UtilsJmodbus.obtenerElementosAfectados(balastArray, scenesOffset, cuantosElementos1, tamReg1, 8, 8);
//            balasto.setEscenasAfectadas(escenasAfect);


            /*
             * Cargamos la informacion de los niveles de cada escena
             */
            //Save array
            boolean escribioBuffer = getDao().setRegValue(initOffset, balastArray);
            //            addElement(balastNumber);// agrega el indice a la lista de balastros en memoria



//            Luego escribimos el valor
            OperacionesDaoHilo h = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_ESCRIBIR_VALORES, balastNumber);
            h.execute();
            h.setBar(ppalView.getBarraProgreso_jProgressBar());
            h.setLabel(ppalView.getStatusLabel());
            h.getLabel().setText("Escribiendo la informacion del balasto " + balastNumber);

            h.get();
            //MODO
//            setMode(MODE_RUN);

            Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.INFO, "Balasto numero {0} guardado correctamente.", balastNumber);
            ppalView.getStatusLabel().setText("Balasto numero " + balastNumber + " guardado correctamente.");
//            System.out.println("Balast No.:" + balastNumber + " saved.");
            state = true;

           OperacionesDaoHilo h1 = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_LEER_VALORES, balastNumber);
            h1.execute();
            h1.setBar(ppalView.getBarraProgreso_jProgressBar());
            h1.setLabel(ppalView.getStatusLabel());
            h1.getLabel().setText("Volviendo a leer la informacion del balasto " + balastNumber);
            
            showSelectedElement(Integer.toString(balastNumber), ppalView);


        } catch (Exception e) {
            state = false;
            Logger.getLogger(BalastoConfiguracionDAOJmodbus.class.getName()).log(Level.SEVERE, "ERROR guardando el balasto " + balastNumber, e);
            JOptionPane.showMessageDialog(ppalView, "No se pudo escribir la información.\nPudo existir problemas en la red al momento de escribir. Intentelo nuevamente.");
        }


    }

    @Override
    public void deleteElement(PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void readElements(PpalView ppalView) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void filterAddedElements(PpalView ppalView) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void showSelectedElement(String num, PpalView ppalView) {
        try {
            //        BalastoDAOJmodbus dao=new BalastoDAOJmodbus(new DAOJmodbus());

            Integer numeroBalasto = Integer.parseInt(num);


            OperacionesDaoHilo hilo = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_LEER_VALORES, Integer.parseInt(num));
            hilo.setLabel(ppalView.getStatusLabel());
            hilo.setBar(ppalView.getBarraProgreso_jProgressBar());
//            cola.getCola().enqueue(hilo);
            hilo.getLabel().setText("Cargando los valores del balasto " + num);
            hilo.execute();
            hilo.get();
            Balasto selectedBalast = BalastoDAOJmodbus.readBalast(Integer.parseInt(num));
            balasto = selectedBalast;
            ppalView.getBalastoDir_jTextField().setText(String.valueOf(selectedBalast.getDir()));
            ppalView.getBalastoMin_jTextField().setText(String.valueOf(selectedBalast.getMin()));
            ppalView.getBalastoMax_jTextField().setText(String.valueOf(selectedBalast.getMax()));
            ppalView.getBalastoFT_jTextField().setText(String.valueOf(selectedBalast.getFt()));
            ppalView.getBalastoFR_jTextField().setText(String.valueOf(selectedBalast.getFr()));
            ppalView.getBalastoLF_jTextField().setText(String.valueOf(selectedBalast.getLf()));
            ppalView.getBalastoLX_jTextField().setText(String.valueOf(selectedBalast.getLx()));
            ppalView.getBalastoPot_jTextField().setText(String.valueOf(selectedBalast.getPot()));
            ppalView.getjLabel41().setText(num);
            //        ppalView.getBalastoNum_jComboBox().setSelectedIndex(0);

            gruposPert(num, ppalView);
//            ecenasPert(num, ppalView);


            OperacionesDaoHilo hilo1 = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_SELECCIONAR_BALASTO, numeroBalasto);

            hilo1.setLabel(ppalView.getStatusLabel());
            hilo1.getLabel().setText("Seleccionando visualmente balasto...");
            hilo1.setBar(ppalView.getBarraProgreso_jProgressBar());
//            cola.getCola().enqueue(hilo1);


            //Inicimos todas las operaciones
//            cola.iniciarOperaciones();

            hilo1.execute();
//            setSingleReg(2000, numeroBalasto);
//            setSingleReg(1, 25);


            //creamos el balasto con la nueva informacion
        } catch (Exception ex) {
            Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.SEVERE, "Probelma mostrando la informacion del  balasto "+num, ex);
        }

//        } catch (InterruptedException ex) {
//            Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExecutionException ex) {
//            Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.SEVERE, null, ex);
//        }


    }

    /**
     * Método que limpia los grupos
     *
     * @param ppalView
     */
    private void seleccionGrupos(PpalView ppalView, boolean act) {

        ppalView.getGrupo_jCheckBox1().setSelected(act);
        ppalView.getGrupo_jCheckBox2().setSelected(act);
        ppalView.getGrupo_jCheckBox3().setSelected(act);
        ppalView.getGrupo_jCheckBox4().setSelected(act);
        ppalView.getGrupo_jCheckBox5().setSelected(act);
        ppalView.getGrupo_jCheckBox6().setSelected(act);
        ppalView.getGrupo_jCheckBox7().setSelected(act);
        ppalView.getGrupo_jCheckBox8().setSelected(act);
        ppalView.getGrupo_jCheckBox9().setSelected(act);
        ppalView.getGrupo_jCheckBox10().setSelected(act);
        ppalView.getGrupo_jCheckBox11().setSelected(act);
        ppalView.getGrupo_jCheckBox12().setSelected(act);
        ppalView.getGrupo_jCheckBox13().setSelected(act);
        ppalView.getGrupo_jCheckBox14().setSelected(act);
        ppalView.getGrupo_jCheckBox15().setSelected(act);
        ppalView.getGrupo_jCheckBox16().setSelected(act);



    }

    /**
     * Método que limpia o selecciona todas las escenas.
     *
     * @param ppalView
     * @param act boolean que representa si se seleccionan o se deseleccionan
     * todas las escenas
     */
    private void seleccionEscenas(PpalView ppalView, boolean act) {
        ppalView.getSliderConValor1().getCheckBox().setSelected(act);
        ppalView.getSliderConValor2().getCheckBox().setSelected(act);
        ppalView.getSliderConValor3().getCheckBox().setSelected(act);
        ppalView.getSliderConValor4().getCheckBox().setSelected(act);
        ppalView.getSliderConValor5().getCheckBox().setSelected(act);
        ppalView.getSliderConValor6().getCheckBox().setSelected(act);
        ppalView.getSliderConValor7().getCheckBox().setSelected(act);
        ppalView.getSliderConValor8().getCheckBox().setSelected(act);
        ppalView.getSliderConValor9().getCheckBox().setSelected(act);
        ppalView.getSliderConValor10().getCheckBox().setSelected(act);
        ppalView.getSliderConValor11().getCheckBox().setSelected(act);
        ppalView.getSliderConValor12().getCheckBox().setSelected(act);
        ppalView.getSliderConValor13().getCheckBox().setSelected(act);
        ppalView.getSliderConValor14().getCheckBox().setSelected(act);
        ppalView.getSliderConValor15().getCheckBox().setSelected(act);
        ppalView.getSliderConValor16().getCheckBox().setSelected(act);

    }

    /**
     * Método que marca en la GUI los grupos a los que pertenece un balasto
     *
     * @param numBalasto balasto al cual se le van a buscar los grupos
     */
    public void gruposPert(String numBalasto, PpalView ppalView) {
        
        inicializandoEscenasConSliders(ppalView);
        inicializandoGruposCheckboxes(ppalView);
        
        int[] gruposAfectados = balasto.getGruposAfectados();

        
        for (int i=0; i<16; i++) {
            if (gruposAfectados[i] == 1) {
                gruposJCheckboxes.elementAt(i).setSelected(true);
            } else {
                gruposJCheckboxes.elementAt(i).setSelected(false);
            }
           
        }


        //        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Método que marca en la GUI las escenas a las que pertenece un balasto
     * estableciendo los valores del nivel
     *
     * @param numBalasto
     */
    private void ecenasPert(String numBalasto, PpalView ppalView) throws Exception{
        int[] escenasAfectadas = balasto.getEscenasAfectadas();

        int i = 0;
        for (int ele : escenasAfectadas) {
            if (ele == 1) {
                escenasSliders.elementAt(ele).getCheckBox().setSelected(true);
            } else {
                escenasSliders.elementAt(ele).getCheckBox().setSelected(false);
            }
            i++;
        }
    }

   
    public void recojerDatos(PpalView ppalView) {


        recojerDatosBasicos(ppalView);
        recojerInfoGrupos(ppalView);
        recojerInfoEscenas(ppalView);
        recojerNivelesEscenas(ppalView);


    }

    private void recojerInfoGrupos(PpalView ppalView) {

        StringBuilder binario = new StringBuilder();
        int[] gruposAfectados = new int[16];

        Stack<JCheckBox> componentes = new Stack<JCheckBox>();
        //ingresamos los grupos a la pila
        componentes.push(ppalView.getGrupo_jCheckBox1());
        componentes.push(ppalView.getGrupo_jCheckBox2());
        componentes.push(ppalView.getGrupo_jCheckBox3());
        componentes.push(ppalView.getGrupo_jCheckBox4());
        componentes.push(ppalView.getGrupo_jCheckBox5());
        componentes.push(ppalView.getGrupo_jCheckBox6());
        componentes.push(ppalView.getGrupo_jCheckBox7());
        componentes.push(ppalView.getGrupo_jCheckBox8());
        componentes.push(ppalView.getGrupo_jCheckBox9());
        componentes.push(ppalView.getGrupo_jCheckBox10());
        componentes.push(ppalView.getGrupo_jCheckBox11());
        componentes.push(ppalView.getGrupo_jCheckBox12());
        componentes.push(ppalView.getGrupo_jCheckBox13());
        componentes.push(ppalView.getGrupo_jCheckBox14());
        componentes.push(ppalView.getGrupo_jCheckBox15());
        componentes.push(ppalView.getGrupo_jCheckBox16());


        int i = gruposAfectados.length - 1;
        while (!componentes.isEmpty()) {
            JCheckBox pop = componentes.pop();

            if (pop.isSelected()) {
                binario.append(1);
                gruposAfectados[i] = 1;

            } else {
                binario.append(0);
                gruposAfectados[i] = 0;
            }

            i--;
        }



        //teniendo contruido el binario en texto procedemos a convertirlo a entero
        Integer binarioAEntero = Conversion.binarioAEntero(binario.toString());
        System.out.println("Representacion binaria de grupos: " + binario);
        System.out.println("Representacion entera de grupos: " + binarioAEntero);
        balasto.setGruposAfectados(gruposAfectados);


    }

    private void recojerInfoEscenas(PpalView ppalView) {

        StringBuilder binario = new StringBuilder();
        int[] escenasAfectadas = new int[16];

        Stack<JCheckBox> componentes = new Stack<JCheckBox>();
        //ingresamos los grupos a la pila
        componentes.push(ppalView.getSliderConValor1().getCheckBox());
        componentes.push(ppalView.getSliderConValor2().getCheckBox());
        componentes.push(ppalView.getSliderConValor3().getCheckBox());
        componentes.push(ppalView.getSliderConValor4().getCheckBox());
        componentes.push(ppalView.getSliderConValor5().getCheckBox());
        componentes.push(ppalView.getSliderConValor6().getCheckBox());
        componentes.push(ppalView.getSliderConValor7().getCheckBox());
        componentes.push(ppalView.getSliderConValor8().getCheckBox());
        componentes.push(ppalView.getSliderConValor9().getCheckBox());
        componentes.push(ppalView.getSliderConValor10().getCheckBox());
        componentes.push(ppalView.getSliderConValor11().getCheckBox());
        componentes.push(ppalView.getSliderConValor12().getCheckBox());
        componentes.push(ppalView.getSliderConValor13().getCheckBox());
        componentes.push(ppalView.getSliderConValor14().getCheckBox());
        componentes.push(ppalView.getSliderConValor15().getCheckBox());
        componentes.push(ppalView.getSliderConValor16().getCheckBox());


        int i = escenasAfectadas.length - 1;
        while (!componentes.isEmpty()) {
            JCheckBox pop = componentes.pop();

            if (pop.isSelected()) {
                binario.append(1);
                escenasAfectadas[i] = 1;

            } else {
                binario.append(0);
                escenasAfectadas[i] = 0;
            }

            i--;
        }



        //teniendo contruido el binario en texto procedemos a convertirlo a entero
        Integer binarioAEntero = Conversion.binarioAEntero(binario.toString());
        System.out.println("Representacion binaria de escenas: " + binario);
        System.out.println("Representacion entera de escenas: " + binarioAEntero);
        balasto.setEscenasAfectadas(escenasAfectadas);
    }

    private void recojerDatosBasicos(PpalView ppalView) {
        balasto.setDir(Integer.parseInt(ppalView.getBalastoDir_jTextField().getText()));
        balasto.setMin(Integer.parseInt(ppalView.getBalastoMin_jTextField().getText()));
        balasto.setMax(Integer.parseInt(ppalView.getBalastoMax_jTextField().getText()));
        balasto.setFt(Integer.parseInt(ppalView.getBalastoFT_jTextField().getText()));
        balasto.setFr(Integer.parseInt(ppalView.getBalastoFR_jTextField().getText()));
        balasto.setLf(Integer.parseInt(ppalView.getBalastoLF_jTextField().getText()));
        balasto.setLx(Integer.parseInt(ppalView.getBalastoLX_jTextField().getText()));
        balasto.setPot(Integer.parseInt(ppalView.getBalastoPot_jTextField().getText()));
    }

    private void recojerNivelesEscenas(PpalView ppalView) {
        StringBuilder binario = new StringBuilder();
        int[] escenasAfectadas = new int[16];

        Stack<Integer> componentes = new Stack<Integer>();
        //ingresamos los grupos a la pila
        componentes.push(ppalView.getSliderConValor1().getValor());
        componentes.push(ppalView.getSliderConValor2().getValor());
        componentes.push(ppalView.getSliderConValor10().getValor());
        componentes.push(ppalView.getSliderConValor11().getValor());
        componentes.push(ppalView.getSliderConValor12().getValor());
        componentes.push(ppalView.getSliderConValor13().getValor());
        componentes.push(ppalView.getSliderConValor14().getValor());
        componentes.push(ppalView.getSliderConValor15().getValor());
        componentes.push(ppalView.getSliderConValor16().getValor());


        int i = escenasAfectadas.length - 1;
        while (!componentes.isEmpty()) {
            Integer pop = componentes.pop();

            binario.append(pop);
            escenasAfectadas[i] = pop;

            i--;
        }

        //teniendo contruido el binario en texto procedemos a convertirlo a entero
        balasto.setNivelesEscenas(escenasAfectadas);
    }

    public Vector getGruposJCheckboxes() {
        return gruposJCheckboxes;
    }

    public void setGruposJCheckboxes(Vector gruposJCheckbox) {
        this.gruposJCheckboxes = gruposJCheckbox;
    }
    
    private void inicializandoGruposCheckboxes(PpalView ppalView){
        if (gruposJCheckboxes.isEmpty()) {
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox1());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox2());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox3());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox4());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox5());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox6());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox7());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox8());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox9());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox10());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox11());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox12());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox13());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox14());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox15());
            gruposJCheckboxes.addElement(ppalView.getGrupo_jCheckBox16());
        }
        
    };
    
    private void inicializandoEscenasConSliders(PpalView ppalView){
        if (escenasSliders.isEmpty()) {
            escenasSliders.addElement(ppalView.getSliderConValor1());
            escenasSliders.addElement(ppalView.getSliderConValor2());
            escenasSliders.addElement(ppalView.getSliderConValor3());
            escenasSliders.addElement(ppalView.getSliderConValor4());
            escenasSliders.addElement(ppalView.getSliderConValor5());
            escenasSliders.addElement(ppalView.getSliderConValor6());
            escenasSliders.addElement(ppalView.getSliderConValor7());
            escenasSliders.addElement(ppalView.getSliderConValor8());
            escenasSliders.addElement(ppalView.getSliderConValor9());
            escenasSliders.addElement(ppalView.getSliderConValor10());
            escenasSliders.addElement(ppalView.getSliderConValor11());
            escenasSliders.addElement(ppalView.getSliderConValor12());
            escenasSliders.addElement(ppalView.getSliderConValor13());
            escenasSliders.addElement(ppalView.getSliderConValor14());
            escenasSliders.addElement(ppalView.getSliderConValor15());
            escenasSliders.addElement(ppalView.getSliderConValor16());
        }
 }
}