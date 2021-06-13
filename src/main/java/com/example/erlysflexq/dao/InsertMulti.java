package com.example.erlysflexq.dao;

import com.example.erlysflexq.pojo.Userinfo;
import org.springframework.stereotype.Component;
import org.teasoft.bee.erlys.gets.GetMyWish;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.List;


/**
 * 插入对应的比赛分数
 */
@Component
public class InsertMulti {

    public  boolean insertMulti(String name, Long[] scores, int sc){
       return insertMulti(name, scores[0], scores[1], scores[2],
                scores[3], scores[4], scores[5], sc);
    }

    public  boolean insertMulti(String name,Long fir,
                            Long sec, Long thr, Long fou, Long fif,
                            Long six, int sc){
        Long sum = fir + sec + thr +fou + fif + six;
        Userinfo userinfo = GetMyWish.selectByName(new Userinfo(), name);
        if(userinfo == null)
            return false;
        userinfo.setRacefir(fir);
        userinfo.setRacesec(sec);
        userinfo.setRacethr(thr);
        userinfo.setRacefif(fif);
        userinfo.setRacesix(six);
        userinfo.setRacescore(sum);
        switch(sc){
            case 2:
                userinfo.setSec(sum);
                break;
            case 3:
                userinfo.setThr(sum);
                break;
            case 5:
                userinfo.setFif(sum);
                break;
            case 6:
                userinfo.setWholes(sum);
                break;
            case 7:
                userinfo.setBests(sum);
                break;
        }
        SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
        int update = suidRich.update(userinfo);

        List<Userinfo> racescore = GetMyWish.selectAllAndSortGrade(new Userinfo(), "racescore");
        for (int i = 1; i <= racescore.size(); i++) {
            racescore.get(i).setRanks(Long.parseLong(i+""));
            suidRich.update(racescore.get(i));
        }
        return update == 1;
    }

}
