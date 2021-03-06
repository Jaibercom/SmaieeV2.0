package com.isolux.dao.jmodbus;

import com.isolux.bo.Balasto;
import com.isolux.dao.Conversion;
import com.isolux.dao.Utils;
import com.isolux.dao.modbus.DAO4j;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Clase que se encarga de la conexión de bajo nivel con la tarjeta.
 * Aquí se hace uso directo de los daos del objero balasto 
 * @author Juan Diego Toro Cano
 */
public class BalastoDAOJmodbus implements OperacionesElemento_Interface {

    private static DAOJmodbus dao = DAOJmodbus.getInstancia();

    public static DAOJmodbus getDao() {
        return dao;
    }

    public static void setDao(DAOJmodbus dao) {
        BalastoDAOJmodbus.dao = dao;
    }

    public BalastoDAOJmodbus(DAOJmodbus dao) {
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
     * Saves the balast.
     *
     * @param balast
     */
    public static boolean saveBalast(Balasto balasto) {
        boolean state = false;
        int balastNumber = balasto.getBalastNumber();

        try {
            //MODO Escritura
            setSingleReg(0, 1);

            //Init offset.
            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.init.position"));
            int[] balastArray = new int[Integer.parseInt(PropHandler.getProperty("balast.memory.size"))];

            System.out.println("SAVE BALAST NUMBER: " + balastNumber);

            balastArray[0] = balastNumber;                  //2000
            balastArray[1] = balasto.getLevel();            //2001
            balastArray[2] = balasto.getActivation();       //2002

            //            //<editor-fold defaultstate="collapsed" desc="codigo antiguo">
//            int nameOffset = 3;
            //            ArrayList<BigInteger> balastNameBytes = UtilsJmodbus.getNameBytesReverse(balasto.getName());
            //            int size = balastNameBytes.size();
            //            for (int i = 0; i < 5; i++) {
            //                if (i < size) {
            //                    balastArray[nameOffset] = balastNameBytes.get(i).intValue();
            //                } else {
            //                    balastArray[nameOffset] = 0;
            //                }
            //                nameOffset++;
            //            }
            //</editor-fold>

//            codifica el nombre y lo mete en el array
            UtilsJmodbus.encriptarNombre(balastArray, 3, balasto.getName(), 5);

            balastArray[8] = balasto.getDir();              //2008
            balastArray[9] = balasto.getMin();              //2009
            balastArray[10] = balasto.getMax();             //2010
            balastArray[11] = balasto.getFt();              //2011
            balastArray[12] = balasto.getFr();              //2012
            balastArray[13] = balasto.getLf();              //2013
            balastArray[14] = balasto.getLx();              //2014
            balastArray[15] = balasto.getPot();             //2015




            /**
             * Numero dentro del array que corresponde al offset desde donde se
             * empezarán a guardar los datos de los grupos
             */
 
            
            int gruposOffset = 16;
            int tamReg = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
            int[] gruposAfect = balasto.getGruposAfectados();
            int cuantosElementos = 16;
            gruposAfect = UtilsJmodbus.obtenerElementosAfectados(balastArray, gruposOffset, cuantosElementos, tamReg, 8, 8);
            balasto.setGruposAfectados(gruposAfect);

            /*
             *Procesamos para guardar las escenas
             */
            int escenasOffset = 18;
            int tamRegesc = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
            int[] escAfect = balasto.getEscenasAfectadas();
            int cuantosElementoses = 16;
            escAfect = UtilsJmodbus.obtenerElementosAfectados(balastArray, escenasOffset, cuantosElementoses, tamRegesc, 8, 8);
            balasto.setEscenasAfectadas(escAfect);



            //Save array
            dao.setRegValue(initOffset, balastArray);
            addBalast(balastNumber);// agrega el indice a la lista de balastros en memoria

            //MODO
            setSingleReg(0, 0);

            System.out.println("Balast No.:" + balastNumber + " saved.");
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
    public static boolean deleteBalast(String balastNumber) {
        boolean state = false;

        try {

            //MODO
            setSingleReg(0, 1);

            //Init offset.
            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.init.position"));
            int[] balastArray = new int[Integer.parseInt(PropHandler.getProperty("balast.memory.size"))];
            int balastN = Integer.parseInt(balastNumber);

            System.out.println("DELETE BALAST NUMBER: " + balastN);

            PropHandler.deleteBalast(balastN, dao);
            balastArray[0] = balastN;                           //2000
            balastArray[1] = 0;                                 //2001
            balastArray[2] = 0;                                 //2002
            balastArray[3] = 0;                                 //2003
            balastArray[4] = 0;                                 //2004
            balastArray[5] = 0;                                 //2005
            balastArray[6] = 0;                                 //2006
            balastArray[7] = 0;                                 //2007
            balastArray[8] = 0;                                 //2008
            balastArray[9] = 0;                                 //2009
            balastArray[10] = 0;                                //2010
            balastArray[11] = 0;                                //2011
            balastArray[12] = 0;                                //2012
            balastArray[13] = 0;                                //2013
            balastArray[14] = 0;                                //2014
            balastArray[15] = 0;                                //2015

            //Save array
            dao.setRegValue(initOffset, balastArray);
            deleteBalast(balastN);

            //MODO
            setSingleReg(0, 0);

            System.out.println("Balast No.:" + balastNumber + " saved.");
            state = true;
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        }

        return state;
    }

    /**
     * Get a balast from the card.
     *
     * @param balast
     */
    public static Balasto readBalast(int balastNumber) {
        Balasto balastoLocal = new Balasto();

        try {
            //MODO
            setSingleReg(0, 1);

            System.out.println("READING BALASTO No.:" + balastNumber);
            
            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.init.position"));             //2000

            setSingleReg(initOffset, balastNumber);
            int[] balastArray = dao.getRegValue(initOffset, Integer.parseInt(PropHandler.getProperty("balast.memory.size")));


            balastoLocal.setBalastNumber(balastArray[0]);
            balastoLocal.setLevel(balastArray[1]);
            balastoLocal.setActivation(balastArray[2]);

            //           //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
            //name
            //            int nameOffset = 3;
            //            ArrayList<BigInteger> balastNameBytes = new ArrayList<BigInteger>();
            //            String balastName = "";
            //            //Get the bytes from the card.
            //            for (int i = 0; i < 5; i++) {
            //                int tales = 0;
            //                tales |= balastArray[nameOffset] & 0xFFFF;
            //                balastNameBytes.add(new BigInteger(String.valueOf(tales)));
            //                nameOffset++;
            //            }
            //            //Join the bytes using a string
            //            for (BigInteger nameByte : balastNameBytes) {
            //                String value = nameByte.toString(2);
            //                balastName = UtilsJmodbus.getCeros(value) + balastName;
            //            }
            //            //Recreates the entire name bytes and sets the name to the balast.
            //            BigInteger totalBytes = new BigInteger(balastName, 2);
            //            balasto.setName(new String(totalBytes.toByteArray()));
            //</editor-fold>

            balastoLocal.setName(UtilsJmodbus.desencriptarNombre(balastArray, 3, 5));

            balastoLocal.setDir(balastArray[8]);
            balastoLocal.setMin(balastArray[9]);
            balastoLocal.setMax(balastArray[10]);
            balastoLocal.setFt(balastArray[11]);
            balastoLocal.setFr(balastArray[12]);
            balastoLocal.setLf(balastArray[13]);
            balastoLocal.setLx(balastArray[14]);
            balastoLocal.setPot(balastArray[15]);

            //<editor-fold defaultstate="collapsed" desc="Grupos en los que se incluye el balasto">
            //  Cuando se escriben numeros superiores a 8 bits los lee y puede terminar creando un array de mas de los permitidos
            int numeroMenosS = balastArray[16];
            int[] grupoMenosSignificativo = Conversion.intToBinaryArray(numeroMenosS);
            int numeroMasS = balastArray[17];
            int[] grupoMasSignificativo = Conversion.intToBinaryArray(numeroMasS);
            
            //concatenamos los arreglos
            int[] gruposAfectados = ArrayUtils.addAll(grupoMenosSignificativo, grupoMasSignificativo);
            //agregamos los grupos afectados
            balastoLocal.setGruposAfectados(gruposAfectados);
            //</editor-fold>

            
            //<editor-fold defaultstate="collapsed" desc="Escenas en las que se incluye el balasto actual">
            //  Cuando se escriben numeros superiores a 8 bits los lee y puede terminar creando un array de mas de los permitidos
            int numeroMenosSEsc = balastArray[18];
            int[] escenaMenosSigni = Conversion.intToBinaryArray(numeroMenosSEsc);
            int numeroMasSEsc = balastArray[19];
            int[] escenaMasSigni = Conversion.intToBinaryArray(numeroMasSEsc);
            
            //concatenamos los arreglos
            int[] escenasAfectadas = ArrayUtils.addAll(escenaMenosSigni, escenaMasSigni);
            //agregamos los grupos afectados
            balastoLocal.setEscenasAfectadas(escenasAfectadas);
            //</editor-fold>
            
            //llenamos los valores de las escenas
            
            int[] nivelesEscenas=new int[16];
            int indiceofsetNiveles=20;
            for (int i = 0; i < nivelesEscenas.length; i++) {
                nivelesEscenas[i]=balastArray[indiceofsetNiveles];
                indiceofsetNiveles++;
            }
            
            
            balastoLocal.setNivelesEscenas(nivelesEscenas);
            
            
            
            
            System.out.println("Balast number " + balastNumber + " readed.");
            
            

            //MODO
//            setSingleReg(0, 0);

        } catch (Exception e) {

            Logger.getLogger(BalastoDAOJmodbus.class.getName()).log(Level.SEVERE, "Error leyendo la informacion del balasto " + balastNumber, e);
        }

        return balastoLocal;
    }

    /**
     * Saves the level of the given balast
     *
     * @param balastNumber
     * @param level
     */
    public static void saveRealTimeLevel(int balastNumber, int level) {
        try {

            //MODO
            setSingleReg(0, 1);
            System.out.println("SAVE LEVEL OF BALAST: " + balastNumber + ", LEVEL: " + level);

            //Init offset.
            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.init.position"));

            //Balast number
            setSingleReg(initOffset, balastNumber);
            initOffset++;

            //level
            setSingleReg(initOffset, level);

//            setSingleReg(0, 0);

        } catch (Exception e) {
            Logger.getLogger(BalastoDAOJmodbus.class.getName()).log(Level.SEVERE, "Error actualizando el nivel del balasto", e);
        }
    }

    /**
     * Saves the level of the given balast
     *
     * @param balastNumber
     * @param level
     */
    public static int getRealTimeLevel(int balastNumber) {
        int balastLevel = 0;
        try {

            //Init offset.
            int levelOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.levelOffset"));
            setSingleReg(Integer.parseInt(PropHandler.getProperty("balast.init.position")), balastNumber);

            //level
            balastLevel = dao.getRegValue(levelOffset, 1)[0];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return balastLevel;
    }

    /**
     * Saves the level of the given balast
     *
     * @param balastNumber
     * @param level
     */
    public static String getRealTimeNames(int balastNumber) {
        String name = "";

        try {

            //Init offset.
            int initNameOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.nameOffset"));

            setSingleReg(Integer.parseInt(PropHandler.getProperty("balast.init.position")), balastNumber);
            int[] balastNameArray = dao.getRegValue(initNameOffset, 5);

            //name
            int nameOffset = 0;
            ArrayList<BigInteger> balastNameBytes = new ArrayList<BigInteger>();
            String balastName = "";
            //Get the bytes from the card.
            for (int i = 0; i < 5; i++) {
                int tales = 0;
                tales |= balastNameArray[nameOffset] & 0xFFFF;
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
            name = new String(totalBytes.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * Get the levels of the balasts
     *
     * @return
     */
    public HashMap<Integer, Balasto> getBalastsLevels() {
        HashMap<Integer, Balasto> balasts = new HashMap<Integer, Balasto>();
        ArrayList<String> addedBalasts = PropHandler.getAddedBalasts(this);

        for (int i = 0; i < addedBalasts.size(); i++) {
            Balasto balast = new Balasto();
            int balastNumber = Integer.parseInt(addedBalasts.get(i));
            balast.setBalastNumber(balastNumber);
            balast.setLevel(getRealTimeLevel(balastNumber));
            balast.setName(getRealTimeNames(balastNumber));
            balasts.put(balastNumber, balast);
        }

        return balasts;
    }

    /**
     * Funcion que extrae la informacion de los balastos de la tarjeta
     *
     * @return
     */
    public static ArrayList<String> getAddedBalasts() {

        ArrayList<String> elementosEnMemoria;
        try {

//            ArrayList<String> addedBalasts = new ArrayList<String>();
            int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));//dir 10
            int usedRegisters = Integer.parseInt(PropHandler.getProperty("balast.memory.registers"));// 4
            int tamReg = 8;
//            int tamReg = Integer.parseInt(PropHandler.getProperty("registro.tamanio.lectura"));
            elementosEnMemoria = UtilsJmodbus.getElementosEnMemoria(numBalastos, dao, initOffset, usedRegisters, tamReg);
        } catch (Exception e) {
            System.out.println("No se encontraron los archivos de configuración");
            e.printStackTrace();
            return null;
        }
        return elementosEnMemoria;


        //      //<editor-fold defaultstate="collapsed" desc="codigo antiguo">
//        try {
        //                int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));//dir 10
        //                int usedRegisters = Integer.parseInt(PropHandler.getProperty("balast.memory.registers"));// 4
        //
        //
        //                int tamReg = 8;
        //                int[] balastos = new int[numBalastos];
        //                float bytesToRead = balastos.length / tamReg;
        //                ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
        //                int[] addedB = dao.getRegValue(initOffset, usedRegisters);
        //
        //                //Get the bytes from the card.
        //                for (int i = 0; i < bytesToRead; i++) {
        ////                    affectedBalasts.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
        //                    affectedBalasts.add(new BigInteger(String.valueOf(addedB[i]&0x00FF)));
        //                }
        //
        //                String balastName = "";
        //                for (BigInteger nameByte : affectedBalasts) {
        //                    String value = nameByte.toString(2);
        //                    value = Utils.getCeros(value);
        //                    balastName = value + balastName;
        //                }
        //
        //
        //
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
        //                e.printStackTrace();
        //            }
//        return addedBalasts;
        //</editor-fold>




    }

    /**
     * Returns the an array with the added balasts.
     *
     * @return
     */
    public static int[] getAddedBalastsCardArray() {
        //<editor-fold defaultstate="collapsed" desc="Codigo antiguo">
        //        int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
        //
        //        int[] balastos = new int[numBalastos];
        //        try {
        //            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));
        //            int usedRegisters = Integer.parseInt(PropHandler.getProperty("balast.memory.registers"));
        //            int tamReg = 16;
        //
        //            float bytesToRead = balastos.length / tamReg;
        //            ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
        //            int[] addedB = dao.getRegValue(initOffset, usedRegisters);
        //
        //            //Get the bytes from the card.
        //            for (int i = 0; i < bytesToRead; i++) {
        ////                    affectedBalasts.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
        //                affectedBalasts.add(new BigInteger(String.valueOf(addedB[i] & 0x00FF)));
        //            }
        //
        //            String balastName = "";
        //            for (BigInteger nameByte : affectedBalasts) {
        //                String value = nameByte.toString(2);
        //                value = Utils.getCeros(value);
        ////                    balastName = value + balastName;
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
        //        }
        //
        //        return balastos;
        //</editor-fold>

        int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));
        int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));
        int usedRegisters = Integer.parseInt(PropHandler.getProperty("balast.memory.registers"));
        int tamReg = Integer.parseInt(PropHandler.getProperty("memoria.bits.lectura"));
        return UtilsJmodbus.getElementosEnMemoriaInt(numBalastos, dao, initOffset, usedRegisters, tamReg);

    }

    /**
     * Agrega un balastro al registro de balastros adheridos.
     *
     * @param writtenBalastNumber
     * @return
     */
    public static void addBalast(int writtenBalastNumber) {
        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));

            int[] balastos = getAddedBalastsCardArray();
//            int[] balastos = getAddedBalastsCardArray();
//            System.out.println("Offset: " + initOffset + ", group balasts: " + balastos);

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
    public static void deleteBalast(int writtenBalastNumber) {
//        DAO4j dao = new DAO4j();

        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));

