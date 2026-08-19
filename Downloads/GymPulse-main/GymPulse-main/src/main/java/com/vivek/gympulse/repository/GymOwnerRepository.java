package com.vivek.gympulse.repository;

import com.vivek.gympulse.entity.GymOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface GymOwnerRepository extends JpaRepository<GymOwner, Long> {

    Optional<GymOwner> findByEmail(String email);

}