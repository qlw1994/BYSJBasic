package qlw.sign;

import qlw.util.MyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wiki on 2017/3/19.
 */
public class test {
    public static List<Date> DOdateToWeek(Date mdate,int flag) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mdate);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        mdate=calendar.getTime();
        int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - (b+flag*7) * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a-1, fdate);
        }
        return list;
    }
    public static void main(String[] args) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd|EEE");
        List<Date> dates = null;
        try {
            dates = DOdateToWeek(MyUtils.SIMPLE_DATE_FORMAT.parse("2017-3-20"),1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(Date d:dates){
            System.out.println(format.format(d));
        }
    }
}
