package com.akash.rideshare.service.impl;

import com.akash.rideshare.common.exception.InvalidRideStateException;
import com.akash.rideshare.common.exception.RideNotFoundException;
import com.akash.rideshare.common.exception.UserNotFoundException;
import com.akash.rideshare.dto.SubmitReviewRequest;
import com.akash.rideshare.entity.*;
import com.akash.rideshare.service.ReviewService;
import com.akash.rideshare.user.entity.User;
import com.akash.rideshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    @Override
    public Review submitReview(Long rideId, SubmitReviewRequest request) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException("Ride not found"));

        User passenger = getCurrentUser();

        validatePassenger(ride, passenger);
        validateRideCompleted(ride);
        validateReviewNotExists(rideId);

        Review review = createReview(ride, passenger, request);

        return reviewRepository.save(review);
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    private void validatePassenger(Ride ride,User passenger){
        if(!ride.getPassenger().getId().equals(passenger.getId())){
            throw new InvalidRideStateException("Only passenger can submit a review.");
        }
    }

    private void validateRideCompleted(Ride ride){
        if(ride.getRideStatus() != RideStatus.COMPLETED){
            throw new InvalidRideStateException("Only completed ride can be reviewed.");
        }
    }

    private void validateReviewNotExists(Long rideId){
        reviewRepository.findByRideId(rideId)
                .ifPresent(review -> {
                    throw new InvalidRideStateException("Review already exists.");
                });
    }

    private Review createReview(Ride ride,User passenger,SubmitReviewRequest request){
        Review review = new Review();
        review.setRide(ride);
        review.setPassenger(passenger);
        review.setDriver(ride.getDriver());
        review.setRating(request.getRating());
        review.setReview(request.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return  review;

    }
}
