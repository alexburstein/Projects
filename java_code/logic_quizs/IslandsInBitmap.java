package logic_quizs;

/*
receive a matrix of booleans.
return the number of "islands" of 'true's there are in the matrix.
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IslandsInBitmap {
    private boolean[][] visited;

    public int findIslandInBitmap(boolean[][] matrix){
        int count = 0;
        visited = new boolean[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; ++i){
            for(int j = 0; j < matrix[0].length; ++j){
                if (matrix[i][j] && !visited[i][j]){
                    ++count;
                    recSignVisited(matrix,i, j);
                }
            }
        }

        return count;
    }

    private void recSignVisited(boolean[][] matrix, int rowIndex, int columnIndex){
        if ((rowIndex < 0) || (columnIndex < 0) || (columnIndex > (matrix[0].length - 1)) ||
                rowIndex > (matrix.length - 1) ||  visited[rowIndex][columnIndex] ||
                !matrix[rowIndex][columnIndex]){
            return;
        }

        visited[rowIndex][columnIndex] = true;

        for (int i = -1; i < 2 ; ++i){
            for(int j = -1; j < 2; ++j){
                recSignVisited(matrix,rowIndex + i, columnIndex + j);
            }
        }
    }

    @Test
    public void test1(){
        boolean[][] matrix = new boolean[2][2];
        matrix[0][0] = true;
        matrix[1][1] = true;
        assertEquals(1, findIslandInBitmap(matrix));
        matrix = new boolean[2][2];
        matrix[0][1] = true;
        matrix[1][0] = true;
        assertEquals(1, findIslandInBitmap(matrix));
        matrix = new boolean[2][2];
        matrix[1][0] = true;
        matrix[0][1] = true;
        assertEquals(1, findIslandInBitmap(matrix));
    }

    @Test
    public void test2(){
        boolean[][] matrix = new boolean[10][10];

        matrix[0][0] = true;
        matrix[0][9] = true;
        matrix[9][9] = true;
        matrix[9][0] = true;

        assertEquals(4, findIslandInBitmap(matrix));
    }

    @Test
    public void test3(){
        int matrixSize = 10;
        boolean[][] matrix = new boolean[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; ++i){
            matrix[0][i] = true;
            matrix[i][0] = true;
            matrix[matrixSize - 1][i] = true;
            matrix[i][matrixSize - 1] = true;
        }
        assertEquals(1, findIslandInBitmap(matrix));
    }

    @Test
    public void test4(){
        int matrixSize = 9;
        boolean[][] matrix = new boolean[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i += 2){
            for (int j = 0; j < matrixSize; j += 2){
                matrix[i][j] = true;
            }
        }
        assertEquals(25, findIslandInBitmap(matrix));
    }

    @Test
    public void test5(){
        int matrixSize = 66;
        boolean[][] matrix = new boolean[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize / 2; ++i){
            for (int j = matrixSize / 2; j < matrixSize; ++j){
                matrix[i][j] = true;
                matrix[i][matrixSize - j] = true;
                matrix[matrixSize - 1 - i][j] = true;
                matrix[matrixSize - 1 - i][matrixSize - j] = true;
            }
        }

        assertEquals(1, findIslandInBitmap(matrix));
    }
}
