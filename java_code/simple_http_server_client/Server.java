package simple_http_server_client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import utils.factory.singleton.FactorySingleton;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server {
    private static HttpServer webServer;
    private final static FactorySingleton factory = FactorySingleton.getInstanceOf();

    public Server(InetSocketAddress address) throws IOException {
        webServer = HttpServer.create(address, 0);
        webServer.createContext("/test", new MyHttpHandler());
        webServer.setExecutor(Executors.newFixedThreadPool(10));
    }

    public void start() {
        webServer.start();
    }

    public void stop(){
        webServer.stop(5);
    }

    private static class MyHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String jsonString = getReqBodeyJson(exchange);
            JSONObject jsonObject;
            String action = null;
            try {
                jsonObject = new JSONObject(jsonString);

            action = (String)jsonObject.get("action");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!exchange.getRequestMethod().equals("POST")){
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String finalAction = action;
            webServer.getExecutor().execute(()->factory.create(finalAction, jsonString));
            exchange.sendResponseHeaders(200, -1);
        }

        private String getReqBodeyJson(HttpExchange exchange) throws IOException {
            try(InputStreamReader inStream = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(inStream)){
                return br.lines().collect(Collectors.joining("\n"));
            }
        }
    }
}