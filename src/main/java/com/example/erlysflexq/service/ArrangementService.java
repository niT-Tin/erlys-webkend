package com.example.erlysflexq.service;


import com.example.erlysflexq.dao.ArrangementProperties;
import com.example.erlysflexq.pojo.Arrangement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArrangementService {
    @Autowired
    ArrangementProperties arrangementProperties;

    public List<Arrangement> findAll(){
        return arrangementProperties.findAll();
    }
    public int update(Arrangement arrangement){
        return arrangementProperties.update(arrangement);
    }

    public Arrangement findOne(int id){
        return arrangementProperties.findOne(id);
    }

    public int delete(int id){
        return arrangementProperties.delete(id);
    }

    public int insert(Arrangement arrangement){
        return arrangementProperties.insert(arrangement);
    }
}
