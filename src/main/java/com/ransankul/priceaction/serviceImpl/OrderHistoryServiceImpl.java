package com.ransankul.priceaction.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ransankul.priceaction.model.OrderHistory;
import com.ransankul.priceaction.repositery.OrderHistoryRepo;
import com.ransankul.priceaction.security.JWTTokenHelper;
import com.ransankul.priceaction.service.OrderHistoryService;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    @Autowired
    private OrderHistoryRepo orderHistoryRepo;
    
    @Value("${application.stockname.search.pagesize}")
    private int pageSize;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Override
    public List<OrderHistory> getAllOrders(String token, int pageNumber) {
        token = token.substring(7);
        String userName = jwtTokenHelper.extractUsername(token);
        Pageable pageable = PageRequest.of(pageNumber, pageSize); 
        return orderHistoryRepo.findByUserName(userName, pageable);
    }
    
}
