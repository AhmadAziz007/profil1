package com.profil.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserJwtDTO {
    private Long userId;
    private Long roleId;
    private String userEmail;
    private RoleJwtDTO jwtDTO;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public RoleJwtDTO getJwtDTO() {
        return jwtDTO;
    }

    public void setJwtDTO(RoleJwtDTO jwtDTO) {
        this.jwtDTO = jwtDTO;
    }
}
