package com.akash.rideshare.controller;
import com.akash.rideshare.common.dto.ApiResponse;
import com.akash.rideshare.dto.response.AvailableRideResponse;
import com.akash.rideshare.mapper.RideMapper;
import com.akash.rideshare.dto.BookRideRequest;
import com.akash.rideshare.dto.response.RideResponse;
import com.akash.rideshare.entity.Ride;
import com.akash.rideshare.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideController{
    private final RideService rideService;
    private final RideMapper rideMapper;


    @PostMapping("/book")
    public ResponseEntity<ApiResponse<RideResponse>> bookRide(@Valid @RequestBody BookRideRequest request) {

        Ride ride = rideService.bookRide(request);

        RideResponse response = rideMapper.toRideResponse(ride);

        ApiResponse<RideResponse> apiResponse = ApiResponse.<RideResponse>builder()
                .success(true)
                .message("Ride booked successfully")
                .data(response).
                build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<AvailableRideResponse>>> getAvailableRides() {
        List<Ride> rides = rideService.getAvailableRides();

        List<AvailableRideResponse> response=rides.stream()
                .map(rideMapper::toAvailableRideResponse)
                .toList();

        ApiResponse<List<AvailableRideResponse>> apiResponse= ApiResponse.<List<AvailableRideResponse>>builder()
                .success(true)
                .message("Available rides fetched successfully.")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{rideId}/accept")
    public ResponseEntity<ApiResponse<RideResponse>> acceptRide(@PathVariable Long rideId) {
        Ride ride=rideService.acceptRide(rideId);
        RideResponse response = rideMapper.toRideResponse(ride);

        ApiResponse<RideResponse> apiResponse=ApiResponse.<RideResponse>builder()
                .success(true)
                .message("Ride accepted successfully.")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{rideId}/start")
    public ResponseEntity<ApiResponse<RideResponse>> startRide(@PathVariable Long rideId) {
        Ride ride=rideService.startRide(rideId);
        RideResponse response = rideMapper.toRideResponse(ride);

        ApiResponse<RideResponse> apiResponse= ApiResponse.<RideResponse>builder()
                .success(true)
                .message("Ride start successfully.")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

    @PostMapping("/{rideId}/complete")
    public ResponseEntity<ApiResponse<RideResponse>> completeRide(@PathVariable Long rideId) {
        Ride ride=rideService.completeRide(rideId);
        RideResponse response = rideMapper.toRideResponse(ride);

        ApiResponse<RideResponse> apiResponse = ApiResponse.<RideResponse>builder()
                .success(true)
                .message("Ride complete successfully.")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("{rideId}/cancel")
    public ResponseEntity<ApiResponse<RideResponse>> cancelRide(@PathVariable Long rideId) {
        Ride ride=rideService.cancelRide(rideId);
        RideResponse response = rideMapper.toRideResponse(ride);

        ApiResponse<RideResponse> apiResponse = ApiResponse.<RideResponse>builder()
                .success(true)
                .message("Ride cancel successfully.")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);

    }
}
