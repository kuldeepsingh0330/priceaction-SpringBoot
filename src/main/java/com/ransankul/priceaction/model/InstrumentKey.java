package com.ransankul.priceaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "instrumentkey")
public class InstrumentKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    private String stockName;
    private String tradingSymbol;
    private String upstoxInstrumentKey;
    
    public InstrumentKey() {
    }

    public InstrumentKey(long id, String stockName, String tradingSymbol, String upstoxInstrumentKey) {
        this.id = id;
        this.stockName = stockName;
        this.tradingSymbol = tradingSymbol;
        this.upstoxInstrumentKey = upstoxInstrumentKey;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    public String getTradingSymbol() {
        return tradingSymbol;
    }
    public void setTradingSymbol(String tradingSymbol) {
        this.tradingSymbol = tradingSymbol;
    }
    public String getUpstoxInstrumentKey() {
        return upstoxInstrumentKey;
    }
    public void setUpstoxInstrumentKey(String upstoxInstrumentKey) {
        this.upstoxInstrumentKey = upstoxInstrumentKey;
    }

    @Override
    public String toString() {
        return "InstrumentKey{" +
                "id=" + id +
                ", stockName='" + stockName + '\'' +
                ", tradingSymbol='" + tradingSymbol + '\'' +
                ", upstoxInstrumentKey='" + upstoxInstrumentKey + '\'' +
                '}';
    }
}
