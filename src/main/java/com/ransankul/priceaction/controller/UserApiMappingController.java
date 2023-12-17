package com.ransankul.priceaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ransankul.priceaction.model.UserApiMapping;
import com.ransankul.priceaction.service.UserApiMappingService;


@RestController
@RequestMapping("/api/userApiMappings")
public class UserApiMappingController {

    @Autowired
    private UserApiMappingService userApiMappingService;

    @PostMapping("/")
    public ResponseEntity<?> getAllApi(@RequestHeader("Authorization") String jwtToken){

        List<UserApiMapping> list = userApiMappingService.getAllApi(jwtToken);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<?> addUserApiMapping(@RequestBody UserApiMapping userApiMapping,@RequestHeader("Authorization") String jwtToken) {

        UserApiMapping addedUserApiMapping = userApiMappingService.addUserApiMapping(userApiMapping,jwtToken);
        if(addedUserApiMapping == null) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(addedUserApiMapping, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeUserApiMapping(@PathVariable long id,@RequestHeader("Authorization") String jwtToken) {


        int b = userApiMappingService.removeUserApiMapping(id,jwtToken);
        if(b == 1){
            return new ResponseEntity<>("API removed succesfully", HttpStatus.OK);
        }else if(b == 2){
            return new ResponseEntity<>("Please Deactivate or Disconnect API before remove", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("API not found", HttpStatus.OK);
        }

    }

    @PostMapping("/onoff/{id}")
    public ResponseEntity<?> onOrOff(@PathVariable long id,@RequestHeader("Authorization") String jwtToken) {
        

        UserApiMapping userApiMapping = userApiMappingService.onOrOff(id, jwtToken);
        if(userApiMapping != null) return new ResponseEntity<>(userApiMapping, HttpStatus.OK);
        else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/connectOrDis/{id}")
    public ResponseEntity<?> connectOrDis(@PathVariable long id,@RequestHeader("Authorization") String jwtToken) {
        


        UserApiMapping userApiMapping = userApiMappingService.connectOrDis(id, jwtToken);
        if(userApiMapping != null) return new ResponseEntity<>(userApiMapping, HttpStatus.OK);
        else return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchApi(@RequestParam String q,@RequestHeader("Authorization") String jwtToken){

        List<UserApiMapping> list = userApiMappingService.searchApi(q, jwtToken);

        if(list == null || list.isEmpty()) return new ResponseEntity<>("Nothing Found",HttpStatus.OK);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/platformJwtToken")
    public ResponseEntity<?> loadJWTTokenAndWatchlist(@RequestHeader("Authorization") String jwtToken){

        Object[] platformjwTtokenAndWatchlist = userApiMappingService.loadplatformJWTtokenAndWatchlist(jwtToken);

        if(platformjwTtokenAndWatchlist == null || platformjwTtokenAndWatchlist.length == 0) return new ResponseEntity<>("Connect an API",HttpStatus.OK);
        return new ResponseEntity<>(platformjwTtokenAndWatchlist,HttpStatus.OK);
    }
}
