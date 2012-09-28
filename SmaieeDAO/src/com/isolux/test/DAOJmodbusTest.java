package com.isolux.test;

import com.isolux.dao.modbus.DAOJmodbus;

public class DAOJmodbusTest {
    
    public static void main(String args[]){
        DAOJmodbus dao = new DAOJmodbus();
        int pos = 5000;
        int[] valuesRTC = {1,30,5,113,6,47,0};
        int[] valuesBalasto = {12,30,1,12595,12595,24947,24940,66,12,0,100,4,12,12,12,12};
        
//        //set
//        int[] conf = {1};
//        dao.setRegValue(0, conf);
//        System.out.println(dao.setRegValue(pos, valuesBalasto));
//        int[] conf2 = {0};
//        dao.setRegValue(0, conf2);
        
        //read
        int[] result = dao.getRegValue(pos, 100);
        for (int i : result) {
            System.out.println(pos + ": " + i);
            pos++;
        }
    }
    
}
