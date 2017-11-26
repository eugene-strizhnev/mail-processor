package org.istrid.mail.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.istrid.mail.repository.MailHDFSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MailService {
    private static final Logger LOGGER = LogManager.getLogger(MailService.class);

    @Autowired
    private MailHDFSRepository mailHDFSRepository;

    @Autowired
    private QueueSenderService queueSenderService;

    public void saveMail(InputStream mailData) throws IOException {
        String id = mailHDFSRepository.saveMessage(mailData);
        LOGGER.info("Saved to file to HDFS. Id is - {}", id);
        queueSenderService.send(id);
    }
}
