package com.isolux.bo;

import com.isolux.properties.PropHandler;

/**
 *
 * @author Juan Diego Toro Cano.
 */
public class Evento extends Elemento{
    int numeroEvento;
    String nombre;
    int porFechaODias;
    int dia;
    int mes;
    int anho;
    int hora;
    int minuto;
    int diaYrepetir;
    int[] nivelBalasto;
    int[] balastosAfectados;
    int[] nivelGrupo;
    int[] gruposAfectados;
    int[] escenasAfectadas;
    int tipoSalida;

    public Evento() {
        int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
        int numGrupos = Integer.parseInt(PropHandler.getProperty("group.max.number"));
        int numEscenas = Integer.parseInt(PropHandler.getProperty("scene.max.number"));
        int tamConfDiaFecha = Integer.parseInt(PropHandler.getProperty("event.dia.conf"));
        nivelBalasto = new int[numBalastos];
        balastosAfectados = new int[numBalastos];
        nivelGrupo = new int[numGrupos];
        gruposAfectados = new int[numGrupos];
        escenasAfectadas = new int[numEscenas];
        diaYrepetir = 0;
        tipoSalida = 0;
    }
    
    public Evento(int numeroEvento, String nombre, int porFechaODias, int dia, int mes, int anho, int hora, int minuto, int diaYrepetir, int[] nivelBalasto, int[] balastosAfectados, int[] nivelGrupo, int[] gruposAfectados, int[] escenasAfectadas, int tipoSalida) {
        this.numeroEvento = numeroEvento;
        this.nombre = nombre;
        this.porFechaODias = porFechaODias;
        this.dia = dia;
        this.mes = mes;
        this.anho = anho;
        this.hora = hora;
        this.minuto = minuto;
        this.diaYrepetir = diaYrepetir;
        this.nivelBalasto = nivelBalasto;
        this.balastosAfectados = balastosAfectados;
        this.nivelGrupo = nivelGrupo;
        this.gruposAfectados = gruposAfectados;
        this.escenasAfectadas = escenasAfectadas;
        this.tipoSalida = tipoSalida;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getDiaYrepetir() {
        return diaYrepetir;
    }

    public void setDiaYrepetir(int diaYrepetir) {
        this.diaYrepetir = diaYrepetir;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroEvento() {
        return numeroEvento;
    }

    public void setNumeroEvento(int numeroEvento) {
        this.numeroEvento = numeroEvento;
    }

    public int getPorFechaODias() {
        return porFechaODias;
    }

    public void setPorFechaODias(int porFechaODias) {
        this.porFechaODias = porFechaODias;
    }

    public int[] getBalastosAfectados() {
        return balastosAfectados;
    }

    public void setBalastosAfectados(int[] balastosAfectados) {
        this.balastosAfectados = balastosAfectados;
    }

    public int[] getEscenasAfectadas() {
        return escenasAfectadas;
    }

    public void setEscenasAfectadas(int[] escenasAfectadas) {
        this.escenasAfectadas = escenasAfectadas;
    }

    public int[] getGruposAfectados() {
        return gruposAfectados;
    }

    public void setGruposAfectados(int[] gruposAfectados) {
        this.gruposAfectados = gruposAfectados;
    }

    public int[] getNivelBalasto() {
        return nivelBalasto;
    }

    public void setNivelBalasto(int[] nivelBalasto) {
        this.nivelBalasto = nivelBalasto;
    }

    public int[] getNivelGrupo() {
        return nivelGrupo;
    }

    public void setNivelGrupo(int[] nivelGrupo) {
        this.nivelGrupo = nivelGrupo;
    }

    public int getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(int tipoSalida) {
        this.tipoSalida = tipoSalida;
    }

}
