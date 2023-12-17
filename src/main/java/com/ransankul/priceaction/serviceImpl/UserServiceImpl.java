package com.ransankul.priceaction.serviceImpl;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ransankul.priceaction.model.UserApiMapping;
import com.ransankul.priceaction.repositery.UserApiMappingRepo;
import com.ransankul.priceaction.util.UpstoxURL;
import jakarta.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ransankul.priceaction.config.Constant;
import com.ransankul.priceaction.model.Roles;
import com.ransankul.priceaction.model.User;
import com.ransankul.priceaction.repositery.UserRepo;
import com.ransankul.priceaction.service.UserService;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

   @Autowired
    private UserRepo userRepositery;

   @Autowired
   private UserApiMappingRepo userApiMappingRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User registerUser(User user) {
    	Set<Roles> set = new HashSet<>();
    	set.add(new Roles(Constant.READER_ID,Constant.READER_ROLE_VALUE));
    	user.setRoles(set);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); 
        User createdUser = userRepositery.save(user);
        return createdUser;
    }


    @Override
    public User finduserByUsername(String username) {
        User user = userRepositery.findByEmailId(username);
        return user;     

    }

    @Override
    public String[] loadOtherPlatformUserDetail(String username,String platform) {

        User user = userRepositery.findByEmailId(username);
        UserApiMapping userApiMapping = userApiMappingRepo.findByUserAndPlatform(user,platform);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Api-Version", "2.0");
        headers.set("Authorization", "Bearer " + userApiMapping.getJwttoken());

        RequestEntity<String> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(UpstoxURL.upstoxProfileurl));
        ResponseEntity<String> responseProfile = restTemplate.exchange(requestEntity, String.class);

        requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(UpstoxURL.upstoxFundAndMarginurl));
        ResponseEntity<String> responseFund = restTemplate.exchange(requestEntity, String.class);

        String[] res = new String[]{responseProfile.getBody(),responseFund.getBody()};

        return res;
    }


}
