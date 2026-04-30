package org.example.jdbc.orm.onetoone;

public class Car extends Entity{
    private String brand;
    private String model;
    private int year;
    private Engine engine;
    private Transmission transmission;

    public Car(String brand, String model, int year, Engine engine, Transmission transmission){
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.engine = engine;
        this.transmission = transmission;
    }
    public Car(long id, String brand, String model, int year, Engine engine, Transmission transmission){
        super(id);
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.engine = engine;
        this.transmission = transmission;
    }
    @Override
    public String toString() {
        return "Car{" + brand + " " + model + ", " + getId() + ", " + year + ". " + engine + ". " + transmission + "}";
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public Engine getEngine() {
        return engine;
    }
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    public Transmission getTransmission() {
        return transmission;
    }
    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }
}
