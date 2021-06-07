package com.example.erlysflexq.dao;


import com.example.erlysflexq.pojo.Administrator;
import org.springframework.stereotype.Component;
import org.teasoft.bee.getData.SuidRichData;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.List;

@Component
public class AdministratorProperties {
    public static Administrator findOne(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.selectById(new Administrator(), id);
    }
    public List<Administrator> findAll(){
//        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return SuidRichData.selectAll(new Administrator());
    }


    public int update(Administrator administrator){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
//        Condition
        return suidRich.update(administrator);
    }
    public  int delete(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.deleteById(Administrator.class, id);
    }
    public int insert(Administrator administrator){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.insert(administrator);
    }


}
