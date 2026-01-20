package org.Nurel.Q1;

public class norecursion {
    public static void main(String[] args) {
        System.out.println(nonRecursiveFibo(5));
    }

    public static long nonRecursiveFibo(int n) {
        if (n == 0){
            return 0;
        }
        if (n == 1){
            return 1;
        }

        long previous = 0;
        long current = 1;

        for (int i = 2; i <= n; i++) {
            long sum = current + previous;
            previous = current;
            current = sum;
        }
        return current;
    }
}
