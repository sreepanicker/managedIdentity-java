/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagedidentity.infrastructure;

import com.azure.data.appconfiguration.ConfigurationClient;
import com.azure.data.appconfiguration.ConfigurationClientBuilder;
import com.azure.data.appconfiguration.models.ConfigurationSetting;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sreep
 */
public class AppConfigService {
    
    private static final Logger logger = Logger.getLogger(AppConfigService.class.getName());
    private DefaultAzureCredential credentail;
    private ConfigurationClient configClient ;
    
    public  AppConfigService(){
        //credentail = new DefaultAzureCredentialBuilder().build();
    }
    
    public void establishConnection(){
        
        credentail = new DefaultAzureCredentialBuilder().build();
        logger.log(Level.INFO, "Started the connection , credential :" + credentail);
        configClient = new ConfigurationClientBuilder().
                credential(credentail).
                endpoint("xx").
                buildClient();
         logger.log(Level.INFO, "Connection success");
    }
    public String getValue(){
        logger.log(Level.INFO, "Getting the value from app config");
        return configClient.getConfigurationSetting("vaultUrl","").getValue();
     
    }
}
