package com.ransankul.priceaction.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ransankul.priceaction.model.InstrumentKey;
import com.ransankul.priceaction.payload.InstrumentKeyPayload;
import com.ransankul.priceaction.repositery.InstrumentKeyRepo;
import com.ransankul.priceaction.service.InstrumentKeyService;

@Service
public class InstrumentKeyServiceImpl implements InstrumentKeyService {

    @Autowired
    private InstrumentKeyRepo instrumentKeyRepo;

    @Value("${application.stockname.search.pagesize}")
    private int pageSize;

    @Override
    public void updadteInstrumentKey(MultipartFile file) {

        List<InstrumentKey> instrumentKeyslist;
        try {
            instrumentKeyslist = parseAndInsertData(file.getInputStream());
            instrumentKeyRepo.saveAll(instrumentKeyslist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<InstrumentKeyPayload> findSimilarInstrumentKeys(String platform, String query, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize); 
        List<InstrumentKey> dataList = instrumentKeyRepo.findByStockNameOrTradingSymbolContaining(query, pageable);
        List<InstrumentKeyPayload> results = new ArrayList<>();

        for(InstrumentKey obj : dataList){
            InstrumentKeyPayload payload = new InstrumentKeyPayload();
            payload.setId(obj.getId());
            if(platform.equals("UPSTOX"))payload.setInstrumentKey(obj.getUpstoxInstrumentKey());

            payload.setStockName(obj.getStockName());
            payload.setTradingSymbol(obj.getTradingSymbol());

            results.add(payload);
        }

        return results;
    }


    private List<InstrumentKey> parseAndInsertData(InputStream inputStream) {
        List<InstrumentKey> instrumentKeysList = new ArrayList<>();

        try  {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                
                InstrumentKey instrumentKey = new InstrumentKey();
                String[] fields = line.split(",");
                instrumentKey.setStockName(fields[3]);
                instrumentKey.setTradingSymbol(fields[2]);
                instrumentKey.setUpstoxInstrumentKey(fields[0]);
                instrumentKeysList.add(instrumentKey);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        

        return instrumentKeysList;
    }
    
}
