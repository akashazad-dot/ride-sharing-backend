package com.akash.rideshare.dto.response;

import com.akash.rideshare.entity.PaymentStatus;
import com.akash.rideshare.entity.RideStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RideResponse {

    private Long rideId;

    private String passengerName;

    private String driverName;

    private String pickupAddress;

    private String dropAddress;

    private RideStatus rideStatus;

    private PaymentStatus paymentStatus;

    private BigDecimal fare;

    private LocalDateTime bookedAt;
}
