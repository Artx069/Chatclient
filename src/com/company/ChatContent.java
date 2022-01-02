package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ChatContent implements Runnable {
    int counter = 0;

    int currentCount = SqlCommands.countMessages(SqlCon.connector());



    public ChatContent() throws SQLException {
    }

    @Override
    public void run() {
        try {
                Connection c = SqlCon.connector();
                refreshChat(c);
                c.close();


        } catch (SQLException | InterruptedException throwables) {
            throwables.printStackTrace();
        }

    }

    public void refreshChat(Connection c) throws SQLException, InterruptedException {


        Thread.sleep(500);


        if (currentCount == SqlCommands.countMessages(c)) {
        counter++;
        refreshChat(c);
        }else {

            ResultSet rs = SqlCommands.newestMessage(c);


            rs.next() ;

            System.out.println(rs.getString(1)  + " (" +rs.getString(2).substring(11) + ") " + ": " + rs.getString(3));
            currentCount++;

            refreshChat(c);

            }
        }

        public static void wrtiteNewMessage(String username, int userId, Connection c) throws SQLException {
            Scanner scan = new Scanner(System.in);
            String nextLine = scan.nextLine();
            while(!nextLine.equals("/logout")){
                SqlCommands.saveNewMessage(username, userId, nextLine, c);
                nextLine = scan.nextLine();
            }
            System.exit(0);
        }

}
