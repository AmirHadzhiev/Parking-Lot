package parkinglot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import parkinglot.models.dto.ParkingDTO;
import parkinglot.models.dto.forPrinting.ParkingInfoDTO;
import parkinglot.models.dto.forPrinting.ParkingToStringDTO;
import parkinglot.models.entity.Parking;
import parkinglot.repository.ParkingRepository;
import parkinglot.utils.ValidationUtil;

import java.util.Optional;
import java.util.stream.Collectors;

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

    public void addParking(ParkingDTO parkingDTO) {

        Parking parkingToSafe = modelMapper.map(parkingDTO, Parking.class);
        parkingRepository.saveAndFlush(parkingToSafe);

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
