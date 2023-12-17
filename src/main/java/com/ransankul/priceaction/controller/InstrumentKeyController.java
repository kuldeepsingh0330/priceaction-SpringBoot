package com.ransankul.priceaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ransankul.priceaction.model.InstrumentKey;
import com.ransankul.priceaction.payload.InstrumentKeyPayload;
import com.ransankul.priceaction.service.InstrumentKeyService;

@RestController
@RequestMapping("/api/instrumentkey")
public class InstrumentKeyController {

    @Autowired
    private InstrumentKeyService updateInstrumentKeyService;
    
    @PostMapping("/update")
    public void updadteInstrumentKey(@RequestParam("file") MultipartFile file){
        updateInstrumentKeyService.updadteInstrumentKey(file);
    }

    @GetMapping("/search/{pageNumber}")
    public ResponseEntity<List<InstrumentKeyPayload>> searchInstrumentKeys(@RequestParam String platform, @RequestParam String query,@PathVariable int pageNumber) {
        System.out.println(query);

        List<InstrumentKeyPayload> results = updateInstrumentKeyService.findSimilarInstrumentKeys(platform,query,pageNumber);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
}
