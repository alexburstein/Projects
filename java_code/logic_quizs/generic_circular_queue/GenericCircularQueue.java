package logic_quizs.generic_circular_queue;

/*
create a circular queue
 */

public class GenericCircularQueue<T>{
    private static int Q_SIZE;
    Object[] queue;
    int numOfElements = 0;
    int firstElementIndex = 0;

    public GenericCircularQueue(int queueSize){
        Q_SIZE = queueSize;
        queue = new Object[Q_SIZE];
    }

    public GenericCircularQueue(){this(20);}

    public void push(T element){
        queue[(firstElementIndex + numOfElements) % Q_SIZE] = element;
        ++numOfElements;
    }

    public T pop(){
        @SuppressWarnings("unchecked")
        T popped =(T) queue[firstElementIndex];
        firstElementIndex = (firstElementIndex + 1) % Q_SIZE;
        --numOfElements;
        return popped;
    }
}
