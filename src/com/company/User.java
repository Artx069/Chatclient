package com.company;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    private String username;
    private int userId;

    public void login(Connection c) throws SQLException, NoSuchAlgorithmException {
        System.out.println("Please type in your login name: ");
        Scanner scan = new Scanner(System.in);

        String username = scan.nextLine();

        ResultSet findUsername = SqlCommands.findUsername(username,c);

        if(!findUsername.next()){
            System.out.println("This account does not exist. Do you want to create it? Y/N/EXIT");
            String reply = scan.nextLine().toUpperCase().trim();
            while(!reply.matches("[YN]") && !reply.matches("EXIT") ){
                reply = scan.nextLine().toUpperCase();
            }
            if(reply.equals("N")){
                login(c);
            }else if(reply.equals("EXIT")){
                System.exit(0);
            }
            else if(reply.equals("Y")){
                System.out.println("Type in your password: ");
                String password = scan.nextLine();
                String hashedPassword = PasswordGenerator.hashPassword256(password);

                SqlCommands.createAccount(username, hashedPassword, c);

                findUsername = SqlCommands.findUsername(username,c);
                findUsername.next();
                System.out.println("Registration was successfull");

                this.username = username;
                this.userId = findUsername.getInt(1);

            }
        }else{
            System.out.println(username + " exists. Enter password to log in");
            String enteredPassword = scan.nextLine();
            String hashedEnteredPassword = PasswordGenerator.hashPassword256(enteredPassword);
            String hashedPasswordDB = findUsername.getString(3);
            if(hashedEnteredPassword.equals(hashedPasswordDB)){
                System.out.println("Login was successful");
            }else{
                System.out.println("Wrong Password entered!");
                login(c);
            }

            this.username = username;
            this.userId = findUsername.getInt(1);
        }
    }
}
