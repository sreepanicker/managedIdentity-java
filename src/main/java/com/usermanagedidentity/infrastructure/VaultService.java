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

/**
 *
 * @author sreep
 */
public class VaultService {

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
            System.out.println("Starting the Connnection .....");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String bearerToekn = "Bearer " + accessToken;
            con.setRequestProperty("Authorization", bearerToekn);
            
            System.out.printf("Connection String %s\n", con.toString());
            
            if (con.getResponseCode() != 200) {
                throw new Exception("Error calling key Valute endpoint.");
            }
            
            System.out.println("Connection Successed .....");
            try {

                InputStream inputStream = con.getInputStream();
                System.out.println(" Response String :" + inputStream);
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
                System.out.println(" error in Getting value");
                System.out.println(e);

            }
        }
        System.out.printf("Secrts from the the Vault %s \n ", keyValue);
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
        System.out.printf("URL String %s \n", stringBuffer.toString());

        try {
            url = new URL(stringBuffer.toString());
        } catch (Exception e) {
            System.out.println(" URL Exception ");
            System.out.println(e);
        }
        return url;
    }

}
