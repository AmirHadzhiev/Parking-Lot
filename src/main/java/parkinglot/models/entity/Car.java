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

    @Size(min = 8, max = 10)
    @Column(name = "plate_number",unique = true)
    String plateNumber;




    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "parking_place_id",referencedColumnName = "id")
    ParkingPlace parkingPlaces;









}
