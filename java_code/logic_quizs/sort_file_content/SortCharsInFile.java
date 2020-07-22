package logic_quizs.sort_file_content;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SortCharsInFile {
    public void sortCharsInFile(String path){
        int[] histogram = new int[254];
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(path, "rws");

            while (true){
                int c = randomAccessFile.read();

                if (c == -1){
                    break;
                }

                histogram[c] += 1;
            }

            randomAccessFile.seek(0);

            for (int i = 0; i < histogram.length; ++i){
                for (int j = 0; j < histogram[i]; ++j){
                    randomAccessFile.writeChar(i);
                }
            }

            randomAccessFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        sortCharsInFile("tmp");
    }
}
