package com.example.erlysflexq.service;


import com.example.erlysflexq.dao.AdministratorProperties;
import com.example.erlysflexq.pojo.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdministratorService {
    @Autowired
    AdministratorProperties administratorProperties;
    public List<Administrator> findAll(){
        return administratorProperties.findAll();
    }

    public int update(Administrator administrator){
        return administratorProperties.update(administrator);
    }

    public Administrator findOne(int id){
        return administratorProperties.findOne(id);
    }

    public int delete(int id){
        return administratorProperties.delete(id);
    }

    public int insert(Administrator administrator){
        return administratorProperties.insert(administrator);
    }

}
