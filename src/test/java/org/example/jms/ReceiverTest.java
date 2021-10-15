package org.example.jms;

import org.example.facade.BookingFacade;
import org.example.model.Event;
import org.example.model.Ticket;
import org.example.model.TicketCategory;
import org.example.model.User;
import org.example.storage.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class ReceiverTest {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void test() {
        List<Ticket> before = (List<Ticket>) ticketRepository.findAll();
        assertTrue(before.isEmpty());

        Ticket ticket = new Ticket(
                new Event("Test messaging", new Date(), 0.0),
                new User("messaging", "messagingTest@test.test"),
                TicketCategory.PREMIUM,
                1
        );

        jmsTemplate.convertAndSend("mailbox", ticket);
        await().atMost(2, TimeUnit.SECONDS).until(() -> !((List<Ticket>) ticketRepository.findAll()).isEmpty());

        List<Ticket> allTickets = (List<Ticket>) ticketRepository.findAll();
        Ticket createdTicked = allTickets.iterator().next();
        ticket.setTicketId(createdTicked.getTicketId());
        ticket.getEvent().setEventId(createdTicked.getEvent().getEventId());
        ticket.getUser().setUserId(createdTicked.getUser().getUserId());

        assertEquals(1, allTickets.size());
        assertEquals(ticket, createdTicked);
        assertTrue(bookingFacade.cancelTicket(createdTicked.getTicketId()));
        assertTrue(bookingFacade.deleteUser(createdTicked.getUser().getUserId()));
        assertTrue(bookingFacade.deleteEvent(createdTicked.getEvent().getEventId()));
    }
}