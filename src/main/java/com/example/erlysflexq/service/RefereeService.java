package com.example.erlysflexq.service;


import com.example.erlysflexq.dao.RefereeProperties;
import com.example.erlysflexq.pojo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RefereeService {
    @Autowired
    RefereeProperties refereeProperties;

    public List<Userinfo> findAll(){
        return refereeProperties.findAll();
    }

    public int update(Userinfo referee){
        return refereeProperties.update(referee);
    }

    public Userinfo findOne(int id){
        return refereeProperties.findOne(id);
    }

    public int delete(int id){
        return refereeProperties.delete(id);
    }

    public int insert(Userinfo referee){
        return refereeProperties.insert(referee);
    }

}
