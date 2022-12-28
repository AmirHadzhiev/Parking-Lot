package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import parkinglot.models.dto.CarDTO;
import parkinglot.service.CarService;

@Controller
public class CarController {
    private final CarService carService ;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/add-car")
    public ModelAndView zones(ModelAndView ModelAndVew){
        ModelAndVew.setViewName("add-car");

        return ModelAndVew;
    }

    @PostMapping("/add-car")
    public String addParking (CarDTO carDTO){

        carService.addCar(carDTO);

        return "redirect:/add-car" ;

    }

    @GetMapping("/unpark-car")
    public String exportParkingZone(Model model) {

        String info = carService.getAllCarsInParkPlaces();

        if (info.isEmpty()){
            info="Dont have cars in parkings";
            model.addAttribute("CarList", info);
        } else {

            model.addAttribute("CarList", info);
        }

        return "unpark-car";
    }


  @PostMapping("/unpark-car")
  public String unparkCars (Long id){

       carService.unparkCar(id);
           return "redirect:/unpark-car" ;
    }



}
