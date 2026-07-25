package com.akash.rideshare.controller;
import com.akash.rideshare.mapper.RideMapper;
import com.akash.rideshare.dto.BookRideRequest;
import com.akash.rideshare.dto.response.RideResponse;
import com.akash.rideshare.entity.Ride;
import com.akash.rideshare.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideController{
    private final RideService rideService;
    private final RideMapper rideMapper;

    @PostMapping("/book")
    public ResponseEntity<RideResponse> bookRide(
            @Valid @RequestBody BookRideRequest request) {

        Ride ride = rideService.bookRide(request);

        RideResponse response = rideMapper.toRideResponse(ride);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
