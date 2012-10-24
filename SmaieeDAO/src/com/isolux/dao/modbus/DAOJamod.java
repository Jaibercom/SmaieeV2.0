/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.modbus;

import com.isolux.dao.properties.PropHandler;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.procimg.SimpleRegister;
import net.wimpi.modbus.util.*;
import net.wimpi.modbus.facade.*;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleDigitalOut;

/**
 *
 * @author Juan Toro
 */
public class DAOJamod {

    /*
     * GENERAL OPERATIONS
     */
   
    

    /*
     * REG OPERATIONS
     */
    /**
     * Sets a single register value.
     *
     * @param offset
     * @param value
     */
    public static void setRegValue(int offset, int value) {
        WriteSingleRegisterRequest request = null;
        WriteSingleRegisterResponse response = null;
        ModbusTCPTransaction trans = null; //the transaction
        TCPMasterConnection con = null;  //the connection
        InetAddress addr = null; //the address
        SimpleRegister sr = null;

        int port = Integer.parseInt(PropHandler.getProperty("general.port"));
        int ref = offset;

        try {
            //1. Set the parameters.
            String astr = PropHandler.getProperty("general.ip");
            addr = InetAddress.getByName(astr);
            con = new TCPMasterConnection(addr);
            sr = new SimpleRegister(value);

            //2. Open the connection	
            con.setPort(port);
            con.setTimeout(3000);
            con.connect();
//            System.out.println("Connected... " + port + " " + ref);

            //3. Prepare the request and the response.
            request = new WriteSingleRegisterRequest(ref, sr);
            response = new WriteSingleRegisterResponse();

            //4. Prepare the transaction.
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(request);

            //5. Excecute the transaction.
            trans.setRetries(0);
            trans.execute();
            response = (WriteSingleRegisterResponse) trans.getResponse();
            System.out.println("The value in " + response.getReference() + " is: " + response.getRegisterValue());


            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //6. Close the connection.
            con.close();
        }
    }

    /**
     * Sets a single register value.
     *
     * @param offset
     * @param value
     */
    public static void setRegArray(int offset, int value) {
        WriteSingleRegisterRequest request = null;
        WriteSingleRegisterResponse response = null;
        ModbusTCPTransaction trans = null; //the transaction
        TCPMasterConnection con = null;  //the connection
        InetAddress addr = null; //the address
        SimpleRegister sr = null;

        int port = Integer.parseInt(PropHandler.getProperty("general.port"));
        int ref = offset;

        try {
            //1. Set the parameters.
            String astr = PropHandler.getProperty("general.ip");
            addr = InetAddress.getByName(astr);
            con = new TCPMasterConnection(addr);
            sr = new SimpleRegister(value);

            //2. Open the connection	
            con.setPort(port);
            con.setTimeout(20000);
            con.connect();
            System.out.println("Connected... " + port + " " + ref);

            //3. Prepare the request and the response.
            request = new WriteSingleRegisterRequest(ref, sr);
            response = new WriteSingleRegisterResponse();

            //4. Prepare the transaction.
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(request);

            //5. Excecute the transaction.
            trans.execute();
            response = (WriteSingleRegisterResponse) trans.getResponse();
            System.out.println("The value is: " + response.getRegisterValue());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //6. Close the connection.
            con.close();
        }
    }

    /**
     * Gets a single register value.
     *
     * @param offset
     */
    //Function 03
    public static void getRegValue(int offset) {
        ModbusTCPMaster master = null;

        try {
            master = new ModbusTCPMaster(PropHandler.getProperty("general.ip"),
                    Integer.parseInt(PropHandler.getProperty("general.port")));
            master.connect();

            Register[] registers = master.readMultipleRegisters(offset, 1);
            Register register = registers[0];
            System.out.println("Valor: " + register.getValue());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            master.disconnect();
        }
    }

    //Function 04
    public static void getRegValue2(int offset) {
        ModbusTCPMaster master = null;
        int port = Integer.parseInt(PropHandler.getProperty("general.port"));

        try {
            master = new ModbusTCPMaster(PropHandler.getProperty("general.ip"), port);
            master.connect();

            InputRegister[] registers = master.readInputRegisters(offset, 1);
            InputRegister register = registers[0];
            System.out.println("Valor: " + register.getValue());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            master.disconnect();
        }
    }

    public static void setRegValue2(int offset, int value) {
        ModbusTCPMaster master = null;
        int port = Integer.parseInt(PropHandler.getProperty("general.port"));

        try {
            master = new ModbusTCPMaster(PropHandler.getProperty("general.ip"), port);
            master.connect();

            SimpleRegister reg = new SimpleRegister(value);
            master.writeSingleRegister(offset, reg);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            master.disconnect();
        }
    }

