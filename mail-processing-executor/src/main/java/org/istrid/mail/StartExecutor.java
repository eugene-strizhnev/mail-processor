package org.istrid.mail;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartExecutor {

    public static void main(String... args) {
        new ClassPathXmlApplicationContext(new String[]{"spring-jms-beans.xml"});
    }
}
