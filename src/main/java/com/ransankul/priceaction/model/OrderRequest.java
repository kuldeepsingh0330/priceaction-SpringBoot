package com.ransankul.priceaction.model;

public class OrderRequest {
    private String platform;
    private int quantity;
    private String product;
    private String validity;
    private double price;
    private String tag;
    private String instrument_token;
    private String order_type;
    private String transaction_type;
    private int disclosed_quantity;
    private double trigger_price;
    private boolean is_amo;

    private double target_price;

    private boolean simpletradeis;

    private double trailstoploss;



    
    public OrderRequest() {
    }


    public OrderRequest(String platform, int quantity, String product, String validity, double price, String tag, String instrument_token, String order_type, String transaction_type, int disclosed_quantity, double trigger_price, boolean is_amo, double target_price, boolean simpletradeis, double trailstoploss) {
        this.platform = platform;
        this.quantity = quantity;
        this.product = product;
        this.validity = validity;
        this.price = price;
        this.tag = tag;
        this.instrument_token = instrument_token;
        this.order_type = order_type;
        this.transaction_type = transaction_type;
        this.disclosed_quantity = disclosed_quantity;
        this.trigger_price = trigger_price;
        this.is_amo = is_amo;
        this.target_price = target_price;
        this.simpletradeis = simpletradeis;
        this.trailstoploss = trailstoploss;
    }

    public String getPlatform() {
        return platform;
    }


    public void setPlatform(String platform) {
        this.platform = platform;
    }




    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getProduct() {
        return product;
    }


    public void setProduct(String product) {
        this.product = product;
    }


    public String getValidity() {
        return validity;
    }


    public void setValidity(String validity) {
        this.validity = validity;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public String getTag() {
        return tag;
    }


    public void setTag(String tag) {
        this.tag = tag;
    }


    public String getInstrument_token() {
        return instrument_token;
    }


    public void setInstrument_token(String instrument_token) {
        this.instrument_token = instrument_token;
    }


    public String getOrder_type() {
        return order_type;
    }


    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }


    public String getTransaction_type() {
        return transaction_type;
    }


    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }


    public int getDisclosed_quantity() {
        return disclosed_quantity;
    }


    public void setDisclosed_quantity(int disclosed_quantity) {
        this.disclosed_quantity = disclosed_quantity;
    }


    public double getTrigger_price() {
        return trigger_price;
    }


    public void setTrigger_price(double trigger_price) {
        this.trigger_price = trigger_price;
    }


    public boolean isIs_amo() {
        return is_amo;
    }


    public void setIs_amo(boolean is_amo) {
        this.is_amo = is_amo;
    }

    public double getTarget_price() {
        return target_price;
    }

    public void setTarget_price(double target_price) {
        this.target_price = target_price;
    }

    public boolean isSimpletradeis() {
        return simpletradeis;
    }

    public void setSimpletradeis(boolean simpletradeis) {
        this.simpletradeis = simpletradeis;
    }

    public double getTrailstoploss() {
        return trailstoploss;
    }

    public void setTrailstoploss(double trailstoploss) {
        this.trailstoploss = trailstoploss;
    }
}