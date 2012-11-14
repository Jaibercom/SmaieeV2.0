/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.modbus;

import com.isolux.dao.ConexionWorker;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.wimpi.modbus.net.TCPMasterConnection;


/**
 *
 * @author Sebastian Rodriguez
 */
public class ConectionManager {

    private TCPMasterConnection con = null;  //the connection
    private InetAddress addr = null;
    private String ip;
    private int port=502;
    private static ConectionManager INSTANCE = null;

    private ConectionManager(String ip) {

        try {
            this.ip = ip;
            this.setConection();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConectionManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error conectandose al host. " + ex.getMessage());
        }
    }

    private void setConection() throws UnknownHostException {
        addr = InetAddress.getByName(ip);
        con = new TCPMasterConnection(addr);
        con.setPort(port);
        con.setTimeout(2000);
    }

    public static ConectionManager getInstancia(String ip) {

        if (INSTANCE == null) {
            INSTANCE = new ConectionManager(ip);
            return INSTANCE;
        } else {
            
            return INSTANCE;
        }

    }

    public boolean conectar() throws Exception {

        if (!con.isConnected()) {
            con.connect();
            return true;
        }
        return false;

    }

    public boolean close() throws Exception {
        if (con.isConnected()) {
            con.close();
            return true;
        }
        return false;

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public boolean estaConectado(){
        return con.isConnected();
    }
    
    
    public static void main(String args[]) {
        try {
//            boolean conectar = ConectionManager.getInstancia("192.168.1.121").conectar();
            System.out.println("Verificando la conexion");
            ConexionWorker worker = new ConexionWorker();
            worker.execute();
//            worker.run();
            System.out.println("termino el programa pincipal ");
            Thread.sleep(60000);
          
        } catch (Exception ex) {
            Logger.getLogger(ConectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}