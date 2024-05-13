package com.profil.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class DtoParamPaging {
    private int offset;
    private int limit;
    // field yang akan diurutkan
    private String sort;

    //descending atau ascending
    private String order;
    private Map<String,Object> search;

}
