package org.istrid.mail;

import com.google.protobuf.ServiceException;
import io.undertow.Undertow;
import org.istrid.mail.controller.MailController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.server.reactive.UndertowHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@SpringBootApplication
public class StartRest {


    public static void main(String[] args) throws IOException, URISyntaxException, ServiceException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/config.xml");

        MailController mailController = context.getBean(MailController.class);
        RouterFunction<ServerResponse> routerFunction = RouterFunctions
                .route(POST("/mail"), mailController::saveMail);

//        System.setProperty("io.netty.tmpdir","/Users/istrid/Documents/temp");
        Undertow server = Undertow.builder()
//                .setServerOption(Option.fromString("io.netty.tmpdir", StartRest.class.getClassLoader()), "/var/folders/dz/8kcvp0dj2mvfd8_w41mvkgnr0000gn/T")
                .addHttpListener(8080, "localhost",
                        new UndertowHttpHandlerAdapter(RouterFunctions.toHttpHandler(routerFunction)))
                .build();
        server.start();
    }
}
