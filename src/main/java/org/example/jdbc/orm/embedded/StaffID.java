package org.example.jdbc.orm.embedded;

import java.util.Objects;

public class StaffID {
    private String dept;
    private long id;

    public StaffID(String dept, long id){
        this.dept = dept;
        this.id = id;
    }
    public String getDept() {
        return dept;
    }
    public long getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffID staffID = (StaffID) o;
        return id == staffID.id && Objects.equals(dept, staffID.dept);
    }
    @Override
    public int hashCode() {
        return Objects.hash(dept, id);
    }
    @Override
    public String toString() {
        return dept + ", " + id;
    }
}
