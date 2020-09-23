package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {

        String type ="m";
        int hours = 10;
        int days = 7;
        int hourSize=0;
        if(type.equals("thirtyMinute")){
            hourSize=hours*2;
        }else{
            hourSize=hours;
        }
        String[][] timeString = new String[days][hourSize];
        int hoursCount = 8;
        int minutCount =30;

        for (int i = 0; i < days; i++) {
            for (int j = 0; j < hours; j++) {
                String tempTime="";
                if(type.equals("thirtyMinute")){
                    if(minutCount!=30){
                        tempTime = hoursCount +"."+minutCount+"0";
                    }else{
                        tempTime = hoursCount +"."+minutCount;
                    }
                    minutCount+=30;
                    if(minutCount>=60){
                        hoursCount++;
                        minutCount=0;
                    }
                    if(minutCount!=30){
                        timeString[i][j] = tempTime +"-" + hoursCount +"."+minutCount+"0";
                    }else{
                        timeString[i][j] = tempTime +"-" + hoursCount +"."+minutCount;
                    }
                }else{
                    tempTime = hoursCount +"."+minutCount;
                    hoursCount += 1;
                    timeString[i][j] = tempTime + "-" + hoursCount+"."+minutCount;
                }
            }
            hoursCount = 8;
            minutCount =30;
        }

    }
}