    //Function 04
    public static void setRegValue3(int offset, int value) {
        ModbusTCPMaster master = null;
        int port = Integer.parseInt(PropHandler.getProperty("general.port"));

        try {
            master = new ModbusTCPMaster(PropHandler.getProperty("general.ip"), port);
            master.connect();

            SimpleRegister reg = new SimpleRegister(value);
            SimpleRegister[] registers = {reg};
            master.writeMultipleRegisters(offset, registers);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            master.disconnect();
        }
    }

    /**
     * Get a register
     *
     * @param host
     * @param offset
     * @throws Exception
     */
    public void getRegisters(int offset) throws Exception {
        InetAddress address = InetAddress.getByName(PropHandler.getProperty("general.ip"));
        TCPMasterConnection connection = new TCPMasterConnection(address);
        connection.setPort(Integer.parseInt(PropHandler.getProperty("general.port")));
        connection.connect();

        ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(offset, 1);
        ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse) executeTransaction(connection, request);
        Register[] registers = response.getRegisters();

        for (int i = 0; i < registers.length; i++) {
            System.out.println("Valor: " + registers[i].getValue());
        }
    }

    private static ModbusResponse executeTransaction(TCPMasterConnection connection, ModbusRequest request)
            throws ModbusIOException, ModbusSlaveException, ModbusException {

        ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
        transaction.setRequest(request);
        transaction.execute();
        return transaction.getResponse();
    }

    /*
     * COIL OPERATIONS
     */
    /**
     * Get single coil value
     *
     * @param offset
     */
    public static void getCoilValue(int offset) {
        ModbusTCPMaster master = null;
        int port = Integer.parseInt(PropHandler.getProperty("general.port"));

        try {
            master = new ModbusTCPMaster(PropHandler.getProperty("general.ip"), port);
            master.connect();
            BitVector coil = master.readCoils(offset, 1);
            boolean valor = coil.getBit(0);
            System.out.println("Valor de " + offset + ": " + valor);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            master.disconnect();
        }
    }

    /**
     * Set single coil value.
     *
     * @param offset
     * @param value
     */
    public static void setCoilValue(int offset, boolean value) {

        WriteCoilRequest request = null;
        WriteCoilResponse response = null;

        ModbusTCPTransaction trans = null; //the transaction
        TCPMasterConnection con = null;  //the connection
        InetAddress addr = null; //the address
        SimpleDigitalOut sdo = null;

        int port = Integer.parseInt(PropHandler.getProperty("general.port"));
        int ref = offset;

        try {
            //1. Set the parameters.
            String astr = PropHandler.getProperty("general.ip");
            addr = InetAddress.getByName(astr);
            con = new TCPMasterConnection(addr);

            sdo = new SimpleDigitalOut(value);

            //2. Open the connection	
            con.setPort(port);
            con.connect();
            System.out.println("Connected... " + port + " " + ref);

            //65535

            //3. Prepare the request and the response.
            request = new WriteCoilRequest(ref, value);
            response = new WriteCoilResponse();

            //4. Prepare the transaction.
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(request);

            //5. Excecute the transaction.
            trans.execute();
            response = (WriteCoilResponse) trans.getResponse();
            System.out.println("The value is: " + response.getCoil());



        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //6. Close the connection.
            con.close();
        }

    }

    /**
     * Test the state of the connection.
     *
     * @return
     */
    public static boolean testConnection(String ip, int port) {
        boolean connectionState = false;

//        ModbusTCPMaster master = null;
//        try {
//            master = new ModbusTCPMaster(ip, port);
//            master.connect();
//            connectionState = true;
//        } catch (Exception ex) {
//            connectionState = false;
//            ex.printStackTrace();
//        } 
//        return connectionState;


        TCPMasterConnection con = null;  //the connection
        InetAddress addr = null; //the address

        try {
            //1. Set the parameters.
            String astr = ip;
            addr = InetAddress.getByName(astr);
            con = new TCPMasterConnection(addr);

            //2. Open the connection	
            con.setPort(port);
            con.setTimeout(2000);
            con.connect();

            connectionState = true;



//            con.close();
        } catch (Exception ex) {
            if (con.isConnected()) {
                con.close();
            }
            connectionState = false;
            ex.printStackTrace();

        } finally {
            //6. Close the connection.
            con.close();
        }

        return connectionState;
    }

    /*
     * Main test.
     */
    public static void main(String args[]) {
        //COILS
//        new SmaieeModMaster().setCoilValue("127.0.0.1", 6, true);
//        new SmaieeModMaster().getCoilValue("127.0.0.1", 6);

        //REGISTERS 
        // Holding register 40108 is addressed as register 006B hex (107 decimal). Page
//        new DAOJamod().setRegValue(0, 1);
//        new DAOJamod().getRegValue(6);
//        new DAOJamod().getRegValue2(6);

//        new DAOJamod().setRegValue3(6, 34);

        try {
            new DAOJamod().getRegValue2(500);
        } catch (Exception ex) {
            Logger.getLogger(DAOJamod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
//