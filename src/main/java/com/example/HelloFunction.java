package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.function.Function;

import com.example.model.Greeting;
import com.example.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

@Component
public class HelloFunction implements Function<Mono<User>, Mono<Greeting>> {

    public Mono<Greeting> apply(Mono<User> mono) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create("https://rest.ensembl.org/info/ping?content-type=application/json"))
              .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "request failed", e);
        }

        return mono.map(user -> new Greeting("Hello, " + user.getName() +
            "!\nExternal HTTP Request Reponse: " + response.body()));
    }
}
