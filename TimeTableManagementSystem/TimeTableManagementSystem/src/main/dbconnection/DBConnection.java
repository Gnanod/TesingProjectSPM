package main.dbconnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private Connection conn;
    private static DBConnection dbConnection;

    private DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        String jdbcurl="jdbc:mysql://"+"spmproject.calqnpocpi57.us-east-1.rds.amazonaws.com:"+3306+"/timetableManagementSystem";
        conn=(Connection) DriverManager.getConnection(jdbcurl,"root","mysql123");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if(dbConnection==null){
            dbConnection=new DBConnection();
        }
        return dbConnection;
    }
    public Connection getConnection(){
        return this.conn;
    }

}
