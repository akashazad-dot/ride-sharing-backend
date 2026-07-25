package com.akash.rideshare.entity;

import com.akash.rideshare.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    Optional<Ride> findByPassengerIdAndRideStatusIn(
            UUID passengerId,
            List<RideStatus> rideStatuses
    );

    List<Ride> findRideStatus(RideStatus rideStatus);
}

