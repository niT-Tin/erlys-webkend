package com.example.erlysflexq.service;


import com.example.erlysflexq.dao.UserinfoProperties;
import com.example.erlysflexq.pojo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserinfoService {
    @Autowired
    UserinfoProperties userinfoProperties;

    public List<Userinfo> findAll(){
        return userinfoProperties.findAll();
    }

    public int update(Userinfo userinfo){
        return userinfoProperties.update(userinfo);
    }

    public Userinfo findOne(int id){
        return userinfoProperties.findOne(id);
    }

    public int delete(int id){
        return userinfoProperties.delete(id);
    }

    public int insert(Userinfo userinfo){
        return userinfoProperties.insert(userinfo);
    }
}
