package parkinglot.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParkCarDTO {

    Long parkingId;
    Long zoneId;
    Long placeId;
    Long carId;
}
