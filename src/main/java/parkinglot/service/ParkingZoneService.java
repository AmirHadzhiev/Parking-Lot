package parkinglot.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parkinglot.models.dto.ParkingZoneDTO;
import parkinglot.models.dto.forPrinting.ParkingZoneToStringDTO;
import parkinglot.models.dto.forPrinting.ZoneInfoDTO;
import parkinglot.models.entity.Parking;
import parkinglot.models.entity.ParkingPlace;
import parkinglot.models.entity.ParkingZone;
import parkinglot.repository.ParkingRepository;
import parkinglot.repository.ParkingZoneRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParkingZoneService {
    private final ModelMapper modelMapper;
    private  final ParkingZoneRepository parkingZoneRepository;

    private final CarService carService;

    private final ParkingRepository parkingRepository;
    private Long selectedParkingId = null;

    @Autowired
    public ParkingZoneService(ModelMapper modelMapper, ParkingZoneRepository parkingZoneRepository, CarService carService, ParkingRepository parkingRepository) {
        this.modelMapper = modelMapper;
        this.parkingZoneRepository = parkingZoneRepository;
        this.carService = carService;
        this.parkingRepository = parkingRepository;
    }

    public void selectParkingId(Long id) {
        selectedParkingId=id;
    }
    public boolean addParkingZoneWithParkingId(ParkingZoneDTO parkingZoneDTO) {
        Optional<Parking> parking = parkingRepository.findById(selectedParkingId);

        if (parkingZoneDTO.getName().length()<2 || parkingZoneDTO.getName().length()>30) {
            return true;
        }
        if (parking.isPresent()) {

            ParkingZone parkingZoneToSafe = modelMapper.map(parkingZoneDTO, ParkingZone.class);
            parkingZoneRepository.saveAndFlush(parkingZoneToSafe);
            Set<ParkingZone> parkingZones = parking.get().getParkingZones();
            parkingZones.add(parkingZoneToSafe);

            parking.get().setParkingZones(parkingZones);
            parkingRepository.saveAndFlush(parking.get());
        }
        return false;
    }

    public String getAllZonesByParkingId(Long id) {
       if (id == 0 || parkingRepository.findById(id).isEmpty()){
           return "";
       }
       Parking parking = parkingRepository.findById(id).get();
       Set<ParkingZone> parkingZones = parking.getParkingZones();

       return parkingZones.stream()
               .map(zones -> this.modelMapper.map(zones, ParkingZoneToStringDTO.class))
                .map(ParkingZoneToStringDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }

    public String getAllZones() {
        return  parkingZoneRepository.findAll() .stream()
                .map(zone -> this.modelMapper.map(zone, ParkingZoneToStringDTO.class))
                .map( ParkingZoneToStringDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }

    public void deleteZone(Long zoneId){

        if (parkingZoneRepository.findById(zoneId).isPresent()) {
            unparkCarForZone(zoneId);
            Parking parkingWithoutZone = dropZoneFromParking(zoneId);
            if (parkingWithoutZone!=null){
               parkingRepository.save(parkingWithoutZone);
            }
            parkingZoneRepository.deleteById(zoneId);
        }
    }
    public boolean isZonePersent(Long zoneId) {
       return   parkingZoneRepository.findById(zoneId).isPresent();
    }
    public Parking dropZoneFromParking(Long zoneId ){
        if (parkingZoneRepository.findById(zoneId).isPresent()) {
            List<Parking> allParkings = parkingRepository.findAll();
            for (Parking parking : allParkings) {
                Set<ParkingZone> parkingZones = parking.getParkingZones();
                for (ParkingZone zone : parkingZones) {
                    if (zone != null && zone.getId() == zoneId) {
                        parkingZones.remove(zone);
                        parking.setParkingZones(parkingZones);
                        return parking;
                    }
                }
            }
        }
        return null;
    }


    public ParkingZone getZoneById(Long selectedZoneId) {
        if (parkingZoneRepository.findById(selectedZoneId).isPresent()) {
        return   parkingZoneRepository.findById(selectedZoneId).get();
        }
        return null;
    }

    public String updateZone(ParkingZoneDTO zoneDTO, Long selectedZoneId) {

        if (parkingZoneRepository.findById(selectedZoneId).isPresent()) {
            ParkingZone zoneToUpdate = parkingZoneRepository.findById(selectedZoneId).get();

            if (zoneDTO.getName().length()<2 || zoneDTO.getName().length()>20) {
                return"name";
            }

                zoneToUpdate.setName(zoneDTO.getName());
            parkingZoneRepository.saveAndFlush(zoneToUpdate);
        }
        return null;

    }
   // public List<ParkingZone> findAllZones(){
  //  return parkingZoneRepository.findAll();
   // }
    public String showZoneById(Long zoneId) {

        return   parkingZoneRepository.findById(zoneId)
                .map(zone -> this.modelMapper.map(zone, ZoneInfoDTO.class))
                .map(ZoneInfoDTO::toString)
                .get();
    }
    public void  unparkCarForZone(Long zoneId) {
        Optional<ParkingZone> parkingZone = parkingZoneRepository.findById(zoneId);
        if (parkingZone.isPresent()){
            Set<ParkingPlace> parkingPlaceList = parkingZone.get().getParkingPlace();
            for (ParkingPlace place : parkingPlaceList) {
               if (place.getCar()!=null){
                   carService.unparkCar(place.getCar().getId());
               }
            }
        }
    }
}