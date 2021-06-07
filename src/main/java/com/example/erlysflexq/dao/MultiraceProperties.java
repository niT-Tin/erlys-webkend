package com.example.erlysflexq.dao;


import com.example.erlysflexq.pojo.Multirace;
import org.springframework.stereotype.Component;
import org.teasoft.bee.getData.SuidRichData;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.List;

@Component
public class MultiraceProperties {
    public static int insert(Multirace multirace){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.insert(multirace);
    }

    public static Multirace  findOne(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.selectById(new Multirace(), id);
    }

    public List<Multirace> findAll(){
        return SuidRichData.selectAll(new Multirace());
    }
    public int delete(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.deleteById(Multirace.class, id);
    }

    public int update(Multirace multirace){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.update(multirace);
    }

}
