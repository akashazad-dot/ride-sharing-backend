package com.akash.rideshare.dto;

import com.akash.rideshare.entity.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRideRequest {
    @NotNull(message = "Pickup location is required")
    @Valid
    private Location pickupLocation;

    @NotNull(message = "Drop location is required")
    @Valid
    private Location dropLocation;
}
