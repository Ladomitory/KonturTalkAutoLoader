package ru.ladomitory.kontur.talkApi.httpApi;

import ru.ladomitory.kontur.talkApi.util.JSONMap;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpLoader {
    private final HttpClient CLIENT;
    private final String AUTH_TOKEN;

    public HttpLoader(String authToken) {
        CLIENT = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        AUTH_TOKEN = authToken;
    }

    protected HttpResponse<String> createGetRequest(String url, JSONMap params) {
        String paramsString = params.toJSONString();

        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create(url))
                .header("X-Auth-Token", AUTH_TOKEN)
                .timeout(Duration.ofMinutes(5))
                .method("GET", HttpRequest.BodyPublishers.ofString(paramsString))
                .build();
        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    protected HttpResponse<String> createGetRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create(url))
                .header("X-Auth-Token", AUTH_TOKEN)
                .timeout(Duration.ofMinutes(5))
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
