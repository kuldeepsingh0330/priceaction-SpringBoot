package com.ransankul.priceaction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.ransankul.priceaction.model.OrderHistory;
import com.ransankul.priceaction.model.OrderRequest;
import com.ransankul.priceaction.model.UserApiMapping;
import com.ransankul.priceaction.repositery.OrderHistoryRepo;
import com.ransankul.priceaction.upstoxutil.MarketDataFeed;
import com.ransankul.priceaction.util.UpstoxURL;
import jakarta.servlet.http.HttpSession;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UpstoxMarketFeedWebsocketController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderHistoryRepo orderHistoryRepo;

    private String instrumentkey;

    private String buyStockId;

    private WebSocketClient portfoliowebSocketClient;

    public void portfolioMarketFeed(String upstoxjwtToken, OrderRequest or, UserApiMapping userApiMapping,String userName){

        String websocketurl = getwebSocketUrl(upstoxjwtToken,false);
        instrumentkey = or.getInstrument_token();

        try {
            URI serverUri = new URI(websocketurl);
            portfoliowebSocketClient = new WebSocketClient(serverUri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                     buyStockId = buyStockUpstox(or, userApiMapping.getJwttoken(),userName);
                }

                @Override
                public void onMessage(String message) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = null;

                    try {
                        jsonNode = objectMapper.readTree(message);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    if(message.contains("complete")){
                        or.setPrice(jsonNode.path("price").doubleValue());
                        if(or.isSimpletradeis()){
                            or.setTransaction_type("SELL");
                            or.setOrder_type("LIMIT");
                            or.setPrice(or.getTarget_price());
                            buyStockUpstox(or, userApiMapping.getJwttoken(),userName);
                        }
                        else{
                            or.setTrailstoploss(or.getPrice()-or.getTrigger_price());
                            liveMarketFeed(userApiMapping.getJwttoken(),or);
                        }
                        portfoliowebSocketClient.close();
                    } else if (message.contains("rejected") || message.contains("cancelled")
                        || message.contains("not modified")) {
                        portfoliowebSocketClient.close();
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connection closed: " + code + " - " + reason);
                }

                @Override
                public void onError(Exception e) {
                }
            };

            portfoliowebSocketClient.connect();
        } catch (Exception ignore) {
        }

    }

    public void liveMarketFeed(String upstoxjwtToken, OrderRequest or){

        String websocketurl = getwebSocketUrl(upstoxjwtToken,true);

        try {
            URI serverUri = new URI(websocketurl);
            WebSocketClient webSocketClient = new WebSocketClient(serverUri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    sendSubscriptionRequest(this);
                }

                @Override
                public void onMessage(String message) {
                    // Handle the WebSocket message here
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    super.onMessage(bytes);

                    try {
                        String response = handleBinaryMessage(bytes);
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(response);
                        double currentprice = Double.parseDouble(jsonNode.path("feeds")
                                .path(instrumentkey).path("ff").path("marketFF")
                                .path("ltpc").path("ltp").asText());

                        if(currentprice-or.getPrice()>=or.getTrailstoploss()){
                            updateUpstoxOrder(upstoxjwtToken,or);
                        }

                    } catch (InvalidProtocolBufferException | JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connection closed: " + code + " - " + reason);
                }

                @Override
                public void onError(Exception e) {
                }
            };

            webSocketClient.connect();
        } catch (Exception ignored) {
        }
    }

    private void updateUpstoxOrder(String upstoxJWT,OrderRequest or) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Api-Version","2.0");
        headers.add("Authorization","Bearer "+upstoxJWT);
        headers.add("Content-Type","application/json");
        headers.add("Accept","application/json");

        HashMap<String,Object> map = new HashMap<>();
        map.put("validity",or.getValidity());
        map.put("price",or.getPrice());
        map.put("order_id",buyStockId);
        map.put("order_type",or.getOrder_type());
        map.put("trigger_price",or.getTrigger_price()+or.getTrailstoploss());
        HttpEntity<HashMap<String,Object>> requestEntity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(UpstoxURL.upstoxModifyurl, requestEntity, String.class);
        String responseBody = response.getBody();

        if(responseBody.contains("success")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(responseBody);
                buyStockId = jsonNode.path("data").path("order_id").asText();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getwebSocketUrl(String upstoxjwtToken, boolean isMarketfeed) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Api-Version", "2.0");
        headers.set("Authorization", "Bearer " + upstoxjwtToken);

        RequestEntity<Void> requestEntity;

         if(isMarketfeed) requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(UpstoxURL.upstoxMarketFeedWebSocketurl));
         else requestEntity = new RequestEntity<>(headers,HttpMethod.GET,URI.create(UpstoxURL.upstoxPortfolioWebSocketurl));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseEntity.getBody());
            return jsonNode.path("data").path("authorized_redirect_uri").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
}

    private String handleBinaryMessage(ByteBuffer bytes) throws InvalidProtocolBufferException {

        MarketDataFeed.FeedResponse feedResponse = MarketDataFeed.FeedResponse.parseFrom(bytes.array());
        return JsonFormat.printer().print(feedResponse);

    }

    private void sendSubscriptionRequest(WebSocketClient client) {
        JsonObject requestObject = constructSubscriptionRequest();
        byte[] binaryData = requestObject.toString()
                .getBytes(StandardCharsets.UTF_8);

        System.out.println("Sending: " + requestObject);
        client.send(binaryData);
    }

    private JsonObject constructSubscriptionRequest() {
        JsonObject dataObject = new JsonObject();
        dataObject.addProperty("mode", "full");

        JsonArray instrumentKeys = new Gson().toJsonTree(Collections.singletonList(instrumentkey))
                .getAsJsonArray();
        dataObject.add("instrumentKeys", instrumentKeys);

        JsonObject mainObject = new JsonObject();
        mainObject.addProperty("guid", "someguid");
        mainObject.addProperty("method", "sub");
        mainObject.add("data", dataObject);

        return mainObject;
    }

    private String buyStockUpstox(OrderRequest orderRequest, String token,String username) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Version", "2.0");
        headers.set("Authorization", "Bearer "+token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<OrderRequest> requestEntity = new HttpEntity<>(orderRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(UpstoxURL.upstoxBuyurl, requestEntity, String.class);
        String responseBody = response.getBody();

        String orderId = null;

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrderTime(new Date());
        orderHistory.setOrderType(orderRequest.getTransaction_type());
        orderHistory.setStockSymbol(orderRequest.getInstrument_token());
        orderHistory.setQuantity(orderRequest.getQuantity());
        orderHistory.setPlatform("UPSTOX");
        orderHistory.setUserName(username);

        assert responseBody != null;
        if(responseBody.contains("success")) orderHistory.setOrderStatus("EXECUTED");
        else orderHistory.setOrderStatus("FAILED");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        try {
            jsonNode = objectMapper.readTree(responseBody);
            orderId = jsonNode.path("data").path("order_id").asText();
            orderHistory.setOrderId(orderId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        orderHistoryRepo.save(orderHistory);


        return orderId;

    }

}