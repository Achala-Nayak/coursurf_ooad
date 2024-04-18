// CustomUserDetailsService.java

package com.example.coursurf.services;

public interface CustomUserDetailsService {
    void loadByEmail(String email);
    void loadById(int id); // Corrected method name and added semicolon
}
