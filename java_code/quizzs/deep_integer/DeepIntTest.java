package quizzs.deep_integer;

import org.junit.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeepIntTest {

    /**
     * a method to sum up deep integers by depth
     *
     * @param input string representing collections of integers (separated by commas)
     *              , and deep integers (bound by square brackets).
     * @return int, sum of all integers in the given collection, multiplied by the depth they are in.
     */
    public static int sumUpByDepth(String input) {
        if (input.length() < 1) {
            return 0;
        }
        checkInputFormat(input);
        Node root = new DeepInt(input, 0);

        return root.getSum();
    }


    @Test
    public void test() {
        assertEquals(27, sumUpByDepth("[1,[4,[6]]]"));
        assertEquals(99, sumUpByDepth("[1,2,3[4,5,6[6,7,8]]]"));
        assertEquals(1, sumUpByDepth("[1]"));
        assertEquals(0, sumUpByDepth(""));

        assertEquals(33, sumUpByDepth("[[[4]][[7]]]"));
        assertEquals(15, sumUpByDepth("[[4],7]"));

        assertThrows(IllegalArgumentException.class, () -> sumUpByDepth("[y]"));
        assertThrows(IllegalArgumentException.class, () -> sumUpByDepth("[4"));
        assertThrows(IllegalArgumentException.class, () -> sumUpByDepth("[4]6"));
    }

    private static void checkInputFormat(String input) throws IllegalArgumentException {
        char[] inputChars = input.toCharArray();
        Stack<Character> stack = new Stack<>();
        boolean isCommaAllowed = false;
        boolean isNumberAllowed = false;

        for (char c : inputChars) {
            if (c == '[') {
                stack.push(c);
                isNumberAllowed = true;
            } else if (c == ']' && !stack.isEmpty()) {
                stack.pop();
                isNumberAllowed = false;
                isCommaAllowed = true;
            } else if (c >= '0' && c <= '9' && isNumberAllowed) {
                isCommaAllowed = true;
            } else if (c == ',' && isCommaAllowed) {
                isCommaAllowed = false;
                isNumberAllowed = true;

            } else {
                throw new IllegalArgumentException("input string is not in the right format");
            }
        }

        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("input string is not in the right format");
        }
    }

}