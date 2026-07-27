package com.akash.rideshare.service.impl;

import com.akash.rideshare.common.exception.GlobalExceptionHandler;
import com.akash.rideshare.common.exception.UserNotFoundException;
import com.akash.rideshare.common.exception.RideAlreadyActiveException;
import com.akash.rideshare.dto.BookRideRequest;
import com.akash.rideshare.entity.PaymentStatus;
import com.akash.rideshare.entity.Ride;
import com.akash.rideshare.entity.RideRepository;
import com.akash.rideshare.entity.RideStatus;
import com.akash.rideshare.service.RideService;
import com.akash.rideshare.user.entity.User;
import com.akash.rideshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    @Override
    public Ride bookRide(BookRideRequest request) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        User passenger = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<RideStatus> activeStatus = List.of(
                RideStatus.REQUESTED,
                RideStatus.ACCEPTED,
                RideStatus.ARRIVED,
                RideStatus.STARTED
        );

        rideRepository.findByPassengerIdAndRideStatusIn(
                passenger.getId(),
                activeStatus
        ).ifPresent(ride -> {
            throw new RideAlreadyActiveException(
                    "Passenger already has an active ride."
            );
        });

        Ride ride = new Ride();

        ride.setPassenger(passenger);
        ride.setDriver(null);
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setRideStatus(RideStatus.REQUESTED);
        ride.setPaymentStatus(PaymentStatus.PENDING);
        ride.setFare(null);
        ride.setBookedAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }

    @Override
    public List<Ride> getAvailableRides() {
        return rideRepository.findByRideStatus(RideStatus.REQUESTED);
    }
}
