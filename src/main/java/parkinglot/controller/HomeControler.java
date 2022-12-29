package parkinglot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import parkinglot.repository.CarRepository;
import parkinglot.service.CarService;
import parkinglot.service.ParkingService;

@Controller
public class HomeControler {

    private final CarRepository carRepository;
    private final CarService carService;

    private final ParkingService parkingService;


    public HomeControler(CarRepository carRepository, CarService carService, ParkingService parkingService) {
        this.carRepository = carRepository;

        this.carService = carService;
        this.parkingService = parkingService;
    }


   @GetMapping("/")
    public String home(Model model) {

        return "home";
    }





}

