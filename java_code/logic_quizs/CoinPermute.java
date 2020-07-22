package logic_quizs;

import org.junit.Test;

import java.util.Arrays;

public class CoinPermute {
    public int coinPermute(int n, int[] coins){
        int permFound = 0;

        if (n == 0){
            return 1;
        }

        if (n < 0){
            return 0;
        }

        for(int i = 0; i < coins.length; ++i){
            permFound += coinPermute(n - coins[i], Arrays.copyOfRange(coins,i,coins.length));
        }

        return permFound;
    }

    @Test
    public void coinPermTest(){
        int[] coinArr = new int[]{25, 10, 5, 1};
        assert (coinPermute(0, coinArr) == 1);
        assert (coinPermute(1, coinArr) == 1);
        assert (coinPermute(2, coinArr) == 1);
        assert (coinPermute(5, coinArr) == 2);
        assert (coinPermute(6, coinArr) == 2);
        assert (coinPermute(10, coinArr) == 4);
        assert (coinPermute(25, coinArr) == 13);
    }
}
