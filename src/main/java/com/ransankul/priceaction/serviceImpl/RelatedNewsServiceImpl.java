package com.ransankul.priceaction.serviceImpl;

import com.ransankul.priceaction.model.RelatedNews;
import com.ransankul.priceaction.repositery.RelatedNewsRepo;
import com.ransankul.priceaction.service.RelatedNewsService;
import com.ransankul.priceaction.util.RelatedNewsUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RelatedNewsServiceImpl implements RelatedNewsService{

    @Value("${application.stockname.search.pagesize}")
    private int pageSize;

    @Autowired
    private RelatedNewsRepo relatedNewsRepo;

    @Override
    public List<RelatedNews> getAllRelatedNews(int pageNumber) {
        try {
            String url = RelatedNewsUrl.MONEYCONTROL_MARKET_UPDATE_URL;
            Document document = Jsoup.connect(url).get();
            Elements relatednewsElements = document.getElementById("cagetory").getElementsByClass("clearfix");

            Element e = relatednewsElements.get(0);
            String newsTime = e.getElementsByClass("lazy-container").get(0).text();
            String newsHeading = e.getElementsByTag("h2").select("a").text();
            String newsSubheading = e.child(2).text();
            RelatedNews relatedNews = new RelatedNews(0,newsTime,newsHeading,newsSubheading);

            RelatedNews r = relatedNewsRepo.findByNewsHeading(newsHeading).orElse(null);
            if(r == null) relatedNewsRepo.save(relatedNews);

            for (int i = 1;i<relatednewsElements.size();i++) {
                e = relatednewsElements.get(i);
                newsTime = e.child(1).text();
                newsHeading = e.getElementsByTag("h2").select("a").text();
                newsSubheading = e.child(3).text();
                RelatedNews relatedNews1 = new RelatedNews(0,newsTime,newsHeading,newsSubheading);

                r = relatedNewsRepo.findByNewsHeading(newsHeading).orElse(null);
                if(r == null) relatedNewsRepo.save(relatedNews1);
                else break;
            }

            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            List<RelatedNews> relatedNewsList = relatedNewsRepo.findAllByOrderByIdDesc(pageable);
            System.out.println(relatedNewsList.size());
            return relatedNewsList;
        } catch (IOException e) {
            System.out.println("Error during fetching data : " + e.getMessage());
        }

        return Collections.emptyList();
    }

}
