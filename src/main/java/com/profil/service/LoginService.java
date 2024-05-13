package com.profil.service;

import com.profil.dto.MstLoginDTO;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    public ResponseEntity<?> login(MstLoginDTO requestDTO);
}
