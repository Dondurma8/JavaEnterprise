package org.example.jdbc.orm.onetoone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class CarService {
    private Connection connection;
    public CarService(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/orm", "root", "art_foot1010");
            connection.setAutoCommit(false);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Car saveCar(Car car){
        CarsController carsController = new CarsController(connection);
        try{
            Car saved = carsController.save(car);
            if(saved == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return saved;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Car updateCar(Car car) {
        CarsController carsController = new CarsController(connection);
        try {
            Car updated = carsController.update(car);
            if (updated == null) {
                connection.rollback();
                return null;
            }
            connection.commit();
            return updated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Car deleteCar(Car car){
        CarsController carsController = new CarsController(connection);
        try {
            Car deleted = carsController.delete(car);
            if(deleted == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return deleted;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Car findById(Object id) {
        CarsController carsController = new CarsController(connection);
        try {
            Car found = carsController.findById(id);
            if (found == null) {
                System.out.println("Car with ID " + id + " not found.");
            }
            return found;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Car> findAll() {
        CarsController carsController = new CarsController(connection);
        try {
            List<Car> cars = carsController.findAll();
            if (cars.isEmpty()) {
                System.out.println("No cars found.");
            }
            return cars;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
    public void close(){
        try{
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    //
    public Transmission saveGear(Transmission transmission){
        GearsController gearsController = new GearsController(connection);
        try {
            Transmission saved = gearsController.save(transmission);
            if(saved == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return saved;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Transmission updateGear(Transmission transmission) {
        GearsController gearsController = new GearsController(connection);
        try {
            Transmission updated = gearsController.update(transmission);
            if (updated == null) {
                connection.rollback();
                return null;
            }
            connection.commit();
            return updated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Transmission deleteGear(Transmission transmission){
        GearsController gearsController = new GearsController(connection);
        try {
            Transmission deleted = gearsController.delete(transmission);
            if(deleted == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return deleted;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Transmission findByIdGear(Object id) {
        GearsController gearsController = new GearsController(connection);
        try {
            Transmission found = gearsController.findById(id);
            if (found == null) {
                System.out.println("Transmission with ID " + id + " not found.");
            }
            return found;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Transmission> findAllGears() {
        GearsController gearsController = new GearsController(connection);
        try {
            List<Transmission> gears = gearsController.findAll();
            if (gears.isEmpty()) {
                System.out.println("No gears found.");
            }
            return gears;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
    //
    public Engine saveEngine(Engine engine){
        EngineController engineController = new EngineController(connection);
        try {
            Engine saved = engineController.save(engine);
            if(saved == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return saved;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Engine updateEngine(Engine engine) {
        EngineController engineController = new EngineController(connection);
        try {
            Engine updated = engineController.update(engine);
            if (updated == null) {
                connection.rollback();
                return null;
            }
            connection.commit();
            return updated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Engine deleteEngine(Engine engine){
        EngineController engineController = new EngineController(connection);
        try {
            Engine deleted = engineController.delete(engine);
            if(deleted == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return deleted;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Engine findByIdEngine(Object id) {
        EngineController engineController = new EngineController(connection);
        try {
            Engine  found = engineController.findById(id);
            if (found == null) {
                System.out.println("Engine with ID " + id + " not found.");
            }
            return found;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Engine> findAllEngines() {
        EngineController engineController = new EngineController(connection);
        try {
            List<Engine> engines = engineController.findAll();
            if (engines.isEmpty()) {
                System.out.println("No engines found.");
            }
            return engines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
