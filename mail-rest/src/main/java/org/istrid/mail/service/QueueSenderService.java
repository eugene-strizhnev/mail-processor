package org.istrid.mail.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class QueueSenderService {

    private static final Logger LOGGER = LogManager.getLogger(QueueSenderService.class);

    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private Destination mailQueue;

    public void send(String msg) {
        Connection conn = null;
        try {
            conn = connectionFactory.createConnection();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(mailQueue);
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

}
