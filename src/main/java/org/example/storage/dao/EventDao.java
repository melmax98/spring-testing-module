package org.example.storage.dao;

import lombok.RequiredArgsConstructor;
import org.example.model.Entity;
import org.example.model.Event;
import org.example.storage.DataSource;
import org.example.storage.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EventDao implements Dao {

    private final DataSource dataSource;

    private static final String EVENT_TITLE = "event:";

    @Override
    public Event save(Entity entity) {
        Event event = (Event) entity;
        String entityName =  EVENT_TITLE + event.getId();

        getStorage().put(entityName, event);
        return event;
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
        String key = getStorage().keySet()
                .stream()
                .filter((EVENT_TITLE + eventId)::equals)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found"));
        return (Event) getStorage().get(key);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Entity> matchingEvents = getStorage().values()
                .stream()
                .filter(Event.class::isInstance)
                .filter(event -> title.equals(((Event) event).getTitle()))
                .collect(Collectors.toList());

        List<Entity> page = getPage(matchingEvents, pageNum, pageSize);

        return page
                .stream()
                .map(Event.class::cast)
                .collect(Collectors.toList());
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return null;
    }

    @Override
    public Map<String, Entity> getStorage() {
        return dataSource.getStorage();
    }
}
