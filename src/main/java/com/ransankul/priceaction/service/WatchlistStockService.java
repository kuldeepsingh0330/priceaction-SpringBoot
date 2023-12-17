package com.ransankul.priceaction.service;

import java.util.List;

public interface WatchlistStockService {


    public String saveFavouriteStock(String username,String watchlistStock);
    public String deleteFavouriteStock(String username, String watchlistStock);

}
