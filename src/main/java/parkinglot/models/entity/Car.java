package parkinglot.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Size(min = 6)
    @Column(name = "plate_number",unique = true)
    String plateNumber;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkig_place_id",referencedColumnName = "id")
    ParkingPlace parkingPlaces;









}
