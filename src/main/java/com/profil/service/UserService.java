package com.profil.service;

import com.profil.dto.MstUserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<?> createUser(MstUserDTO requestDTO);

    public ResponseEntity<?> updateUser(MstUserDTO requestDTO);

    public ResponseEntity<?> getUser(String email);

    public ResponseEntity<?> getAll();

    public ResponseEntity<?> deleteUser(Long id);

}
