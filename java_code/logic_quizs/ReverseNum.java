package logic_quizs;

import org.junit.Test;

/*
 * answers correctly only to positive numbers.
 */

public class ReverseNum {
    public int reverseNum(int num){
        int res = 0;

        while (num > 0){
            res *= 10;
            res += (num % 10);
            num /= 10;
        }

        return res;
    }

    @Test
    public void test(){
        assert(reverseNum(10) == 1);
        assert(reverseNum(12) == 21);
        assert(reverseNum(11) == 11);
        assert(reverseNum(0) == 0);
        assert(reverseNum(123) == 321);
    }
}
