package com.abelium.inatrace.components.user.mappers;

import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.common.User;

public class UserMapper {

    public static ApiUser toSimpleApiUser(User entity){
        if(entity == null) return null;
        ApiUser apiUser = new ApiUser();
        apiUser.setId(entity.getId());
        apiUser.setName(entity.getName());
        apiUser.setSurname(entity.getSurname());
        return apiUser;
    }


    public static ApiUser toApiUser(User entity){
        if(entity == null) return null;
        ApiUser apiUser = new ApiUser();
        apiUser.setId(entity.getId());
        apiUser.setName(entity.getName());
        apiUser.setSurname(entity.getSurname());
        apiUser.setEmail(entity.getEmail());
        apiUser.setLanguage(entity.getLanguage());
        apiUser.setStatus(entity.getStatus());
        apiUser.setRole(entity.getRole());
        return apiUser;
    }

}