            int[] balastos = getAddedBalastsCardArray();
            System.out.println("Offset: " + initOffset + ", group balasts: " + balastos);

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
     *
     * @param key
     * @return
     */
    public static int getBalastNumber() {
        ArrayList<String> addedBalasts = new ArrayList<String>();
        DAO4j dao = new DAO4j();
        int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));

        try {
            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));
            int balastsOffset = initOffset;
            int tamReg = 16;
            int[] balastos = new int[numBalastos];
            float bytesToRead = balastos.length / tamReg;
            ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();

            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
                affectedBalasts.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset) & 0xFFFF)));
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

    /**
     * TEST
     */
    public static void main(String[] args) {
//       HashMap<Integer, Balasto> balastos = new BalastoDAOJmodbus().getBalastsLevels();
//       
//       Set<Integer> balastsNumbers = balastos.keySet();
//       for (Integer object : balastsNumbers) {
//            System.out.println("Balasto: " + object + ", nivel: " + balastos.get(object).getLevel());
//        }
    }

    @Override
    public String[] elementosSinGrabar() {
        int[] addedBalastsCardArray = BalastoDAOJmodbus.getAddedBalastsCardArray();
        String[] ele = new String[addedBalastsCardArray.length];

        for (int i = 0; i < addedBalastsCardArray.length; i++) {
            ele[i] = Integer.toString(addedBalastsCardArray[i]);
        }

        return ele;


    }
}
