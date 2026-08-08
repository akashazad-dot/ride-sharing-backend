package com.akash.rideshare.service;

import com.akash.rideshare.dto.SubmitReviewRequest;
import com.akash.rideshare.entity.Review;

public interface ReviewService {
    Review submitReview(Long rideId, SubmitReviewRequest request);
}
