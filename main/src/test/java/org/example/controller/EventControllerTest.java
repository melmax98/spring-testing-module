package org.example.controller;

import com.google.gson.Gson;
import org.example.facade.BookingFacade;
import org.example.model.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class EventControllerTest {

    private MockMvc mockMvc;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private Gson gson;

    @Autowired
    private EventController eventController;

    @Autowired
    private BookingFacade bookingFacade;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyy");

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void getEventsByTitleTest() throws Exception {
        List<Event> events = Collections.singletonList(bookingFacade.createEvent(new Event("test", new Date(), 1)));
        String jsonResult = gson.toJson(events);

        this.mockMvc.perform(get("/event/title/test"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult))
                .andReturn();

        assertTrue(bookingFacade.deleteEvent(events.iterator().next().getEventId()));
    }

    @Test
    public void getEventsByDateTest() throws Exception {
        Date date = SIMPLE_DATE_FORMAT.parse("2099-10-14");
        List<Event> events = Collections.singletonList(bookingFacade.createEvent(new Event("test", date, 1)));
        String resultJson = gson.toJson(events);

        this.mockMvc.perform(get("/event/date/2099-10-14"))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andReturn();

        assertTrue(bookingFacade.deleteEvent(events.iterator().next().getEventId()));
    }

    @Test
    public void createEvent() throws Exception {
        this.mockMvc.perform(post("/event")
                        .param("title", "America")
                        .param("date", "Sep 10, 2099")
                        .param("ticketPrice", "1"))
                .andExpect(status().isOk())
                .andReturn();

        Event createdEvent = bookingFacade.getAllEvents().stream().max(Comparator.comparing(Event::getEventId)).orElseThrow(NullPointerException::new);
        assertEquals("America", createdEvent.getTitle());
        assertEquals("Sep 10, 2099", simpleDateFormat.format(createdEvent.getDate()));
        assertTrue(bookingFacade.deleteEvent(createdEvent.getEventId()));
    }

    @Test
    public void updateEvent() throws Exception {
        Event event = bookingFacade.createEvent(new Event("test", new Date(1), 0));

        this.mockMvc.perform(post("/event/update/" + event.getEventId())
                        .param("title", "America")
                        .param("date", "Sep 10, 2099")
                        .param("ticketPrice", "1"))
                .andExpect(status().isOk())
                .andReturn();

        Event updatedEvent = bookingFacade.getEventById(event.getEventId());
        assertEquals("America", updatedEvent.getTitle());
        assertEquals("Sep 10, 2099", simpleDateFormat.format(updatedEvent.getDate()));
        assertTrue(bookingFacade.deleteEvent(updatedEvent.getEventId()));
    }

    @Test
    public void deleteEvent_eventExists() throws Exception {
        Event event = bookingFacade.createEvent(new Event("test", new Date(1), 0));
        this.mockMvc.perform(post("/event/delete/" + event.getEventId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andReturn();

        assertNull(bookingFacade.getEventById(event.getEventId()));
    }

    @Test
    public void deleteEvent_eventDoesNotExist() throws Exception {
        this.mockMvc.perform(post("/event/delete/" + Integer.MAX_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andReturn();

        assertNull(bookingFacade.getEventById(Integer.MAX_VALUE));
    }
}