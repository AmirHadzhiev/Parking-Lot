package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import parkinglot.models.dto.ParkingDTO;
import parkinglot.service.ParkingService;

import java.util.List;

import static parkinglot.config.Messages.*;

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
    public String addParking (Model model, ParkingDTO parkingDTO){

        List<String> strings = parkingService.addParking(parkingDTO);
        StringBuilder stringBuilder = new StringBuilder();
        if (strings.contains("name")){
            model.addAttribute("mistakeForName",INVALID_NAME);
        }if (strings.contains("city")){
            model.addAttribute("mistakeForCity",INVALID_CITY);
        }
        if (strings.contains("zipCode")){
            model.addAttribute("mistakeForZipCode",INVALID_ZIP_CODE);
        }
        if (strings.contains("street")){
            model.addAttribute("mistakeForName",INVALID_NAME);
        }


        return "/parking";
    }


}
