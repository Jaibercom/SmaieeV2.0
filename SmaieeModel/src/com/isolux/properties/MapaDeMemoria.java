/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.properties;

/**
 * Clase que representa las constantes del mapa de memoria de la aplicación.
 * Aquí se encuentran todos los valores enteros que están consignados en el mapa
 * de memoria. Son valores estáticos para ser accesados desde el contexto de la
 * aplicación
 *
 * @author Juan Camilo Cañas Gómez
 *
 */
public class MapaDeMemoria {

    //<editor-fold defaultstate="collapsed" desc="Balastos Mapa de memoria">
    public final static int BALASTO_OFFSET_NUMERO = Integer.parseInt(PropHandler.getProperty("balast.init.position"));
    public final static int BALASTO_NIVEL = Integer.parseInt(PropHandler.getProperty("balast.memory.levelOffset"));
    public final static int BALASTO_NUMB = Integer.parseInt(PropHandler.getProperty("balast.memory.numb"));
    public final static int BALASTO_DIRB = Integer.parseInt(PropHandler.getProperty("balast.memory.dirb"));
//    public final static int BALASTO_NIVEL=Integer.parseInt(PropHandler.getProperty("balast.memory.levelOffset"));
    public final static int BALASTO_OFFSET_NIVELES_ESCENAS = Integer.parseInt(PropHandler.getProperty("balast.memory.valorescenas.memoryoffset"));
    public final static int BALASTO_MEMORY_SIZE = Integer.parseInt(PropHandler.getProperty("balast.memory.size"));
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Miscelaneas">
    public static final int REINTENTOS = 5;
    /**
     * Retardo que se inserta entre las operaciones de escritura y lectura de
     * registros.
     */
    public static final int DELAY_OPERACIONES_CORTO = 500;
    public static final int DELAY_OPERACIONES_LARGO = 1500;
    public static final int BALASTO_DE_FABRICA=Integer.parseInt(PropHandler.getProperty("balast.config.defabrica"));
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Configuracion general">
    public final static String CONFIGURACION_GENERAL_IP_GENERAL = PropHandler.getProperty("general.ip");
    public final static String CONFIGURACION_GENERAL_IP_MASCARA = PropHandler.getProperty("general.ip.mask");
    public final static String CONFIGURACION_GENERAL_IP_GATEWAY = PropHandler.getProperty("general.ip.gateway");
    public final static int CONFIGURACION_GENERAL_PUERTO_GENERAL = Integer.parseInt(PropHandler.getProperty("general.port"));
    public final static int CONFIGURACION_GENERAL_BITS_NOMBRE = Integer.parseInt(PropHandler.getProperty("general.name.bytes"));
    public final static int CONFIGURACION_GENERAL_PUERTO_MAXIMONUM = Integer.parseInt(PropHandler.getProperty("general.offset.max"));
    //</editor-fold>
}
