package com.ransankul.priceaction.service;

import com.ransankul.priceaction.model.RelatedNews;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RelatedNewsService {
    public List<RelatedNews> getAllRelatedNews(int pageNumber);
}
