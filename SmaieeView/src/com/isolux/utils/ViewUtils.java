/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isolux.utils;

import com.isolux.dao.properties.PropHandler;
import com.isolux.dao.view.Status;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.ListModel;

/**
 *
 * @author EAFIT
 */
public class ViewUtils {

    static Status statusWindow = null;

    /**
     * Add selected balasts to the group.
     */
    public static void addSelected(JList available, JList selectedList) {
        DefaultListModel defGroupModel = new DefaultListModel();
        DefaultListModel defAvailModel = new DefaultListModel();

        List selected = available.getSelectedValuesList();

        int groupSize = selectedList.getModel().getSize();
        int availSize = available.getModel().getSize();

        //Available
        ListModel availModel = available.getModel();
        for (int i = 0; i < availSize; i++) {
            defAvailModel.addElement(availModel.getElementAt(i));
        }

        //Add selected items to the group.
        for (Object object : selected) {
            defAvailModel.removeElement(object);
        }

        //Group
        ListModel groupModel = selectedList.getModel();
        for (int i = 0; i < groupSize; i++) {
            defGroupModel.addElement(groupModel.getElementAt(i));
        }

        //Add selected items to the group.
        for (Object object : selected) {
            defGroupModel.addElement(object);
        }

        //Set models
        available.setModel(defAvailModel);
        selectedList.setModel(defGroupModel);


    }

    /**
     * Add all balasts to the group.
     */
    public static void addAll(JList available, JList selectedList) {
        DefaultListModel defGroupModel = new DefaultListModel();
        DefaultListModel defAvailModel = new DefaultListModel();

        int groupSize = selectedList.getModel().getSize();
        int availSize = available.getModel().getSize();

        List selected = new ArrayList();
        for (int i = 0; i < availSize; i++) {
            selected.add(available.getModel().getElementAt(i));
        }

        //Available
        ListModel availModel = available.getModel();
        for (int i = 0; i < availSize; i++) {
            defAvailModel.addElement(availModel.getElementAt(i));
        }

        //Add selected items to the group.
        for (Object object : selected) {
            defAvailModel.removeElement(object);
        }

        //Group
        ListModel groupModel = selectedList.getModel();
        for (int i = 0; i < groupSize; i++) {
            defGroupModel.addElement(groupModel.getElementAt(i));
        }

        //Add selected items to the group.
        for (Object object : selected) {
            defGroupModel.addElement(object);
        }

        //Set models
        available.setModel(defAvailModel);
        selectedList.setModel(defGroupModel);
    }

    /**
     * Remove a balast from the group.
     */
    public static void remSelected(JList available, JList selectedList) {
        DefaultListModel defGroupModel = new DefaultListModel();
        DefaultListModel defAvailModel = new DefaultListModel();

        List selected = selectedList.getSelectedValuesList();

        int groupSize = selectedList.getModel().getSize();
        int availSize = available.getModel().getSize();

        //Available
        ListModel availModel = available.getModel();
        for (int i = 0; i < availSize; i++) {
            defAvailModel.addElement(availModel.getElementAt(i));
        }

        //Add selected items to the group.
        for (Object object : selected) {
            defAvailModel.addElement(object);
        }

        //Group
        ListModel groupModel = selectedList.getModel();
        for (int i = 0; i < groupSize; i++) {
            defGroupModel.addElement(groupModel.getElementAt(i));
        }

        //Add selected items to the group.
        for (Object object : selected) {
            defGroupModel.removeElement(object);
        }

        //Set models
        available.setModel(defAvailModel);
        selectedList.setModel(defGroupModel);
    }

    /**
     * Remove all balasts from the group.
     */
    public static void remAll(JList available, JList selectedList) {
        DefaultListModel defGroupModel = new DefaultListModel();
        DefaultListModel defAvailModel = new DefaultListModel();

        int groupSize = selectedList.getModel().getSize();
        int availSize = available.getModel().getSize();

        List selected = new ArrayList();
        for (int i = 0; i < groupSize; i++) {
            selected.add(selectedList.getModel().getElementAt(i));
        }


        //Available
        ListModel availModel = available.getModel();
        for (int i = 0; i < availSize; i++) {
            defAvailModel.addElement(availModel.getElementAt(i));
        }

        //Add selected items to the group.
        for (Object object : selected) {
            defAvailModel.addElement(object);
        }

        //Group
        ListModel groupModel = selectedList.getModel();
        for (int i = 0; i < groupSize; i++) {
            defGroupModel.addElement(groupModel.getElementAt(i));
        }

        //Add selected items to the group.
        for (Object object : selected) {
            defGroupModel.removeElement(object);
        }

        //Set models
        available.setModel(defAvailModel);
        selectedList.setModel(defGroupModel);
    }

