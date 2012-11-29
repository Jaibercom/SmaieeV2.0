/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.bo.Elemento;
import com.isolux.bo.Escena;
import com.isolux.bo.Grupo;
import com.isolux.dao.Utils;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class EscenaDAOJmodbus extends OperacionesDaoJModbus{

    private static DAOJmodbus dao;

    //CONSTRUCTOR
    public EscenaDAOJmodbus(DAOJmodbus dao) {
        this.dao = dao;
    }

//    /**
//     * Write a single register.
//     */
//    public static void setSingleReg(int pos, int mode) {
//        int[] values = {mode};
//        dao.setRegValue(pos, values);
//    }

    /**
     * Saves the group.
     *
     * @param escena
     */
    @Override
    public boolean saveElement(Elemento escena1) {
        //<editor-fold defaultstate="collapsed" desc="CODIGO ANTIGUO">
        //        boolean state = false;
        //        int escenaNumber = escena.getNumeroEscena();
        //
        //        try {
        //
        //            //MODO
        //            setSingleReg(0, 1);
        //
        //            //Init offset.
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.scene"));
        //            int[] sceneArray = new int[Integer.parseInt(PropHandler.getProperty("scene.memory.size"))];
        //
        //            System.out.println("SAVING SCENE NUMBER: " + escenaNumber);
        //
        //            //scene number
        //            sceneArray[0] = escenaNumber;
        //
        //            //activation
        //            sceneArray[1] = 1;
        //
        //            //name
        //            //        //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
        ////            try {
        //            //                int nameOffset = 2;
        //            //                ArrayList<BigInteger> balastNameBytes = UtilsJmodbus.getNameBytesReverse(escena.getNombre());
        //            //                int size = balastNameBytes.size();
        //            //                for (int i = 0; i < 5; i++) {
        //            //                    if (i < size) {
        //            //                        sceneArray[nameOffset] = balastNameBytes.get(i).intValue();
        //            //                    } else {
        //            //                        sceneArray[nameOffset] = 0;
        //            //                    }
        //            //                    nameOffset++;
        //            //                }
        //            //            } catch (Exception e) {
        //            //            }
        //            //</editor-fold>
        //
        //            UtilsJmodbus.encriptarNombre(sceneArray, 2, escena.getNombre(), 5);
        //
        //
        //            //niveles balastos
        //            int balastsLevels = 7;
        //            int[] nivelBalastos = escena.getNivelBalasto();
        //            for (int i = 0; i < nivelBalastos.length; i++) {
        //                sceneArray[balastsLevels] = nivelBalastos[i];
        //                balastsLevels++;
        //            }
        //
        //
        //            //balastos afectados
        //            int[] balastos = escena.getBalastosAfectados();
        //
        //            //Get a string with the bits of the selected values.
        //            String seleBal = "";
        //            for (int i : balastos) {
        //                seleBal = String.valueOf(i) + seleBal;
        //            }
        //
        //            //Get BitIntegers every 16 bits and store them in the card.
        //            ArrayList<BigInteger> affectedBalasts = UtilsJmodbus.getSelectedItems(seleBal);
        //            int pos = 71;
        //            for (int i = affectedBalasts.size() - 1; i >= 0; i--) {
        //                sceneArray[pos] = affectedBalasts.get(i).intValue();
        //                pos++;
        //            }
        //
        //
        //            dao.setRegValue(initOffset, sceneArray);
        //            PropHandler.addScene(escenaNumber, dao);
        //            System.out.println("Scene number " + escenaNumber + " saved");
        //
        //            //MODO
        //            setSingleReg(0, 0);
        //
        //            state = true;
        //        } catch (Exception e) {
        //            state = false;
        //            e.printStackTrace();
        //        }
        //
        //        return state;
        //</editor-fold>

        Escena escena=(Escena) escena1;
        boolean state = false;
        int escenaNumero = escena.getNumeroEscena();

        try {

            //MODO Configuracion
            setSingleReg(0, 1);

            //Init offset.
            System.out.println("SAVING SCENE NUMBER: " + escenaNumero);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.scene"));
            int[] escenasArray = new int[Integer.parseInt(PropHandler.getProperty("scene.memory.size"))];

            //Group number
//            Es de 10 caracteres porque el nombre es de 10 caracteres
            escenasArray[0] = escenaNumero;// hay que validar el numero de grupo
            escenasArray[1] = 1;

            //name
//            Con esta rutina se calcula el nombre
            //<editor-fold defaultstate="collapsed" desc="Codigo anterior">
            //            try {
            //                int nameOffset = 2;
            //                ArrayList<BigInteger> balastNameBytes = UtilsJmodbus.getNameBytesReverse(group.getName());
            //                int size = balastNameBytes.size();
            //                for (int i = 0; i < 5; i++) {
            //                    if (i < size) {
            //                        groupsArray[nameOffset] = balastNameBytes.get(i).intValue();
            //                    } else {
            //                        groupsArray[nameOffset] = 0;
            //                    }
            //                    nameOffset++;
            //                }
            //            } catch (Exception e) {
            //            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Codigo anterior">
            //            int nameOffset = 2;
            //            ArrayList<BigInteger> balastNameBytes = UtilsJmodbus.getNameBytesReverse(group.getName());
            //            int size = balastNameBytes.size();
            //            for (int i = 0; i < 5; i++) {
            //                if (i < size) {
            //                    groupsArray[nameOffset] = balastNameBytes.get(i).intValue();
            //                } else {
            //                    groupsArray[nameOffset] = 0;
            //                }
            //                nameOffset++;
            //            }
            //</editor-fold>

            UtilsJmodbus.encriptarNombre(escenasArray, 2, escena.getNombre(), 5);
            
            //<editor-fold defaultstate="collapsed" desc="Codigo quitado de balastos afectados en la escena">
            //            //balastos afectados
            //            int[] escenas = escena.getBalastosAfectados();
            //
            //            //Get a string with the bits of the selected values.
            //            String seleBal = "";
            //            for (int i : escenas) {
            //                seleBal = String.valueOf(i) + seleBal;
            //            }
            //
            //            //Get BitIntegers every 16 bits and store them in the card.
            //            ArrayList<BigInteger> name = Utils.getSelectedItems(seleBal);
            //            int affectedBalasts = 7;
            //            for (int i = name.size() - 1; i >= 0; i--) {
            //                escenasArray[affectedBalasts] = name.get(i).intValue();
            //                affectedBalasts++;
            //            }
            //</editor-fold>

            dao.setRegValue(initOffset, escenasArray);
            addElement(escenaNumero);

            //MODO
            setSingleReg(0, 0);

            System.out.println("Scene number " + escenaNumero + " saved.");
            Logger.getLogger(EscenaDAOJmodbus.class.getName()).log(Level.INFO, "Escena numero {0} grabada.", escenaNumero);

            state = true;
        } catch (Exception e) {
            state = false;
//            e.printStackTrace();
             Logger.getLogger(EscenaDAOJmodbus.class.getName()).log(Level.SEVERE, "Escena numero"+escenaNumero+ "grabada.",e );
        }
        return state;
    }

    /**
     * Deletes the group.
     *
     * @param groupNumbers
     * @return
     */
    @Override
    public boolean deleteElement(String sceneNumbers) {
        //        //<editor-fold defaultstate="collapsed" desc="Codigo Antiguo">
        //        boolean state = false;
        //        int escenaNumber = Integer.parseInt(sceneNumbers);
        //        Escena escena = new Escena();
        //
        //        try {
        //
        //            //MODO
        //            setSingleReg(0, 1);
        //
        //            //Init offset.
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.scene"));
        //            int[] sceneArray = new int[Integer.parseInt(PropHandler.getProperty("scene.memory.size"))];
        //
        //            System.out.println("DELETING SCENE NUMBER: " + escenaNumber);
        //
        //            //scene number
        //            sceneArray[0] = escenaNumber;
        //
        //            //activation
        //            sceneArray[1] = 0;
        //
        //            //name
        //            try {
        //                int nameOffset = 2;
        //                for (int i = 0; i < 5; i++) {
        //                    sceneArray[nameOffset] = 0;
        //                    nameOffset++;
        //                }
        //            } catch (Exception e) {
        //            }
        //
        //
        //            //niveles balastos
        //            int balastsLevels = 7;
        //            int[] nivelBalastos = escena.getNivelBalasto();
        //            for (int i = 0; i < nivelBalastos.length; i++) {
        //                sceneArray[balastsLevels] = 0;
        //                balastsLevels++;
        //            }
        //
        //
        //            //balastos afectados
        //            int[] balastos = escena.getBalastosAfectados();
        //
        //            //Get a string with the bits of the selected values.
        //            String seleBal = "";
        //            for (int i : balastos) {
        //                seleBal = String.valueOf(i) + seleBal;
        //            }
        //
        //            //Get BitIntegers every 16 bits and store them in the card.
        //            ArrayList<BigInteger> affectedBalasts = UtilsJmodbus.getSelectedItems(seleBal);
        //            int pos = 71;
        //            for (int i = affectedBalasts.size() - 1; i >= 0; i--) {
        //                sceneArray[pos] = 0;
        //                pos++;
        //            }
        //
        //            dao.setRegValue(initOffset, sceneArray);
        //            PropHandler.deleteScene(escenaNumber, dao);
        //            System.out.println("Scene number " + escenaNumber + " deleted");
        //
        //            //MODO
        //            setSingleReg(0, 0);
        //
        //            state = true;
        //        } catch (Exception e) {
        //            state = false;
        //            e.printStackTrace();
        //        }
        //
        //        return state;
        //</editor-fold>
        
        boolean state = false;
        int SceneNumber = Integer.parseInt(sceneNumbers);

        try {

            //MODO
            setSingleReg(0, 1);

            //Init offset.
            System.out.println("DELETING SCENE NUMBER: " + SceneNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.scene"));
            int[] sceneArray = new int[Integer.parseInt(PropHandler.getProperty("scene.memory.size"))];

            //Group number
            sceneArray[0] = SceneNumber;
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

            //balastos afectados
            int[] balastos = new Grupo().getBalastosAfectados();

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = Utils.getSelectedItems(seleBal);
            int affectedBalasts = 7;
            for (int i = name.size() - 1; i >= 0; i--) {
                sceneArray[affectedBalasts] = 0;
                affectedBalasts++;
            }

            dao.setRegValue(initOffset, sceneArray);
            deleteElement(SceneNumber);

            //MODO
            setSingleReg(0, 0);

            System.out.println("Scene number " + SceneNumber + " deleted.");

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
    @Override
    public Escena readElement(int sceneNumber) {
        //        //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
        //        Escena escene = new Escena();
        //
        //        try {
        //
        //            //MODO
        //            setSingleReg(0, 1);
        //
        //            System.out.println("READING SCENE No.:" + sceneNumber);
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.scene"));             //2000
        //
        //            setSingleReg(initOffset, sceneNumber);
        //            int[] sceneArray = dao.getRegValue(initOffset, Integer.parseInt(PropHandler.getProperty("scene.memory.size")));
        //
        //
        //            //Scene number
        //            escene.setNumeroEscena(sceneArray[0]);
        //
        //            //activation
        //            escene.setActivacion(sceneArray[1]);
        //
        //            //name
        //            try {
        //                int nameOffset = 2;
        //                int nameBytes = Integer.parseInt(PropHandler.getProperty("general.name.bytes"));
        //                ArrayList<BigInteger> balastNameBytes = new ArrayList<BigInteger>();
        //                String balastName = "";
        //                //Get the bytes from the card.
        //                for (int i = 0; i < nameBytes; i++) {
        //                    int tales = 0;
        //                    tales |= sceneArray[nameOffset] & 0xFFFF;
        //                    balastNameBytes.add(new BigInteger(String.valueOf(tales)));
        //                    nameOffset++;
        //                }
        //                //Join the bytes using a string
        //                for (BigInteger nameByte : balastNameBytes) {
        //                    String value = nameByte.toString(2);
        //                    balastName = UtilsJmodbus.getCeros(value) + balastName;
        //                }
        //                //Recreates the entire name bytes and sets the name to the balast.
        //                BigInteger totalBytes = new BigInteger(balastName, 2);
        //                escene.setNombre(new String(totalBytes.toByteArray()));
        //
        //            } catch (Exception e) {
        //                System.out.println("Error al leer el nombre de la escena.");
        //            }
        //
        //            //niveles balastos
        //            int balastsLevels = 7;
        //            int balastsMaxNumber = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
        //            int[] nivelBalastos = new int[balastsMaxNumber];
        //            for (int i = 0; i < nivelBalastos.length; i++) {
        //                nivelBalastos[i] = sceneArray[balastsLevels];
        //                balastsLevels++;
        //            }
        //            escene.setNivelBalasto(nivelBalastos);
        //
        //            //balastos afectados
        //            try {
        //                int balastsOffset = 71;
        //                int tamReg = 16;
        //                int[] balastos = escene.getBalastosAfectados();
        //                float bytesToRead = balastos.length / tamReg;
        //                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
        //
        //                //Get the bytes from the card.
        //                for (int i = 0; i < bytesToRead; i++) {
        //                    affectedBalasts.add(new BigInteger(String.valueOf(sceneArray[balastsOffset] & 0xFFFF)));
        //                    balastsOffset++;
        //                }
        //
        //                String balastName = "";
        //                for (BigInteger nameByte : affectedBalasts) {
        //                    String value = nameByte.toString(2);
        //                    value = UtilsJmodbus.getCeros(value);
        //                    balastName = value + balastName;
        //                }
        //
        //                int j = 0;
        //                for (int i = balastos.length - 1; i >= 0; i--) {
        //                    String bit = String.valueOf(balastName.charAt(i));
        //                    balastos[j] = Integer.parseInt(bit);
        //                    j++;
        //                }
        //            } catch (Exception e) {
        //                System.out.println("Error al leer los balastos afectados por la escena.");
        //            }
        //
        //
        //            System.out.println("Scene number " + sceneNumber + " readed.");
        //
        //            //MODO
        //            setSingleReg(0, 0);
        //
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        //
        //        return escene;
        //</editor-fold>
        
         Escena escena = new Escena();

        try {

            //MODO
            setSingleReg(0, 1);//Pone el smaiee en modo run en 0 y modo configuracion en 1 (en este caso es 1)

            System.out.println("READING GROUP NUMBER: " + sceneNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.scene"));
            int tamGrupo = Integer.parseInt(PropHandler.getProperty("scene.memory.size"));

            //Group number
            setSingleReg(initOffset, sceneNumber);
            int[] sceneArray = dao.getRegValue(initOffset, tamGrupo);


            //Group number
            escena.setNumeroEscena(sceneNumber);
            setSingleReg(1, 4);

            //activation
            escena.setActivacion(sceneArray[1]);

            //name
            try {

                //           //<editor-fold defaultstate="collapsed" desc="codigo antiguo">

                //                int nameOffset = 2;
                //                ArrayList<BigInteger> balastNameBytes = new ArrayList<BigInteger>();
                //                String balastName = "";
                //                //Get the bytes from the card.
                //                for (int i = 0; i < 5; i++) {
                //                    int tales = 0;
                //                    tales |= groupArray[nameOffset] & 0x00FF;
                //                    balastNameBytes.add(new BigInteger(String.valueOf(tales)));
                //                    nameOffset++;
                //                }
                //                //Join the bytes using a string
                //                for (BigInteger nameByte : balastNameBytes) {
                //                    String value = nameByte.toString(2);
                //                    balastName = Utils.getCeros(value) + balastName;
                //                }
                //                //Recreates the entire name bytes and sets the name to the balast.
                //                BigInteger totalBytes = new BigInteger(balastName, 2);
                //                grupo.setName(new String(totalBytes.toByteArray()));
                //
                //           
                //</editor-fold>

                String nomb = UtilsJmodbus.desencriptarNombre(sceneArray, 2, 5);
                escena.setNombre(nomb);

            } catch (Exception e) {
                System.out.println("Error al leer el nombre del grupo.");
            }
////
//            int balastOffset = 7;
//            int tamReg = 16;
//            int[] balastos = escena.getBalastosAfectados();
////            float bytesToRead = balastos.length / tamReg;
//
//            //balastos afectados
//            //            <editor-fold defaultstate="collapsed" desc="Balastros afectados codigo antiguo corregido">
////            try {
////                int balastsOffset = 7;
////                int tamReg = 16;
////                int[] balastos = grupo.getBalastosAfectados();
////                float bytesToRead = balastos.length / tamReg;
////                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
////                
////                //Get the bytes from the card.
////                for (int i = 0; i < bytesToRead; i++) {
////                    affectedBalasts.add(new BigInteger(String.valueOf(groupArray[balastsOffset] & 0xFFFF)));
////                    balastsOffset++;
////                }
////                
////                String balastName = "";
////                for (BigInteger nameByte : affectedBalasts) {
////                    String value = nameByte.toString(2);
////                    value = Utils.getCeros(value,16);
////                    balastName = value + balastName;
////                }
////                
////                int j = 0;
////                for (int i = balastos.length - 1; i >= 0; i--) {
////                    String bit = String.valueOf(balastName.charAt(i));
////                    balastos[j] = Integer.parseInt(bit);
////                    j++;
////                }
////                
//////                agregamos los balastos al objeto grupo
////                grupo.setBalastosAfectados(balastos);
////            } catch (Exception e) {
////                System.out.println("Error al leer los balastos afectados por el grupo.");
////            }
//            //</editor-fold>
//
//            balastos = UtilsJmodbus.obtenerElementosAfectados(sceneArray, balastOffset, 64, tamReg, 16);
//            escena.setBalastosAfectados(balastos);

            //MODO
            setSingleReg(0, 0);

            System.out.println("scene number " + sceneNumber + " readed.");

        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(EscenaDAOJmodbus.class.getName()).log(Level.SEVERE,null,e);
        }

        return escena;
    }

    @Override
    public ArrayList<String> getAddedElements() {
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
    @Override
    public int[] getAddedCardArray() {
        //      //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
//        int numBalastos = Integer.parseInt(PropHandler.getProperty("scene.max.number"));
        //        if (numBalastos < 16) {
        //            numBalastos = 16;
        //        }
        //
        //        int[] balastos = new int[numBalastos];
        //        try {
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));
        //            int usedRegisters = Integer.parseInt(PropHandler.getProperty("scene.memory.registers"));
        //
        //            int balastsOffset = initOffset;
        //            int tamReg = 16;
        //
        //            float bytesToRead = (balastos.length / tamReg) < 1 ? 1 : (balastos.length / tamReg);
        //            ArrayList<BigInteger> affectedScenes = new ArrayList<BigInteger>();
        //            int[] addedB = dao.getRegValue(initOffset, usedRegisters);
        //
        //            //Get the bytes from the card.
        //            for (int i = 0; i < bytesToRead; i++) {
        ////                    affectedScenes.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
        //                affectedScenes.add(new BigInteger(String.valueOf(addedB[i] & 0xFFFF)));
        //            }
        //
        //            String balastName = "";
        //            for (BigInteger nameByte : affectedScenes) {
        //                String value = nameByte.toString(2);
        //                value = Utils.getCeros(value);
        //                balastName = value + balastName;
        //            }
        //
        //            int j = 0;
        //            for (int i = balastos.length - 1; i >= 0; i--) {
        //                String bit = String.valueOf(balastName.charAt(i));
        //                balastos[j] = Integer.parseInt(bit);
        //                j++;
        //            }
        //
        //        } catch (Exception e) {
        //            System.out.println("Error al leer los balastos añadidos.");
        //            e.printStackTrace();
        //        }
        //
        //        return balastos;
        //</editor-fold>

        int numBalastos = Integer.parseInt(PropHandler.getProperty("scene.max.number"));
        int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));
        int usedRegisters = Integer.parseInt(PropHandler.getProperty("scene.memory.registers"));
        int tamReg = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
        return UtilsJmodbus.getElementosEnMemoriaInt(numBalastos, dao, initOffset, usedRegisters, tamReg);

    }

    /**
     * Add a new balast.
     *
     * @param key
     * @return
     */
    @Override
    public void addElement(int writtenNumber) {
        //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
        //        try {
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));
        //
        //            int[] balastos = getAddedScenesCardArray();
        //
        //            //Add the new balast.
        //            balastos[writtenBalastNumber] = 1;
        //
        //            //Get a string with the bits of the selected values.
        //            String seleBal = "";
        //            for (int i : balastos) {
        //                seleBal = String.valueOf(i) + seleBal;
        //            }
        //
        //
        //
        //            //Get BitIntegers every 16 bits and store them in the card.
        //            ArrayList<BigInteger> name = Utils.getSelectedItems(seleBal);
        //            for (int i = name.size() - 1; i >= 0; i--) {
        //                int[] nameValues = {name.get(i).intValue()};
        //                dao.setRegValue(initOffset, nameValues);
        //                initOffset++;
        //            }
        //
        //        } catch (Exception e) {
        //            System.out.println("No se pudo escribir el balasto especificado!");
        //            e.printStackTrace();
        //        }
        //</editor-fold>
        
          try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));

            int[] scene = getAddedCardArray();// Corregida. Ya carga bien los grupos adheridos

            //Add the new Group.
            scene[writtenNumber] = 1;

            //Get a string with the bits of the selected values.
            String selScene = "";
            for (int i : scene) {
                selScene = String.valueOf(i) + selScene;
            }



            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = Utils.getSelectedItems(selScene);
            for (int i = name.size() - 1; i >= 0; i--) {
                int[] nameValues = {name.get(i).intValue()};
                dao.setRegValue(initOffset, nameValues);
                initOffset++;
            }

        } catch (Exception e) {
            int errCode = 4002;
            String err = "Error " + errCode + ". No se pudo escribir la escena especificado!";
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, err, "Error "+errCode, JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Delete the balast.
     *
     * @param key
     * @return
     */
    @Override
    public void deleteElement(int writtenNumber) {
        ////<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
        //        try {
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));
        //
        //            int[] balastos = getAddedScenesCardArray();
        ////            System.out.println("Offset: " + initOffset + ", group balasts: " + balastos);
        //
        //            //Delete the specified balast.
        //            balastos[writtenBalastNumber] = 0;
        //
        //            //Get a string with the bits of the selected values.
        //            String seleBal = "";
        //            for (int i : balastos) {
        //                seleBal = String.valueOf(i) + seleBal;
        //            }
        //
        //            //Get BitIntegers every 16 bits and store them in the card.
        //            ArrayList<BigInteger> name = Utils.getSelectedItems(seleBal);
        //            for (int i = name.size() - 1; i >= 0; i--) {
        //                int[] nameValues = {name.get(i).intValue()};
        //                dao.setRegValue(initOffset, nameValues);
        //                initOffset++;
        //            }
        //
        //        } catch (Exception e) {
        //            System.out.println("No se pudo eliminar el balasto especificado!");
        //            e.printStackTrace();
        //        }
        //</editor-fold>
        
         try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("scene.memory.added"));

            int[] scenes = getAddedCardArray();
//            System.out.println("Offset: " + initOffset + ", group balasts: " + balastos);

            //Delete the specified grupo.
            scenes[writtenNumber] = 0;

            //Get a string with the bits of the selected values.
            String seleScene = "";
            for (int i : scenes) {
                seleScene = String.valueOf(i) + seleScene;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = Utils.getSelectedItems(seleScene);
            for (int i = name.size() - 1; i >= 0; i--) {
                int[] nameValues = {name.get(i).intValue()};
                dao.setRegValue(initOffset, nameValues);
                initOffset++;
            }

        } catch (Exception e) {
            int errCode = 3002;
            String err = "Error " + errCode + ". No se pudo borrar el grupo especificado!";
            JOptionPane.showMessageDialog(null, err, "Error "+errCode, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
    }
}
