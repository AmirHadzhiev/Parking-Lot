package parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import parkinglot.models.dto.CarDTO;
import parkinglot.models.dto.ParkingDTO;
import parkinglot.models.dto.ParkingPlaceDTO;
import parkinglot.models.dto.ParkingZoneDTO;
import parkinglot.models.entity.Car;
import parkinglot.models.entity.Parking;
import parkinglot.models.entity.ParkingPlace;
import parkinglot.models.entity.ParkingZone;
import parkinglot.repository.ParkingRepository;
import parkinglot.service.CarService;
import parkinglot.service.ParkingPlaceService;
import parkinglot.service.ParkingService;
import parkinglot.service.ParkingZoneService;

import java.time.temporal.ValueRange;
import java.util.List;

import static parkinglot.config.Messages.*;

@Controller
public class UpdateController {

    private final ParkingService parkingService;
    private final ParkingPlaceService parkingPlaceService;
    private final ParkingRepository parkingRepository ;
    private final ParkingZoneService parkingZoneService;
    private final CarService carService;
    private  Long selectedParkingId;
    private  Long selectedZoneId;
    private  Long selectedPlaceId;
    private Long selectedCarId;

   @Autowired
    public UpdateController(ParkingService parkingService, ParkingPlaceService parkingPlaceService, ParkingRepository parkingRepository, ParkingZoneService parkingZoneService, CarService carService) {
        this.parkingService = parkingService;
       this.parkingPlaceService = parkingPlaceService;
       this.parkingRepository = parkingRepository;
       this.parkingZoneService = parkingZoneService;
       this.carService = carService;
   }

    @GetMapping("/select-parking-update")
    public String selectParkingForUpdate(Model model) {

        String info = parkingService.getAllParkings();
        if (info.isEmpty()){
            info="Don't have parkings";
        }
        model.addAttribute("parkingList", info);
        return "select-parking-update";
    }

    @PostMapping("/select-parking-update")
    public String takeParkingToUpdate (Model model,String StringId) {
        boolean catchException = false;
        try {
            Long parkingId = Long.valueOf(StringId);
        } catch (NumberFormatException nfe) {
            catchException = true;
        }
        if (!catchException) {
            Long parkingId = Long.valueOf(StringId);
            if (parkingService.getParkingById(parkingId).isPresent()) {
                selectedParkingId = parkingId;
                return "redirect:/parking-update";
            }
            String errorText = "Invalid Id";
            model.addAttribute("mistakeForId", errorText);
            String info = parkingService.getAllParkings();
            if (info.isEmpty()) {
                info = "Don't have parkings";
            }
            model.addAttribute("parkingList", info);
            return "/select-parking-update";

        } else {
            String errorText = "Invalid Id";

            String info = parkingService.getAllParkings();
            if (info.isEmpty()) {
                info = "Don't have parkings";
            }
            model.addAttribute("parkingList", info);
            model.addAttribute("mistakeForId", errorText);
            return "/select-parking-update";
        }
    }

    @GetMapping("/parking-update")
    public String showOldParking(Model model) {
        Parking parking = parkingService.getParkingById(selectedParkingId).get();

        model.addAttribute("city",parking.getCity());
        model.addAttribute("name",parking.getName());
        model.addAttribute( "street",parking.getStreet());
        model.addAttribute( "zipCode",parking.getZipCode());


        return "parking-update";
    }

    @PostMapping("/parking-update")
    public String updateParking (Model model,ParkingDTO parkingDTO){
        List<String> strings = parkingService.updateParking(parkingDTO,selectedParkingId);
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
            model.addAttribute("mistakeForStreet",INVALID_STREET);
        }
        if (!strings.isEmpty()) {
            return "parking-update";
        }



