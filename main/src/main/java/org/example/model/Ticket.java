package org.example.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Objects;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlType(propOrder = {"ticketId", "event", "user", "category", "placeNumber"})
@Entity
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1846226795597524566L;

    public Ticket(Event event, User user, TicketCategory category, int placeNumber) {
        this.event = event;
        this.user = user;
        this.category = category;
        this.placeNumber = placeNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private TicketCategory category;

    private int placeNumber;

    @XmlElement(name = "ticketId")
    public Long getTicketId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ticket ticket = (Ticket) o;
        return ticketId != null && Objects.equals(ticketId, ticket.ticketId);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
