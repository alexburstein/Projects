package logic_quizs;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

public class StackyQueue<T> {
    private final Stack<T> firstElmOnTopStack = new Stack<>();
    private final Stack<T> lastEmlOnTopStack = new Stack<>();
    private boolean isFirstElmOnTop = false;

    private void switchStack(Stack<T> from, Stack<T> to){
        int numberOfElements = from.size();
        for(int i = 0; i < numberOfElements; ++i){
            to.push(from.pop());
        }
        isFirstElmOnTop = !isFirstElmOnTop;
    }

    public int size(){
        return isFirstElmOnTop ? firstElmOnTopStack.size() : lastEmlOnTopStack.size();
    }

    public boolean isEmpty(){
        return isFirstElmOnTop ? firstElmOnTopStack.isEmpty() : lastEmlOnTopStack.isEmpty();
    }

    public T peek(){
        if (!isFirstElmOnTop) {
            switchStack(lastEmlOnTopStack, firstElmOnTopStack);
        }
        return firstElmOnTopStack.peek();
    }

    public T poll(){
        if (!isFirstElmOnTop) {
            switchStack(lastEmlOnTopStack, firstElmOnTopStack);
        }
        return firstElmOnTopStack.pop();
    }

    public void add(T elm){
        if (isFirstElmOnTop) {
            switchStack(firstElmOnTopStack, lastEmlOnTopStack);
        }
        lastEmlOnTopStack.push(elm);
    }

    @Test
    public void testQueue(){
        Queue<Integer> simpleQueue = new LinkedList<>();
        StackyQueue<Integer> stackyQueue = new StackyQueue<>();
        simpleQueue.add(1);
        simpleQueue.add(2);
        simpleQueue.add(3);
        stackyQueue.add(1);
        stackyQueue.add(2);
        stackyQueue.add(3);
        assert(simpleQueue.size() == stackyQueue.size());
        assert(Objects.equals(simpleQueue.poll(), stackyQueue.poll()));
        assert(Objects.equals(simpleQueue.peek(), stackyQueue.peek()));
        simpleQueue.add(4);
        simpleQueue.add(5);
        simpleQueue.add(6);
        stackyQueue.add(4);
        stackyQueue.add(5);
        stackyQueue.add(6);
        assert(simpleQueue.size() == stackyQueue.size());
        assert(Objects.equals(simpleQueue.poll(), stackyQueue.poll()));
        assert(Objects.equals(simpleQueue.peek(), stackyQueue.peek()));
        assert(simpleQueue.size() == stackyQueue.size());
        assert(Objects.equals(simpleQueue.poll(), stackyQueue.poll()));
        assert(Objects.equals(simpleQueue.peek(), stackyQueue.peek()));
    }
}