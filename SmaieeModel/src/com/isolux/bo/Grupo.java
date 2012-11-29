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
public class Grupo extends Elemento {
    
    int groupNumber;
    long activation;
    String name;
    int[] balastosAfectados;

    
    
    public Grupo(int groupNumber, long activation, String name, int[] balastosAfectados) {
        this.groupNumber = groupNumber;
        this.activation = activation;
        this.name = name;
        this.balastosAfectados = balastosAfectados;
    }

    public Grupo(int groupNumber, long activation, String name) {
        this.groupNumber = groupNumber;
        this.activation = activation;
        this.name = name;
    }

    
    
    public Grupo(){
        int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
        balastosAfectados = new int[numBalastos];
    }
    
    public long getActivation() {
        return activation;
    }

    public void setActivation(long activation) {
        this.activation = activation;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getBalastosAfectados() {
        return balastosAfectados;
    }

    public void setBalastosAfectados(int[] balastosAfectados) {
        this.balastosAfectados = balastosAfectados;
    }    
}
