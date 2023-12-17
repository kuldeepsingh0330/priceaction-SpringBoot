package com.ransankul.priceaction.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ransankul.priceaction.model.User;
import com.ransankul.priceaction.repositery.UserRepo;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepositery;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {

        User user = this.userRepositery.findByEmailId(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with this username "+username); 
        }   

        return user;
    }
}