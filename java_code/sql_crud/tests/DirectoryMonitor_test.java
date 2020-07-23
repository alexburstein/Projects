package sql_crud.tests;

import sql_crud.DirectoryMonitor;
import utils.observer.CallBack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.WatchKey;
import java.util.function.Consumer;


public class DirectoryMonitor_test {

    String filePath = "C:\\Users\\WIN10\\IdeaProjects\\WorkSpace\\src\\IOTProject\\sqlCrud\\tmpMonitored\\monitored.txt";
    boolean isChanged = false;


    @Test
    public void basicTest(){
        initMonitor();
        try {Thread.sleep(100);} catch (InterruptedException ignored) {}
        assertFalse(isChanged);
        changeFile();
        try {Thread.sleep(100);} catch (InterruptedException ignored) {}
        assertTrue(isChanged);
        isChanged = false;
    }



    private void changeFile(){
        RandomAccessFile modifier = null;
        try {
            modifier = new RandomAccessFile(filePath, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert modifier != null;
        try {
            modifier.seek(modifier.length());
            modifier.writeBytes("\nhello");
            modifier.writeBytes("\nhow are you?");
            modifier.writeBytes("\nall cool?");

            modifier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMonitor(){
        DirectoryMonitor monitor = null;
        monitor = new DirectoryMonitor(filePath);

        new Thread(monitor).start();

        Consumer<WatchKey> func = (x)->{System.out.println("change detected"); isChanged = true;};

        CallBack<WatchKey> callBack = new CallBack<>(func);
        assert monitor != null;
        monitor.register(callBack);
    }
}
