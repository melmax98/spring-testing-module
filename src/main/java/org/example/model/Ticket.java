package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket implements Entity {
    private long ticketId;
    private Event event;
    private User user;
    private TicketCategory category;
    private int placeNumber;
}
