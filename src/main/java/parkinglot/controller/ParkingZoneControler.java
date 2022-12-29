package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import parkinglot.models.dto.ParkingZoneDTO;
import parkinglot.service.ParkingZoneService;

import static parkinglot.config.Messages.INVALID_NAME;
import static parkinglot.config.Messages.INVALID_ZONE_NAME;

@Controller
public class ParkingZoneControler {

    private final ParkingZoneService parkingZoneService;

    @Autowired
    public ParkingZoneControler(ParkingZoneService parkingZoneService) {
        this.parkingZoneService = parkingZoneService;
    }



    @GetMapping("/zones-with-id")
    public ModelAndView zonesWithId(ModelAndView ModelAndVew){
        ModelAndVew.setViewName("zones-with-id");

        return ModelAndVew;
    }

    @PostMapping("/zones-with-id")
    public String addParkingZoneWithId (Model model,ParkingZoneDTO parkingZoneDTO){

        String exception = parkingZoneService.addParkingZoneWithParkingId(parkingZoneDTO);
        if (exception!=null){
            model.addAttribute("mistakeForName",INVALID_ZONE_NAME);
        }

        return "zones-with-id" ;
    }
}
