package com.company;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Thread thread = new Thread(new ChatContent());
        thread.start();

        Connection c = SqlCon.connector();

        try {
            User user = new User();
            user.login(c);

            if(user.getUsername() != null &&  user.getUserId() != 0) {
                ResultSet firstLogin = SqlCommands.firstLogin(c);
                System.out.println("Die letzten 5 Nachrichten:");
                while (firstLogin.next()) {
                    System.out.println(firstLogin.getString(1)  + " (" +firstLogin.getString(2).substring(11) + ") " + ": " + firstLogin.getString(3));
                }

                ChatContent.wrtiteNewMessage(user.getUsername(), user.getUserId(), c);
            }
            System.exit(0);

        }catch (Exception e){
            System.out.println(e);
        }

    }



}
