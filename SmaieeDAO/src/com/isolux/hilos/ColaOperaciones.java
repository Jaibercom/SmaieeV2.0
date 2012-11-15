/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.hilos;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import sun.misc.Queue;

/**
 *
 * @author Sebastian Rodriguez
 */
public class ColaOperaciones {

    private boolean progreso = false;
    private static ColaOperaciones instancia = null;
    private Queue cola = new Queue();

    public static ColaOperaciones getInstancia() {

        if (instancia == null) {
            instancia = new ColaOperaciones();
        }
        return instancia;
    }

    public boolean isProgreso() {
        return progreso;
    }

    public void setProgreso(boolean progreso) {
        this.progreso = progreso;


    }

    private ColaOperaciones() {
    }

    public Queue getCola() {
        return cola;
    }

    /**
     * Método que inicia toda las operaciones que se encuentran en la cola
     */
    public void iniciarOperaciones(){
        HiloCola nuevoHilo=new HiloCola();
        nuevoHilo.execute();
    }
    
    /**
     * Método que ejecuta el hilo que está pendiente.
     */
    synchronized private void iniciarCola() throws InterruptedException {


        if (cola.isEmpty() == false) {
            OperacionesDaoHilo elemento = (OperacionesDaoHilo) cola.dequeue();
            System.out.println("Desencolando "+elemento.getClass().toString());
            elemento.execute();
            

            while (elemento.isDone() == false) {
                Thread.sleep(500);
            }

            elemento.getLabel().setText("La tarea se realizo exitosamente.");
        }


    }

    private class HiloCola extends SwingWorker<Void, String> {

        

        @Override
        protected void done() {
            Logger.getLogger(HiloCola.class.getName()).log(Level.INFO,"La cola no contiene más operaciones");
            
        }

        @Override
        protected Void doInBackground() throws Exception {
            iniciarCola();
            return null;
        }
        
        
    }
}
