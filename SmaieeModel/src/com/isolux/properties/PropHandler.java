package com.isolux.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    
    
   
    
    
    
    //TEST
    public static void main(String [] args){
//        System.out.println("Propiedad test: " + new PropHandler().getProperty("test"));
        
    }
}
