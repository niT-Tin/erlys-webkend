package com.example.erlysflexq.service;


import com.example.erlysflexq.dao.AdministratorProperties;
import com.example.erlysflexq.pojo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdministratorService {
    @Autowired
    AdministratorProperties administratorProperties;
    public List<Userinfo> findAll(){
        return administratorProperties.findAll();
    }

    public int update(Userinfo administrator){
        return administratorProperties.update(administrator);
    }

    public Userinfo findOne(int id){
        return administratorProperties.findOne(id);
    }

    public int delete(int id){
        return administratorProperties.delete(id);
    }

    public int insert(Userinfo administrator){
        return administratorProperties.insert(administrator);
    }

}
