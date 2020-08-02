package quizzs;

/*
    Given a non-empty array of integers, return the k most frequent elements.
 */

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class KFrequentElements {
    public List<Integer> getKMostFrequent(List<Integer> nums, int k){
        HashMap<Integer, Integer> histogram = fillHistogram(nums);
        if (k < 1 || histogram.size() < k){
            throw new IllegalArgumentException("there are fewer repeated elements then required by K");
        }

        PriorityQueue<Unit> pq = new PriorityQueue<>(11, (o1, o2)-> o2.priority - o1.priority);

        for(Integer i: histogram.keySet()){
            pq.add(new Unit(i,histogram.get(i)));
        }

        List<Integer> intList = new ArrayList<>();

        for(int i = 0; i < k; ++i){
            intList.add(Objects.requireNonNull(pq.poll()).num);
        }

        return intList;
    }

    private HashMap<Integer, Integer> fillHistogram(List<Integer> nums){
        HashMap<Integer, Integer> histogram = new HashMap<>();

        for(Integer i: nums){
            histogram.merge(i, 1, Integer::sum);
        }

        return histogram;
    }

    private static class Unit{
        private final Integer num;
        private final Integer priority;
        public Unit(Integer num, Integer priority){
            this.num = num;
            this.priority = priority;
        }
    }


    @Test
    public void test(){
        assertEquals(Arrays.asList(2, 5), getKMostFrequent(Arrays.asList(1, 2, 2, 5, 5), 2));
        assertEquals(Arrays.asList(6), getKMostFrequent(Arrays.asList(1, 6,6,6,6,6,6,6,6), 1));
        assertEquals(Arrays.asList(6), getKMostFrequent(Arrays.asList(6,6,6,6,6,6,6,6), 1));
        assertEquals(Arrays.asList(11, 2,5), getKMostFrequent(Arrays.asList(11,11,11,11,11,11, 2, 2, 5, 5), 3));
        assertThrows(IllegalArgumentException.class, ()->getKMostFrequent(Arrays.asList(11,11,11, 2, 2, 5, 5), 4));
    }
}
