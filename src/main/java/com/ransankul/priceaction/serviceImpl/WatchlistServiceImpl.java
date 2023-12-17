package com.ransankul.priceaction.serviceImpl;

import com.ransankul.priceaction.model.InstrumentKey;
import com.ransankul.priceaction.model.User;
import com.ransankul.priceaction.repositery.InstrumentKeyRepo;
import com.ransankul.priceaction.repositery.UserRepo;
import com.ransankul.priceaction.service.WatchlistStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchlistServiceImpl implements WatchlistStockService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InstrumentKeyRepo instrumentKeyRepo;

    @Override
    public String saveFavouriteStock(String username,String watchlistStock) {

            User user = userRepo.findByEmailId(username);
            InstrumentKey instrumentKey = instrumentKeyRepo.findByUpstoxInstrumentKey(watchlistStock);

            if(!user.getWatchlist().contains(instrumentKey))user.getWatchlist().add(instrumentKey);
            user = userRepo.save(user);
            if(user.getUserId() != 0) return "added succesfully";

        return "null";
    }

    @Override
    public String deleteFavouriteStock(String username, String watchlistStock) {
        try{
            User user = userRepo.findByEmailId(username);
            InstrumentKey instrumentKey = instrumentKeyRepo.findByUpstoxInstrumentKey(watchlistStock);
            user.getWatchlist().remove(instrumentKey);
            user = userRepo.save(user);
            if(user.getUserId() != 0) return "remove succesfully";
        }catch (Exception ignore){

        }
        return "null";
    }
    
}
