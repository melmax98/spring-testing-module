package org.example.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Ticket;
import org.example.storage.DataSaver;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Receiver {

    private final DataSaver dataSaver;

    @JmsListener(destination = "mailbox")
    public void receiveMessage(Ticket ticket) {
        log.info("Received booking message: {}", ticket);
        dataSaver.createTicketSaveUserAndEvent(ticket);
    }

}
