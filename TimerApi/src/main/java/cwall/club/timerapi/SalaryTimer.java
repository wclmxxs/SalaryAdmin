package cwall.club.timerapi;

import cwall.club.common.Exception.SalaryException;
import cwall.club.core.Service.SalaryService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Configuration
public class SalaryTimer {
 //   private static Set<Integer> hasHour = new HashSet<>();
 //   private static Set<Integer> has = new HashSet<>();
    static HashMap<Integer,Integer> timL = new HashMap<Integer,Integer>(){
     {
         put(1,31);
         put(2,28);
         put(3,31);
         put(4,30);
         put(5,31);
         put(6,30);
         put(7,31);
         put(8,31);
         put(9,30);
         put(10,31);
         put(11,30);
         put(12,31);
     }
 };
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private SalaryService salaryService;
    @PostConstruct
    public void init(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Set<Integer> hasHour = redisTemplate.opsForSet().members("hasHour");
                Set<Integer> has = redisTemplate.opsForSet().members("has");
                Date date = new Date(System.currentTimeMillis());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int day = (int)(date.getTime()/(1000*60*60*24));
                if (cal.get(Calendar.DAY_OF_WEEK) == 6 && !hasHour.contains(day)){
                    redisTemplate.opsForSet().add("hasHour",day);
                    salaryService.sendHour();
                }
                if (isMonthLast(cal)&&!has.contains(day)){
                    redisTemplate.opsForSet().add("has",day);
                    salaryService.send();
                }
            }
        },0l,5*1000l);
    }

    private boolean isMonthLast(Calendar cal2) {
        int week;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, cal2.get(Calendar.MONTH));
        int lastDay = timL.get(cal.get(Calendar.MONTH)+1);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        week=cal.get(Calendar.DAY_OF_WEEK)-1==0?7:cal.get(Calendar.DAY_OF_WEEK)-1;//获得最后一天是星期几
        if (week==7) {
            lastDay=lastDay-2;
        }else if(week==6){
            lastDay=lastDay-1;
        }
        if (cal2.get(Calendar.DAY_OF_MONTH) == lastDay){
            return true;
        }
        return false;
    }
}