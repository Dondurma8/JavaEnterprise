package org.example.jdbc.basic;

import com.mysql.jdbc.Driver;

import java.sql.*;

public class SelectExample {
    public static void main(String[] args) {
        Connection connection = null;
        try{
            Driver SQLDriver = new Driver();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store", "root", "art_foot1010");
            System.out.println(connection);
            DatabaseMetaData metaData = connection.getMetaData();
            String url = metaData.getURL();
            String userName = metaData.getUserName();
            System.out.println("URL: " + url + ". User Name: " + userName);
            String sql = "SELECT i.date, p.title, p.brand, p.price, SUM(d.amount) AS total_sold\n" +
                    "FROM store.invoices AS i\n" +
                    "JOIN store.invoices_details AS d ON i.id = d.inv_id\n" +
                    "JOIN store.products AS p ON d.prod_id = p.id\n" +
                    "WHERE i.date BETWEEN '2024-01-01' AND '2024-10-19'\n" +
                    "GROUP BY i.date, p.id\n" +
                    "HAVING total_sold > 50\n" +
                    "ORDER BY i.date DESC;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for(int i = 1; i <= columnCount; i++){
                System.out.print(resultSetMetaData.getColumnName(i) + " ");
            }
            while (resultSet.next()){
                Date date = resultSet.getDate(1);
                String title = resultSet.getString(2);
                String brand = resultSet.getString(3);
                double price = resultSet.getDouble(4);
                int totalSold = resultSet.getInt(5);
                System.out.println(date + " " + title + " " + brand + " " + price + " " + totalSold);
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
