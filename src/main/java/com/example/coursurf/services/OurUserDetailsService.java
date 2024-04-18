// OurUserDetailsService.java

package com.example.coursurf.services;

import com.example.coursurf.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OurUserDetailsService implements UserDetailsService, CustomUserDetailsService {

    @Autowired
    private OurUserRepo ourUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ourUserRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    @Override
    public void loadByEmail(String email) {
    ourUserRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
}

    @Override
    public void loadById(int id) {
        // ourUserRepo.findByid(id)
        //     .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
