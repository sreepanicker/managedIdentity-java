/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagedidentity.app;

import com.usermanagedidentity.framework.Utility;
import com.usermanagedidentity.infrastructure.SQLDBService;
import com.usermanagedidentity.infrastructure.TokenService;
import com.usermanagedidentity.infrastructure.VaultService;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author sreep
 */
public class UserManagedIdentity {
    
    private static final Logger logger = LogManager.getLogger(UserManagedIdentity.class);
    public static void main(String args[]){
        
        
        // Get a token from Azure tenant for the Managed Identity
        //Create an Instance of the Token Servce 
        try{
            logger.trace("Starting the Application");
            TokenService  tokenService = new TokenService();            
            tokenService.setURL(new URL(Utility.getInstance().getValue("vaultTokenUrl")));
            VaultService vaultService = new VaultService();
            vaultService.setToken(tokenService.getToken());
            SQLDBService sqlDbService = new SQLDBService();
            sqlDbService.setDBName(vaultService.getValue("dbString", "https://appuseridentity.vault.azure.net/"));
            tokenService.setURL(new URL(Utility.getInstance().getValue("dbTokenUrl")));
            sqlDbService.setToken(tokenService.getToken());
            sqlDbService.displayData();
            logger.trace("Ending  the Application");
        }catch(Exception e){
            logger.error(e);
        }                
    }
    
    
}
