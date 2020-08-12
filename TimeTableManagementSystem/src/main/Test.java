package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Test {

    public static void main(String[] args) {
        ArrayList<String> s1 = new ArrayList<>();

        s1.add("Alpha");
        s1.add("Beta");
        s1.add("Zeta");
        s1.add("Beta");
        s1.add("Zeta");
        s1.add("Zeta");
        s1.add("Epsilon");
        s1.add("Beta");
        s1.add("Zeta");
        s1.add("Beta");
        s1.add("Zeta");
        s1.add("Beta");
        s1.add("Delta");
        s1.add("Zeta");
        s1.add("Beta");
        s1.add("Zeta");
        s1.add("Beta");
        s1.add("Zeta");
        s1.add("Beta");
        s1.add("Zeta");
        s1.add("Beta");

        mostActive(s1);
    }
    public static List<String> mostActive(List<String> customers) {
        // Write your code here

        ArrayList<String> arr1 = new ArrayList<>();
        for (String s1: customers) {
            int count =0;
            for (String s2: customers
                 ) {
                if(s1.equals(s2)){
                    count++;
                }
            }
            double totalTrade = ((double)count/customers.size())*100.00;
            if(totalTrade>5){
               if(arr1.size()==0){
                   arr1.add(s1);
               }else{
                   boolean status=true;
                   for (String s3:arr1
                        ) {
                       if(s3.equals(s1)){
                          status=false;
                       }
                   }
                   if(status){
                       arr1.add(s1);
                   }
               }
            }

        }
        Collections.sort(arr1);
        System.out.println(arr1.toString());
        return arr1;
    }

}

