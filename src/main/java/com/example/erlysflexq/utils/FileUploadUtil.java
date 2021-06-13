package com.example.erlysflexq.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileUploadUtil {

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.fileUrl}")
    private String fileUrl;

//    public String localExcelFile()

    //返回导出文件地址
    public String ExportExcel(MultipartFile file, boolean isExport) {
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();  //获取文件名+加个时间戳
        //图片访问URI(即除了协议、地址和端口号的URL)
        String savePath = uploadFolder+fileName;  //图片保存路径
        File saveFile = new File(savePath);
        System.out.println(savePath);
        if (!saveFile.exists()){
            boolean mkdirs = saveFile.mkdirs();
//            System.out.println(mkdirs);
        }
        System.out.println(saveFile.getAbsoluteFile());
        try {
            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //返回图片访问地址

        if(isExport){
            return fileUrl+fileName;
        }else{
            return fileName;
        }
    }
}
