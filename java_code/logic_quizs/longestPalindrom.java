package logic_quizs;

import org.junit.Test;

public class longestPalindrom {
    public String findLongestPalindrom(String orig){
        String longestPalind = "";

        for (int i = 0; i < orig.length(); ++i){
            String localPalind = expandMiddleOfPalindrom(orig, i);
            if (localPalind != null){
                if (localPalind.length() > longestPalind.length()){
                    longestPalind = localPalind;
                }

                if (longestPalind.length() / 2 > orig.length() - i){ // THIS is an optimization
                    System.out.println("got up to" + i);
                    break;
                }
            }

        }

        return longestPalind;
    }

    private String expandMiddleOfPalindrom(String orig, int i){
        int span = 0;

        while ((i - span - 1 >= 0) && (i  + span < orig.length()) &&
                orig.charAt(i - 1 - span) == orig.charAt(i + span)){
            ++span;
        }

        if (span > 0){
            return orig.substring(i - span, i + span);
        }

        while ((i - 1 - span >= 0) && (i + 1 + span < orig.length()) &&
                orig.charAt(i - 1 - span) == orig.charAt(i + 1 + span)){
            ++span;
        }

        if (span > 0){
            return orig.substring(i - span, i + span + 1);
        }

        return null;
    }

    @Test
    public void test(){
        String res = findLongestPalindrom("fucking string with no palindrome");
        System.out.println(res);
        res = findLongestPalindrom("abcdc");
        System.out.println(res);
        res = findLongestPalindrom("2123421231213");
        System.out.println(res);
        res = findLongestPalindrom("ababcbabcbabab");
        System.out.println(res);

        res = findLongestPalindrom("abbbba edede");
        System.out.println(res);

        res = findLongestPalindrom("bb 1234567");
        System.out.println(res);

        res = findLongestPalindrom("123bb4567");
        System.out.println(res);
    }
}
