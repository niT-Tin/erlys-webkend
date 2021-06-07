package com.example.erlysflexq.dao;


import com.example.erlysflexq.pojo.User;
import org.springframework.stereotype.Component;
import org.teasoft.bee.getData.SuidRichData;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.List;

@Component
public class UserProperties {
    public User findOne(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.selectById(new User(), id);
    }
    public List<User> findAll(){
//        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return SuidRichData.selectAll(new User());
    }


    public int update(User user){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
//        Condition
        return suidRich.update(user);
    }
    public  int delete(int id){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.deleteById(User.class, id);
    }


    public int insert(User user){
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        return suidRich.insert(user);
    }
}
