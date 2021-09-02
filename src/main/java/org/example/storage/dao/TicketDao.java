package org.example.storage.dao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Entity;
import org.example.model.Event;
import org.example.model.Ticket;
import org.example.model.TicketCategory;
import org.example.model.User;
import org.example.storage.DataSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TicketDao implements Dao {

    @Setter
    private DataSource dataSource;

    private static final String TICKET_TITLE = "ticket:";

    public Ticket bookTicket(User user, Event event, int place, TicketCategory category) {
        List<Ticket> allTickets = getAllTickets();
        long lastTicketId = allTickets
                .stream()
                .mapToLong(Ticket::getId)
                .max()
                .orElse(0L);

        Ticket ticket = new Ticket();
        ticket.setId(lastTicketId + 1);
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setPlaceNumber(place);
        ticket.setCategory(category);

        return save(ticket);
    }

    @Override
    public Ticket save(Entity entity) {
        Ticket ticket = (Ticket) entity;
        String entityKey = TICKET_TITLE + ticket.getId();

        getStorage().put(entityKey, ticket);
        return ticket;
    }

    @Override
    public Entity update(Entity entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long ticketId) {
        Ticket ticket = getTicketById(ticketId);

        if (ticket == null) {
            log.warn("Could not delete the ticket with id {} because it does not exist", ticketId);
            return false;
        }

        getStorage().remove(TICKET_TITLE + ticketId);
        return true;
    }

    public List<Ticket> getAllTickets() {
        return getStorage().values()
                .stream()
                .filter(Ticket.class::isInstance)
                .map(Ticket.class::cast)
                .collect(Collectors.toList());
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Entity> matchingTickets = getStorage().values()
                .stream()
                .filter(Ticket.class::isInstance)
                .filter(ticket -> ((Ticket) ticket).getUser().equals(user))
                .collect(Collectors.toList());

        return getTicketsPage(pageSize, pageNum, matchingTickets);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Entity> matchingTickets = getStorage().values()
                .stream()
                .filter(Ticket.class::isInstance)
                .filter(ticket -> ((Ticket) ticket).getEvent().equals(event))
                .collect(Collectors.toList());

        return getTicketsPage(pageSize, pageNum, matchingTickets);
    }

    public Ticket getTicketById(long ticketId) {
        String key = getStorage().keySet()
                .stream()
                .filter((TICKET_TITLE + ticketId)::equals)
                .findFirst()
                .orElse(null);

        if (key == null) {
            log.warn("Ticket with id {} not found", ticketId);
            return null;
        }
        return (Ticket) getStorage().get(key);
    }

    private List<Ticket> getTicketsPage(int pageSize, int pageNum, List<Entity> matchingTickets) {
        List<Entity> page = getPage(matchingTickets, pageNum, pageSize);

        return page
                .stream()
                .map(Ticket.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Entity> getStorage() {
        return dataSource.getStorage();
    }
}
