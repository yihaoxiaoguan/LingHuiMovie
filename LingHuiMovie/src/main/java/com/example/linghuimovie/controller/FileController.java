package com.example.linghuimovie.controller;

import cn.hutool.core.io.FileUtil;
import com.example.linghuimovie.common.AuthAccess;
import com.example.linghuimovie.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/2/27 14:30
 */
@RestController
@RequestMapping("/file")
public class  FileController {
    /**
     * 这一整段代码是关于文件上传的，它将文件上传到项目的files文件夹下，并返回文件的下载链接
     * 首先，获取文件的原始名称、扩展名和主名称
     * 然后，获取项目的存放路径，
     * 然后，定义父级目录的存放路径，
     * 然后，判断当前文件的父级目录是否存在，如果存在，就直接将文件存储到文件夹下，如果不存在，就创建文件的父级目录
     * 然后，判断文件名是否存在，如果存在，就进行文件的重命名，然后将文件存储到文件夹下，如果不存在，就直接将文件存储到文件夹下，
     * 最后，返回文件的下载链接
     */
    @Value("${ip:localhost}")
     String ip;

    @Value("${server.port}")
    String port;

    private static  final String FILE_PATH = System.getProperty("user.dir") + File.separator + "files";
    @AuthAccess
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //这是文件的原始名称
        // 例如上传的是app.png，
        String mainName = FileUtil.mainName(originalFilename) ;  //这是文件的名称（不包括后缀）
        String extName = FileUtil.extName(originalFilename); //这是文件的后缀名
        //那么mainName就是app，extName就是png

        // 判断当前文件的父级目录是否存在
        if (!FileUtil.exist(FILE_PATH)){
            FileUtil.mkdir(FILE_PATH);  //如果当前文件的父级目录不存在，就创建
        }

        if (FileUtil.exist(FILE_PATH + File.separator + originalFilename)){   //如果当前创建的文件已经存在，那么这个时候需要重命名文件名称
            originalFilename = System.currentTimeMillis()+ "_" +mainName + "." + extName;  //这是自定义重命名名称规则

        }
        File saveFile = new  File(FILE_PATH + File.separator + originalFilename);  //  项目路径\files\自定义名称规则.png
        file.transferTo(saveFile); //存储文件到本地的磁盘上去
        String url = " http://"+ ip + ":" + port + "/file/download/"+ originalFilename;  //定义文件的链接
        //将文件的下载链接返回给前端
        return Result.success(url);//返回文件的链接，这个链接就是文件的下载地址，这个下载地址是后台提供的

         }
    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {

        String filePath = FILE_PATH + File.separator + fileName;
        if (!FileUtil.exist(filePath)){
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);//数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();//刷新流
        outputStream.close();//关闭流


    }

}