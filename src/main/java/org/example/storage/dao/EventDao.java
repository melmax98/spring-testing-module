package org.example.storage.dao;

import lombok.RequiredArgsConstructor;
import org.example.model.Entity;
import org.example.model.Event;
import org.example.storage.DataSource;
import org.joda.time.DateTimeComparator;

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
        String entityName = EVENT_TITLE + event.getId();

        getStorage().put(entityName, event);
        return event;
    }

    @Override
    public Event update(Entity entity) {
        Event event = getEventById(((Event) entity).getId());

        if (event == null) {
            //logg
            return null;
        }

        event.setId(event.getId());
        event.setTitle(event.getTitle());
        event.setDate(event.getDate());

        return save(event);
    }

    @Override
    public boolean delete(long eventId) {
        Event event = getEventById(eventId);

        if (event == null) {
            //logg
            return false;
        }

        getStorage().remove(EVENT_TITLE + eventId);
        return true;
    }

    public Event getEventById(long eventId) {
        String key = getStorage().keySet()
                .stream()
                .filter((EVENT_TITLE + eventId)::equals)
                .findFirst()
                .orElse(null);

        if (key == null) {
            //logg
            return null;
        }
        return (Event) getStorage().get(key);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Entity> matchingEvents = getStorage().values()
                .stream()
                .filter(Event.class::isInstance)
                .filter(event -> title.equals(((Event) event).getTitle()))
                .collect(Collectors.toList());

        //check null, logg, return

        return getEventsPage(pageSize, pageNum, matchingEvents);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();

        List<Entity> matchingEvents = getStorage().values()
                .stream()
                .filter(Event.class::isInstance)
                .filter(event -> dateTimeComparator.compare(day, ((Event) event).getDate()) == 0)
                .collect(Collectors.toList());

        //check null, logg, return

        return getEventsPage(pageSize, pageNum, matchingEvents);
    }

    private List<Event> getEventsPage(int pageSize, int pageNum, List<Entity> matchingEvents) {
        List<Entity> page = getPage(matchingEvents, pageNum, pageSize);

        //check null, logg, return

        return page
                .stream()
                .map(Event.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Entity> getStorage() {
        return dataSource.getStorage();
    }
}
