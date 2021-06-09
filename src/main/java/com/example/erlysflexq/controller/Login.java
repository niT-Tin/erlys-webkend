package com.example.erlysflexq.controller;

import com.example.erlysflexq.config.shiro.ThreadLocalToken;
import com.example.erlysflexq.config.shiro.Token;
import com.example.erlysflexq.pojo.RqObject;
import com.example.erlysflexq.pojo.Userinfo;
import com.example.erlysflexq.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.teasoft.bee.erlys.gets.GetMyWish;

@RestController
@RequestMapping("/api")
public class Login {

    private static final String SUCCESS = "成功";
    private static final String FAILED = "失败";
    @Autowired
    ThreadLocalToken threadLocalToken;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    @ApiOperation("登录接口")
    @CrossOrigin
    public RqObject weLogin(String account, String password) {
        Userinfo userinfo = GetMyWish.selectByAccount(new Userinfo(), account);
        RqObject r = new RqObject();
        if(userinfo.getPassword().equals(password)){
            String token = jwtUtil.generateToken(userinfo.getId());
            r.setToken(token);
            r.setSTATUS(HttpStatus.SC_OK);
            r.setUserInfo(userinfo);
            r.setMessage(SUCCESS);
            Subject subject = SecurityUtils.getSubject();
            subject.login(new Token(token));
        }else{
            r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            r.setMessage(FAILED);
            r.setMessage("密码错误");
        }
        return r;
    }

//    @PostMapping("/login")
//    @ApiOperation("Web端登录")
//    @CrossOrigin
//    public RqObject webLogin(String account, String password) {
//        Userinfo userinfo = GetMyWish.selectByAccount(new Userinfo(), account);
//        Subject subject = SecurityUtils.getSubject();
////        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
//        String s = jwtUtil.generateToken(userinfo.getId());
//        Token t = new Token(s);
//        RqObject obj = new RqObject();
//        obj.setUserInfo(userinfo);
////        System.out.println(s);
////        try{
//            subject.login(t);
//            obj.setToken(s+"");
//            return obj;
////        }catch(Exception e){
////            return null;
////        }
//    }
}
