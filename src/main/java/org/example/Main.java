package org.example;

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
public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");

        BookingFacade bookingFacade = (BookingFacade) context.getBean("bookingFacade");

        Event event = new Event(1, "New event", new Date());
        User user = new User(1, "John", "john@mail.com");

        bookingFacade.createEvent(event);
        bookingFacade.createUser(user);

        System.out.println(bookingFacade.bookTicket(1, 1, 1, TicketCategory.BAR));
        System.out.println(bookingFacade.getBookedTickets(user, 1, 1));
        System.out.println(bookingFacade.getBookedTickets(event, 1, 1));
        bookingFacade.cancelTicket(1);
        System.out.println(bookingFacade.getBookedTickets(user, 1, 1));
        System.out.println(bookingFacade.getBookedTickets(event, 1, 1));
    }
}