        return "redirect:/select-parking-update";
    }
    //Update For Zones
    @GetMapping("/select-zone-update")
    public String selectZoneForUpdate(Model model) {


       String allZones = parkingZoneService.getAllZones();
        if (allZones.isEmpty()){
            allZones="Don't have zone, you can add zone in Parking Zone Operations";
        }
        model.addAttribute("zoneList", allZones);
        return "select-zone-update";
    }

    @PostMapping("/select-zone-update")
    public String takeZonesForUpdate (Model model,String StringId){
       // selectedZoneId=zoneId;
        boolean catchException = false;
        try {
            Long zoneId = Long.valueOf(StringId);
        } catch (NumberFormatException nfe) {
            catchException = true;
        }
        if (!catchException) {
            Long zoneId = Long.valueOf(StringId);
            if (parkingZoneService.getZoneById(zoneId)!=null) {
                selectedZoneId=zoneId;
                return "redirect:/zone-update";
            }
            model.addAttribute("mistakeForId", INVALID_ID);
            String info = parkingZoneService.getAllZones();
            if (info.isEmpty()) {
                info = "Don't have zone, you can add zone in Parking Zone Operations";
            }
            model.addAttribute("zoneList", info);
            return "/select-zone-update";

        } else {
            String info = parkingZoneService.getAllZones();
            if (info.isEmpty()) {
                info = "Don't have zone, you can add zone in Parking Zone Operations";
            }
            model.addAttribute("zoneList", info);
            model.addAttribute("mistakeForId", INVALID_ID);
            return "/select-zone-update";
        }
    }

    @GetMapping("/zone-update")
    public String showOldZone(Model model) {
       ParkingZone zone = parkingZoneService.getZoneById(selectedZoneId);
       model.addAttribute("name",zone.getName());
       return "zone-update";
    }

    @PostMapping("/zone-update")
    public String updateZone (Model model,ParkingZoneDTO zoneDTO){

       String excZone = parkingZoneService.updateZone(zoneDTO,selectedZoneId);
      if (excZone!=null){
          model.addAttribute("mistakeForName",INVALID_ZONE_NAME);
          ParkingZone zone = parkingZoneService.getZoneById(selectedZoneId);
          model.addAttribute("name",zone.getName());
          return "/zone-update";
      }


        return "redirect:/select-zone-update";
    }
    //Update For Places

    @GetMapping("/select-place-update")
    public String selectPlaceForUpdate(Model model) {

        String pLaces = parkingPlaceService.getAllParkingPLaces();
        if (pLaces.isEmpty()){
            pLaces="Don't have places, you can add place in Parking Place Operations";
        }
        model.addAttribute("placeList", pLaces);
        return "select-place-update";
    }

    @PostMapping("/select-place-update")
    public String takePlaceToUpdate (Model model,String StringId){

        boolean catchException = false;
        try {
            Long placeId = Long.valueOf(StringId);
        } catch (NumberFormatException nfe) {
            catchException = true;
        }
        if (!catchException) {
            Long placeId = Long.valueOf(StringId);
            if (parkingPlaceService.getParkingPlaceById(placeId).isPresent()) {
                selectedPlaceId=placeId;
                return "redirect:/place-update";
            }
            model.addAttribute("mistakeForId", INVALID_ID);
            String info = parkingPlaceService.getAllParkingPLaces();
            if (info.isEmpty()) {
                info = "Don't have place, you can add place in Parking Place Operations";
            }
            model.addAttribute("placeList", info);
            return "/select-place-update";

        } else {
            String info = parkingPlaceService.getAllParkingPLaces();
            if (info.isEmpty()) {
                info = "Don't have place, you can add zone in Parking Place Operations";
            }
            model.addAttribute("placeList", info);
            model.addAttribute("mistakeForId", INVALID_ID);
            return "/select-place-update";
        }
    }

    @GetMapping("/place-update")
    public String showOldPLace(Model model) {
       ParkingPlace parkingPlace = parkingPlaceService.getParkingPlaceById(selectedPlaceId).get();
        model.addAttribute("number",parkingPlace.getNumber());
        return "place-update";
    }

    @PostMapping("/place-update")
    public String updatePlace (Model model,ParkingPlaceDTO placeDTO){
        String excPlace = parkingPlaceService.updatePlace(placeDTO,selectedPlaceId);
        if (excPlace!=null){
            model.addAttribute("mistakeForPlace",INVALID_PLACE_NUMBER);
            ParkingPlace parkingPlace = parkingPlaceService.getParkingPlaceById(selectedPlaceId).get();
            model.addAttribute("number",parkingPlace.getNumber());
            return "/place-update";
        }

        return "redirect:/select-place-update";
    }

    //Update For Car

    @GetMapping("/select-car-update")
    public String selectCarForUpdate(Model model) {

        String allCars = carService.getAllCars();
        if (allCars.isEmpty()){
            allCars="Don't have cars, you can add car in Car Operations";
        }
        model.addAttribute("carList", allCars);
        return "select-car-update";
    }

    @PostMapping("/select-car-update")
    public String takeCarToUpdate (Model model,String StringId){
       boolean catchException = false;
        try {
            Long carId = Long.valueOf(StringId);
        } catch (NumberFormatException nfe) {
            catchException = true;
        }
        if (!catchException) {
            Long carId = Long.valueOf(StringId);
            if (carService.getCarById(carId).isPresent()) {
                selectedCarId=carId;
                return "redirect:/car-update";
            }
            model.addAttribute("mistakeForId", INVALID_ID);
            String info = carService.getAllCars();
            if (info.isEmpty()) {
                info = "Don't have cars, you can add car in Car Operations";
            }
            model.addAttribute("carList", info);
            return "/select-car-update";

        } else {
            String info = carService.getAllCars();
            if (info.isEmpty()) {
                info = "Don't have cars, you can add car in Car Operations";
            }
            model.addAttribute("carList", info);
            model.addAttribute("mistakeForId", INVALID_ID);
            return "/select-car-update";
        }
    }

    @GetMapping("/car-update")
    public String showOldCar(Model model) {
        Car car = carService.getCarById(selectedCarId).get();

        model.addAttribute("car",car.getPlateNumber());


        return "car-update";
    }

    @PostMapping("/car-update")
    public String updateCar (Model model,CarDTO carDTO){
        carService.updateParking(carDTO,selectedCarId);
        String excCar = carService.updateParking(carDTO,selectedCarId);
        if (excCar!=null){
            model.addAttribute("mistakeForCar",INVALID_PLATE_NUMBER);
            Car oldCartoPrint = carService.getCarById(selectedCarId).get();
            model.addAttribute("car",oldCartoPrint.getPlateNumber());
            return "/car-update";
        }

        return "redirect:/select-car-update";
    }




}
