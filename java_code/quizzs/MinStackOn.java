package quizzs;

import org.junit.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinStackOn {
    public static <T extends Comparable<? super T>> T getMin(Stack<T> stack){
        Stack<T> tmpStack = new Stack<>();
        int size = stack.size();
        int minNumberIndex = 0;
        T minElem = stack.peek();

        for(int i = 0; i < size; ++i){
            T curElm = stack.pop();

            if (curElm.compareTo(minElem) <= 0){
                minElem = curElm;
                minNumberIndex = i;
            }

            tmpStack.add(curElm);
        }

        for(int i = 0; i < size - minNumberIndex - 1; ++i){
            stack.push(tmpStack.pop());
        }

        tmpStack.pop();

        for(int i = 0; i < minNumberIndex; ++i){
            stack.push(tmpStack.pop());
        }

        return minElem;
    }


    @Test
    public void test1(){
        Stack<Integer> stack = new Stack<>();
        int min = 0;

        for (int i = 0; i < 5; ++i){
            stack.push(i);
        }

        assertEquals(min, getMin(stack));

        stack = new Stack<>();

        for (int i = 4; i >= 0; --i){
            stack.push(i);
        }

        assertEquals(min, getMin(stack));
    }

    @Test
    public void test2(){
        Stack<Integer> stack = new Stack<>();
        int min = -10;

        for (int i = 0; i < 5; ++i){
            stack.push(i);
        }

        stack.push(min);

        for (int i = 5; i < 10; ++i){
            stack.push(i);
        }

        assertEquals(min, getMin(stack));
    }
}
