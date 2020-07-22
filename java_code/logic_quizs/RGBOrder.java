package logic_quizs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RGBOrder {
    public char[] orderChars(char[] chars){
        if (chars == null || chars.length < 2){ return chars;}

        int leftLength = putChosenInRight(chars, 'B', chars.length);
        putChosenInRight(chars,'G', leftLength);

        return chars;
    }

    private int putChosenInRight(char[] chars, char chosen, int length){
        int leftIndex = 0;
        int rightIndex = length - 1;

        while(leftIndex < rightIndex){
            while ((leftIndex < rightIndex) && (chars[leftIndex] != chosen)){
                ++leftIndex;
            }

            while ((leftIndex < rightIndex) && (chars[rightIndex] == chosen)){
                --rightIndex;
            }

            swapChars(chars, leftIndex, rightIndex);
        }

        return rightIndex;
    }


    private void swapChars(char[] arr, int index1, int index2){
        char tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }


    @Test
    public void test(){
        assertEquals(new String(orderChars(new char[]{})), "");
        assertEquals(new String(orderChars("RGBRGBRGBRGBRGBRGB".toCharArray())), "RRRRRRGGGGGGBBBBBB");
        assertEquals(new String(orderChars("BRRRRR".toCharArray())), "RRRRRB");
        assertEquals(new String(orderChars("GG".toCharArray())), "GG");
        assertEquals(new String(orderChars("GBBBR".toCharArray())), "RGBBB");
    }
}