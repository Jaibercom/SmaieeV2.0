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
import com.isolux.properties.MapaDeMemoria;
import com.isolux.utils.Validacion;
import com.isolux.view.PpalView;
import com.isolux.view.componentes.SliderConValor;
import java.awt.HeadlessException;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
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
    private Vector<SliderConValor> escenasSliders = new Vector<SliderConValor>();

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
//        Agregamos el ultimo elemento del combo

        ppalView.getBalastoConfiguracion_jComboBox().addItem("254");
    }

    
    @Override
    public String[] elementosDisponibles(PpalView ppalView) {
//        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(ppalView.getDao());

        BalastoConfiguracionDAOJmodbus dao = new BalastoConfiguracionDAOJmodbus(ppalView.getDao());

        String[] ses;
        ses = dao.elementosSinGrabar();

        return ses;
    }
    
    
    public void cargarBalastosEnRed(PpalView ppalView) throws HeadlessException {
        int intentosBalastosRed = 0;

        while (intentosBalastosRed < 5) {
            String itemAt = ppalView.getBalastoConfiguracion_jComboBox().getItemAt(0).toString();
            if (itemAt.equals(String.valueOf(MapaDeMemoria.BALASTO_DE_FABRICA))) {
                try {
                    OperacionesDaoHilo hilo1 = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_VERIFICA_RED);
                    hilo1.setLabel(ppalView.getStatusLabel());
                    hilo1.getLabel().setText("Cargando los balastos en red. Intento: " + (intentosBalastosRed + 1));
                    hilo1.setBar(ppalView.getBarraProgreso_jProgressBar());
                    hilo1.setDelay(MapaDeMemoria.DELAY_OPERACIONES_LARGO);

                    hilo1.execute();
                    hilo1.get();
                    intentosBalastosRed++;
                    //                            Thread.sleep(MapaDeMemoria.DELAY_OPERACIONES_CORTO);

                    if (intentosBalastosRed == MapaDeMemoria.REINTENTOS) {
                        JOptionPane.showMessageDialog(null, "Al parecer no hay balastos en la red. Verifique que si se encuentran conectados.\nSi estan conectados intente reiniciar el programa.", "No hay balastos en la red", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(PpalView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(PpalView.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception e) {
                    Logger.getLogger(PpalView.class.getName()).log(Level.SEVERE, null, e);
                }
                 finally {
                }

            }else {
                intentosBalastosRed = 5;
            }
        }
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

            balastArray[16] = Conversion.integerArrayToInt(gruposAfectMenosS);


            /*
             * Cargamos la informacion de escenas
             */
            int[] escenasAfect = balasto.getEscenasAfectadas();
            balastArray[18] = Conversion.integerArrayToInt(escenasAfect);


            /*
             * Cargamos la informacion de los niveles de cada escena
             */
            int[] nivelesEscenas = balasto.getNivelesEscenas();

            int j = 20;//inicia desde 20 porque ese es el offset en el array de niveles de escenas

            for (int elemento : nivelesEscenas) {
                balastArray[j] = elemento;
                j++;
            }


            //Save array
            boolean escribioBuffer = getDao().setRegValue(initOffset, balastArray);
            Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.INFO, "El buffer respondio a la escritura {0}", escribioBuffer);


//            Luego escribimos el valor
            OperacionesDaoHilo h = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_ESCRIBIR_VALORES, balastNumber);
            h.setBar(ppalView.getBarraProgreso_jProgressBar());
            h.setLabel(ppalView.getStatusLabel());
            h.getLabel().setText("Escribiendo la informacion del balasto " + balastNumber);
            h.setPpalView(ppalView.getTabbedPane());
            h.execute();
            h.get();
            //MODO
//            setMode(MODE_RUN);

            Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.INFO, "Balasto numero {0} guardado correctamente.", balastNumber);
            ppalView.getStatusLabel().setText("Balasto numero " + balastNumber + " guardado correctamente.");

            JOptionPane.showMessageDialog(ppalView, ("La escritura en balasto " + (balastNumber+1) + " fue exitosa"));



            System.out.println("Balast No.:" + balastNumber + " saved.");
            state = true;

            OperacionesDaoHilo h1 = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_LEER_VALORES, balastNumber);
            h1.setBar(ppalView.getBarraProgreso_jProgressBar());
            h1.setLabel(ppalView.getStatusLabel());
            h1.getLabel().setText("Volviendo a leer la informacion del balasto " + balastNumber);
            h1.execute();
            h1.get();
            showSelectedElement(Integer.toString(balastNumber+1), ppalView);



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
            Integer numeroBalasto = Integer.parseInt(num)-1;// se hace la resta por el cambio de numeracion.
            int aumentado=numeroBalasto+1;

