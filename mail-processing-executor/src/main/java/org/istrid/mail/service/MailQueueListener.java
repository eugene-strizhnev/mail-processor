package org.istrid.mail.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MailQueueListener implements MessageListener {
    private static final Logger LOGGER = LogManager.getLogger(MailQueueListener.class);

    private final List<String> idsBatch = new ArrayList<>();

    @Value("max.batch.number")
    private int maxBatchNumber;

    @Override
    public void onMessage(Message message) {
        String mailId;
        try {
            mailId = ((TextMessage) message).getText();
            LOGGER.info("Accepted mail id - {}", mailId);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        idsBatch.add(mailId);
        if (idsBatch.size() >= maxBatchNumber) {
            try {
                handleBatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleBatch() throws IOException, ClassNotFoundException, InterruptedException {
        LOGGER.info("Started batch handling");
    }
}
