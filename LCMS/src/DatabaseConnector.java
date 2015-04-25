
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Greg
 */
public class DatabaseConnector {
    private final String HOST_NAME = "108.15.105.2";
    private final String PORT_NUM = "3306";
    private final String DATABASE_NAME = "cosc412";
    private final String USER_NAME = "softeng";
    private final String PASSWORD = "password";
    private final String DBMS = "mysql";
    private Connection connection;
    
    public Connection getConnection() throws SQLException {
       
        connection = null;
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", this.USER_NAME);
        connectionProperties.put("password", this.PASSWORD);
        
        if(this.DBMS.equals("mysql")){
            connection = DriverManager.getConnection(
                "jdbc:" + this.DBMS + "://" + this.HOST_NAME + ":" +
                this.PORT_NUM + "/" + this.DATABASE_NAME, connectionProperties);
        }        
        
        System.out.println("Connection Successfull");
        return connection;
    }
    
    
    public ArrayList<String> getUserLoginInformation(){
        Statement query = null;
        ArrayList<String> toReturn = new ArrayList<>();
        
        try{
            query = connection.createStatement();
            String sql = "SELECT username, password FROM Users";
            ResultSet results = query.executeQuery(sql);
           
            while(results.next()){
                toReturn.add(results.getString("username"));
                toReturn.add(results.getString("password"));                
            }
            results.close();
            query.close();
                        
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        
       return toReturn;
    }
    
    
}
