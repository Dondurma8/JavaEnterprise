package org.example.jdbc.orm.embedded;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class StaffController {
    public boolean create(Employee employee){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/orm", "root", "art_foot1010")){
            connection.setAutoCommit(false);
            String sql = "INSERT INTO staff (dept, id, name, surname, date, salary)\n" +
                    "VALUES ('" + employee.getStaffID().getDept() + "', " + employee.getStaffID().getId() + ", '" + employee.getName() +
                    "', '" + employee.getSurname() + "', '" + simpleDateFormat.format(employee.getDate()) + "', " + employee.getSalary() + ");";
            Statement statement = connection.createStatement();
            int inserted = statement.executeUpdate(sql);
            if(inserted == 0){
                connection.rollback();
            }
            for(String phone : employee.getPhones()){
                String phones = "INSERT INTO phones (dept, id, phone)\n" +
                        "VALUES ('" + employee.getStaffID().getDept() + "', " + employee.getStaffID().getId() + ", '" + phone + "');";
                statement.addBatch(phones);
            }
            int[] phonesInt = statement.executeBatch();
            for(int phone : phonesInt){
                if(phone != 1){
                    connection.rollback();
                }
            }
            for(Document document : employee.getDocuments()){
                String documents = "INSERT INTO documents (dept, id, name, date, number)\n" +
                        "VALUES ('" + employee.getStaffID().getDept() +"', " + employee.getStaffID().getId() + ", '" + document.getName() +
                        "', '" + simpleDateFormat.format(document.getDate()) + "', " + document.getNumber() + ");";
                statement.addBatch(documents);
            }
            int[] docs = statement.executeBatch();
            for(int doc : docs){
                if(doc != 1){
                    connection.rollback();
                }
            }
            connection.commit();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean delete(Employee employee){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/orm", "root", "art_foot1010")){
            String sql = "DELETE FROM staff WHERE id = " + employee.getStaffID().getId() + ";";
            Statement statement = connection.createStatement();
            int inserted = statement.executeUpdate(sql);
            if(inserted == 0){
                throw new SQLException();
            }
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(Employee employee){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/orm", "root", "art_foot1010")){
            connection.setAutoCommit(false);
            String deleteEmployee = "DELETE FROM staff WHERE id = " + employee.getStaffID().getId() + ";";
            Statement statement = connection.createStatement();
            int updatedRows = statement.executeUpdate(deleteEmployee);
            if(updatedRows == 0){
                connection.rollback();
            }
            String addEmployee = "INSERT INTO staff (dept, id, name, surname, date, salary) VALUES ('" + employee.getStaffID().getDept() +
                    "', " + employee.getStaffID().getId() + ", '" + employee.getName() + "', '" + employee.getSurname() + "', '" +
                    simpleDateFormat.format(employee.getDate() ) + "', " + employee.getSalary() + ");";
            int updated = statement.executeUpdate(addEmployee);
            if(updated == 0){
                connection.rollback();
            }
            for(String phone : employee.getPhones()){
                String insertPhone = "INSERT INTO phones (dept, id, phone) VALUES ('" + employee.getStaffID().getDept() +
                        "', " + employee.getStaffID().getId() + ", '" + phone + "');";
                statement.addBatch(insertPhone);
            }
            int[] phoneResults = statement.executeBatch();
            for(int result : phoneResults){
                if(result != 1){
                    connection.rollback();
                }
            }
            for(Document document : employee.getDocuments()){
                String insertDocument = "INSERT INTO documents (dept, id, name, date, number) VALUES ('" + employee.getStaffID().getDept() +
                        "', " + employee.getStaffID().getId() + ", '" + document.getName() + "', '" + simpleDateFormat.format(document.getDate()) +
                        "', " + document.getNumber() + ");";
                statement.addBatch(insertDocument);
            }
            int[] documentResults = statement.executeBatch();
            for(int result : documentResults){
                if(result != 1){
                    connection.rollback();
                }
            }
            connection.commit();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public Employee find(StaffID id){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/orm", "root", "art_foot1010")){
            String find = "SELECT s.dept, s.id, s.name, s.surname, s.date, s.salary, d.name AS doc_name, " +
                    "d.date AS doc_date, d.number AS doc_number, p.phone " +
                    "FROM staff AS s " +
                    "LEFT JOIN documents AS d ON s.dept = d.dept AND s.id = d.id " +
                    "LEFT JOIN phones AS p ON s.dept = p.dept AND s.id = p.id " +
                    "WHERE s.dept = '" + id.getDept() + "' AND s.id = " + id.getId() + ";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(find);
            Employee employee = null;
            List<String> phones = new ArrayList<>();
            List<Document> documents = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                Date date = resultSet.getDate("date");
                double salary = resultSet.getDouble("salary");
                String phone = resultSet.getString("phone");
                if (phone != null && !phones.contains(phone)) {
                    phones.add(phone);
                }
                String docName = resultSet.getString("doc_name");
                Date docDate = resultSet.getDate("doc_date");
                int docNumber = resultSet.getInt("doc_number");
                if (docNumber != 0) {
                    Document document = new Document(docName, docDate, docNumber);
                    if(!documents.contains(document)){
                        documents.add(document);
                    }
                }
                employee = new Employee(name, surname, date, "", null, salary, id, documents, phones);
            }
            return employee;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/orm", "root", "art_foot1010")) {
            String query = "SELECT s.dept, s.id, s.name, s.surname, s.date, s.salary, " +
                    "d.name AS doc_name, d.date AS doc_date, d.number AS doc_number, p.phone " +
                    "FROM staff AS s " +
                    "LEFT JOIN documents AS d ON s.dept = d.dept AND s.id = d.id " +
                    "LEFT JOIN phones AS p ON s.dept = p.dept AND s.id = p.id " +
                    "ORDER BY s.dept, s.id;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String dept = resultSet.getString("dept");
                int id = resultSet.getInt("id");
                StaffID staffID = new StaffID(dept, id);
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                Date date = resultSet.getDate("date");
                double salary = resultSet.getDouble("salary");
                Employee employee = new Employee(name, surname, date, salary, staffID);
                int index = employees.indexOf(employee);
                if(index != -1){
                    employee = employees.get(index);
                }
                else{
                    employees.add(employee);
                }
                String phone = resultSet.getString("phone");
                employee.addPhone(phone);
                String docName = resultSet.getString("doc_name");
                Date docDate = resultSet.getDate("doc_date");
                int docNumber = resultSet.getInt("doc_number");
                Document document = new Document(docName, docDate, docNumber);
                employee.addDocument(document);
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}