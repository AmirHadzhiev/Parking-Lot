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

import static parkinglot.config.Messages.INVALID_ID;


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
    public String showByParkingId (Model model,String StringId){

        boolean catchException = false;
        try {
            Long parkingId = Long.valueOf(StringId);
        } catch (NumberFormatException nfe) {
            catchException = true;
        }
        if (!catchException) {
            Long parkingId = Long.valueOf(StringId);
            if (parkingService.getParkingById(parkingId).isPresent()) {
                String parkingInfo =parkingService.showParkingById(parkingId);
                model.addAttribute("selectedInfo",parkingInfo);

            }  else {
                model.addAttribute("mistakeForId",INVALID_ID);
            }
        } else {
            model.addAttribute("mistakeForId",INVALID_ID);
        }


        return "/parking-info";
    }

    // Zone Info

    @GetMapping("/zone-info")
    public String ZOneInfo(Model model) {

        return "zone-info";
    }


    @PostMapping("/zone-info")
    public String showByZoneId (Model model,String StringId){
        boolean catchException = false;
        try {
            Long zoneId = Long.valueOf(StringId);
        } catch (NumberFormatException nfe) {
            catchException = true;
        }
        if (!catchException) {
            Long zoneId = Long.valueOf(StringId);
            if (parkingZoneService.getZoneById(zoneId)!=null) {
                String zoneInfo =parkingZoneService.showZoneById(zoneId);
                model.addAttribute("selectedInfo",zoneInfo);
            }  else {
                model.addAttribute("mistakeForId",INVALID_ID);
            }
        } else {
            model.addAttribute("mistakeForId",INVALID_ID);
        }

        return "/zone-info";
    }
    //Place Info

    @GetMapping("/place-info")
    public String placeInfo(Model model) {

        return "place-info";
    }


    @PostMapping("/place-info")
    public String showByPlaceId (Model model,String StringId){
        boolean catchException = false;
        try {
            Long placeId = Long.valueOf(StringId);
        } catch (NumberFormatException nfe) {
            catchException = true;
        }
        if (!catchException) {
            Long placeId = Long.valueOf(StringId);
            if (parkingPlaceService.getParkingPlaceById(placeId).isPresent()) {
                String placeInfo =parkingPlaceService.showPlaceById(placeId);
                model.addAttribute("selectedInfo",placeInfo);

            }  else {
                model.addAttribute("mistakeForId",INVALID_ID);
            }
        } else {
            model.addAttribute("mistakeForId",INVALID_ID);
        }

        return "/place-info";
    }
    //Car Info

    @GetMapping("/car-info")
    public String carInfo(Model model) {

        return "car-info";
    }


    @PostMapping("/car-info")
    public String showByCarId (Model model,String StringId){
        boolean catchException = false;
        try {
            Long carId = Long.valueOf(StringId);
        } catch (NumberFormatException nfe) {
            catchException = true;
        }
        if (!catchException) {
            Long carId = Long.valueOf(StringId);
            if (carService.getCarById(carId).isPresent()) {
                String carInfo = carService.showCarInfoById(carId);
                model.addAttribute("selectedInfo", carInfo);

            }  else {
                model.addAttribute("mistakeForId",INVALID_ID);

            }
        } else {
            model.addAttribute("mistakeForId",INVALID_ID);

        }


        return "/car-info";
    }


}
