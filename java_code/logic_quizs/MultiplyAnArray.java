package logic_quizs;

import org.junit.Test;

import java.util.Objects;

public class MultiplyAnArray{
    public int[] multiplyByAllButOne(int[] arr){
        Objects.requireNonNull(arr);
        int zeroIndex = arr.length;
        int arrLength = arr.length;

        int[] newArr = new int[arr.length];
        int allMult = 1;

        for (int i = 0; i < arrLength; ++i){
            if (arr[i] == 0){
                if (zeroIndex < arrLength){
                    return newArr;
                }
                zeroIndex = i;
                continue;
            }

            allMult *= arr[i];
        }

        if (zeroIndex < arrLength){
            newArr[zeroIndex] = allMult;
        }
        else{
            for(int i = 0; i < arrLength; ++i){
                newArr[i] = allMult / arr[i];
            }
        }

        return newArr;
    }

    @Test
    public void test1() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] resArr = multiplyByAllButOne(arr);

        for (int i : resArr) {
            System.out.print(i + ",");
        }
    }

    @Test
    public void test2() {
        int[] arr = {7,8,9};
        int[] resArr = multiplyByAllButOne(arr);

        for (int i : resArr) {
            System.out.print(i + ",");
        }
    }

    @Test
    public void test3() {
        int[] arr = {1,4,6,8,9,5,1,0,3,11,2};
        int[] resArr = multiplyByAllButOne(arr);

        for (int i : resArr) {
            System.out.print(i + ",");
        }
    }

    @Test
    public void test4() {
        int[] arr = {1, 4, 0, 8, 9, 5, 1, 0, 3, 11, 2};
        int[] resArr = multiplyByAllButOne(arr);

        for (int i : resArr) {
            System.out.print(i + ",");
        }
    }
    }
