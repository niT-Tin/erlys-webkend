package com.example.erlysflexq.controller;


import com.example.erlysflexq.pojo.Administrator;
import com.example.erlysflexq.pojo.Referee;
import com.example.erlysflexq.pojo.User;
import com.example.erlysflexq.pojo.Userinfo;
import com.example.erlysflexq.service.AdministratorService;
import com.example.erlysflexq.service.RefereeService;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.teasoft.bee.erlys.gets.GetMyWish;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Bosscontroller {
    @Autowired
    RefereeService refereeService;

    @Autowired
    AdministratorService administratorService;

    @GetMapping("/getallAdministrator")
    @ApiOperation("获取所有信息")
    @CrossOrigin
    public List<Administrator> selectAllAdministrator(){
        return administratorService.findAll();
    }

    @PostMapping("/updateoneAdministrator")
    @ApiOperation("修改单个信息")
    @CrossOrigin
    public int updateOneAdministrator(Administrator administrator){
        int update = administratorService.update(administrator);
        if(update == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }



    @GetMapping("/getoneAdministrator")
    @ApiOperation("获取单个信息")
    @CrossOrigin
    public Administrator findOneAdministrator(Integer id){
        return administratorService.findOne(id);
    }

    @PostMapping("/deleteoneAdministrator")
    @ApiOperation("根据ID删除单个信息")
    @CrossOrigin
    public int deleteByIdAdministrator(Integer id){
        int delete = administratorService.delete(id);
        if(delete == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }

    @RequiresRoles({"admin", "referee"})
    @GetMapping("/selectscorelist")
    @ApiOperation("获取成绩列表降序排列")
    @CrossOrigin
    public List<Userinfo> selectScoreList(){
        return GetMyWish.selectAllAndSortGrade(new Userinfo(), "racescore");
    }

    @PostMapping("/insertoneAdministrator")
    @ApiOperation("插入单个信息")
    @CrossOrigin
    public int insertOneAdministrator(Administrator administrator){
        int insert = administratorService.insert(administrator);
        if(insert == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }

    @RequiresRoles("admin")
    @GetMapping("/getallReferee")
    @ApiOperation("获取所有信息")
    @CrossOrigin
    public List<Referee> selectAllReferee(){
        return refereeService.findAll();
    }

    @RequiresRoles("admin")
    @PostMapping("/updateoneReferee")
    @ApiOperation("修改单个信息")
    @CrossOrigin
    public int updateOneReferee(Referee referee){
        int update = refereeService.update(referee);
        if(update == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }

    @RequiresRoles("admin")
    @GetMapping("/getoneReferee")
    @ApiOperation("获取单个信息")
    @CrossOrigin
    public Referee findOneReferee(Integer id){
        return refereeService.findOne(id);
    }

    @RequiresRoles("admin")
    @PostMapping("/deleteoneReferee")
    @ApiOperation("根据ID删除单个信息")
    @CrossOrigin
    public int deleteByIdReferee(Integer id){
        int delete = refereeService.delete(id);
        if(delete == 1){
            return HttpStatus.SC_OK;
        }else
            return HttpStatus.SC_FORBIDDEN;
    }

    @RequiresRoles("admin")
    @PostMapping("/insertoneReferee")
    @ApiOperation("插入单个信息")
    @CrossOrigin
    public int insertOneReferee(Referee referee) {
        int insert = refereeService.insert(referee);
        if (insert == 1) {
            return HttpStatus.SC_OK;
        } else
            return HttpStatus.SC_FORBIDDEN;
    }
}
