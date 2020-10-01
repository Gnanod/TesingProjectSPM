package main.dbconnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class Solution {

    public static void main (String args[]){
//        String time1 = "09:30";
//        String time2 = "10:30";
//
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        Date date1 = null;
//        Date date2 = null;
//        try {
//            date1 = format.parse(time1);
//            date2 = format.parse(time2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        long difference = date2.getTime() - date1.getTime();
//        System.out.println(difference / (60 * 60 * 1000));

        Set<Integer> roomList = new HashSet<>();
        roomList.add(1);
        roomList.add(2);
        roomList.add(4);
        roomList.add(1);

        for (Integer i: roomList
             ) {
            System.out.println(i);
        }
    }
}
