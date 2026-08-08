package com.akash.rideshare.controller;

import com.akash.rideshare.common.dto.ApiResponse;
import com.akash.rideshare.dto.SubmitReviewRequest;
import com.akash.rideshare.dto.response.ReviewResponse;
import com.akash.rideshare.entity.Review;
import com.akash.rideshare.mapper.ReviewMapper;
import com.akash.rideshare.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping("/{rideId}/review")
    public ResponseEntity<ApiResponse<ReviewResponse>> submitReview(@PathVariable Long rideId, @Valid @RequestBody SubmitReviewRequest request ) {

        Review review = reviewService.submitReview(rideId, request);

        ReviewResponse response = reviewMapper.toReviewResponse(review);

        ApiResponse<ReviewResponse> apiResponse = ApiResponse.<ReviewResponse>builder()
                .success(true)
                .message("Review submitted successfully.")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
