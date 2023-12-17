package com.ransankul.priceaction.controller;

import com.ransankul.priceaction.model.RelatedNews;
import com.ransankul.priceaction.service.RelatedNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatednews")
public class RelatedNewsController {

    @Autowired
    private RelatedNewsService relatedNewsService;

    @GetMapping("/{pageNumber}")
    public ResponseEntity<List<RelatedNews>> getAllRelatedNews(@PathVariable int pageNumber){
        return new ResponseEntity<>(relatedNewsService.getAllRelatedNews(pageNumber), HttpStatus.OK);
    }

}
