package com.example.testcase2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {

    Connection conn = null;
    public static Connection Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/criminallist","root","SNow2796");
            return conn;
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE,null, ex);
        }
        return null;

    }
}
