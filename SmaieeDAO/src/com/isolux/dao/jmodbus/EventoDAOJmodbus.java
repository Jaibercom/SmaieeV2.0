/*
 * To change this template, choose Tools | Templates
 * and open the template event the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.dao.Utils;
import com.isolux.bo.Evento;
import com.isolux.dao.modbus.DAO4j;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author Juan Diego Toro Cano
 */
public class EventoDAOJmodbus {
    
    private static int initOffset = 0;
    private static DAOJmodbus dao;
    
    //CONSTRUCTOR.
    public EventoDAOJmodbus(DAOJmodbus dao) {
        this.dao  = dao;
    }
    
    /**
     * Write a single register.
     */
    public static void setSingleReg(int pos, int mode){
        int[] values = {mode};
        dao.setRegValue(pos, values);
    }
    
    /**
     * Saves the balast.
     * @param evento 
     */
    public static boolean saveEvent(Evento evento) {
//        DAO4j dao = new DAO4j();
        boolean state = false;
        int eventNumber = evento.getNumeroEvento();

        try {
            
            //MODO
            setSingleReg(0, 1);
            
            //Init offset
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.events"));
            int[] eventArray = new int[Integer.parseInt(PropHandler.getProperty("event.memory.size"))];
            
            System.out.println("SAVING EVENT NUMBER: " + eventNumber);
            
            //numeroEvento
            eventArray[0]=eventNumber;
            
            //nombre
            int nameOffset = 1;
            ArrayList<BigInteger> balastNameBytes = Utils.getNameBytesReverse(evento.getNombre());
            int size = balastNameBytes.size();
            for (int i = 0; i < 5; i++) {
                if (i < size) {
                    eventArray[nameOffset]=balastNameBytes.get(i).intValue();
                } else {
                    eventArray[nameOffset]=0;
                }
                nameOffset++;
            }
            
            eventArray[6]=evento.getPorFechaODias();
            eventArray[7]=evento.getDia();
            eventArray[8]=evento.getMes();
            eventArray[9]=evento.getAnho();
            eventArray[10]=evento.getHora();
            eventArray[11]=evento.getMinuto();
            eventArray[12]=evento.getDiaYrepetir();

            //nivelBalasto
            int balastosOffset = 13;
            int[]  nivelBalastos = evento.getNivelBalasto();
            for (int i = 0; i < nivelBalastos.length; i++) {
                eventArray[balastosOffset]=nivelBalastos[i];
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
            int affectedBalasts = 77;
            for (int i = balastsRegisters.size() - 1; i >= 0; i--) {
                eventArray[affectedBalasts]=balastsRegisters.get(i).intValue();
                affectedBalasts++;
            }

            //nivelGrupo
            int gruposOffset = 81;
            int[]  nivelGrupos = evento.getNivelGrupo();
            for (int i = 0; i < nivelGrupos.length; i++) {
                eventArray[gruposOffset]=nivelGrupos[i];
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
            int affectedGroups = 97;
            for (int i = groupsRegisters.size() - 1; i >= 0; i--) {
                eventArray[affectedGroups]=groupsRegisters.get(i).intValue();
                affectedGroups++;
            }
            
            //escenasAfectadas
            int[] scenes = evento.getEscenasAfectadas();
            
            //Get a string with the bits of the selected values.
            String seleScene = "";
            for (int i : scenes) {
                seleScene = String.valueOf(i) + seleScene;
            }
            
            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> scenesRegisters = Utils.getSelectedItems(seleScene);
            int affectedScenes = 98;
            for (int i = scenesRegisters.size() - 1; i >= 0; i--) {
                eventArray[affectedScenes]=scenesRegisters.get(i).intValue();
                affectedScenes++;
            }         
            
            //Out type
            eventArray[99]=evento.getTipoSalida();
            
            dao.setRegValue(initOffset, eventArray);
            addEvent(eventNumber);
            
            //MODO
            setSingleReg(0, 0);
            
            System.out.println("Event number " + eventNumber + " saved.");
            
            state = true;
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        }
        
        return state;
    }
    
    /**
     * Deletes the event.
     * @param eventNumbers
     * @return 
     */
    public static boolean deleteEvent(String eventNumbers) {
//        DAO4j dao = new DAO4j();
        boolean state = false;
        int eventNumber = Integer.parseInt(eventNumbers);

        try {
            
            //MODO
            setSingleReg(0, 1);
            
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.events"));
            int[] eventArray = new int[Integer.parseInt(PropHandler.getProperty("event.memory.size"))];
            
            System.out.println("DELETING EVENT NUMBER: " + eventNumber);
            
            //numeroEvento
            eventArray[0]=eventNumber;
            
            //nombre
            int nameOffset = 1;
            for (int i = 0; i < 5; i++) {
                eventArray[nameOffset]=0;
                nameOffset++;
            }
            
            eventArray[6]=0;
            eventArray[7]=0;
            eventArray[8]=0;
            eventArray[9]=0;
            eventArray[10]=0;
            eventArray[11]=0;
            eventArray[12]=0;

            //nivelBalasto
            Evento evento = new Evento();
            int balastosOffset = 13;
            int[]  nivelBalastos = evento.getNivelBalasto();
            for (int i = nivelBalastos.length - 1; i >= 0; i--) {
                eventArray[balastosOffset]=nivelBalastos[i];
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
            int affectedBalasts = 77;
            for (int i = balastsRegisters.size() - 1; i >= 0; i--) {
                eventArray[affectedBalasts] = 0;
                affectedBalasts++;
            }

            //nivelGrupo
            int gruposOffset = 81;
            int[]  nivelGrupos = evento.getNivelGrupo();
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
            int affectedGroups = 97;
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
            
            //Get BitIntegers every 16 bits and store them in the card.
            ArrayList<BigInteger> scenesRegisters = Utils.getSelectedItems(seleScene);
            int affecedScenes = 98;
            for (int i = scenesRegisters.size() - 1; i >= 0; i--) {
                eventArray[affecedScenes] = 0;
                affecedScenes++;
            }   
            
            //Out type
            eventArray[99] =  0;
            
            //SAVE EVENT
            deleteEvent(eventNumber);
            dao.setRegValue(initOffset, eventArray);
            
            //MODO
            setSingleReg(0, 0);
            
            System.out.println("Event number " + eventNumber + " deleted.");
            
            state = true;
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        }
        
        return state;
    }
    
    
    
    /**
     * Get a balast from the card.
     * @param balasto 
     */
    public static Evento readEvent(int eventNumber) {
        Evento evento = new Evento();

        try {
            
            //MODO
            setSingleReg(0, 1);
            
            System.out.println("READING EVENT NUMBER: " + eventNumber);
            int initOffset = Integer.parseInt(PropHandler.getProperty("memory.offset.events"));
            
            //numeroEvento
            setSingleReg(initOffset, eventNumber);
            int[] eventArray = dao.getRegValue(initOffset, Integer.parseInt(PropHandler.getProperty("event.memory.size")));
            
            //nombre
            int nameOffset = 1;
            ArrayList<BigInteger> balastNameBytes = new ArrayList<BigInteger>();
            String balastName = "";
            //Get the bytes from the card.
            for (int i = 0; i < 5; i++) {
                int tales = 0;
                tales |= eventArray[nameOffset] & 0xFFFF;
                balastNameBytes.add(new BigInteger(String.valueOf(tales)));
                nameOffset++;
            }
            //Join the bytes using a string
            for (BigInteger nameByte : balastNameBytes) {
                String value = nameByte.toString(2);
                balastName = Utils.getCeros(value) + balastName;
            }
            //Recreates the entire name bytes and sets the name to the balast.
            BigInteger totalBytes = new BigInteger(balastName, 2);
            evento.setNombre(new String(totalBytes.toByteArray()));
            
            
            evento.setPorFechaODias(eventArray[6]);
            evento.setDia(eventArray[7]);
            evento.setMes(eventArray[8]);
            evento.setAnho(eventArray[9]+256);
            evento.setHora(eventArray[10]);
            evento.setMinuto(eventArray[11]);
            evento.setDiaYrepetir(eventArray[12]);

            
            //nivelBalasto
            int balastsLevels = 13;
            int balastsMaxNumber = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
            int[] nivelBalastos = new int[balastsMaxNumber];
            for (int i = 0; i < nivelBalastos.length; i++) {
                nivelBalastos[i] = eventArray[balastsLevels];
                balastsLevels++;
            }
            evento.setNivelBalasto(nivelBalastos);
            
            //balastosAfectados
            try {
                int balastsOffset = 77;
                int tamReg = 16;
                int[] balastos = evento.getBalastosAfectados();
                float bytesToRead = balastos.length / tamReg;
                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();

                //Get the bytes from the card.
                for (int i = 0; i < bytesToRead; i++) {
                    affectedBalasts.add(new BigInteger(String.valueOf(eventArray[balastsOffset]&0xFFFF)));
                    balastsOffset++;
                }
                
                String balastBitString = "";
                for (BigInteger nameByte : affectedBalasts) {
                    String value = nameByte.toString(2);
                    value = Utils.getCeros(value);
                    balastBitString = value + balastBitString;
                }
                
                int j=0;
                for (int i = balastos.length -1; i >= 0; i--) {
                    String bit = String.valueOf(balastBitString.charAt(i));
                    balastos[j] = Integer.parseInt(bit);
                    j++;
                }
            } catch (Exception e) {
                System.out.println("Error al leer los balastos afectados por la escena.");
            }

            //nivelGrupo
            int groupLevels = 81;
            int groupsMaxNumber = Integer.parseInt(PropHandler.getProperty("group.max.number"));
            int[] nivelGrupos = new int[groupsMaxNumber];
            for (int i = 0; i < nivelGrupos.length; i++) {
                nivelGrupos[i] = eventArray[groupLevels];
                groupLevels++;
            }
            evento.setNivelGrupo(nivelGrupos);
            
            //gruposAfectados
            try {
                int groupsOffset = 97;
                int tamReg = 16;
                int[] grupos = evento.getGruposAfectados();
                float bytesToRead = grupos.length / tamReg;
                ArrayList<BigInteger> affectedGroups = new ArrayList<BigInteger>();

                //Get the bytes from the card.
                for (int i = 0; i < bytesToRead; i++) {
                    affectedGroups.add(new BigInteger(String.valueOf(eventArray[groupsOffset] & 0xFFFF)));
                    groupsOffset++;
                }
                
                String groupsBitString = "";
                for (BigInteger nameByte : affectedGroups) {
                    String value = nameByte.toString(2);
                    value = Utils.getCeros(value);
                    groupsBitString = value + groupsBitString;
                }
                
                int j=0;
                for (int i = grupos.length -1; i >= 0; i--) {
                    String bit = String.valueOf(groupsBitString.charAt(i));
                    grupos[j] = Integer.parseInt(bit);
                    j++;
                }
            } catch (Exception e) {
                System.out.println("Error al leer los balastos afectados por la escena.");
            }
            
            //escenasAfectadas
            try {
                int sceneOffset = 98;
                int tamReg = 16;
                int[] escenas = evento.getEscenasAfectadas();
                float bytesToRead = escenas.length / tamReg;
                ArrayList<BigInteger> affectedScenes = new ArrayList<BigInteger>();

                //Get the bytes from the card.
                for (int i = 0; i < bytesToRead; i++) {
                    affectedScenes.add(new BigInteger(String.valueOf(eventArray[sceneOffset] & 0xFFFF)));
                    sceneOffset++;
                }
                
                String sceneName = "";
                for (BigInteger nameByte : affectedScenes) {
                    String value = nameByte.toString(2);
                    value = Utils.getCeros(value);
                    sceneName = value + sceneName;
                }
                
                int j=0;
                for (int i = escenas.length -1; i >= 0; i--) {
                    String bit = String.valueOf(sceneName.charAt(i));
                    escenas[j] = Integer.parseInt(bit);
                    j++;
                }
                initOffset = initOffset + new Float(bytesToRead).intValue();
            } catch (Exception e) {
                System.out.println("Error al leer los balastos afectados por la escena.");
            }
            
            
            //Out type
            evento.setTipoSalida(eventArray[99]);
            
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
     * @return 
     */
    public static ArrayList<String> getAddedEvents() {
        ArrayList<String> addedBalasts = new ArrayList<String>();
         int eventNum = Integer.parseInt(PropHandler.getProperty("event.max.number"));
        
        if (eventNum < 16) {
            eventNum = 16;
        }
        
        try {
                int initOffset = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
                int usedRegisters = Integer.parseInt(PropHandler.getProperty("event.memory.registers"));
                
                int balastsOffset = initOffset;
                int tamReg = 16;
                int[] balastos = new int[eventNum];
                float bytesToRead = balastos.length / tamReg;
                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
                int[] addedE = dao.getRegValue(initOffset, usedRegisters);

                //Get the bytes from the card.
                for (int i = 0; i < bytesToRead; i++) {
                    affectedBalasts.add(new BigInteger(String.valueOf(addedE[i]&0xFFFF)));
                }
                
                String balastName = "";
                for (BigInteger nameByte : affectedBalasts) {
                    String value = nameByte.toString(2);
                    value = Utils.getCeros(value);
                    balastName = value + balastName;
                }
                
                int j=0;
                for (int i = balastos.length -1; i >= 0; i--) {
                    String bit = String.valueOf(balastName.charAt(i));
                    balastos[j] = Integer.parseInt(bit);
                    j++;
                }
                
                //Get an ArrayList with the result
                for (int i = 0; i < balastos.length; i++) {
                    if (balastos[i] != 0) {
                       addedBalasts.add(String.valueOf(i));
                    }
                    
                }
                
            } catch (Exception e) {
                System.out.println("Error al leer los balastos añadidos.");
                e.printStackTrace();
            }
        
        return addedBalasts;
    }
    
    /**
     * Returns the an array with the added balasts.
     * @return 
     */
    public static int[] getAddedEventsCardArray() {
        int numBalastos = Integer.parseInt(PropHandler.getProperty("event.max.number"));
        if (numBalastos < 16) {
            numBalastos = 16;
        }
        
        int[] balastos = new int[numBalastos];
        try {
                int initOffset = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
                int usedRegisters = Integer.parseInt(PropHandler.getProperty("event.memory.registers"));
                
                int balastsOffset = initOffset;
                int tamReg = 16;
                
                float bytesToRead = balastos.length / tamReg;
                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
                int[] addedE = dao.getRegValue(initOffset, usedRegisters);

                //Get the bytes from the card.
                for (int i = 0; i < bytesToRead; i++) {
                    affectedBalasts.add(new BigInteger(String.valueOf(addedE[i]&0xFFFF)));
                }
                
                String balastName = "";
                for (BigInteger nameByte : affectedBalasts) {
                    String value = nameByte.toString(2);
                    value = Utils.getCeros(value);
                    balastName = value + balastName;
                }
                
                int j=0;
                for (int i = balastos.length -1; i >= 0; i--) {
                    String bit = String.valueOf(balastName.charAt(i));
                    balastos[j] = Integer.parseInt(bit);
                    j++;
                }
                
            } catch (Exception e) {
                System.out.println("Error al leer los balastos añadidos.");
            }
        
        return balastos;
    }
    
    
    /**
     * Add a new balast.
     * @param key
     * @return 
     */
    public static void addEvent(int writtenBalastNumber) {
        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
            
            
            int[] events = getAddedEventsCardArray();
//            System.out.println("Offset: " + initOffset + ", group balasts: " + balastos);
            
            //Add the new balast.
            events[writtenBalastNumber] = 1;
            
            //Get a string with the bits of the selected values.
            String seleBal = "";
            for (int i : events) {
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
     * @param key
     * @return 
     */
    public static void deleteEvent(int writtenBalastNumber) {
        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
            
            int[] balastos = getAddedEventsCardArray();
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
    
    /**
     * Gets the number of the next balast to write.
     * @param key
     * @return 
     */
    public static int getEventNumber() {
        ArrayList<String> addedBalasts = new ArrayList<String>();
        DAO4j dao = new DAO4j();
        int numBalastos = Integer.parseInt(PropHandler.getProperty("event.max.number"));
        if (numBalastos < 16) {
            numBalastos = 16;
        }
        
        try {
                int initOffset = Integer.parseInt(PropHandler.getProperty("event.memory.added"));
                int balastsOffset = initOffset;
                int tamReg = 16;
                int[] balastos = new int[numBalastos];
                float bytesToRead = balastos.length / tamReg;
                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();

                //Get the bytes from the card.
                for (int i = 0; i < bytesToRead; i++) {
                    affectedBalasts.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
                    balastsOffset++;
                }
                
                String balastName = "";
                for (BigInteger nameByte : affectedBalasts) {
                    String value = nameByte.toString(2);
                    value = Utils.getCeros(value);
                    balastName = value + balastName;
                }
                
                int j=0;
                for (int i = balastos.length -1; i >= 0; i--) {
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
            }
        return addedBalasts.size();
    }
    
    
    public static void main(String args[]){
//        //Add balasts
        
        EventoDAOJmodbus eDao = new EventoDAOJmodbus(new DAOJmodbus());
        
        System.out.println("Escribiendo...");
        eDao.addEvent(6);
//        eDao.addEvent(12);
//        eDao.addEvent(15);
//        
////        //Read balasts
//        System.out.println();
//        System.out.println("Leyendo...");
        ArrayList<String> array = eDao.getAddedEvents();
//        for (String string : array) {
//            System.out.println("Evento: " + string);
//        }
//        
//        System.out.println();
//        System.out.println("Eliminando...");
//        eDao.deleteEvent(15);
//        
//        System.out.println();
//        System.out.println("Leyendo...");
//        array = eDao.getAddedEvents();
//        for (String string : array) {
//            System.out.println("Evento: " + string);
//        }
        
    }
   
}
