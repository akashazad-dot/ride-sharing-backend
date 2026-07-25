package com.akash.rideshare.service;

import com.akash.rideshare.dto.BookRideRequest;
import com.akash.rideshare.entity.Ride;

public interface RideService {
    Ride bookRide(BookRideRequest request);
}
