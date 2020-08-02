package quizzs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlipList {
    private static class Node<T>{
        Node<T> next;
        T data;
    }

    public Node<Integer> flipListRec(Node<Integer> head){
        if (head.next == null){
            return head;
        }

        Node<Integer> newHead = flipListRec(head.next);
        head.next.next = head;
        head.next = null;

        return newHead;
    }

    public Node<Integer> flipListItr(Node<Integer> head){
        Node<Integer> before = null;
        Node<Integer> after;

        while(head != null){
            after = head.next;
            head.next = before;
            before = head;
            head = after;
        }

        return before;
    }

    private Node<Integer> makeList(Integer[] arr){
        Node<Integer> head = new Node<>();
        Node<Integer> runner = head;
        runner.data = arr[0];


        for (int i = 1; i < arr.length; ++i){
            Node<Integer> newNode = new Node<>();
            runner.next = newNode;
            runner = newNode;
            runner.data = arr[i];
        }

        return head;
    }

    private Integer[] listToArr(Node<Integer> head){
        List<Integer> list = new ArrayList<>();

        while (head != null){
            list.add(head.data);
            head = head.next;
        }

        Integer[] arr = new Integer[list.size()];
        arr = list.toArray(arr);

        return arr;
    }

    @Test
    public void test(){
        Integer[] arr = {1,2,3,4,5,6,7,8,9};
        Node<Integer> head = makeList(arr);
        Node<Integer> head2 = makeList(arr);

        Integer[] newArr = listToArr(flipListRec(head));
        Integer[] newArr2 = listToArr(flipListItr(head2));


        for (int i = 0; i < arr.length; ++i){
            assert (arr[i].equals(newArr[arr.length - 1 - i]));
        }

        for (int i = 0; i < arr.length; ++i){
            assert (arr[i].equals(newArr2[arr.length - 1 - i]));
        }

        assert (Arrays.equals(newArr,newArr2));
    }
}
