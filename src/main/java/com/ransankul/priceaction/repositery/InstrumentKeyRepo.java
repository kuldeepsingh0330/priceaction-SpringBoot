package com.ransankul.priceaction.repositery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ransankul.priceaction.model.InstrumentKey;

@Repository
public interface InstrumentKeyRepo extends JpaRepository<InstrumentKey,Long>{
    
    @Query("SELECT ik FROM InstrumentKey ik WHERE ik.stockName LIKE %:query% ")
    List<InstrumentKey> findByStockNameOrTradingSymbolContaining(String query, Pageable pageable);

    InstrumentKey findByUpstoxInstrumentKey(String query);
}
