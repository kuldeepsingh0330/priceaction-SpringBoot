package com.ransankul.priceaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.priceaction.model.Platform;
import com.ransankul.priceaction.service.PlatformService;

@RestController
@RequestMapping("/api/platform")
public class PlatformController {
    
    @Autowired
    private PlatformService platformService;

    @GetMapping("/")
    public List<Platform> getAllPlatform(){
        return platformService.getAllPlatform();
    }
    
}
