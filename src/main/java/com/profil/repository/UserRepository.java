package com.profil.repository;

import com.profil.model.MstUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<MstUser, Long> {
    @Query(value = "select a.* from mst_user a where a.user_id = :user_id",nativeQuery = true)
    MstUser findByUserId(@Param("user_id") Long userId);

    @Query(value = "select a.* from mst_user a where lower(a.user_email)= lower(:userEmail)",nativeQuery = true)
    MstUser findByEmail(@Param("userEmail") String userEmail);

    @Query(value = "select a.* from mst_user a order by a.user_id asc ",nativeQuery = true)
    List<MstUser> findAll();

    public MstUser findByToken(String token);
}
