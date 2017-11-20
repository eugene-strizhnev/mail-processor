package org.istrid.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource("classpath:spring/config.xml")
public class MailRest {
    public static void main(String[] args) {
        SpringApplication.run(MailRest.class, args);
    }

}

