package com.profil.service.implement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.profil.common.ResponseDTO;
import com.profil.dto.MstLoginDTO;
import com.profil.dto.MstRoleDTO;
import com.profil.dto.MstUserDTO;
import com.profil.model.MstRole;
import com.profil.model.MstUser;
import com.profil.repository.RoleRepository;
import com.profil.repository.UserRepository;
import com.profil.service.LoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;

@Service(value = "loginService")
public class LoginServiceImplement implements LoginService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    private String passEncript(String pass) {
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

    @Override
    public ResponseEntity<?> login(MstLoginDTO requestDTO) {
        ResponseDTO response = new ResponseDTO();
        MstUser user = userRepo.findByEmail(requestDTO.getUserEmail());
        if (user == null){
            response.setCode("401");
            response.setMessage("Invalid email or password");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String pas=passEncript(requestDTO.getPassword());
        if (!pas.equals((user.getPassword()))){
            response.setCode("401");
            response.setMessage("Invalid email or password");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String token = Jwts.builder()
                .setSubject(requestDTO.getPassword())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS512, "SecretKey")
                .compact();

        user.setToken(token);
        userRepo.save(user);

        MstRole role = roleRepo.findByRoleId(user.getRoleId());
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

//        User userJson = gson.fromJson(gson.toJson(user), User.class);
//        Role roleJson = gson.fromJson(gson.toJson(role), Role.class);

        MstUserDTO userJson = new MstUserDTO();
        userJson.setUserId(user.getUserId());
        userJson.setUserEmail(user.getUserEmail());

        MstRoleDTO roleJson = new MstRoleDTO();
        roleJson.setRoleId(role.getRoleId());
        roleJson.setRoleName(role.getRoleName());

        HashMap<String, Object> jsonResponse = new HashMap<>();
//        jsonResponse.put("token", token);
        jsonResponse.put("user", userJson);
//        jsonResponse.put("user", userJson);
        jsonResponse.put("role", roleJson);
//        jsonResponse.put("group", groupJson);

//        HashMap<String,String> jsonResponse = new HashMap<>();
        jsonResponse.put("token",token);
        jsonResponse.put("role_admin",user.getRoleId().toString());

        response.setCode("200");
        response.setData(jsonResponse);
        response.setMessage("success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
