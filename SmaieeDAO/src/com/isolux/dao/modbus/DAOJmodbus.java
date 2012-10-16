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

    public DAOJmodbus() {
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
    public int[] getRegValue(int pos, int length) {
        int[] result = new int[length];
        if (length == 128) {
            result = getRegValue128(pos, length);

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
    public boolean setRegValue(int pos, int[] values) {
        int length = values.length;
        boolean result = false;
        if (length == 128) {
            result = setRegValue128(pos, values);

        } else if (length >= 60) {
            result = setRegValue60(pos, values);
        } else {
            result = setRegValueNormal(pos, values); //??
        }
        return result;
    }

    /**
     * Get information from the card.
     *
     * @return
     */
    private int[] getRegValue60(int pos, int length) {
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
    private boolean setRegValue60(int pos, int[] values) {
        boolean result = false;
        int length = values.length;
        if (length > 60) {
            int len = length / 2;
            int[] values1 = ArrayUtils.subarray(values, 0, len);
            int[] values2 = ArrayUtils.subarray(values, len, length);

            int[] totalValues = ArrayUtils.addAll(values1, values2);
            master.writeMultipleRegisters(1, pos, totalValues.length, 1, totalValues);

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
    private int[] getRegValueNormal(int pos, int length) {
        int[] result = new int[length];
        master.readInputRegisters(1, pos, length, 1, result);


        //TEST
//        master.readMultipleRegisters(1, pos, length, 1, result);
        return result;
    }

    /**
     * Set information in the card. unit id, offset, length, transaction id,
     * values to write array.
     */
    private boolean setRegValueNormal(int pos, int[] values) {
        boolean result = master.writeMultipleRegisters(1, pos, values.length, 1, values);
        return result;
    }

    private int[] getRegValue128(int pos, int length) {

        int[] result = new int[0];

        int len = 60;


        int[] result1 = getRegValueNormal(pos, len);
        pos = pos + len + 1;//new int[len];
        int[] result2 = getRegValueNormal(pos, len);
        pos = pos + len + 1;
        int[] result3 = getRegValueNormal(pos, 8); //new int[length-len];

        result = ArrayUtils.addAll(result1, result2);
        result = ArrayUtils.addAll(result, result3);


        return result;
    }

    /**
     * Escribe un elemento que sea de 128 bits de tamanio
     *
     * @param pos
     * @param values
     * @return
     */
    private boolean setRegValue128(int pos, int[] values) {
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
