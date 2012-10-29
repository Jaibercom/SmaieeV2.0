/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao;

import com.isolux.dao.modbus.ConectionManager;
import javax.swing.SwingWorker;

/**
 * VerificarConexion. Clase que se encarga de lanzar en un hilo aparte la
 * verificacion de la conexion con la tarjeta y de reconectarse si es necesario.
 *
 * @author Juan Camilo Canias Gómez
 */
public class ConexionWorker extends SwingWorker<Boolean, String> {

    String ip = "192.168.1.121";

    @Override
    protected Boolean doInBackground() throws Exception {
        boolean estaConectado = ConectionManager.getInstancia(ip).estaConectado();      
        int i=0;
        while(estaConectado){
            wait(10000);
            i++;
            System.out.println("Esta conectado hace "+i+"sg");
        }
        
        return false;

    }

    @Override
        protected void done() {
       // Mostramos el nombre del hilo para ver que efectivamente esto
      // se ejecuta en el hilo de eventos.
      System.out.println("done() esta en el hilo "
         + Thread.currentThread().getName());
     
    }
}
