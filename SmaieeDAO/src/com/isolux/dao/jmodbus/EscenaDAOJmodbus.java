/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.bo.Escena;
import com.isolux.dao.Utils;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class EscenaDAOJmodbus {

    private static DAOJmodbus dao;

    //CONSTRUCTOR
    public EscenaDAOJmodbus(DAOJmodbus dao) {
        this.dao = dao;
    }

    /**
     * Write a single register.
     */
    public static void setSingleReg(int pos, int mode) {
        int[] values = {mode};
        dao.setRegValue(pos, values);
    }

    /**
     * Saves the group.
     *
     * @param escena
     */
    public static boolean saveScene(Escena escena) {
        boolean state = false;
        int escenaNumber = escena.getNumeroEscena();

        try {

            //MODO
            setSingleReg(0, 1);

            //Init offset.
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.scene"));
            int[] sceneArray = new int[Integer.parseInt(PropHandler.getProperty("scene.memory.size"))];

            System.out.println("SAVING SCENE NUMBER: " + escenaNumber);

            //scene number
            sceneArray[0] = escenaNumber;

            //activation
            sceneArray[1] = 1;

            //name
            //        //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
//            try {
            //                int nameOffset = 2;
            //                ArrayList<BigInteger> balastNameBytes = UtilsJmodbus.getNameBytesReverse(escena.getNombre());
            //                int size = balastNameBytes.size();
            //                for (int i = 0; i < 5; i++) {
            //                    if (i < size) {
            //                        sceneArray[nameOffset] = balastNameBytes.get(i).intValue();
            //                    } else {
            //                        sceneArray[nameOffset] = 0;
            //                    }
            //                    nameOffset++;
            //                }
            //            } catch (Exception e) {
            //            }
            //</editor-fold>

            UtilsJmodbus.encriptarNombre(sceneArray, 2, escena.getNombre(), 5);


            //niveles balastos
            int balastsLevels = 7;
            int[] nivelBalastos = escena.getNivelBalasto();
            for (int i = 0; i < nivelBalastos.length; i++) {
                sceneArray[balastsLevels] = nivelBalastos[i];
                balastsLevels++;
            }


            //balastos afectados
            int[] balastos = escena.getBalastosAfectados();

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> affectedBalasts = UtilsJmodbus.getSelectedItems(seleBal);
            int pos = 71;
            for (int i = affectedBalasts.size() - 1; i >= 0; i--) {
                sceneArray[pos] = affectedBalasts.get(i).intValue();
                pos++;
            }


            dao.setRegValue(initOffset, sceneArray);
            PropHandler.addScene(escenaNumber, dao);
            System.out.println("Scene number " + escenaNumber + " saved");

            //MODO
            setSingleReg(0, 0);

            state = true;
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        }

        return state;
    }

    /**
     * Deletes the group.
     *
     * @param groupNumbers
     * @return
     */
    public static boolean deleteScene(String sceneNumbers) {
        boolean state = false;
        int escenaNumber = Integer.parseInt(sceneNumbers);
        Escena escena = new Escena();

        try {

            //MODO
            setSingleReg(0, 1);

            //Init offset.
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.scene"));
            int[] sceneArray = new int[Integer.parseInt(PropHandler.getProperty("scene.memory.size"))];

            System.out.println("DELETING SCENE NUMBER: " + escenaNumber);

            //scene number
            sceneArray[0] = escenaNumber;

            //activation
            sceneArray[1] = 0;

            //name
            try {
                int nameOffset = 2;
                for (int i = 0; i < 5; i++) {
                    sceneArray[nameOffset] = 0;
                    nameOffset++;
                }
            } catch (Exception e) {
            }


            //niveles balastos
            int balastsLevels = 7;
            int[] nivelBalastos = escena.getNivelBalasto();
            for (int i = 0; i < nivelBalastos.length; i++) {
                sceneArray[balastsLevels] = 0;
                balastsLevels++;
            }


            //balastos afectados
            int[] balastos = escena.getBalastosAfectados();

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> affectedBalasts = UtilsJmodbus.getSelectedItems(seleBal);
            int pos = 71;
            for (int i = affectedBalasts.size() - 1; i >= 0; i--) {
                sceneArray[pos] = 0;
                pos++;
            }

            dao.setRegValue(initOffset, sceneArray);
            PropHandler.deleteScene(escenaNumber, dao);
            System.out.println("Scene number " + escenaNumber + " deleted");

            //MODO
            setSingleReg(0, 0);

            state = true;
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        }

        return state;
    }

    /**
     * Get a group from the card.
     *
     * @param sceneNumber
     */
    public static Escena readScene(int sceneNumber) {
        Escena escene = new Escena();

        try {

            //MODO
            setSingleReg(0, 1);

            System.out.println("READING SCENE No.:" + sceneNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.scene"));             //2000

            setSingleReg(initOffset, sceneNumber);
            int[] sceneArray = dao.getRegValue(initOffset, Integer.parseInt(PropHandler.getProperty("scene.memory.size")));


            //Scene number
            escene.setNumeroEscena(sceneArray[0]);

            //activation
            escene.setActivacion(sceneArray[1]);

            //name
            try {
                int nameOffset = 2;
                int nameBytes = Integer.parseInt(PropHandler.getProperty("general.name.bytes"));
                ArrayList<BigInteger> balastNameBytes = new ArrayList<BigInteger>();
                String balastName = "";
                //Get the bytes from the card.
                for (int i = 0; i < nameBytes; i++) {
                    int tales = 0;
                    tales |= sceneArray[nameOffset] & 0xFFFF;
                    balastNameBytes.add(new BigInteger(String.valueOf(tales)));
                    nameOffset++;
                }
                //Join the bytes using a string
                for (BigInteger nameByte : balastNameBytes) {
                    String value = nameByte.toString(2);
                    balastName = UtilsJmodbus.getCeros(value) + balastName;
                }
                //Recreates the entire name bytes and sets the name to the balast.
                BigInteger totalBytes = new BigInteger(balastName, 2);
                escene.setNombre(new String(totalBytes.toByteArray()));

            } catch (Exception e) {
                System.out.println("Error al leer el nombre de la escena.");
            }

            //niveles balastos
            int balastsLevels = 7;
            int balastsMaxNumber = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
            int[] nivelBalastos = new int[balastsMaxNumber];
            for (int i = 0; i < nivelBalastos.length; i++) {
                nivelBalastos[i] = sceneArray[balastsLevels];
                balastsLevels++;
            }
            escene.setNivelBalasto(nivelBalastos);

            //balastos afectados
            try {
                int balastsOffset = 71;
                int tamReg = 16;
                int[] balastos = escene.getBalastosAfectados();
                float bytesToRead = balastos.length / tamReg;
                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();

                //Get the bytes from the card.
                for (int i = 0; i < bytesToRead; i++) {
                    affectedBalasts.add(new BigInteger(String.valueOf(sceneArray[balastsOffset] & 0xFFFF)));
                    balastsOffset++;
                }

                String balastName = "";
                for (BigInteger nameByte : affectedBalasts) {
                    String value = nameByte.toString(2);
                    value = UtilsJmodbus.getCeros(value);
                    balastName = value + balastName;
                }

                int j = 0;
                for (int i = balastos.length - 1; i >= 0; i--) {
                    String bit = String.valueOf(balastName.charAt(i));
                    balastos[j] = Integer.parseInt(bit);
                    j++;
                }
            } catch (Exception e) {
                System.out.println("Error al leer los balastos afectados por la escena.");
            }


            System.out.println("Scene number " + sceneNumber + " readed.");

            //MODO
            setSingleReg(0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return escene;
    }

    public static ArrayList<String> getAddedScenes() {
        //       //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
        ArrayList<String> addedScenes = new ArrayList<String>();
        ////        DAO4j dao = new DAO4j();
        //        int numBalastos = Integer.parseInt(PropHandler.getProperty("scene.max.number"));
        //        if (numBalastos < 16) {
        //            numBalastos = 16;
        //        }
        //
        //
        //        try {
        //                int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));
        //                int usedRegisters = Integer.parseInt(PropHandler.getProperty("scene.memory.registers"));
        //
        //
        //                int balastsOffset = initOffset;
        //                int tamReg = 16;
        //                int[] balastos = new int[numBalastos];
        //                float bytesToRead = (balastos.length / tamReg) < 1 ? 1 : (balastos.length / tamReg);
        //                ArrayList<BigInteger> affectedScenes = new ArrayList<BigInteger>();
        //                int[] addedB = dao.getRegValue(initOffset, usedRegisters);
        //
        //                //Get the bytes from the card.
        //                for (int i = 0; i < bytesToRead; i++) {
        ////                    affectedBalasts.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
        //                    affectedScenes.add(new BigInteger(String.valueOf(addedB[i]&0xFFFF)));
        //                }
        //
        //                String balastName = "";
        //                for (BigInteger nameByte : affectedScenes) {
        //                    String value = nameByte.toString(2);
        //                    value = Utils.getCeros(value);
        //                    balastName = value + balastName;
        //                }
        //
        //                int j=0;
        //                for (int i = balastos.length -1; i >= 0; i--) {
        //                    String bit = String.valueOf(balastName.charAt(i));
        //                    balastos[j] = Integer.parseInt(bit);
        //                    j++;
        //                }
        //
        //                //Get an ArrayList with the result
        //                for (int i = 0; i < balastos.length; i++) {
        //                    if (balastos[i] != 0) {
        //                       addedScenes.add(String.valueOf(i));
        //                    }
        //
        //                }
        //
        //            } catch (Exception e) {
        //                System.out.println("Error al leer los balastos añadidos.");
        //            }
        //
        //        return addedScenes;
        //</editor-fold>

        ArrayList<String> elementosEnMemoria;
        try {

            int numEscenas = Integer.parseInt(PropHandler.getProperty("scene.max.number"));
            int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));
            int usedRegisters = Integer.parseInt(PropHandler.getProperty("scene.memory.registers"));
            int tamReg = 8;
//            int tamReg = Integer.parseInt(PropHandler.getProperty("registro.tamanio.lectura"));
            elementosEnMemoria = UtilsJmodbus.getElementosEnMemoria(numEscenas, dao, initOffset, usedRegisters, tamReg);



        } catch (Exception e) {
            System.out.println("No se encontraron los archivos de configuración");
            e.printStackTrace();
            return null;
        }
        return elementosEnMemoria;

    }

    /**
     * Returns the an array with the added balasts.
     *
     * @return
     */
    public static int[] getAddedScenesCardArray() {
        int numBalastos = Integer.parseInt(PropHandler.getProperty("scene.max.number"));
        if (numBalastos < 16) {
            numBalastos = 16;
        }

        int[] balastos = new int[numBalastos];
        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));
            int usedRegisters = Integer.parseInt(PropHandler.getProperty("scene.memory.registers"));

            int balastsOffset = initOffset;
            int tamReg = 16;

            float bytesToRead = (balastos.length / tamReg) < 1 ? 1 : (balastos.length / tamReg);
            ArrayList<BigInteger> affectedScenes = new ArrayList<BigInteger>();
            int[] addedB = dao.getRegValue(initOffset, usedRegisters);

            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
//                    affectedScenes.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
                affectedScenes.add(new BigInteger(String.valueOf(addedB[i] & 0xFFFF)));
            }

            String balastName = "";
            for (BigInteger nameByte : affectedScenes) {
                String value = nameByte.toString(2);
                value = Utils.getCeros(value);
                balastName = value + balastName;
            }

            int j = 0;
            for (int i = balastos.length - 1; i >= 0; i--) {
                String bit = String.valueOf(balastName.charAt(i));
                balastos[j] = Integer.parseInt(bit);
                j++;
            }

        } catch (Exception e) {
            System.out.println("Error al leer los balastos añadidos.");
            e.printStackTrace();
        }

        return balastos;
    }

    /**
     * Add a new balast.
     *
     * @param key
     * @return
     */
    public static void addScene(int writtenBalastNumber) {
        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));

            int[] balastos = getAddedScenesCardArray();

            //Add the new balast.
            balastos[writtenBalastNumber] = 1;

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }



            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = Utils.getSelectedItems(seleBal);
            for (int i = name.size() - 1; i >= 0; i--) {
                int[] nameValues = {name.get(i).intValue()};
                dao.setRegValue(initOffset, nameValues);
                initOffset++;
            }

        } catch (Exception e) {
            System.out.println("No se pudo escribir el balasto especificado!");
            e.printStackTrace();
        }
    }

    /**
     * Delete the balast.
     *
     * @param key
     * @return
     */
    public static void deleteScene(int writtenBalastNumber) {

        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));

            int[] balastos = getAddedScenesCardArray();
//            System.out.println("Offset: " + initOffset + ", group balasts: " + balastos);

            //Delete the specified balast.
            balastos[writtenBalastNumber] = 0;

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = Utils.getSelectedItems(seleBal);
            for (int i = name.size() - 1; i >= 0; i--) {
                int[] nameValues = {name.get(i).intValue()};
                dao.setRegValue(initOffset, nameValues);
                initOffset++;
            }

        } catch (Exception e) {
            System.out.println("No se pudo eliminar el balasto especificado!");
            e.printStackTrace();
        }
    }
}
