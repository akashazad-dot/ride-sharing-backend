package com.akash.rideshare.mapper;

import com.akash.rideshare.dto.response.RideResponse;
import com.akash.rideshare.entity.Ride;
import org.springframework.stereotype.Component;


@Component
public class RideMapper {
    public RideResponse toRideResponse(Ride ride) {
        return RideResponse.builder()
                .rideId(ride.getId())
                .passengerName(
                        ride.getPassenger() != null
                                ? ride.getPassenger().getFullName()
                                : null
                )
                .driverName(
                        ride.getDriver() != null
                                ? ride.getDriver().getFullName()
                                : null
                )
                .pickupAddress(ride.getPickupLocation().getAddress())
                .dropAddress(ride.getDropLocation().getAddress())
                .rideStatus(ride.getRideStatus())
                .paymentStatus(ride.getPaymentStatus())
                .fare(ride.getFare())
                .bookedAt(ride.getBookedAt())
                .build();
    }
}
