package org.example.jdbc.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertExample {
    public static void main(String[] args) {
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store", "root", "art_foot1010");
            String insert = "INSERT INTO clients (name, surname, phone, email)\n" +
                    "VALUES ('Mark', 'Markovich', '+38099841293', 'mark12@gmail.com');";
            Statement statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(insert);
            if(rowsInserted > 0){
                System.out.println("A new client was inserted successfully.");
            }
            else{
                System.out.println("A client not found.");
            }
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        finally{
            try { connection.close(); }
            catch (SQLException e){ e.printStackTrace(); }
        }
    }
}
