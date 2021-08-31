package org.example.model;

import lombok.Data;

@Data
public class Ticket implements Entity {
    private long id;
    private Event event;
    private User user;
    private TicketCategory category;
    private int placeNumber;
}
