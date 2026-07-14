package com.akash.rideshare.user.service;

import com.akash.rideshare.auth.dto.RegisterRequest;
import com.akash.rideshare.common.exception.EmailAlreadyExistsException;
import com.akash.rideshare.user.entity.User;
import com.akash.rideshare.user.enums.Role;
import com.akash.rideshare.user.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
