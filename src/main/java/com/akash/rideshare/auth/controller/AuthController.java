package com.akash.rideshare.auth.controller;
import com.akash.rideshare.auth.dto.RegisterRequest;
import com.akash.rideshare.common.dto.ApiResponse;
import com.akash.rideshare.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerUser(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("User registered successfully")
                .data(null)
                .build();
        return ResponseEntity.ok(response);

    }
}
