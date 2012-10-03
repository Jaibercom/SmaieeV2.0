/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.dao.Utils;
import com.isolux.bo.Grupo;
import com.isolux.dao.modbus.DAO4j;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class GrupoDAOJmodbus {

    private static DAOJmodbus dao;

    //CONSTRUCTOR
    public GrupoDAOJmodbus(DAOJmodbus dao) {
        this.dao = dao;
    }

    /**
     * Escribe un registro individual
     *
     * @param pos Posicion de registro
     * @param mode El modo en el que se quiere ese registro
     */
    public static void setSingleReg(int pos, int mode) {
        int[] values = {mode};
        dao.setRegValue(pos, values);
    }

    /**
     * Saves the group.
     *
     * @param group
     */
    public static boolean saveGroup(Grupo group) {
        boolean state = false;
        int groupNumber = group.getGroupNumber();

        try {

            //MODO Configuracion
            setSingleReg(0, 1);

            //Init offset.
            System.out.println("SAVING GROUP NUMBER: " + groupNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.groups"));
            int[] groupsArray = new int[Integer.parseInt(PropHandler.getProperty("group.memory.size"))];

            //Group number
//            Es de 10 caracteres porque el nombre es de 10 caracteres
            groupsArray[0] = groupNumber;// hay que validar el numero de grupo
            groupsArray[1] = 1;// ojo, esto si esta correcto?

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

            int nameOffset = 2;
            ArrayList<BigInteger> balastNameBytes = UtilsJmodbus.getNameBytesReverse(group.getName());
            int size = balastNameBytes.size();
            for (int i = 0; i < 5; i++) {
                if (i < size) {
                    groupsArray[nameOffset] = balastNameBytes.get(i).intValue();
                } else {
                    groupsArray[nameOffset] = 0;
                }
                nameOffset++;
            }

            //balastos afectados
            int[] balastos = group.getBalastosAfectados();

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = Utils.getSelectedItems(seleBal);
            int affectedBalasts = 7;
            for (int i = name.size() - 1; i >= 0; i--) {
                groupsArray[affectedBalasts] = name.get(i).intValue();
                affectedBalasts++;
            }

            dao.setRegValue(initOffset, groupsArray);
            addGroup(groupNumber);

            //MODO
            setSingleReg(0, 0);

            System.out.println("Group number " + groupNumber + " saved.");

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
    public static boolean deleteGroup(String groupNumbers) {
        boolean state = false;
        int groupNumber = Integer.parseInt(groupNumbers);

        try {

            //MODO
            setSingleReg(0, 1);

            //Init offset.
            System.out.println("DELETING GROUP NUMBER: " + groupNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.groups"));
            int[] groupsArray = new int[Integer.parseInt(PropHandler.getProperty("group.memory.size"))];

            //Group number
            groupsArray[0] = groupNumber;
            groupsArray[1] = 0;

            //name
            try {
                int nameOffset = 2;
                for (int i = 0; i < 5; i++) {
                    groupsArray[nameOffset] = 0;
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
                groupsArray[affectedBalasts] = 0;
                affectedBalasts++;
            }

            dao.setRegValue(initOffset, groupsArray);
            deleteGroup(groupNumber);

            //MODO
            setSingleReg(0, 0);

            System.out.println("Group number " + groupNumber + " deleted.");

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
     * @param groupNumber
     */
    public static Grupo readGroup(int groupNumber) {
        Grupo grupo = new Grupo();

        try {

            //MODO
            setSingleReg(0, 1);//Pone el smaiee en modo run en 0 y modo configuracion en 1 (en este caso es 1)

            System.out.println("READING GROUP NUMBER: " + groupNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.groups"));

            //Group number
            setSingleReg(initOffset, groupNumber);
            int[] groupArray = dao.getRegValue(initOffset, Integer.parseInt(PropHandler.getProperty("group.memory.size")));


            //Group number
            grupo.setGroupNumber(groupNumber);
            setSingleReg(1, 4);

            //activation
            grupo.setActivation(groupArray[1]);

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

                String nomb = UtilsJmodbus.desencriptarNombre(groupArray, 2, 5);
                grupo.setName(nomb);

            } catch (Exception e) {
                System.out.println("Error al leer el nombre del grupo.");
            }

            //balastos afectados
            try {
                int balastsOffset = 7;
                int tamReg = 16;
                int[] balastos = grupo.getBalastosAfectados();
                float bytesToRead = balastos.length / tamReg;
                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();

                //Get the bytes from the card.
                for (int i = 0; i < bytesToRead; i++) {
                    affectedBalasts.add(new BigInteger(String.valueOf(groupArray[balastsOffset] & 0xFFFF)));
                    balastsOffset++;
                }

                String balastName = "";
                for (BigInteger nameByte : affectedBalasts) {
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
                System.out.println("Error al leer los balastos afectados por el grupo.");
            }

            //MODO
            setSingleReg(0, 0);

            System.out.println("Group number " + groupNumber + " readed.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return grupo;
    }

    public static ArrayList<String> getAddedGroups() {

        //<editor-fold defaultstate="collapsed" desc="Código antiguo corregido">
//        ArrayList<String> addedGroups = new ArrayList<String>();
//        int numGrupos = Integer.parseInt(PropHandler.getProperty("group.max.number"));
//        if (numGrupos < 16) {
//            numGrupos = 16;
//        }
//        
//        
//        try {
//            int initOffset = Integer.parseInt(PropHandler.getProperty("group.memory.added"));
//            int usedRegisters = Integer.parseInt(PropHandler.getProperty("group.memory.registers"));
//            
//            //                int balastsOffset = initOffset;
//            int tamReg = 8;//cambiado por extencion de signo
//            int[] grupos = new int[numGrupos];
//            float bytesToRead = (grupos.length / tamReg) < 1 ? 1 : (grupos.length / tamReg);
//            ArrayList<BigInteger> affectedGroups = new ArrayList<BigInteger>();
//            int[] addedG = dao.getRegValue(initOffset, usedRegisters);
//            //En este lugar esta el problema de los nombres de los grupos
//            //Get the bytes from the card.
//            for (int i = 0; i < bytesToRead; i++) {
//                affectedGroups.add(new BigInteger(String.valueOf(addedG[i]&0x00FF)));
//            }
//            
//            String balastName = "";
//            for (BigInteger nameByte : affectedGroups) {
//                String value = nameByte.toString(2);
//                value = Utils.getCeros(value);
//                //                    balastName = value + balastName;
//                balastName= balastName+value;
//            }
//            
//            int j=0;
//            // lee los 16 bits littlendian de derecha a izquierda
//            for (int i = grupos.length -1; i >= 0; i--) {
//                String bit = String.valueOf(balastName.charAt(i));
//                /*Aqui se establecen la lista de bits activos y por tanto la lista de
//                 * los grupos. Se mira el registro 40 y se lee 1 registro (de 16 bits) y con el se
//                 * determinan el numero de grupos activos.
//                 *
//                 */
//                grupos[j] = Integer.parseInt(bit);
//                j++;
//            }
//            
//            //Get an ArrayList with the result
//            for (int i = 0; i < grupos.length; i++) {
//                if (grupos[i] != 0) {
//                    addedGroups.add(String.valueOf(i));//se agrega a la lista de grupos adheridos
//                }
//                
//            }
//            
//        } catch (Exception e) {
//            System.out.println("Error al leer los grupos añadidos.");
//        }
//        
//        return addedGroups;
        //</editor-fold>

        ArrayList<String> elementosEnMemoria;
        try {

            int numGrupos = Integer.parseInt(PropHandler.getProperty("group.max.number"));
            int initOffset = Integer.parseInt(PropHandler.getProperty("group.memory.added"));
            int usedRegisters = Integer.parseInt(PropHandler.getProperty("group.memory.registers"));
            int tamReg = 8;
//            int tamReg = Integer.parseInt(PropHandler.getProperty("registro.tamanio.lectura"));
            elementosEnMemoria = UtilsJmodbus.getElementosEnMemoria(numGrupos, dao, initOffset, usedRegisters, tamReg);



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
    public static int[] getAddedGroupsCardArray() {

        //       //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
//        int numgrupos = Integer.parseInt(PropHandler.getProperty("group.max.number"));
        //        if (numgrupos < 16) {
        //            numgrupos = 16;
        //        }
        //
        //        int[] gruposAdheridos = new int[numgrupos];
        //        try {
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("group.memory.added"));
        //            int usedRegisters = Integer.parseInt(PropHandler.getProperty("group.memory.registers"));
        //
        //            int balastsOffset = initOffset;
        //            int tamReg = 8;
        //
        //            int bytesToRead = (gruposAdheridos.length / tamReg);
        //            ArrayList<BigInteger> affectedGroups = new ArrayList<BigInteger>();
        //
        //            int[] addedIns = dao.getRegValue(initOffset, usedRegisters);
        //
        //            //Get the bytes from the card.
        //            for (int i = 0; i < bytesToRead; i++) {
        //                affectedGroups.add(new BigInteger(String.valueOf(addedIns[i] & 0x00FF)));
        //            }
        //
        //            String balastName = "";
        //            for (BigInteger nameByte : affectedGroups) {
        //                String value = nameByte.toString(2);
        //                value = Utils.getCeros(value);
        //                balastName = balastName + value;
        //            }
        //
        //            int j = 0;
        //            for (int i = gruposAdheridos.length - 1; i >= 0; i--) {
        //                String bit = String.valueOf(balastName.charAt(i));
        //                gruposAdheridos[j] = Integer.parseInt(bit);
        //                j++;
        //            }
        //
        //        } catch (Exception e) {
        //            System.out.println("Error al leer los grupos añadidos.");
        //        }
        //
        //        return gruposAdheridos;
        //</editor-fold>

        int numBalastos = Integer.parseInt(PropHandler.getProperty("group.max.number"));
        int initOffset = Integer.parseInt(PropHandler.getProperty("group.memory.added"));
        int usedRegisters = Integer.parseInt(PropHandler.getProperty("group.memory.registers"));
        int tamReg = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
        return UtilsJmodbus.getElementosEnMemoriaInt(numBalastos, dao, initOffset, usedRegisters, tamReg);
    }

    /**
     * Add a new group.
     *
     * @param key
     * @return
     */
    public static void addGroup(int writtenGroupNumber) {
        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("group.memory.added"));

            int[] grupos = getAddedGroupsCardArray();// Corregida. Ya carga bien los grupos adheridos

            //Add the new Group.
            grupos[writtenGroupNumber] = 1;

            //Get a string with the bits of the selected values.
            String seleGrupo = "";
            for (int i : grupos) {
                seleGrupo = String.valueOf(i) + seleGrupo;
            }



            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = Utils.getSelectedItems(seleGrupo);
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
     * Borra el grupo.
     *
     * @param key
     * @return
     */
    public static void deleteGroup(int writtenBalastNumber) {
        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("group.memory.added"));

            int[] grupos = getAddedGroupsCardArray();
//            System.out.println("Offset: " + initOffset + ", group balasts: " + balastos);

            //Delete the specified grupo.
            grupos[writtenBalastNumber] = 0;

            //Get a string with the bits of the selected values.
            String seleGrupo = "";
            for (int i : grupos) {
                seleGrupo = String.valueOf(i) + seleGrupo;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = Utils.getSelectedItems(seleGrupo);
            for (int i = name.size() - 1; i >= 0; i--) {
                int[] nameValues = {name.get(i).intValue()};
                dao.setRegValue(initOffset, nameValues);
                initOffset++;
            }

        } catch (Exception e) {
            System.out.println("No se pudo eliminar el grupo especificado!");
            e.printStackTrace();
        }
    }
}
