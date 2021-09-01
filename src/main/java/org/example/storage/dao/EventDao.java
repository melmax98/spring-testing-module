package org.example.storage.dao;

import org.example.model.Entity;
import org.example.model.Event;

import java.util.Date;
import java.util.List;

public class EventDao implements Dao {

    @Override
    public Event save(Entity entity) {
        return null;
    }

    @Override
    public Event update(Entity entity) {
        return null;
    }

    @Override
    public boolean delete(long eventId) {
        return false;
    }

    public Event getEventById(long eventId) {
        return null;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return null;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return null;
    }
}
