package com.akash.rideshare.service.impl;

import com.akash.rideshare.common.exception.InvalidRideStateException;
import com.akash.rideshare.common.exception.RideAlreadyActiveException;
import com.akash.rideshare.common.exception.RideNotFoundException;
import com.akash.rideshare.common.exception.UserNotFoundException;
import com.akash.rideshare.dto.BookRideRequest;
import com.akash.rideshare.entity.PaymentStatus;
import com.akash.rideshare.entity.Ride;
import com.akash.rideshare.entity.RideRepository;
import com.akash.rideshare.entity.RideStatus;
import com.akash.rideshare.service.FareCalculationService;
import com.akash.rideshare.service.RideService;
import com.akash.rideshare.user.entity.User;
import com.akash.rideshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final FareCalculationService fareCalculationService;

    @Override
    public Ride bookRide(BookRideRequest request) {

        User passenger = getCurrentUser();

        rideRepository.findByPassengerIdAndRideStatusIn(
                passenger.getId(),
                getPassengerActiveStatuses()
        ).ifPresent(ride -> {
            throw new RideAlreadyActiveException(
                    "Passenger already has an active ride."
            );
        });

        Ride ride = createRide(request, passenger);

        return rideRepository.save(ride);
    }

    @Override
    public List<Ride> getAvailableRides() {
        return rideRepository.findByRideStatus(RideStatus.REQUESTED);
    }

    @Override
    public Ride acceptRide(Long rideId) {

        User driver = getCurrentUser();

        Ride ride = getRideById(rideId);

        validateRideStatus(
                ride,
                RideStatus.REQUESTED,
                "Only REQUESTED ride can be accepted."
        );

        rideRepository.findByDriverIdAndRideStatusIn(
                driver.getId(),
                getDriverActiveStatuses()
        ).ifPresent(existingRide -> {
            throw new RideAlreadyActiveException(
                    "Driver already has an active ride."
            );
        });

        ride.setDriver(driver);
        ride.setRideStatus(RideStatus.ACCEPTED);
        ride.setAcceptedAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }

    @Override
    public Ride startRide(Long rideId) {

        User driver = getCurrentUser();

        Ride ride = getRideById(rideId);

        validateAssignedDriver(ride, driver);

        validateRideStatus(
                ride,
                RideStatus.ACCEPTED,
                "Only ACCEPTED ride can be started."
        );

        ride.setRideStatus(RideStatus.STARTED);
        ride.setStartedAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }

    @Override
    public Ride completeRide(Long rideId) {

        User driver = getCurrentUser();

        Ride ride = getRideById(rideId);

        validateAssignedDriver(ride, driver);

        validateRideStatus(
                ride,
                RideStatus.STARTED,
                "Only STARTED ride can be completed."
        );

        ride.setRideStatus(RideStatus.COMPLETED);
        ride.setCompletedAt(LocalDateTime.now());

        BigDecimal fare = fareCalculationService.calculateFare(ride);
        ride.setFare(fare);
        ride.setPaymentStatus(PaymentStatus.COMPLETED);

        return rideRepository.save(ride);
    }

    @Override
    public Ride cancelRide(Long rideId) {

        User currentUser = getCurrentUser();

        Ride ride = getRideById(rideId);

        validatePassengerOrDriver(ride, currentUser);

        if (ride.getRideStatus() != RideStatus.REQUESTED
                && ride.getRideStatus() != RideStatus.ACCEPTED) {

            throw new InvalidRideStateException(
                    "Only REQUESTED or ACCEPTED ride can be cancelled."
            );
        }

        ride.setRideStatus(RideStatus.CANCELLED);
        ride.setCancelledAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }

    @Override
    public List<Ride> getMyRides() {
        User passenger = getCurrentUser();

        return rideRepository.findByPassengerId(passenger.getId());
    }

    @Override
    public List<Ride> getMyAssignedRides() {
        User driver = getCurrentUser();

        return rideRepository.findByDriverId(driver.getId());
    }

    private Ride getRideById(Long rideId) {

        return rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RideNotFoundException("Ride not found"));
    }

    private User getCurrentUser() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
    }

    private void validateAssignedDriver(Ride ride, User driver) {

        if (ride.getDriver() == null ||
                !ride.getDriver().getId().equals(driver.getId())) {

            throw new InvalidRideStateException(
                    "You are not assigned to this ride."
            );
        }
    }

    private void validatePassengerOrDriver(Ride ride, User currentUser) {

        boolean isPassenger =
                ride.getPassenger().getId().equals(currentUser.getId());

        boolean isDriver =
                ride.getDriver() != null &&
                        ride.getDriver().getId().equals(currentUser.getId());

        if (!isPassenger && !isDriver) {

            throw new InvalidRideStateException(
                    "You are not allowed to cancel this ride."
            );
        }
    }

    private void validateRideStatus(
            Ride ride,
            RideStatus expectedRideStatus,
            String message) {

        if (ride.getRideStatus() != expectedRideStatus) {
            throw new InvalidRideStateException(message);
        }
    }

    private List<RideStatus> getPassengerActiveStatuses() {

        return List.of(
                RideStatus.REQUESTED,
                RideStatus.ACCEPTED,
                RideStatus.ARRIVED,
                RideStatus.STARTED
        );
    }

    private List<RideStatus> getDriverActiveStatuses() {

        return List.of(
                RideStatus.ACCEPTED,
                RideStatus.ARRIVED,
                RideStatus.STARTED
        );
    }

    private Ride createRide(BookRideRequest request, User passenger) {

        Ride ride = new Ride();

        ride.setPassenger(passenger);
        ride.setDriver(null);
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setRideStatus(RideStatus.REQUESTED);
        ride.setPaymentStatus(PaymentStatus.PENDING);
        ride.setFare(null);
        ride.setBookedAt(LocalDateTime.now());

        return ride;
    }
}