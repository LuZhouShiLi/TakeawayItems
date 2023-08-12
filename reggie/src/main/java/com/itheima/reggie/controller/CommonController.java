package com.itheima.reggie.controller;


import com.ustc.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    // 取出路径  文件上传路径 配置到yml文件中  使用value 注解 从yml 配置文件中 取出文件配置  然后赋值给basePath
    @Value("${reggie.path")
    private String basePath;

    // 上传文件 响应接口  将上传的文件封装成MultipartFile
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        // file是一个临时文件 需要转存到 指定位置 否则本次请求完成之后临时文件会删除
        log.info(file.toString());
        // 使用原始文件名

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用UUID重新生成文件名  放置文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        // 创建文件目录对象
        File dir = new File(basePath);// 取出yml 文件中配置的路径

        // 判断当前目录是否存在  因为 yml 配置文件中的路径可能不存在
        if(!dir.exists()){
            // 目录不存在 需要创建
            dir.mkdirs();
        }

        // 将临时文件转存到指定位置
        file.transferTo(new File(basePath+ fileName));
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {

//         使用输入流读取指定文件的内容
        FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

        // 输出流  通过输出流将文件协会服务器  在浏览器展示图片

        ServletOutputStream outputStream = response.getOutputStream();

        response.setContentType("/image/png");

        int len  = 0;
        byte[] bytes = new byte[1024];// 每次读取1024个字节
        while((len = fileInputStream.read(bytes)) != -1){

//           想输出流中写入长度为1024个字节
            outputStream.write(bytes,0,len);

            outputStream.flush();// 清除缓存
        }

    }


}
