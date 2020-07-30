package sql_crud.tests;

import sql_crud.FileEventHandler;
import sql_crud.SQLServerLogic;
import sql_crud.SqlCrudImp;
import sql_crud.stubs.FileCRUDImp;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Integration_test {
    SQLServerLogic server;
    FileEventHandler fileEventHandler;
    FileCRUDImp fileCRUDImp;
    SqlCrudImp sqlCrudImp;
    Thread serverThread;
    String origFilePath = "XXXXXX";

    private void init() {
        try {
            server = new SQLServerLogic();
            serverThread = new Thread(server);
            serverThread.start();
            fileEventHandler = new FileEventHandler(origFilePath, "localhost", 6666);
            fileCRUDImp = new FileCRUDImp(origFilePath);
            sqlCrudImp = new SqlCrudImp();

        } catch (ClassNotFoundException | IOException | SQLException e) {
            System.err.println(e.getMessage() + Arrays.toString(e.getCause().getStackTrace()));
        }
    }

    private void stop(){
        fileEventHandler.stop();
        server.stop();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void basic(){
        init();

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        try {fileCRUDImp.close();} catch (IOException ignored) {}

        stop();
    }

    @Test
    public void secondTest(){
        init();

        int lastLine = sqlCrudImp.create("bibi");

        for (int i = 0; i < lastLine; ++i){
            sqlCrudImp.delete(1);
        }

        String check = "blablabla";

        fileCRUDImp.create(check);
        try {fileCRUDImp.close();} catch (IOException ignored) {}

        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        String res = sqlCrudImp.read(1);

        assertEquals(res, check);

        sqlCrudImp.close();
        stop();
    }
}
