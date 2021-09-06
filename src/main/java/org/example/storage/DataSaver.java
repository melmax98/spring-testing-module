package org.example.storage;

import lombok.Setter;
import org.example.model.Event;
import org.example.model.Ticket;
import org.example.model.User;
import org.example.storage.dao.EventDao;
import org.example.storage.dao.TicketDao;
import org.example.storage.dao.UserDao;

@Setter
public class DataSaver {

    private EventDao eventDao;
    private UserDao userDao;
    private TicketDao ticketDao;

    public void createTicket(Ticket ticket) {
        ticketDao.save(ticket);
    }

    public void createUser(User user) {
        userDao.save(user);
    }

    public void createEvent(Event event) {
        eventDao.save(event);
    }
}
