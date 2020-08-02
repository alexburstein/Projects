package quizzs.generic_circular_queue;


import org.junit.jupiter.api.Test;

public class Tests {

    @Test
    public void test1(){
        GenericCircularQueue<Character> q = new GenericCircularQueue<>(20);
        char[] word = "hello, I am alex".toCharArray();
        StringBuilder str = new StringBuilder();
        for(int x = 0; x < 10000; ++x){
            for (char c : word) {
                q.push(c);
            }

            for (int i = 0; i < word.length; ++i){
                str.append(q.pop());
            }

            System.out.println(str);
            str.setLength(0);
        }
    }
}
