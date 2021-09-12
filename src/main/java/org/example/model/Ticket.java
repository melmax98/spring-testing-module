package org.example.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlType(propOrder = {"ticketId", "event", "user", "category", "placeNumber"})
public class Ticket implements Entity {
    private long ticketId;
    private Event event;
    private User user;
    private TicketCategory category;
    private int placeNumber;

    public long getTicketId() {
        return ticketId;
    }

    @XmlElement(name = "event")
    public Event getEvent() {
        return event;
    }

    @XmlElement(name = "user")
    public User getUser() {
        return user;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    @Override
    public String toString() {
        return "Ticket = " + ticketId +
                ", event = " + event +
                ", user = " + user +
                ", category = " + category +
                ", place = " + placeNumber;
    }
}
