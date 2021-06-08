package com.example.erlysflexq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RqObject {
    int STATUS;
    String token;

    Userinfo userInfo;
    List<Userinfo> userinfoList;

    Arrangement arrangement;
    List<Arrangement> arrangementList;

}
