package com.ransankul.priceaction.serviceImpl;

import java.util.Date;
import java.util.List;

import com.ransankul.priceaction.model.InstrumentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ransankul.priceaction.model.User;
import com.ransankul.priceaction.model.UserApiMapping;
import com.ransankul.priceaction.repositery.UserApiMappingRepo;
import com.ransankul.priceaction.repositery.UserRepo;
import com.ransankul.priceaction.security.JWTTokenHelper;
import com.ransankul.priceaction.service.UserApiMappingService;

@Service
public class UserApiMappingServiceImpl implements UserApiMappingService{

    @Autowired
    private UserApiMappingRepo userApiMappingRepo;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    private UserRepo userRepo;


    @Override
    public UserApiMapping addUserApiMapping(UserApiMapping userApiMapping,String jwtToken) {
        String token = jwtToken.substring(7);
        String userName = jwtTokenHelper.extractUsername(token);
        User user = userRepo.findByEmailId(userName);
        User u = new User();
        u.setUserId(user.getUserId());
        userApiMapping.setUser(u);
        userApiMapping.setLastTokenTime(new Date());

        return userApiMappingRepo.save(userApiMapping);

    }

    @Override
    public int removeUserApiMapping(long id,String jwtToken) {

        String token = jwtToken.substring(7);
        String userName = jwtTokenHelper.extractUsername(token);
        User user = userRepo.findByEmailId(userName);
        UserApiMapping userApiMapping = this.findById(id);

        if(userApiMapping != null && user.getUserId() == userApiMapping.getUser().getUserId()){
            
            if(userApiMapping.isConnectedOrNot() || userApiMapping.isOnOrOff())return 2;
            
            userApiMappingRepo.delete(userApiMapping);
            return 1;
        }

        return 3;
    }

    @Override
    public UserApiMapping findById(long id) {
        return userApiMappingRepo.findById(id).get();
    }

    @Override
    public UserApiMapping onOrOff(long id, String jwtToken) {
        String token = jwtToken.substring(7);
        String userName = jwtTokenHelper.extractUsername(token);
        User user = userRepo.findByEmailId(userName);
        UserApiMapping userApiMapping = this.findById(id);
        if(userApiMapping != null && userApiMapping.getUser().getUserId() == user.getUserId()){
            userApiMapping.setOnOrOff(!userApiMapping.isOnOrOff());
            User u = new User();
            u.setUserId(user.getUserId());
            userApiMapping.setUser(u);
            UserApiMapping addedUserApiMapping = userApiMappingRepo.save(userApiMapping);
            return addedUserApiMapping;
        }
        else{
            return null;
        }
    }

    @Override
    public UserApiMapping connectOrDis(long id, String jwtToken) {

        String token = jwtToken.substring(7);
        String userName = jwtTokenHelper.extractUsername(token);
        User user = userRepo.findByEmailId(userName);

        UserApiMapping userApiMapping = this.findById(id);


        if(userApiMapping != null && userApiMapping.getUser().getUserId() == user.getUserId()){

            if(userApiMapping.isConnectedOrNot()){
                userApiMapping.setConnectedOrNot(false);
                userApiMapping.setJwttoken(null);
                userApiMapping.setLastTokenTime(null);
                User u = new User();
                u.setUserId(user.getUserId());
                userApiMapping.setUser(u);
                return userApiMappingRepo.save(userApiMapping);
            }
        }
        
        return null;
        
    }

    @Override
    public List<UserApiMapping> getAllApi(String jwtToken) {
        String token = jwtToken.substring(7);
        String userName = jwtTokenHelper.extractUsername(token);
        User user = userRepo.findByEmailId(userName);

        List<UserApiMapping> list =  userApiMappingRepo.findByUserUserId(user.getUserId());

        for(UserApiMapping u : list){
            u.setUser(null);
            u.setJwttoken(null);
            u.setJwttoken(null);
       }
       return list;
    }

    @Override
    public void saveJWTTokenAPI(UserApiMapping userApiMapping){
        userApiMappingRepo.save(userApiMapping);
    }

    @Override
    public List<UserApiMapping> searchApi(String query, String jwtToken) {
        String token = jwtToken.substring(7);
        String userName = jwtTokenHelper.extractUsername(token);
        User user = userRepo.findByEmailId(userName);

        List<UserApiMapping> userApiMapping = userApiMappingRepo.findApiMappingsByApiNameAndUser(query,user);

        for(UserApiMapping u : userApiMapping){
            u.setUser(null);
            u.setJwttoken(null);
            u.setApisecret(null);
        }
        
        return userApiMapping;
        
    }

    @Override
    public Object[] loadplatformJWTtokenAndWatchlist(String jwtToken) {
        String token = jwtToken.substring(7);
        String userName = jwtTokenHelper.extractUsername(token);
        User user = userRepo.findByEmailId(userName);

        List<UserApiMapping> list =  userApiMappingRepo.findByUserUserId(user.getUserId());

        if (list.isEmpty()) return null;
        List<String> res = user.getWatchlist().stream().map(InstrumentKey::getUpstoxInstrumentKey).toList();
        List<String> tradingSymbolList = user.getWatchlist().stream().map(InstrumentKey::getTradingSymbol).toList();
        return new Object[]{list.get(0).getJwttoken(),res,tradingSymbolList};
    }


}
