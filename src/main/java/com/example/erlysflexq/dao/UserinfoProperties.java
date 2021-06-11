package com.example.erlysflexq.dao;


import com.example.erlysflexq.pojo.Userinfo;
import org.springframework.stereotype.Component;
import org.teasoft.bee.erlys.gets.GetMyWish;
import org.teasoft.bee.getData.SuidRichData;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.List;

@Component
public class UserinfoProperties {

    SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();

    public List<Userinfo> findAll(){
//        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return SuidRichData.selectAll(new Userinfo());
    }


    public int update(Userinfo userinfo){

//        Condition
        return suidRich.update(userinfo);
    }


    public Userinfo findOne(int id){
        return suidRich.selectById(new Userinfo(), id);
//        SuidRich suidRich
    }

    public Userinfo findByName(String name){
        return GetMyWish.selectByName(new Userinfo(), name);
    }


    public  int delete(int id){
        return suidRich.deleteById(Userinfo.class, id);
    }


    public int insert(Userinfo userinfo){
        return suidRich.insert(userinfo);
    }
}
