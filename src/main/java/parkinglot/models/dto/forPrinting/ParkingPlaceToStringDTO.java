package parkinglot.models.dto.forPrinting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import parkinglot.models.entity.Car;

@Getter
@Setter
@NoArgsConstructor
public class ParkingPlaceToStringDTO {

    Long id ;

    String number;


    Car car;

    @Override
    public String toString() {
        return "id=" + id +
                ", number='" + number ;
    }
}
