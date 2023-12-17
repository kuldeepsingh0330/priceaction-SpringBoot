package com.ransankul.priceaction.serviceImpl;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.ransankul.priceaction.controller.UpstoxMarketFeedWebsocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ransankul.priceaction.model.OrderHistory;
import com.ransankul.priceaction.model.OrderRequest;
import com.ransankul.priceaction.model.User;
import com.ransankul.priceaction.model.UserApiMapping;
import com.ransankul.priceaction.repositery.OrderHistoryRepo;
import com.ransankul.priceaction.repositery.UserApiMappingRepo;
import com.ransankul.priceaction.repositery.UserRepo;
import com.ransankul.priceaction.security.JWTTokenHelper;
import com.ransankul.priceaction.service.BuySellService;
import com.ransankul.priceaction.service.UserApiMappingService;
import com.ransankul.priceaction.service.UserService;
import com.ransankul.priceaction.util.UpstoxURL;

@Service
public class BuySellServiceImpl implements BuySellService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserApiMappingRepo userApiMappingRepo;

    @Autowired
    private OrderHistoryRepo orderHistoryRepo;

    @Autowired
    private UpstoxMarketFeedWebsocketController upstoxWebsocketController;


    private OrderRequest or = null;


    @Override
    public void buysellStockService(String req, String token) {

        try {
            or = objectMapper.readValue(req, OrderRequest.class);
        } catch (JsonProcessingException ignore) {
        }

        String userName = jwtTokenHelper.extractUsername(token);
        User user = userRepo.findByEmailId(userName);
        String platform = or.getPlatform();
        UserApiMapping userApiMapping = userApiMappingRepo.findByUserAndPlatform(user, platform);


        switch(platform){
            case "UPSTOX":
                upstoxWebsocketController.portfolioMarketFeed(userApiMapping.getJwttoken(),or,userApiMapping,userName);
                break;
        }

    }


}
