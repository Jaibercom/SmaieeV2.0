/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.properties.facadeBack;

//import com.isolux.dao.modbus.DAO4j;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.Constants;
import com.isolux.dao.properties.PropHandler;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EAFIT
 */
public class TiempoRealDAO {

    private static DAOJmodbus dao;

    public TiempoRealDAO(DAOJmodbus dao) {
        this.dao = dao;
    }

    public TiempoRealDAO() {
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
     * Gets the number of the added areas.
     *
     * @param key
     * @return
     */
    public static ArrayList<String> getAddedAreas() {
        String file = Constants.AREAS_FILE;
        ArrayList<String> addedBalasts = new ArrayList<String>();

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

            Set<String> areas = properties.stringPropertyNames();

            for (String string : areas) {
//                System.out.println(string + "=" +properties.getProperty(string));
                if (properties.getProperty(string) != null && !properties.getProperty(string).equals("")) {
                    addedBalasts.add(string);
                }
            }

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

        return addedBalasts;
    }

    /**
     * Add a new area.
     *
     * @param key
     * @return
     */
    public static void addArea(String areaName) {
        String file = Constants.AREAS_FILE;

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
            properties.setProperty(areaName, String.valueOf(0));
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

    /**
     * Delete the specified area.
     *
     * @param key
     * @return
     */
    public static void deleteArea(String areaName) {
        String file = Constants.AREAS_FILE;

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
            properties.remove(areaName);
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

    /**
     * Add balast.
     *
     * @param key
     * @return
     */
    public static void addBalasts(String areaNumber, ArrayList<Integer> balasts) {
        String file = Constants.AREAS_FILE;

        File archivo = new File(file);
        if (!archivo.isFile()) {
            try {
                archivo.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(com.isolux.dao.properties.PropHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        Properties properties = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(archivo);
            properties.load(stream);

            FileOutputStream fos = new FileOutputStream(archivo);

            String addedBalasts = "";
            for (int balast : balasts) {
                addedBalasts += balast + ",";
            }

            properties.setProperty(areaNumber, addedBalasts);
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

    /**
     * Gets the number of the next balast to write.
     *
     * @param key
     * @return
     */
    public static ArrayList<Integer> getAreaBalasts(String areaName) {
        ArrayList<Integer> areaBalasts = null;

        File archivo = new File(Constants.AREAS_FILE);
        Properties properties = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(archivo);
            properties.load(stream);

            String[] balasts = properties.getProperty(areaName).split(",");
            areaBalasts = new ArrayList<Integer>();
            for (int i = 0; i < balasts.length; i++) {
                Integer numAum = null;
                try {


                    numAum = Integer.parseInt(balasts[i]);
                    areaBalasts.add(numAum);

                } catch (NumberFormatException numberFormatException) {
                    numAum = 0;
                }


            }

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
        return areaBalasts;
    }

    /**
     * Get the areas that has an image.
     *
     * @return
     */
    public static HashMap<String, Integer> getAreaImageNames() {
        ArrayList<String> areas = getAddedAreas();
        HashMap<String, Integer> areaNames = new HashMap<String, Integer>();

        //Gets the list of files in the folder
        File dir = new File(Constants.AREA_IMAGES);
        File[] files = dir.listFiles();

        //Filter the directories.
        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
                return !file.isDirectory();
            }
        };
        files = dir.listFiles(fileFilter);

        ArrayList<String> names = new ArrayList<String>();
        for (File fileName : files) {
            names.add(fileName.getName().substring(0, fileName.getName().length() - 4));
        }

        //Compare the area names with the file names.
        for (String areaName : areas) {
            areaNames.put(areaName, names.contains(areaName) ? 1 : 0);
        }

        return areaNames;
    }

    /**
     * Indicates a change in a balast.
     */
    public static void saveBalast() {
        try {
            setSingleReg(0, 1);
            setSingleReg(1, 12);
            setSingleReg(0, 0);
        } catch (Exception e) {
            Logger.getLogger(TiempoRealDAO.class.getName()).log(Level.SEVERE, "Error en tiempo real", e);
        }
    }

    //TEST
    public static void main(String[] args) {
        ArrayList<String> addedAreas = getAddedAreas();

//        System.out.println("Adding área: ");
//        int [] addedBalasts = {10,13,11};
//        addBalasts(12, addedBalasts);
//        
//        System.out.println("Areas: ");
//        for (String string : addedAreas) {
//            System.out.println("Area: " + addedAreas);
//        }

//        System.out.println("Balasts from 12: ");
//        int[] balasts = getAreaBalasts(12);
//        for (int i : balasts) {
//            System.out.println("Balast: " + i);
//        }


        //Get the file names.
        HashMap<String, Integer> test = getAreaImageNames();
        Set<String> names = test.keySet();
        for (String name : names) {
            System.out.println("Area: " + name + ", imagen?:" + test.get(name));
        }

//        File dir = new File(Constants.AREA_IMAGES);
//        File[] files = dir.listFiles();
//
//        FileFilter fileFilter = new FileFilter() {
//
//            public boolean accept(File file) {
//                return !file.isDirectory();
//            }
//        };
//        files = dir.listFiles(fileFilter);
//        
//        System.out.println("Files: ");
//        for (File image : files) {
//            System.out.println(image.getName());
//        }
    }
}
