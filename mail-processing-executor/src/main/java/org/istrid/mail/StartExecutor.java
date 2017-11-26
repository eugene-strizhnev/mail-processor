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
@ImportResource("classpath:spring/spring-jms-beans.xml")
@MappedTypes(CustomJar.class)
@MapperScan("org.istrid.mail.mapper")
public class StartExecutor {

    public static void main(String... args) throws InterruptedException {
        SpringApplication
                .run(StartExecutor.class, args)
                .registerShutdownHook();
        Thread.sleep(30000);
    }
}
