package com.example.erlysflexq.utils;

import com.example.erlysflexq.pojo.Userinfo;
import org.teasoft.bee.erlys.gets.GetMyWish;
import java.lang.reflect.Field;


/**
 * 合并更新后的Userinfo对象
 */
public class CompareSameInfo {

    public static Userinfo cp(Userinfo update) throws IllegalAccessException {
        Userinfo userinfo = GetMyWish.selectByName(update, update.getName());
        if(userinfo == null)
            return null;

        Field[] fields = userinfo.getClass().getDeclaredFields();
        Field[] fields1 = update.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fields1[i].setAccessible(true);
            if(!((fields[i].get(userinfo)) == null)){
                fields1[i].set(update, fields[i].get(userinfo));
            }
        }

        return userinfo;
    }

}
