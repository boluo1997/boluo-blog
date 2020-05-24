package com.boluo.boluoblog.controller;

import com.boluo.boluoblog.service.ImageService;
import com.boluo.boluoblog.utils.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("img")
public class ImageController {

    @Autowired
    private ImageService imageservice;

    //图片上传
    @RequestMapping("uploadImg")
    public PicUploadResult picUp(MultipartFile pic){
        System.out.println(pic);
        return imageservice.picUp(pic);
    }

}