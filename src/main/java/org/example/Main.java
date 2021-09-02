package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.facade.BookingFacade;
import org.example.model.Event;
import org.example.model.TicketCategory;
import org.example.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
    This class is for testing purposes.
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");

        BookingFacade bookingFacade = (BookingFacade) context.getBean("bookingFacade");

        Event event = new Event(1, "New event", new Date());
        User user = new User(1, "John", "john@mail.com");

        bookingFacade.createEvent(event);
        bookingFacade.createUser(user);

        log.debug(bookingFacade.bookTicket(1, 1, 1, TicketCategory.BAR).toString());
        log.debug(bookingFacade.getBookedTickets(user, 1, 1).toString());
        log.debug(bookingFacade.getBookedTickets(event, 1, 1).toString());
        bookingFacade.cancelTicket(1);
        log.debug(bookingFacade.getBookedTickets(user, 1, 1).toString());
        log.debug(bookingFacade.getBookedTickets(event, 1, 1).toString());
    }
}
