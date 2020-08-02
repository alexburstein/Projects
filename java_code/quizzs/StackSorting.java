package quizzs;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackSorting {
    public Stack<Integer> sortTheStack(Stack<Integer> stack){

        List<Integer> list = new LinkedList<>();

        while(!stack.isEmpty()){
            list.add(stack.pop());
        }

        insertionSort(list);

        while(!list.isEmpty()){
            stack.push(list.remove(0));
        }

        return stack;
    }

    private void insertionSort(List<Integer> list){
        int listSize = list.size();
        for (int i = 0; i < listSize; ++i){
            Integer curElm = list.get(i);
            for (int j = 0 ; j < i; ++j){
                if (curElm.compareTo(list.get(j)) < 0){
                    Integer tmp = list.remove(i);
                    list.add(j, tmp);
                    break;
                }
            }
        }

    }

    @Test
    public void test(){
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 100; ++i){
            stack.push(100 - 1 - i);
        }

        stack = sortTheStack(stack);
        assertEquals(99, stack.pop());
        assertEquals(98, stack.pop());

        stack.clear();

        int[] arr = {0, -6, 12, 32, -88};

        for (int value : arr) {
            stack.push(value);
        }

        stack = sortTheStack(stack);

        Arrays.sort(arr);

        for (int i = 0; i < arr.length; ++i){
            assertEquals(arr[arr.length - 1 - i], stack.pop());
        }
    }
}
