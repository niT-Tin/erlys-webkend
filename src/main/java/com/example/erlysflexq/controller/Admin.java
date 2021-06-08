package com.example.erlysflexq.controller;


import cn.hutool.system.UserInfo;
import com.example.erlysflexq.dao.UserProperties;
import com.example.erlysflexq.pojo.Arrangement;
import com.example.erlysflexq.pojo.RqObject;
import com.example.erlysflexq.pojo.User;
import com.example.erlysflexq.pojo.Userinfo;
import com.example.erlysflexq.service.ArrangementService;
import com.example.erlysflexq.service.RefereeService;
import com.example.erlysflexq.service.UserinfoService;
import com.example.erlysflexq.utils.CompareSameInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.teasoft.bee.erlys.gets.GetMyWish;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class Admin {

    final String role2 = "referee";
    final String role3 = "player";

    @Autowired
    UserProperties userService;
    @Autowired
    UserinfoService userinfoService;
    @Autowired
    ArrangementService arrangementService;
    @Autowired
    RefereeService refereeService;


    @GetMapping("/updateplayerinfo")
    @ApiOperation("更新运动员信息")
    @CrossOrigin
    @RequiresAuthentication
    @RequiresRoles({"admin","referee"})
    public RqObject updateUserInfo(RqObject rq){
        RqObject r = new RqObject();
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        try{
            suidRich.update(CompareSameInfo.cp(rq.getUserInfo()));
            r.setSTATUS(HttpStatus.SC_OK);
            return r;
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_BAD_REQUEST);
            return r;
        }
    }


    @GetMapping("/getallplayerinfo")
    @ApiOperation("获取所有运动员信息")
    @CrossOrigin
    @RequiresAuthentication
    @RequiresRoles({"admin","referee"})
    public RqObject selectAllPlayerInfo(){
        List<Userinfo> all = userinfoService.findAll();
        List<Userinfo> collect = all.stream()
                .filter(userinfo -> userinfo.getRoles().equals(role3))
                .collect(Collectors.toList());
        RqObject r = new RqObject();
        r.setUserinfoList(collect);
        return r;
    }

    @GetMapping("/getallrefereeinfo")
    @ApiOperation("获取所有裁判信息")
    @CrossOrigin
    @RequiresAuthentication
    @RequiresRoles("admin")
    public RqObject selectAllRefereeInfo(){
        List<Userinfo> infos = refereeService.findAll();
        List<Userinfo> collect = infos.stream()
                .filter(userinfo -> userinfo.getRoles().equals(role2))
                .collect(Collectors.toList());
        RqObject r = new RqObject();
        r.setUserinfoList(collect);
        return r;
    }

//    @GetMapping("/getallUser")
//    @ApiOperation("获取所有信息")
//    @CrossOrigin
//    public List<User> selectAllUser(){
//        return userService.findAll();
//    }

    @RequiresRoles("admin")
    @PostMapping("/setarrangement")
    @ApiOperation("设置赛程")
    @CrossOrigin
    public RqObject setArrangement(RqObject r){
        List<Arrangement> list = r.getArrangementList();
        RqObject rq = new RqObject();
//        arrangementService.insert()
        int count = 0;
        for (Arrangement arrangement : list) {
            arrangementService.insert(arrangement);
            count++;
        }
        if (count == list.size()){
            rq.setSTATUS(HttpStatus.SC_OK);
        }
        else{
            rq.setSTATUS(HttpStatus.SC_FORBIDDEN);
        }
        return rq;

    }


//    @PostMapping("/deleteoneUser(数据表发生改变)")
//    @ApiOperation("根据ID删除单个信息")
//    @CrossOrigin
//    public int deleteByIdUser(Integer id){
//        int delete = userService.delete(id);
//        if(delete == 1){
//            return HttpStatus.SC_OK;
//        }else{
//            return HttpStatus.SC_FORBIDDEN;
//        }
//
//    }
//    @PostMapping("/insertoneUser(数据表发生改变)")
//    @ApiOperation("插入单个信息")
//    @CrossOrigin
//    public int insertOneUser(User user){
//        int insert = userService.insert(user);
//        if(insert == 1){
//            return HttpStatus.SC_OK;
//        }else
//            return HttpStatus.SC_FORBIDDEN;
//    }

    //
//    @RequiresRoles({"admin", "referee"})
//    @GetMapping("/getallUserinfo")
//    @ApiOperation("获取所有信息")
//    @CrossOrigin
//    public List<Userinfo> selectAllUserinfo(){
//        return userinfoService.findAll();
//    }

//    @RequiresRoles("admin")
//    @PostMapping("/updateoneUserinfo")
//    @ApiOperation("修改单个信息")
//    @CrossOrigin
//    public int updateOneUserinfo(Userinfo userinfo){
//        int update = userinfoService.update(userinfo);
//        if(update == 1){
//            return HttpStatus.SC_OK;
//        }else
//            return HttpStatus.SC_FORBIDDEN;
//    }

    @GetMapping("/getoneUserinfo")
    @ApiOperation("获取单个信息")
    @CrossOrigin
    @RequiresAuthentication
    public RqObject findOneUserinfo(Integer id){
        RqObject value = new RqObject();
        value.setUserInfo(userinfoService.findOne(id));
        return value;
    }

    @RequiresRoles("admin")
    @PostMapping("/deleteoneUserinfo")
    @ApiOperation("根据ID删除单个信息")
    @CrossOrigin
    public RqObject deleteByIdUserinfo(Integer id){
        int delete = userinfoService.delete(id);
        RqObject r = new RqObject();
        if(delete == 1){
            r.setSTATUS(HttpStatus.SC_OK);
        }else{
            r.setSTATUS(HttpStatus.SC_FORBIDDEN);
        }
        return r;
    }

    @RequiresRoles("admin")
    @PostMapping("/insertoneUserinfo")
    @ApiOperation("插入单个信息")
    @CrossOrigin
    public RqObject insertOneUserinfo(RqObject r){
        int insert = userinfoService.insert(r.getUserInfo());
        if(insert == 1){
            r.setSTATUS(HttpStatus.SC_OK);
        }else
            r.setSTATUS(HttpStatus.SC_FORBIDDEN);
        return r;
    }
    @PostMapping("/insertmany")
    @ApiOperation("插入n项信息(测试接口)")
    @CrossOrigin
    public RqObject insertMany(RqObject rqObject){

        List<Arrangement> list = rqObject.getArrangementList();
        int count = 0;
        for (Arrangement arrangement : list) {
            arrangementService.insert(arrangement);
            count++;
        }
        rqObject.setSTATUS(count);
        return rqObject;
    }

}
