/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.control;

import com.isolux.bo.Balasto;
import com.isolux.dao.Utils;
import com.isolux.dao.jmodbus.BalastoDAOJmodbus;
import com.isolux.dao.modbus.DAOJamod;
import com.isolux.dao.properties.Constants;
import com.isolux.dao.properties.PropHandler;
import com.isolux.dao.properties.facadeBack.TiempoRealDAO;
import com.isolux.view.threads.CheckBalastsLevels;
import com.isolux.utils.Validacion;
import com.isolux.view.AreaBalasts;
import com.isolux.view.PpalView;
import com.isolux.view.threads.ThreadManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.awt.Image;
import javax.swing.table.DefaultTableModel;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author EAFIT
 */
public class RealTimeControl {
    
    private CheckBalastsLevels balastsLevelsThread;
    
    public RealTimeControl(){
        this.balastsLevelsThread = new CheckBalastsLevels();
    }
    
    /**
     * Get the selected balasts.
     * @param ppalView 
     */
    public static void selectBalasts(PpalView ppalView, HashMap<String, Balasto> balasts, ArrayList<Integer> selectedBalasts, String areaName){
        AreaBalasts areaBalasts = new AreaBalasts(balasts, selectedBalasts, areaName, ppalView);
        areaBalasts.setLocationRelativeTo(null);
        areaBalasts.setVisible(true);
        
    }
    
    
    /**
     * Show the selection window to upload a picture.
     */
    public void uploadAreaPicture(PpalView ppalView) {
        File selectedImage = new File("");
        String imagesRoute = Constants.AREA_IMAGES;
        String areaName = ppalView.getjComboBox2().getSelectedItem().toString();
        
        //Choose the image.
        JFileChooser chooser = new JFileChooser();
       
        int returnVal = chooser.showOpenDialog(ppalView.getIndex());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedImage = chooser.getSelectedFile();
        }
        
        //"Upload" the image.
        System.out.println("nombre imagen: " + imagesRoute + "/" + areaName + selectedImage.getName().subSequence(selectedImage.getName().length() - 4, selectedImage.getName().length()));
        String uploadedFileName = imagesRoute + "/" + areaName + selectedImage.getName().subSequence(selectedImage.getName().length() - 4, selectedImage.getName().length());
        try {
            File uploaded = Utils.copyFile(selectedImage.getAbsolutePath(), uploadedFileName);
            String path = uploaded.getAbsolutePath();
            path = path.replaceAll("\\\\SmaieeView\\\\..", "");
            path = path.replaceAll("\\\\", "/");
            ImageIcon icon = createImageIcon(path, "");


            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(456, 480, java.awt.Image.SCALE_SMOOTH);
            ppalView.getjLabel56().setIcon(new ImageIcon(newimg));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Show the imge in the view.
    }
    
    public ImageIcon createImageIcon(String path, String description) {
//        java.net.URL imgURL = getClass().getResource(path);
//        
//        
//        if (imgURL != null) {
//            return new ImageIcon(imgURL, description);
//        } else {
//            System.err.println("Couldn't find file: " + path);
//            return null;
//        }
        
        return new ImageIcon(path);
}

    /**
     * Sets spinner value.
     */
    public void setSpinnerValue(PpalView ppalView) {
        int value = ppalView.getjSlider1().getValue();
        ppalView.getjSpinner3().setValue(value);
    }
    
