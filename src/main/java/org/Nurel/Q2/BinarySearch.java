package org.Nurel.Q2;

public class BinarySearch {

    public static void main(String[] args) {
        // int[] myArray = {};  // пустой массив.
        // int[] myArray = {1}; // массив с одним элементом.
        // int[] myArray = {1,4,9};   // массив с тремя элементами.
        int[] myArray = {1,2,4,5,7,9,11,14,17,20,23}; // четное/нечетное число элементов.
        int target = 1;

        int result = biSearch(myArray,target);

        System.out.println(result);
    }

    public static int biSearch(int[] array, int target){
        int low = 0;
        int high = array.length - 1;

        while (low <= high){
            int mid =  (low + high) / 2;

            int guess = array[mid];
            if (target == guess){
                return mid;
            }
            if (guess < target){
                low = mid + 1;
            } else {
                 high = mid - 1;
            }
        }
        return -1;
    }
}
