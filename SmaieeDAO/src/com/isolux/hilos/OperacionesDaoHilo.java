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
     * Operacion es un valor de la clase OperacionesBalastoConfiguracionDaoJmodbus,
     * que determina qué operación se va a realizar seobre la tarjeta.
     */
    int operacion;
    OperacionesBalastoConfiguracionDaoJmodbus obcdj=OperacionesBalastoConfiguracionDaoJmodbus.getInstancia();

    public OperacionesDaoHilo(int operacion) {
        this.operacion = operacion;
        
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        boolean termino = false;

        switch (operacion) {
            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_CAMBIAR_DIR_BALASTO:


                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_ESCRIBIR_VALORES:


                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_LEER_VALORES:


                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_RESET:


                termino = true;
                break;

            case OperacionesBalastoConfiguracionDaoJmodbus.OPCODE_VERIFICA_RED:


                termino = true;
                break;

           
           

            default: //se seleccionó el balasto y se manda para que titile.
                throw new AssertionError();
        }

        return termino;
    }

    @Override
    protected void done() {
        super.done(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
