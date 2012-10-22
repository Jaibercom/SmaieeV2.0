/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.view.threads;

import com.isolux.view.PpalView;




/**
 *
 * @author EAFIT
 */
public class ThreadManager {

    public static final char RTC_REFRESHING = 'T';
    public static final char REAL_TIME = 'R';
    
    
    private RefreshRTC rtcRefreshing;
    private CheckBalastsLevels realTime;
    private PpalView ppalView;
    
    
    public ThreadManager(){
        this.rtcRefreshing = new RefreshRTC();
        this.realTime = new CheckBalastsLevels();
    }
    
    public ThreadManager(PpalView ppalView){
        this.ppalView = ppalView;
        this.rtcRefreshing = new RefreshRTC();
        this.realTime = new CheckBalastsLevels();
        
        this.rtcRefreshing.setPpalView(ppalView);
        this.realTime.setPpalView(ppalView);
    }
    
    
    /**
     * Stops all current threads.
     */
    public void stopAllCurrentThreads(){
        stopThread(REAL_TIME);
        stopThread(RTC_REFRESHING);
        System.out.println("parados todos los hilos");
    }
    
    /**
     * Starts all threads.
     */
    public void startAllCurrentThreads(){
        startThread(REAL_TIME);
        startThread(RTC_REFRESHING);
    }
    
    /**
     * Starts the specified thread.
     * @param thread 
     */
    public void startThread(char thread){
        
        switch(thread){
            case RTC_REFRESHING:
                Thread.State stateRtc = rtcRefreshing.getState();
                
                if (stateRtc.equals(Thread.State.NEW)) {
                    Thread showRtc = rtcRefreshing;
                    showRtc.start();
                }
                break;
                
            case REAL_TIME:
                Thread.State stateReal = realTime.getState();
                
                if (stateReal.equals(Thread.State.NEW)) {
                    Thread showReal = realTime;
                    showReal.start();
                    break;
                }
        }
        System.out.println("Comenzado el hilo "+thread);
    }
    
    public void startThreadIfTerminated(char thread){
        
        switch(thread){
            case RTC_REFRESHING:
                Thread.State stateRtc = rtcRefreshing.getState();
                
                if (stateRtc.equals(Thread.State.NEW) || stateRtc.equals(Thread.State.TERMINATED)) {
                    rtcRefreshing = new RefreshRTC(ppalView);
                    Thread showRtc = rtcRefreshing;
                    showRtc.start();
                }
                break;
                
            case REAL_TIME:
                Thread.State stateReal = realTime.getState();
                
                if (stateReal.equals(Thread.State.NEW) || stateReal.equals(Thread.State.TERMINATED)) {
                    realTime = new CheckBalastsLevels();
                    realTime.setPpalView(ppalView);
                    Thread showReal = realTime;
                    showReal.start();
                    break;
                }
        }
    }
    
    /**
     * Stops the specified thread.
     * @param thread 
     */
    public void stopThread(char thread){
        
        switch(thread){
            case RTC_REFRESHING:
                Thread.State stateRtc = rtcRefreshing.getState();
                if (!stateRtc.equals(Thread.State.TERMINATED)) {
                    rtcRefreshing.setStopRTCRefreshing(true);
                    rtcRefreshing.interrupt();
                }
                System.out.println("Parado el Hilo RTC");
                break;
                
            case REAL_TIME:
                Thread.State stateReal = realTime.getState();
                if (!stateReal.equals(Thread.State.TERMINATED)) {
                    realTime.setStopBalast(true);
                    realTime.interrupt();
                    
                }
                System.out.println("Parado el hilo REAL_Time");
                break;
        }
    }
    
    
    
    
    //GETTERS AND SETTERS.

    public CheckBalastsLevels getRealTime() {
        return realTime;
    }

    public void setRealTime(CheckBalastsLevels realTime) {
        this.realTime = realTime;
    }

    public RefreshRTC getRtcRefreshing() {
        return rtcRefreshing;
    }

    public void setRtcRefreshing(RefreshRTC rtcRefreshing) {
        this.rtcRefreshing = rtcRefreshing;
    }
    
   
    
}
