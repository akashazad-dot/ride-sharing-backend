package com.akash.rideshare.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class AvailableRideResponse {

    private Long rideId;

    private String pickupAddress;

    private String dropAddress;

    private BigDecimal fare;

    private LocalDateTime bookedAt;
}
