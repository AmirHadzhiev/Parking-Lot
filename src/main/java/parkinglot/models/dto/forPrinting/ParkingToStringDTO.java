package parkinglot.models.dto.forPrinting;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParkingToStringDTO {

     Long id;

    String name;


    String city;


    String street;


    String zipCode;



    @Override
    public String toString() {
        return
                "id=" + id +
                "   name= " + name  +
                "   city= " + city  +
                "   street= " + street  +
                "   zipCode= " + zipCode  ;
    }
}