    /**
     * Disable the non-selected checks
     */
//    public static int selectCheks(JCheckBox balasts, JCheckBox groups, JCheckBox scenes){
//        int type = 0;
//        //balasts
//        if (balasts.isSelected()) {
//            type = Integer.parseInt(PropHandler.getProperty("in.out.type.balast"));
//            groups.setEnabled(false);
//            scenes.setEnabled(false);
//            return type;
//        } else {
//            groups.setEnabled(true);
//            scenes.setEnabled(true);
//        }
//        
//        //groups
//        if (groups.isSelected()) {
//            type = Integer.parseInt(PropHandler.getProperty("in.out.type.group"));
//            balasts.setEnabled(false);
//            scenes.setEnabled(false);
//            return type;
//        } else {
//            balasts.setEnabled(true);
//            scenes.setEnabled(true);
//        }
//        
//        //scene
//        if (scenes.isSelected()) {
//            type = Integer.parseInt(PropHandler.getProperty("in.out.type.scene"));
//            groups.setEnabled(false);
//            balasts.setEnabled(false);
//            return type;
//        } else {
//            groups.setEnabled(true);
//            balasts.setEnabled(true);
//        }
//        
//        return type;
//    }
    public static int selectCheks(JCheckBox balasts, JCheckBox groups, JCheckBox scenes) {
        int type = 0;
        //balasts
        if (balasts.isSelected()) {
            type = Integer.parseInt(PropHandler.getProperty("in.out.type.balast"));
            groups.setEnabled(false);
            scenes.setEnabled(false);
            return type;
        } else if (groups.isSelected()) {
            type = Integer.parseInt(PropHandler.getProperty("in.out.type.group"));
            balasts.setEnabled(false);
            scenes.setEnabled(false);
            return type;
        } else if (scenes.isSelected()) {
            type = Integer.parseInt(PropHandler.getProperty("in.out.type.scene"));
            groups.setEnabled(false);
            balasts.setEnabled(false);
            return type;
        } else {
            groups.setEnabled(true);
            balasts.setEnabled(true);
            scenes.setEnabled(true);
        }

        return type;
    }

    /**
     * Disable the non-selected checks
     */
    public static int selectCheksSensor(JCheckBox balasts, JCheckBox groups) {
        int type = 0;
        //balasts
        if (balasts.isSelected()) {
            type = Integer.parseInt(PropHandler.getProperty("in.out.type.balast"));
            groups.setEnabled(false);
            return type;
        } else {
            groups.setEnabled(true);
        }

        //groups
        if (groups.isSelected()) {
            type = Integer.parseInt(PropHandler.getProperty("in.out.type.group"));
            balasts.setEnabled(false);
            return type;
        } else {
            balasts.setEnabled(true);
        }
        return type;
    }

    /**
     * Gets an int property
     *
     * @param prop
     * @return
     */
    public static int getIntProperty(String prop) {
        return Integer.parseInt(PropHandler.getProperty(prop));
    }

    /**
     * Gets an string property
     *
     * @param prop
     * @return
     */
    public static String getStringProperty(String prop) {
        return PropHandler.getProperty(prop);
    }

    /**
     * Sets the in out type.
     *
     * @param balasts
     * @param groups
     * @param scenes
     * @param type
     */
    public static void setInOutType(JCheckBox balasts, JCheckBox groups, JCheckBox scenes, int type) {
        int balast = Integer.parseInt(PropHandler.getProperty("in.out.type.balast"));
        int group = Integer.parseInt(PropHandler.getProperty("in.out.type.group"));
        int scene = Integer.parseInt(PropHandler.getProperty("in.out.type.scene"));

        //balasts
        if (type == balast) {
            balasts.setEnabled(true);
            balasts.setSelected(true);
            groups.setEnabled(false);
            scenes.setEnabled(false);
            return;
        }

        //groups
        if (type == group) {
            balasts.setEnabled(false);
            groups.setEnabled(true);
            groups.setSelected(true);
            scenes.setEnabled(false);
            return;
        }

        //scene
        if (type == scene) {
            groups.setEnabled(false);
            balasts.setEnabled(false);
            scenes.setEnabled(true);
            scenes.setSelected(true);
            return;
        }
    }

    /**
     * Sets the in out type for sensors.
     *
     * @param balasts
     * @param groups
     * @param scenes
     * @param type
     */
    public static void setInOutTypeSensors(JCheckBox balasts, JCheckBox groups, int type) {
        int balast = Integer.parseInt(PropHandler.getProperty("in.out.type.balast"));
        int group = Integer.parseInt(PropHandler.getProperty("in.out.type.group"));

        //balasts
        if (type == balast) {
            balasts.setEnabled(true);
            balasts.setSelected(true);
            groups.setEnabled(false);
            return;
        }

        //groups
        if (type == group) {
            balasts.setEnabled(false);
            groups.setEnabled(true);
            groups.setSelected(true);
            return;
        }
    }

    public static Status getStatusView() {
        statusWindow = new Status();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                statusWindow.setLocationRelativeTo(null);
                statusWindow.setVisible(true);
            }
        });
        return statusWindow;
    }
}
