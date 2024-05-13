package com.profil.service.implement;

import com.profil.common.ResponseDTO;
import com.profil.dto.MstRoleDTO;
import com.profil.model.MstRole;
import com.profil.repository.RoleRepository;
import com.profil.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImplement implements RoleService {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public ResponseEntity<?> createRole(MstRoleDTO requestDTO) {
        ResponseDTO response = new ResponseDTO();
        try {
            MstRole mstRole = new MstRole();
            MstRole role = roleRepo.findByRoleName(requestDTO.getRoleName());
            if (role != null) {
                response.setCode("409");
                response.setMessage("Role Name " + requestDTO.getRoleName() + " already exists");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            if (requestDTO.getRoleName() == null ) {
                response.setCode("204");
                response.setMessage("Field mandatory tidak boleh kosong");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            mstRole.setRoleName(requestDTO.getRoleName());
            roleRepo.save(mstRole);
            response.setCode("200");
            response.setData(mstRole);
            response.setMessage("Master data role  has been saved successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setCode("500");
            response.setMessage("Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateRole(MstRoleDTO requestDTO) {
        ResponseDTO response = new ResponseDTO();
        try {
            MstRole mstRole = new MstRole();
            MstRole role = roleRepo.findByRoleId(requestDTO.getRoleId());
            if (role == null) {
                response.setCode("409");
                response.setMessage("Role Name " + requestDTO.getRoleName() + " not found");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            if (requestDTO.getRoleId() == null ||
                    requestDTO.getRoleName() == null ) {
                response.setCode("204");
                response.setMessage("Field mandatory tidak boleh kosong");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            mstRole.setRoleId(requestDTO.getRoleId());
            mstRole.setRoleName(requestDTO.getRoleName());
            roleRepo.save(mstRole);
            response.setCode("200");
            response.setData(mstRole);
            response.setMessage("Master data role has been saved successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setCode("500");
            response.setMessage("Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getRole(String name) {
        ResponseDTO response = new ResponseDTO();
        name = name.toUpperCase();

        try {
            MstRole role = roleRepo.findByRoleName(name);
            if (role == null) {
                response.setCode("409");
                response.setMessage("Role Name " + name + " not found");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            response.setCode("200");
            response.setData(role);
            response.setMessage("Get Data By Master role successfully");
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
        List<MstRole> mstRoles = roleRepo.findAll();

        response.setCode("200");
        response.setData(mstRoles);
        response.setMessage("Get Data By Master role successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteRole(String name) {
        ResponseDTO response = new ResponseDTO();
        try {
            MstRole role = roleRepo.findByRoleName(name);
            if (role == null) {
                response.setCode("409");
                response.setMessage("Role Name " + name + " not found");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            response.setCode("200");
            response.setData(role);
            response.setMessage("delete Data By Master role successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setCode("500");
            response.setMessage("Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
