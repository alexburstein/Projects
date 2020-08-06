package quizzs;
/*
Write a function that, given a string of text (possibly with punctuation and line-breaks), returns an array of the top-3 most occurring words, in descending order of the number of occurrences.

        Assumptions:
        A word is a string of letters (A to Z) optionally containing one or more apostrophes (') in ASCII. (No need to handle fancy punctuation.)
        Matches should be case-insensitive, and the words in the result should be lowercased.
        Ties may be broken arbitrarily.
        If a text contains fewer than three unique words, then either the top-2 or top-1 words should be returned, or an empty array if a text contains no words.
*/

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TopWords {
    public static List<String> top3(String s) {
        HashMap<String, Integer> map = new HashMap<>();

        for(String str: s.split("[^a-zA-Z']+")){
            if(str.isBlank() || str.charAt(0) == '\''){
                continue;
            }
            map.merge(str.toLowerCase(), 1, Integer::sum);
        }

        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a,b)-> b.getValue() - a.getValue());

        for(Map.Entry<String, Integer> entry: map.entrySet()){
            pq.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
        }

        List<String> resList = new ArrayList<>();

        for(int i = 0; i < 3 && !pq.isEmpty(); ++i){
            resList.add(pq.poll().getKey());
        }

        return resList;
    }


    @Test
    public void sampleTests() {
        assertEquals(Arrays.asList("e", "d", "a"),    top3("a a a  b  c c  d d d d  e e e e e"));
        assertEquals(Arrays.asList("e", "ddd", "aa"), top3("e e e e DDD ddd DdD: ddd ddd aa aA Aa, bb cc cC e e e"));
        assertEquals(Arrays.asList("won't", "wont"),  top3("  //wont won't won't "));
        assertEquals(Arrays.asList("e"),              top3("  , e   .. "));
        assertEquals(Arrays.asList(),                 top3("  ...  "));
        assertEquals(Arrays.asList(),                 top3("  '  "));
        assertEquals(Arrays.asList(),                 top3("  '''  "));
        assertEquals(Arrays.asList("a", "of", "on"),  top3(String.join("\n", "In a village of La Mancha, the name of which I have no desire to call to",
                "mind, there lived not long since one of those gentlemen that keep a lance",
                "in the lance-rack, an old buckler, a lean hack, and a greyhound for",
                "coursing. An olla of rather more beef than mutton, a salad on most",
                "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra",
                "on Sundays, made away with three-quarters of his income.")));
    }
}
