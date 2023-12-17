package com.ransankul.priceaction.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "user_api_mapping")
public class UserApiMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String platform;
    private String redirecturl;
    private boolean onOrOff;
    private boolean connectedOrNot;
    private String apikey;
    private String apisecret;
    private String apiname;
    @Column(name = "jwttoken", length = 500)
    private String jwttoken;
    private Date lastTokenTime;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserApiMapping() {
    }

    public UserApiMapping(long id, String platform, String redirecturl, boolean onOrOff, boolean connectedOrNot,
            String apikey, String apisecret, String apiname, String jwttoken, Date lastTokenTime, User user) {
        this.id = id;
        this.platform = platform;
        this.redirecturl = redirecturl;
        this.onOrOff = onOrOff;
        this.connectedOrNot = connectedOrNot;
        this.apikey = apikey;
        this.apisecret = apisecret;
        this.apiname = apiname;
        this.jwttoken = jwttoken;
        this.lastTokenTime = lastTokenTime;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOnOrOff() {
        return onOrOff;
    }

    public void setOnOrOff(boolean onOrOff) {
        this.onOrOff = onOrOff;
    }

    public boolean isConnectedOrNot() {
        return connectedOrNot;
    }

    public void setConnectedOrNot(boolean connectedOrNot) {
        this.connectedOrNot = connectedOrNot;
    }

    public Date getLastTokenTime() {
        return lastTokenTime;
    }

    public void setLastTokenTime(Date lastTokenTime) {
        this.lastTokenTime = lastTokenTime;
    }







    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    public String getRedirecturl() {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    @Override
    public String toString() {
        return "UserApiMapping [id=" + id + ", platform=" + platform + ", redirecturl=" + redirecturl + ", onOrOff="
                + onOrOff + ", connectedOrNot=" + connectedOrNot + ", apikey=" + apikey + ", apisecret=" + apisecret
                + ", apiname=" + apiname + ", jwttoken=" + jwttoken + ", lastTokenTime=" + lastTokenTime + ", user="
                + user + "]";
    }
    
}
