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

        sessionList.add(1);
        sessionList.add(2);
        sessionList.add(3);

        for(Integer i :sessionList){
            if(i==3){
                System.out.println("LLL");
                sessionList.remove(i);
//                break;

            }
        }
        for (Integer i1 : sessionList) {
            System.out.println(i1);
        }

    }
}
