/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.view.PpalView;

/**
 *Clase que es el padre de los controles. Esta clase implementa métodos comunes 
 * que tienen las clases hijas.
 * @author Juan Camilo Canias Gómez
 */
public interface ElementoControl_Interface {

      public void refrescarVista(PpalView ppalView);
      
      public String[] elementosDisponibles(PpalView ppalView);
    
    
    
}
