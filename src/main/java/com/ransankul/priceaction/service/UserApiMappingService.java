package com.ransankul.priceaction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ransankul.priceaction.model.UserApiMapping;

@Service
public interface UserApiMappingService {

    public List<UserApiMapping> getAllApi(String jwtToken);

    public UserApiMapping addUserApiMapping(UserApiMapping userApiMapping,String jwtToken);

    public int removeUserApiMapping(long id,String jwtToken);

    public UserApiMapping findById(long id);

    public UserApiMapping onOrOff(long id,String jwtToken);

    public UserApiMapping connectOrDis(long id,String jwtToken);

    public void saveJWTTokenAPI(UserApiMapping userApiMapping);

    public List<UserApiMapping> searchApi(String query, String jwtToken);
    public Object[] loadplatformJWTtokenAndWatchlist(String jwtToken);


}
