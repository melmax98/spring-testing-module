package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.facade.BookingFacade;
import org.example.model.Event;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final BookingFacade bookingFacade;

    @GetMapping(value = "/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEventByName(@PathVariable String title,
                                      @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return bookingFacade.getEventsByTitle(title, pageSize, pageNum);
    }

    @GetMapping(value = "/date/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEventsForDay(@PathVariable String date,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = simpleDateFormat.parse(date, new ParsePosition(0));
        return bookingFacade.getEventsForDay(parsedDate, pageSize, pageNum);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event getEventById(@PathVariable Integer id) {
        return bookingFacade.getEventById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Event createEvent(@RequestParam(required = false) String title,
                             @RequestParam(required = false) String date,
                             @RequestParam(required = false) Double ticketPrice
    ) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyy");
        Date parsedDate = simpleDateFormat.parse(date, new ParsePosition(0));
        return bookingFacade.createEvent(new Event(title, parsedDate, ticketPrice));
    }

    @PostMapping(value = "/update/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event updateEvent(@RequestParam(required = false) String title,
                             @RequestParam(required = false) String date,
                             @RequestParam(required = false) Double ticketPrice,
                             @PathVariable Integer eventId) {
        Event event = Optional.ofNullable(bookingFacade.getEventById(eventId)).orElseThrow(NullPointerException::new);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyy");

        event.setTitle(title);
        Date parsedDate = simpleDateFormat.parse(date, new ParsePosition(0));
        event.setDate(parsedDate);
        event.setTicketPrice(ticketPrice);
        return bookingFacade.updateEvent(event);
    }

    @ResponseBody
    @PostMapping(value = "/delete/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean deleteEvent(@PathVariable Integer eventId) {
        return bookingFacade.deleteEvent(eventId);
    }
}
