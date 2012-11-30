/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

import java.awt.Component;
import java.awt.Container;

/**
 *
 * @author Sebastian Rodriguez
 */
public class SwingUtils {

    public static void setEnableContainer(Container c, boolean band) {

        Component[] components = c.getComponents();
        c.setEnabled(band);
        for (int i = 0; i < components.length; i++) {
            components[i].setEnabled(band);

            if (components[i] instanceof Container) {
                setEnableContainer((Container) components[i], band);
            }

        }
    }
}
