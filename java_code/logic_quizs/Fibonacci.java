package logic_quizs;

import org.junit.Test;

public class Fibonacci {

    public int iterativeFib(int n){
        int a = 1, b = 1, c = 0;

        if (n <= 0){
            return 0;
        }

        if (n <= 2){
            return 1;
        }

        for (int i = 1; i < n - 1; ++i){
            c = a + b;
            a = b;
            b = c;
        }

        return c;
    }


    public int recursiveFib(int n){
        if (n <= 0){
            return 0;
        }

        if (n <= 2){
            return 1;
        }

        return recursiveFib(n - 1) + recursiveFib(n - 2);
    }


    public int dynamicFib(int n){
        int[] arr = new int[n + 2];

        arr[0] = 0;
        arr[1] = 1;

        for (int i = 2; i <= n; ++i){
            arr[i] = arr[i - 1] + arr[i - 2];
        }

        return arr[n];
    }


    @Test
    public void iterativeTest(){
        assert (iterativeFib(0) == 0);
        assert (iterativeFib(1) == 1);
        assert (iterativeFib(2) == 1);
        assert (iterativeFib(3) == 2);
        assert (iterativeFib(4) == 3);
        assert (iterativeFib(5) == 5);
        assert (iterativeFib(6) == 8);
        assert (iterativeFib(7) == 13);
        assert (iterativeFib(8) == 21);
    }

    @Test
    public void recursiveTest(){
        assert (recursiveFib(0) == 0);
        assert (recursiveFib(1) == 1);
        assert (recursiveFib(2) == 1);
        assert (recursiveFib(3) == 2);
        assert (recursiveFib(4) == 3);
        assert (recursiveFib(5) == 5);
        assert (recursiveFib(6) == 8);
        assert (recursiveFib(7) == 13);
        assert (recursiveFib(8) == 21);
    }

    @Test
    public void dynamicTest(){
        assert (dynamicFib(0) == 0);
        assert (dynamicFib(1) == 1);
        assert (dynamicFib(2) == 1);
        assert (dynamicFib(3) == 2);
        assert (dynamicFib(4) == 3);
        assert (dynamicFib(5) == 5);
        assert (dynamicFib(6) == 8);
        assert (dynamicFib(7) == 13);
        assert (dynamicFib(8) == 21);
    }
}
