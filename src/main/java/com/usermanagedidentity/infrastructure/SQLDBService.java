/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagedidentity.infrastructure;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author sreep
 */
public class SQLDBService {
    
    private static final Logger logger = Logger.getLogger(SQLDBService.class.getName());
    //Access Token for DB
    private String accessToken = null;
    private String dbName = null;
    
    public void setToken(String accessToken){
        this.accessToken = accessToken;
    }
    public void setDBName(String dbName){
        this.dbName = dbName;
    }
    
    public void displayData() throws Exception{
        logger.log(Level.INFO,"Accesss token: " +accessToken + " dbName: "+ dbName);
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName(dbName);
        ds.setDatabaseName("EJ");
        ds.setAccessToken(accessToken);
        
        try(Connection connection = ds.getConnection(); Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ID, FirstName ,MiddelInitial,LastName FROM Person ")){
            
            while(rs.next()){
                logger.log(Level.INFO," First Name : " + rs.getString("FirstName") +" , Initial :" + rs.getString("MiddelInitial") +", LastName : " + rs.getString("LastName"));
            }
            
        }
    }
    
}
