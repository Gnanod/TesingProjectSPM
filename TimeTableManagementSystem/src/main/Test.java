package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Test {
    public static void main(String[] args) {
        String z = "";
        String x = "SLIIT";
        String y = "SLHEEIITSLEEIIT";
        int count = 0;
        int pos = 0;
        for (int i = 0; i < x.length(); i++) {
            for (int j = pos; j < y.length(); j++) {
                if (x.charAt(i) == y.charAt(j)) {
                    pos++;
                    z += x.charAt(i);
                    System.out.println(z);
                    break;
                }
            }
            if (x.equals(z)) {
                count++;
                z = "";
                pos--;
                i = 0;
            }
        }
        System.out.println(count);
    }
    }



