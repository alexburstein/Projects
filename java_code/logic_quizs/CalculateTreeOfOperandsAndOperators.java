package logic_quizs;

/*
you receive a tree that contains arithmetic operators in all the nodes except the leafs.
the leafs contain numbers. make a function that calculates the tree, in post-order.
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateTreeOfOperandsAndOperators {
    public static int calculateTree(Node root){
        return switch (root.op){
            case "*" -> calculateTree(root.child[0]) * calculateTree(root.child[1]);
            case "+" -> calculateTree(root.child[0]) + calculateTree(root.child[1]);
            case "-" -> calculateTree(root.child[0]) - calculateTree(root.child[1]);
            case "/" -> calculateTree(root.child[0]) / calculateTree(root.child[1]);
            default -> Integer.parseInt(root.op);
        };
    }


    @Test
    public void test1(){
        assertEquals(45, calculateTree(initTestTree2()));
        assertEquals(-16, calculateTree(initTestTree1()));
        assertEquals(-87, calculateTree(initTestTree3()));

    }


    private static Node initTestTree1(){
        Node root = new Node("*");
        root.child[0] = new Node("-");
        root.child[1] = new Node("+");
        root.child[0].child[0] = new Node("7");
        root.child[0].child[1] = new Node("9");
        root.child[1].child[0] = new Node("/");
        root.child[1].child[0].child[0] = new Node("9");
        root.child[1].child[0].child[1] = new Node("3");
        root.child[1].child[1] = new Node("5");

        return root;
    }

    private static Node initTestTree2(){
        Node root = new Node("*");
        root.child[0] = new Node("+");
        root.child[1] = new Node("+");
        root.child[0].child[0] = new Node("3");
        root.child[0].child[1] = new Node("2");
        root.child[1].child[0] = new Node("4");
        root.child[1].child[1] = new Node("5");

        return root;
    }

    private static Node initTestTree3(){
        Node root = new Node("/");
        root.child[0] = new Node("-");
        root.child[1] = new Node("-");
        root.child[0].child[0] = new Node("0");
        root.child[0].child[1] = new Node("-348");
        root.child[1].child[0] = new Node("-6");
        root.child[1].child[1] = new Node("-2");

        return root;
    }


    private static class Node {
        String op;
        Node[] child = new Node[2];

        public Node(String op) {
            this.op = op;
        }
    }
}
