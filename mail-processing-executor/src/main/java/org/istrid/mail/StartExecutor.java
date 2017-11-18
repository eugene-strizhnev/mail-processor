package org.istrid.mail;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartExecutor {

    public static void main(String... args) {
        new ClassPathXmlApplicationContext("spring-jms-beans.xml");
    }
}
