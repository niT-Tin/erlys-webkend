package com.example.erlysflexq.utils;

import com.alibaba.excel.EasyExcel;
import com.example.erlysflexq.pojo.Userinfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.teasoft.bee.getData.SuidRichData;

import java.util.List;

@Component
public class ExcelUtil {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

//    @Value("${role.role2}")
//    private String role2;

    @Value("${role.role3}")
    private String role3;

    public String DatabaseToExcel(){
        String fileName = System.currentTimeMillis() + "Grade.xlsx";
        try{
            List<Userinfo> userinfos = SuidRichData.selectAll(new Userinfo());

            EasyExcel.write(fileName, Userinfo.class).sheet("Sheet1").doWrite(SuidRichData.selectAll(new Userinfo()));
            String[] cmd = {"mv", fileName, uploadFolder+fileName};
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            p.destroy();
            return fileName;
        }catch(Exception e){
            return null;
        }
    }
}
