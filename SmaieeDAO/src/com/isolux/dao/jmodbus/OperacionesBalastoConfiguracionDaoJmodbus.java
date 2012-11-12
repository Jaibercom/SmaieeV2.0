/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.dao.jmodbus;

import com.isolux.dao.properties.PropHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que ejecuta las acciones a nivel del blasto y no a niverl del bufer del
 * SMAIEE.
 *
 * @author Juan Camilo Cañas Gómez
 */
public class OperacionesBalastoConfiguracionDaoJmodbus extends ElementoDAOJmobdus {

    /**
     * Codigo de operacion de escribir valores. Se usa para escribir los valores
     * en un balasto
     */
    public static final int OPCODE_ESCRIBIR_VALORES = 20;
    /**
     * Codigo de operacion para leer valores. Se usa para leer los valores de
     * los campos del balasto
     */
    public static final int OPCODE_LEER_VALORES = 21;
    /**
     * Codigo de operación para cambiar la dirección de un balasto.
     */
    public static final int OPCODE_CAMBIAR_DIR_BALASTO = 22;
    /**
     * Codigo de operacion para resetear los valores de los balastos.
     */
    public static final int OPCODE_RESET = 23;
    /**
     * Codigo de operacion de verificacion de balastos en red.
     */
    public static final int OPCODE_VERIFICA_RED = 24;
    /**
     * Código de operación que se usa para cuando un balasto es seleccionado y
     * para que titile
     */
    public static final int OPCODE_SELECCIONAR_BALASTO = 20;
    private final int regNumBalsat = Integer.parseInt(PropHandler.getProperty("balast.init.position"));
    private static OperacionesBalastoConfiguracionDaoJmodbus instancia=null;
        
    
    private OperacionesBalastoConfiguracionDaoJmodbus() {
        
       
    }

    /**
     * Obtiene la instancia única de la clase.
     * @return 
     */
    public static OperacionesBalastoConfiguracionDaoJmodbus getInstancia() {
        if (instancia==null) {
            instancia=new OperacionesBalastoConfiguracionDaoJmodbus();
        }         
        return instancia;
    }
    
    

    public void escribirValores() {
    }

    /**
     * Método que lee los valores que contiene un balasto a nivel del balasto y
     * no del buffer. Esto quiere decir que lee la información directamente del
     * balasto y no del SMAIEE. Para reflejar los cambios a nivel de interfaz de
     * usuario es necesario que despues de aplicar este método se ejecute los
     * metodos de readElement que hacen parte de la interfaz
     *
     * @param numBalasto Numero del balasto que se quiere leer.
     */
    public boolean leeValores(int numBalasto) {

        System.out.println("Leyendo los datos del balasto " + numBalasto);
        try {
            setMode(MODE_CONFIG);

            //escribimos para obtener la info del buffer (el numero del balasto deseado.
            setSingleReg(regNumBalsat, numBalasto);
            //luego escribimos en el registro 1 la orden de lectura
            setSingleReg(1, OPCODE_LEER_VALORES);

            setMode(MODE_RUN);
            Logger.getLogger(OperacionesBalastoConfiguracionDaoJmodbus.class.getName()).log(Level.INFO, "Leidos los datos del balasto {0}", numBalasto);

            return true;

        } catch (NumberFormatException ex) {
            Logger.getLogger(OperacionesBalastoConfiguracionDaoJmodbus.class.getName()).log(Level.INFO, "No se pudo leer los datos del balasto " + numBalasto, ex);

        } finally {
            System.out.println("No se pudo leer los datos del balasto " + numBalasto);
            return false;
        }



    }

    /**
     * Método que cambia la dirección de un balasto a una nueva.
     *
     * @param numBalasto
     * @param nuevaDir
     */
    public boolean cambiarDirBalasto(int numBalasto, int nuevaDir) {

        try {
            Logger.getLogger(OperacionesBalastoConfiguracionDaoJmodbus.class.getName()).log(Level.INFO, "Cambiando la direcci\u00f3n del balasto {0} por la direcci\u00f3n {1}", new Object[]{numBalasto, nuevaDir});

            int dirOrigialOffset = Integer.parseInt(PropHandler.getProperty("balast.init.position"));
            int dirbOffset = Integer.parseInt(PropHandler.getProperty("balast.memoy.dirbOffset"));

            setMode(MODE_CONFIG);

            setSingleReg(dirOrigialOffset, numBalasto);

            setSingleReg(dirbOffset, nuevaDir);

            setSingleReg(1, OPCODE_CAMBIAR_DIR_BALASTO);

            setMode(MODE_RUN);

            Logger.getLogger(OperacionesBalastoConfiguracionDaoJmodbus.class.getName()).log(Level.INFO, "Direcci\u00f3n del balasto {0} cambiada  por la direcci\u00f3n {1}", new Object[]{numBalasto, nuevaDir});
            return true;
        } catch (Exception e) {
            Logger.getLogger(OperacionesBalastoConfiguracionDaoJmodbus.class.getName()).log(Level.INFO, "No se pudo cambiar la dirección del balasto " + numBalasto, e);
            return false;
        }
    }

    /**
     * Método que resetea los valores de un balasto.
     *
     * @param numBalasto valasto a ser reseteado
     * @return true si la operación es exitosa.
     */
    public boolean reset(int numBalasto) {
        try {
            setMode(MODE_CONFIG);
            setSingleReg(0, OPCODE_RESET);
            leeValores(numBalasto);
            return true;
        } catch (Exception e) {
            System.out.println("No se pudo resetear el balasto " + numBalasto);
            Logger.getLogger(OperacionesBalastoConfiguracionDaoJmodbus.class.getName()).log(Level.WARNING, null, e);
            return false;
        }
    }

    /**
     * Método que lee los valores de los balstos que se encuentran en la red.
     */
    public void balastosEnRed() {
        setMode(MODE_CONFIG);
        setSingleReg(1, OPCODE_VERIFICA_RED);
        setMode(MODE_RUN);

    }

    /**
     * Método que se usa para que cuando se seleccione un balaso este prenda y
     * apague para indicar que se seleccionó
     */
    public void seleccionaBalasto() {
        setMode(MODE_CONFIG);
        setSingleReg(1, OPCODE_SELECCIONAR_BALASTO);
        setMode(MODE_RUN);
    }
}
