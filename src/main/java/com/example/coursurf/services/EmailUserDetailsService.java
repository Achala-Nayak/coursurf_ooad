package com.example.coursurf.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface EmailUserDetailsService {
    UserDetails loadUserByEmail(String email);
}
