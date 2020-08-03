package quizzs;
/*
   Write a function called sumIntervals/sum_intervals() that accepts an array of intervals,
   and returns the sum of all the interval lengths. Overlapping intervals should only be counted once.
   Intervals
   Intervals are represented by a pair of integers in the form of an array.
   The first value of the interval will always be less than the second value.
   Interval example: [1, 5] is an interval from 1 to 5. The length of this interval is 4.

   List containing overlapping intervals:

   [
   [1,4],
   [7, 10],
   [3, 5]
   ]
*/

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumIntervals {
    public static int sumIntervals(int[][] intervals) {
       if(intervals == null){
            return 0;
       }

       List<Integer> startPointList = new ArrayList<>();
       List<Integer> endPointList = new ArrayList<>();

        for (int[] interval : intervals) {
            if (interval.length > 1) {
                startPointList.add(interval[0]);
                endPointList.add(interval[1]);
            }
        }

        if(startPointList.size() == 0){
            return 0;
        }

        startPointList.sort(Integer::compareTo);
        endPointList.sort(Integer::compareTo);

        int sum = endPointList.get(0) - startPointList.get(0);

        for (int i = 1; i < startPointList.size(); ++i){
            if (startPointList.get(i) < endPointList.get(i - 1)) {
                sum -= endPointList.get(i - 1) - startPointList.get(i);
            }
            sum += endPointList.get(i) - startPointList.get(i);
        }

        return sum;
    }

    @Test
    public void shouldHandleNullOrEmptyIntervals() {
        assertEquals(0, sumIntervals(null));
        assertEquals(0, sumIntervals(new int[][]{}));
        assertEquals(0, sumIntervals(new int[][]{{4, 4}, {6, 6}, {8, 8}}));
    }

    @Test
    public void shouldAddDisjoinedIntervals() {
        assertEquals(9, sumIntervals(new int[][]{{1, 2}, {6, 10}, {11, 15}}));
        assertEquals(11, sumIntervals(new int[][]{{4, 8}, {9, 10}, {15, 21}}));
        assertEquals(7, sumIntervals(new int[][]{{-1, 4}, {-5, -3}}));
        assertEquals(78, sumIntervals(new int[][]{{-245, -218}, {-194, -179}, {-155, -119}}));
    }

    @Test
    public void shouldAddAdjacentIntervals() {
        assertEquals(54, sumIntervals(new int[][]{{1, 2}, {2, 6}, {6, 55}}));
        assertEquals(23, sumIntervals(new int[][]{{-2, -1}, {-1, 0}, {0, 21}}));
    }

    @Test
    public void shouldAddOverlappingIntervals() {
        assertEquals(7, sumIntervals(new int[][]{{1, 4}, {7, 10}, {3, 5}}));
        assertEquals(6, sumIntervals(new int[][]{{5, 8}, {3, 6}, {1, 2}}));
        assertEquals(19, sumIntervals(new int[][]{{1, 5}, {10, 20}, {1, 6}, {16, 19}, {5, 11}}));
    }

    @Test
    public void shouldHandleMixedIntervals() {
        assertEquals(13, sumIntervals(new int[][]{{2, 5}, {-1, 2}, {-40, -35}, {6, 8}}));
        assertEquals(1234, sumIntervals(new int[][]{{-7, 8}, {-2, 10}, {5, 15}, {2000, 3150}, {-5400, -5338}}));
        assertEquals(158, sumIntervals(new int[][]{{-101, 24}, {-35, 27}, {27, 53}, {-105, 20}, {-36, 26},}));
    }
}
