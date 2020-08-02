package quizzs;

import org.junit.Test;

public class BiggestNonAjacentSubArr {

    public int findBiggestNonAjacentSubArrRec(int[] arr, int curIndex){
        if (curIndex >= arr.length){
            return 0;
        }

        int sum1  = findBiggestNonAjacentSubArrRec(arr, curIndex + 2);
        if (arr[curIndex] > 0){
            sum1 += arr[curIndex];
        }
        int sum2  = findBiggestNonAjacentSubArrRec(arr, curIndex + 1);

        return Math.max(sum1,sum2);
    }

    /* geeks for geeks iterative solution simplified */
    int findBiggestNonAjacentSubArrIter(int[] arr){
        int sumWithCur = arr[0];
        int sumNoCur = 0;
        int maxUpToPrev;

        for (int current = 1; current < arr.length; current++) {
            maxUpToPrev = Math.max(sumWithCur, sumNoCur);
            sumWithCur = sumNoCur + arr[current];
            sumNoCur = maxUpToPrev;
        }

        return (Math.max(sumWithCur, sumNoCur));
    }


    @Test
    public void test(){
        System.out.println(findBiggestNonAjacentSubArrRec(new int[]{1,3,1,0}, 0));
        System.out.println(findBiggestNonAjacentSubArrRec(new int[]{1,0,0,1}, 0));
        System.out.println(findBiggestNonAjacentSubArrRec(new int[]{1,0,0,0}, 0));
        System.out.println(findBiggestNonAjacentSubArrRec(new int[]{1,2,2,3}, 0));
        System.out.println(findBiggestNonAjacentSubArrRec(new int[]{1,2,3,4}, 0));
        System.out.println(findBiggestNonAjacentSubArrRec(new int[]{5,1,1,5}, 0));
        System.out.println(findBiggestNonAjacentSubArrRec(new int[]{1,-1,-1,1,-1,-1,1,-1,-1}, 0));
        System.out.println(findBiggestNonAjacentSubArrRec(new int[]{1,-1,1,-1,1,-1}, 0));

    }

    @Test
    public void test2(){
        System.out.println(findBiggestNonAjacentSubArrIter(new int[]{1,3,1,0}));
        System.out.println(findBiggestNonAjacentSubArrIter(new int[]{1,0,0,1}));
        System.out.println(findBiggestNonAjacentSubArrIter(new int[]{1,0,0,0}));
        System.out.println(findBiggestNonAjacentSubArrIter(new int[]{1,2,2,3}));
        System.out.println(findBiggestNonAjacentSubArrIter(new int[]{1,2,3,4}));
        System.out.println(findBiggestNonAjacentSubArrIter(new int[]{5,1,1,5}));
    }
}
