package concurrent_non_comparing_sorts;

import java.util.List;

public class CountingSort<T> {
    /**
     * sorts the elements in the list. complexity: O(2n + k) time, and O(n + 2k) space. k is range of values
     * @param list a list of Countable elements.
     * @param <T> a type that implements Countable, or its subtype.
     */
    public static <T extends Countable> void countingSort(List<T> list){
        new NonCompareSort<T>().CountingSortLogic(list);
    }
}
