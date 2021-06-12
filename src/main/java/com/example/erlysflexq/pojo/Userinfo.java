package com.example.erlysflexq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userinfo implements Serializable {
    private Long id;
    private Long idcard;
    private String address;
    private String school;
    private String coach;
    private String guardian;
    private String rela;
    private String contact;
    private String name;
    private Boolean gender;


    private Long sec;
    private Long thr;
    private Long fif;
    private Long wholes;
    private Long bests;


    private String account;
    private String password;
    private Long  racefir;
    private Long  racesec;
    private Long  racethr;
    private Long  racefou;
    private Long racefif;
    private Long racesix;
    private Long  racescore;
    private Long ranks;
    private String roles;
    private Boolean isdeleted;
}
