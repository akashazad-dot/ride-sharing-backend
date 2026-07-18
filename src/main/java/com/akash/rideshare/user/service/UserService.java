package com.akash.rideshare.user.service;

import com.akash.rideshare.auth.dto.LoginRequest;
import com.akash.rideshare.auth.dto.LoginResponse;
import com.akash.rideshare.auth.dto.RegisterRequest;
import com.akash.rideshare.common.exception.EmailAlreadyExistsException;
import com.akash.rideshare.common.exception.UserNotFoundException;
import com.akash.rideshare.common.security.JwtService;
import com.akash.rideshare.user.entity.User;
import com.akash.rideshare.user.enums.Role;
import com.akash.rideshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void registerUser(RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Role.Passenger)
                .build();
        userRepository.save(user);
    }

    public LoginResponse loginUser(LoginRequest request) {
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new UserNotFoundException("Invalid email or password");
        }

        String token= jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
