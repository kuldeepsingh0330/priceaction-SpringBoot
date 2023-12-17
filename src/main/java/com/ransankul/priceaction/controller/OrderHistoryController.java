package com.ransankul.priceaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.priceaction.model.OrderHistory;
import com.ransankul.priceaction.service.OrderHistoryService;

@RestController
@RequestMapping("/api/orderhistory")
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @PostMapping("/{pageNumber}")
    public ResponseEntity<List<OrderHistory>> getOrderHistory(@RequestHeader("Authorization") String jwtToken,@PathVariable int pageNumber){
        List<OrderHistory> list = orderHistoryService.getAllOrders(jwtToken, pageNumber);
        return new ResponseEntity<List<OrderHistory>>(list, HttpStatus.OK);
    }
    
}
