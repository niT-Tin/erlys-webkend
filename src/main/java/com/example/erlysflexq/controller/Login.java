package com.example.erlysflexq.controller;

import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class Login {

    @RequestMapping("/welogin")
    public int weLogin(String account, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try{
            subject.login(token);
            return HttpStatus.SC_OK;
        }catch(Exception e){
            return HttpStatus.SC_FORBIDDEN;
        }
    }

    @RequestMapping("/login")
    public int webLogin(String account, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try{
            subject.login(token);
            return HttpStatus.SC_OK;
        }catch(Exception e){
            return HttpStatus.SC_FORBIDDEN;
        }
    }
}
