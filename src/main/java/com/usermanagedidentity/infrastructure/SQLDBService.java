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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author sreep
 */
public class SQLDBService {
    
    private static final Logger logger = LogManager.getLogger(SQLDBService.class);
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
        logger.printf(Level.TRACE,"Accesss token: %s  , dbName: %s \n",accessToken, dbName);
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName(dbName);
        ds.setDatabaseName("EJ");
        ds.setAccessToken(accessToken);
        
        try(Connection connection = ds.getConnection(); Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ID, FirstName ,MiddelInitial,LastName FROM Person ")){
            
            while(rs.next()){
                logger.printf(Level.TRACE," First Name : %s ,  Initial : %s  , LastName %s ", rs.getString("FirstName"),rs.getString("MiddelInitial"),rs.getString("LastName"));
            }
            
        }
    }
    
}
