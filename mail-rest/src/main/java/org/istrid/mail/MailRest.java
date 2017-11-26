package org.istrid.mail;

import org.apache.ibatis.type.MappedTypes;
import org.istrid.mail.domain.CustomJar;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource("classpath:spring/config.xml")
@MappedTypes(CustomJar.class)
@MapperScan("org.istrid.mail.mapper")
public class MailRest {
    public static void main(String[] args) {
        SpringApplication.run(MailRest.class, args);
    }

}

