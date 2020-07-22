package logic_quizs;

import org.junit.Test;

public class FindAllPerm {

//    public void shrinkingPremPrint(String str, int rotateIndex){
//        for (int i = 0; str.length() > i; ++i){
//            printPerm(str.substring(i), 0);
//        }
//    }

    public void printPerm(String str, int rotateIndex){
        if (rotateIndex == str.length() - 1){
            System.out.println(str);
            return;
        }

        String firstPart = str.substring(0,rotateIndex);
        String rotatingPart = str.substring(rotateIndex);

        for(int i = 0; i < rotatingPart.length(); ++i){
            rotatingPart = rotatingPart.substring(1) + rotatingPart.charAt(0);
            printPerm(firstPart + rotatingPart, rotateIndex + 1);
        }
    }

    @Test
    public void test(){
        printPerm("alex", 0);
//        shrinkingPremPrint("alex", 0);
    }
}
