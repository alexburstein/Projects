package logic_quizs;

import org.junit.Test;

public class RemoveElmFromArr {
    public int removeElmFromArr(int[] arr, int elm){
        int arrLength = arr.length;

        for (int i = 0; i < arrLength; ++i) {
           if (arr[i] == elm){
               for (int j = i; j < arrLength -1; ++j){
                    arr[j] = arr[j + 1];
                }
               --arrLength;
               --i;
           }
        }

        return arrLength;
    }

    @Test
    public void test() {
        RemoveElmFromArr test = new RemoveElmFromArr();
        int res;
        res = test.removeElmFromArr(new int[] {1}, 2);
        assert(res == 1);
        res = test.removeElmFromArr(new int[] {1,2}, 2);
        assert (res == 1);
        res = test.removeElmFromArr(new int[] {2,1}, 2);
        assert (res == 1);
        res = test.removeElmFromArr(new int[] {1,2,3,4}, 2);
        assert (res == 3);
        res = test.removeElmFromArr(new int[] {2,1,3,4,3,1,2}, 2);
        assert (res == 5);
        res = test.removeElmFromArr(new int[] {2,1,2,2,3,2,1}, 2);
        assert (res == 3);
        res = test.removeElmFromArr(new int[] {2,2,2,2,1,2,2}, 2);
        assert (res == 1);
    }
}
