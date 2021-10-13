package org.example;

import org.example.model.Event;
import org.example.model.Ticket;
import org.example.model.TicketCategory;
import org.example.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

/**
 * This class is for testing purposes.
 */
@SpringBootApplication
@ImportResource("classpath:spring.xml")
@EnableScheduling
@EnableJms
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        jmsTemplate.convertAndSend("mailbox", new Ticket(
                new Event("Test messaging", new Date(), 0.0),
                new User("messaging", "messagingTest@test.test"),
                TicketCategory.PREMIUM,
                1
        ));
    }
}