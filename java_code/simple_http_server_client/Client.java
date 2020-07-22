package simple_http_server_client;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Client {
    private final HttpClient client = HttpClient.newBuilder().build();
    private final String uriPath;

    public Client(String uriPath){
        this.uriPath = uriPath;
    }

    public int createClient(String name, String businessNumber, String phone, String address, String contact)
                                                            throws ExecutionException, InterruptedException {
        Map<String, String> map = new HashMap<>();
        map.put("action", "create client");
        map.put("name", name);
        map.put("buisness number", businessNumber);
        map.put("phone number", phone);
        map.put("address", address);
        map.put("contact name", contact);

        return getResponseStatusCode(buildPOSTRequest(new JSONObject(map).toString()));
    }

    public int addProduct(String name, String serialNum, String pathToJar) throws IOException, ExecutionException, InterruptedException {
        String jsonString;
        try(FileInputStream fileStream = new FileInputStream(new File(pathToJar))) {
            jsonString = Base64.getEncoder().encodeToString(fileStream.readAllBytes());
        }

        Map<String, String> map = new HashMap<>();
        map.put("action", "add product");
        map.put("name", name);
        map.put("serial num", serialNum);
        map.put("jar", jsonString);

        return getResponseStatusCode(buildPOSTRequest(new JSONObject(map).toString()));
    }

    private HttpRequest buildPOSTRequest(String jsonString) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uriPath))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
    }

    private int getResponseStatusCode(HttpRequest request) throws ExecutionException, InterruptedException {
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode).get();
    }
}
