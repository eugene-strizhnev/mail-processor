package org.istrid.mail.service;

import org.istrid.mail.repository.MailHDFSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private MailHDFSRepository mailHDFSRepository;

    @Autowired
    private QueueSenderService queueSenderService;

    public void saveMail() {

    }
}
