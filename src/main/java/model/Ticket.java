package model;

import lombok.Data;

@Data
public class Ticket {
    private long id;
    private Event event;
    private User user;
    private TicketCategory category;
    private int placeNumber;
}
