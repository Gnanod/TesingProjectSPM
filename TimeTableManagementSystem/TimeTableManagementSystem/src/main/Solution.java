package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {

        ArrayList<Integer> sessionList = new ArrayList<>();
        ArrayList<Integer> roomList = new ArrayList<>();

        sessionList.add(1);
        sessionList.add(2);
        sessionList.add(3);

        roomList.add(4);
        roomList.add(5);
        roomList.add(6);

        lblSessionList:
        for (Integer i : sessionList
        ) {
            lblRoomList:
            for (Integer i1 : roomList
            ) {
                firstLoop:
                for (int j = 0; j < 7; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (k == 2) {
                            System.out.println("lbl RoomList Loop Break");
                            break lblRoomList;
                        }
                    }
                }
            }
        }

//        String type ="m";
//        int hours = 10;
//        int days = 7;
//        int hourSize=0;
//        if(type.equals("thirtyMinute")){
//            hourSize=hours*2;
//        }else{
//            hourSize=hours;
//        }
//        String[][] timeString = new String[days][hourSize];
//        int hoursCount = 8;
//        int minutCount =30;
//
//        for (int i = 0; i < days; i++) {
//            for (int j = 0; j < hours; j++) {
//                String tempTime="";
//                if(type.equals("thirtyMinute")){
//                    if(minutCount!=30){
//                        tempTime = hoursCount +"."+minutCount+"0";
//                    }else{
//                        tempTime = hoursCount +"."+minutCount;
//                    }
//                    minutCount+=30;
//                    if(minutCount>=60){
//                        hoursCount++;
//                        minutCount=0;
//                    }
//                    if(minutCount!=30){
//                        timeString[i][j] = tempTime +"-" + hoursCount +"."+minutCount+"0";
//                    }else{
//                        timeString[i][j] = tempTime +"-" + hoursCount +"."+minutCount;
//                    }
//                }else{
//                    tempTime = hoursCount +"."+minutCount;
//                    hoursCount += 1;
//                    timeString[i][j] = tempTime + "-" + hoursCount+"."+minutCount;
//                }
//            }
//            hoursCount = 8;
//            minutCount =30;
//        }

    }
}
