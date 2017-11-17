package org.istrid.mail.controller;

import org.istrid.mail.domain.User;
import org.istrid.mail.repository.UserRepository;
import org.istrid.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
public class MailController {

    @Autowired
    private MailService repository;

    public Mono<ServerResponse> listPeople(ServerRequest request) {
        Flux<User> people = repository.allPeople();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(people, User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        return ServerResponse.ok().build(repository.saveUser(user));
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        int userId = Integer.valueOf(request.pathVariable("id"));
        Mono<User> userMono = this.repository.getUser(userId);
        return userMono
                .flatMap(
                        user ->
                                ServerResponse.ok()
                                        .contentType(APPLICATION_JSON)
                                        .body(BodyInserters.fromObject(user))
                ).switchIfEmpty(
                        ServerResponse.notFound().build()
                );
    }
}
