package com.example.erlysflexq.controller;

import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class Logout {

    @RequestMapping("/logout")
    public int logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            subject.logout();
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_BAD_REQUEST;
    }

}
