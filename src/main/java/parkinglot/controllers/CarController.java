package parkinglot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import parkinglot.models.dto.CarDTO;
import parkinglot.service.CarService;

import static parkinglot.config.Messages.*;

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
    public String addParking (Model model,CarDTO carDTO){


       if (carService.addCar(carDTO)){
           model.addAttribute("mistakeForPlateNumber",INVALID_PLATE_NUMBER);
       }

        return "/add-car" ;

    }

    @GetMapping("/unpark-car")
    public String exportParkingZone(Model model) {

        String info = carService.getAllCarsInParkPlaces();

        if (info.isEmpty()){
            info="Don't have parked cars in parkings";
            model.addAttribute("CarList", info);
        } else {

            model.addAttribute("CarList", info);
        }

        return "unpark-car";
    }


  @PostMapping("/unpark-car")
  public String unparkCars (Model model,String StringId) {
      boolean catchException = false;
      try {
          Long carId = Long.valueOf(StringId);
      } catch (NumberFormatException nfe) {
          catchException = true;
      }
      if (!catchException) {
          Long carId = Long.valueOf(StringId);
          if (carService.getCarById(carId).isPresent()) {
              if (carService.getCarById(carId).get().getParkingPlaces()!=null) {
                  carService.unparkCar(carId);
                  return "redirect:/unpark-car";
              } else {
                  model.addAttribute("mistakeForId",INVALID_ID);
                  String info = carService.getAllCarsInParkPlaces();

                  if (info.isEmpty()){
                      info="Don't have parked cars in parkings";
                      model.addAttribute("CarList", info);
                  } else {

                      model.addAttribute("CarList", info);
                  }
              }
          } else {
              model.addAttribute("mistakeForId",INVALID_ID);
              String info = carService.getAllCarsInParkPlaces();

              if (info.isEmpty()){
                  info="Don't have parked cars in parkings";
                  model.addAttribute("CarList", info);
              } else {

                  model.addAttribute("CarList", info);
              }
          }
      }  else {
          model.addAttribute("mistakeForId",INVALID_ID);
          String info = carService.getAllCarsInParkPlaces();

          if (info.isEmpty()){
              info="Don't have parked cars in parkings";
              model.addAttribute("CarList", info);
          }
              model.addAttribute("CarList", info);
      }
      return "/unpark-car";
  }
}
