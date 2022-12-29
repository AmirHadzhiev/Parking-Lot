package parkinglot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import parkinglot.models.dto.ParkingDTO;
import parkinglot.models.dto.forPrinting.ParkingInfoDTO;
import parkinglot.models.dto.forPrinting.ParkingToStringDTO;
import parkinglot.models.entity.Parking;
import parkinglot.repository.ParkingRepository;
import parkinglot.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ParkingService {

    private final ModelMapper modelMapper;
    private final ParkingRepository parkingRepository;
    private final ValidationUtil validationUtil;

    private Long selectedParkingId=null;



    public ParkingService(ModelMapper modelMapper, ParkingRepository parkingRepository, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.parkingRepository = parkingRepository;
        this.validationUtil = validationUtil;
    }

    public List<String> addParking(ParkingDTO parkingDTO) {
       List<String> caughtErrors = new ArrayList<>();

       boolean testingZipCodeForExc = false;
        try {
            int zipCode = Integer.parseInt(parkingDTO.getZipCode());
        } catch (NumberFormatException nfe){
            testingZipCodeForExc=true;
        }


        if (parkingDTO.getName().length()<2 || parkingDTO.getName().length()>30) {
                caughtErrors.add("name");
            }
        if (parkingDTO.getCity()==null || parkingDTO.getCity().length()<2 || parkingDTO.getCity().length()>30) {
            caughtErrors.add("city");
        }

            if (!testingZipCodeForExc) {
                int zipCode = Integer.parseInt(parkingDTO.getZipCode());
                if (parkingDTO.getZipCode().length()!=4){
                  caughtErrors.add("zipCode");
                }
            } else {
                caughtErrors.add("zipCode");
            }
            if (parkingDTO.getStreet().length()<2 || parkingDTO.getStreet().length()>60 ){
                caughtErrors.add("street");
            }
      if (caughtErrors.isEmpty()) {
          Parking parkingToSafe = modelMapper.map(parkingDTO, Parking.class);
          parkingRepository.saveAndFlush(parkingToSafe);
      }

            return caughtErrors;
    }


    public void deleteById(Long id){
        if (parkingRepository.findById(id).isPresent()) {
            parkingRepository.deleteById(id);
        }

    }
    public boolean isParkingPresent( Long id){
        return  parkingRepository.findById(id).isPresent();
    }

    public void updateParking(ParkingDTO parkingDTO, Long selectedParkingId) {

        if (parkingRepository.findById(selectedParkingId).isPresent()) {

            Parking parkingToUpdate = parkingRepository.findById(selectedParkingId).get();

            if (!parkingDTO.getCity().isEmpty()) {
                parkingToUpdate.setCity((parkingDTO.getCity()));
            } else {
                parkingToUpdate.setCity("");
            }

            if (!parkingDTO.getName().isEmpty()) {
                parkingToUpdate.setName((parkingDTO.getName()));
            } else {
                parkingToUpdate.setName("");
            }

            if(!parkingDTO.getStreet().isEmpty()) {
                parkingToUpdate.setStreet((parkingDTO.getStreet()));
            } else {
                parkingToUpdate.setStreet("");
            }

            if(!parkingDTO.getZipCode().isEmpty()) {
                parkingToUpdate.setZipCode((parkingDTO.getZipCode()));
            } else {
                parkingToUpdate.setZipCode("");
            }
            parkingRepository.saveAndFlush(parkingToUpdate);
        }

    }



    public String getAllParkings() {


      return  parkingRepository.findAll() .stream()
              .map(parking -> this.modelMapper.map(parking, ParkingToStringDTO.class))
                .map(ParkingToStringDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }
    public void selectParkingId(Long id){
        selectedParkingId=id;
    }

    public Optional<Parking> getParkingById(Long selectedParkingId) {
      return parkingRepository.findById(selectedParkingId);
    }

    public String showParkingById(Long parkingId) {


        return   parkingRepository.findById(parkingId)
                .map(parking -> this.modelMapper.map(parking, ParkingInfoDTO.class))
                .map(ParkingInfoDTO::toString)
              .get();
    }
}
