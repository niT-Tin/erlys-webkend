package com.example.erlysflexq.controller;

import com.example.erlysflexq.config.shiro.ThreadLocalToken;
import com.example.erlysflexq.pojo.RqObject;
import com.example.erlysflexq.pojo.Userinfo;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.teasoft.bee.erlys.gets.GetMyWish;

@RestController
@RequestMapping("/api")
public class Login {

    @Autowired
    ThreadLocalToken threadLocalToken;

    @PostMapping("/welogin")
    @ApiOperation("手机端登录")
    @CrossOrigin
    public RqObject weLogin(String account, String password) {
        RqObject r = new RqObject();
        Subject subject = SecurityUtils.getSubject();
        Userinfo userinfo = GetMyWish.selectByAccount(new Userinfo(), account);
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        r.setUserInfo(userinfo);
        threadLocalToken.setToken(token.toString());
        try{
            subject.login(token);
            return r;
        }catch(Exception e){
            return null;
        }
    }

    @PostMapping("/login")
    @ApiOperation("Web端登录")
    @CrossOrigin
    public RqObject webLogin(String account, String password) {
        Userinfo userinfo = GetMyWish.selectByAccount(new Userinfo(), account);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        threadLocalToken.setToken(token.toString());
        RqObject obj = new RqObject();
        obj.setUserInfo(userinfo);
        try{
            subject.login(token);
            return obj;
        }catch(Exception e){
            return null;
        }
    }
}
