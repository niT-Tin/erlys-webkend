package com.example.erlysflexq.service;


import com.example.erlysflexq.dao.UserProperties;
import com.example.erlysflexq.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    @Autowired
    UserProperties userProperties;

    public List<User> findAll(){
        return userProperties.findAll();
    }

    public int update(User user){
        return userProperties.update(user);
    }

    public User findOne(int id){
        return userProperties.findOne(id);
    }

    public int delete(int id){
        return userProperties.delete(id);
    }

    public int insert(User report){
        return userProperties.insert(report);
    }

}
