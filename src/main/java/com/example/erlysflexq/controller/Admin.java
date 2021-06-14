package com.example.erlysflexq.controller;


import com.example.erlysflexq.dao.UserProperties;
import com.example.erlysflexq.pojo.Arrangement;
import com.example.erlysflexq.pojo.RqObject;
import com.example.erlysflexq.pojo.Userinfo;
import com.example.erlysflexq.service.ArrangementService;
import com.example.erlysflexq.service.RefereeService;
import com.example.erlysflexq.service.UserinfoService;
import com.example.erlysflexq.utils.CompareSameInfo;
import com.example.erlysflexq.utils.ExcelUtil;
import com.example.erlysflexq.utils.FileUploadUtil;
import com.example.erlysflexq.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.teasoft.bee.ExcelToDatabase.ExcelToDatabase;
import org.teasoft.bee.erlys.gets.GetMyWish;
import org.teasoft.bee.getData.SuidRichData;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class Admin {

    @Value("${role.role2}")
    private String role2;
    @Value("${role.role3}")
    private String role3;

    @Value("${msg.success}")
    private String SUCCESS;

    @Value("${msg.failed}")
    private String FAILED;

    @Value("${file.fileUrl}")
    private String fileUrl;

    @Autowired
    UserProperties userService;
    @Autowired
    UserinfoService userinfoService;
    @Autowired
    ArrangementService arrangementService;
    @Autowired
    RefereeService refereeService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    FileUploadUtil fileUploadUtil;
    @Autowired
    ExcelUtil excelUtil;

    @GetMapping("/getarrangement")
    @ApiOperation("获取赛程信息")
    @CrossOrigin
    public RqObject selectArrangementList(@RequestHeader("token") String token){
        RqObject r = new RqObject();
        try{
            jwtUtil.verifyToken(token);
            List<Arrangement> arrangements = SuidRichData.selectAll(new Arrangement());
            if(arrangements != null && !arrangements.isEmpty()){
                r.setArrangementList(arrangements);
                r.setSTATUS(HttpStatus.SC_OK);
                r.setToken(token);
                r.setMessage(SUCCESS);
            }else{
                r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
                r.setMessage(FAILED);
            }
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            r.setMessage(FAILED);
        }
        return r;
    }



    @PostMapping("/exportg")
    @ApiOperation("返回导出成绩表链接地址")
    @CrossOrigin
    public RqObject exportExcel(@RequestHeader("token") String token){
        RqObject r = new RqObject();

        try{
            jwtUtil.verifyToken(token);
            String fileName = excelUtil.DatabaseToExcel();
//            fileUploadUtil.ExportExcel()
            if(fileName != null){
                r.setSTATUS(HttpStatus.SC_OK);
                r.setToken(token);
                r.setMessage(fileUrl + fileName);
            }else{
                r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
                r.setMessage(FAILED);
            }
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            r.setMessage(FAILED);
        }
        return r;
    }


    @PostMapping("/signs")
    @ApiOperation("报名接口")
    @CrossOrigin
    public RqObject sign(MultipartFile file){
        RqObject r = new RqObject();
        System.out.println(file.getOriginalFilename());
        String[] f = {"身份证号", "地址", "学校", "教练", "监护人", "与监护人关系",
                "联系方式", "姓名", "性别"};
        try {
            String s = fileUploadUtil.ExportExcel(file, false);
            System.out.println(s);
            List<Object> objects = ExcelToDatabase.MyExcelToDatabase(
                    s,
                    f,
                    "idcard,address,school,coach,guardian,rela,con" +
                            "tact,name,gender",
                    0,
                    new Userinfo(),
                    1,
                    100000
            );
            if(objects != null){
                r.setMessage(file.getOriginalFilename());
                r.setSTATUS(HttpStatus.SC_OK);
            }else{
                r.setMessage(FAILED);
                r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            }
        } catch (FileNotFoundException e) {
            r.setMessage(FAILED);
            r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            e.printStackTrace();
        }
        return r;
    }


    @PostMapping("/updateplayerinfo")
    @ApiOperation("更新运动员信息")
    @CrossOrigin
    public RqObject updateUserInfo(@RequestBody RqObject rq,@RequestHeader("token") String token){
        RqObject r = new RqObject();
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        try{
            jwtUtil.verifyToken(token);
            System.out.println(rq);
            suidRich.update(CompareSameInfo.cp(rq.getUserInfo()));
            r.setSTATUS(HttpStatus.SC_OK);
            r.setToken(token);
            r.setMessage(SUCCESS);
            r.setUserInfo(rq.getUserInfo());
            return r;
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_BAD_REQUEST);
            r.setMessage(FAILED);
            return r;
        }
    }


    @GetMapping("/getallplayerinfo")
    @ApiOperation("获取所有运动员信息")
    @CrossOrigin
