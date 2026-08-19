package com.vivek.gympulse.security;

import com.vivek.gympulse.entity.GymOwner;
import com.vivek.gympulse.repository.GymOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymOwnerDetailsService implements UserDetailsService {

    @Autowired
    private GymOwnerRepository gymOwnerRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        GymOwner owner = gymOwnerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Owner Not Found"));

        return new User(
                owner.getEmail(),
                owner.getPassword(),
                List.of(new SimpleGrantedAuthority(owner.getRole()))
        );
    }
}