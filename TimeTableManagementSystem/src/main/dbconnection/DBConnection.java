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


        File file=new File(System.getProperty("user.dir")+"/src/dbSettings.properties");

        FileReader fileReader=new FileReader(file);
        Properties properties=new Properties();
        properties.load(fileReader);
        String ip=properties.getProperty("ip");
        String port=properties.getProperty("port");
        String db=properties.getProperty("database");
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String jdbcurl="jdbc:mysql://"+ip+":"+port+"/"+db;
        conn=(Connection) DriverManager.getConnection(jdbcurl,user,password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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