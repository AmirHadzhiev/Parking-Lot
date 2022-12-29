package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import parkinglot.models.dto.ParkingPlaceDTO;
import parkinglot.service.ParkingPlaceService;

import static parkinglot.config.Messages.INVALID_CITY;
import static parkinglot.config.Messages.INVALID_PLACE_NUMBER;

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
    public String addParkingPlaceWithId (Model model, ParkingPlaceDTO parkingPlaceDTO){

        String excForPlace = parkingPlaceService.addParkingPlaceWithParkingId(parkingPlaceDTO);
        if (excForPlace!=null){
            model.addAttribute("mistakeForPlace",INVALID_PLACE_NUMBER);
        }


        return "places-with-id" ;
    }
}
