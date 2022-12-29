package parkinglot.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParkCarDTO {

    String parkingId;
    String zoneId;
    String placeId;
    String carId;
}
