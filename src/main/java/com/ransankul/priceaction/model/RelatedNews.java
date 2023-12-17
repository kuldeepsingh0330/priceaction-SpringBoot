package com.ransankul.priceaction.model;

import jakarta.persistence.*;

@Entity
public class RelatedNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String newstime;

    private String newsHeading;
    @Column(length = 10000) // Adjust the length according to your needs
    private String newsSubheading;

    public RelatedNews() {
    }

    public RelatedNews(long id, String newstime, String newsHeading, String newsSubheading) {
        this.id = id;
        this.newstime = newstime;
        this.newsHeading = newsHeading;
        this.newsSubheading = newsSubheading;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String getNewsHeading() {
        return newsHeading;
    }

    public void setNewsHeading(String newsHeading) {
        this.newsHeading = newsHeading;
    }

    public String getNewsSubheading() {
        return newsSubheading;
    }

    public void setNewsSubheading(String newsSubheading) {
        this.newsSubheading = newsSubheading;
    }

    @Override
    public String toString() {
        return "RelatedNews{" +
                "id=" + id +
                ", newstime='" + newstime + '\'' +
                ", newsHeading='" + newsHeading + '\'' +
                ", newsSubheading='" + newsSubheading + '\'' +
                '}';
    }
}
