package com.isolux.dao.modbus;

import com.isolux.dao.properties.PropHandler;
import net.sourceforge.jmodbus.ModbusTCPMaster;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author JUAN DIEGO
 */
public class DAOJmodbus {

    private static ModbusTCPMaster master;
    private static DAOJmodbus instancia = null;

    public static DAOJmodbus getInstancia() {

        if (instancia == null) {
            instancia = new DAOJmodbus();
        }
        return instancia;
    }

    private DAOJmodbus() {
        String ip = PropHandler.getProperty("general.ip");
        int port = Integer.parseInt(PropHandler.getProperty("general.port"));
        this.master = new ModbusTCPMaster(ip, port);
    }

    public void destroyDAOJmodbus() {
        this.master = null;
    }

    /**
     * Get registers values.
     *
     * @param pos
     * @param length
     * @return
     */
    synchronized public int[] getRegValue(int pos, int length) {
        int[] result = new int[length];
        if (length == 128) {
            result = getRegValueGeneral(pos, length, 8);

        } else if (length >= 60 && length < 128) {
            result = getRegValue60(pos, length);
        } else {
            result = getRegValueNormal(pos, length);
        }
        return result;
    }

    /**
     * Get registers values.
     *
     * @param pos
     * @param length
     * @return
     */
    synchronized public boolean setRegValue(int pos, int[] values) throws Exception {
        int length = values.length;
        boolean result = false;
        if (length == 128) {
            result = setRegValue128(pos, values);

        } else if (length >= 60) {
            result = setRegValue60(pos, values);
        } else {
            //            result = setRegValueNormal(pos, values); //??
            result = setRegValueNormal(pos, values); //??
//            result = master.writeMultipleRegisters(pos, values.length, values);


            
        }
        return result;
    }

    /**
     * Get information from the card.
     *
     * @return
     */
    synchronized private int[] getRegValue60(int pos, int length) {
        int[] result;
        if (length > 60) {
            result = new int[0];
            int len = length / 2;
            int[] result1 = getRegValueNormal(pos, len); //new int[len];
            int[] result2 = getRegValueNormal(pos + len, length - len); //new int[length-len];
            result = ArrayUtils.addAll(result1, result2);
        } else {
            result = new int[length];
            master.readInputRegisters(1, pos, length, 1, result);
        }
        return result;
    }

    /**
     * Set information in the card. unit id, offset, length, transaction id,
     * values to write array.
     */
    synchronized private boolean setRegValue60(int pos, int[] values) {
        boolean result = false;
        int length = values.length;
        if (length > 60) {

            boolean res1 = false;
            boolean res2 = false;
            int len = length / 2;
            int[] values1 = ArrayUtils.subarray(values, 0, len);
            int[] values2 = ArrayUtils.subarray(values, len, length);

//            res1 = master.writeMultipleRegisters(1,pos,values1.length,1, values1);
//             res2 = master.writeMultipleRegisters(1,pos,values2.length,1, values2);
            int[] totalValues = ArrayUtils.addAll(values1, values2);
            result = master.writeMultipleRegisters(1, pos, totalValues.length, 1, totalValues);
//             result=res1&&res2;


        } else {
            result = master.writeMultipleRegisters(1, pos, values.length, 1, values);

        }
        return result;
    }

    /**
     * Get information from the card. unit id, offset, length, transaction id,
     * result array
     *
     * @return
     */
    synchronized private int[] getRegValueNormal(int pos, int length) {
        int[] result = new int[length];
        master.readInputRegisters(1, pos, length, 1, result);
        return result;
    }

    /**
     * Set information in the card. unit id, offset, length, transaction id,
     * values to write array.
     */
    synchronized private boolean setRegValueNormal(int pos, int[] values) throws Exception {
        boolean result = master.writeMultipleRegisters(1, pos, values.length, 1, values);
        return result;
    }

    /**
     * Método que lee un conjunto de registros. Estaba pensado para 128bits,
     * pero se puede pensar para cualquier numero de bits. Funciona para
     * cualquier numero
     *
     * @param pos Representa el offset inicial
     * @param length Numero de bloques a leer. Típicamente para saber que numero
     * es este se hace lo siguiente. Si son 1024 elementos se divide por el
     * tamanio del registro que es normalmente 8, por tanto son 128.
     * @param len Numero de registros que se han de leer por iteracion. Se usa
     * este numero para leer por porciones o en varios intentos la memoria. Es
     * un numero entre 10 y 60
     * @return int[] result, que representa el array construido sin importar su
     * extension.
     */
    synchronized private int[] getRegValueGeneral(int pos, int length, int len) {

        int[] result = new int[0];

//        int len = 50;

        /*Se calcula el numero de arrays que se van a crear dependiendo del tamanio length
         y del numero de elementos len que va a tener cada arreglo*/

        int lim = 0;
        if (length % len == 0) {
            lim = length / len;
        } else {
            lim = length / len + 1;
        }


        int[] result1 = null;
        for (int i = 0; i < lim; i++) {

            if (i != lim - 1) {

                result1 = getRegValueNormal(pos, len);
                pos = pos + len + 1;//new int[len];

//                result1 = getRegValueNormal(pos, len);
            } else {
                result1 = getRegValueNormal(pos, length - (i * len));
            }
            result = ArrayUtils.addAll(result, result1);
        }

        //<editor-fold defaultstate="collapsed" desc="Codigo viejo">
        //        int[] result2 = getRegValueNormal(pos, len);
        //        pos = pos + len + 1;
        //        int[] result3 = getRegValueNormal(pos, length - (2 * len)); //new int[length-len];
        //
        //        result = ArrayUtils.addAll(result1, result2);
        //        result = ArrayUtils.addAll(result, result3);
        //</editor-fold>


        return result;
    }

    /**
     * Escribe un elemento que sea de 128 bits de tamanio
     *
     * @param pos
     * @param values
     * @return
     */
    synchronized private boolean setRegValue128(int pos, int[] values) {
        boolean result = false;
        int length = values.length;
        if (length == 128) {
            int len = length / 2;

            int[] values1 = ArrayUtils.subarray(values, 0, len);
            int[] values2 = ArrayUtils.subarray(values, len, length);
            int[] values3 = ArrayUtils.subarray(values, len, length);

            int[] totalValues = ArrayUtils.addAll(values1, values2);

            master.writeMultipleRegisters(1, pos, totalValues.length, 1, totalValues);

            totalValues = ArrayUtils.addAll(totalValues, values3);
            master.writeMultipleRegisters(1, pos, totalValues.length, 1, totalValues);

        } else {
            result = master.writeMultipleRegisters(1, pos, values.length, 1, values);

        }
        return result;
    }
}
