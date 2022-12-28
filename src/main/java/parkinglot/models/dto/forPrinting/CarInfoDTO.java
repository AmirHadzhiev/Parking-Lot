package parkinglot.models.dto.forPrinting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import parkinglot.models.entity.ParkingPlace;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarInfoDTO {


    Long id;

    String plateNumber;

    ParkingPlace parkingPlaces;


    @Override
    public String toString() {



        StringBuilder finalInput = new StringBuilder();
        String firstInput =  "   Car id= " + id +
                "plateNumber= " + plateNumber +System.lineSeparator() ;
        finalInput.append(firstInput);
        if (parkingPlaces!=null){
            finalInput.append(String.format("Car is in  place with id - %s and number - %s%n"
                    +"This place is in Zone with id - %s and name %s %n"
                    ,parkingPlaces.getId().toString(),parkingPlaces.getNumber()));
        } else {
            finalInput.append(String.format("This car is not parked%n"));
        }

        return finalInput.toString();
    }

}
