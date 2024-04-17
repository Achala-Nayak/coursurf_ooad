package com.example.coursurf.repository;

import com.example.coursurf.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OurUserRepo extends JpaRepository<OurUsers, Integer> {
    Optional<OurUsers> findByEmail(String email);
}