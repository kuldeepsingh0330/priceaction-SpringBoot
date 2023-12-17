package com.ransankul.priceaction.repositery;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ransankul.priceaction.model.OrderHistory;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistory,Long>{
    
    public List<OrderHistory> findByUserName(String userName,Pageable pageable);
}
