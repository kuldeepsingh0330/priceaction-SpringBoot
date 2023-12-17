package com.ransankul.priceaction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ransankul.priceaction.model.Platform;

@Service
public interface PlatformService {
    public List<Platform> getAllPlatform();
}
