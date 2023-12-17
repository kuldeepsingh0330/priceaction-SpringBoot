package com.ransankul.priceaction.repositery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ransankul.priceaction.model.Platform;

@Repository
public interface PlatformRepo extends JpaRepository<Platform,Integer> {
    
}
