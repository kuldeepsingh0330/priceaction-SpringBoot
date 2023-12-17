package com.ransankul.priceaction.controller;


import com.ransankul.priceaction.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ransankul.priceaction.service.WatchlistStockService;

import java.util.List;

@RestController
@RequestMapping("/api/Watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistStockService watchlistStockService;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @PostMapping("/add/{watchlistStock}")
    public ResponseEntity<?> addWatchlistStock(@RequestHeader("Authorization") String jwtToken, @PathVariable String watchlistStock) {
        String username = jwtTokenHelper.extractUsername(jwtToken.substring(7));
        String addOrNot = watchlistStockService.saveFavouriteStock(username,watchlistStock);
        if(!addOrNot.isEmpty() && !addOrNot.isBlank() || !addOrNot.equals("null")){
            return new ResponseEntity<>("Added succesfully",HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Something went wrong try again",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{watchlistStock}")
    public ResponseEntity<String> deleteWatchlistStock(@RequestHeader("Authorization") String jwtToken,@PathVariable String watchlistStock) {
        String username = jwtTokenHelper.extractUsername(jwtToken.substring(7));
        String deleteOrNot = watchlistStockService.deleteFavouriteStock(username,watchlistStock);
        if(!deleteOrNot.isEmpty() && !deleteOrNot.isBlank() || !deleteOrNot.equals("null")){
            return new ResponseEntity<>("Deleted succesfully",HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Something went wrong try again",HttpStatus.OK);

    }

}

