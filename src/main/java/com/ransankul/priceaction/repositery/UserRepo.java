package com.ransankul.priceaction.repositery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ransankul.priceaction.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmailId(String emailId);   
}
