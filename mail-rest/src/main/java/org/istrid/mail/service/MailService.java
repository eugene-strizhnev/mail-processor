package org.istrid.mail.service;

import org.istrid.mail.repository.MailHDFSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MailService {

    @Autowired
    private MailHDFSRepository mailHDFSRepository;

    @Autowired
    private QueueSenderService queueSenderService;

    public Mono<Void> saveMail(Flux<DataBuffer> mailData) {
        return mailHDFSRepository.saveMessage(mailData)
                .flatMap(queueSenderService::send);
    }
}
