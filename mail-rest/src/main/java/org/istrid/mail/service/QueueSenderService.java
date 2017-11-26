package org.istrid.mail.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class QueueSenderService {

    private static final Logger LOGGER = LogManager.getLogger(QueueSenderService.class);

    private ConnectionFactory connectionFactory;
    private Destination destination;

    public void send(String msg) {
        Connection conn = null;
        try {
            conn = connectionFactory.createConnection();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(msg);
            producer.send(message);
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

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