//            Si el balasto no es el de fabrica se lee. 
            if (aumentado!=Integer.parseInt(PropHandler.getProperty("balast.config.defabrica"))) {

                OperacionesDaoHilo hilo = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_LEER_VALORES, numeroBalasto);
                hilo.setLabel(ppalView.getStatusLabel());
                hilo.setBar(ppalView.getBarraProgreso_jProgressBar());
//            cola.getCola().enqueue(hilo);
                hilo.getLabel().setText("Cargando los valores del balasto " + num);
                hilo.execute();
                hilo.get();
                Balasto selectedBalast = BalastoDAOJmodbus.readBalast(numeroBalasto);
                balasto = selectedBalast;
                ppalView.getBalastoDir_jTextField().setText(String.valueOf(selectedBalast.getDir()+1));
                ppalView.getBalastoMin_jTextField().setText(String.valueOf(selectedBalast.getMin()));
                ppalView.getBalastoMax_jTextField().setText(String.valueOf(selectedBalast.getMax()));
                ppalView.getBalastoFT_jTextField().setText(String.valueOf(selectedBalast.getFt()));
                ppalView.getBalastoFR_jTextField().setText(String.valueOf(selectedBalast.getFr()));
                ppalView.getBalastoLF_jTextField().setText(String.valueOf(selectedBalast.getLf()));
                ppalView.getBalastoLX_jTextField().setText(String.valueOf(selectedBalast.getLx()));
                ppalView.getBalastoPot_jTextField().setText(String.valueOf(selectedBalast.getPot()));
                ppalView.getjLabel41().setText(num);
                //        ppalView.getBalastoNum_jComboBox().setSelectedIndex(0);

                gruposPert(String.valueOf(numeroBalasto), ppalView);
                ecenasPert(String.valueOf(numeroBalasto), ppalView);


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

                hilo1.get();
                //creamos el balasto con la nueva informacion
            }
        } catch (Exception ex) {
            Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.SEVERE, "Probelma mostrando la informacion del  balasto " + num, ex);
        }

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


        for (int i = 0; i < 16; i++) {
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
    private void ecenasPert(String numBalasto, PpalView ppalView) throws Exception {
        int[] escenasAfectadas = balasto.getEscenasAfectadas();
        int[] nivelesEscenas = balasto.getNivelesEscenas();
        int valor;//es el indice dentro del arreglo


        for (int i = 0; i < 16; i++) {
            if (escenasAfectadas[i] == 1) {
                escenasSliders.elementAt(i).getCheckBox().setSelected(true);
                valor = nivelesEscenas[i];
                escenasSliders.elementAt(i).setValor(valor);
            } else {
                escenasSliders.elementAt(i).getCheckBox().setSelected(false);
                valor = 0;
                escenasSliders.elementAt(i).setValor(valor);
            }

        }
        System.out.println("Seleccionadas las escenas y sus valores");
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
        int[] nivelesAfectados = new int[16];

        Stack<Integer> componentes = new Stack<Integer>();
        //ingresamos los grupos a la pila
        componentes.push(ppalView.getSliderConValor1().getValor());
        componentes.push(ppalView.getSliderConValor2().getValor());
        componentes.push(ppalView.getSliderConValor3().getValor());
        componentes.push(ppalView.getSliderConValor4().getValor());
        componentes.push(ppalView.getSliderConValor5().getValor());
        componentes.push(ppalView.getSliderConValor6().getValor());
        componentes.push(ppalView.getSliderConValor7().getValor());
        componentes.push(ppalView.getSliderConValor8().getValor());
        componentes.push(ppalView.getSliderConValor9().getValor());
        componentes.push(ppalView.getSliderConValor10().getValor());
        componentes.push(ppalView.getSliderConValor11().getValor());
        componentes.push(ppalView.getSliderConValor12().getValor());
        componentes.push(ppalView.getSliderConValor13().getValor());
        componentes.push(ppalView.getSliderConValor14().getValor());
        componentes.push(ppalView.getSliderConValor15().getValor());
        componentes.push(ppalView.getSliderConValor16().getValor());


        int i = nivelesAfectados.length - 1;
        while (!componentes.isEmpty()) {
            Integer pop = componentes.pop();

            binario.append(pop);
            nivelesAfectados[i] = pop;

            i--;
        }

        //teniendo contruido el binario en texto procedemos a convertirlo a entero
        balasto.setNivelesEscenas(nivelesAfectados);
    }

    public Vector getGruposJCheckboxes() {
        return gruposJCheckboxes;
    }

    public void setGruposJCheckboxes(Vector gruposJCheckbox) {
        this.gruposJCheckboxes = gruposJCheckbox;
    }

    private void inicializandoGruposCheckboxes(PpalView ppalView) {
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

    }

    ;
    
    private void inicializandoEscenasConSliders(PpalView ppalView) {
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

    public void cambiarDireccion(PpalView ppalView) throws Exception {
        int dir = Integer.parseInt(ppalView.getBalastoConfiguracion_jComboBox().getSelectedItem().toString())-1;
        int nuevaDir = Integer.parseInt(ppalView.getBalastoDir_jTextField().getText());
        
        if (nuevaDir<1||(nuevaDir)>64) {
            JOptionPane.showMessageDialog(ppalView, "Debe digitar un numero entre 1 y 64");
            return;
        }
        
        nuevaDir=nuevaDir-1;
                
        int[] dirB = new int[1];
        dirB[0] = nuevaDir;

//           escribimos en el buffer
//            boolean escribioBuffer = getDao().setRegValue(initOffset, balastArray);
        boolean escribioBuffer = getDao().setRegValue(MapaDeMemoria.BALASTO_DIRB, dirB);
        Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.INFO, "El buffer respondio a la escritura de dirB {0}", escribioBuffer);

        int[] numbB = new int[1];
        numbB[0] = dir;

        boolean escribioBuffer1 = getDao().setRegValue(MapaDeMemoria.BALASTO_NUMB, numbB);
        Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.INFO, "El buffer respondio a la escritura de numbB {0}", escribioBuffer1);


//            Luego escribimos el valor
        OperacionesDaoHilo h = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_CAMBIAR_DIR_BALASTO, dir, nuevaDir);
        h.setBar(ppalView.getBarraProgreso_jProgressBar());
        h.setLabel(ppalView.getStatusLabel());
        h.getLabel().setText("Cambiando la direccion del balasto " + (dir+1) + " por " + (nuevaDir+1));
        h.execute();
        h.get();
        //MODO
