package com.profil.service;

import com.profil.dto.UserJwtDTO;

import java.util.Map;

public interface JwtService {
    public UserJwtDTO filter(Map<String,String> header);
}
