package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import parkinglot.service.CarService;
import parkinglot.service.ParkingPlaceService;
import parkinglot.service.ParkingService;
import parkinglot.service.ParkingZoneService;


@Controller
public class OperationsController {
    private final ParkingService parkingService;
    private final ParkingZoneService parkingZoneService;
    private final ParkingPlaceService parkingPlaceService;
    private final CarService carService;


    @Autowired
    public OperationsController(ParkingService parkingService, ParkingZoneService parkingZoneService, ParkingPlaceService parkingPlaceService, CarService carService) {
        this.parkingService = parkingService;
        this.parkingZoneService = parkingZoneService;
        this.parkingPlaceService = parkingPlaceService;
        this.carService = carService;
    }

    @GetMapping("/parking-info")
    public String ParkingInfo(Model model) {

        return "parking-info";
    }

    @PostMapping("/parking-info")
    public String showByParkingId (Model model,Long parkingId){

       String parkingInfo =parkingService.showParkingById(parkingId);

        model.addAttribute("selectedInfo",parkingInfo);


        return "/parking-info";
    }

    // Zone Info

    @GetMapping("/zone-info")
    public String ZOneInfo(Model model) {

        return "zone-info";
    }


    @PostMapping("/zone-info")
    public String showByZoneId (Model model,Long zoneId){

        String zoneInfo =parkingZoneService.showZoneById(zoneId);


        model.addAttribute("selectedInfo",zoneInfo);


        return "/zone-info";
    }
    //Place Info

    @GetMapping("/place-info")
    public String placeInfo(Model model) {

        return "place-info";
    }


    @PostMapping("/place-info")
    public String showByPlaceId (Model model,Long placeId){

        String placeInfo =parkingPlaceService.showPlaceById(placeId);


        model.addAttribute("selectedInfo",placeInfo);


        return "/place-info";
    }
    //Car Info

    @GetMapping("/car-info")
    public String carInfo(Model model) {

        return "car-info";
    }


    @PostMapping("/car-info")
    public String showByCarId (Model model,Long carId){

        String carInfo = carService.showCarInfoById(carId);


        model.addAttribute("selectedInfo",carInfo);


        return "/car-info";
    }


}
