package parkinglot.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ParkingUpdateDTO {


    Long id;

    String city;

    String name;

    String street;


    String zipCode;

    @Override
    public String toString() {
        return "ParkingUpdateDTO{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
