package parkinglot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkinglot.models.entity.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
   List<Car> findByParkingPlacesNotNull();

   List<Car> findByParkingPlacesNull();





}
