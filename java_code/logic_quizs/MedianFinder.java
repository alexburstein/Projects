package logic_quizs;

import org.junit.Test;

import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Integer> biggerQueue = new PriorityQueue<>();
    PriorityQueue<Integer> smallerQueue = new PriorityQueue<>((a,b)-> b - a);
    boolean isEvenFlag = false;
    Integer curMedian;

    public Integer insert(Integer newInt) {
        if (curMedian == null) {
            curMedian = newInt;
            return curMedian;
        }

        if (newInt.compareTo(curMedian) > 0) {
            addAndBalanceHeaps(biggerQueue, smallerQueue, newInt);
        }
        else{
            addAndBalanceHeaps(smallerQueue, biggerQueue, newInt);
        }

        return curMedian;
    }

    private void addAndBalanceHeaps(PriorityQueue<Integer> gotElm, PriorityQueue<Integer> gotNoElm, Integer data){
        gotElm.add(data);
        isEvenFlag = !isEvenFlag;

        if (isEvenFlag){
            gotNoElm.add(curMedian);
            assert(!gotElm.isEmpty() && !gotNoElm.isEmpty());
            curMedian = (gotElm.peek() + curMedian) / 2;
        }
        else{
            curMedian = gotElm.poll();
        }
    }

    @Test
    public void  test(){
        System.out.println(insert(1));
        System.out.println(insert(2));
        System.out.println(insert(3));
        System.out.println(insert(6));
        System.out.println(insert(9));
        System.out.println(insert(11));
        System.out.println(insert(13));
        System.out.println(insert(-1));
    }
}
