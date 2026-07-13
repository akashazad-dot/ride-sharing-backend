package com.akash.rideshare.auth.controller;
import com.akash.rideshare.auth.dto.RegisterRequest;
import com.akash.rideshare.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public String registerUser(@Valid @RequestBody RegisterRequest request){
        userService.registerUser(request);
        return "User registered successfully";
    }
}
