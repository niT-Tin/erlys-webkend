package com.example.erlysflexq.controller;


import com.example.erlysflexq.dao.InsertMulti;
import com.example.erlysflexq.pojo.Administrator;
import com.example.erlysflexq.pojo.RqObject;
import com.example.erlysflexq.pojo.Userinfo;
import com.example.erlysflexq.pojo.Userinfo;
import com.example.erlysflexq.service.AdministratorService;
import com.example.erlysflexq.service.MultiraceService;
import com.example.erlysflexq.service.RefereeService;
import com.example.erlysflexq.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.teasoft.bee.erlys.gets.GetMyWish;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Bosscontroller {

    @Value("${msg.success}")
    private String SUCCESS;

    @Value("${msg.failed}")
    private String FAILED;

    @Autowired
    private JwtUtil jwtUtil;

    SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
//    @Autowired
//    RefereeService refereeService;
//
//    @Autowired
//    AdministratorService administratorService;
//
//
//    final String role1 = "admin";
//
//    @GetMapping("/getallAdministrator")
//    @ApiOperation("获取所有信息")
//    @CrossOrigin
//    @RequiresAuthentication
//    public RqObject selectAllAdministrator(){
//        RqObject r = new RqObject();
//
//        List<Administrator> all = administratorService.findAll();
//
//        return ;
//    }
//
//    @PostMapping("/updateoneAdministrator")
//    @ApiOperation("修改单个信息")
//    @CrossOrigin
//    @RequiresAuthentication
//    public int updateOneAdministrator(Administrator administrator){
//        int update = administratorService.update(administrator);
//        if(update == 1){
//            return HttpStatus.SC_OK;
//        }else
//            return HttpStatus.SC_FORBIDDEN;
//    }
//
//
//
//    @GetMapping("/getoneAdministrator")
//    @ApiOperation("获取单个信息")
//    @CrossOrigin
//    @RequiresAuthentication
//    public Administrator findOneAdministrator(Integer id){
//        return administratorService.findOne(id);
//    }
//
//    @PostMapping("/deleteoneAdministrator")
//    @ApiOperation("根据ID删除单个信息")
//    @CrossOrigin
//    @RequiresAuthentication
//    public int deleteByIdAdministrator(Integer id){
//        int delete = administratorService.delete(id);
//        if(delete == 1){
//            return HttpStatus.SC_OK;
//        }else
//            return HttpStatus.SC_FORBIDDEN;
//    }
//
    @GetMapping("/selectscorelist")
    @ApiOperation("获取成绩列表降序排列")
    @CrossOrigin
//    @RequiresRoles(value = {"admin", "referee"}, logical = Logical.OR)
    public RqObject selectScoreList(@RequestHeader("token") String token){
        RqObject r = new RqObject();
        try{
            jwtUtil.verifyToken(token);
            MultiraceService.sort();
            r.setUserinfoList(GetMyWish.selectAllAndSortGrade(new Userinfo(), "racescore"));
            r.setToken(token);
            r.setSTATUS(HttpStatus.SC_OK);
            r.setMessage(SUCCESS);
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            r.setMessage(FAILED);
        }
        return r;
    }

    @PostMapping("/insertonePlayer")
    @ApiOperation("插入单个队员信息")
    @CrossOrigin
    public RqObject insertOneReferee(@RequestBody RqObject r, @RequestHeader("token") String token) {
        RqObject rq = new RqObject();
        try{
            System.out.println();
            jwtUtil.verifyToken(token);
            suidRich.insert(r.getUserInfo());
            rq.setUserInfo(r.getUserInfo());
            rq.setSTATUS(HttpStatus.SC_OK);
            rq.setMessage(SUCCESS);
        }catch(Exception e){
            rq.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            rq.setMessage(FAILED);
        }
        return rq;
    }
//
//    @PostMapping("/insertoneAdministrator")
//    @ApiOperation("插入单个信息")
//    @CrossOrigin
//    public int insertOneAdministrator(Administrator administrator){
//        int insert = administratorService.insert(administrator);
//        if(insert == 1){
//            return HttpStatus.SC_OK;
//        }else
//            return HttpStatus.SC_FORBIDDEN;
//    }
//
//    @RequiresRoles("admin")
//    @GetMapping("/getallReferee")
//    @ApiOperation("获取所有信息")
//    @CrossOrigin
//    @RequiresAuthentication
//    public List<Userinfo> selectAllReferee(){
//        return refereeService.findAll();
//    }
//
//    @RequiresRoles("admin")
//    @PostMapping("/updateoneReferee")
//    @ApiOperation("修改单个信息")
//    @CrossOrigin
//    @RequiresAuthentication
//    public int updateOneReferee(Userinfo referee){
//        int update = refereeService.update(referee);
//        if(update == 1){
//            return HttpStatus.SC_OK;
//        }else
//            return HttpStatus.SC_FORBIDDEN;
//    }
//
//    @RequiresRoles("admin")
//    @GetMapping("/getoneReferee")
//    @ApiOperation("获取单个信息")
//    @CrossOrigin
//    public Userinfo findOneReferee(Integer id){
//        return refereeService.findOne(id);
//    }
//
//    @RequiresRoles("admin")
//    @PostMapping("/deleteoneReferee")
//    @ApiOperation("根据ID删除单个信息")
//    @CrossOrigin
//    @RequiresAuthentication
//    public int deleteByIdReferee(Integer id){
//        int delete = refereeService.delete(id);
//        if(delete == 1){
//            return HttpStatus.SC_OK;
//        }else
//            return HttpStatus.SC_FORBIDDEN;
//    }
//

}
