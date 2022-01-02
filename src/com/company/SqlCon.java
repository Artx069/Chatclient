package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlCon {

    public static Connection connector() throws SQLException {

        Connection c = DriverManager.getConnection(
                "jdbc:mysql://75.119.155.13/chatclient",
                DbLoginInfo.LOGIN,
                DbLoginInfo.PSSWD
        );

            return c;

        }



}
