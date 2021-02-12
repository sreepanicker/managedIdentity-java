/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagedidentity.app;

import com.usermanagedidentity.framework.Utility;
import com.usermanagedidentity.infrastructure.AppConfigService;
import com.usermanagedidentity.infrastructure.SQLDBService;
import com.usermanagedidentity.infrastructure.TokenService;
import com.usermanagedidentity.infrastructure.VaultService;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
/**
 *
 * @author sreep
 */
public class UserManagedIdentity {
    
    private static final Logger logger = Logger.getLogger(UserManagedIdentity.class.getName());
    public static void main(String args[]){
        
        
        // Get a token from Azure tenant for the Managed Identity
        //Create an Instance of the Token Servce 
        try{
            logger.log(Level.INFO,"Starting the Application");
            //Initializing token service
            TokenService  tokenService = new TokenService();            
            tokenService.setURL(new URL(Utility.getInstance().getValue("vaultTokenUrl")));
            
            //Initializing vault Service 
            VaultService vaultService = new VaultService();
            vaultService.setToken(tokenService.getToken());
            
            //Inilaizing the App Config Service 
            AppConfigService appConfig = new AppConfigService();
            appConfig.establishConnection();
            
            //Inializing the DB service 
            SQLDBService sqlDbService = new SQLDBService();
            sqlDbService.setDBName(vaultService.getValue("dbString", appConfig.getValue()));
            tokenService.setURL(new URL(Utility.getInstance().getValue("dbTokenUrl")));
            sqlDbService.setToken(tokenService.getToken());
            sqlDbService.displayData();
            logger.log(Level.INFO,"Ending  the Application");
        }catch(Exception e){
            logger.log(Level.SEVERE,e.toString());
        }                
    }
    
    
}

