package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pojo.Department;
import com.pojo.Item;
import com.service.ItemService;
import com.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Autowired
    private ItemService service;


    @RequestMapping("/getItems")
    @ResponseBody
    public Object getItems(){
        List<Item> items = service.getAllItem();
        if (items!=null&&items.size()>0){
            return items;
        }else {
            return "错误";
        }
    }

    @RequestMapping("/chickUserName")
    @ResponseBody
    public Object chickUserName(@RequestBody JSONObject param){
        String username = param.getString("username");
        System.out.println(username);
        if ("tom".equals(username)){
            return "no";
        }
        return "ok";
    }

    @RequestMapping("/htcli")
    @ResponseBody
    public Object htcli(){
        Map map= new HashMap();
        map.put("username","tom");
        map.put("password","123");
        try {
            String s = HttpClientUtil.httpGetRequest("http://94.191.30.241:8080/userlogin", map);
            JSONObject json = (JSONObject) JSONObject.parse(s);
            JSONObject root = json.getJSONObject("root");
            JSONObject body = root.getJSONObject("body");
            Boolean status = body.getBoolean("status");
            if (status){
                System.out.println("登陆成功");
            }else {
                System.out.println("登陆失败");
            }
            System.out.println("返回的数据为："+s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping("/GetDepartment")
    @ResponseBody
    public JSONObject getRes(Integer row ,Integer ye){
//        Integer row = param.getInteger("row");
//        Integer ye = param.getInteger("ye");
//        Integer row = null;
//        Integer ye = null;
        if (row==null||ye==null){
            row = 10;
            ye = 1;
        }
        List<Department> lstRes = new ArrayList();

        for (int i = 0; i <50 ; i++) {
            Department oModel = new Department();
            oModel.setId(i+"");
            oModel.setName("销售部"+i);
            oModel.setLevel(i+"");
            oModel.setDesc("暂无描述信息");
            Item item = new Item(i,"商品"+i,1.1+i,"描述信息","lala","暂不公布");
            oModel.setItem(item);
            lstRes.add(oModel);
        }
        Integer total = lstRes.size();
        Integer page = (ye - 1) * row;// 设置集合截取点
        Integer rows = (ye - 1) * row + row;// 设置集合截取数量
        List<Department> rows1 = lstRes.subList(page,rows);
        JSONObject json = new JSONObject();
        json.put("total",total);
        json.put("rows",rows1);
        return json;
    }
}
