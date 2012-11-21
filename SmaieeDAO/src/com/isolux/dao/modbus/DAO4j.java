/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.modbus;

import com.isolux.dao.properties.LocalMemoryHandler;
import com.isolux.dao.properties.PropHandler;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import java.net.*;
import net.wimpi.modbus.net.*;

import com.serotonin.modbus4j.ip.IpParameters;
import java.util.HashMap;
//import net.wimpi.modbus.facade.ModbusTCPMaster;
import net.sourceforge.jmodbus.ModbusTCPMaster;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.Register;

//JAMOD TEST
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.util.*;

/**
 *
 * @author Juan Toro
 */
public class DAO4j {

    
    /**
     * Gets a single register value.
     * @param offset 
     */
    public static int getRegValue(int offset) {
        ModbusMaster master = null;
        int value = 0;
//        TCPMasterConnection con = null; //the connection
        try {

            //MODBUS4J
            ModbusFactory factory = new ModbusFactory();
            IpParameters params = new IpParameters();
            params.setHost(PropHandler.getProperty("general.ip"));
            params.setPort(Integer.parseInt(PropHandler.getProperty("general.port")));

            master = factory.createTcpMaster(params, false);
            master.setTimeout(5000);
            master.setRetries(0);
            master.init();

            value = Integer.parseInt(master.getValue(1, RegisterRange.INPUT_REGISTER, offset, DataType.TWO_BYTE_INT_UNSIGNED).toString());
            
            //TEST
//            value = Integer.parseInt(master.getValue(1, RegisterRange.HOLDING_REGISTER, offset, DataType.TWO_BYTE_INT_UNSIGNED).toString());
            
//            System.out.println("Value of " + offset + ": " + value);
            master.destroy();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            master.destroy();
        }
        
        return value;
    }
    
    /**
     * Sets a single register value.
     * @param offset
     * @param value 
     */
    public static void setRegValue(int offset, Object value) {
//        System.out.println("Writting: " + offset + " - Value: " + value);
        ModbusMaster master = null;
        try {

            ModbusFactory factory = new ModbusFactory();
            IpParameters params = new IpParameters();
            params.setHost(PropHandler.getProperty("general.ip"));
            params.setPort(Integer.parseInt(PropHandler.getProperty("general.port")));

            master = factory.createTcpMaster(params, false);
            master.setTimeout(5000);
            master.setRetries(0);
            master.init();

            master.setValue(1, RegisterRange.HOLDING_REGISTER, offset, DataType.TWO_BYTE_INT_SIGNED, value);
            
            master.destroy();
        } catch (Exception ex) {
            ex.printStackTrace();
        }  finally {
            master.destroy();
        }
    }
    

    
    /*
     * Main test.
     */
    public static void main(String args[]) {
        
        //REGISTERS 
//        short tales = 234;
//        DAO4j.setRegValue(2000, 11);
//        new DAO4j().getRegValue(500);
        
        new DAO4j().getRegValue(500);
        

    }
}