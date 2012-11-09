/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.bo;

/**
 *
 * @author EAFIT
 */
public class Balasto extends Elemento {

    int balastNumber;
    int level;
    int activation;
    String name;
    int dir;
    int min;
    int max;
    int ft;
    int fr;
    int lf;
    int lx;
    int pot;
    /**
     * Grupos afectados son los grupos en los cuales se encuentra incluido el
     * balasto actual.
     */
    private int[] gruposAfectados;
    /**
     * Escenas afectadas son las escenas en las cuales se encuentra incluido el
     * balasto actual.
     */
    private int[] escenasAfectadas;
    /**
     * Nivel escenas son un arreglo de valores en los cuales se reflejan los
     * niveles del balasto en las escenas correspondientes.
     */
    private int[] nivelesEscenas;

    public Balasto() {
    }

    public Balasto(int level, int activation, String name, int dir, int min, int max, int ft, int fr, int lf, int lx, int pot) {
        this.level = level;
        this.activation = activation;
        this.name = name;

        this.dir = dir;
        this.min = min;
        this.max = max;
        this.ft = ft;
        this.fr = fr;
        this.lf = lf;
        this.lx = lx;
        this.pot = pot;
    }

    public Balasto(int balastNumber, int level, int activation, String name, int dir, int min, int max, int ft, int fr, int lf, int lx, int pot) {
        this.balastNumber = balastNumber;
        this.level = level;
        this.activation = activation;
        this.name = name;

        this.dir = dir;
        this.min = min;
        this.max = max;
        this.ft = ft;
        this.fr = fr;
        this.lf = lf;
        this.lx = lx;
        this.pot = pot;


    }

    /**
     * Constructor básico para iniciar un balasto solo con el nombre la
     * dirección y el numero de balasto.
     *
     * @param balastNumber Numero con el que será identificado el balasto
     * @param name Nombre del balasto
     * @param dir Dirección con la que se identificará el balasto. Se usa
     * principalmente para el cambio de dirección cuando se quiere cambiar la
     * dirección del balasto en la configuración del balasto. Típicamente viene
     * con la misma dirección y el mismo numero de balasto.
     *
     */
    public Balasto(int balastNumber, String name, int dir) {
        this.balastNumber = balastNumber;
        this.name = name;
        this.dir = dir;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getFr() {
        return fr;
    }

    public void setFr(int fr) {
        this.fr = fr;
    }

    public int getFt() {
        return ft;
    }

    public void setFt(int ft) {
        this.ft = ft;
    }

    public int getLf() {
        return lf;
    }

    public void setLf(int lf) {
        this.lf = lf;
    }

    public int getLx() {
        return lx;
    }

    public void setLx(int lx) {
        this.lx = lx;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getActivation() {
        return activation;
    }

    public void setActivation(int activation) {
        this.activation = activation;
    }

    public int getBalastNumber() {
        return balastNumber;
    }

    public void setBalastNumber(int balastNumber) {
        this.balastNumber = balastNumber;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getGruposAfectados() {
        return gruposAfectados;
    }

    public void setGruposAfectados(int[] gruposAfectados) {
        this.gruposAfectados = gruposAfectados;
    }

    public int[] getEscenasAfectadas() {
        return escenasAfectadas;
    }

    public void setEscenasAfectadas(int[] escenasAfectadas) {
        this.escenasAfectadas = escenasAfectadas;
    }

    public int[] getNivelesEscenas() {
        return nivelesEscenas;
    }

    public void setNivelesEscenas(int[] nivelesEscenas) {
        this.nivelesEscenas = nivelesEscenas;
    }
    //</editor-fold>
}
