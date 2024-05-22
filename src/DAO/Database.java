/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class Database {
    public static Connection KetNoiCSDL(){
        Connection conn = null;
        try {
            // 1. Load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver loaded...");

            // 2. Database URL, username, and password  
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLDSV";
            String user = "sa";
            String password = "123";

            // 3. Open connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database...");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return conn;
    }
    
    public static void main(String[] args){
        Connection cnn = KetNoiCSDL();
        if(cnn != null){
            System.out.println("Thành công");
        }else{
            System.out.println("Thất bại");
        }
    }
}
