package org.example.jdbc.orm.onetoone;

public class Engine extends Entity{
    private String brand;
    private String type;
    private int power;
    private double volume;
    public Engine(long id, String brand, String type, int power, double volume){
        super(id);
        this.brand = brand;
        this.type = type;
        this.power = power;
        this.volume = volume;
    }
    public Engine(String brand, String type, int power, double volume){
        this.brand = brand;
        this.type = type;
        this.power = power;
        this.volume = volume;
    }
    @Override
    public String toString() {
        return "Engine{" + getId() + ", " + brand + ", " + type + ", power " + power +
                ", volume " + volume + "}";
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
    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public double getVolume() {
        return volume;
    }
    public void setVolume(double volume) {
        this.volume = volume;
    }
}
