package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import parkinglot.service.ParkingPlaceService;
import parkinglot.service.ParkingService;
import parkinglot.service.ParkingZoneService;

@Controller
public class SelectParkingControler {

    private final ParkingService parkingService;
    private final ParkingZoneService parkingZoneService;

    private final ParkingPlaceService parkingPlaceService;


    @Autowired
    public SelectParkingControler(ParkingService parkingService, ParkingZoneService parkingZoneService, ParkingPlaceService parkingPlaceService) {
        this.parkingService = parkingService;
        this.parkingZoneService = parkingZoneService;
        this.parkingPlaceService = parkingPlaceService;
    }

    @GetMapping("/select-parking")
    public String exportParking(Model model) {

        String info = parkingService.getAllParkings();
        if (info.isEmpty()){
            info="Dont have parkings";
        }
            model.addAttribute("parkingList", info);
        return "select-parking";
    }

    @PostMapping("/select-parking")
    public String addParkingZone (Long parkingId){
        parkingZoneService.selectParkingId(parkingId);


        return "redirect:/zones-with-id" ;
    }

    @GetMapping("/select-zones")
    public String exportParkingPlace(Model model) {

        String info = parkingPlaceService.getAllParkingZones();
        if (info.isEmpty()){
            info="Don't have parking Zones";
            model.addAttribute("parkingZonesList", info);
        } else {

        model.addAttribute("parkingZonesList", info);}

        return "select-zones";
    }

    @PostMapping("/select-zones")
    public String addParkingPlace (Long parkingZoneId){
      //  parkingZoneService.selectParkingId(parkingZoneId);
        parkingPlaceService.selectParkingZoneId(parkingZoneId);


        return "redirect:/places-with-id" ;
    }


}
