package com.akash.rideshare.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitReviewRequest {

    @Min(value = 1, message = "Rating must be at least 1.")
    @Max(value = 5,message = "Rating cannot be greater than 5")

    private Integer rating;

    @Size(max = 500, message = "Review cannot exceed 500 characters.")
    private String review;
}
