package com.example.erlysflexq.controller;


import com.example.erlysflexq.pojo.RqObject;
import com.example.erlysflexq.pojo.User;
import com.example.erlysflexq.service.MultiraceService;
import com.example.erlysflexq.service.UserService;
import com.example.erlysflexq.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api")
public class Referees {
    private static final String SUCCESS = "成功";
    private static final String FAILED = "失败";
    @Autowired
    UserService userService;

    @Autowired
    MultiraceService multiraceService;

    @Autowired
    private JwtUtil jwtUtil;

//    @PostMapping("/updateoneUser")
//    @ApiOperation("修改单个信息（数据表发生更改）")
//    @CrossOrigin
//    @RequiresAuthentication
//    public int updateOneUser(User user){
//        int update= userService.update(user);
//        if (update == 1) {
//            return HttpStatus.SC_OK;
//        } else
//            return HttpStatus.SC_FORBIDDEN;
//    }

    @PostMapping("/insertm")
    @ApiOperation("根据名字插入多人赛信息")
    @CrossOrigin
//    @RequiresRoles(value = {"admin", "referee"}, logical = Logical.OR)
    public RqObject insertMultiRace(String name,Long fir,
                               Long sec, Long thr, Long fou, Long fif,
                               Long six, int sc, @RequestHeader("token") String token){
        RqObject r = new RqObject();
        try{
            jwtUtil.verifyToken(token);
            r.setSTATUS(multiraceService.insertMulti(name, fir, sec, thr, fou, fif, six, sc));
            r.setToken(token);
            r.setMessage(SUCCESS);
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            r.setMessage(FAILED);
        }
        return r;
    }

}

