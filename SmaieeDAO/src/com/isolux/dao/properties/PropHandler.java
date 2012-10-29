package com.isolux.dao.properties;

import com.isolux.dao.jmodbus.BalastoDAOJmodbus;
import com.isolux.dao.jmodbus.BalastoDAOJmodbus1;
import com.isolux.dao.jmodbus.EntradaDAOJmodbus;
import com.isolux.dao.jmodbus.EscenaDAOJmodbus;
import com.isolux.dao.jmodbus.EventoDAOJmodbus;
import com.isolux.dao.jmodbus.GrupoDAOJmodbus;
import com.isolux.dao.jmodbus.OperacionesDaoJModbus;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.facadeBack.TiempoRealDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class PropHandler {

    /**
     * Get values from properies files.
     * 
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        String property = null;
        File archivo = new File(Constants.PROP_FILE);
        Properties properties = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(archivo);
            properties.load(stream);
            property = properties.getProperty(key);
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar el archivo especificado!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return property != null ? property : null;
    }

    /**
     * Sets a value from a property file.
     * 
     * @param key
     * @param value
     */
    public static void setProperty(String key, String value) {
        
        String file = Constants.PROP_FILE;
//        String file = Constants.PROP_FILE;
        
        File archivo = new File(file);
        if (!archivo.isFile()) {
            try {
                archivo.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(PropHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        Properties properties = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(archivo);
            properties.load(stream);

            FileOutputStream fos = new FileOutputStream(archivo);
            properties.setProperty(key, value);
            properties.store(fos, null);
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar el archivo especificado!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void putProperty(String key, String value){
        File archivo = new File(Constants.PROP_FILE);
        Properties properties = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(archivo);
            properties.load(stream);

            FileOutputStream fos = new FileOutputStream(archivo);
            properties.put(key, value);
            properties.store(fos, "Smaiee props2");
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar el archivo especificado!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    /*
     * FACADE ELEMENTOS
     */
    
    //BALASTOS**************
    /**
     * Gets the number of the next balast to write.
     * @param key
     * @return 
     */
//    public static int getBalastNumber() {
//        return BalastoLocalDAO.getBalastNumber();
//    }
    
    /**
     * Add a new balast.
     * @param key
     * @return 
     */
//    public static void addBalast(int writtenBalastNumber) {
//        BalastoLocalDAO.addBalast(writtenBalastNumber);
//    }
    
    
    
    /**
     * Delete the balast.
     * @param key
     * @return 
     */
    public static void deleteBalast(int writtenBalastNumber, DAOJmodbus daoJ) {
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(daoJ);
        dao.deleteBalast(writtenBalastNumber);
    }
    
    
    /**
     * Gets the number of the added balasts.
     * @param key
     * @return 
     */
    public static ArrayList<String> getAddedBalasts(DAOJmodbus daoJ) {
        BalastoDAOJmodbus dao = new BalastoDAOJmodbus(daoJ);
        return dao.getAddedBalasts();
    }
    
   
    
    public static ArrayList<String> getAddedBalasts(BalastoDAOJmodbus dao) {
        return dao.getAddedBalasts();
    }
    
    
      public static ArrayList<String> getAddedBalasts(OperacionesDaoJModbus dao) {
        BalastoDAOJmodbus1 dao1=(BalastoDAOJmodbus1)dao;
          return dao1.getAddedElements();
    }
    
    
    //ENTRADAS**************
    /**
     * Gets the number of the next balast to write.
     * @param key
     * @return 
     */
//    public static int getInNumber() {
//        return EntradaDAOJmodbus.getInNumber();
//    }
    
    /**
     * Add a new balast.
     * @param key
     * @return 
     */
    public static void addIn(int writtenBalastNumber, DAOJmodbus dao) {
        EntradaDAOJmodbus jDao = new EntradaDAOJmodbus(dao);
        jDao.addIn(writtenBalastNumber);
    }
    
    
    
    /**
     * Delete the balast.
     * @param key
     * @return 
     */
    public static void deleteIn(int writtenBalastNumber, DAOJmodbus dao) {
        EntradaDAOJmodbus jDao = new EntradaDAOJmodbus(dao);
        jDao.deleteIn(writtenBalastNumber);
    }
    
    
    /**
     * Gets the number of the added balasts.
     * @param key
     * @return 
     */
    public static ArrayList<String> getAddedIns(DAOJmodbus dao) {
        EntradaDAOJmodbus jDao = new EntradaDAOJmodbus(dao);
        return jDao.getAddedIns();
    }
    
    
    
    
    //ESCENAS**************
    /**
     * Gets the number of the next balast to write.
     * @param key
     * @return 
     */
//    public static int getSceneNumber() {
//        return EscenaLocalDAO.getSceneNumber();
//    }
    
    /**
     * Add a new balast.
     * @param key
     * @return 
     */
    public static void addScene(int writtenBalastNumber, DAOJmodbus daoJ) {
        EscenaDAOJmodbus dao = new EscenaDAOJmodbus(daoJ);
        dao.addScene(writtenBalastNumber);
    }
    
    
    /**
     * Delete the balast.
     * @param key
     * @return 
     */
    public static void deleteScene(int writtenBalastNumber, DAOJmodbus daoJ) {
        EscenaDAOJmodbus dao = new EscenaDAOJmodbus(daoJ);
        dao.deleteScene(writtenBalastNumber);
    }
    
    
    /**
     * Gets the number of the added balasts.
     * @param key
     * @return 
     */
    public static ArrayList<String> getAddedScenes(DAOJmodbus daoJ) {
        EscenaDAOJmodbus dao = new EscenaDAOJmodbus(daoJ);
        return dao.getAddedScenes();
    }
    
    
    
    
    //EVENTOS**************
    /**
     * Gets the number of the next balast to write.
     * @param key
     * @return 
     */
    public static int getEventNumber() {
        return EventoDAOJmodbus.getEventNumber();
    }
    
    /**
     * Add a new balast.
     * @param key
     * @return 
     */
    public static void addEvent(int writtenBalastNumber, DAOJmodbus dao) {
        EventoDAOJmodbus eDao = new EventoDAOJmodbus(dao);
        eDao.addEvent(writtenBalastNumber);
    }
    
    
    
    /**
     * Delete the balast.
     * @param key
     * @return 
     */
    public static void deleteEvent(int writtenBalastNumber, DAOJmodbus dao) {
        EventoDAOJmodbus eDao = new EventoDAOJmodbus(dao);
        eDao.deleteAddedEvent(writtenBalastNumber);
    }
    
    
    /**
     * Gets the number of the added balasts.
     * @param key
     * @return 
     */
    public static ArrayList<String> getAddedEvents(DAOJmodbus dao) {
        EventoDAOJmodbus eDao = new EventoDAOJmodbus(dao);
        return eDao.getAddedEvents();
    }
    
    
    
    //GRUPOS**************
    /**
     * Gets the number of the next balast to write.
     * @param key
     * @return 
     */
//    public static int getGroupNumber() {
//        return GrupoLocalDAO.getGroupNumber();
//    }
    
    /**
     * Add a new balast.
     * @param key
     * @return 
     */
    public static void addGroup(int writtenBalastNumber, DAOJmodbus dao) {
        GrupoDAOJmodbus gDao = new GrupoDAOJmodbus(dao);
        gDao.addGroup(writtenBalastNumber);
    }
    
    
    
    /**
     * Delete the balast.
     * @param key
     * @return 
     */
    public static void deleteGroup(int writtenBalastNumber, DAOJmodbus dao) {
        GrupoDAOJmodbus gDao = new GrupoDAOJmodbus(dao);
        gDao.deleteGroup(writtenBalastNumber);
    }
    
    
    /**
     * Gets the number of the added balasts.
     * @param key
     * @return 
     */
    public static ArrayList<String> getAddedGroups(DAOJmodbus dao) {
        GrupoDAOJmodbus gDao = new GrupoDAOJmodbus(dao);
        return gDao.getAddedGroups();
    }
    
    
    
    
    //TIEMPO REAL************
    public static ArrayList<String> getAddedAreas() {
        return TiempoRealDAO.getAddedAreas();
    }
    
    public static void addArea(String areaName) {
        TiempoRealDAO.addArea(areaName);
    }
    
    public static void deleteArea(String areaName) {
        TiempoRealDAO.deleteArea(areaName);
    }
    
    public static void addBalasts(String areaNumber, ArrayList<Integer> balasts) {
        TiempoRealDAO.addBalasts(areaNumber, balasts);
    }
    
    public static ArrayList<Integer> getAreaBalasts(String areaNumber) {
        return TiempoRealDAO.getAreaBalasts(areaNumber);
    }
    
    public static HashMap<String,Integer> getAreaImageNames() {
        return TiempoRealDAO.getAreaImageNames();
    }
    
//    public static void main(String [] args){
//        
//        for(int i = 1; i < 65; i++){
//            int nextBalastNumber = new PropHandler().getBalastNumber();
////            System.out.println("Next balast number: " + nextBalastNumber);
//            new PropHandler().deleteBalast(i);
//        }
////        System.out.println("Propiedad test: " + new PropHandler().getProperty("test"));
//        
//    }
}
