package parkinglot.controllers;

import org.junit.jupiter.api.Test;
import parkinglot.models.entity.Car;
import parkinglot.models.entity.ParkingPlace;
import parkinglot.service.CarService;

class CarControllerTest {
   private final   CarService carService;

    CarControllerTest(CarService carService) {
        this.carService = carService;
    }

    @Test
    void unparkCars() {
        Car car = new Car();
        ParkingPlace parkingPlace = new ParkingPlace();
        car.setParkingPlaces(parkingPlace);
        car.setPlateNumber("ds2121sd");
        Long carId = car.getId();
        carService.unparkCar(carId);

        //assertEquals();
    }
}