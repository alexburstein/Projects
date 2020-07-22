package sql_crud.stubs;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileCRUDImp_test {
    String pathToFile = "C:\\Users\\WIN10\\IdeaProjects\\WorkSpace\\src\\IOTProject\\sqlCrud\\tmpMonitored\\backup.txt";
    List<String> list = new ArrayList<>();

    @Test
    public void basic(){
        init();
        FileCRUDImp crud = null;
        long lineNum;
        try {
            crud = new FileCRUDImp(pathToFile);
            crud.eraseFileContent();
            lineNum = crud.create(list.get(0));
        }
        catch (IOException e) {
        e.printStackTrace();
        }
        assert crud != null;
        crud.create(list.get(1));
        lineNum = crud.create(list.get(2));

        System.out.println(lineNum);

        List<String> linesInFile = linesInFile();

        assert (linesInFile.equals(list));
    }

    private List<String> linesInFile(){
        List<String> linesInFile = null;
        try {
            linesInFile = Files.readAllLines(Paths.get(pathToFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesInFile;
    }

    private void init(){
        list.add("hello, this is me");
        list.add("me again, nothing to worry about");
        list.add("don't call the police!");
    }
}
