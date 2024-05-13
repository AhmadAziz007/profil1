package com.profil.service.implement;

import com.profil.dto.RoleJwtDTO;
import com.profil.dto.UserJwtDTO;
import com.profil.exception.JwtException;
import com.profil.exception.NotFoundRole;
import com.profil.model.MstRole;
import com.profil.model.MstUser;
import com.profil.repository.RoleRepository;
import com.profil.repository.UserRepository;
import com.profil.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static io.vertx.core.http.impl.HttpClientConnection.log;

@Slf4j
@Service(value = "jwtService")
public class JwtServiceImplement implements JwtService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

        public UserJwtDTO filter(Map<String,String> header){

            String bearer = header.get("authorization");
            String split[] = bearer.split(" ");
            bearer = split[1];
            log.info(bearer);

            MstUser user = userRepo.findByToken(bearer);
            if(user == null){
                throw new JwtException();
            }

            Long idrole = user.getRoleId();
            Optional<MstRole> u = roleRepo.findById(idrole);
            if(!u.isPresent()){
                throw new NotFoundRole();
            }

            RoleJwtDTO role = new RoleJwtDTO();
            role.setRoleName(u.get().getRoleName());
            role.setRoleId(u.get().getRoleId());

            UserJwtDTO rt = new UserJwtDTO();
            rt.setUserId(user.getUserId());
            rt.setUserEmail(user.getUserEmail());
            rt.setRoleId(user.getRoleId());
            rt.setJwtDTO(role);

            log.info(String.format("%s", rt));

            return rt;
        }

        private String getBearerToken(Map<String,String> bearer){
            return bearer.get("Authorization");
        }
    }

