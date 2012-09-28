/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.bo;

/**
 *
 * @author EAFIT
 */
public class Balasto {
    
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

    public Balasto(){
        
    }
    
    
    public Balasto(int level, int activation, String name, int dir, int min, int max, int ft, int fr, int lf, int lx, int pot){
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
    
    public Balasto(int balastNumber, int level, int activation, String name, int dir, int min, int max, int ft, int fr, int lf, int lx, int pot){
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
    
    
    
}
