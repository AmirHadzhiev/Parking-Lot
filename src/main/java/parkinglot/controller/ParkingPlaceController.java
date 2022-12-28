package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import parkinglot.models.dto.ParkingPlaceDTO;
import parkinglot.service.ParkingPlaceService;

@Controller
public class ParkingPlaceController {
   private final ParkingPlaceService parkingPlaceService;

   @Autowired
   public ParkingPlaceController(ParkingPlaceService parkingPlaceService) {
        this.parkingPlaceService = parkingPlaceService;
    }

    @GetMapping("/places-with-id")
    public ModelAndView zonesWithId(ModelAndView ModelAndVew){
        ModelAndVew.setViewName("places-with-id");

        return ModelAndVew;
    }

    @PostMapping("/places-with-id")
    public String addParkingPlaceWithId (ParkingPlaceDTO parkingPlaceDTO){

        parkingPlaceService.addParkingPlaceWithParkingId(parkingPlaceDTO);


        return "redirect:/places-with-id" ;
    }
}
