package org.istrid.mail.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MailQueueListener implements MessageListener {
    private static final Logger LOGGER = LogManager.getLogger(MailQueueListener.class);

    private final List<String> idsBatch = new ArrayList<>();

    @Value("${max.batch.number}")
    private int maxBatchNumber;

    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private Destination mailQueue;

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

    @PreDestroy
    public void shutdown() {
        LOGGER.info("Shutting down. Moving all unprocessed messages back to queue");
        if (idsBatch.size() > 0) {
            Connection conn = null;
            try {
                conn = connectionFactory.createConnection();
                Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = session.createProducer(mailQueue);
                for (String id : idsBatch) {
                    TextMessage message = session.createTextMessage(id);
                    producer.send(message);
                }
            } catch (Exception ex) {
                LOGGER.error(ex);
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (JMSException e) {
                        LOGGER.error(e);
                    }
                }
            }
        }
    }
}
