package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Event;
import org.example.storage.dao.EventDao;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;

    @Override
    public Event getEventById(long eventId) {
        return eventDao.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventDao.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventDao.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        return eventDao.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventDao.update(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventDao.delete(eventId);
    }
}
