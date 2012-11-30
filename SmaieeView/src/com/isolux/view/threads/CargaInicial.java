/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.view.threads;

import com.isolux.control.GeneralControl;
import com.isolux.view.PpalView;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.tree.TreePath;

/**
 *
 * @author Sebastian Rodriguez
 */

    public class CargaInicial extends SwingWorker<Boolean, Boolean> {

        PpalView ppalView;

        public CargaInicial(PpalView ppalView) {
            this.ppalView = ppalView;
        }

        @Override
        protected Boolean doInBackground() throws Exception {

            GeneralControl generalCtrl = new GeneralControl();

//            ppalView.getArbol_jTree().setEnabled(false);
            ppalView.getGeneralCtrl().habilitarTodo(ppalView.getTabbedPane(), false);
            ppalView.getBarraProgreso_jProgressBar().setIndeterminate(true);
            Boolean c = generalCtrl.cargaInicial(ppalView, ppalView.getBalastoCtrl(), ppalView.getGroupsCtrl(), ppalView.getSceneCtrl(), ppalView.getEventCtrl(), ppalView.getInsCtrl(), ppalView.getBalastoConfigCtrl());


            return c;

        }

        @Override
        protected void done() {
//            expandirArbol(getArbol_jTree());
            Object nodo = ppalView.getArbol_jTree().getModel().getRoot();
            ppalView.expandirArbol(ppalView.getArbol_jTree(), new TreePath(nodo), true);
            ppalView.getBarraProgreso_jProgressBar().setIndeterminate(false);
            ppalView.getStatusLabel().setText("");
//            ppalView.getArbol_jTree().setEnabled(true);
            ppalView.getGeneralCtrl().habilitarTodo(ppalView.getTabbedPane(), true);
            super.done();

        }
    }

