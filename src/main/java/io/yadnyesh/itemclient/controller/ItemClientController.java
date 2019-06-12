package io.yadnyesh.itemclient.controller;

import io.yadnyesh.itemclient.domain.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ItemClientController {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @GetMapping("/client/retrieve")
    public Flux<Item> getAllItemsUsingRetrieve() {
       return webClient.get().uri("/v1/items")
                .retrieve()
                .bodyToFlux(Item.class)
                .log("Items in Client retrieve: ");
    }

    @GetMapping("/client/exchange")
    public Flux<Item> getAllItemsUsingExchange() {
        return webClient.get().uri("/v1/items")
                .exchange()
                .flatMapMany(clientResponse ->
                    clientResponse.bodyToFlux(Item.class))
                .log("Items in Client exchange: ");
    }

    @GetMapping("/client/retrieve/singleitem")
    public Mono<Item> getSingleItemUsingRetrieve() {

        String id = "ABC";

        return webClient.get().uri("/v1/items/{id}", id)
                .retrieve()
                .bodyToMono(Item.class)
                .log("Item in Client retrieve single Item: ");
    }

    @GetMapping("/client/exchange/singleitem")
    public Mono<Item> getSingleItemUsingExchange() {

        String id = "ABC";

        return webClient.get().uri("/v1/items/{id}", id)
                .exchange()
                .flatMap(clientResponse ->
                        clientResponse.bodyToMono(Item.class))
                .log("Items in Client exchange: ");
    }
}
