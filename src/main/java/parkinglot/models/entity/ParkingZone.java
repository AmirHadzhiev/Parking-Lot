package parkinglot.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parking_zone")
public class ParkingZone extends BaseEntity{
//id

    @Column
    String name;


   @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
   List<ParkingPlace> parkingPlace;




}
