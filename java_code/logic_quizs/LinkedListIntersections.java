package logic_quizs;

import org.junit.Test;

public class LinkedListIntersections {

    public Node<Integer> findIntersection(Node<Integer> firstListHead,Node<Integer> secondListHead){
        assert (firstListHead != null && secondListHead != null);

        Node<Integer> firstListRunner = firstListHead;
        Node<Integer> secondListRunner = secondListHead;

        while(firstListRunner != null && secondListRunner != null){
            firstListRunner = firstListRunner.next;
            secondListRunner = secondListRunner.next;
        }

        while(firstListRunner != null){
            firstListRunner = firstListRunner.next;
            firstListHead = firstListHead.next;
        }

        while(secondListRunner != null){
            secondListRunner = secondListRunner.next;
            secondListHead = secondListHead.next;
        }

        while(secondListHead != null){
            if (secondListHead.equals(firstListHead)){
                return secondListHead;
            }
            firstListHead = firstListHead.next;
            secondListHead = secondListHead.next;
        }

        return null;
    }

    public void openIntersection(Node<Integer> firstListHead,Node<Integer> secondListHead){
        Node<Integer> intersection = findIntersection(firstListHead, secondListHead);

        while (!firstListHead.next.equals(intersection)){
            firstListHead = firstListHead.next;
        }

        firstListHead.next = null;
    }

    @Test
    public void test(){
        Node<Integer> first = new Node<>();
        Node<Integer> firstTmp = first;
        Node<Integer> second = new Node<>();
        Node<Integer> secondTmp = second;
        first.data = 0;
        second.data = 0;

        for (int i = 1; i < 5; ++i){
            Node<Integer> nextNode = new Node<>();
            first.next = nextNode;
            first = nextNode;
            first.data = i;
        }

        for (int i = 1; i < 5; ++i){
            Node<Integer> nextNode = new Node<>();
            second.next = nextNode;
            second = nextNode;
            second.data = i;
        }

        first.next = secondTmp.next.next;
        Node<Integer> intersection = findIntersection(firstTmp,secondTmp);
        assert (intersection != null);
        assert (intersection.data.equals(2));
    }

    private static class Node<T>{
        Node<T> next;
        T data;
    }
}
