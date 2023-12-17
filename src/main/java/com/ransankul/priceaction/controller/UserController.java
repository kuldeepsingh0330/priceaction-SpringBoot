package com.ransankul.priceaction.controller;

import java.util.Map;

import com.ransankul.priceaction.model.UserApiMapping;
import com.ransankul.priceaction.payload.UserPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.ransankul.priceaction.model.User;
import com.ransankul.priceaction.security.JWTAuthResponse;
import com.ransankul.priceaction.security.JWTTokenHelper;
import com.ransankul.priceaction.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User userpPayload) {
        userServiceImpl.registerUser(userpPayload);
        return ResponseEntity.ok("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody Map<String,String> map){

    	String username = map.get("username");
    	String password = map.get("password");
        this.doAuthenticate(username,password);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JWTAuthResponse response = new JWTAuthResponse(token,userDetails.getUsername(),"Login succesfully");
        
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserPayload> loadUserDetail(@RequestHeader("Authorization") String jwtToken){
        jwtToken = jwtToken.substring(7);
        String userName = jwtTokenHelper.extractUsername(jwtToken);
        User user = userServiceImpl.finduserByUsername(userName);
        UserPayload userPayload = new UserPayload(user.getName(),user.getPhoneNumber(),user.getEmailId(),user.getApiMappings().size(),0);
        int count = (int) user.getApiMappings().stream().filter(UserApiMapping::isOnOrOff).count();
        userPayload.setActiveApi(count);
        return new ResponseEntity<>(userPayload,HttpStatus.OK);
    }

    @PostMapping("/user/{platform}")
    public ResponseEntity<String[]> loadOtherPlatformUserDetail(@RequestHeader("Authorization") String jwtToken,@PathVariable String platform){
        jwtToken = jwtToken.substring(7);
        String userName = jwtTokenHelper.extractUsername(jwtToken);
        String[] res = userServiceImpl.loadOtherPlatformUserDetail(userName,platform);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }



    private void doAuthenticate(String username, String password){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,password);
        try{
            manager.authenticate(authentication);
        }catch(BadCredentialsException e){
            throw new RuntimeException("Invalid username and password");
        }
    }
    
}
