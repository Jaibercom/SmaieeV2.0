/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.dao.Utils;
import com.isolux.bo.Entrada;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author Juan Diego Toro Cano.
 */
public class EntradaDAOJmodbus {

    private static DAOJmodbus dao;

    //CONSTRUCTOR
    public EntradaDAOJmodbus(DAOJmodbus dao) {
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
     * Saves the balast.
     *
     * @param in
     */
    public static boolean saveIn(Entrada in) {
        boolean state = false;
        int inNumber = in.getNumeroEntrada();

        try {

            //MODO
            setSingleReg(0, 1);

            System.out.println("SAVING IN NUMBER: " + inNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.ins"));
            int[] inArray = new int[Integer.parseInt(PropHandler.getProperty("in.memory.size"))];


            inArray[0] = inNumber;
            inArray[1] = 1;
            inArray[2] = in.getTipo();
            inArray[3] = in.getComportamiento1();
            inArray[4] = in.getComportamiento2();
            inArray[5] = in.getNivelON();
            inArray[6] = in.getNivelOFF();
            inArray[7] = in.getTiempoRetardo();
            inArray[8] = in.getGanancia();
            inArray[9] = (int) in.getNivIlumxvoltio();
            float nivel = (in.getNivelDeseado() / (in.getNivIlumxvoltio() * 10)) * 100;
            inArray[10] = (int) nivel;
            inArray[11] = in.getTipoSalida();
            //valor ADC
            inArray[12] = in.getValorADC(); //in.getValorADC();



            //balasto
            int[] balastos = in.getBalastos();

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Organizamos los balastros afectados
            ArrayList<BigInteger> name = UtilsJmodbus.getSelectedItems(seleBal);
            int affectedBalasts = 13;
            for (int i = name.size() - 1; i >= 0; i--) {
                inArray[affectedBalasts] = name.get(i).intValue();
                affectedBalasts=affectedBalasts+2;
            }



            //grupos afectados
            int[] grupos = in.getGrupos();

            //Get a string with the bits of the selected values.
            String seleGroup = "";
            for (int i : grupos) {
                seleGroup = String.valueOf(i) + seleGroup;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> groups = UtilsJmodbus.getSelectedItems(seleGroup);
            int affectedGroups = 21;
            for (int i = groups.size() - 1; i >= 0; i--) {
                inArray[affectedGroups] = groups.get(i).intValue();
                affectedGroups=affectedGroups+2;
            }



            //escenas
            int[] escenas = in.getEscenas();

            //Get a string with the bits of the selected values.
            String seleScene = "";
            for (int i : escenas) {
                seleScene = String.valueOf(i) + seleScene;
            }

            //Escenas afectadas, las escribimos en el array
            ArrayList<BigInteger> scenes = UtilsJmodbus.getSelectedItems(seleScene);
            int affectedScenes = 23;
            for (int i = scenes.size() - 1; i >= 0; i--) {
                inArray[affectedScenes] = scenes.get(i).intValue();
                affectedScenes=affectedScenes+2;
            }

//          Escribimos todo el conjunto
            dao.setRegValue(initOffset, inArray);
         
   
            

//            Agregamos el numero entrada a los elementos activos
            addIn(inNumber);

            System.out.println("In number " + inNumber + " saved.");

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
     * Deletes the balast.
     *
     * @param balastNumber
     * @return
     */
    public static boolean deleteIn(String balastNumber) {
        boolean state = false;
        int inNumber = Integer.parseInt(balastNumber);
        Entrada entrada = new Entrada();

        try {

            //MODO
            setSingleReg(0, 1);

            System.out.println("DELETING IN NUMBER: " + inNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.ins"));
            int[] inArray = new int[Integer.parseInt(PropHandler.getProperty("in.memory.size"))];


            inArray[0] = inNumber;
            inArray[1] = 0;
            inArray[2] = 0;
            inArray[3] = 0;
            inArray[4] = 0;
            inArray[5] = 0;
            inArray[6] = 0;
            inArray[7] = 0;
            inArray[8] = 0;
            inArray[9] = 0;
            inArray[10] = 0;
            inArray[11] = 0;



            //balasto
            int[] balastos = entrada.getBalastos();

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = UtilsJmodbus.getSelectedItems(seleBal);
            int affectedBalasts = 12;
            for (int i = name.size() - 1; i >= 0; i--) {
                inArray[affectedBalasts] = 0;
                affectedBalasts++;
            }



            //grupo
            int[] grupos = entrada.getGrupos();

            //Get a string with the bits of the selected values.
            String seleGroup = "";
            for (int i : grupos) {
                seleGroup = String.valueOf(i) + seleGroup;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> groups = UtilsJmodbus.getSelectedItems(seleGroup);
            int affectedGroups = 16;
            for (int i = groups.size() - 1; i >= 0; i--) {
                inArray[affectedGroups] = 0;
                affectedGroups++;
            }



            //escenas
            int[] escenas = entrada.getEscenas();

            //Get a string with the bits of the selected values.
            String seleScene = "";
            for (int i : escenas) {
                seleScene = String.valueOf(i) + seleScene;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> scenes = UtilsJmodbus.getSelectedItems(seleScene);
            int affectedScenes = 17;
            for (int i = scenes.size() - 1; i >= 0; i--) {
                inArray[affectedScenes] = 0;
                affectedScenes++;
            }

            //valor ADC
            inArray[18] = 0; //in.getValorADC();

            dao.setRegValue(initOffset, inArray);
            deleteIn(inNumber);

            System.out.println("In number " + inNumber + " deleted.");

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
     * Obtiene una entrada desde la tarjeta. Carga todos los parametros de la
     * entrada que corresponde a
     *
     * @param inNumber Numero de la entrada
     */
    public static Entrada readIn(int inNumber) {
        Entrada in = new Entrada();
        boolean state = false;

        try {

            //MODO
            setSingleReg(0, 1);

            System.out.println("READING IN NUMBER: " + inNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.ins"));
            int tam = Integer.parseInt(PropHandler.getProperty("in.memory.size"));


            //In number
            setSingleReg(initOffset, inNumber);
            int[] inArray = dao.getRegValue(initOffset, tam);

            in.setNumeroEntrada(inNumber);
            in.setActivacion(inArray[1]);
            in.setTipo(inArray[2]);
            in.setComportamiento1(inArray[3]);
            in.setComportamiento2(inArray[4]);
            in.setNivelON(inArray[5]);
            in.setNivelOFF(inArray[6]);
            in.setTiempoRetardo(inArray[7]);
            in.setGanancia(inArray[8]);
            in.setNivIlumxvoltio(inArray[9] + 256);
            in.setNivelDeseado(inArray[10]);
            in.setTipoSalida(inArray[11]);



            //balasto
            //          //<editor-fold defaultstate="collapsed" desc="Balastos afectados Codigo antiguo">
//            try {
            //                int balastsOffset = 12;
            //                int tamReg = 16;
            //                int[] balastos = in.getBalastos();
            //                float bytesToRead = balastos.length / tamReg;
            //                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
            //
            //                //Get the bytes from the card.
            //                for (int i = 0; i < bytesToRead; i++) {
            //                    affectedBalasts.add(new BigInteger(String.valueOf(inArray[balastsOffset]&0xFFFF)));
            //                    balastsOffset++;
            //                }
            //
            //                String balastName = "";
            //                for (BigInteger nameByte : affectedBalasts) {
            //                    String value = nameByte.toString(2);
            //                    value = UtilsJmodbus.getCeros(value);
            //                    balastName = value + balastName;
            //                }
            //                int j=0;
            //                for (int i = balastos.length -1; i >= 0; i--) {
            //                    String bit = String.valueOf(balastName.charAt(i));
            //                    balastos[j] = Integer.parseInt(bit);
            //                    j++;
            //                }
            //            } catch (Exception e) {
            //                System.out.println("Error al leer los balastos afectados por la escena.");
            //            }
            //
            //</editor-fold>

            try {
                int balastsOffset = 13;
                int tamReg = 8;
                int[] balastos = in.getBalastos();
//            float bytesToRead = balastos.length / tamReg;
                System.out.println("Leyendo balastros afectados por la entrada");
                balastos = UtilsJmodbus.obtenerElementosAfectados(inArray, balastsOffset, 64, tamReg, 8);
                in.setBalastos(balastos);
            } catch (Exception e) {
                System.out.println("Problemas cargando los grupos afectados por la entrada " + in.toString());
            }
//            //grupo
            //<editor-fold defaultstate="collapsed" desc="Codigo antiguo de obtener grupos afectados ">
            //            try {
            //                int groupsOffset = 16;
            //                int tamReg = 8;
            //                int[] grupos = in.getGrupos();
            //                float bytesToRead = grupos.length / tamReg;
            //                ArrayList<BigInteger> affectedGroups = new ArrayList<BigInteger>();
            //
            //                //Get the bytes from the card.
            //                for (int i = 0; i < bytesToRead; i++) {
            //                    affectedGroups.add(new BigInteger(String.valueOf(inArray[groupsOffset] & 0xFFFF)));
            //                    groupsOffset++;
            //                }
            //
            //                String groupName = "";
            //                for (BigInteger nameByte : affectedGroups) {
            //                    String value = nameByte.toString(2);
            //                    value = UtilsJmodbus.getCeros(value);
            //                    groupName = value + groupName;
            //                }
            //
            //                int j = 0;
            //                for (int i = grupos.length - 1; i >= 0; i--) {
            //                    String bit = String.valueOf(groupName.charAt(i));
            //                    grupos[j] = Integer.parseInt(bit);
            //                    j++;
            //                }
            //            } catch (Exception e) {
            //                System.out.println("Error al leer los balastos afectados por la escena.");
            //            }
            //</editor-fold>

            try {
//                lectura de grupos, por lo cual el offset es 21
                int groupsOffset = 21;
                int tamReg = 8;
                int[] grupos = in.getGrupos();
                grupos = UtilsJmodbus.obtenerElementosAfectados(inArray, groupsOffset, 16, tamReg, 8);
                in.setGrupos(grupos);


            } catch (Exception e) {
                System.out.println("Problemas cargando los grupos afectados por la entrada " + in.toString());
                e.printStackTrace();
            }


            //escenas afectadas
            //<editor-fold defaultstate="collapsed" desc="Escenas afectadas antiguo">
            //            try {
            //                int sceneOffset = 17;
            //                int tamReg = 16;
            //                int[] escenas = in.getEscenas();
            //                float bytesToRead = escenas.length / tamReg;
            //                ArrayList<BigInteger> affectedScenes = new ArrayList<BigInteger>();
            //
            //                //Get the bytes from the card.
            //                for (int i = 0; i < bytesToRead; i++) {
            //                    affectedScenes.add(new BigInteger(String.valueOf(inArray[sceneOffset] & 0xFFFF)));
            //                    sceneOffset++;
            //                }
            //
            //                String sceneName = "";
            //                for (BigInteger nameByte : affectedScenes) {
            //                    String value = nameByte.toString(2);
            //                    value = UtilsJmodbus.getCeros(value);
            //                    sceneName = value + sceneName;
            //                }
            //
            //                int j = 0;
            //                for (int i = escenas.length - 1; i >= 0; i--) {
            //                    String bit = String.valueOf(sceneName.charAt(i));
            //                    escenas[j] = Integer.parseInt(bit);
            //                    j++;
            //                }
            //            } catch (Exception e) {
            //                System.out.println("Error al leer los balastos afectados por la escena.");
            //            }
            //</editor-fold>

            try {
                int sceneOffset = 23;
                int tamReg = 8;
                int[] escenas = in.getEscenas();

                escenas = UtilsJmodbus.obtenerElementosAfectados(inArray, sceneOffset, 16, tamReg, 8);
                in.setEscenas(escenas);

            } catch (Exception e) {
                System.out.println("Error cargando las escenas afectadas por la entrada " + in.toString());
                e.printStackTrace();
            }


            //valor ADC
            in.setValorADC(inArray[18]);


            //MODO
            setSingleReg(0, 0);

            System.out.println("In number " + inNumber + " readed.");

            state = true;
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        }

        return in;
    }

    public static ArrayList<String> getAddedIns() {
        //       //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
//        ArrayList<String> addedBalasts = new ArrayList<String>();
        ////        DAO4j dao = new DAO4j();
        //        int numBalastos = Integer.parseInt(PropHandler.getProperty("in.max.number"));
        //        if (numBalastos < 16) {
        //            numBalastos = 16;
        //        }
        //
        //
        //        try {
        //                int initOffset = Integer.parseInt(PropHandler.getProperty("in.memory.added"));
        //                int usedRegisters = Integer.parseInt(PropHandler.getProperty("in.memory.registers"));
        //
        //                int balastsOffset = initOffset;
        //                int tamReg = 16;
        //                int[] balastos = new int[numBalastos];
        //                float bytesToRead = (balastos.length / tamReg) < 1 ? 1 : (balastos.length / tamReg);
        //                ArrayList<BigInteger> affectedIns = new ArrayList<BigInteger>();
        //                int[] addedIns = dao.getRegValue(initOffset, usedRegisters);
        //
        //                //Get the bytes from the card.
        //                for (int i = 0; i < bytesToRead; i++) {
        //                    affectedIns.add(new BigInteger(String.valueOf( (addedIns[i]+256) &0xFFFF)));
        //                }
        //
        //                String balastName = "";
        //                for (BigInteger nameByte : affectedIns) {
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
        //                       addedBalasts.add(String.valueOf(i));
        //                    }
        //
        //                }
        //
        //            } catch (Exception e) {
        //                System.out.println("Error al leer los balastos añadidos.");
        //            }
        //
        //        return addedBalasts;
        //</editor-fold>

        ArrayList<String> elementosEnMemoria;
        try {

            int numEntradas = Integer.parseInt(PropHandler.getProperty("in.max.number"));
            int initOffset = Integer.parseInt(PropHandler.getProperty("in.memory.added"));
            int usedRegisters = Integer.parseInt(PropHandler.getProperty("in.memory.registers"));
            int tamReg = 8;
//            int tamReg = Integer.parseInt(PropHandler.getProperty("registro.tamanio.lectura"));
            elementosEnMemoria = UtilsJmodbus.getElementosEnMemoria(numEntradas, dao, initOffset, usedRegisters, 2, 2, tamReg);



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
    public static int[] getAddedInsCardArray() {
        //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
        //        int numBalastos = Integer.parseInt(PropHandler.getProperty("in.max.number"));
        //        if (numBalastos < 16) {
        //            numBalastos = 16;
        //        }
        //
        //        int[] balastos = new int[numBalastos];
        //        try {
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("in.memory.added"));
        //            int usedRegisters = Integer.parseInt(PropHandler.getProperty("in.memory.registers"));
        //
        //            int balastsOffset = initOffset;
        //            int tamReg = 16;
        //
        //            float bytesToRead = (balastos.length / tamReg) < 1 ? 1 : (balastos.length / tamReg);
        //            ArrayList<BigInteger> affectedIns = new ArrayList<BigInteger>();
        //            int[] addedIns = dao.getRegValue(initOffset, usedRegisters);
        //
        //            //Get the bytes from the card.
        //            for (int i = 0; i < bytesToRead; i++) {
        ////                    affectedIns.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
        //                affectedIns.add(new BigInteger(String.valueOf((addedIns[i] + 256) & 0xFFFF)));
        ////                    balastsOffset++;
        //            }
        //
        //            String balastName = "";
        //            for (BigInteger nameByte : affectedIns) {
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

        int numBalastos = Integer.parseInt(PropHandler.getProperty("in.max.number"));
        int initOffset = Integer.parseInt(PropHandler.getProperty("in.memory.added"));
        int usedRegisters = Integer.parseInt(PropHandler.getProperty("in.memory.registers"));
        int tamReg = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
        return UtilsJmodbus.getElementosEnMemoriaInt(numBalastos, dao, initOffset, usedRegisters, tamReg, 2, 8);
    }

    /**
     * Método que agrega una nueva entrada al array de entradas activas
     *
     * @param writtenBalastNumber Numero de la entrada que ha de ser agregada al
     * array de entradas activas
     *
     */
    public static void addIn(int writtenBalastNumber) {

        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("in.memory.added"));

            int[] balastos = getAddedInsCardArray();

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
    public static void deleteIn(int writtenBalastNumber) {
        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("in.memory.added"));

            int[] balastos = getAddedInsCardArray();
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
            System.out.println("No se pudo eliminar la entrada especificada!");
            e.printStackTrace();
        }
    }
}
