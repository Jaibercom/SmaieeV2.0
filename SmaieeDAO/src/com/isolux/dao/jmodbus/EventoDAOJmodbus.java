/*
 * To change this template, choose Tools | Templates
 * and open the template event the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.bo.Elemento;
import com.isolux.bo.Evento;
import com.isolux.dao.Utils;
import com.isolux.dao.modbus.DAO4j;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class EventoDAOJmodbus extends OperacionesDaoJModbus {

    private static int initOffset = 0;
    private static DAOJmodbus dao;

    //CONSTRUCTOR.
    public EventoDAOJmodbus(DAOJmodbus dao) {
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
     * Saves the balast.
     *
     * @param evento
     */
    @Override
    public boolean saveElement(Elemento evento1) {
//        DAO4j dao = new DAO4j();

        Evento evento = (Evento) evento1;
        boolean state = false;
        int eventNumber = evento.getNumeroEvento();

        try {

            //MODO Config
            setSingleReg(0, 1);

            //Init offset
            int eventoInitOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.events"));
            int[] eventArray = new int[Integer.parseInt(PropHandler.getProperty("event.memory.size"))];

            System.out.println("SAVING EVENT NUMBER: " + eventNumber);

            //numeroEvento
            eventArray[0] = eventNumber;
            eventArray[1] = 0;// activacion
            eventArray[2] = evento.getTipoSalida();
            //nombre
            int nameOffset = 3;
            //<editor-fold defaultstate="collapsed" desc="Codigo viejo nombre">
            //            ArrayList<BigInteger> balastNameBytes = Utils.getNameBytesReverse(evento.getNombre());
            //            int size = balastNameBytes.size();
            //            for (int i = 0; i < 5; i++) {
            //                if (i < size) {
            //                    eventArray[nameOffset]=balastNameBytes.get(i).intValue();
            //                } else {
            //                    eventArray[nameOffset]=0;
            //                }
            //                nameOffset++;
            //            }
            //</editor-fold>
            UtilsJmodbus.encriptarNombre(eventArray, nameOffset, evento.getNombre(), 5);


            eventArray[8] = evento.getPorFechaODias();
            eventArray[9] = evento.getDia();
            eventArray[10] = evento.getMes();
            eventArray[11] = evento.getAnho();
            eventArray[12] = evento.getHora();
            eventArray[13] = evento.getMinuto();
            eventArray[14] = evento.getDiaYrepetir();//dia afectado

            //nivelBalasto
            int balastosOffset = 15;
            int[] nivelBalastos = evento.getNivelBalasto();
            for (int i = 0; i < nivelBalastos.length; i++) {
                eventArray[balastosOffset] = nivelBalastos[i];
                balastosOffset++;
            }


            //nivelGrupo
            int gruposOffset = 79;
            int[] nivelGrupos = evento.getNivelGrupo();
            for (int i = 0; i < nivelGrupos.length; i++) {
                eventArray[gruposOffset] = nivelGrupos[i];
                gruposOffset++;
            }


            //           //<editor-fold defaultstate="collapsed" desc="Codigo viejo Grupos afectados">
            //            //gruposAfectados
            //            int[] groups = evento.getGruposAfectados();
            //
            //            //Get a string with the bits of the selected values.
            //            String seleGroup = "";
            //            for (int i : groups) {
            //                seleGroup = String.valueOf(i) + seleGroup;
            //            }
            //
            //            //Get BitIntegers every 16 bits and store them in the card.
            //            ArrayList<BigInteger> groupsRegisters = Utils.getSelectedItems(seleGroup);
            //            int affectedGroups = 97;
            //            for (int i = groupsRegisters.size() - 1; i >= 0; i--) {
            //                eventArray[affectedGroups]=groupsRegisters.get(i).intValue();
            //                affectedGroups++;
            //            }
            //
            //</editor-fold>

            //grupos afectados
            int[] grupos = evento.getGruposAfectados();

            //Get a string with the bits of the selected values.
            String seleGroup = "";
            for (int i : grupos) {
                seleGroup = String.valueOf(i) + seleGroup;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> groups = UtilsJmodbus.getSelectedItems(seleGroup);
            int affectedGroups = 95;
            for (int i = groups.size() - 1; i >= 0; i--) {
                eventArray[affectedGroups] = groups.get(i).intValue();
                affectedGroups = affectedGroups + 2;
            }


            //            //<editor-fold defaultstate="collapsed" desc="codigo viejo Escenas afectadas">
            //            //escenasAfectadas
            //            int[] scenes = evento.getEscenasAfectadas();
            //
            //            //Get a string with the bits of the selected values.
            //            String seleScene = "";
            //            for (int i : scenes) {
            //                seleScene = String.valueOf(i) + seleScene;
            //            }
            //
            //            //Get BitIntegers every 16 bits and store them in the card.
            //            ArrayList<BigInteger> scenesRegisters = Utils.getSelectedItems(seleScene);
            //            int affectedScenes = 98;
            //            for (int i = scenesRegisters.size() - 1; i >= 0; i--) {
            //                eventArray[affectedScenes]=scenesRegisters.get(i).intValue();
            //                affectedScenes++;
            //            }
            //</editor-fold>
            //escenas
            int[] escenas = evento.getEscenasAfectadas();

            //Get a string with the bits of the selected values.
            String seleScene = "";
            for (int i : escenas) {
                seleScene = String.valueOf(i) + seleScene;
            }

            //Escenas afectadas, las escribimos en el array
            ArrayList<BigInteger> scenes = UtilsJmodbus.getSelectedItems(seleScene);
            int affectedScenes = 97;
            for (int i = scenes.size() - 1; i >= 0; i--) {
                eventArray[affectedScenes] = scenes.get(i).intValue();
                affectedScenes = affectedScenes + 2;
            }



            ////<editor-fold defaultstate="collapsed" desc="codigo viejo balastos afectados">
            //            //balastosAfectados
            //            int[] balastos = evento.getBalastosAfectados();
            //
            //            //Get a string with the bits of the selected values.
            //            String seleBal = "";
            //            for (int i : balastos) {
            //                seleBal = String.valueOf(i) + seleBal;
            //            }
            //
            //            //Get BitIntegers every 16 bits and store them in the card.
            //            ArrayList<BigInteger> balastsRegisters = Utils.getSelectedItems(seleBal);
            //            int affectedBalasts = 77;
            //            for (int i = balastsRegisters.size() - 1; i >= 0; i--) {
            //                eventArray[affectedBalasts]=balastsRegisters.get(i).intValue();
            //                affectedBalasts++;
            //            }
            //</editor-fold>
            //balasto
            int[] balastos = evento.getBalastosAfectados();

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Organizamos los balastros afectados
            ArrayList<BigInteger> name = UtilsJmodbus.getSelectedItems(seleBal);
            int affectedBalasts = 99;
            for (int i = name.size() - 1; i >= 0; i--) {
                eventArray[affectedBalasts] = name.get(i).intValue();
                affectedBalasts = affectedBalasts + 2;
            }


//          Guardamos la info con el dao
            dao.setRegValue(eventoInitOffset, eventArray);
            addElement(eventNumber);

            //MODO
            setSingleReg(0, 0);

            System.out.println("Event number " + eventNumber + " saved.");

            state = true;
        } catch (Exception e) {
            state = false;
//            Logger.getLogger(EventoDAOJmodbus.class.getName())log(Level.SEVERE);
            e.printStackTrace();
        }

        return state;
    }

    /**
     * Método que borra el evento como tal de la memoria. Borra toda la
     * información que tiene un evento relacionada, como numero, nombre, etc.
     * Modifica el registro a partir del offset en el mapa de memoria que en
     * este caso es 5000 .
     *
     * @param eventNumbers Numero del evento que se pretende borrar.
     *
     */
    @Override
    public boolean deleteElement(String eventNumbers) {

        boolean state = false;
        int eventNumber = Integer.parseInt(eventNumbers);

        try {

            //MODO
            setSingleReg(0, 1);

            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.events"));
            int[] eventArray = new int[Integer.parseInt(PropHandler.getProperty("event.memory.size"))];

            System.out.println("DELETING EVENT NUMBER: " + eventNumber);

            //numeroEvento
            eventArray[0] = eventNumber;
            eventArray[1] = 0;
            eventArray[2] = 0;
            //nombre
            int nameOffset = 3;
            for (int i = 0; i < 5; i++) {
                eventArray[nameOffset] = 0;
                nameOffset++;
            }

            eventArray[8] = 0;
            eventArray[9] = 0;
            eventArray[10] = 0;
            eventArray[11] = 0;
            eventArray[12] = 0;
            eventArray[13] = 0;
            eventArray[14] = 0;

            //nivelBalasto
            Evento evento = new Evento();
            int balastosOffset = 15;
            int[] nivelBalastos = evento.getNivelBalasto();
            for (int i = nivelBalastos.length - 1; i >= 0; i--) {
                eventArray[balastosOffset] = nivelBalastos[i];
                balastosOffset++;
            }

            //balastosAfectados
            int[] balastos = evento.getBalastosAfectados();

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : balastos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> balastsRegisters = Utils.getSelectedItems(seleBal);
            int affectedBalasts = 97;
            for (int i = balastsRegisters.size() - 1; i >= 0; i--) {
                eventArray[affectedBalasts] = 0;
                affectedBalasts++;
            }

            //nivelGrupo
            int gruposOffset = 79;
            int[] nivelGrupos = evento.getNivelGrupo();
            for (int i = nivelGrupos.length - 1; i >= 0; i--) {
                eventArray[gruposOffset] = 0;
                gruposOffset++;
            }

            //gruposAfectados
            int[] groups = evento.getGruposAfectados();

            //Get a string with the bits of the selected values.
            String seleGroup = "";
            for (int i : groups) {
                seleGroup = String.valueOf(i) + seleGroup;
            }

            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> groupsRegisters = Utils.getSelectedItems(seleGroup);
            int affectedGroups = 95;
            for (int i = groupsRegisters.size() - 1; i >= 0; i--) {
                eventArray[affectedGroups] = 0;
                affectedGroups++;
            }

            //escenasAfectadas
            int[] scenes = evento.getEscenasAfectadas();

            //Get a string with the bits of the selected values.
            String seleScene = "";
            for (int i : scenes) {
                seleScene = String.valueOf(i) + seleScene;
            }


            ArrayList<BigInteger> scenesRegisters = Utils.getSelectedItems(seleScene);
            int affecedScenes = 96;
            for (int i = scenesRegisters.size() - 1; i >= 0; i--) {
                eventArray[affecedScenes] = 0;
                affecedScenes++;
            }

            //Out type
            eventArray[2] = 0;

            
            
             //SAVE EVENT
            deleteElement(eventNumber);
            
            dao.setRegValue(initOffset, eventArray);
            
           
            

            //MODO
            setSingleReg(0, 0);

            System.out.println("Event number " + eventNumber + " deleted.");

            state = true;
        } catch (Exception e) {
            state = false;
            Logger.getLogger(EventoDAOJmodbus.class.getName()).log(Level.SEVERE, "Error borrando el evento", e);
//            e.printStackTrace();
        }

        return state;
    }

    /**
     * Get a balast from the card.
     *
     * @param balasto
     */
    @Override
    public Evento readElement(int eventNumber) {
        Evento evento = new Evento();

        try {

            //MODO
            setSingleReg(0, 1);

            System.out.println("READING EVENT NUMBER: " + eventNumber);
            int eventoOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.events"));

            //numeroEvento
            setSingleReg(eventoOffset, eventNumber);
            int[] eventArray = dao.getRegValue(eventoOffset, Integer.parseInt(PropHandler.getProperty("event.memory.size")));

            // activacion
            evento.setTipoSalida(eventArray[1]);

            //Out type
            evento.setTipoSalida(eventArray[2]);


            //nombre//<editor-fold defaultstate="collapsed" desc="Codigo antiguo obtener nombre">
            //            int nameOffset = 3;
            //            ArrayList<BigInteger> balastNameBytes = new ArrayList<BigInteger>();
            //            String balastName = "";
            //            //Get the bytes from the card.
            //            for (int i = 0; i < 5; i++) {
            //                int tales = 0;
            //                tales |= eventArray[nameOffset] & 0xFFFF;
            //                balastNameBytes.add(new BigInteger(String.valueOf(tales)));
            //                nameOffset++;
            //            }
            //            //Join the bytes using a string
            //            for (BigInteger nameByte : balastNameBytes) {
            //                String value = nameByte.toString(2);
            //                balastName = Utils.getCeros(value) + balastName;
            //            }
            //            //Recreates the entire name bytes and sets the name to the balast.
            //            BigInteger totalBytes = new BigInteger(balastName, 2);
            //</editor-fold>
            String nombreDesencriptado = UtilsJmodbus.desencriptarNombre(eventArray, 3, 5);

            evento.setNombre(nombreDesencriptado);


            evento.setPorFechaODias(eventArray[8]);
            evento.setDia(eventArray[9]);
            evento.setMes(eventArray[10]);
            evento.setAnho(eventArray[11] + 256);
            evento.setHora(eventArray[12]);
            evento.setMinuto(eventArray[13]);
            evento.setDiaYrepetir(eventArray[14]);


            //nivelBalasto
            int balastsLevels = 15;
            int balastsMaxNumber = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
            int[] nivelBalastos = new int[balastsMaxNumber];
            for (int i = 0; i < nivelBalastos.length; i++) {
                nivelBalastos[i] = eventArray[balastsLevels];
                balastsLevels++;
            }
            evento.setNivelBalasto(nivelBalastos);



            //nivelGrupo
            int groupLevels = 79;
            int groupsMaxNumber = Integer.parseInt(PropHandler.getProperty("group.max.number"));
            int[] nivelGrupos = new int[groupsMaxNumber];
            for (int i = 0; i < nivelGrupos.length; i++) {
                nivelGrupos[i] = eventArray[groupLevels];
                groupLevels++;
            }
            evento.setNivelGrupo(nivelGrupos);

            int tamReg = 8;

            //gruposAfectados//<editor-fold defaultstate="collapsed" desc="Codigo viejo Grupos Afectados">
            //            try {
            //                int groupsOffset = 95;
            //                int tamReg = 8;// Hay que verificar si es 8 o 16. Debe ser 8
            //                int[] grupos = evento.getGruposAfectados();
            //                float bytesToRead = grupos.length / tamReg;
            //                ArrayList<BigInteger> affectedGroups = new ArrayList<BigInteger>();
            //
            //                //Get the bytes from the card.
            //                for (int i = 0; i < bytesToRead; i++) {
            //                    affectedGroups.add(new BigInteger(String.valueOf(eventArray[groupsOffset] & 0xFFFF)));
            //                    groupsOffset++;
            //                }
            //
            //                String groupsBitString = "";
            //                for (BigInteger nameByte : affectedGroups) {
            //                    String value = nameByte.toString(2);
            //                    value = Utils.getCeros(value);
            //                    groupsBitString = value + groupsBitString;
            //                }
            //
            //                int j = 0;
            //                for (int i = grupos.length - 1; i >= 0; i--) {
            //                    String bit = String.valueOf(groupsBitString.charAt(i));
            //                    grupos[j] = Integer.parseInt(bit);
            //                    j++;
            //                }
            //            } catch (Exception e) {
            //                System.out.println("Error al leer los grupos afectados por la escena.");
            //            }
            //</editor-fold>
            int groupsOffset = 95;
            int[] gruposAfectados = UtilsJmodbus.obtenerElementosAfectados(eventArray, groupsOffset, 16, 8, 2, 8);
            evento.setGruposAfectados(gruposAfectados);

            //escenasAfectadas
            //           //<editor-fold defaultstate="collapsed" desc="Codigo viejo Escenas afectadas">
//            try {
            //                int sceneOffset = 97;
            //                int tamReg = 8;
            //                int[] escenas = evento.getEscenasAfectadas();
            //                float bytesToRead = escenas.length / tamReg;
            //                ArrayList<BigInteger> affectedScenes = new ArrayList<BigInteger>();
            //
            //                //Get the bytes from the card.
            //                for (int i = 0; i < bytesToRead; i++) {
            //                    affectedScenes.add(new BigInteger(String.valueOf(eventArray[sceneOffset] & 0xFFFF)));
            //                    sceneOffset++;
            //                }
            //
            //                String sceneName = "";
            //                for (BigInteger nameByte : affectedScenes) {
            //                    String value = nameByte.toString(2);
            //                    value = Utils.getCeros(value);
            //                    sceneName = value + sceneName;
            //                }
            //
            //                int j = 0;
            //                for (int i = escenas.length - 1; i >= 0; i--) {
            //                    String bit = String.valueOf(sceneName.charAt(i));
            //                    escenas[j] = Integer.parseInt(bit);
            //                    j++;
            //                }
            //                eventoOffset = eventoOffset + new Float(bytesToRead).intValue();
            //            } catch (Exception e) {
            //                System.out.println("Error al leer los balastos afectados por la escena.");
            //            }
            //</editor-fold>
            int sceneOffset = 97;
            int[] escenasAfectadas = UtilsJmodbus.obtenerElementosAfectados(eventArray, sceneOffset, 16, tamReg, 2, 8);
            evento.setEscenasAfectadas(escenasAfectadas);


            //           //<editor-fold defaultstate="collapsed" desc="Codigo viejo Balastros Afectados">
            //balastosAfectados
            //            try {
            //                int balastsOffset = 99;
            //                int tamReg = 16;
            //                int[] balastos = evento.getBalastosAfectados();
            //                float bytesToRead = balastos.length / tamReg;
            //                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
            //
            //                //Get the bytes from the card.
            //                for (int i = 0; i < bytesToRead; i++) {
            //                    affectedBalasts.add(new BigInteger(String.valueOf(eventArray[balastsOffset] & 0xFFFF)));
            //                    balastsOffset++;
            //                }
            //
            //                String balastBitString = "";
            //                for (BigInteger nameByte : affectedBalasts) {
            //                    String value = nameByte.toString(2);
            //                    value = Utils.getCeros(value);
            //                    balastBitString = value + balastBitString;
            //                }
            //
            //                int j = 0;
            //                for (int i = balastos.length - 1; i >= 0; i--) {
            //                    String bit = String.valueOf(balastBitString.charAt(i));
            //                    balastos[j] = Integer.parseInt(bit);
            //                    j++;
            //                }
            //            } catch (Exception e) {
            //                System.out.println("Error al leer los balastos afectados por la escena.");
            //            }
            //
            //</editor-fold>
            int balastsOffset = 99;
            int[] balastosAfectados = UtilsJmodbus.obtenerElementosAfectados(eventArray, balastsOffset, 64, tamReg, 8, 8);
            evento.setBalastosAfectados(balastosAfectados);

            //MODO
            setSingleReg(0, 0);

            System.out.println("Event number " + eventNumber + " readed.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return evento;
    }

    /**
     * Gets the added events.
     *
     * @return
     */
    @Override
    public ArrayList<String> getAddedElements() {
        //<editor-fold defaultstate="collapsed" desc="Codigo viejo">
        //        ArrayList<String> addedBalasts = new ArrayList<String>();
        //         int eventNum = Integer.parseInt(PropHandler.getProperty("event.max.number"));
        //
        //        if (eventNum < 16) {
        //            eventNum = 16;
        //        }
        //
        //        try {
        //                int initOffset = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
        //                int usedRegisters = Integer.parseInt(PropHandler.getProperty("event.memory.registers"));
        //
        //                int balastsOffset = initOffset;
        //                int tamReg = 16;
        //                int[] balastos = new int[eventNum];
        //                float bytesToRead = balastos.length / tamReg;
        //                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
        //                int[] addedE = dao.getRegValue(initOffset, usedRegisters);
        //
        //                //Get the bytes from the card.
        //                for (int i = 0; i < bytesToRead; i++) {
        //                    affectedBalasts.add(new BigInteger(String.valueOf(addedE[i]&0xFFFF)));
        //                }
        //
        //                String balastName = "";
        //                for (BigInteger nameByte : affectedBalasts) {
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
        //                JOptionPane.showMessageDialog(null, "Error al leer los eventos añadidos.", "Error", JOptionPane.ERROR_MESSAGE);
        //                System.out.println("Error al leer los eventos añadidos.");
        //                e.printStackTrace();
        ////            }
        //
        //        return addedBalasts;
        //</editor-fold>

        ArrayList<String> elementosEnMemoria;
        try {

            int numEventos = Integer.parseInt(PropHandler.getProperty("event.max.number"));
            int offsetInicial = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
            int usedRegisters = Integer.parseInt(PropHandler.getProperty("event.memory.registers"));
//            int usedRegisters = 64;
            int tamReg = 8;
            int bytesToRead = numEventos / tamReg;// si es correcto este valor? 128
            int completacionCeros = 8;
//            int tamReg = Integer.parseInt(PropHandler.getProperty("registro.tamanio.lectura"));
            elementosEnMemoria = UtilsJmodbus.getElementosEnMemoria(numEventos, dao, offsetInicial, usedRegisters, tamReg, bytesToRead, completacionCeros);



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
        //        //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
        //        int numBalastos = Integer.parseInt(PropHandler.getProperty("event.max.number"));
        //        if (numBalastos < 16) {
        //            numBalastos = 16;
        //        }
        //
        //        int[] balastos = new int[numBalastos];
        //        try {
        //                int initOffset = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
        //                int usedRegisters = Integer.parseInt(PropHandler.getProperty("event.memory.registers"));
        //
        //                int balastsOffset = initOffset;
        //                int tamReg = 16;
        //
        //                float bytesToRead = balastos.length / tamReg;
        //                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
        //                int[] addedE = dao.getRegValue(initOffset, usedRegisters);
        //
        //                //Get the bytes from the card.
        //                for (int i = 0; i < bytesToRead; i++) {
        //                    affectedBalasts.add(new BigInteger(String.valueOf(addedE[i]&0xFFFF)));
        //                }
        //
        //                String balastName = "";
        //                for (BigInteger nameByte : affectedBalasts) {
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
        //            } catch (Exception e) {
        //                System.out.println("Error al leer los balastos añadidos.");
        //                e.printStackTrace();
        //            }
        //
        //        return balastos;
        //</editor-fold>

        int numEventos = Integer.parseInt(PropHandler.getProperty("event.max.number"));
        int offsetInicial = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
        int usedRegisters = Integer.parseInt(PropHandler.getProperty("event.memory.registers"));
        int tamReg = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
        int numElementos = numEventos / tamReg;
        return UtilsJmodbus.getElementosEnMemoriaInt(numEventos, dao, offsetInicial, usedRegisters, tamReg, numElementos, 8);
    }

    /**
     * Add a new balast.
     *
     * @param key
     * @return
     */
    @Override
    public void addElement(int writtenEnevtNumber) {
        //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
        //        try {
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
        //
        //
        //            int[] events = getAddedEventsCardArray();
        ////            System.out.println("Offset: " + initOffset + ", group balasts: " + balastos);
        //
        //            //Add the new balast.
        //            events[writtenBalastNumber] = 1;
        //
        //            //Get a string with the bits of the selected values.
        //            String seleBal = "";
        //            for (int i : events) {
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
        //            System.out.println("No se pudo escribir el balasto especificado!");
        //            e.printStackTrace();
        //        }
        //</editor-fold>


        try {
            int offsetInicial = Integer.parseInt(PropHandler.getProperty("event.memory.added"));

            int[] events = getAddedCardArray();

            //Add the new balast.
            events[writtenEnevtNumber] = 1;

            //Get a string with the bits of the selected values.
            String selEvent = "";
            for (int i : events) {
                selEvent = String.valueOf(i) + selEvent;
            }



            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> name = Utils.getSelectedItems(selEvent);
            for (int i = name.size() - 1; i >= 0; i--) {
                int[] nameValues = {name.get(i).intValue()};
                dao.setRegValue(offsetInicial, nameValues);
                offsetInicial += 2;
            }

        } catch (Exception e) {
            System.out.println("No se pudo escribir el evento especificado!");
            e.printStackTrace();
        }

    }

    /**
     * Método que borra un evento de la lista ed eventos adheridos. Lo que hace
     * este método es que borra el bit correspondiente al evento adherido. En
     * ningún momento borra la informacion del evento de la memoria. Sólo borra
     * el evento adherido.
     *
     * @param writtenEventNumber
     *
     */
    @Override
    public void deleteElement(int writtenEventNumber) {

        //       //<editor-fold defaultstate="collapsed" desc="CodigoViejo">
//        try {
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
        //
        //            int[] balastos = getAddedEventsCardArray();
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
            int offsetInicialEntrada = Integer.parseInt(PropHandler.getProperty("event.memory.added"));

            int[] eventos = getAddedCardArray();
//            System.out.println("Offset: " + initOffset + ", group balasts: " + balastos);

            //Delete the specified balast.
            eventos[writtenEventNumber] = 0;

            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : eventos) {
                seleBal = String.valueOf(i) + seleBal;
            }

            //Get BitIntegers every 16 bits and store them in the card.// hasta aqui va bien
            ArrayList<BigInteger> name = Utils.getSelectedItemsWritting(seleBal);
            for (int i = name.size() - 1; i >= 0; i--) {
                int[] nameValues = {name.get(i).intValue()};
                dao.setRegValue(offsetInicialEntrada, nameValues);
                offsetInicialEntrada=offsetInicialEntrada+2;//se pone asi porque se esta escribiendo de a 16 y no de a 8
            }

        } catch (Exception e) {
            System.out.println("No se pudo eliminar el evento especificado!");
            e.printStackTrace();
        }
    }

    /**
     * Gets the number of the next balast to write.
     *
     * @param key
     * @return
     */
    public static int getEventNumber() {
        ArrayList<String> addedBalasts = new ArrayList<String>();
        DAO4j daolocal = new DAO4j();
        int numBalastos = Integer.parseInt(PropHandler.getProperty("event.max.number"));
        if (numBalastos < 16) {
            numBalastos = 16;
        }

        try {
            int offsetInicial = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
            int balastsOffset = offsetInicial;
            int tamReg = 16;
            int[] balastos = new int[numBalastos];
            float bytesToRead = balastos.length / tamReg;
            ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();

            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
                affectedBalasts.add(new BigInteger(String.valueOf(daolocal.getRegValue(balastsOffset) & 0xFFFF)));
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

            //Get an ArrayList with the result
            for (int i = 0; i < balastos.length; i++) {
                if (balastos[i] != 0) {
                    addedBalasts.add(String.valueOf(balastos[i]));
                }

            }

        } catch (Exception e) {
            System.out.println("Error al leer los balastos añadidos.");
            e.printStackTrace();
        }
        return addedBalasts.size();
    }
    //<editor-fold defaultstate="collapsed" desc="Main comentado">
    //    public static void main(String args[]){
    ////        //Add balasts
    //
    //        EventoDAOJmodbus eDao = new EventoDAOJmodbus(new DAOJmodbus());
    //
    //        System.out.println("Escribiendo...");
    //        eDao.addEvent(6);
    ////        eDao.addEvent(12);
    ////        eDao.addEvent(15);
    ////
    //////        //Read balasts
    ////        System.out.println();
    ////        System.out.println("Leyendo...");
    //        ArrayList<String> array = eDao.getAddedEvents();
    ////        for (String string : array) {
    ////            System.out.println("Evento: " + string);
    ////        }
    ////
    ////        System.out.println();
    ////        System.out.println("Eliminando...");
    ////        eDao.deleteEvent(15);
    ////
    ////        System.out.println();
    ////        System.out.println("Leyendo...");
    ////        array = eDao.getAddedEvents();
    ////        for (String string : array) {
    ////            System.out.println("Evento: " + string);
    ////        }
    //
    //    }
    //</editor-fold>
}
