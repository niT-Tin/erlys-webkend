package com.example.erlysflexq.utils;

import com.example.erlysflexq.pojo.Userinfo;
import org.teasoft.bee.erlys.gets.GetMyWish;
import java.lang.reflect.Field;


/**
 * 合并更新后的Userinfo对象
 */
public class CompareSameInfo {

    public static Userinfo cp(Userinfo update) throws IllegalAccessException {
//        Userinfo userinfo = GetMyWish.selectByName(new Userinfo(), update.getName());
//        if(userinfo == null)
//            return null;
//
//        Field[] fields = userinfo.getClass().getDeclaredFields();
//        Field[] fields1 = update.getClass().getDeclaredFields();
//
//        for (int i = 0; i < fields.length; i++) {
//            fields[i].setAccessible(true);
//            fields1[i].setAccessible(true);
//            if((!((fields[i].get(userinfo)) == null)) || (fields[i].get(userinfo) != fields1[i].get(update))){
//                fields1[i].set(update, fields[i].get(userinfo));
//            }else if ((!((fields1[i].get(update)) == null)) || (fields[i].get(userinfo) != fields1[i].get(update)))
//                fields[i].set(userinfo, fields1[i].get(update));
//        }

        //修改直接返回修改后的信息
        System.out.println(update);
        return update;

    }

}
