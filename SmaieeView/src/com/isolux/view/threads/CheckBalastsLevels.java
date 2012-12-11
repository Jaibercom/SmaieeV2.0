/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.view.threads;

import com.isolux.bo.Balasto;
import com.isolux.dao.jmodbus.BalastoDAOJmodbus;
import com.isolux.view.PpalView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EAFIT
 */
public class CheckBalastsLevels extends Thread{
    
    private PpalView ppalView;
    private boolean stopBalast;
    private ArrayList<Integer> selectedAreaBalasts;
    
    public CheckBalastsLevels(PpalView ppalView, ArrayList<Integer> selectedAreaBalasts){
        this.ppalView = ppalView;
        this.stopBalast = false;
        this.selectedAreaBalasts = selectedAreaBalasts;
    }
    
    public CheckBalastsLevels(){
        this.stopBalast = false;
    }
    
    public void run () {
        try {
            while (!this.stopBalast) {
                DefaultTableModel balastsTable = (DefaultTableModel) ppalView.getjTable1().getModel();

                HashMap<Integer, Balasto> balasts = new BalastoDAOJmodbus(ppalView.getDao()).getBalastsLevels();
//                Set<Integer> balastsNumbers = balasts.keySet();

                int rowCount = balastsTable.getRowCount();
                if (rowCount > 0) {
                    for (int i = rowCount - 1; i >= 0; i--) {
                        balastsTable.removeRow(i);
                    }

                }
                

                for (Integer object : selectedAreaBalasts) {
                    Balasto readed = balasts.get(object);
                    Object nuevo[] = {readed.getBalastNumber()+1, readed.getName(), readed.getLevel()};
                    balastsTable.addRow(nuevo);
                }

//                System.out.println("Mostrando niveles...");
                Thread.sleep(5 * 1000L);
            }
        } catch (Exception e) {
            Logger.getLogger(CheckBalastsLevels.class.getName()).log(Level.SEVERE, "Error en el control de tiempo real", e);
        }
    }
    
    /**
     * Stop balast checking.
     */
    public void stopChecking(){
        this.stopBalast = true;
        System.out.println("Se paro el monitoreo");
    }

    
    
    //GETTERS AND SETTERS.
    public ArrayList<Integer> getSelectedAreaBalasts() {
        return selectedAreaBalasts;
    }

    public void setSelectedAreaBalasts(ArrayList<Integer> selectedAreaBalasts) {
        this.selectedAreaBalasts = selectedAreaBalasts;
    }

    public boolean isStopBalast() {
        return stopBalast;
    }

    public void setStopBalast(boolean stopBalast) {
        this.stopBalast = stopBalast;
    }

    public PpalView getPpalView() {
        return ppalView;
    }

    public void setPpalView(PpalView ppalView) {
        this.ppalView = ppalView;
    }
    
    
}
