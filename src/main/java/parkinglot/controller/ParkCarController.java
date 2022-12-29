package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import parkinglot.models.dto.ParkCarDTO;
import parkinglot.service.CarService;
import parkinglot.service.ParkingPlaceService;
import parkinglot.service.ParkingService;
import parkinglot.service.ParkingZoneService;

@Controller
public class ParkCarController {

    private final ParkingService parkingService;
    private final ParkingZoneService parkingZoneService;
    private final CarService carService;

    private final ParkingPlaceService parkingPlaceService;



   @Autowired
    public ParkCarController(ParkingService parkingService, ParkingZoneService parkingZoneService, CarService carService, ParkingPlaceService parkingPlaceService) {
        this.parkingService = parkingService;
       this.parkingZoneService = parkingZoneService;
       this.carService = carService;
       this.parkingPlaceService = parkingPlaceService;
   }

    @GetMapping("/park-car")
    public String getParkInfo(Model model) {
        String allParkings = parkingService.getAllParkings();
        String allAvailableCars = carService.getAllNotParkedCars();
        if (allParkings.isEmpty()){
            allParkings="Don't have parkings";
        }
        if (allAvailableCars.isEmpty()){
            allAvailableCars="Don't have cars";
        }
        model.addAttribute("parkings", allParkings);
        model.addAttribute("cars", allAvailableCars);

        return "park-car";
    }

    @PostMapping("/park-car")
    public String parkCar (Model model, ParkCarDTO parkCarDTO){
        String allParkings = parkingService.getAllParkings();
        String allAvailableCars = carService.getAllNotParkedCars();
        if (allParkings.isEmpty()){
            allParkings="Don't have parkings";
        }
        if (allAvailableCars.isEmpty()){
            allAvailableCars="Don't have cars";
        }
        model.addAttribute("parkings", allParkings);
        model.addAttribute("cars", allAvailableCars);
        if (parkCarDTO.getCarId()!=null && !carService.isCarParked(parkCarDTO.getCarId())){
            model.addAttribute("carId", parkCarDTO.getCarId());
        }
        if (parkCarDTO.getParkingId()!=null){
            String allZonesByParkingId = parkingZoneService.getAllZonesByParkingId(parkCarDTO.getParkingId());
            if (allZonesByParkingId.isEmpty()){
                allZonesByParkingId="Don't have zones";
            } else {
                model.addAttribute("parkingId", parkCarDTO.getParkingId());
            }
            model.addAttribute("parkingZones", allZonesByParkingId);

            if (parkCarDTO.getZoneId() != null){
                String freeParkingPlacesForZone =
                        parkingPlaceService.getFreeParkingPlacesForZone(parkCarDTO.getZoneId());
                if (freeParkingPlacesForZone.isEmpty()){
                    freeParkingPlacesForZone="don't have places";
                } else {
                    model.addAttribute("zoneId", parkCarDTO.getZoneId());
                    if (parkCarDTO.getPlaceId()!= null){
                        model.addAttribute("placeId", parkCarDTO.getPlaceId());
                        if (parkingPlaceService.isParkingPlaceFree(parkCarDTO.getPlaceId())){
                            if (parkCarDTO.getCarId() != null){
                                if (!carService.isCarParked(parkCarDTO.getCarId())){
                                    carService.parkCar(parkCarDTO.getPlaceId(),parkCarDTO.getCarId());
                                    return "redirect:/";
                                }
                            }
                        }
                    }
                }
                model.addAttribute("parkingPlaces", freeParkingPlacesForZone);

            }
        }

        return "park-car" ;
    }



}
