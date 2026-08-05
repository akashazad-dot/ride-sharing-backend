package com.akash.rideshare.entity;

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

    Optional<Ride> findByDriverIdAndRideStatusIn(
            UUID driverId,
            List<RideStatus> rideStatuses
    );

    List<Ride> findByRideStatus(RideStatus rideStatus);

    List<Ride> findByPassengerId(UUID passengerId);

    List<Ride> findByDriverId(UUID driverId);
}

