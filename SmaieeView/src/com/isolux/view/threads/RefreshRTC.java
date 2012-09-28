/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.view.threads;

import com.isolux.dao.jmodbus.ConfiguracionDAOJmodbus;
import com.isolux.view.PpalView;
import java.util.Date;
import javax.swing.JTable;

/**
 *
 * @author EAFIT
 */
public class RefreshRTC extends Thread{
    
    private boolean stopRTCRefreshing;
    private PpalView ppalView;
    
    public RefreshRTC(PpalView ppalView){
        this.stopRTCRefreshing = false;
        this.ppalView = ppalView;
    }
    
    public RefreshRTC(){
        this.stopRTCRefreshing = false;
    }
    
    public void run () {
        try {
            while (!this.stopRTCRefreshing) {
                ConfiguracionDAOJmodbus fechaDao = new ConfiguracionDAOJmodbus(ppalView.getDao());
                fechaDao.loadData();

                Date date = fechaDao.getDate();
                String time = fechaDao.getTime();

                ppalView.getjDateChooser1().setDate(date);
                ppalView.getjFormattedTextField1().setText(time);
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
    
    /**
     * Stop balast checking.
     */
    public void stopChecking(){
        this.stopRTCRefreshing = true;
        System.out.println("Se paro el monitoreo");
    }

    
    //GETTERS AND SETTERS.
    public PpalView getPpalView() {
        return ppalView;
    }

    public void setPpalView(PpalView ppalView) {
        this.ppalView = ppalView;
    }

    public boolean isStopRTCRefreshing() {
        return stopRTCRefreshing;
    }

    public void setStopRTCRefreshing(boolean stopRTCRefreshing) {
        this.stopRTCRefreshing = stopRTCRefreshing;
    }
    
}