package logic_quizs;

/*
find the biggest sub array. return the sum, the leading index, and the number of elements.
 */

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiggestSubArray {
    public List<Integer> maxSubArr(List<Integer> list){
        int localNumOfElms = 0, localSum = 0, numOfElems = 0, sum = 0, head = 0;

        for (int i = 0; i < list.size(); ++i){
            int curNum = list.get(i);
            localSum += curNum;
            ++localNumOfElms;

            if (localSum <= curNum){
                localSum = curNum;
                localNumOfElms = 1;
            }

            if (localSum >= sum){
                head = i;
                numOfElems = localNumOfElms;
                sum = localSum;
            }
        }

        return Arrays.asList(sum, head, numOfElems);
    }

    @Test
    public void test(){
        assertEquals(Arrays.asList(10,3,4), maxSubArr(Arrays.asList(1,2,3,4)));
        assertEquals(Arrays.asList(10,3,4), maxSubArr(Arrays.asList(4,3,2,1)));
        assertEquals(Arrays.asList(0,3,1),maxSubArr(Arrays.asList(0,0,0,0)));
        assertEquals(Arrays.asList(6,6,5),maxSubArr(Arrays.asList(0,-1,1,0,3,-3,5)));
        assertEquals(Arrays.asList(5,6,1),maxSubArr(Arrays.asList(0,-1,2,-2,3,-3,5)));
    }
}
