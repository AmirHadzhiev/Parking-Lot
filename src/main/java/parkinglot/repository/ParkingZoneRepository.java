package parkinglot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkinglot.models.entity.ParkingZone;

import java.util.Optional;

@Repository
public interface ParkingZoneRepository extends JpaRepository<ParkingZone,Long> {



    @Override
    Optional<ParkingZone> findById(Long id);




}
