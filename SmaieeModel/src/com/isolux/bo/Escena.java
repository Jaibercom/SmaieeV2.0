package com.isolux.bo;

import com.isolux.properties.PropHandler;
import java.util.ArrayList;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class Escena {
    
    int numeroEscena;
    int activacion;
    String nombre;
    int[] nivelBalasto;
    int[] balastosAfectados;

    public Escena() {
        int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
        balastosAfectados = new int[numBalastos];
        nivelBalasto = new int[numBalastos];
    }
    
    public Escena(int numeroEscena, int activacion, String nombre, int[] nivelBalasto, int[] balastosAfectados) {
        this.numeroEscena = numeroEscena;
        this.activacion = activacion;
        this.nombre = nombre;
        this.nivelBalasto = nivelBalasto;
        this.balastosAfectados = balastosAfectados;
    }

    
    
    public int getActivacion() {
        return activacion;
    }

    public void setActivacion(int activacion) {
        this.activacion = activacion;
    }

    public int[] getBalastosAfectados() {
        return balastosAfectados;
    }

    public void setBalastosAfectados(int[] balastosAfectados) {
        this.balastosAfectados = balastosAfectados;
    }

    public int[] getNivelBalasto() {
        return nivelBalasto;
    }

    public void setNivelBalasto(int[] nivelBalasto) {
        this.nivelBalasto = nivelBalasto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroEscena() {
        return numeroEscena;
    }

    public void setNumeroEscena(int numeroEscena) {
        this.numeroEscena = numeroEscena;
    }
}
