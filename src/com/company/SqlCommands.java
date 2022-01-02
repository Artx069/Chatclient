package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlCommands {





    public static void saveNewMessage(String username, int userId, String message, Connection c) throws SQLException {
        Statement st = c.createStatement();
        st.execute(
                "INSERT INTO `messages`(`message_sender_id`, `message_content`, `timestamp`) VALUES ('" + userId +"','" + message +"',CURRENT_TIMESTAMP)"
        );
    }

    public static void createAccount(String username, String hashedPassword, Connection c) throws SQLException {
        Statement st = c.createStatement();
        st.execute("INSERT INTO `user_tbl`(`user_Id`, `username`, `password`) VALUES ("+ (SqlCommands.countUserAccounts(c) + 1) +",'" + username + "','" + hashedPassword + "')");
    }

    public static ResultSet findUsername(String username, Connection c) throws SQLException {
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * FROM `user_tbl` WHERE username = \"" + username + "\";"
        );
        return rs;
    }

    public static int countMessages(Connection c) throws SQLException {
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(
                " select \n" +
                        " count(message_id)\n" +
                        "from messages\n" +
                        ";"
        );

        rs.next();
        return rs.getInt(1);
    }

    public static int countUserAccounts(Connection c) throws SQLException {
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(
                " select \n" +
                        " count(user_Id)\n" +
                        "from user_tbl\n" +
                        ";"
        );

        rs.next();
        return rs.getInt(1);
    }

    public static ResultSet newestMessage(Connection c) throws SQLException {
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT user_tbl.username, timestamp, message_content FROM `messages` INNER JOIN user_tbl on message_sender_id = user_tbl.user_id order by timestamp desc limit 1"
        );

        return rs;

    }

    public static ResultSet firstLogin(Connection c) throws SQLException {
        Statement st = c.createStatement();
        ResultSet firstLogin = st.executeQuery(
                "With cte as (SELECT user_tbl.username, timestamp, message_content FROM `messages` INNER JOIN user_tbl on message_sender_id = user_tbl.user_id order by timestamp desc limit 5 ) select * from cte order by timestamp asc;"
        );
        return firstLogin;
    }






}
