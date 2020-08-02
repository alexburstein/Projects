package quizzs;

/*
the assignment is to recieve a very long list of words ,and a string.
return a list of words that start with the given string.
                DOES NOT WORK CORRECTLY
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trie {
    private final Node dummy = new Node();

    public List<String> getWordsStartWith(String str){
        Node runner = dummy;
        int index = 0;
        for (;str.length() > index;  ++index){
            char x = str.charAt(index);
            for(Node node: runner.letterNodeSet){
                if (node.getLetter() == x){
                    runner = node;
                }
            }
        }

        return runner.returnNextWords(str);
    }

    public void fillTrie(List<String> list){
        for(String str: list){
            dummy.insert(str);
        }
    }

    private static class Node{
        private char letter = '\0';
        private final Set<Node> letterNodeSet = new HashSet<>();

        public Node(){}

        public Node(char firstChar, String restOfWord){
            this.letter = firstChar;

            if (restOfWord.isEmpty()){
                return;
            }
            letterNodeSet.add(new Node(restOfWord.charAt(0), restOfWord.substring(1)));
        }

        public char getLetter() {
            return letter;
        }

        public void insert(String str){
            if (str.isEmpty()){
                return;
            }
            char let = str.charAt(0);
            String restOfWord = str.substring(1);

            for (Node node: letterNodeSet){
                if (node.getLetter() == let){
                    node.insert(restOfWord);
                    return;
                }
            }

            letterNodeSet.add(new Node(let, restOfWord));
        }

        public List<String> returnNextWords(String startWith){
            List<String> list = new ArrayList<>();

            if (letterNodeSet.isEmpty()){
                list.add(String.valueOf(letter));
                return list;
            }

            for(Node node: letterNodeSet){
                List<String> nextList =  node.returnNextWords(startWith.substring(0, startWith.length() - 1));
                for(String str: nextList){
                    String word = startWith.substring(0, startWith.length() - 1) + letter + str;
                    list.add(word);
                }
            }

            return list;
        }
    }

    @Test
    public void test(){
        Trie trie = new Trie();
        List<String> list = new ArrayList<>();
        list.add("alex");
        list.add("lia");
        list.add("noga");
        list.add("shirale");
        list.add("aba");
        list.add("amba");

        trie.fillTrie(list);

        List<String> resWords = trie.getWordsStartWith("a");

        for(String str: resWords){
            System.out.println(str + ".     size of word list is: " + resWords.size());
        }
    }
}
