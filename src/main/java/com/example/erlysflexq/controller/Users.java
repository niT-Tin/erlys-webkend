package com.example.erlysflexq.controller;


import com.example.erlysflexq.pojo.Arrangement;
import com.example.erlysflexq.pojo.Multirace;
import com.example.erlysflexq.pojo.User;
import com.example.erlysflexq.service.ArrangementService;
import com.example.erlysflexq.service.MultiraceService;
import com.example.erlysflexq.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teasoft.bee.getData.SuidRichData;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Users {

    @Autowired
    MultiraceService multiraceService;
    @Autowired
    ArrangementService arrangementService;
    @Autowired
    UserService userService;
    @CrossOrigin
    @GetMapping("/getoneMultirace")
    @ApiOperation("获取单个信息(数据表发生改变)")
    public Multirace findOneMultirace(Integer id){
        return multiraceService.findOne(id);
    }
    @CrossOrigin
    @GetMapping("/getallArrangement")
    @ApiOperation("获取所有信息")
    public List<Arrangement> findAllArrangement(){
//        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return SuidRichData.selectAll(new Arrangement());
    }
    @GetMapping("/getoneUser")
    @ApiOperation("获取单个信息（数据表发生改变）")
    @CrossOrigin
    public User findOneUser(Integer id){
        return  userService.findOne(id);
    }
}
