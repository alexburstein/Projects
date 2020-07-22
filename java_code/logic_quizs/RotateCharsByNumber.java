package logic_quizs;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;

public class RotateCharsByNumber {
    public void  rotateCharArrByNum(char[] arr, int rotationNum){
        Objects.requireNonNull(arr);
        int rotateRemainder = rotationNum % arr.length;

        reverse(arr, 0, rotateRemainder);
        reverse(arr, rotateRemainder, arr.length);
        reverse(arr, 0, arr.length);
    }

    private void reverse(char[] arr, int from, int to){
        while(from < to){
            char tmp = arr[from];
            arr[from] = arr[to - 1];
            arr[to - 1] = tmp;
            ++from;
            --to;
        }
    }

    @Test
    public void test(){
        rotateCharsTest("1234567".toCharArray(), 5);
        rotateCharsTest("1234567".toCharArray(), 3);

        rotateCharsTest("alex".toCharArray(), 1);
        rotateCharsTest("alex".toCharArray(), 2);
    }

    private void rotateCharsTest(char[] arr, int numOfRotates){
        char[] copy = arr.clone();

        System.out.println("original arr is : " + String.valueOf(arr));

        rotateCharArrByNum(arr,numOfRotates);
        System.out.print("rotated by " + numOfRotates + " is : " );

        for (char c : arr) {
            System.out.print(c);
        }

        System.out.println();

        for(int i = 0; i < arr.length; ++i){
        Assertions.assertEquals(copy[(numOfRotates + i) % arr.length],  arr[i]);
        }
    }
}
