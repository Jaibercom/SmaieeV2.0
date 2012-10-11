/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.dao.Utils;
import com.isolux.dao.modbus.DAOJmodbus;
import com.isolux.dao.properties.PropHandler;
import java.math.BigInteger;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author EAFIT
 */
public class UtilsJmodbus {

    /**
     * Get the bytes of a balas.
     */
    public static ArrayList<BigInteger> getNameBytes(String balastName) {
        byte[] nameBytes = balastName.getBytes();
        ArrayList<BigInteger> name = new ArrayList<BigInteger>();

        BigInteger bigInt = new BigInteger(nameBytes);
        String nameBits = bigInt.toString(2);
//        System.out.println("Name bits: " + nameBits +", tamaño: " + nameBits.length());

        int bitsToRead = 16;
        String twoBytes = "";

        //name
        for (int i = 0; i < nameBits.length();) {
            //16 bits
            while ((twoBytes.length() < bitsToRead) && (i < nameBits.length())) {
                twoBytes += nameBits.charAt(i);
                i++;
            }
            name.add(new BigInteger(twoBytes, 2));
            twoBytes = "";
        }
        return name;
    }

    public static ArrayList<BigInteger> getNameBytesReverse(String balastName) {
        byte[] nameBytes = balastName.getBytes();
        ArrayList<BigInteger> name = new ArrayList<BigInteger>();

        BigInteger bigInt = new BigInteger(nameBytes);
        String nameBits = bigInt.toString(2);
//        System.out.println("Name bits: " + nameBits +", tamaño: " + nameBits.length());

        int bitsToRead = 16;
        String twoBytes = "";

        //name
        for (int i = nameBits.length() - 1; i > 0;) {
            //16 bits
            while ((twoBytes.length() < bitsToRead) && (i >= 0)) {
                twoBytes = nameBits.charAt(i) + twoBytes;
                i--;
            }
            name.add(new BigInteger(twoBytes, 2));
            twoBytes = "";
        }
        return name;
    }

    public static ArrayList<BigInteger> getSelectedItems(String itemsBits) {
        ArrayList<BigInteger> name = new ArrayList<BigInteger>();

        String nameBits = itemsBits;

        int bitsToRead = 16;
        String twoBytes = "";

        //name
        for (int i = 0; i < nameBits.length();) {
            //16 bits
            while ((twoBytes.length() < bitsToRead) && (i < nameBits.length())) {
                twoBytes += nameBits.charAt(i);
                i++;
            }
            name.add(new BigInteger(twoBytes, 2));
            twoBytes = "";
        }
        return name;
    }

    /**
     * Get the missing ceros at the begining of the bites.
     *
     * @param bits
     * @return
     */
    public static String getCeros(String bits) {

        BigInteger bitsBI = new BigInteger(bits, 2);

        int length = bits.length();
        if (length < 16) {

            int missingCeros = 16 - length;
            String mising = "";

            for (int i = 0; i < missingCeros; i++) {
                mising += "0";
            }

            BigInteger comparator = new BigInteger(mising + bits, 2);

            if (comparator.equals(bitsBI)) {
                bits = mising + bits;
            }
        }

        return bits;
    }

