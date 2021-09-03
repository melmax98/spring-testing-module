package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.facade.BookingFacade;
import org.example.model.Event;
import org.example.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is for testing purposes.
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");

        BookingFacade bookingFacade = (BookingFacade) context.getBean("bookingFacade");

        Event event = bookingFacade.getEventById(1);
        User user = bookingFacade.getUserById(1);
        log.debug(event.toString());
        log.debug(user.toString());
        log.debug(bookingFacade.getBookedTickets(user, 1, 1).toString());
        log.debug(bookingFacade.getBookedTickets(event, 1, 1).toString());
    }
}
