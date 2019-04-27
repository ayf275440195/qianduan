package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.util.Result;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TestController {

    String   fileSpace="D:\\Users\\27544\\前端框架\\demo01\\src\\main\\resources\\static\\vue\\img\\";
    @RequestMapping("/test")
    @ResponseBody
    public Object index(String s){
        System.out.println(s+"11");
        Map<String,Object> map = new HashMap<>();
        map.put("name","tom");
        JSONObject json = (JSONObject) JSON.toJSON(map);
        System.out.println(json);
        return json.toString();
    }

    @RequestMapping("/t1")
    public String t1(){
        return "jquery01";
    }
    @RequestMapping("/t2")
    public String t2(){
        return "jquery02";
    }
    @RequestMapping("/t3")
    public String t3(){
        return "angularjs03";
    }

    @RequestMapping("/t4")
    public String t4(){
        return "test/angularjs04";
    }

    @RequestMapping("/t5/{id}")
    @ResponseBody
    public String t5(@PathVariable Integer id){
        System.out.println("id:" + id);

        return "ok";
    }


    @RequestMapping("/t6")
    public String t6( ){
        System.out.println("id:");

        return "ajax";
    }

    //跨域cookie测试
    @RequestMapping("/cook")
    @ResponseBody
    public String cook(HttpServletResponse response, HttpServletRequest request,String callback){
        System.out.println(callback);
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length>0){
            System.out.println("cookies===========>" +cookies.length);
            for (int i = 0; i < cookies.length; i++) {
                if ("loginInfo".equals(cookies[i].getName())){
                    try {
                        String decode = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
                        System.out.println("userinfo==============》"+decode);
                        JSONObject userInfo = (JSONObject) JSON.parse(decode);
                        return "ok";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return "失败";
    }


    //跨域cookie测试
    @RequestMapping("/upFile")
    @ResponseBody
    public Result upFile(@RequestParam("picture") MultipartFile[] files, HttpServletRequest request, String callback){


        return Result.ok();
    }


    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile[] files, HttpServletResponse response) {
        String id = "ab";
        String uploadPathDB = "/" + id +"/face";
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            if (files!=null && files.length>0){
                String fileName = files[0].getOriginalFilename();
                if (!StringUtils.isEmpty(fileName)){
                    String finalFacePath = fileSpace + uploadPathDB +"/" + fileName;//文件地址和文件名
                    uploadPathDB += "/" + fileName;
                    File outFile = new File(finalFacePath);//创建文件
                    if (outFile.getParent()!= null || !outFile.getParentFile().isDirectory()){
                        outFile.getParentFile().mkdirs();
                    }

                    fos = new FileOutputStream(outFile);
                    is = files[0].getInputStream();
                    IOUtils.copy(is,fos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fos!=null){
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok(uploadPathDB);
    }




}
