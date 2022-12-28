package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import parkinglot.models.dto.ParkingDTO;
import parkinglot.service.ParkingService;

@Controller
public class ParkingController {
    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/parking")
    public ModelAndView parking(ModelAndView ModelAndVew){
        ModelAndVew.setViewName("parking");

        return ModelAndVew;
    }


    @PostMapping("/parking")
    public String addParking (ParkingDTO parkingDTO){

        parkingService.addParking(parkingDTO);

        return "parking";
    }


}
