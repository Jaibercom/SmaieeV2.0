/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class LocalMemoryHandler {

    private static HashMap<String, String> localMemory = new HashMap<String, String>();

    public LocalMemoryHandler() {
        localMemory = readMemoryFile();
    }

    /**
     * Gets a value.
     * @param offset
     * @return 
     */
    public int getValue(int offset) {
        return Integer.parseInt(localMemory.get(String.valueOf(offset)));
    }

    /**
     * Sets a value.
     * @param offset
     * @param value 
     */
    public void setValue(int offset, int value) {
        try {
            if (localMemory.containsKey(String.valueOf(offset))) {
                localMemory.remove(String.valueOf(offset));
                localMemory.put(String.valueOf(offset), String.valueOf(value));
            }
        } catch (Exception e) {
            flush();
        }
    }

    /**
     * Reads the local memory file
     * @return 
     */
    public static HashMap<String, String> readMemoryFile() {
        HashMap<String, String> memory = new HashMap<String, String>();
        BufferedReader br = null;
        try {
            File memFile = new File(Constants.MEMORY_FILE);
            if (!memFile.isFile()) {
                initDefaultMemoryFile();
            }
            
            br = new BufferedReader(new FileReader(memFile));
            String memoryRegister = "";

            while ((memoryRegister = br.readLine()) != null) {
                String[] registerKeyValue = memoryRegister.split("=");
                memory.put(registerKeyValue[0], registerKeyValue[1]);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LocalMemoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(LocalMemoryHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return memory;
    }

    /**
     * Write the content of the localMemory into the memory file.
     */
    public static void flush() {
        BufferedWriter bw = null;
        try {
            //Clear the memory file
            File memoryFile = new File(Constants.MEMORY_FILE);
            if (memoryFile.isFile()) {
                memoryFile.delete();
                memoryFile.createNewFile();
            }

            //Write memory values into the file
            bw = new BufferedWriter(new FileWriter(new File(Constants.MEMORY_FILE)));
            for (int i = 1; i < localMemory.size() + 1; i++) {
                String register = String.valueOf(i) + "=" + localMemory.get(String.valueOf(i));
                bw.write(register + "\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LocalMemoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(LocalMemoryHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Clear the memory file.
     */
    public static void clear() {
        BufferedWriter bw = null;
        try {
            //Clear the memory file
            File memoryFile = new File(Constants.MEMORY_FILE);
            if (memoryFile.isFile()) {
                memoryFile.delete();
                memoryFile.createNewFile();
            }

            //Write memory values into the file
            bw = new BufferedWriter(new FileWriter(new File(Constants.MEMORY_FILE)));
            for (int i = 1; i < localMemory.size() + 1; i++) {
                String register = String.valueOf(i) + "=0";
                bw.write(register + "\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LocalMemoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(LocalMemoryHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Create a new default memory file.
     */
    public static void initDefaultMemoryFile() {
        BufferedWriter bw = null;
        try {
            //Clear the memory file
            File memoryFile = new File(Constants.MEMORY_FILE);
            if (memoryFile.isFile()) {
                memoryFile.delete();
                memoryFile.createNewFile();
            } else {
                memoryFile.createNewFile();
            }

            //Write memory values into the file
            bw = new BufferedWriter(new FileWriter(new File(Constants.MEMORY_FILE)));
            for (int i = 1; i < Constants.OFFSETS + 1; i++) {
                String register = String.valueOf(i) + "=0";
                bw.write(register + "\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LocalMemoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(LocalMemoryHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //GETTERS AND SETTERS
    public static HashMap<String, String> getLocalMemory() {
        return localMemory;
    }

    public static void setLocalMemory(HashMap<String, String> localMemory) {
        LocalMemoryHandler.localMemory = localMemory;
    }
    
    
    
    
    public static void main(String args []){
        new LocalMemoryHandler().clear();
    }
}