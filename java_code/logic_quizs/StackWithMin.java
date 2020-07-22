package logic_quizs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class StackWithMin<T extends Comparable<? super T>> extends Stack<T> {
    private final Stack<T> s1;

    public StackWithMin() {
        s1 = new Stack<>();
    }

    public T push(T elm){
        return s1.push(elm);
    }

    public T pop(){
        return s1.pop();
    }

    public int size(){
        return s1.size();
    }

    public boolean isEmpty(){
        return s1.isEmpty();
    }

    public T min(){
        List<T> list = new ArrayList<>();
        int stackSize = s1.size();
        T min = s1.peek();
        int elmListIndex = 0;

        for (int i = 0; i < stackSize; ++i){
            T curElm = s1.pop();
            if (curElm.compareTo(min) < 0){
                min = curElm;
                elmListIndex = list.size();
            }
            list.add(curElm);
        }

        for (int i = stackSize - 1; i > elmListIndex ; --i){
            s1.push(list.get(i));
        }

        for (int i = elmListIndex - 1; i >= 0 ; --i) {
            s1.push(list.get(i));
        }

        return min;
    }

    @Test
    public void test(){
        StackWithMin<Integer> stack = new StackWithMin<>();
        Random rand = new Random(99);
        for (int i = 0; i < 100; ++i){
            stack.push(rand.nextInt(100));
        }
        while (!stack.isEmpty()){
            int localMin1 = stack.min();
            int localMin2 = stack.min();
            assert(localMin1 <= localMin2);
        }
    }
}