package com.ransankul.priceaction.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ransankul.priceaction.model.User;
import com.ransankul.priceaction.model.UserApiMapping;

@Repository
public interface UserApiMappingRepo extends JpaRepository<UserApiMapping,Long> {

    List<UserApiMapping> findByUserUserId(long userId);
    UserApiMapping findByUserAndPlatform(User user, String platform);
    
    @Query("SELECT uam FROM UserApiMapping uam WHERE uam.apiname LIKE %:apiname% AND uam.user = :user")
    List<UserApiMapping> findApiMappingsByApiNameAndUser(
        @Param("apiname") String apiname,
        @Param("user") User user
    );

    
}
