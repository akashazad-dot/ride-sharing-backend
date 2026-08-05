package com.akash.rideshare.service.impl;

import com.akash.rideshare.entity.Location;
import com.akash.rideshare.entity.Ride;
import com.akash.rideshare.service.FareCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FareCalculationServiceImpl implements FareCalculationService {

    private static final double BASE_FARE = 50.0;

    private static final double PER_KM_RATE = 15.0;

    @Override
    public BigDecimal calculateFare(Ride ride) {

        double distance = getDistanceInKm(
                ride.getPickupLocation(),
                ride.getDropLocation()
        );

        double fare = BASE_FARE + (distance * PER_KM_RATE);

        return BigDecimal.valueOf(fare);
    }
    private Double getDistanceInKm(Location pickupLocation, Location dropLocation){
        return 10.0;
    }
}
