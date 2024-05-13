package com.profil.repository;

import com.profil.model.MstRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<MstRole, Long> {
    @Query(value = "select a.* from mst_role a where a.role_id = :roleId",nativeQuery = true)
    MstRole findByRoleId(@Param("roleId") Long roleId);

    @Query(value = "select a.* from mst_role a where a.role_name = :role_name",nativeQuery = true)
    MstRole findByRoleName(@Param("role_name") String roleName);


    @Query(value = "select a.* from mst_role a order by a.role_id asc ",nativeQuery = true)
    List<MstRole> findAll();
}
