package com.ransankul.priceaction.service;

import org.springframework.stereotype.Service;

import com.ransankul.priceaction.model.User;

import java.util.Map;

@Service
public interface UserService {
    
    public User registerUser(User user);


    //finduserByUsername
    public User finduserByUsername(String username);

    public String[] loadOtherPlatformUserDetail(String username,String platform);
}
