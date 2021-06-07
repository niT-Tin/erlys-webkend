package com.example.erlysflexq.service;


import com.example.erlysflexq.dao.RefereeProperties;
import com.example.erlysflexq.pojo.Referee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RefereeService {
    @Autowired
    RefereeProperties refereeProperties;

    public List<Referee> findAll(){
        return refereeProperties.findAll();
    }

    public int update(Referee referee){
        return refereeProperties.update(referee);
    }

    public Referee findOne(int id){
        return refereeProperties.findOne(id);
    }

    public int delete(int id){
        return refereeProperties.delete(id);
    }

    public int insert(Referee referee){
        return refereeProperties.insert(referee);
    }

}
