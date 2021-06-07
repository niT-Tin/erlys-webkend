package com.example.erlysflexq.controller;


import com.example.erlysflexq.dao.UserProperties;
import com.example.erlysflexq.pojo.Arrangement;
import com.example.erlysflexq.pojo.User;
import com.example.erlysflexq.pojo.Userinfo;
import com.example.erlysflexq.service.ArrangementService;
import com.example.erlysflexq.service.UserinfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Admin {
    @Autowired
    UserProperties userService;
    @Autowired
    UserinfoService userinfoService;
    @Autowired
    ArrangementService arrangementService;

    @GetMapping("/getallUser")
    @ApiOperation("获取所有信息")
    @CrossOrigin
    public List<User> selectAllUser(){
        return userService.findAll();
    }

    @RequiresRoles("admin")
    @PostMapping("/setarrangement")
    @ApiOperation("设置赛程")
    @CrossOrigin
    public int setArrangement(List<Arrangement> list){
//        arrangementService.insert()
        int count = 0;
        for (Arrangement arrangement : list) {
            arrangementService.insert(arrangement);
            count++;
        }
        if (count == list.size())
            return HttpStatus.SC_OK;
        else
            return HttpStatus.SC_FORBIDDEN;
    }


    @PostMapping("/deleteoneUser(数据表发生改变)")
    @ApiOperation("根据ID删除单个信息")
    @CrossOrigin
    public int deleteByIdUser(Integer id){
        int delete = userService.delete(id);
        if(delete == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }
    @PostMapping("/insertoneUser(数据表发生改变)")
    @ApiOperation("插入单个信息")
    @CrossOrigin
    public int insertOneUser(User user){
        int insert = userService.insert(user);
        if(insert == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }

    //
    @RequiresRoles({"admin", "referee"})
    @GetMapping("/getallUserinfo")
    @ApiOperation("获取所有信息")
    @CrossOrigin
    public List<Userinfo> selectAllUserinfo(){
        return userinfoService.findAll();
    }

    @RequiresRoles("admin")
    @PostMapping("/updateoneUserinfo")
    @ApiOperation("修改单个信息")
    @CrossOrigin
    public int updateOneUserinfo(Userinfo userinfo){
        int update = userinfoService.update(userinfo);
        if(update == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }

    @GetMapping("/getoneUserinfo")
    @ApiOperation("获取单个信息")
    @CrossOrigin
    public Userinfo findOneUserinfo(Integer id){
        return userinfoService.findOne(id);
    }

    @RequiresRoles("admin")
    @PostMapping("/deleteoneUserinfo")
    @ApiOperation("根据ID删除单个信息")
    @CrossOrigin
    public int deleteByIdUserinfo(Integer id){
        int delete = userinfoService.delete(id);
        if(delete == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }

    @RequiresRoles("admin")
    @PostMapping("/insertoneUserinfo")
    @ApiOperation("插入单个信息")
    @CrossOrigin
    public int insertOneUserinfo(Userinfo Userinfo){
        int insert = userinfoService.insert(Userinfo);
        if(insert == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }
    @PostMapping("/insertmany")
    @ApiOperation("插入n项信息(测试接口)")
    @CrossOrigin
    public int insertMany(List<Arrangement> list){

        int count = 0;
        for (Arrangement arrangement : list) {
            arrangementService.insert(arrangement);
            count++;
        }
        return count;
    }

}
