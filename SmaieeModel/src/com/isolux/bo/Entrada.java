/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.bo;

import com.isolux.properties.PropHandler;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class Entrada extends Elemento {
    
    int numeroEntrada;
    int activacion;
    int tipo;
    int comportamiento1;
    int comportamiento2;
    int nivelON;
    int nivelOFF;
    int tiempoRetardo;
    int ganancia;
    float nivIlumxvoltio;
    float nivelDeseado;
    int tipoSalida;
    int[] balastos;
    int[] grupos;
    int[] escenas;
    int valorADC;

    public Entrada() {
        balastos = new int[Integer.parseInt(PropHandler.getProperty("balast.max.number"))];
        grupos = new int[Integer.parseInt(PropHandler.getProperty("group.max.number"))];
        escenas = new int[Integer.parseInt(PropHandler.getProperty("scene.max.number"))];
    }
    
    public Entrada(int numeroEntrada, int activacion, int tipo, int comportamiento1, int comportamiento2, int nivelON, int nivelOFF, int tiempoRetardo, int ganancia, int nivIlumxvoltio, int nivelDeseado, int tipoSalida, int[] balastos, int[] grupos, int[] escenas, int valorADC) {
        this.numeroEntrada = numeroEntrada;
        this.activacion = activacion;
        this.tipo = tipo;
        this.comportamiento1 = comportamiento1;
        this.comportamiento2 = comportamiento2;
        this.nivelON = nivelON;
        this.nivelOFF = nivelOFF;
        this.tiempoRetardo = tiempoRetardo;
        this.ganancia = ganancia;
        this.nivIlumxvoltio = nivIlumxvoltio;
        this.nivelDeseado = nivelDeseado;
        this.tipoSalida = tipoSalida;
        this.balastos = balastos;
        this.grupos = grupos;
        this.escenas = escenas;
        this.valorADC = valorADC;
    }
    
    
    
    

    public int getActivacion() {
        return activacion;
    }

    public void setActivacion(int activacion) {
        this.activacion = activacion;
    }


    public int getComportamiento1() {
        return comportamiento1;
    }

    public void setComportamiento1(int comportamiento1) {
        this.comportamiento1 = comportamiento1;
    }

    public int getComportamiento2() {
        return comportamiento2;
    }

    public void setComportamiento2(int comportamiento2) {
        this.comportamiento2 = comportamiento2;
    }


    public int getGanancia() {
        return ganancia;
    }

    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }


    public float getNivIlumxvoltio() {
        return nivIlumxvoltio;
    }

    public void setNivIlumxvoltio(int nivIlumxvoltio) {
        this.nivIlumxvoltio = nivIlumxvoltio;
    }

    public float getNivelDeseado() {
        return nivelDeseado;
    }

    public void setNivelDeseado(int nivelDeseado) {
        this.nivelDeseado = nivelDeseado;
    }

    public int getNivelOFF() {
        return nivelOFF;
    }

    public void setNivelOFF(int nivelOFF) {
        this.nivelOFF = nivelOFF;
    }

    public int getNivelON() {
        return nivelON;
    }

    public void setNivelON(int nivelON) {
        this.nivelON = nivelON;
    }

    public int getNumeroEntrada() {
        return numeroEntrada;
    }

    public void setNumeroEntrada(int numeroEntrada) {
        this.numeroEntrada = numeroEntrada;
    }

    public int getTiempoRetardo() {
        return tiempoRetardo;
    }

    public void setTiempoRetardo(int tiempoRetardo) {
        this.tiempoRetardo = tiempoRetardo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(int tipoSalida) {
        this.tipoSalida = tipoSalida;
    }

    public int getValorADC() {
        return valorADC;
    }

    public void setValorADC(int valorADC) {
        this.valorADC = valorADC;
    }

    public int[] getBalastos() {
        return balastos;
    }

    public void setBalastos(int[] balastos) {
        this.balastos = balastos;
    }

    public int[] getEscenas() {
        return escenas;
    }

    public void setEscenas(int[] escenas) {
        this.escenas = escenas;
    }

    public int[] getGrupos() {
        return grupos;
    }

    public void setGrupos(int[] grupos) {
        this.grupos = grupos;
    }

    
}