    /**
     * Combo that selects an area.
     */
    public void selectArea(PpalView ppalView){
        ppalView.getThreadManager().stopThread(ThreadManager.RTC_REFRESHING);
        cleanRealTimeView(ppalView);
        if (ppalView.getjComboBox2().getSelectedItem() != null && ppalView.getjComboBox2().getSelectedIndex() != 0) {
            HashMap<String, Integer> areaImages = PropHandler.getAreaImageNames();
            String areaName = ppalView.getjComboBox2().getSelectedItem().toString();
            String imagesRoute = Constants.AREA_IMAGES;

            //Imagen
            if (areaImages.get(areaName) == 0) {
                ppalView.getjButton38().setEnabled(true);
                ppalView.getjLabel56().setIcon(new ImageIcon(getClass().getResource("/images/photo_not_available.jpg")));
            } else {
                //"Upload" the image.
                String uploadedFileName = imagesRoute + "/" + areaName + ".jpg";

                try {
                    File uploaded = new File(uploadedFileName);
                    String path = uploaded.getAbsolutePath();
                    path = path.replaceAll("\\\\SmaieeView\\\\..", "");
                    path = path.replaceAll("\\\\", "/");
                    ImageIcon icon = createImageIcon(path, "");
                    
                    Image img = icon.getImage();
                    Image newimg = img.getScaledInstance(456, 480,  java.awt.Image.SCALE_SMOOTH);  
                    ppalView.getjLabel56().setIcon(new ImageIcon(newimg));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            //Balastos
            ArrayList<Integer> areaBalasts = PropHandler.getAreaBalasts(areaName);
            BalastosControlJmodbus balastoCtrl = new BalastosControlJmodbus();
            balastoCtrl.readBalastos(ppalView);
            showBalastos(areaBalasts, ppalView);
            ppalView.getThreadManager().getRealTime().setSelectedAreaBalasts(areaBalasts);
            ppalView.getThreadManager().startThread(ThreadManager.REAL_TIME);
            
//            startCheckingBalastsLevels(ppalView, areaBalasts);
        } else {
//            startCheckingBalastsLevels(ppalView, new ArrayList<Integer>());
        }
        
        
    }
    
    
    /**
     * Edit the balasts of an area.
     */
    public void areaBalasts(PpalView ppalView){
        if (ppalView.getjComboBox2().getSelectedIndex() != 0) {
            RealTimeControl.selectBalasts(ppalView, ppalView.getBalasts(), PropHandler.getAreaBalasts((String)ppalView.getjComboBox2().getSelectedItem()), (String)ppalView.getjComboBox2().getSelectedItem());
        } else {
            Validacion.showAlertMessage("Seleccione un area primero");
        }
    }
    
    /**
     * Creates a new area.
     */
    public void newArea(PpalView ppalView){
        //Create a new area.
        String nombre = JOptionPane.showInputDialog(ppalView.getjPanel5(), "Ingrese el " +
                "nombre de la nueva área", "Nueva área", JOptionPane.QUESTION_MESSAGE);
        if ((nombre != null) && (!nombre.equals(""))) {
            PropHandler.addArea(nombre);

            //Show the new area
            ppalView.getjComboBox2().addItem(nombre);
            ppalView.setShowAreas(true);
        }
        
    }
    
    
    /**
     * Deletes the selected area
     */
    public void deleteArea(PpalView ppalView) {
        String selectedArea = (String) ppalView.getjComboBox2().getSelectedItem();

        int nombre = JOptionPane.showConfirmDialog(ppalView.getjPanel5(), "Realmente desea "
                + "eliminar el área \"" + selectedArea + "\"?", "Eliminar área", JOptionPane.YES_NO_OPTION);

        PropHandler.deleteArea(selectedArea);

        //Show the new area
        ppalView.getjComboBox2().removeItem(nombre);
        ppalView.setShowAreas(true);

    }
    
    /**
     * Event of the slider.
     */
    public void monitoreoBalasto(PpalView ppalView){
        boolean connectionStatus = true; //DAOJamod.testConnection(ppalView.getIp(), ppalView.getPort());
        new GeneralControl().updateConnectionStatus(connectionStatus, ppalView);
        if (connectionStatus) {

            if (!ppalView.getjLabel54().getText().equals("#")) {
                int balastNumber = Integer.parseInt(ppalView.getjLabel54().getText());
                int level = Integer.parseInt(ppalView.getjSpinner3().getValue().toString());
                int activation = 1;
                String name = ppalView.getjLabel65().getText();

                ppalView.getStatusLabel().setText("Guardando balasto");

                //Saves the balast remotelly
                BalastoDAOJmodbus bDao = new BalastoDAOJmodbus(ppalView.getDao());
                bDao.saveRealTimeLevel(balastNumber, level);
                TiempoRealDAO tDao = new TiempoRealDAO(ppalView.getDao());
                tDao.saveBalast();
                
                HashMap<String, Balasto> balasts = ppalView.getBalasts();
                Balasto modifiedBalast = balasts.get(String.valueOf(balastNumber));
                modifiedBalast.setLevel(level);
                balasts.put(String.valueOf(balastNumber), modifiedBalast);
                ppalView.setBalasts(balasts);

                //Update balast locally.
//        balasts.remove(String.valueOf(balast.getBalastNumber()));
//        balasts.put(String.valueOf(balast.getBalastNumber()), balast);

                showBalastos(ppalView);
            } else {
                Validacion.showAlertMessage("Seleccione un balasto");
            }
        }
    }
    
    
    /**
     * Show the available areas.
     */
    public void showAreas(PpalView ppalView){
        if (ppalView.getShowAreas()) {
            ppalView.getjComboBox2().removeAllItems();
            ppalView.getjComboBox2().setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Seleccione el área"}));
            
            final PpalView ppalView2 = ppalView;
            ppalView.getjComboBox2().addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    ppalView2.selectingAreaEncapsulating(evt);
                }
            });
            
            ArrayList<String> areas = PropHandler.getAddedAreas();
            for (String area : areas) {
                ppalView.getjComboBox2().addItem(area);
            }
            ppalView.setShowAreas(true);
        }
    }
    
    
    public void seleccionarBalasto(PpalView ppalView){
        DefaultTableModel tablaTareas = (DefaultTableModel) ppalView.getjTable1().getModel();
        int filaTareaSeleccionada = ppalView.getjTable1().convertRowIndexToModel(ppalView.getjTable1().getSelectedRow());

        String noBalastoSeleccionado = tablaTareas.getValueAt(filaTareaSeleccionada, 0).toString();
        Balasto balastoSeleccionado = ppalView.getBalasts().get(noBalastoSeleccionado);
        
        ppalView.getjLabel54().setText(String.valueOf(balastoSeleccionado.getBalastNumber()));
        ppalView.getjLabel65().setText(balastoSeleccionado.getName());
        ppalView.getjSpinner3().setValue(balastoSeleccionado.getLevel());
        ppalView.getjSlider1().setValue(balastoSeleccionado.getLevel());
        
    }
    
    public void showBalastos(PpalView ppalView) {
        DefaultTableModel tablaBalastos = (DefaultTableModel) ppalView.getjTable1().getModel();
        
        //Clean balasts table.
        int rowCount = tablaBalastos.getRowCount();
        if (rowCount > 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                tablaBalastos.removeRow(i);
            }
        }
        
        ArrayList<Integer> selected = PropHandler.getAreaBalasts(ppalView.getjComboBox2().getSelectedItem().toString());
        
        //Show balasts
        for (Integer balasto : selected) {
            Balasto readed = ppalView.getBalasts().get(String.valueOf(balasto));
            Object nuevo[] = {readed.getBalastNumber(), readed.getName(), readed.getLevel()};
            tablaBalastos.addRow(nuevo);
        }
    }
    
    public void showBalastos(ArrayList<Integer> balastsNumbers, PpalView ppalView) {
        DefaultTableModel tablaBalastos = (DefaultTableModel) ppalView.getjTable1().getModel();
        
        //Clean balasts table.
        int rowCount = tablaBalastos.getRowCount();
        if (rowCount > 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                tablaBalastos.removeRow(i);
            }
        }
        
        //Show balasts
        HashMap<String, Balasto> balasts = ppalView.getBalasts();
        Set<String> iterador = balasts.keySet();
        for (int balastNumber : balastsNumbers) {
            for (String balasto : iterador) {
                Balasto readed = balasts.get(balasto);
                if (balastNumber == readed.getBalastNumber()) {
                    Object nuevo[] = {readed.getBalastNumber(), readed.getName(), readed.getLevel()};
                    tablaBalastos.addRow(nuevo);
                }
            }
        }
    }
    
    public void cleanRealTimeView(PpalView ppalView) {
        DefaultTableModel balastsTable = (DefaultTableModel) ppalView.getjTable1().getModel();
        
        int rowCount = balastsTable.getRowCount();
        if (rowCount > 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                balastsTable.removeRow(i);
            }
        }
        
        ppalView.getjSpinner3().setValue(0);
        ppalView.getjSlider1().setValue(0);
        ppalView.getjLabel54().setText("#");
        ppalView.getjLabel65().setText("#");
    }
    
    /**
     * Starts a thread that checks balast status.
     */
