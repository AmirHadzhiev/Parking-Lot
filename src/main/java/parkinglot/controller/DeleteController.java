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
public class DeleteController {
    private final ParkingService parkingService;
    private final ParkingZoneService parkingZoneService;

    private final ParkingPlaceService parkingPlaceService;

    private final CarService carService;

    @Autowired
    public DeleteController(ParkingService parkingService, ParkingZoneService parkingZoneService, ParkingPlaceService parkingPlaceService, CarService carService) {
        this.parkingService = parkingService;
        this.parkingZoneService = parkingZoneService;
        this.parkingPlaceService = parkingPlaceService;
        this.carService = carService;
    }

    @GetMapping("/select-parking-delete")
    public String exportParking(Model model) {

        String allParkings = parkingService.getAllParkings();
        if (allParkings.isEmpty()){
            allParkings="Dont have parkings, if you want to add you can go in parking operation and add Parking";
        }
        model.addAttribute("parkingList", allParkings);
        return "select-parking-delete";
    }
    @PostMapping("/select-parking-delete")
    public String deleteParking (Model model,Long parkingId){
        boolean parkingPresent = parkingService.isParkingPresent(parkingId);
        if (!parkingPresent){
            String text = "Parking with Id " + parkingId + " doesn't exist!";
            model.addAttribute("infoText",text);
        } else {
            parkingService.deleteById(parkingId);
        }
        String allParkings = parkingService.getAllParkings();
        if (allParkings.isEmpty()){
            allParkings="Don't have parkings, if you want to add you can go in parking operation and add Parking";
        }
        model.addAttribute("parkingList", allParkings);

        return "select-parking-delete";
    }

    @GetMapping("/select-zone-delete")
    public String exportParkingZone(Model model) {

    String allZone = parkingZoneService.getAllZones();
        if (allZone.isEmpty()){
            allZone="Dont have zones, if you want to add you can go in Parking Zone operation and add Zone";
        }
            model.addAttribute("parkingZones", allZone);

        return "select-zone-delete";
    }
    @PostMapping("/select-zone-delete")
    public String deleteParkingZone (Model model,Long zoneId) {
        boolean zonePresent = parkingZoneService.isZonePersent(zoneId);
        if (!zonePresent) {
            String text = "Zone with Id:  " + zoneId + " doesn't exist!";
            model.addAttribute("infoText", text);
        } else {
            parkingZoneService.deleteZone(zoneId);
        }
        String allZone = parkingZoneService.getAllZones();

        if (allZone.isEmpty()) {
            allZone = "Don't have zones, if you want to add you can go in Parking Zone operation and add Zone";

        }
        model.addAttribute("parkingZones", allZone);
        return "select-zone-delete";
    }

    @GetMapping("/select-place-delete")
    public String exportParkingPlace(Model model) {

        String allPLaces = parkingPlaceService.getAllParkingPLaces();
        if (allPLaces.isEmpty()){
            allPLaces="Dont have places, if you want to add you can go in Parking Place operation and add Place";
        }
            model.addAttribute("parkingPlaces", allPLaces);

        return "select-place-delete";
    }
    @PostMapping("/select-place-delete")
    public String deleteParkingPlace(Model model,Long placeId){
        boolean placePresent = parkingPlaceService.isPlacePresent(placeId);
        if (!placePresent) {
            String text = "Parking Place with Id:  " + placeId + " doesn't exist!";
            model.addAttribute("infoText", text);
        } else {
            parkingPlaceService.deletePlace(placeId);
        }
        String allPLaces = parkingPlaceService.getAllParkingPLaces();
        if (allPLaces.isEmpty()){
            allPLaces="Don't have places, if you want to add you can go in Parking Place operation and add Place";
        }
        model.addAttribute("parkingPlaces", allPLaces);

        return "select-place-delete";
    }
    @GetMapping("/select-car-delete")
    public String exportCars(Model model) {

        String allCars = carService.getAllCars();
        if (allCars.isEmpty()){
            allCars="Don't have cars, if you want to add you can go in Car Operation and add Car";
        }
            model.addAttribute("Cars", allCars);
        return "select-car-delete";
    }

    @PostMapping("/select-car-delete")
    public String deleteCaR (Model model,Long carId){
            boolean carPresent = carService.isCarPresent(carId);
            if (!carPresent) {
                String text = "Car with Id:  " + carId + " doesn't exist!";
                model.addAttribute("infoText", text);
            } else {
                carService.deleteCar(carId);
            }
        String allCars = carService.getAllCars();
        if (allCars.isEmpty()){
            allCars="Don't have cars, if you want to add you can go in Car Operation and add Car";
        }
        model.addAttribute("Cars", allCars);
        return "select-car-delete";
    }
}
