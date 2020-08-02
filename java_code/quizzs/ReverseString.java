package quizzs;

import org.junit.Test;

public class ReverseString {

    public void recReverse(char[] arr, int start, int end){
        if (start > end){
            return;
        }

        char tmp = arr[start];
        arr[start] = arr[end];
        arr[end] = tmp;

        recReverse(arr, start + 1, end - 1);
    }

    @Test
    public void test(){
        char[] hello = "hello".toCharArray();
        recReverse(hello, 0, hello.length - 1);
        System.out.println(hello);

        hello = "hell".toCharArray();
        recReverse(hello, 0, hello.length - 1);
        System.out.println(hello);
    }
}
