package com.example.erlysflexq;

import com.example.erlysflexq.pojo.Userinfo;
import com.example.erlysflexq.utils.CompareSameInfo;
import org.apache.commons.collections4.Get;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.teasoft.bee.erlys.gets.GetMyWish;

@SpringBootTest
class ErlysFlexqApplicationTests {

    @Test
    void contextLoads() {
        Userinfo userinfo = GetMyWish.selectByName(new Userinfo(), "chao");
        System.out.println(userinfo);
        Userinfo u = new Userinfo();
        u.setSec(99L);
        u.setName(userinfo.getName());
//        System.out.println(u);
        System.out.println("--------------------------------------------------");
        try {
            Userinfo cp = CompareSameInfo.cp(u);
            System.out.println(cp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
