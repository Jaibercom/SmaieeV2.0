/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.hilos;

import java.util.Observable;

/**
 *
 * @author Sebastian Rodriguez
 */
public class ColaOperaciones extends Observable{

    private boolean progreso = false;
    private static ColaOperaciones instancia = null;

    public static ColaOperaciones getInstancia() {

        if (instancia == null) {
            instancia=new ColaOperaciones();
        }
        return instancia;
    }

    public boolean isProgreso() {
        return progreso;
    }

    public void setProgreso(boolean progreso) {
        this.progreso = progreso;
//        setChanged();
        this.notifyObservers();
    }

    private ColaOperaciones() {
    }

   
}
