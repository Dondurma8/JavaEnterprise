package org.example.jdbc.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateExample {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store", "root", "art_foot1010");
            String update = "UPDATE clients AS c\n" +
                    "SET c.phone = '+38067852196' WHERE c.id = 21;";
            Statement statement = connection.createStatement();
            int updated = statement.executeUpdate(update);
            if(updated > 0){
                System.out.println("A client was updated.");
            }
            else{
                System.out.println("A client not found.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{ connection.close(); }
            catch (SQLException e){ e.printStackTrace(); }
        }
    }
}
