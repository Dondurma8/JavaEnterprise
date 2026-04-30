package org.example.jdbc.orm.embedded;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Employee {

    private String name;
    private String surname;
    private Date date;
    private String position;
    private Date employmentDate;
    private double salary;

    private StaffID staffID;
    private List<Document> documents;
    private List<String> phones;

    public Employee(String name, String surname, Date date, String position, Date employmentDate, double salary,
                    StaffID staffID, List<Document> documents, List<String > phones){
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.position = position;
        this.employmentDate = employmentDate;
        this.salary = salary;
        this.staffID = staffID;
        this.documents = documents;
        this.phones = phones;
    }
    public Employee(String name, String surname, Date date, double salary, StaffID staffID){
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.salary = salary;
        this.staffID = staffID;
        phones = new ArrayList<>();
        documents = new ArrayList<>();
    }
    public void addPhone(String phone){
        if(phone != null && !phones.contains(phone)){
            phones.add(phone);
        }
    }
    public void addDocument(Document document){
        if(document != null && !documents.contains(document)){
            documents.add(document);
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public Date getEmploymentDate() {
        return employmentDate;
    }
    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public StaffID getStaffID() {
        return staffID;
    }
    public void setStaffID(StaffID staffID) {
        this.staffID = staffID;
    }
    public List<Document> getDocuments() {
        return documents;
    }
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
    public List<String> getPhones() {
        return phones;
    }
    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return employee.staffID.equals(staffID);
    }
    @Override
    public int hashCode() {
        return Objects.hash(staffID);
    }
    @Override
    public String toString() {
        return "Employee: " + name + " " + surname + ", " + employmentDate + ", " + salary + ". Staff: "
                + staffID.toString() + ". \nDocuments: " + documents + ". \nPhones: " + phones;
    }
}
