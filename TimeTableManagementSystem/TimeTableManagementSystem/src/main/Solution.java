package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Solution {
    public static void main(String[] args) {

        ArrayList<Integer> sessionList = new ArrayList<>();

        sessionList.add(1);
        sessionList.add(2);
        sessionList.add(3);
        sessionList.add(4);
        sessionList.add(5);

        for (int i=0;i<sessionList.size();i++){
            System.out.println(sessionList.get(i));
        }

//        Iterator<Integer> ite = sessionList.iterator();
//
//        /* Remove the second value of the list, while iterating over its elements,
//         * using the iterator's remove method. */
//        while(ite.hasNext()) {
//            int value = ite.next();
//            if(value==3)
//                ite.remove();
////            else
////                System.out.println(value);
//        }
//
//        for (Integer i:sessionList
//             ) {
//            System.out.println(i);
//        }

    }
}
