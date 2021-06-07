package com.example.erlysflexq.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrator {
    private Long id;
    private String account;
    private String password;
    private String roles;
}
