package parkinglot.models.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parking_place")
public class ParkingPlace extends BaseEntity{

    @Column
    String number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id",referencedColumnName = "id")
    Car car;

}