    /**
     * Copy a file into a new location.
     *
     * @param fromFileName
     * @param toFileName
     * @throws IOException
     */
    public static File copyFile(String fromFileName, String toFileName) {
        boolean result = false;
        File toFile = new File(toFileName);
        try {
            File fromFile = new File(fromFileName);

            if (!fromFile.exists()) {
                throw new IOException("FileCopy: " + "no existe el archivo especificado: "
                        + fromFileName);
            }
            if (!fromFile.isFile()) {
                throw new IOException("FileCopy: " + "no se puede copiar al directorio: "
                        + fromFileName);
            }
            if (!fromFile.canRead()) {
                throw new IOException("FileCopy: " + "el archivo origen no se puede leer: "
                        + fromFileName);
            }

            if (toFile.isDirectory()) {
                toFile = new File(toFile, fromFile.getName());
            }

            if (toFile.exists()) {
                if (!toFile.canWrite()) {
                    throw new IOException("FileCopy: "
                            + "no se puede escribir en la ruta de destino: " + toFileName);
                }
//            System.out.print("Desea sobreescribir el archivo existente " + toFile.getName()
//                    + "? (Y/N): ");
                System.out.flush();
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    System.in));
                String response = "y"; //in.readLine();
                if (!response.equals("Y") && !response.equals("y")) {
                    throw new IOException("FileCopy: "
                            + "existing file was not overwritten.");
                }
            } else {
                String parent = toFile.getParent();
                if (parent == null) {
                    parent = System.getProperty("user.dir");
                }
                File dir = new File(parent);
                if (!dir.exists()) {
                    throw new IOException("FileCopy: "
                            + "destination directory doesn't exist: " + parent);
                }
                if (dir.isFile()) {
                    throw new IOException("FileCopy: "
                            + "destination is not a directory: " + parent);
                }
                if (!dir.canWrite()) {
                    throw new IOException("FileCopy: "
                            + "destination directory is unwriteable: " + parent);
                }
            }

