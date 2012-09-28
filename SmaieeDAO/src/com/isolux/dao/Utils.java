/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao;

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

/**
 *
 * @author EAFIT
 */
public class Utils {

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
     * @param fromFileName
     * @param toFileName
     * @throws IOException 
     */
    public static File copyFile(String fromFileName, String toFileName){
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
    public static void main(String[] args) {
        
        //        ArrayList<BigInteger> name = getSelectedItems("00001011010100000110000100001101");
        //        for (BigInteger nameByte : name) {
        //            System.out.println("Numero: " + nameByte);
        //        }
        //
        //        String balastName = "";
        //        for (BigInteger nameByte : name) {
        //            String value = nameByte.toString(2);
        //            value = getCeros(value);
        //            balastName += value;
        //        }
        //
        //        System.out.println("String final: " + balastName);
                    
        copyFile("C:\\Users\\EAFIT\\Desktop\\fondos\\red.jpg", "C:\\Users\\EAFIT\\Desktop\\redCopy.jpg");

    }
}