//    @RequiresRoles(value = {"admin", "referee"}, logical = Logical.OR)
    public RqObject selectAllPlayerInfo(@RequestHeader("token") String token){
        RqObject r = new RqObject();
        try{
            jwtUtil.verifyToken(token);
            List<Userinfo> all = userinfoService.findAll();
            List<Userinfo> collect = all.stream()
                    .filter(userinfo -> userinfo.getRoles().equals(role3) && userinfo.getIsdeleted())
                    .collect(Collectors.toList());
            r.setToken(token);
            r.setUserinfoList(collect);
            r.setSTATUS(HttpStatus.SC_OK);
            r.setMessage(SUCCESS);
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            r.setMessage(FAILED);
        }
        return r;
    }

    @GetMapping("/getallrefereeinfo")
    @ApiOperation("获取所有裁判信息")
    @CrossOrigin
    public RqObject selectAllRefereeInfo(@RequestHeader("token") String token){
        RqObject r = new RqObject();
        try{
            jwtUtil.verifyToken(token);
            List<Userinfo> infos = refereeService.findAll();
            List<Userinfo> collect = infos.stream()
                    .filter(userinfo -> userinfo.getRoles().equals(role2) && userinfo.getIsdeleted())
                    .collect(Collectors.toList());

            r.setUserinfoList(collect);
            r.setSTATUS(HttpStatus.SC_OK);
            r.setToken(token);
            r.setMessage(SUCCESS);
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            r.setMessage(FAILED);
        }
        return r;
    }


    @PostMapping("/setarrangement")
    @ApiOperation("设置赛程")
    @CrossOrigin
    public RqObject setArrangement(@RequestBody RqObject r, @RequestHeader("token") String token){
        RqObject rq = new RqObject();
        try{
            List<Arrangement> list = r.getArrangementList();
            System.out.println(Arrays.toString(list.toArray()));
            jwtUtil.verifyToken(token);
            int count = 0;
            for (Arrangement arrangement : list) {
                arrangementService.insert(arrangement);
                count++;
            }

            if (count == list.size()){
                rq.setSTATUS(HttpStatus.SC_OK);
                rq.setToken(token);
                rq.setMessage(SUCCESS);
            }
            else{
                rq.setSTATUS(HttpStatus.SC_FORBIDDEN);
                rq.setMessage(FAILED);
            }
        }catch(Exception e){
            rq.setSTATUS(HttpStatus.SC_FORBIDDEN);
            rq.setMessage(FAILED);
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

    @PostMapping("/foneuserinfobyname")
    @ApiOperation("获取单个信息")
    @CrossOrigin
//    @RequiresAuthentication
    public RqObject findOneUserinfoByName(@RequestBody String name, @RequestHeader("token") String token){
        RqObject value = new RqObject();
        try{
            jwtUtil.verifyToken(token);
            value.setUserInfo(userinfoService.findByName(name));
            value.setToken(token);
            value.setSTATUS(HttpStatus.SC_OK);
            value.setMessage(SUCCESS);
        }catch(Exception e){
            value.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            value.setMessage(FAILED);
        }
        return value;
    }

    @PostMapping("/getoneUserinfo")
    @ApiOperation("获取单个信息")
    @CrossOrigin
//    @RequiresAuthentication
    public RqObject findOneUserinfo(Integer id, @RequestHeader("token") String token){
        RqObject value = new RqObject();
        try{
            jwtUtil.verifyToken(token);
            value.setUserInfo(userinfoService.findOne(id));
            value.setToken(token);
            value.setSTATUS(HttpStatus.SC_OK);
            value.setMessage(SUCCESS);
        }catch(Exception e){
            value.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            value.setMessage(FAILED);
        }
        return value;
    }

    @PostMapping("/deleteoneUserinfo")
    @ApiOperation("根据姓名删除单个信息")
    @CrossOrigin
    public RqObject deleteByIdUserinfo(@RequestBody String name, @RequestHeader("token") String token){
        RqObject r = new RqObject();
        try{
            jwtUtil.verifyToken(token);
            Userinfo userinfo = GetMyWish.selectByName(new Userinfo(), name);
            if(userinfo != null){
                SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
                userinfo.setIsdeleted(false);
                suidRich.update(userinfo);
                r.setUserInfo(userinfo);
                r.setSTATUS(HttpStatus.SC_OK);
                r.setToken(token);
                r.setMessage(SUCCESS);
            }else{
                r.setSTATUS(HttpStatus.SC_FORBIDDEN);
                r.setMessage(FAILED);
            }
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_UNAUTHORIZED);
            r.setMessage(FAILED);
        }
        return r;
    }

    @PostMapping("/insertoneUserinfo")
    @ApiOperation("插入单个信息")
    @CrossOrigin
    public RqObject insertOneUserinfo(@RequestBody RqObject r, @RequestHeader("token") String token){
        try{
            jwtUtil.verifyToken(token);
            int insert = userinfoService.insert(r.getUserInfo());
            if(insert == 1){
                r.setSTATUS(HttpStatus.SC_OK);
                r.setToken(token);
                r.setMessage(SUCCESS);
            }else{
                r.setSTATUS(HttpStatus.SC_FORBIDDEN);
                r.setMessage(FAILED);
            }
        }catch(Exception e){
            r.setSTATUS(HttpStatus.SC_FORBIDDEN);
            r.setMessage(FAILED);
        }
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
