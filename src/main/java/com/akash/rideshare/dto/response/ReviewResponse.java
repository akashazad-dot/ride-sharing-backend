package com.akash.rideshare.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {
    private Long id;

    private Integer rating;

    private String review;

    private String passengerName;

    private String driverName;

    private Long rideId;

    private LocalDateTime createdAt;
}
