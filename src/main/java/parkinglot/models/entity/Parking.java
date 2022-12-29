package parkinglot.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parkings")
public class Parking extends BaseEntity{


    @Column
    String name;


    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    List<ParkingZone> parkingZones;

    @Column
    String city;

    @Column
    String street;

    @Column
    String zipCode;


}
