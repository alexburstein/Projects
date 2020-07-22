package logic_quizs;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaxSubArrayNaive {

    public int getMaxSumOfKElements(int[] arr, int k){
        if ((arr == null) || (arr.length < k)){
            throw new IllegalArgumentException("parameter don't fit function");
        }

        int sum = 0;

        for (int i = 0; i < k; ++i){
            sum += arr[i];
        }

        int max = sum;

        for (int i = k; i < arr.length; ++i){
            sum = sum + arr[i] - arr[i - k];
            max = Math.max(max,sum);
        }

        return max;
    }

    @Test
    public void test(){
        assertEquals(12, getMaxSumOfKElements(new int[]{1,2,3,4,5}, 3));
        assertEquals(12, getMaxSumOfKElements(new int[]{5,4,3,2,1}, 3));
        assertEquals(31, getMaxSumOfKElements(new int[]{5,4,31,2,1}, 1));
        assertEquals(17, getMaxSumOfKElements(new int[]{5,4,5,2,1}, 5));
        assertEquals(1, getMaxSumOfKElements(new int[]{1}, 1));

        assertThrows(IllegalArgumentException.class, ()->getMaxSumOfKElements(new int[]{5,4,5,2,1}, 6));
        assertThrows(IllegalArgumentException.class, ()->getMaxSumOfKElements(null, 6));
        assertThrows(IllegalArgumentException.class, ()->getMaxSumOfKElements(new int[]{1}, 2));


    }
}
