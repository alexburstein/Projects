package concurrent_non_comparing_sorts;

/*
    Stable Generic MultiThreaded Counting Sort and Radix Sort.
    Counting sort complexity: O(2n + k) time, and O(n + 2k) space. k is range of values
    Radix Sort complexity: O(d * (2n + b)), and O(d * (n + 2b)) space.
        d is number of buckets in k. b is size of each bucket.
    version: 1.2
    By: Alexander Burstein.
    Accepts positive and negative numbers, as long as the range of values is no bugger than max int.
    * the histogram filling is multithreaded.
    * the tmp result array filling is dual threaded.
    * lazy init.
    ** accepts only types that implement Countable interface.
*/

import pair.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NonCompareSort <T extends Countable> {
    private final static int MAX_ALLOWED_NUM_OF_ELEMENTS = Integer.MAX_VALUE; // for radix sort only.
    private final static int NUM_OF_THREADS = Runtime.getRuntime().availableProcessors(); // for your consideration
    private final static int BYTE_SIZE = 8;
    private List<T> origList;
    private Object[] tmpResultArray;
    private AtomicInteger[] histogram;
    private int[] firstIndexAccumulationArray;
    private int valueOffset;
    private int valueRange;


    void CountingSortLogic(List<T> list){
        origList = list;
        initValueRange(); // initializes the range of values, and the minimum value offset from zero.
        initConcurrentHistogram(valueRange); // initializes a histogram size of value range.
        fillHistogramConcurrently(-1, 0); // mask -1 includes all the bits of the the int, effectively inactive.
        initAccumulationArrays();
        initTmpResultArray(-1, 0); // same
        sortOriginalList();
    }

    void radixSortLogic(List<T> list){
        origList = list;
        initValueRange();
        int mask = 0xFF; // byte size.

        for (int i = 0; i < 4 && (valueRange >> (i * BYTE_SIZE) != 0); ++i){
            initConcurrentHistogram(mask); // initializes histogram size of mask.
            fillHistogramConcurrently(mask<<i, i); // fills histogram according to masked bits.
            initAccumulationArrays();
            initTmpResultArray(mask<<i, i); // fills array with references to elements, according to masked bits.
            sortOriginalList();
        }
    }

    /**
     * finds the maximum and minimum values with O(3/4n) time
     */
    private void initValueRange(){
        Pair<T, T> minMax = Pair.minMax(origList, Comparator.comparingInt(Countable::getIntValue));
        int minValue = minMax.getKey().getIntValue();
        int maxValue = minMax.getValue().getIntValue();

        if ((long)maxValue - minValue > MAX_ALLOWED_NUM_OF_ELEMENTS){
            throw new IllegalArgumentException("Can't preform counting sort operation on variables of this range");
        }

        valueOffset = minValue;
        valueRange = maxValue  + 1 - minValue;
    }

    private void initConcurrentHistogram(int maxIndexInHistogram){
        histogram = new AtomicInteger[maxIndexInHistogram + 1];

        for(int i = 0; i <= maxIndexInHistogram; ++i){
            histogram[i] = new AtomicInteger();
        }
    }

    /**
     * fills the histogram concurrently with as many threads as defined in the global variable.
     * @param mask representing the bits in the value int to fill the histogram by. According to size of histogram.
     * @param maskOffset  representing the location of the mask in the value int.
     */
    private void fillHistogramConcurrently(int mask, int maskOffset) {
        Thread[] threadList = new Thread[NUM_OF_THREADS];

        for (int threadNum = 0; threadNum < threadList.length; ++threadNum){
            final int finalThreadNum = threadNum;
            threadList[finalThreadNum] = new Thread(()->{
                int portionPerThread = origList.size() / NUM_OF_THREADS;
                int rangeStart = finalThreadNum * portionPerThread;
                int rangeEnd = (finalThreadNum == NUM_OF_THREADS - 1) ? origList.size() : rangeStart + portionPerThread;

                for(int i = rangeStart; i < rangeEnd; ++i){
                    int placeInHistogram =
                                ((origList.get(i).getIntValue() - valueOffset) & mask) >> (maskOffset * BYTE_SIZE);
                    histogram[placeInHistogram].getAndIncrement();
                }
            });
            threadList[finalThreadNum].start();
        }

        for(Thread t: threadList){
            try {t.join();} catch (InterruptedException e) {System.err.println("thread has been interrupted");}
        }
    }

    /**
     * initializes the histogram as an accumulation array, and adds another array,
     * representing the first appearance of each element in the original array.
     * this is for the ability to iterate the result array from two sides, with two threads, while keeping in stable.
     */
    private void initAccumulationArrays(){
        firstIndexAccumulationArray = new int[histogram.length];
        firstIndexAccumulationArray[0] = 0;

        for(int i = 1; i < histogram.length; ++i){
            int prev = histogram[i - 1].get();
            histogram[i].addAndGet(prev);
            firstIndexAccumulationArray[i] = prev;
        }
    }

    /**
     * initializes a tmp result array of references to an Object,
     *  refering them to the original objects in the original list. concurrently from both sides.
     * @param mask representing the bits in the value int to examine to find its place in histogram.
     * @param maskOffset representing the location of the mask in the value int.
     */
    private void initTmpResultArray(int mask, int maskOffset){
        tmpResultArray = new Object[origList.size()];

        Thread fromLeft = new Thread(()->{
            for (int i = 0; i < origList.size() / 2; ++i){
                int histogramIndex = ((origList.get(i).getIntValue() - valueOffset) & mask)>>(maskOffset * BYTE_SIZE);
                tmpResultArray[firstIndexAccumulationArray[histogramIndex]++] = origList.get(i);
            }
        });
        Thread fromRight = new Thread(()->{
            for (int i = origList.size() - 1; i >= origList.size() / 2; --i){
                int histogramIndex = ((origList.get(i).getIntValue() - valueOffset) & mask)>>(maskOffset * BYTE_SIZE);
                tmpResultArray[histogram[histogramIndex].decrementAndGet()] = origList.get(i);
            }
        });

        fromLeft.start();
        fromRight.start();
        try {
            fromLeft.join();
            fromRight.join();
        }
        catch (InterruptedException e) {System.err.println("threads interrupted");}
    }

    /**
     * fills the original list with references to the original elements, now sorted, from the tmp result array.
     */
    @SuppressWarnings({"unchecked"})
    private void sortOriginalList(){
        for(int i = 0; i < origList.size(); ++i){
            origList.set(i, (T) tmpResultArray[i]);
        }
    }
}
