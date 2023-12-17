package com.ransankul.priceaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.priceaction.service.BuySellService;

@RestController
@RequestMapping("/webhook")
public class BuySellController {
    
    @Autowired
    private BuySellService buyService;


    @PostMapping("/buysell/{token}")
    public void buysellStock(@RequestBody String req, @PathVariable String token){
        buyService.buysellStockService(req, token);
    }
}
