package logic_quizs;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuValidator {
    public static boolean check(int[][] sudoku) {
        for (int i = 0; i < 9; ++i){
            int[] column = new int[9];
            for (int j = 0; j < 9; ++j){
                column[j] = sudoku[j][i];
            }

            if (isNumberSetInvalid(column) || isNumberSetInvalid(sudoku[i])){ // check columns and rows
                return false;
            }
        }

        for (int i = 0; i < 9; i += 3){ // check blocks
            for (int j = 0; j < 9; j += 3){
                if (isNumberSetInvalid(blockToArr(sudoku, i, j))){
                    return false;
                }
            }
        }

        return true;
    }

    private static int[] blockToArr(int[][] sudoku, int columnIndex, int rowIndex){
        int[] nine = new int[9];
        int newArrIndex = 0;

        for (int i = columnIndex; i < columnIndex + 3; ++i){
            for(int j = rowIndex; j < rowIndex + 3; ++j, ++newArrIndex){
                nine[newArrIndex] = sudoku[i][j];
            }
        }

        return nine;
    }


    private static boolean isNumberSetInvalid(int[] arr){
        int sum = 0;
        Set<Integer> aSet = new HashSet<>();

        for (int i = 0; i < 9; ++i) {
            aSet.add(arr[i]);
            sum += arr[i];

            if(arr[i] > 9 || arr[i] < 1){
                return true;
            }
        }

        return (aSet.size() != 9) || (sum != 45);
    }


    @Test
    public void simpleTest() {
        int[][] sudoku = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        assertTrue(SudokuValidator.check(sudoku));

        sudoku[0][0]++;
        sudoku[1][1]++;
        sudoku[0][1]--;
        sudoku[1][0]--;

        assertFalse(SudokuValidator.check(sudoku));

        sudoku[0][0]--;
        sudoku[1][1]--;
        sudoku[0][1]++;
        sudoku[1][0]++;

        sudoku[4][4] = 0;

        assertFalse(SudokuValidator.check(sudoku));
    }
}
