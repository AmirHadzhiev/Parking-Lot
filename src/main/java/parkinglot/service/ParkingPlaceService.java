package parkinglot.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parkinglot.models.dto.ParkingPlaceDTO;
import parkinglot.models.dto.forPrinting.ParkingPlaceToStringDTO;
import parkinglot.models.dto.forPrinting.ParkingZoneToStringDTO;
import parkinglot.models.dto.forPrinting.PlaceInfoDTO;
import parkinglot.models.entity.ParkingPlace;
import parkinglot.models.entity.ParkingZone;
import parkinglot.repository.ParkingPlaceRepository;
import parkinglot.repository.ParkingZoneRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParkingPlaceService {
    private final ParkingPlaceRepository parkingPlaceRepository;

    private final ParkingZoneRepository parkingZoneRepository;
    private final ModelMapper modelMapper;

    private final CarService carService;

    private Long selectedParkingZoneId = null;

    @Autowired
    public ParkingPlaceService(ParkingPlaceRepository parkingPlaceRepository, ParkingZoneRepository parkingZoneRepository, ModelMapper modelMapper, CarService carService) {
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.parkingZoneRepository = parkingZoneRepository;
        this.modelMapper = modelMapper;
        this.carService = carService;
    }

    public String getAllParkingZones() {
        return  parkingZoneRepository.findAll() .stream()
                    .map(zone -> this.modelMapper.map(zone, ParkingZoneToStringDTO.class))
                    .map( ParkingZoneToStringDTO::toString)
                    .collect(Collectors.joining(System.lineSeparator()));

        }
    public void selectParkingZoneId(Long parkingZoneId) {
        selectedParkingZoneId=parkingZoneId;
    }
    public boolean addParkingPlaceWithParkingId(ParkingPlaceDTO parkingPlaceDTO) {
        Optional<ParkingZone> zone = parkingZoneRepository.findById(selectedParkingZoneId);

        boolean testingPlaceForExc = false;
        try {
            int placeNumber = Integer.parseInt(parkingPlaceDTO.getNumber());
        } catch (NumberFormatException nfe){
            testingPlaceForExc=true;
        }
        if (testingPlaceForExc){
            return true;
        }
        if (parkingPlaceDTO.getNumber().length()< 1 || parkingPlaceDTO.getNumber().length()>7){
            return true;
        }

        if (zone.isPresent()){

            ParkingPlace parkingPlaceToSafe = modelMapper.map(parkingPlaceDTO, ParkingPlace.class);
            parkingPlaceRepository.saveAndFlush(parkingPlaceToSafe);
            ParkingZone parkingZone = zone.get();
            Set<ParkingPlace> parkingPlace = parkingZone.getParkingPlace();
            parkingPlace.add(parkingPlaceToSafe);
            parkingZone.setParkingPlace(parkingPlace);
            parkingZoneRepository.saveAndFlush(parkingZone);
        }
        return false;
    }
    public String getFreeParkingPlacesForZone(Long zoneId) {

        ParkingZone parkingZone = parkingZoneRepository.findById(zoneId).get();

        Set<ParkingPlace> parkingPlaces = parkingZone.getParkingPlace();
        return parkingPlaces.stream()
                .filter(parkingPlace -> parkingPlace.getCar()==null)
                .map(place -> this.modelMapper.map(place, ParkingPlaceToStringDTO.class))
                .map(ParkingPlaceToStringDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }
    public boolean isParkingPlaceFree(Long placeId) {
        ParkingPlace parkingPlace = parkingPlaceRepository.findById(placeId).get();
        return parkingPlace.getCar()==null;
    }

    public void deletePlace(Long placeId) {
        Optional<ParkingPlace> parkingPlace = parkingPlaceRepository.findById(placeId);
        if (parkingPlace.isPresent()) {
            if (parkingPlace.get().getCar()!=null) {
                carService.unparkCar(parkingPlace.get().getCar().getId());
            }
            ParkingZone parkingZoneWithoutPlace = dropPlaceFromParkingZone(placeId);

            if (parkingZoneWithoutPlace!=null){

               parkingZoneRepository.save(parkingZoneWithoutPlace);
            }

            parkingPlaceRepository.deleteById(placeId);
        }
    }

    public String getAllParkingPLaces() {
      return   parkingPlaceRepository.findAll().stream()
                .map(place -> this.modelMapper.map(place, ParkingPlaceToStringDTO.class))
                .map(ParkingPlaceToStringDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public boolean isPlacePresent(Long placeId) {

        return parkingPlaceRepository.findById(placeId).isPresent();
    }
    public ParkingZone dropPlaceFromParkingZone(Long placeId){
     if (parkingPlaceRepository.findById(placeId).isPresent()){
         List<ParkingZone> listZones = parkingZoneRepository.findAll();
         for (ParkingZone parkingZone : listZones) {
             Set<ParkingPlace> parkingPlace = parkingZone.getParkingPlace();
             for (ParkingPlace place : parkingPlace) {
                 if (place!=null && place.getId()==placeId){
                     parkingPlace.remove(place);
                     parkingZone.setParkingPlace(parkingPlace);
                     return parkingZone;
                 }

             }

         }
     }
     return null;
    }

    public Optional<ParkingPlace> getParkingPlaceById(Long id) {
       return parkingPlaceRepository.findById(id);
    }

    public String updatePlace(ParkingPlaceDTO placeDTO, Long selectedPlaceId) {

        if (parkingPlaceRepository.findById(selectedPlaceId).isPresent()) {
            ParkingPlace placeToUpdate = parkingPlaceRepository.findById(selectedPlaceId).get();
            boolean testingPlaceForExc = false;
            try {
                int placeCode = Integer.parseInt(placeDTO.getNumber());
            } catch (NumberFormatException nfe){
                testingPlaceForExc=true;
            }
            if (testingPlaceForExc){
                return "place";
            }

            if (placeDTO.getNumber().length()< 1 || placeDTO.getNumber().length()>5){
                return "place";
            }

            placeToUpdate.setNumber((placeDTO.getNumber()));

            parkingPlaceRepository.saveAndFlush(placeToUpdate);
        }
        return null;
    }
    public String showPlaceById(Long placeId) {

        return   parkingPlaceRepository.findById(placeId)
                .map(place -> this.modelMapper.map(place, PlaceInfoDTO.class))
                .map(PlaceInfoDTO::toString)
                .get();
    }

}
