package parkinglot.controllers;

import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import parkinglot.models.dto.ParkCarDTO;
import parkinglot.service.CarService;
import parkinglot.service.ParkingPlaceService;
import parkinglot.service.ParkingService;
import parkinglot.service.ParkingZoneService;

@Controller
public class ParkCarController {

    private final ParkingService parkingService;
    private final ParkingZoneService parkingZoneService;
    private final CarService carService;

    private final ParkingPlaceService parkingPlaceService;
    private final CarToPark carToPark;
    private static class CarToPark{
        private Long carId;
        private Long parkPlaceId;

        public CarToPark() {
            this.carId = null;
            this.parkPlaceId = null;
        }

        public Long getCarId() {
            return carId;
        }

        public void setCarId(Long carId) {
            this.carId = carId;
        }

        public Long getParkPlaceId() {
            return parkPlaceId;
        }

        public void setParkPlaceId(Long parkPlaceId) {
            this.parkPlaceId = parkPlaceId;
        }
    }
   @Autowired
    public ParkCarController(ParkingService parkingService, ParkingZoneService parkingZoneService,
                             CarService carService, ParkingPlaceService parkingPlaceService) {
        this.parkingService = parkingService;
       this.parkingZoneService = parkingZoneService;
       this.carService = carService;
       this.parkingPlaceService = parkingPlaceService;
       this.carToPark = new CarToPark();
   }

    @GetMapping("/park-car")
    public String getParkInfo(Model model) {
        String allParkings = parkingService.getAllParkings();
        String allAvailableCars = carService.getAllNotParkedCars();
        if (allParkings.isEmpty()){
            allParkings="Don't have parkings";
        }
        if (allAvailableCars.isEmpty()){
            allAvailableCars="Don't have cars";
        }
        model.addAttribute("parkings", allParkings);
        model.addAttribute("cars", allAvailableCars);

        return "park-car";
    }

    @PostMapping("/park-car")
    public String parkCar (Model model, ParkCarDTO parkCarDTO){
        CarService.ParkCarInput parkCarInput = carService.validateInput(parkCarDTO);
        Pair<Long, String> carIdExc = parkCarInput.getCarIdExc();
        Pair<Long, String> parkingIdExc = parkCarInput.getParkingIdExc();
        Pair<Long, String> parkingZoneIdExc = parkCarInput.getParkingZoneIdExc();
        Pair<Long, String> parkingPlaceIdExc = parkCarInput.getParkingPlaceIdExc();
        if (!carIdExc.getRight().isEmpty()){
            model.addAttribute("mistakeForCarID",carIdExc.getRight());
        }
        if (!parkingIdExc.getRight().isEmpty()){
            model.addAttribute("mistakeForParkingID",parkingIdExc.getRight());
        }
        if (!parkingZoneIdExc.getRight().isEmpty()){
            model.addAttribute("mistakeForZoneID",parkingZoneIdExc.getRight());
        }
        if (!parkingPlaceIdExc.getRight().isEmpty()){
            model.addAttribute("mistakeForPlaceID",parkingPlaceIdExc.getRight());
        }


        String allParkings = parkingService.getAllParkings();
        String allAvailableCars = carService.getAllNotParkedCars();
        if (allParkings.isEmpty()){
            allParkings="Don't have parkings";
        }
        if (allAvailableCars.isEmpty()){
            allAvailableCars="Don't have cars";
        }
        model.addAttribute("parkings", allParkings);
        model.addAttribute("cars", allAvailableCars);
        if (carIdExc.getLeft()!=null && !carService.isCarParked(carIdExc.getLeft())){
            model.addAttribute("carId", carIdExc.getLeft());
        }
        if (parkingIdExc.getLeft()!=null){
            String allZonesByParkingId = parkingZoneService.getAllZonesByParkingId(parkingIdExc.getLeft());
            if (allZonesByParkingId.isEmpty()){
                allZonesByParkingId="Don't have zones";
            } else {
                model.addAttribute("parkingId", parkingIdExc.getLeft());
            }
            model.addAttribute("parkingZones", allZonesByParkingId);

            if (parkingZoneIdExc.getLeft() != null){
                String freeParkingPlacesForZone =
                        parkingPlaceService.getFreeParkingPlacesForZone(parkingZoneIdExc.getLeft());
                if (freeParkingPlacesForZone.isEmpty()){
                    freeParkingPlacesForZone="don't have places";
                } else {
                    model.addAttribute("zoneId", parkingZoneIdExc.getLeft());
                    if (parkingPlaceIdExc.getLeft()!= null){
                        model.addAttribute("placeId", parkingPlaceIdExc.getLeft());
                        if (parkingPlaceService.isParkingPlaceFree(parkingPlaceIdExc.getLeft())){
                            if (carIdExc.getLeft() != null){
                                if (!carService.isCarParked(carIdExc.getLeft())){
                                    carToPark.setCarId(carIdExc.getLeft());
                                    carToPark.setParkPlaceId(parkingPlaceIdExc.getLeft());
                                }
                            }
                        }
                    }
                }
                model.addAttribute("parkingPlaces", freeParkingPlacesForZone);
            }
        }
        return "park-car" ;
    }
    @GetMapping("/dummy")
    public String dummy() {
        if (carToPark.getCarId()!=null && carToPark.getParkPlaceId()!=null){
          carService.parkCar(carToPark.getParkPlaceId(), carToPark.getCarId());
          carToPark.setParkPlaceId(null);
          carToPark.setCarId(null);
      }
        return "redirect:/park-car";
    }
}
