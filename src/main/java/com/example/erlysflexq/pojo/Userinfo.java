package com.example.erlysflexq.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userinfo implements Serializable {
    @ExcelIgnore
    private Long id;


    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private Boolean gender;
    @ExcelProperty("身份证号")
    private Long idcard;
    @ExcelProperty("地址")
    private String address;
    @ExcelProperty("学校")
    private String school;
    @ExcelProperty("教练")
    private String coach;
    @ExcelProperty("监护人")
    private String guardian;
    @ExcelProperty("与监护人关系")
    private String rela;
    @ExcelProperty("联系方式")
    private String contact;



    @ExcelProperty("双人赛总分")
    private Long sec;
    @ExcelProperty("三人赛总分")
    private Long thr;
    @ExcelProperty("五人赛总分")
    private Long fif;
    @ExcelProperty("全能赛总分")
    private Long wholes;
    @ExcelProperty("精英赛总分")
    private Long bests;


    @ExcelIgnore
    private String account;
    @ExcelIgnore
    private String password;

    @ExcelProperty("第一局")
    private Long  racefir;
    @ExcelProperty("第二局")
    private Long  racesec;
    @ExcelProperty("第三局")
    private Long  racethr;
    @ExcelProperty("第四局")
    private Long  racefou;
    @ExcelProperty("第五局")
    private Long racefif;
    @ExcelProperty("第六局")
    private Long racesix;
    @ExcelProperty("六局总分")
    private Long  racescore;
    @ExcelProperty("排名")
    private Long ranks;
    @ExcelIgnore
    private String roles;
    @ExcelIgnore
    private Boolean isdeleted;
}
