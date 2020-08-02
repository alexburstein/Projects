package quizzs;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BursaProblem {

    public int findMaxProfit(List<Integer> price){
        int maxProfit = 0;
        int minPriceIndex = 0;
        int maxPriceIndex = 0;
        int priceListLength = price.size();

        for(int i = 0; i < priceListLength; ++i){
            int curPrice = price.get(i);
            int curMinPrice = price.get(minPriceIndex);
            int curMaxPrice = price.get(maxPriceIndex);

            if (curPrice > curMaxPrice){
                maxPriceIndex = i;
            }
            else if(curPrice < curMinPrice){
                maxProfit = Math.max(maxProfit, curMaxPrice - curMinPrice);
                minPriceIndex = i;
                maxPriceIndex = i;
            }
        }

        return Math.max(maxProfit, price.get(maxPriceIndex) - price.get(minPriceIndex));
    }

    @Test
    public void test(){
        assertEquals(6, findMaxProfit(Arrays.asList(1,5,4,-1,3,-2,1,-1,4)));
        assertEquals(0, findMaxProfit(Arrays.asList(0,0,0,0,0,0,0,0)));
        assertEquals(8, findMaxProfit(Arrays.asList(1,2,3,4,5,6,7,8,9)));
        assertEquals(0, findMaxProfit(Arrays.asList(9,8,7,6,5,4,3,2,1)));
        assertEquals(5, findMaxProfit(Arrays.asList(-5,-3,-1,0,-1,-5,-9)));
        assertEquals(7, findMaxProfit(Arrays.asList(7,5,3,1,4,6,8,0)));
        assertEquals(7, findMaxProfit(Arrays.asList(9,7,5,3,1,4,6,8,0)));
    }
}
