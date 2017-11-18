package org.istrid.mail.controller;

import org.istrid.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Controller
public class MailController {

    @Autowired
    private MailService mailService;

    public Mono<ServerResponse> saveMail(ServerRequest request) {
        return request
                .body(BodyExtractors.toMultipartData())
                .flatMap(map ->
                        mailService.saveMail(
                                map.toSingleValueMap().get("mail").content()
                        ).flatMap(result -> ServerResponse.ok().build())
                );
    }
}
