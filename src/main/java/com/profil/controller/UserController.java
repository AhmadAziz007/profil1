package com.profil.controller;

import com.profil.dto.MstUserDTO;
import com.profil.service.JwtService;
import com.profil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody MstUserDTO request){
        return userService.createUser(request);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader Map<String,String> header, @RequestBody MstUserDTO request){
        jwtService.filter(header);
        return userService.updateUser(request);
    }

    @GetMapping("/getuser")
    public ResponseEntity<?> getUser(@RequestHeader Map<String,String> header, @RequestBody MstUserDTO request){
        jwtService.filter(header);
        String email = request.getUserEmail();
        return userService.getUser(email);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getall(@RequestHeader Map<String,String> header){
        jwtService.filter(header);
        return userService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader Map<String,String> header, @PathVariable Long id){
        jwtService.filter(header);
        return userService.deleteUser(id);
    }
}
