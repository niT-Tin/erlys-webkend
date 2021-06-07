package com.example.erlysflexq.dao;


import com.example.erlysflexq.pojo.Arrangement;
import org.springframework.stereotype.Component;
import org.teasoft.bee.getData.SuidRichData;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.List;

@Component
public class ArrangementProperties {
    public List<Arrangement> findAll(){
//        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return SuidRichData.selectAll(new Arrangement());
    }


    public int update(Arrangement arrangement){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
//        Condition
        return suidRich.update(arrangement);
    }


    public Arrangement findOne(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.selectById(new Arrangement(), id);
//        SuidRich suidRich
    }


    public  int delete(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.deleteById(Arrangement.class, id);
    }


    public int insert(Arrangement arrangement){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.insert(arrangement);
    }
}
