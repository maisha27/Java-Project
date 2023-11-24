package com.example.testcase4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JudgeConnection {

    Connection conn = null;
    public static Connection Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/judges","root","SNow2796");
            return conn;
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(JudgeConnection.class.getName()).log(Level.SEVERE,null, ex);
        }
        return null;

    }
}
