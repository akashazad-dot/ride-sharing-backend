package com.akash.rideshare.common.controller;

import com.akash.rideshare.common.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/api/v1/health")
    public HealthResponse health(){
        return new HealthResponse(
                "Up",
                "Ride sharing Backend",
                "1.1.0"
        );
    }
}
