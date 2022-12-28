package parkinglot.models.dto.forPrinting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import parkinglot.models.entity.ParkingPlace;

@Getter
@Setter
@NoArgsConstructor
public class CarToStringDTO {

    Long id;

    String plateNumber;

    ParkingPlace parkingPlaces;

    @Override
    public String toString() {
        String result =  "   Car id= " + id +
                "        plateNumber= " + plateNumber ;
        if (parkingPlaces!=null){
            result+= "ParkingPlace number = " + parkingPlaces.getNumber();
        }
        return result;
    }
}
