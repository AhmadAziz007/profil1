package com.profil.service.implement;

import com.profil.common.ResponseDTO;
import com.profil.dto.MstUserDTO;
import com.profil.model.MstUser;
import com.profil.repository.UserRepository;
import com.profil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.List;

@Service(value = "userService")
public class UserServiceImplement implements UserService {
    @Autowired
    private UserRepository userRepo;


    @Override
    public ResponseEntity<?> createUser(MstUserDTO requestDTO) {
        ResponseDTO response = new ResponseDTO();
        try {
            MstUser userEntity = new MstUser();
            MstUser user = userRepo.findByEmail(requestDTO.getUserEmail());
            if (user != null) {
                response.setCode("409");
                response.setMessage("Email " + requestDTO.getUserEmail() + " already exists");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            } else if (!isValidEmail(requestDTO.getUserEmail())){
                response.setCode("204");
                response.setMessage("Email tidak valid");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (requestDTO.getRoleId() == null &&
                    requestDTO.getUserName() == null &&
                    requestDTO.getUserEmail() == null &&
                    requestDTO.getPassword() == null &&
                    requestDTO.getAlamat() == null &&
                    requestDTO.getNoHp() == null &&
                    requestDTO.getMotoKerja() == null &&
                    requestDTO.getSignature() == null) {
                response.setCode("204");
                response.setMessage("Field mandatory tidak boleh kosong");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            userEntity.setRoleId(requestDTO.getRoleId());
            userEntity.setUserName(requestDTO.getUserName());
            userEntity.setUserEmail(requestDTO.getUserEmail());
            userEntity.setPassword(passEncript(requestDTO.getPassword()));
            userEntity.setAlamat(requestDTO.getAlamat());
            userEntity.setNoHp(requestDTO.getNoHp());
            userEntity.setMotoKerja(requestDTO.getMotoKerja());
            userEntity.setSignature(requestDTO.getSignature());

            userRepo.save(userEntity);
            response.setCode("200");
            response.setData(userEntity);
            response.setMessage("Master user has been saved successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setCode("500");
            response.setMessage("Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    @Override
    public ResponseEntity<?> updateUser(MstUserDTO requestDTO) {
        ResponseDTO response = new ResponseDTO();
        try {
            MstUser userEntity = new MstUser();
            MstUser user = userRepo.findByEmail(requestDTO.getUserEmail());
            if (user == null) {
                response.setCode("409");
                response.setMessage("Email " + requestDTO.getUserEmail() + " not found");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            if (requestDTO.getUserId() == null ||
                    requestDTO.getRoleId() == null ||
                    requestDTO.getUserName() == null ||
                    requestDTO.getUserEmail() == null ||
                    requestDTO.getPassword() == null ||
                    requestDTO.getAlamat() == null ||
                    requestDTO.getNoHp() == null ||
                    requestDTO.getMotoKerja() == null ||
                    requestDTO.getSignature() == null) {
                response.setCode("204");
                response.setMessage("Field mandatory tidak boleh kosong");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            userEntity.setUserId(requestDTO.getUserId());
            userEntity.setRoleId(requestDTO.getRoleId());
            userEntity.setUserName(requestDTO.getUserName());
            userEntity.setUserEmail(requestDTO.getUserEmail());
            userEntity.setPassword(passEncript(requestDTO.getPassword()));
            userEntity.setAlamat(requestDTO.getAlamat());
            userEntity.setNoHp(requestDTO.getNoHp());
            userEntity.setMotoKerja(requestDTO.getMotoKerja());
            userEntity.setSignature(requestDTO.getSignature());

            userRepo.save(userEntity);
            response.setCode("200");
            response.setData(userEntity);
            response.setMessage("Master user has been saved successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setCode("500");
            response.setMessage("Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getUser(String email) {
        ResponseDTO response = new ResponseDTO();
        email = email.toUpperCase();

        try{
            MstUser mstUser = userRepo.findByEmail(email);
            if (mstUser == null){
                response.setCode("204");
                response.setMessage("Alamat email " + email + " tidak ditemukan");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.setCode("200");
            response.setData(mstUser);
            response.setMessage("Get Data By Master user successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setCode("500");
            response.setMessage("Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        ResponseDTO response = new ResponseDTO();
        List<MstUser> mstUsers = userRepo.findAll();

        response.setCode("200");
        response.setData(mstUsers);
        response.setMessage("Get Data By Master user successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUser(Long id) {
        ResponseDTO response = new ResponseDTO();
        try{
            MstUser mstUser = userRepo.findByUserId(id);
            if (mstUser == null){
                response.setCode("204");
                response.setMessage("id dengan " + id + " tidak ditemukan");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            userRepo.delete(mstUser);
            response.setCode("200");
            response.setData(mstUser);
            response.setMessage("Delete data by Master user successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setCode("500");
            response.setMessage("Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String passEncript(String pass){
        MessageDigest md;
        String passEnc;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            passEnc = s.toString();
        } catch (Exception e) {
            return "Password Encryption Error";
        }
        return passEnc;
    }
}
