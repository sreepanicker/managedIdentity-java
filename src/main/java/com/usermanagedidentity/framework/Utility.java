/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagedidentity.framework;

import java.util.Properties;

/**
 *
 * @author sreep
 */
public class Utility {

    private static Properties prop = null;
    private static Utility utility = null;
    private Utility() {
        if (prop == null){              
            try {
                prop = new Properties();
                prop.load(getClass().getResourceAsStream("/UserManagedIdentity.properties"));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public static Utility getInstance(){
       if (utility == null){
           utility = new Utility();
       }
       return utility;
    }
    
    public static String getValue (String key){
        return prop.getProperty(key);
    }
}
