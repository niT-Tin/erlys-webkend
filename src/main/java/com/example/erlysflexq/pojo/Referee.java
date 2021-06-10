package com.example.erlysflexq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Referee implements Serializable {
    private Long id;
    private String account;
    private String password;
    private String name;
    private String roles;
}
