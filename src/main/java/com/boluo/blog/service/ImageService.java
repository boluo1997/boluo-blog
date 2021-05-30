package com.boluo.blog.service;

import com.boluo.blog.utils.PicUploadResult;
import com.boluo.blog.utils.UploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ImageService {

    public PicUploadResult picUp(MultipartFile pic) {

        PicUploadResult result = new PicUploadResult();

        //result 带着两个参数 error 和 url     凭借result.error的状态码来判断是否上传成功

        /*
            1 . pic判断合法,通过后缀 .jpg .png .gif ...
                不合法 error设置成1返回

            2 . 生成一个公用路径
                存储路径 D:/img/upload/.../图片.jpg
                url路径:http://boluo.image.com/upload/.../图片.jpg
            3 . 拼接存储路径,重命名文件,输出图片到文件夹
            4 . 拼接url地址封装到对象数据返回
        */

        String oName = pic.getOriginalFilename();   //获取文件原名称
        String extName = oName.substring(oName.lastIndexOf("."));
        if(!extName.matches(".(jpg|png|gif|jpeg|bmp)$")){
            result.setError(1);
            return result;
        }

        //正常向下执行，后缀合法
        //生成一个公用路径 /upload/2/d/3/d/4/f/5/4/ 使用一个uploadUtils
        /*
            file:String 表示文件名称，图片原名
            upload:String 表示路径前缀 拼接时使用/upload/*** /pic/***

            可以根据指定的文件名称生成一个 /2/d/3/d/4/f/5/4/ 只要文件名称不变
            地址路径结构完全一样，拼接上给定的 upload前缀
        */
        String path = UploadUtil.getUploadPath(oName,"upload");
        //根据公用路径,拼接nginx管理的文件夹
        String dir = "d://img/"+path+"/";

        //在磁盘上生成多级路径,有则用没有则创建
        File _dir = new File(dir);
        if(!_dir.exists()){
            _dir.mkdirs();
        }

        //将图片输出到这个文件夹,重命名文件名称
        String newName = UUID.randomUUID().toString()+extName;
        //通过multipart的api输出到_dir中
        try{
            pic.transferTo(new File(dir+newName));
        }catch(Exception e){
            e.printStackTrace();
            result.setError(1);
            return result;
        }

        //拼接上可以访问这个图片的url路径
        String url = "http://image.boluo.com/"+path+"/"+newName;
        result.setUrl(url);
        return result;
    }
}


