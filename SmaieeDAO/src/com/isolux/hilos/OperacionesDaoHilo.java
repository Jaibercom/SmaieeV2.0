/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.hilos;

import com.isolux.dao.jmodbus.OperacionesBalastoConfiguracionDaoJmodbus;
import com.isolux.properties.MapaDeMemoria;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 * Clase que administra los hilos de las operaciones sobre la tarjeta. Estas
 * operaciones son del tipo register, por tanto tardan un tiempo en realizarse.
 * Inicialmente se usa para administrar las operaciones de la configuración de
 * balastos por su naturaleza demorada, pero después se puede aplicar el mismo
 * concepto a las demás operaciones, para encapsularlas a través de hilos de
 * este mismo tipo.
 *
 * @author Juan Camilo Cañas Gómez
 */
public class OperacionesDaoHilo extends SwingWorker<Boolean, Integer> implements Observer {

    /**
     * Operación es un valor de la clase
     * OperacionesBalastoConfiguracionDaoJmodbus, que determina qué operación se
     * va a realizar sobre la tarjeta.
     */
    int operacion;
    OperacionesBalastoConfiguracionDaoJmodbus obcdj = OperacionesBalastoConfiguracionDaoJmodbus.getInstancia();
    int parm1;
    int parm2;
    private int delay = 0;
    private JLabel label;
    private JProgressBar bar;

    /**
     * Constructor para la operación de cambiarDirBalasto
     *
     * @param operacion
     * @param parm1
     * @param parm2
     */
    public OperacionesDaoHilo(int operacion, int parm1, int parm2) {
        this.operacion = operacion;
        this.parm1 = parm1;
        this.parm2 = parm2;

    }

    /**
     * Constructor para los metodos de un solo parametro. estos son: <br>
     * escribirValores(numBalasto)<br> leerValores(numeroBalasto)<br>
     * reset(numeroBalasto)<br>
     *
     * @param operacion
     * @param parm1
     */
    public OperacionesDaoHilo(int operacion, int parm1) {
        this.operacion = operacion;
        this.parm1 = parm1;
    }

    /**
     * Constructor para los métodos de:<br> balastosEnRed()<br>
     *
     *
     * @param operacion
     */
    public OperacionesDaoHilo(int operacion) {
        this.operacion = operacion;
    }

    @Override
    protected Boolean doInBackground() throws Exception {

//        int i = 0;
//        while (ColaOperaciones.getInstancia().isProgreso()==true) {
//            Thread.sleep(50);
//            publish(i);
//            i++;
//        }

        ColaOperaciones.getInstancia().setProgreso(true);
        boolean termino = false;
        publish(50);

        if (obcdj.getMode() == OperacionesBalastoConfiguracionDaoJmodbus.MODE_RUN) {
            obcdj.setMode(OperacionesBalastoConfiguracionDaoJmodbus.MODE_CONFIG);
        }
        publish(70);
        switch (operacion) {
            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_CAMBIAR_DIR_BALASTO:

                termino = obcdj.cambiarDirBalasto(operacion, operacion);
//                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_ESCRIBIR_VALORES:

                termino = obcdj.escribirValores(parm1);
//                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_LEER_VALORES:
                obcdj.leeValores(parm1);

                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_RESET:
                obcdj.reset(parm1);

                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_VERIFICA_RED:
                obcdj.balastosEnRed();

                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_SELECCIONAR_BALASTO:
                obcdj.seleccionaBalasto(parm1);
                termino = true;
                break;

            default: //se seleccionó el balasto y se manda para que titile.
                Logger.getLogger(OperacionesDaoHilo.class.getName()).log(Level.INFO, "Se escogio un valor invalido. se ignorará la orden");
                termino = true;
                break;
        }

        publish(100);

        return termino;
    }

    @Override
    protected void process(List<Integer> chunks) {
        if (bar != null) {
            bar.setValue(chunks.get(0));
        }

    }

    @Override
    protected void done() {
        try {

            if (label != null) {
                label.setText("Operacion terminada con exito");
                Thread.sleep(MapaDeMemoria.DELAY_OPERACIONES_LARGO);
                label.setText("");
                getBar().setValue(0);
            }
            Thread.sleep(getDelay());
            ColaOperaciones.getInstancia().setProgreso(false);
        } catch (Exception ex) {
            Logger.getLogger(OperacionesDaoHilo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update(Observable o, Object arg) {
//        boolean c = ColaOperaciones.getInstancia().isProgreso();
        System.out.println("Hay una operacion en progreso? " + ColaOperaciones.getInstancia().isProgreso());


    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JProgressBar getBar() {
        return bar;
    }

    public void setBar(JProgressBar bar) {
        this.bar = bar;
    }

    public int getDelay() {

        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
