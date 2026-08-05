package com.akash.rideshare.service;

import com.akash.rideshare.dto.BookRideRequest;
import com.akash.rideshare.entity.Ride;

import java.util.List;

public interface RideService {

    Ride bookRide(BookRideRequest request);

    List<Ride> getAvailableRides();

    Ride acceptRide(Long rideId);

    Ride startRide(Long rideId);

    Ride completeRide(Long rideId);

    Ride cancelRide(Long rideId);
}
