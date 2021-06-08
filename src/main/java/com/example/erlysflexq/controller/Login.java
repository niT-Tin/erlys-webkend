package com.example.erlysflexq.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Login {

    @PostMapping("/welogin")
    @ApiOperation("手机端登录")
    @CrossOrigin
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

    @PostMapping("/login")
    @ApiOperation("Web端登录")
    @CrossOrigin
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
