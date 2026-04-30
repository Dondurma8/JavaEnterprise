package org.example.jdbc.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteExample {
    public static void main(String[] args) {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store", "root", "art_foot1010");
            String delete = "DELETE FROM store.clients WHERE id = 20;";
            Statement statement = connection.createStatement();
            int deleted = statement.executeUpdate(delete);
            if(deleted > 0){
                System.out.println("A client was successfully deleted.");
            }
            else{
                System.out.println("A client not found.");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{ connection.close(); }
            catch (SQLException e){ e.printStackTrace(); }
        }
    }
}
