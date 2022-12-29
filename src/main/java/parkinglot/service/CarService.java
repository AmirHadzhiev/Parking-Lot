package parkinglot.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parkinglot.models.dto.CarDTO;
import parkinglot.models.dto.ParkCarDTO;
import parkinglot.models.dto.forPrinting.CarToStringDTO;
import parkinglot.models.entity.Car;
import parkinglot.models.entity.Parking;
import parkinglot.models.entity.ParkingPlace;
import parkinglot.models.entity.ParkingZone;
import parkinglot.repository.CarRepository;
import parkinglot.repository.ParkingPlaceRepository;
import parkinglot.repository.ParkingRepository;
import parkinglot.repository.ParkingZoneRepository;
import parkinglot.utils.ValidationUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private  final ParkingPlaceRepository parkingPlaceRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final ParkingRepository parkingRepository;

    private final ParkingZoneRepository parkingZoneRepository;

    @Autowired
    public CarService(CarRepository carRepository, ParkingPlaceRepository parkingPlaceRepository, ModelMapper modelMapper, ValidationUtil validationUtil, ParkingRepository parkingRepository, ParkingZoneRepository parkingZoneRepository) {
        this.carRepository = carRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;

        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.parkingRepository = parkingRepository;
        this.parkingZoneRepository = parkingZoneRepository;
    }







    public String addCar(CarDTO carDTO) {

        boolean isValid = validationUtil.isValid(carDTO);
        if (isValid) {
            Car carToSafe = modelMapper.map(carDTO, Car.class);
            carRepository.saveAndFlush(carToSafe);
            return null;
        }
        return "plateNumber";
    }


    public String getAllCarsInParkPlaces() {
        return  carRepository.findByParkingPlacesNotNull()
                .stream()
                .map(car -> this.modelMapper.map(car, CarToStringDTO.class))
                .map( CarToStringDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
    public void unparkCar(Long id) {
        Car carToSafeWithNoPLace = carRepository.findById(id).get();
        ParkingPlace parkingPlacesWithNoCar = carToSafeWithNoPLace.getParkingPlaces();
        carToSafeWithNoPLace.setParkingPlaces(null);
        carRepository.saveAndFlush(carToSafeWithNoPLace);

        parkingPlacesWithNoCar.setCar(null);
        parkingPlaceRepository.saveAndFlush(parkingPlacesWithNoCar);

    }
    public String getAllNotParkedCars() {
        return   carRepository.findByParkingPlacesNull()
                .stream()
                .map(car -> this.modelMapper.map(car, CarToStringDTO.class))
                .map( CarToStringDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void parkCar(Long parkingPlaceId, Long parkingCarId){
        Car car = carRepository.findById(parkingCarId).get();
        ParkingPlace parkingPlace = parkingPlaceRepository.findById(parkingPlaceId).get();
        car.setParkingPlaces(parkingPlace);
        parkingPlace.setCar(car);
        carRepository.saveAndFlush(car);
        parkingPlaceRepository.saveAndFlush(parkingPlace);

    }
    public boolean isCarParked(Long carId){
        if (carRepository.findById(carId).isPresent()) {
            Car car = carRepository.findById(carId).get();

            return   car.getParkingPlaces()!=null;
        }
        return true;

    }


    public String getAllCars() {

      return   carRepository.findAll() .stream()
                .map(car -> this.modelMapper.map(car, CarToStringDTO.class))
                .map( CarToStringDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void deleteCar(Long carId) {
        if (carRepository.findById(carId).isPresent()) {
            if (isCarParked(carId)) {
                unparkCar(carId);
            }
            carRepository.deleteById(carId);
        }
    }


    public boolean isCarPresent(Long carId) {
        return carRepository.findById(carId).isPresent();
    }

    public Optional<Car> getCarById(Long selectedCarId) {

        return carRepository.findById(selectedCarId);

    }

    public String updateParking(CarDTO carDTO, Long selectedCarId) {

        if (carRepository.findById(selectedCarId).isPresent()) {
            Car carToUpdate = carRepository.findById(selectedCarId).get();
            boolean isValid = validationUtil.isValid(carDTO);
            if (!isValid) {
                return "car";
            }

            if (carDTO.getPlateNumber().equals(carToUpdate.getPlateNumber())){

                return null;
            }
            List<Car> allCars = carRepository.findAll();
            for (Car car : allCars) {
                if (car.getPlateNumber().equals(carDTO.getPlateNumber())){
                    return "car";
                }

            }

            carToUpdate.setPlateNumber((carDTO.getPlateNumber()));
            carRepository.saveAndFlush(carToUpdate);
        }
        return null;
    }

    public String showCarInfoById(Long carId) {
        ParkingZone carParkingZone = null;
        Parking carParking = null;
        ParkingPlace carParkingPlace = null;

        Optional<Car> car = carRepository.findById(carId);

        if (car.isPresent()){
            carParkingPlace = car.get().getParkingPlaces();
            if (carParkingPlace!=null) {
                List<ParkingZone> zoneList = parkingZoneRepository.findAll();
                zoneLoop:
                for (ParkingZone zone : zoneList) {
                    Set<ParkingPlace> parkingPlace = zone.getParkingPlace();
                    for (ParkingPlace place : parkingPlace) {
                        if (Objects.equals(place.getId(), carParkingPlace.getId())) {
                            carParkingZone = zone;
                            break zoneLoop;
                        }
                    }
                }
            }
            if (carParkingZone!=null){
                List<Parking> allParkings = parkingRepository.findAll();
                carLoop:
                for (Parking parking : allParkings) {
                    Set<ParkingZone> zones = parking.getParkingZones();
                    for (ParkingZone zone : zones) {
                        if (Objects.equals(zone.getId(), carParkingZone.getId())){
                            carParking=parking;
                            break carLoop;
                        }
                    }
                }
            }
        }
        StringBuilder result = new StringBuilder();
        if (car.isPresent()) {
            result.append(String.format("Car info: id - %s, Plate Number: %s%n",
                    car.get().getId().toString(),car.get().getPlateNumber()));
        } else {
            result.append(String.format("Car is not found!%n"));
        }
        if (carParking!=null){
            result.append(String.format("Car is in Parking with Info: Id - %s, Name - %s," +
                            "City - %s, ZipCode - %s, Street - %s %n",carParking.getId().toString(),
                    carParking.getName(),carParking.getCity(),carParking.getZipCode(),carParking.getStreet()));
            result.append(String.format("Car is  in Parking Zone with Info: id - %s, Name: %s%n",
                    carParkingZone.getId().toString(),carParkingZone.getName()));
            result.append(String.format("Car is  in Parking Place with Info: id - %s, Number: %s%n",
                    carParkingPlace.getId().toString(),carParkingPlace.getNumber()));
        } else {
            result.append(String.format("The Car is not Parked!%n"));
        }
        return result.toString();
    }

    public ParkCarInput validateInput(ParkCarDTO parkCarDTO) {
        ParkCarInput parkCarInput = new ParkCarInput();
        try {
            Long carId = Long.valueOf(parkCarDTO.getCarId());
            parkCarInput.setCarIdExc( Pair.of(carId,""));
        }catch (NumberFormatException exc){
            parkCarInput.setCarIdExc(Pair.of(null,"Invalid Car Id"));
        }

        try {
            Long parkingId = Long.valueOf(parkCarDTO.getParkingId());
            parkCarInput.setParkingIdExc(Pair.of(parkingId,""));
        }catch (NumberFormatException exc){
            parkCarInput.setParkingIdExc(Pair.of(null,"Invalid Parking Id"));
        }
        try {
            Long placeId = Long.valueOf(parkCarDTO.getPlaceId());
            parkCarInput.setParkingPlaceIdExc(Pair.of(placeId,""));
        }catch (NumberFormatException exc){
            parkCarInput.setParkingPlaceIdExc(Pair.of(null,"Invalid Place Id"));
        }
        try {
            Long zoneId = Long.valueOf(parkCarDTO.getZoneId());
            parkCarInput.setParkingZoneIdExc(Pair.of(zoneId,""));
        }catch (NumberFormatException exc){
            parkCarInput.setParkingZoneIdExc(Pair.of(null,"Invalid Zone Id"));
        }


        return parkCarInput;
    }
   public static class ParkCarInput{


       public ParkCarInput() {
           this.carIdExc = Pair.of(null,"");
           this.parkingIdExc = Pair.of(null,"");
           this.parkingZoneIdExc = Pair.of(null,"");
           this.parkingPlaceIdExc = Pair.of(null,"");
       }

       Pair<Long,String> carIdExc;
       Pair<Long,String> parkingIdExc;
       Pair<Long,String> parkingZoneIdExc;
       Pair<Long,String> parkingPlaceIdExc;

       public Pair<Long, String> getCarIdExc() {
           return carIdExc;
       }

       public void setCarIdExc(Pair<Long, String> carIdExc) {
           this.carIdExc = carIdExc;
       }

       public Pair<Long, String> getParkingIdExc() {
           return parkingIdExc;
       }

       public void setParkingIdExc(Pair<Long, String> parkingIdExc) {
           this.parkingIdExc = parkingIdExc;
       }

       public Pair<Long, String> getParkingZoneIdExc() {
           return parkingZoneIdExc;
       }

       public void setParkingZoneIdExc(Pair<Long, String> parkingZoneIdExc) {
           this.parkingZoneIdExc = parkingZoneIdExc;
       }

       public Pair<Long, String> getParkingPlaceIdExc() {
           return parkingPlaceIdExc;
       }

       public void setParkingPlaceIdExc(Pair<Long, String> parkingPlaceIdExc) {
           this.parkingPlaceIdExc = parkingPlaceIdExc;
       }
   }
}
