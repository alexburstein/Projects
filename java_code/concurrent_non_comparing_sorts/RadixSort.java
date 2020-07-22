package concurrent_non_comparing_sorts;

import java.util.List;

public class RadixSort<T>{
    /**
     * sorts the elements in the list. O(d * (2n + b)), and O(d * (n + 2b)) space.
     *  d is number of buckets in k. b is size of each bucket.
     * @param list a list of Countable elements.
     * @param <T> a type that implements Countable, or its subtype.
     */
    public static <T extends Countable> void radixSort(List<T> list){
        new NonCompareSort<T>().radixSortLogic(list);
    }
}
