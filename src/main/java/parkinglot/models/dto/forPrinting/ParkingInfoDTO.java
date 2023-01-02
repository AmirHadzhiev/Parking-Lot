package parkinglot.models.dto.forPrinting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import parkinglot.models.entity.ParkingZone;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParkingInfoDTO {
    Long id;

    String name;


    String city;

    List<ParkingZone> parkingZones;

    String street;


    String zipCode;



    @Override
    public String toString() {
            StringBuilder finalInput = new StringBuilder();
             String firstInput =   "Parking with  " +
                     "id " + id +
                        " have   name - " + name  +
                        ",   city - " + city  +
                        ",   street - " + street  +
                        ",   zipCode - " + zipCode + System.lineSeparator() +
                "The Parking  contains the following zones: " + System.lineSeparator() ;
             finalInput.append(firstInput);

        for (ParkingZone parkingZone : parkingZones) {
            finalInput.append(String.format("Id -%s Name - %s%n",parkingZone.getId().toString(),parkingZone.getName()));
        }
             return finalInput.toString();
    }
}

