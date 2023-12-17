package com.ransankul.priceaction.util;

public class UpstoxURL {
    

    private static final String upstoxBaseurl = "https://api-v2.upstox.com";

    public static final String upstoxProfileurl = upstoxBaseurl+"/user/profile";
    public static final String upstoxFundAndMarginurl = upstoxBaseurl+"/user/get-funds-and-margin";
    public static final String upstoxBuyurl = upstoxBaseurl+"/order/place";

    public static final String upstoxModifyurl = upstoxBaseurl+"/order/modify";

    public static final String upstoxCancelurl = upstoxBaseurl+"/order/cancel";
    public static final String upstoxMarketFeedWebSocketurl = upstoxBaseurl+"/feed/market-data-feed/authorize";
    public static final String upstoxPortfolioWebSocketurl = upstoxBaseurl+"/feed/portfolio-stream-feed/authorize";

    public static final String upstoxInstrumentKeyurl = "https://assets.upstox.com/market-quote/instruments/exchange/complete.csv.gz";
}
