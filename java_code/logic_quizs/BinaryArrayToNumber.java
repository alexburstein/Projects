package logic_quizs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryArrayToNumber {
    public static int ConvertBinaryArrayToInt(List<Integer> binary) {
        int res = 0;
        int arrSize = binary.size();

        for (int i = 0 ; i < arrSize; ++i){
            if (binary.get(arrSize - i - 1) == 1){
                res += Math.pow(2,i);
            }
        }

        return res;
    }



    @Test
    public void convertBinaryArrayToInt(){
        assertEquals(1, ConvertBinaryArrayToInt(new ArrayList<>(Arrays.asList(0,0,0,1))));
        assertEquals(15, ConvertBinaryArrayToInt(new ArrayList<>(Arrays.asList(1,1,1,1))));
        assertEquals(6, ConvertBinaryArrayToInt(new ArrayList<>(Arrays.asList(0,1,1,0))));
        assertEquals(9, ConvertBinaryArrayToInt(new ArrayList<>(Arrays.asList(1,0,0,1))));

    }
}
