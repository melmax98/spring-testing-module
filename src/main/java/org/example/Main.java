package org.example;

import org.example.model.Event;
import org.example.service.EventService;
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
        Event event = new Event();
        event.setId(1);
        event.setDate(new Date());
        event.setTitle("New");

        EventService eventService = (EventService) context.getBean("eventService");
        eventService.createEvent(event);
        System.out.println(eventService.getEventById(1));
    }
}
