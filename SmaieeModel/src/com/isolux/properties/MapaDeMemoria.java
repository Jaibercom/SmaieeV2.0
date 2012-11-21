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
//    public final static int BALASTO_NIVEL=Integer.parseInt(PropHandler.getProperty("balast.memory.levelOffset"));
    public final static int BALASTO_OFFSET_NIVELES_ESCENAS = Integer.parseInt(PropHandler.getProperty("balast.memory.valorescenas.memoryoffset"));
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Miscelaneas">
    public static final int REINTENTOS = 5;
    /**
     * Retardo que se inserta entre las operaciones de escritura y lectura de registros.
     */
    public static final int DELAY_OPERACIONES_CORTO = 500;
    public static final int DELAY_OPERACIONES_LARGO = 1500;
    
    //</editor-fold>
}
