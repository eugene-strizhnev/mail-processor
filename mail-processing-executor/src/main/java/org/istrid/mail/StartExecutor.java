package org.istrid.mail;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartExecutor {

    public static void main(String... args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jms-beans.xml");
        context.registerShutdownHook();
    }
}
