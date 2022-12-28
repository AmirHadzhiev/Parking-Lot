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
    public String takeParkingToUpdate (Long parkingId){
       selectedParkingId=parkingId;


        return "redirect:/parking-update";
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
    public String updateParking (ParkingDTO parkingDTO){
       parkingService.updateParking(parkingDTO,selectedParkingId);


        return "redirect:/select-parking-update";
    }
    //Update For Zones
    @GetMapping("/select-zone-update")
    public String selectZoneForUpdate(Model model) {

       String allZones = parkingZoneService.getAllZones();
        if (allZones.isEmpty()){
            allZones="Don't have zone, you can add zone in Parking Zone Operations ";
        }
        model.addAttribute("zoneList", allZones);
        return "select-zone-update";
    }

    @PostMapping("/select-zone-update")
    public String takeZonesForUpdate (Long zoneId){
        selectedZoneId=zoneId;


        return "redirect:/zone-update";
    }

    @GetMapping("/zone-update")
    public String showOldZone(Model model) {
       ParkingZone zone = parkingZoneService.getZoneById(selectedZoneId);

       model.addAttribute("name",zone.getName());
       return "zone-update";
    }

    @PostMapping("/zone-update")
    public String updateZone (ParkingZoneDTO zoneDTO){

       parkingZoneService.updateZone(zoneDTO,selectedZoneId);


        return "redirect:/select-zone-update";
    }
    //Update For Places

    @GetMapping("/select-place-update")
    public String selectPlaceForUpdate(Model model) {

        String pLaces = parkingPlaceService.getAllParkingPLaces();
        if (pLaces.isEmpty()){
            pLaces="Don't have Parking Places";
        }
        model.addAttribute("placeList", pLaces);
        return "select-place-update";
    }

    @PostMapping("/select-place-update")
    public String takePlaceToUpdate (Long placeId){
        selectedPlaceId=placeId;


        return "redirect:/place-update";
    }

    @GetMapping("/place-update")
    public String showOldPLace(Model model) {
       ParkingPlace parkingPlace = parkingPlaceService.getParkingPlaceById(selectedPlaceId).get();

        model.addAttribute("number",parkingPlace.getNumber());

        return "place-update";
    }

    @PostMapping("/place-update")
    public String updatePlace (ParkingPlaceDTO placeDTO){
        parkingPlaceService.updatePlace(placeDTO,selectedPlaceId);


        return "redirect:/select-place-update";
    }

    //Update For Car

    @GetMapping("/select-car-update")
    public String selectCarForUpdate(Model model) {

        String allCars = carService.getAllCars();
        if (allCars.isEmpty()){
            allCars="Don't have cars";
        }
        model.addAttribute("carList", allCars);
        return "select-car-update";
    }

    @PostMapping("/select-car-update")
    public String takeCarToUpdate (Long carId){
        selectedCarId=carId;

        return "redirect:/car-update";
    }

    @GetMapping("/car-update")
    public String showOldCar(Model model) {
        Car car = carService.getCarById(selectedCarId).get();

        model.addAttribute("plateNumber",car.getPlateNumber());


        return "car-update";
    }

    @PostMapping("/car-update")
    public String updateCar (CarDTO carDTO){
        carService.updateParking(carDTO,selectedCarId);


        return "redirect:/select-car-update";
    }




}
