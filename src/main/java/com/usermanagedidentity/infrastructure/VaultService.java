/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagedidentity.infrastructure;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author sreep
 */
public class VaultService {

    
    private static final Logger logger = Logger.getLogger(VaultService.class.getName());
    private String accessToken;

    public void setToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /*
    public String getValue(String key,String vaultURI){
        String keyValue = null;
        URL url = getURL(vaultURI,key);
        if (url != null){
        try{    
            HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, url);
            HttpHeaders httpHeader = new HttpHeaders();
            String bearerToekn = "Bearer " +accessToken;
            httpHeader.put("Authorization", bearerToekn);
            httpRequest.setHeaders(httpHeader);HttpResponse httpResponse = HttpClient.createDefault().send(httpRequest).block();
            String reponseString = httpResponse.getBodyAsString().block();
        
            JsonFactory factory = new JsonFactory();
            
            JsonParser  parser = factory.createParser(reponseString);
            
            
            while(!parser.isClosed()){
                JsonToken jsonToken = parser.nextToken();
                 if(JsonToken.FIELD_NAME.equals(jsonToken)){
                     String fieldName = parser.getCurrentName();
                     jsonToken = parser.nextToken();
                     if("dbKey".equals(fieldName)){
                         keyValue = parser.getValueAsString();
                     }
                 }
            }
            
            
        }catch(Exception e){
            System.out. println(" error in Getting value");
            System.out.println(e);
            
        }                     
        }
        return keyValue;
    }*/
    public String getValue(String key, String vaultURI) throws Exception {
        String keyValue = null;
        URL url = getURL(vaultURI, key);
        if (url != null) {
            logger.log(Level.INFO,"Starting the Connnection .....");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String bearerToekn = "Bearer " + accessToken;
            con.setRequestProperty("Authorization", bearerToekn);
            
            logger.log(Level.INFO,"Connection String " + con.toString());
            
            if (con.getResponseCode() != 200) {
                throw new Exception("Error calling key Valute endpoint.");
            }
            
            logger.log(Level.INFO,"Connection Successed .....");
            try {

                InputStream inputStream = con.getInputStream();
                logger.log(Level.INFO," Response String :" + inputStream);
                JsonFactory factory = new JsonFactory();

                JsonParser parser = factory.createParser(inputStream);

                while (!parser.isClosed()) {
                    JsonToken jsonToken = parser.nextToken();
                    if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                        String fieldName = parser.getCurrentName();
                        jsonToken = parser.nextToken();
                        if ("value".equals(fieldName)) {
                            keyValue = parser.getValueAsString();
                        }
                    }
                }

            } catch (Exception e) {               
                logger.log(Level.SEVERE, e.toString());

            }
        }
        logger.log(Level.INFO, "Secrts from the the Vault "+ keyValue);
        return keyValue;
    }
   
    //Accessing the data using Secrets
    /*
    public String getValue(String key, String vaultURI) throws Exception {
        String keyValue = null;
        //URL url = getURL(vaultURI, key);
        System.out.printf("Starting a Connection to Key vault.... \n");
        
        DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder().build();

    // Azure SDK client builders accept the credential as a parameter
        SecretClient client = new SecretClientBuilder()
        .vaultUrl(vaultURI)
        .credential(defaultCredential)
        .buildClient();
       
        System.out.printf("Estalished connection to Key vault.... \n");
        KeyVaultSecret secret = client.getSecret(key);
        
       // defaultCredential.       
       
        System.out.printf("Secrts from the the Vault: %s \n ", secret.getValue());
        return keyValue = secret.getValue();
    }
    */
    
    
    private URL getURL(String vaultURI, String key) {
        URL url = null;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(vaultURI).append("/secrets/").append(key).append("?api-version=2016-10-01");
        
        //https://appuseridentity.vault.azure.net/secrets/dbString/a660f83289f847a2aab39170246e2ab4
        //stringBuffer.append(vaultURI).append("/secrets").append("?api-version=7.1");
        logger.log(Level.INFO,"URL String" + stringBuffer.toString());

        try {
            url = new URL(stringBuffer.toString());
        } catch (Exception e) {
   
            logger.log(Level.SEVERE,e.toString());
        }
        return url;
    }

}
