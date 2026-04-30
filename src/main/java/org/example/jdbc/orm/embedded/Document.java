package org.example.jdbc.orm.embedded;

import java.util.Date;
import java.util.Objects;

public class Document {
    private String name;
    private Date date;
    private long number;

    public Document(String name, Date date, long number){
        this.name = name;
        this.date = date;
        this.number = number;
    }
    @Override
    public String toString() {
        return name + ", " + date + ", " + number;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Document document = (Document) obj;
        return number == document.number;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, date, number);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public long getNumber() {
        return number;
    }
    public void setNumber(long number) {
        this.number = number;
    }
}
