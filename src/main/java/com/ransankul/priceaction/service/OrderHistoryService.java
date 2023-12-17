package com.ransankul.priceaction.service;

import java.util.List;

import com.ransankul.priceaction.model.OrderHistory;

public interface OrderHistoryService {

    public List<OrderHistory> getAllOrders(String token, int pageNumber);
    
}
