package com.example.erlysflexq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RqObject {
    private int STATUS;
    private String token;

    private String message;

    private Userinfo userInfo;
    private List<Userinfo> userinfoList;

    private Arrangement arrangement;
    private List<Arrangement> arrangementList;

}
