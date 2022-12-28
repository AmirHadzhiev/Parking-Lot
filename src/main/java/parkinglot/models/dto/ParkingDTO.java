package parkinglot.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ParkingDTO {
    String name;
    String city;
    String street;
    String zipCode;
}
