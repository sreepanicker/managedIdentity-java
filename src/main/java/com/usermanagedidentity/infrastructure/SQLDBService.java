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

/**
 *
 * @author sreep
 */
public class SQLDBService {
    
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
        System.out.printf("Accesss token: %s  , dbName: %s \n",accessToken, dbName);
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName(dbName);
        ds.setDatabaseName("EJ");
        ds.setAccessToken(accessToken);
        
        try(Connection connection = ds.getConnection(); Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ID, FirstName ,MiddelInitial,LastName FROM Person ")){
            
            while(rs.next()){
                System.out.printf(" First Name : %s ,  Initial : %s  , LastName %s ", rs.getString("FirstName"),rs.getString("MiddelInitial"),rs.getString("LastName"));
            }
            
        }
    }
    
}