//    public void checkBalasts(PpalView ppalView){
//        CheckBalastsLevels checkThread = new CheckBalastsLevels(ppalView.getjTable1());
//        checkThread.run();
//    }
    
    /**
     * Stops the thread that checks balast status.
     */
//    public void stopCheckingBalasts(PpalView ppalView) {
//        CheckBalastsLevels checkThread = new CheckBalastsLevels(ppalView.getjTable1());
//        checkThread.run();
//    }
    
     /**
     * Refresh the balasts levels
     */
//    public void refreshBalastsLevels(PpalView ppalView){
//        Thread showBalasts = new CheckBalastsLevels(ppalView.getjTable1());
//        showBalasts.start();
//    }
    
    
    /**
     * Starts monitoring the balasts levels.
     * @param ppalView
     * @param areaBalasts 
     */
//    private void startCheckingBalastsLevels(PpalView ppalView, ArrayList<Integer> areaBalasts){
//        Thread.State state = balastsLevelsThread.getState();
//        if (state.equals(Thread.State.NEW)) { //si no ha arrancado
//            balastsLevelsThread.setPpalView(ppalView);
//            balastsLevelsThread.setSelectedAreaBalasts(areaBalasts);
//            
//            Thread showBalasts = balastsLevelsThread;
//            showBalasts.start();
//            
//        } else if (state.equals(Thread.State.RUNNABLE)){ //Si ya arranco se para se cambia de balastos a mirar y se continua
//            balastsLevelsThread.interrupt();
//            balastsLevelsThread = new CheckBalastsLevels();
//            balastsLevelsThread.setPpalView(ppalView);
//            balastsLevelsThread.setSelectedAreaBalasts(areaBalasts);
//            
//            Thread showBalasts = balastsLevelsThread;
//            showBalasts.start();
//        } else if (ppalView.getjComboBox2().getSelectedIndex() == 0){ //Si se selecciona la primera opcion.
//            if (!state.equals(Thread.State.TERMINATED)) {
//                balastsLevelsThread.interrupt();
//            }
//        }
//    }
//    
//    public void stopRealTimeChecking() {
//        Thread.State state = balastsLevelsThread.getState();
//        if (!state.equals(Thread.State.TERMINATED)) {
//            balastsLevelsThread.interrupt();
//        }
//    }
    
}
