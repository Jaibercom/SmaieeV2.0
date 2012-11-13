/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.hilos;

import com.isolux.dao.jmodbus.OperacionesBalastoConfiguracionDaoJmodbus;
import javax.swing.SwingWorker;

/**
 * Clase que administra los hilos de las operaciones sobre la tarjeta. Estas
 * operaciones son del tipo register, por tanto tardan un tiempo en realizarse.
 * Inicialmente se usa para administrar las operaciones de la configuración de
 * balastos por su naturaleza demorada, pero despues se puede aplicar el mismo
 * concepto a las demás operaciones, para encapsularlas a travez de hilos de
 * este mismo tipo.
 *
 * @author Juan Camilo Cañas Gómez
 */
public class OperacionesDaoHilo extends SwingWorker<Boolean, String> {

    /**
     * Operacion es un valor de la clase
     * OperacionesBalastoConfiguracionDaoJmodbus, que determina qué operación se
     * va a realizar seobre la tarjeta.
     */
    int operacion;
    OperacionesBalastoConfiguracionDaoJmodbus obcdj = OperacionesBalastoConfiguracionDaoJmodbus.getInstancia();
    int parm1;
    int parm2;

    /**
     * Constructor para la operacion de cambiarDirBalasto
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
        boolean termino = false;

        switch (operacion) {
            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_CAMBIAR_DIR_BALASTO:

               termino= obcdj.cambiarDirBalasto(operacion, operacion);
//                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_ESCRIBIR_VALORES:

                termino=obcdj.escribirValores(parm1);
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




            default: //se seleccionó el balasto y se manda para que titile.

//                OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_SELECCIONAR_BALASTO;
                obcdj.seleccionaBalasto(parm1);

                break;
        }

        return termino;
    }

    @Override
    protected void done() {
        
        System.out.println("Se realizó la operacion.");
        
    }
}
