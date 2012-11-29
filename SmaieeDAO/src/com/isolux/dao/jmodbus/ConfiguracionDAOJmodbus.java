/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.bo.*;
import com.isolux.dao.modbus.DAO4j;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import com.isolux.properties.MapaDeMemoria;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author EAFIT
 */
public class ConfiguracionDAOJmodbus {

    //date
    private short dia = 0;
    private short diaSemana = 0;
    private short mes = 0;
    private short anho = 0;
    private short h = 0;
    private short m = 0;
    private short s = 0;
    //rol
    private String rol = "";
    //network
    private String type = "";
    private String ip = "";
    private String mask = "";
    private String gateway = "";
    private String port = "";
    private static DAOJmodbus dao;

    //CONSTRUCTOR
    public ConfiguracionDAOJmodbus(DAOJmodbus dao) {
        this.dao = dao;
    }

    /**
     * Write a single register.
     */
    public static void setSingleReg(int pos, int mode) throws Exception {
        int[] values = {mode};
        dao.setRegValue(pos, values);
    }

    /**
     * Get a single register.
     */
    public static int[] getSingleReg(int pos) {
        return dao.getRegValue(pos, 1);
    }

    /**
     * Saves the date
     *
     * @param fecha
     */
    public void saveDate(Fecha fecha) {
        try {

            int dateOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.date"));
            int[] dateArray = new int[Integer.parseInt(PropHandler.getProperty("memory.date.size"))];

            System.out.println("SAVING DATE-TIME");
            //MODO
            setSingleReg(0, 1);

            dateArray[0] = fecha.getDiaSemana();
            dateArray[1] = fecha.getDia();
            dateArray[2] = fecha.getMes();
            dateArray[3] = fecha.getAno();
            dateArray[4] = fecha.getH();
            dateArray[5] = fecha.getM();
            dateArray[6] = fecha.getS();

            //Save array
            dao.setRegValue(dateOffset, dateArray);

            //Confirmacion
            setSingleReg(1, 1);

            //MODO
            setSingleReg(0, 0);
            System.out.println("DATE-TIME saved");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the rol of the card.
     *
     * @param rol
     */
    public void saveRol(String rol) throws Exception {
//        DAO4j dao = new DAO4j();
        int rolOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.rol"));

        //MODO
        setSingleReg(0, 1);

        setSingleReg(rolOffset, Integer.parseInt(rol));

        //MODO
        setSingleReg(0, 0);
    }

    /**
     * Save network configuration.
     *
     * @param type
     * @param ip
     * @param mask
     * @param gateway
     * @param port
     */
    public void saveNetworkConf(String type, String ip, String mask, String gateway, String port) {
        try {

            int networkOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.network"));
            int[] network = new int[Integer.parseInt(PropHandler.getProperty("memory.network.size"))];


            System.out.println("SAVING NETWORK CONF");

            //MODO
            setSingleReg(0, 1);

            //TYPE - DHCP/STATIC
            network[0] = Integer.parseInt(type);


            //STATIC IP

            String[] ipArray = ip.split("\\.");
            network[1] = Integer.parseInt(ipArray[0]);
            network[2] = Integer.parseInt(ipArray[1]);
            network[3] = Integer.parseInt(ipArray[2]);
            network[4] = Integer.parseInt(ipArray[3]);

            //MASK
            String[] maskArray = mask.split("\\.");
            network[5] = Integer.parseInt(maskArray[0]);
            network[6] = Integer.parseInt(maskArray[1]);
            network[7] = Integer.parseInt(maskArray[2]);
            network[8] = Integer.parseInt(maskArray[3]);

            //GATEWAY
            String[] gatewayArray = gateway.split("\\.");
            network[9] = Integer.parseInt(gatewayArray[0]);
            network[10] = Integer.parseInt(gatewayArray[1]);
            network[11] = Integer.parseInt(gatewayArray[2]);
            network[12] = Integer.parseInt(gatewayArray[3]);

            //PORT
            network[13] = Integer.parseInt(port);
            //Save array
            boolean grabado = dao.setRegValue(networkOffset, network);
//            System.out.println("Grabo la configuracion en el buffer?: " + grabado);

            
            String ipTarjeta=com.isolux.properties.PropHandler.getProperty("general.ip");
            String maskTarjeta=com.isolux.properties.PropHandler.getProperty("general.ip.mask");
            String gatwayTarjeta=com.isolux.properties.PropHandler.getProperty("general.ip.gateway");
            String portTarjeta=com.isolux.properties.PropHandler.getProperty("general.port");

//            if (ip == null ? com.isolux.properties.PropHandler.getProperty("general.ip") != null : !ip.equals(com.isolux.properties.PropHandler.getProperty("general.ip"))) {
            if (ip !=ipTarjeta ||mask!=maskTarjeta||gateway!=gatwayTarjeta||port!=portTarjeta) {
                //cambiamos la ip
                setSingleReg(1, 3);

                //CONFIRM
                setSingleReg(1, 1);


                //MODO
                setSingleReg(0, 0);

                Logger.getLogger(ConfiguracionDAOJmodbus.class.getName()).log(Level.INFO, "Se cambió la ip correctamente");

//                System.out.println("NETWORK CONF saved");

                PropHandler.setProperty("general.ip", ip);
                PropHandler.setProperty("general.ip.mask", mask);
                PropHandler.setProperty("general.ip.gateway", gateway);
                PropHandler.setProperty("general.port", port);


                Logger.getLogger(ConfiguracionDAOJmodbus.class.getName()).log(Level.INFO, "Se grabó la informacion de red correctamente");

                System.out.println("NETWORK CONF saved");

                JOptionPane.showMessageDialog(null, "Debe reiniciar el software para que el cambio de la ip sea exitoso.\nSe cerrará el programa.", "Cambio de la ip", JOptionPane.WARNING_MESSAGE);
                System.exit(1);
            }
        } catch (Exception e) {
            Logger.getLogger(ConfiguracionDAOJmodbus.class.getName()).log(Level.SEVERE, "Error grabando la información de red", e);
        }
    }

    /**
     * Loads the information from the local memory file.
     */
    public void loadData() {
        try {

//            System.out.println("READING GENERAL CONF");

            //DATE
            int dateOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.date"));
            int[] dateArray = dao.getRegValue(dateOffset, Integer.parseInt(PropHandler.getProperty("memory.date.size")));

            //dia de la semana
            diaSemana = new Integer(dateArray[0]).shortValue();

            //dia del mes.
            dia = new Integer(dateArray[1]).shortValue();

            //mes
            mes = new Integer(dateArray[2]).shortValue();

            //año
            anho = new Integer(dateArray[3]).shortValue();

            //hora
            h = new Integer(dateArray[4]).shortValue();

            //minutos
            m = new Integer(dateArray[5]).shortValue();

            //segundos.
            s = new Integer(dateArray[6]).shortValue();



            //ROL
            int rolOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.rol"));
            rol = String.valueOf(getSingleReg(rolOffset)[0]);


            //NETWORK
            int networkOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.network"));

            //READ NETWORK
            setSingleReg(2, 1);
            int[] networkArray = dao.getRegValue(networkOffset, Integer.parseInt(PropHandler.getProperty("memory.network.size")));

            type = String.valueOf(networkArray[0]);

            //ip
            String ip1 = String.valueOf(networkArray[1]);
            networkOffset++;
            String ip2 = String.valueOf(networkArray[2]);
            networkOffset++;
            String ip3 = String.valueOf(networkArray[3]);
            networkOffset++;
            String ip4 = String.valueOf(networkArray[4]);
            networkOffset++;
            ip = ip1 + "." + ip2 + "." + ip3 + "." + ip4;


            //mask
            String mask1 = String.valueOf(networkArray[5]);
            networkOffset++;
            String mask2 = String.valueOf(networkArray[6]);
            networkOffset++;
            String mask3 = String.valueOf(networkArray[7]);
            networkOffset++;
            String mask4 = String.valueOf(networkArray[8]);
            networkOffset++;
            mask = mask1 + "." + mask2 + "." + mask3 + "." + mask4;


            //gateway
            String gateway1 = String.valueOf(networkArray[9]);
            networkOffset++;
            String gateway2 = String.valueOf(networkArray[10]);
            networkOffset++;
            String gateway3 = String.valueOf(networkArray[11]);
            networkOffset++;
            String gateway4 = String.valueOf(networkArray[12]);
            networkOffset++;
            gateway = gateway1 + "." + gateway2 + "." + gateway3 + "." + gateway4;


            //port
            port = String.valueOf(networkArray[13]);

//            System.out.println("GENERAL CONF readed");

        } catch (Exception e) {
            System.out.println("No se pudo cargar la fecha desde el archivo local.");
        }
    }

    /*
     * GETS
     */
    public Date getDate() {
        Date fecha = null;
        try {

            String storedDate =
                    String.valueOf(dia) + "/"
                    + String.valueOf(mes) + "/"
                    + String.valueOf(anho + 1900);
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(storedDate);

            Calendar calen = Calendar.getInstance();
            calen.set(anho, mes - 1, dia);
            diaSemana = new Integer(calen.get(Calendar.DAY_OF_WEEK)).shortValue();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fecha;
    }

    public String getTime() {
        String time = null;
        try {

            String hour = String.valueOf(h).length() == 1 ? "0" + String.valueOf(h) : String.valueOf(h);
            String minute = String.valueOf(m).length() == 1 ? "0" + String.valueOf(m) : String.valueOf(m);
            String second = String.valueOf(s).length() == 1 ? "0" + String.valueOf(s) : String.valueOf(s);
            time = hour + ":" + minute + ":" + second;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * Save program to the flash memory.
     */
    public static void saveToFlash() {
        System.out.println("Guardando en flash");
        try {
            setSingleReg(0, 0);// volvemos a poner el modo run
            setSingleReg(0, 1);// ponemos en modo configuracion
            setSingleReg(1, 10);// escribimos el valor 
            setSingleReg(0, 0);// volvemos a poner el modo run
            System.out.println("Guardado");
            JOptionPane.showMessageDialog(null, "Guardada la información en la flash exitósamente");
        } catch (Exception ex) {
            System.out.println("Excepcion al guardar en Flash");
            Logger.getLogger(ConfiguracionDAOJmodbus.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error guardando la informacion en flash. " + ex.getLocalizedMessage());

        }

    }

    /**
     * Save program to the flash memory.
     */
    public static void readFromFlash() {
        System.out.println("Leyendo de flash");
        try {


            setSingleReg(1, 11);
        } catch (Exception e) {
        }
        System.out.println("Leido");
    }

    /**
     * Erase the flash memory.
     */
    public static void eraseMemory() {
        System.out.println("Borrando la memoria");
        try {
            setSingleReg(1, 7);
        } catch (Exception e) {
        }
        System.out.println("Borrada");
    }

    public String getRol() {
        return rol;
    }

    public String getType() {
        return type;
    }

    public String getIp() {
        return ip;
    }

    public String getMask() {
        return mask;
    }

    public String getGateway() {
        return gateway;
    }

    public String getPort() {
        return port;
    }
    //TEST
//    public static void main(String [] args){
//        Calendar calen = Calendar.getInstance();
//        calen.set(2011, 10, 13);
//        System.out.println(calen.getTime());
//        System.out.println(new Integer(calen.get(Calendar.DAY_OF_WEEK)).shortValue());
//    }
}
