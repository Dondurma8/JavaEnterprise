package org.example.jdbc.orm.onetoone;

public class Transmission extends Entity{
    private String brand;
    private String type;
    private int speed;
    public Transmission(int id, String brand, String type, int speed){
        super(id);
        this.brand = brand;
        this.type= type;
        this.speed = speed;
    }
    public Transmission(String brand, String type, int speed){
        this.brand = brand;
        this.type= type;
        this.speed = speed;
    }
    @Override
    public String toString() {
        return "Transmission{" + getId() + ", " + brand + ", " + type + ", speed " + speed + "}";
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
