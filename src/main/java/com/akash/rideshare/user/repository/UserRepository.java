package com.akash.rideshare.user.repository;

import com.akash.rideshare.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}