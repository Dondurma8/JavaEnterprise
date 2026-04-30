package org.example.jdbc.orm.embedded;

import java.util.*;

public class MainEmployee {
    public static void main(String[] args) {
        StaffID staffID = new StaffID("Mangr",  128);
        Document document1 = new Document("Attestat", new Date(2023 - 1900, 8, 15), 677);
        Document document2 = new Document("Diplom", new Date(2024 - 1900, 10, 30), 898);
        List<Document> documents = new ArrayList<>();
        Collections.addAll(documents, document1, document2);
        List<String> phones = new ArrayList<>();
        phones.add("+3806784192");
        phones.add("+3809751238");
        Employee employee = new Employee("Vasya", "Vasiliev", new Date(2001 - 1900, 10, 21),
                "Manager", new Date(2020 - 1900, 4, 18), 2000.5, staffID, documents, phones);
        StaffController staffController = new StaffController();
        //staffController.create(employee);
        StaffID staffID1 = new StaffID("Dev", 36);
        Employee employee1 = new Employee("Sasha", "Aleksandrov", new Date(2004 - 1900, 5, 30),
                "Developer", new Date(2024 - 1900, 9, 14), 1960.7, staffID1,
                Arrays.asList(new Document("Medal", new Date(2023 - 1900, 3, 29), 9881)), new ArrayList<>());
        //staffController.create(employee1);
        StaffID staffID2 = new StaffID("Drct", 506);
        Document document3 = new Document("Attestat", new Date(2008 - 1900, 7, 10), 111);
        Document document4 = new Document("Diplom", new Date(2012 - 1900, 2, 6), 444);
        List<Document> documents1 = Arrays.asList(document3, document4);
        List<String> phones2 = Arrays.asList("+3806879923", "+48051230689");
        Employee employee2 = new Employee("Artem", "Kravchuk", new Date(1999 - 1900, 12, 5),
                "Director", new Date(2020 - 1900, 8, 16), 2500.2, staffID2, documents1, phones2);
        //staffController.create(employee2);
        StaffID staffID3 = new StaffID("Mangr", 222);
        List<String> phones3 = Arrays.asList("+38056939293", "+380998420104");
        Employee employee3 = new Employee("Dmitrii", "Shevchenko", new Date(1980 - 1900, 6, 12),
                "Manager", new Date(2021-1900, 3, 27), 2300.1, staffID3, new ArrayList<>(), phones3);
        //staffController.create(employee3);
        StaffID staffID4 = new StaffID("Secrt", 190);
        Document document = new Document("Diplom", new Date(2009 - 1900, 2, 10), 909);
        Document document5 = new Document("Attestat", new Date(2015 - 1900, 8, 8), 520);
        List<Document> documents2 = Arrays.asList(document, document5);
        Employee employee4 = new Employee("Arsenii", "Nesatyi", new Date(1997 - 1900, 9, 28),
                "Security", new Date(2018-1900, 5, 2), 1990.6, staffID4, documents2, new ArrayList<>());
        //staffController.create(employee4);
        //staffController.delete(employee3);
        Document newDocument1 = new Document("UpAttestat", new Date(2024 - 1900, 11, 20), 699);
        Document newDocument2 = new Document("HighDiplom", new Date(2024 - 1900, 11, 30), 899);
        List<Document> newDocuments = Arrays.asList(newDocument1, newDocument2);
        List<String> newPhones = Arrays.asList("+38068748982", "+38099252932");
        Employee updatedEmployee = new Employee("Vasya", "Petrenko", new Date(2001 - 1900, 10, 21),
                "Director", new Date(2020 - 1900, 4, 18), 3000, staffID, newDocuments, new ArrayList<>());
        //staffController.update(updatedEmployee);
        Employee employee5 = staffController.find(staffID1);
        System.out.println(employee5);
        List<Employee> employees = staffController.findAll();
        for(Employee employee6 : employees){
            System.out.println(employee6);
        }
    }
}