package com.akash.rideshare.mapper;

import com.akash.rideshare.dto.response.ReviewResponse;
import com.akash.rideshare.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponse toReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .rideId(review.getRide().getId())
                .passengerName(review.getPassenger().getFullName())
                .driverName(review.getDriver().getFullName())
                .rating(review.getRating())
                .review(review.getReview())
                .createdAt(review.getCreatedAt())
                .build();

    }
}
