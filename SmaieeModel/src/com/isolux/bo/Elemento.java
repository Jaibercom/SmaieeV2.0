/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.bo;

/**
 * Clase de la cual extienden los demás elementos. Esta es la clase base.
 * @author Juan Camilo Canias Gómez
 */
public  abstract class Elemento<T> {
    String nombre;
    int numeroElemento;
    int activacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroElemento() {
        return numeroElemento;
    }

    public void setNumeroElemento(int numeroElemento) {
        this.numeroElemento = numeroElemento;
    }

    public int getActivacion() {
        return activacion;
    }

    public void setActivacion(int activacion) {
        this.activacion = activacion;
    }
    
    
}
