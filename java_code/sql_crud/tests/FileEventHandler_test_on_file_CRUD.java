package sql_crud.tests;

import sql_crud.FileEventHandler;
import sql_crud.stubs.FileCRUDImp;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileEventHandler_test_on_file_CRUD {
    String origFilePath = "C:\\Users\\WIN10\\IdeaProjects\\WorkSpace\\src\\IOTProject\\sqlCrud\\tmpMonitored\\monitored.txt";
    String backupPath = "C:\\Users\\WIN10\\IdeaProjects\\WorkSpace\\src\\IOTProject\\sqlCrud\\tmpMonitored\\backup.txt";
    InetSocketAddress serverAddress = new InetSocketAddress("localhost",6666);

    @Test
    public void aTest() {
        FileEventHandler fileEventHandler;
        FileCRUDImp backupModifier;
        FileCRUDImp origFileModifier;

        try {
            backupModifier = new FileCRUDImp(backupPath);
            origFileModifier = new FileCRUDImp(origFilePath);
            backupModifier.eraseFileContent();
            origFileModifier.eraseFileContent();

            origFileModifier.create("one line before event handler init");

            System.out.println("orig file is like this****************************************:");
            origFileModifier.printFile();

            System.out.println("backup file is like this*****************************************:");
            backupModifier.printFile();

            fileEventHandler = new FileEventHandler(origFilePath,serverAddress.getHostName(),serverAddress.getPort());

            Thread.sleep(300);
            origFileModifier.create("first update");
            origFileModifier.create("second update");
            origFileModifier.create("third update");

            Thread.sleep(300);

            origFileModifier.create("forth update");

            System.out.println("orig file is like this****************************************:");
            origFileModifier.printFile();

            System.out.println("backup file is like this*****************************************:");
            backupModifier.printFile();

            List<String> origList = Files.readAllLines(Paths.get(origFilePath), StandardCharsets.UTF_8);
            List<String> backupList = Files.readAllLines(Paths.get(backupPath), StandardCharsets.UTF_8);

            System.out.println("origList size is:" + origList.size());
            System.out.println("backupList size is:" + backupList.size());
            assert(origList.equals(backupList));

            origFileModifier.create("another addition");
            origFileModifier.create("and another one........");

            Thread.sleep(300);

            origFileModifier.close();
            backupModifier.close();
            fileEventHandler.stop();
            Thread.sleep(600);

            origList = Files.readAllLines(Paths.get(origFilePath), StandardCharsets.UTF_8);
            backupList = Files.readAllLines(Paths.get(backupPath), StandardCharsets.UTF_8);

            Thread.sleep(300);

            System.out.println("origList size is:" + origList.size());
            System.out.println("backupList size is:" + backupList.size());

            assert(origList.equals(backupList));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
