package parkinglot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkinglot.models.entity.ParkingPlace;

@Repository
public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace,Long> {


}
