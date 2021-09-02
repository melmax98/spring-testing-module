package org.example;

import org.example.model.Event;
import org.example.model.TicketCategory;
import org.example.model.User;
import org.example.service.EventService;
import org.example.service.TicketService;
import org.example.service.UserService;
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

        EventService eventService = (EventService) context.getBean("eventService");
        UserService userService = (UserService) context.getBean("userService");
        TicketService ticketService = (TicketService) context.getBean("ticketService");

        Event event = new Event(1, "New event", new Date());
        User user = new User(1, "John", "john@mail.com");

        eventService.createEvent(event);
        userService.createUser(user);

        System.out.println(ticketService.bookTicket(1, 1, 1, TicketCategory.BAR));
        System.out.println(ticketService.getBookedTickets(user, 1, 1));
        System.out.println(ticketService.getBookedTickets(event, 1, 1));
        ticketService.cancelTicket(1);
        System.out.println(ticketService.getBookedTickets(user, 1, 1));
        System.out.println(ticketService.getBookedTickets(event, 1, 1));
    }
}
