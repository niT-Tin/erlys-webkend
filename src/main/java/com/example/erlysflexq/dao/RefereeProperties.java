package com.example.erlysflexq.dao;


import com.example.erlysflexq.pojo.Userinfo;
import org.springframework.stereotype.Component;
import org.teasoft.bee.getData.SuidRichData;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.List;

@Component
public class RefereeProperties {
    public List<Userinfo> findAll(){
//        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return SuidRichData.selectAll(new Userinfo());
    }


    public int update(Userinfo referee){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
//        Condition
        return suidRich.update(referee);
    }


    public Userinfo findOne(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.selectById(new Userinfo(), id);
//        SuidRich suidRich
    }


    public  int delete(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.deleteById(Userinfo.class, id);
    }


    public int insert(Userinfo referee){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.insert(referee);
    }

}