//            setMode(MODE_RUN);

        Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.INFO, "Balasto {0} cambiado por {1} correctamente", new Object[]{dir, nuevaDir});
        ppalView.getStatusLabel().setText("Balasto " + (dir+1) + " cambiado por " + (nuevaDir+1) + " correctamente");

        JOptionPane.showMessageDialog(ppalView, ("El cambio de dirección del balasto " + (nuevaDir+1) + " fue exitoso"));

        OperacionesDaoHilo h1 = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_VERIFICA_RED);
        h1.setBar(ppalView.getBarraProgreso_jProgressBar());
        h1.setLabel(ppalView.getStatusLabel());
        h1.getLabel().setText("Verificando la red");
        h1.execute();
        h1.get();

        

        cleanView(ppalView);
        cargarBalastosEnRed(ppalView);
        refrescarVista(ppalView);
        ppalView.getBalastoDir_jTextField().setText("0");
    }

    public void resetElement(PpalView ppalView) {
        try {

            Integer numBalasto = Integer.parseInt(ppalView.getBalastoConfiguracion_jComboBox().getSelectedItem().toString())-1;
            int reset = JOptionPane.showConfirmDialog(ppalView, "¿Está seguro de que quiere resetar el balasto numero " + (numBalasto+1) + "?", "Resetear Balasto", JOptionPane.OK_CANCEL_OPTION);
            if (reset == JOptionPane.OK_OPTION) {
                OperacionesDaoHilo hilo = new OperacionesDaoHilo(OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_RESET, numBalasto);
                hilo.setBar(ppalView.getBarraProgreso_jProgressBar());
                hilo.setLabel(ppalView.getStatusLabel());
                hilo.getLabel().setText("Reseteando balasto...");
                hilo.setPpalView(ppalView.getTabbedPane());
                hilo.execute();
                hilo.get();
                JOptionPane.showMessageDialog(ppalView, "Balasto reseteado correctamente");
                refrescarVista(ppalView);
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(ppalView, "Hubo un error reseteando el balasto.\nIntentelo nuevamente.");
        } catch (ExecutionException ex) {
            Logger.getLogger(BalastosConfiguracionControl.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(ppalView, "Hubo un error reseteando el balasto.\nIntentelo nuevamente.");
        }


    }
}