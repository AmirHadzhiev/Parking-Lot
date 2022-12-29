package parkinglot.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CarDTO {
    @Size(min = 6, max = 8)
    @Column(unique = true)
    String plateNumber;


}
