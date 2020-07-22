package simple_http_server_client;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerClientTest {

    @Test
    public void test(){
        Client client = new Client("http://localhost:80/test");
        String pathToJar = "C:\\jars\\json-.jar";
        Server testServer = null;

        try {testServer = new Server(new InetSocketAddress(80));}
        catch (IOException e) {System.err.println("problem with the address");}

        assert testServer != null;
        testServer.start();

        try {Thread.sleep(3000);} catch (InterruptedException ignored) {}

        int x = 0;
        try { x = client.addProduct("telephone", "1234567", pathToJar);
            assert x / 100 == 2;
            System.out.println("method came back as:" + x);


            x = client.createClient("alex", "12341", "1234", "here", "dudu");
            System.out.println("method came back as:" + x);
            assert x / 100 == 2;

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {Thread.sleep(2000);} catch (InterruptedException ignored) {}

        testServer.stop();
    }

}
