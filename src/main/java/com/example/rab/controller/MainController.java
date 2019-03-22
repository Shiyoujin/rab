package com.example.rab.controller;


import com.example.rab.annotation.Cache;
import com.example.rab.dao.CheckLoginDao;
import com.example.rab.dao.JDBC;
import com.example.rab.dao.SetRoleDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController //组合注解
public class MainController {

    //role 1 ---top
    @GetMapping("/setRole")
    public String setRole(String u_id,String role) {

        //设置角色前，先检验 role表里面 是否存在
        //若不存在 才进行 role 表的插入
        SetRoleDao setRoleDao = new SetRoleDao();
        String result = setRoleDao.setRole(u_id,role);
        return result;
    }

    //role 1,2,3
    @GetMapping("/speak")
    public String speak(String content){
        return "登录后才speak，此内容为："+content;
    }

    //role 1,2
    @GetMapping("/coding")
    public String coding(){
        System.out.println("coding");
        return "programmer和god才可以coding";

    }

    @GetMapping("/login")
    public String Login(String name, String password, HttpSession session){
        if (new CheckLoginDao().checkUser(name,password)){
            String u_id = new JDBC().findU_id(name);
            session.setAttribute("u_id",u_id);
            session.setAttribute("flag","login");
            return "登录成功！";
        }else {
            return "用户或密码错误";
        }
    }

    @GetMapping("/getName")
    @Cache
    public String GetName(String name){
        System.out.println("开始判断 缓存是否存在");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return "获得name为："+ name;
    }
}
