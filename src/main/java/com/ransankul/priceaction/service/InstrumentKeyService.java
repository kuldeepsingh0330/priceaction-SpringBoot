package com.ransankul.priceaction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ransankul.priceaction.model.InstrumentKey;
import com.ransankul.priceaction.payload.InstrumentKeyPayload;

@Service
public interface InstrumentKeyService {
    public void updadteInstrumentKey(MultipartFile file);

    List<InstrumentKeyPayload> findSimilarInstrumentKeys(String platform, String query, int pageNumber);
}
