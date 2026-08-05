package com.akash.rideshare.service;

import com.akash.rideshare.entity.Ride;

import java.math.BigDecimal;

public interface FareCalculationService {
    BigDecimal calculateFare(Ride ride);
}
