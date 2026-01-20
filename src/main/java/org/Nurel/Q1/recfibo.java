package org.Nurel.Q1;

public class recfibo {
    public static void main(String[] args) {
        System.out.println(fiborec(5));
    }

    public static int fiborec(int n) {
        if (n <= 2){
            return 1;
        } else {
             return fiborec(n - 1) + fiborec(n - 2);
        }
    }
}