            FileInputStream from = null;
            FileOutputStream to = null;
            try {
                from = new FileInputStream(fromFile);
                to = new FileOutputStream(toFile);
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = from.read(buffer)) != -1) {
                    to.write(buffer, 0, bytesRead); // write
                }
            } finally {
                if (from != null) {
                    try {
                        from.close();
                    } catch (IOException e) {
                        ;
                    }
                }
                if (to != null) {
                    try {
                        to.close();
                    } catch (IOException e) {
                        ;
                    }
                }
            }
            result = true;
        } catch (Exception e) {
        }

        return toFile;
    }

    //TEST
    //<editor-fold defaultstate="collapsed" desc="Main">
    //    public static void main(String[] args) {
    //
    //        //        ArrayList<BigInteger> name = getSelectedItems("00001011010100000110000100001101");
    //        //        for (BigInteger nameByte : name) {
    //        //            System.out.println("Numero: " + nameByte);
    //        //        }
    //        //
    //        //        String balastName = "";
    //        //        for (BigInteger nameByte : name) {
    //        //            String value = nameByte.toString(2);
    //        //            value = getCeros(value);
    //        //            balastName += value;
    //        //        }
    //        //
    //        //        System.out.println("String final: " + balastName);
    //
    //        copyFile("C:\\Users\\EAFIT\\Desktop\\fondos\\red.jpg", "C:\\Users\\EAFIT\\Desktop\\redCopy.jpg");
    //
    //    }
    //</editor-fold>
    /**
     * Método que devuelve un array de elementos que están en memoria. sirve
     * para obtener los grupos, balastos, o escenas.
     *
     * @param numElementos tama;o del arreglo de elementos. En el caso de grupos
     * Por lo general el tama;o es 16 porque solo se pueden crear 16 grupos.
     * @param dao Objeto de tipo DAOJmodbus
     * @param initOffset ofset inicial a partir del cual se quiere recuperar la
     * informacion de los registros
     * @param usedRegisters Cuantos registros son usados a partir de la posicion
     * @param tamReg Tama;o del registro inicial de memoria que se pretende usar
     * @return Objeto de tipo ArrayList<String> que contiene la lista de los
     * elementos que estan en 1 o 0
     */
    public static ArrayList<String> getElementosEnMemoria(int numElementos, DAOJmodbus dao, int initOffset, int usedRegisters, int tamReg) {
        ArrayList<String> elementosadheridos = new ArrayList<String>();
        //int numGrupos = Integer.parseInt(PropHandler.getProperty("group.max.number"));
        try {
//                int initOffset = Integer.parseInt(PropHandler.getProperty("group.memory.added"));
//                int usedRegisters = Integer.parseInt(PropHandler.getProperty("group.memory.registers"));

//                int balastsOffset = initOffset;
//            int tamReg = 8;//cambiado por extencion de signo (lectura)
            int[] grupos = new int[numElementos];
            float bytesToRead = (grupos.length / tamReg) < 1 ? 1 : (grupos.length / tamReg);
            ArrayList<BigInteger> elementosAfectados = new ArrayList<BigInteger>();
            int[] addedG = dao.getRegValue(initOffset, usedRegisters);
//En este lugar esta el problema de los nombres de los grupos
            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
                elementosAfectados.add(new BigInteger(String.valueOf(addedG[i] & 0x00FF)));
            }

            String balastName = "";
            for (BigInteger nameByte : elementosAfectados) {
                String value = nameByte.toString(2);
                value = Utils.getCeros(value);
                balastName = value + balastName;
//                balastName = balastName + value;
            }

            int j = 0;
            // lee los 16 bits littlendian de derecha a izquierda
            for (int i = grupos.length - 1; i >= 0; i--) {
                String bit = String.valueOf(balastName.charAt(i));
                /*Aqui se establecen la lista de bits activos y por tanto la lista de 
                 * los grupos. Se mira el registro 40 y se lee 1 registro (de 16 bits) y con el se
                 * determinan el numero de grupos activos.
                 * 
                 */
                grupos[j] = Integer.parseInt(bit);
                j++;
            }

            //Get an ArrayList with the result
            for (int i = 0; i < grupos.length; i++) {
                if (grupos[i] != 0) {
                    elementosadheridos.add(String.valueOf(i));//se agrega a la lista de elementos adheridos
                }

            }

        } catch (Exception e) {
            System.out.println("Error al leer los elementos añadidos.");
            JOptionPane.showMessageDialog(null, "Error al leer los elementos añadidos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return elementosadheridos;
    }

    /**
     * Método que devuelve un array de elementos que están en memoria. sirve
     * para obtener los grupos, balastos, o escenas.
     *
     * @param numElementos tama;o del arreglo de elementos. En el caso de grupos
     * Por lo general el tama;o es 16 porque solo se pueden crear 16 grupos.
     * @param dao Objeto de tipo DAOJmodbus
     * @param initOffset ofset inicial a partir del cual se quiere recuperar la
     * informacion de los registros
     * @param usedRegisters Cuantos registros son usados a partir de la posicion
     * @param tamReg Tama;o del registro inicial de memoria que se pretende usar
     * @return Objeto de tipo ArrayList<String> que contiene la lista de los
     * elementos que estan en 1 o 0
     */
    public static ArrayList<String> getElementosEnMemoria(int numElementos, DAOJmodbus dao, int initOffset, int usedRegisters, int tamReg, int completacionCeros) {
        ArrayList<String> elementosadheridos = new ArrayList<String>();
        //int numGrupos = Integer.parseInt(PropHandler.getProperty("group.max.number"));
        try {
//                int initOffset = Integer.parseInt(PropHandler.getProperty("group.memory.added"));
//                int usedRegisters = Integer.parseInt(PropHandler.getProperty("group.memory.registers"));

//                int balastsOffset = initOffset;
//            int tamReg = 8;//cambiado por extencion de signo (lectura)
            int[] grupos = new int[numElementos];
            float bytesToRead = (grupos.length / tamReg) < 1 ? 1 : (grupos.length / tamReg);
            ArrayList<BigInteger> elementosAfectados = new ArrayList<BigInteger>();
            int[] addedG = dao.getRegValue(initOffset, usedRegisters);
//En este lugar esta el problema de los nombres de los grupos
            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
                elementosAfectados.add(new BigInteger(String.valueOf(addedG[i] & 0x00FF)));
            }

            String balastName = "";
            for (BigInteger nameByte : elementosAfectados) {
                String value = nameByte.toString(2);
                value = Utils.getCeros(value, completacionCeros);
                balastName = value + balastName;
//                balastName = balastName + value;
            }

            int j = 0;
            // lee los 16 bits littlendian de derecha a izquierda
            for (int i = grupos.length - 1; i >= 0; i--) {
                String bit = String.valueOf(balastName.charAt(i));
                /*Aqui se establecen la lista de bits activos y por tanto la lista de 
                 * los grupos. Se mira el registro 40 y se lee 1 registro (de 16 bits) y con el se
                 * determinan el numero de grupos activos.
                 * 
                 */
                grupos[j] = Integer.parseInt(bit);
                j++;
            }

            //Get an ArrayList with the result
            for (int i = 0; i < grupos.length; i++) {
                if (grupos[i] != 0) {
                    elementosadheridos.add(String.valueOf(i));//se agrega a la lista de elementos adheridos
                }

            }

        } catch (Exception e) {
            System.out.println("Error al leer los elementos añadidos.");
            JOptionPane.showMessageDialog(null, "Error al leer los elementos añadidos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return elementosadheridos;
    }

    /**
     * Método que devuelve un array de elementos que están en memoria. sirve
     * para obtener los grupos, balastos, o escenas.
     *
     * @param numElementos tama;o del arreglo de elementos. En el caso de grupos
     * Por lo general el tama;o es 16 porque solo se pueden crear 16 grupos.
     * @param dao Objeto de tipo DAOJmodbus
     * @param initOffset ofset inicial a partir del cual se quiere recuperar la
     * informacion de los registros
     * @param usedRegisters Cuantos registros son usados a partir de la posicion
     * @param tamReg Tamaño del registro inicial de memoria que se pretende usar
     * @param bytesToRead Cantidad de bits a leer. Las cadenas estan divididas
     * en bloques de a 2 bytes de 8 bits para lectura, de esa manera con 2 bytes
     * se completan los 16 bits que se necesitan por registro. Esto varia
     * dependiendo del mapa de memoria.
     * @param completacionCeros Completa la cantidad de ceros en la cadena de 1
     * y 0's
     * @return Objeto de tipo ArrayList<String> que contiene la lista de los
     * elementos que estan en 1 o 0
     */
    public static ArrayList<String> getElementosEnMemoria(int numElementos, DAOJmodbus dao, int initOffset, int usedRegisters, int tamReg, int bytesToRead, int completacionCeros) {
        ArrayList<String> elementosadheridos = new ArrayList<String>();
        //int numGrupos = Integer.parseInt(PropHandler.getProperty("group.max.number"));
        try {
//                int initOffset = Integer.parseInt(PropHandler.getProperty("group.memory.added"));
//                int usedRegisters = Integer.parseInt(PropHandler.getProperty("group.memory.registers"));

//                int balastsOffset = initOffset;
//            int tamReg = 8;//cambiado por extencion de signo (lectura)
            int[] grupos = new int[numElementos];
//            float bytesToRead = (grupos.length / tamReg) < 1 ? 1 : (grupos.length / tamReg);
            ArrayList<BigInteger> elementosAfectados = new ArrayList<BigInteger>();
            int[] addedG = dao.getRegValue(initOffset, usedRegisters);
//En este lugar esta el problema de los nombres de los grupos
            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
                elementosAfectados.add(new BigInteger(String.valueOf(addedG[i] & 0x00FF)));
            }

            String balastName = "";
            for (BigInteger nameByte : elementosAfectados) {
                String value = nameByte.toString(2);
                value = Utils.getCeros(value, completacionCeros);
                balastName = value + balastName;
//                balastName = balastName + value;
            }

            int j = 0;
            // lee los 16 bits littlendian de derecha a izquierda
//            for (int i = grupos.length - 1; i >= 0; i--) {
            int k = balastName.length() - 1;
            while (j < grupos.length) {

//            for (int i = balastName.length() - 1; i >= 0; i--) {
                String bit = String.valueOf(balastName.charAt(k));
                grupos[j] = Integer.parseInt(bit);
                j++;
                k--;
            }

            //Get an ArrayList with the result
            for (int i = 0; i < grupos.length; i++) {
                if (grupos[i] != 0) {
                    elementosadheridos.add(String.valueOf(i));//se agrega a la lista de elementos adheridos
                }

            }

        } catch (Exception e) {
            System.out.println("Error al leer los elementos añadidos.");
            JOptionPane.showMessageDialog(null, "Error al leer los elementos añadidos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return elementosadheridos;
    }

    /**
     * Metodo que retorna el array de elementos de enteros que representa a los
     * elementos en memoria
     *
     * @param numElementos Tama;o del agreglo de elementos. En el caso de grupos
     * es 16 porque ese es el numero maximo de grupos que se pueden crear
     * @param dao objeto del tipo DaoJmodbus que posee cad clase que instancia
     * este método
     * @param initOffset posicion inicial del registro a partir del cual se va a
     * hacer el procesamiento de informacion
     * @param usedRegisters cuantos registros son usados a partir de la posicion
     * inicial?
     * @param tamReg tama;o del registro inical de memoria que se pretende usar
     * en caso de lectura es 8.
     *
     * @return array de enteros que representan los valores binarios de los
     * elementos activados y desactivados para activados es 1 y 0 en el caso
     * contrario.
     */
    public static int[] getElementosEnMemoriaInt(int numElementos, DAOJmodbus dao, int initOffset, int usedRegisters, int tamReg) {
//    int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));

        int[] balastos = new int[numElementos];
        try {
//            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));
//            int usedRegisters = Integer.parseInt(PropHandler.getProperty("balast.memory.registers"));
//            int tamReg = 16;

            float bytesToRead = balastos.length / tamReg;
            ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
            int[] addedB = dao.getRegValue(initOffset, usedRegisters);

            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
//                    affectedBalasts.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
                affectedBalasts.add(new BigInteger(String.valueOf(addedB[i] & 0x00FF)));
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
            System.out.println("Error al leer los balastos añadidos.");
            e.printStackTrace();
        }

        return balastos;

    }

    /**
     * Metodo que retorna el array de elementos de enteros que representa a los
     * elementos en memoria
     *
     * @param numElementos Tamaño del agreglo de elementos. En el caso de grupos
     * es 16 porque ese es el numero maximo de grupos que se pueden crear
     * @param dao objeto del tipo DaoJmodbus que posee cad clase que instancia
     * este método
     * @param initOffset posicion inicial del registro a partir del cual se va a
     * hacer el procesamiento de informacion
     * @param usedRegisters cuantos registros son usados a partir de la posicion
     * inicial?
     * @param tamReg tama;o del registro inical de memoria que se pretende usar
     * en caso de lectura es 8.
     * @param completacionCeros numero que determina de cuantos bits va a ser la
     * cadena. puede ser de 8 o 16
     *
     * @return array de enteros que representan los valores binarios de los
     * elementos activados y desactivados para activados es 1 y 0 en el caso
     * contrario.
     */
    public static int[] getElementosEnMemoriaInt(int numElementos, DAOJmodbus dao, int initOffset, int usedRegisters, int tamReg, int completacionCeros) {
//    int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));

        int[] balastos = new int[numElementos];
        try {
//            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));
//            int usedRegisters = Integer.parseInt(PropHandler.getProperty("balast.memory.registers"));
//            int tamReg = 16;

            float bytesToRead = balastos.length / tamReg;
            ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
            int[] addedB = dao.getRegValue(initOffset, usedRegisters);

            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
//                    affectedBalasts.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
                affectedBalasts.add(new BigInteger(String.valueOf(addedB[i] & 0x00FF)));
            }

            String balastName = "";
            for (BigInteger nameByte : affectedBalasts) {
                String value = nameByte.toString(2);
                value = Utils.getCeros(value, completacionCeros);
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
     * Método que carga los elementos que se encuentran en memoria.
     *
     * @param numElementos
     * @param dao
     * @param initOffset
     * @param usedRegisters
     * @param tamReg
     * @param bytesToRead
     * @param completacionCeros
     * @return
     */
    public static int[] getElementosEnMemoriaInt(int numElementos, DAOJmodbus dao, int initOffset, int usedRegisters, int tamReg, int bytesToRead, int completacionCeros) {
//    int numBalastos = Integer.parseInt(PropHandler.getProperty("balast.max.number"));

        int[] array = new int[numElementos];
        try {
//            int initOffset = Integer.parseInt(PropHandler.getProperty("balast.memory.added"));
//            int usedRegisters = Integer.parseInt(PropHandler.getProperty("balast.memory.registers"));
//            int tamReg = 16;

//            float bytesToRead = balastos.length / tamReg;
            ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();
            int[] addedB = dao.getRegValue(initOffset, usedRegisters);

            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
//                    affectedBalasts.add(new BigInteger(String.valueOf(dao.getRegValue(balastsOffset)&0xFFFF)));
                affectedBalasts.add(new BigInteger(String.valueOf(addedB[i] & 0x00FF)));
            }

            String balastName = "";
            for (BigInteger nameByte : affectedBalasts) {
                String value = nameByte.toString(2);
                value = Utils.getCeros(value, completacionCeros);
                balastName = value + balastName;
            }

            int k = balastName.length() - 1;
            int j = 0;
            while (j < array.length) {

                String bit = String.valueOf(balastName.charAt(k));
                array[j] = Integer.parseInt(bit);
                j++;
                k--;
            }

        } catch (Exception e) {
            System.out.println("Error al leer los balastos añadidos.");
            e.printStackTrace();
        }

        return array;

    }

    /**
     * Método que encripta un nombre dentro de un array
     *
     * @param groupsArray array de enteros que contiene la cadena a desencriptar
     * @param nameOffset numero inicial en el registro
     * @param name cadena de texto que contiene la representacion de unos y
     * ceros
     * @param bits numero de bits o largo del nombre. Típicamente en este caso
     * es 5.
     */
    public static void encriptarNombre(int[] groupsArray, int nameOffset, String name, int bits) {
        //name
//            Con esta rutina se calcula el nombre
        try {
//                int nameOffset = 2;
            ArrayList<BigInteger> balastNameBytes = UtilsJmodbus.getNameBytesReverse(name);
            int size = balastNameBytes.size();
            for (int i = 0; i < bits; i++) {
                if (i < size) {
                    groupsArray[nameOffset] = balastNameBytes.get(i).intValue();
                } else {
                    groupsArray[nameOffset] = 0;
                }
                nameOffset++;
            }
        } catch (Exception e) {
            System.out.println("Hubo problemas desencriptando el nombre del elemento");
        }
    }

    /**
     * Método que desencripta el nombre que esté en bits en un arreglo
     *
     * @param arrayElementos array de elementos en el que se encuentra el nombre
     * encriptado
     * @param nameOffset valor desde el cual se quiere desencriptar
     * @param tam Es el tamannio de la palabra a desencriptar en bits, en este
     * caso tipicamente es 5
     * @return
     */
    public static String desencriptarNombre(int[] arrayElementos, int nameOffset, int tam) {
        //name
//        int nameOffset = 3;
        ArrayList<BigInteger> balastNameBytes = new ArrayList<BigInteger>();
        String balastName = "";
        //Get the bytes from the card.
        for (int i = 0; i < tam; i++) {
            int tales = 0;
            tales |= arrayElementos[nameOffset] & 0xFFFF;
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
        String nombre = new String(totalBytes.toByteArray());
        return nombre;
    }

    /**
     *
     * @param array arreglo de int que contiene los elementos que se quieren
     * buscar
     * @param arrayOffset Lugar desde el cual se quiere buscar
     * @param cuantosElementos numero de elementos a procesar. En el caso de los
     * balastos es 64
     * @param tamReg Tamanio del registro. Puede ser 8 o 16. En el caso de los
     * balastros afectados el tamanio es 16
     * @param completacionDeCeros es un numero que puede ser 8 o 16. en el caso
     * de los balastros afectados es 16
     * @return Un array de enteros que esta compuestos por unos y ceros, y que
     * representan los balastros afectados.
     */
    public static int[] obtenerElementosAfectados(int[] array, int arrayOffset, int cuantosElementos, int tamReg, int completacionDeCeros) {
        try {
//               balastsOffset = 7;
//                int tamReg = 16;
             /*tiene por largo el numero de elementos del arreglo grouparray por ejemplo
             * balastros deberian ser 64*/
            int[] balastos = new int[cuantosElementos];
            float bytesToRead = balastos.length / tamReg;
            ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();

            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
                affectedBalasts.add(new BigInteger(String.valueOf(array[arrayOffset] & 0xFFFF)));//Pendiente por leer de a 8 bits
                arrayOffset = arrayOffset + 1;
            }

            String balastName = "";
            for (BigInteger nameByte : affectedBalasts) {
                String value = nameByte.toString(2);
//                    Se establece que la completacion de ceros es de 16 bits, porque depende del tamanio del
//                    registro. esto es especialmente verdad para la carga de los balasrtos afectados
                value = Utils.getCeros(value, completacionDeCeros);
                balastName = value + balastName;
            }

            int j = 0;
            for (int i = balastos.length - 1; i >= 0; i--) {
                String bit = String.valueOf(balastName.charAt(i));
                balastos[j] = Integer.parseInt(bit);
                j++;
            }
            return balastos;
//              

        } catch (Exception e) {
            System.out.println("Error al leer los elementos afectados.");
            JOptionPane.showMessageDialog(null, "Error al cargar los elementos afectados " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
      }

    public static int[] obtenerElementosAfectados(int[] array, int arrayOffset, int cuantosElementos, int tamReg, int bytesToRead, int completacionDeCeros) {
        try {
//               balastsOffset = 7;
//                int tamReg = 16;
             /*tiene por largo el numero de elementos del arreglo grouparray por ejemplo
             * balastros deberian ser 64*/
            int[] balastos = new int[cuantosElementos];
//            float bytesToRead = balastos.length / tamReg;
            ArrayList<BigInteger> affectedBalasts = new ArrayList<BigInteger>();

            //Get the bytes from the card.
            for (int i = 0; i < bytesToRead; i++) {
                affectedBalasts.add(new BigInteger(String.valueOf(array[arrayOffset] & 0xFFFF)));//Pendiente por leer de a 8 bits
                arrayOffset = arrayOffset + 1;
            }

            String balastName = "";
            for (BigInteger nameByte : affectedBalasts) {
                String value = nameByte.toString(2);
//                    Se establece que la completacion de ceros es de 16 bits, porque depende del tamanio del
//                    registro. esto es especialmente verdad para la carga de los balasrtos afectados
                value = Utils.getCeros(value, completacionDeCeros);
                balastName = value + balastName;
            }

            int j = 0;
            for (int i = balastos.length - 1; i >= 0; i--) {
                String bit = String.valueOf(balastName.charAt(i));
                balastos[j] = Integer.parseInt(bit);
                j++;
            }
            return balastos;
//              

        } catch (Exception e) {
            System.out.println("Error al leer los elementos afectados.");
            JOptionPane.showMessageDialog(null, "Error al cargar los elementos afectados " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
}

        /**
         * Metodo que se usa para escribir por partes los conjuntos de array que
         * anteriormente estaban contiguos. El ejemplo mas claro es el array que
         * contiene los balastos afectados por una entrada determinada. Escribe
         * de manera coherente, de tal forma que la escritura y la lectura se
         * manejan con el mismo indice en el mapa de memoria.
         *
         * @param initOffset ofset inical en el registro. En el caso de las
         * entradas es el 1000
         * @param offsetTemporal Es el ofset en el array que contiene los
         * elementos, es decir, desde donde se va a comenzar el proceso.
         * @param cuantosBloques Cuantos bloques de 16 bits se van a procesar.
         * En el caso de las entradas son 4 bloques.
         * @param inArray Array que contiene los elementos a ser procesados
         * @param dao Es el objeto dao generico que contiene cada clase
         * daojmodbus
         */
    

    public static void escribirPorPartes(int initOffset, int offsetTemporal, int cuantosBloques, int[] inArray, DAOJmodbus dao) {
        try {
            for (int i = 0; i < cuantosBloques; i++) {
                int bytebalastos = inArray[offsetTemporal];
                setSingleReg(initOffset + offsetTemporal, bytebalastos, dao);
                offsetTemporal += 2;

            }
        } catch (Exception e) {
            System.out.println("Error al leer los elementos afectados.");
            JOptionPane.showMessageDialog(null, "Error al cargar los elementos afectados " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void setSingleReg(int pos, int mode, DAOJmodbus dao) {
        int[] values = {mode};
        dao.setRegValue(pos, values);
    }
}
