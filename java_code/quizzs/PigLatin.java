package quizzs;
/*
Code Wars task
 */


import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PigLatin {
    public static String pigIt(String str) {
        return Stream.of(str.split(" ")).map(s ->
                (s.matches(".*[,.!?]") ? s : s.substring(1) + s.charAt(0) + "ay")
                 + " ").collect(Collectors.joining()).trim();
    }


    @Test
    public void FixedTests() {
        assertEquals("igPay atinlay siay oolcay", pigIt("Pig latin is cool"));
        assertEquals("hisTay siay ymay tringsay", pigIt("This is my string"));
        assertEquals("hisTay ! ?", pigIt("This ! ?"));

    }
}
