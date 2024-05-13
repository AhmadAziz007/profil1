package com.profil.controller;

import com.profil.dto.MstRoleDTO;
import com.profil.service.JwtService;
import com.profil.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/save")
    public ResponseEntity<?> createRole(@RequestBody MstRoleDTO request){
        return roleService.createRole(request);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRole(@RequestHeader Map<String,String> header, @RequestBody MstRoleDTO request){
        jwtService.filter(header);
        return roleService.updateRole(request);
    }

    @GetMapping("/getrole/{name}")
    public ResponseEntity<?> getRole(@RequestHeader Map<String,String> header, @PathVariable String name){
        jwtService.filter(header);
        return roleService.getRole(name);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getall(@RequestHeader Map<String,String> header){
        jwtService.filter(header);
        return roleService.getAll();
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteRole(@RequestHeader Map<String,String> header,@PathVariable String name){
        jwtService.filter(header);
        return roleService.deleteRole(name);
    }

}
