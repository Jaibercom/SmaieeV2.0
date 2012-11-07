/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.view.PpalView;

/**
 * Clase que es el padre de los controles. Esta clase implementa métodos comunes
 * que tienen las que implementen el control.
 *
 * @author Juan Camilo Canias Gómez
 */
public interface ElementoControl_Interface {

    /**
     * Método que refresca la vista de grupos cuando se ejecuta una operacion de
     * escritura o borrado.
     *
     * @param ppalView
     */
    public void refrescarVista(PpalView ppalView);

    /**
     * Método que muestra los elementos disponibles o los elementos 
     * candidatos a ser grabado.
     * @param ppalView
     * @return
     */
    public String[] elementosDisponibles(PpalView ppalView);

    /**
     * Método que limpia la interfaz de usuario
     * @param ppalView
     */
    public void cleanView(PpalView ppalView);
    
    /**
     * Método que graba el elemento, tomando como base la información tomada desde
     * la interfaz.
     * @param ppalView
     */
    public void saveElement(PpalView ppalView);

    /**
     * Método que elimina el elemento actual.
     * @param ppalView
     */
    public void deleteElement(PpalView ppalView);
    
    /**
     * Lee la lista de elementos insertados.
     * @param ppalView
     */
    public void readElements(PpalView ppalView);

    /**
     * Filter the used groups (add existing groups to the menu).
     * Filtra los grupo usados (agrega los grupos existentes al menú)
     * @param ppalView
     */
    public void filterAddedElements(PpalView ppalView);

    /**
     * Muestra la información del elemento seleccionado.
     * @param num numero que representa el elemento seleccionado
     * @param ppalView
     */
    public void showSelectedElement(String num, PpalView ppalView);
}
