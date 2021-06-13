package com.example.erlysflexq.service;


import com.example.erlysflexq.dao.InsertMulti;
import com.example.erlysflexq.dao.MultiraceProperties;
import com.example.erlysflexq.pojo.Multirace;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultiraceService {
    @Autowired
    MultiraceProperties multiraceProperties;

    @Autowired
    InsertMulti insertMulti;

    public static void sort(){
        InsertMulti.sort();
    }

    public int insertMulti(String name, Long[] scores, int sc){
        return insertMulti(name, scores[0],scores[1],scores[2],scores[3],scores[4],scores[5],sc);
    }

    public int insertMulti(
            String name,Long fir,
            Long sec, Long thr, Long fou, Long fif,
            Long six, int sc
    ){
        boolean b = insertMulti.insertMulti(name, fir, sec, thr, fou, fif, six, sc);
        if(b){
            return HttpStatus.SC_OK;
        }else{
            return HttpStatus.SC_BAD_GATEWAY;
        }
    }

    public List<Multirace> findAll(){
        return multiraceProperties.findAll();
    }
    public int update(Multirace multirace){
        return multiraceProperties.update(multirace);
    }

    public Multirace findOne(int id){
        return MultiraceProperties.findOne(id);
    }

    public int delete(int id){
        return multiraceProperties.delete(id);
    }

    public int insert(Multirace multirace){
        return MultiraceProperties.insert(multirace);
    }
}
