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

        EventService eventService = (EventService) context.getBean("eventService");

        for (int i = 1; i <= 30; i++) {
            Event event = new Event();
            event.setId(i);
            event.setDate(new Date());
            event.setTitle("New");
            eventService.createEvent(event);
        }

        System.out.println(eventService.getEventsByTitle("New", 15, 3).size());
    }
}
