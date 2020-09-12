package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        int number = s1.nextInt();

        for(int i=0;i<number;i++){
            for(int j=0;j<=i;j++){
              if(i==j){
                  System.out.println(i+" * "+j);
                  break;
              }
            }
            System.out.println();
        }
    }
}
