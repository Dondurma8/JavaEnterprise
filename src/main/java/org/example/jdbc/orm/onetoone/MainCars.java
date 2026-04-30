package org.example.jdbc.orm.onetoone;

import java.util.Arrays;
import java.util.List;

public class MainCars {
    public static void main(String[] args) {
        CarService carService = new CarService();
        Engine engine = new Engine("Mazda", "diesel", 300, 3.3);
        //engine = engineController.save(engine);
        Engine engine1 = new Engine("BMW", "electro", 400, 4.2);
        //engine1 = engineController.update(engine1);
        //engineController.delete(engine1);
        Engine engine2 = carService.findByIdEngine(2);
        List<Engine> engines = carService.findAllEngines();
        Transmission transmission = new Transmission("BMW", "auto", 4);
        //gearsController.save(transmission);
        Transmission transmission1 = new Transmission("Volkswagen", "tiptron", 5);
        Transmission transmission2 = new Transmission("Opel", "auto", 4);
        //gearsController.update(transmission1);
        //gearsController.delete(transmission2);
        Transmission transmission3 = carService.findByIdGear(4);
        List<Transmission> transmissions = carService.findAllGears();
        Car car = new Car("BMW", "X6", 2023, new Engine("BMW", "auto", 550, 4.9), null);
        Car car1 = new Car("Ferrari", "Mh32", 2020, null, null);
        Car car2 = new Car(22, "Volkswagen", "Tiguan", 2023, null, transmission1);
        Car car3 = new Car(11, "Audi", "Q8", 2023, new Engine("Audi", "diesel", 300, 3), new Transmission("BMW", "tiptron", 7));
        //carService.updateCar(car3);
        System.out.println(carService.findById(24));
        List<Car> cars = carService.findAll();
        for(Car carAll : cars){
            System.out.println(carAll);
        }
        carService.close();
    }
}
