package com.ransankul.priceaction.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ransankul.priceaction.model.Platform;
import com.ransankul.priceaction.repositery.PlatformRepo;
import com.ransankul.priceaction.service.PlatformService;

@Service
public class PlatformServiceImpl implements PlatformService{

    @Autowired
    private PlatformRepo platformRepo;

    @Override
    public List<Platform> getAllPlatform() {
        return platformRepo.findAll();
    }


    
}
