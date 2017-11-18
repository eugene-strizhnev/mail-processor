package org.istrid.mail.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MailQueueListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        String lastMessage;
        try {
            lastMessage = ((TextMessage) message).getText();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        System.out.println("MESSAGE RECEIVED BY FIRST ONE: " + lastMessage);
    }
}
