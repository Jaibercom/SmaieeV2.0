/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.bo;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class Fecha {
    private int dia;
    private int diaSemana;
    private int mes; 
    private int ano;
    private int h;
    private int m;
    private int s;
    
    public Fecha(){
        
    }
    
    public Fecha(int dia, int diaSemana, int mes, int ano, int h, int m){
        this.dia = dia;
        this.diaSemana = diaSemana;
        this.mes = mes;
        this.ano = ano;
        this.h = h;
        this.m = m;
    }
    
    
    public Fecha(int dia, int diaSemana, int mes, int ano, int h, int m, int s){
        this.dia = dia;
        this.diaSemana = diaSemana;
        this.mes = mes;
        this.ano = ano;
        this.h = h;
        this.m = m;
        this.s = s;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }
    
    
    
}
