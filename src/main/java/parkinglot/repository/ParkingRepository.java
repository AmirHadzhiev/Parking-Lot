package parkinglot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkinglot.models.entity.Parking;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking,Long> {


    @Override
    Optional<Parking> findById(Long id);


    @Override
    List<Parking> findAll();



}
