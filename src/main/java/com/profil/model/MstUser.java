package com.profil.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_user", schema = "public")
public class MstUser {
    @Id
    @SequenceGenerator(name = "mst_user_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mst_user_seq")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "password")
    private String password;

    @Column(name = "no_hp")
    private Number noHp;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "moto_kerja")
    private String motoKerja;

    @Column(name = "signature")
    private String signature;

    @Column(name = "token")
    private String token;

    public Number getNoHp() {
        return noHp;
    }

    public void setNoHp(Number noHp) {
        this.noHp = noHp;
    }

    public void setNoHp(Integer noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getMotoKerja() {
        return motoKerja;
    }

    public void setMotoKerja(String motoKerja) {
        this.motoKerja = motoKerja;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
