package com.example.erlysflexq.controller;

import com.example.erlysflexq.pojo.RqObject;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Logout {

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    @CrossOrigin
    public RqObject logout(){
        Subject subject = SecurityUtils.getSubject();
        RqObject r = new RqObject();
        if(subject != null){
            subject.logout();
            r.setSTATUS(HttpStatus.SC_OK);
        }else
            r.setSTATUS(HttpStatus.SC_BAD_REQUEST);
        return r;
    }

}
