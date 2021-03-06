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
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 *
 * @author sreep
 */
public class TokenService {
    private static final Logger logger = Logger.getLogger(TokenService.class.getName());
    // URL String to Token service 
    
    private URL url = null;
    
    //Set the token URL 
    public void setURL(URL url){
        this.url = url;
    }
    //Access the token based on the URL 
    /*
    public String getToken(){
        String accessToken = null;
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, url);
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.put("Metadata", "true");
        httpRequest.setHeaders(httpHeader);
        System.out.println(" Before request");
       // HttpResponse httpResponse = HttpClient.createDefault().send(httpRequest);
        HttpClient httpClient = new NettyAsyncHttpClientBuilder().build();
        HttpResponse httpResponse = httpClient.send(httpRequest).block();
        String reponseString = httpResponse.getBodyAsString().block();
        System.out.println(" Response String :" + reponseString);
        JsonFactory factory = new JsonFactory();
        try{
            JsonParser  parser = factory.createParser(reponseString);
            
            
            while(!parser.isClosed()){
                JsonToken jsonToken = parser.nextToken();
                 if(JsonToken.FIELD_NAME.equals(jsonToken)){
                     String fieldName = parser.getCurrentName();
                     jsonToken = parser.nextToken();
                     if("access_token".equals(fieldName)){
                         accessToken = parser.getValueAsString();
                     }
                 }
            }
            
            
        }catch(Exception e){
            System.out.println("Token Access issue");
            System.out.println(e);
        }
        
        return accessToken;
    }*/
    public String getToken()throws Exception{
        String accessToken = null;
        logger.log(Level.INFO,"Getting the token, url  %" + url.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Metadata", "true");
        
        if (con.getResponseCode()!=200) {
            throw new Exception("Error calling managed identity token endpoint.");
        }
        InputStream inputStream = con.getInputStream();
        logger.log(Level.INFO," Response String :" + inputStream);
        JsonFactory factory = new JsonFactory();
        try{
            JsonParser  parser = factory.createParser(inputStream);
            
            
            while(!parser.isClosed()){
                JsonToken jsonToken = parser.nextToken();
                 if(JsonToken.FIELD_NAME.equals(jsonToken)){
                     String fieldName = parser.getCurrentName();
                     jsonToken = parser.nextToken();
                     if("access_token".equals(fieldName)){
                         accessToken = parser.getValueAsString();
                     }
                 }
            }
            
            
        }catch(Exception e){
           logger.log(Level.SEVERE,e.toString());
        }
        logger.log(Level.SEVERE,"Access Toekn  " + accessToken);
        return accessToken;
    }
}
