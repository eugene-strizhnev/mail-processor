package org.istrid.mail;

import com.google.protobuf.ServiceException;
import io.undertow.Undertow;
import org.istrid.mail.controller.MailController;
import org.istrid.mail.domain.User;
import org.istrid.mail.repository.UserRepository;
import org.reactivestreams.Publisher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.server.reactive.UndertowHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@SpringBootApplication
public class StartRest {

    private static void generateUsers(ApplicationContext context) {
        UserRepository repository = context.getBean(UserRepository.class);

        List<Publisher<Void>> publishers = Stream.of(new User(1, "Vasya"), new User(2, "Petya"), new User(3, "Vova"))
                .map(user -> repository.saveUser(Mono.just(user))).collect(Collectors.toList());
        Flux.concat(publishers).collectList().block();
    }

    public static void main(String[] args) throws IOException, URISyntaxException, ServiceException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/config.xml");
        generateUsers(context);

        MailController mailController = context.getBean(MailController.class);
        RouterFunction<ServerResponse> id = RouterFunctions
                .route(GET("/person/{id}").and(accept(APPLICATION_JSON)), mailController::getUser)
                .andRoute(GET("/person").and(accept(APPLICATION_JSON)), mailController::listPeople)
                .andRoute(POST("/person").and(contentType(APPLICATION_JSON)), mailController::createUser);

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost",
                        new UndertowHttpHandlerAdapter(RouterFunctions.toHttpHandler(id)))
                .build();
        server.start();

//        TestHadoop.testHadoop();
    }
}
