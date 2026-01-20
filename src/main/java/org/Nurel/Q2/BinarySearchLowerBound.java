package org.Nurel.Q2;

public class BinarySearchLowerBound {
    public static void main(String[] args) {
        int[] myArray = {1,3,4,4,4,4,6,9,11};  // массив с одинаковыми элементами
        int target = 4;
        int result = biSearch(myArray,target);
        System.out.println(result);
    }

    public static int biSearch(int[] array, int target){
        int low = 0;
        int high = array.length - 1;
        int result = -1;

        while (low <= high){
            int mid = low + (high - low) / 2;
            int guess = array[mid];

            if (guess == target){
                result = mid;
                high = mid - 1;
            } else if (guess < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
}
