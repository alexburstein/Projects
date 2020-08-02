package quizzs;

/*
"7777...8?!??!", exclaimed Bob, "I missed it again! Argh!" Every time there's an interesting number coming up, he notices and then promptly forgets. Who doesn't like catching those one-off interesting mileage numbers?

Let's make it so Bob never misses another interesting number. We've hacked into his car's computer, and we have a box hooked up that reads mileage numbers. We've got a box glued to his dash that lights up yellow or green depending on whether it receives a 1 or a 2 (respectively).

It's up to you, intrepid warrior, to glue the parts together. Write the function that parses the mileage number input, and returns a 2 if the number is "interesting" (see below), a 1 if an interesting number occurs within the next two miles, or a 0 if the number is not interesting.

Note: In Haskell, we use No, Almost and Yes instead of 0, 1 and 2.

"Interesting" Numbers
Interesting numbers are 3-or-more digit numbers that meet one or more of the following criteria:

Any digit followed by all zeros: 100, 90000
Every digit is the same number: 1111
The digits are sequential, incementing†: 1234
The digits are sequential, decrementing‡: 4321
The digits are a palindrome: 1221 or 73837
The digits match one of the values in the awesomePhrases array
† For incrementing sequences, 0 should come after 9, and not before 1, as in 7890.
‡ For decrementing sequences, 0 should come after 1, and not before 9, as in 3210.
 */

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatchingCarMileageNumbers {
    private static final String ascendingList = "1234567890";
    private static final String descendingList = "9876543210";


    public static int isInteresting(int number, int[] awesomePhrases) {
        if (isNumberInteresting(number,awesomePhrases)){
            return 2;
        }

        if (isNumberInteresting(number + 1,awesomePhrases) ||
                isNumberInteresting(number + 2,awesomePhrases)){
            return 1;
        }

        return 0;
    }

    private static boolean isNumberInteresting(int number, int[] awesomePhrases){
        if (number < 100){ return false;}

        String numString = String.valueOf(number);

        return Arrays.stream(awesomePhrases).anyMatch(x-> x == number) ||
                descendingList.contains(numString) || // descending list
                ascendingList.contains(numString) || // ascending list
                numString.chars().allMatch(c-> c == numString.charAt(0)) || // all same
                Integer.parseInt(numString.substring(1)) == 0 || // all but first zeros
                numString.equals(new StringBuilder(numString).reverse().toString()); // palindrome
    }

    @Test
    public void testTooSmall() {
        assertEquals(0, isInteresting(3, new int[]{1337, 256}));
    }

    @Test
    public void testAlmostAwesome() {
        assertEquals(1, isInteresting(1336, new int[]{1337, 256}));
    }

    @Test
    public void testAwesome() {
        assertEquals(2, isInteresting(1337, new int[]{1337, 256}));
    }

    @Test
    public void testFarNotInteresting() {
        assertEquals(0, isInteresting(11208, new int[]{1337, 256}));
    }

    @Test
    public void testAlmostInteresting() {
        assertEquals(1, isInteresting(11209, new int[]{1337, 256}));
    }

    @Test
    public void testInteresting() {
        assertEquals(2, isInteresting(11211, new int[]{1337, 256}));
    }

    @Test
    public void anotherTest() {
        assertEquals(1, isInteresting(98, new int[]{}));
    }
}
