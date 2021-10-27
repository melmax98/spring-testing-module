package org.example.controller;

import com.google.gson.Gson;
import org.example.facade.BookingFacade;
import org.example.model.Event;
import org.example.model.Ticket;
import org.example.model.TicketCategory;
import org.example.model.User;
import org.example.model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TicketControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TicketController ticketController;

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private Gson gson;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    public void getBookedTicketsByUser() throws Exception {
        User user = bookingFacade.createUser(new User());
        Event event = bookingFacade.createEvent(new Event("title", new Date(), 0));
        UserAccount userAccount = bookingFacade.refillAccount(user, 1.0);
        List<Ticket> tickets = Collections.singletonList(bookingFacade.bookTicket(user.getUserId(), event.getEventId(), 1, TicketCategory.STANDARD));
        String jsonResult = gson.toJson(tickets);

        this.mockMvc.perform(get("/ticket/user/" + user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult))
                .andReturn();

        assertTrue(bookingFacade.deleteUserAccount(userAccount.getUserAccountId()));
        assertTrue(bookingFacade.cancelTicket(tickets.iterator().next().getTicketId()));
        assertTrue(bookingFacade.deleteEvent(event.getEventId()));
        assertTrue(bookingFacade.deleteUser(user.getUserId()));
    }

    @Test
    public void getBookedTicketsByEvent() throws Exception {
        User user = bookingFacade.createUser(new User());
        Event event = bookingFacade.createEvent(new Event("title", new Date(), 0));
        UserAccount userAccount = bookingFacade.refillAccount(user, 1.0);
        List<Ticket> tickets = Collections.singletonList(bookingFacade.bookTicket(user.getUserId(), event.getEventId(), 1, TicketCategory.STANDARD));
        String jsonResult = gson.toJson(tickets);

        this.mockMvc.perform(get("/ticket/event/" + event.getEventId()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult))
                .andReturn();

        assertTrue(bookingFacade.deleteUserAccount(userAccount.getUserAccountId()));
        assertTrue(bookingFacade.cancelTicket(tickets.iterator().next().getTicketId()));
        assertTrue(bookingFacade.deleteEvent(event.getEventId()));
        assertTrue(bookingFacade.deleteUser(user.getUserId()));
    }

    @Test
    public void getBookedTicketById() throws Exception {
        Ticket ticket = bookingFacade.createTicket(new Ticket());
        String jsonResult = gson.toJson(ticket);

        this.mockMvc.perform(get("/ticket/" + ticket.getTicketId()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult))
                .andReturn();

        assertTrue(bookingFacade.cancelTicket(ticket.getTicketId()));
    }

    @Test
    public void bookTicket() throws Exception {
        User user = bookingFacade.createUser(new User("test", "testmail"));
        Event event = bookingFacade.createEvent(new Event("test", new Date(1), 1000));
        UserAccount userAccount = bookingFacade.refillAccount(user, 1001.0);

        this.mockMvc.perform(post("/ticket")
                        .param("userId", String.valueOf(user.getUserId()))
                        .param("eventId", String.valueOf(event.getEventId()))
                        .param("categoryName", "BAR")
                        .param("place", "3"))
                .andExpect(status().isOk())
                .andReturn();

        Ticket ticket = bookingFacade.getBookedTickets(user, 1, 1).iterator().next();

        assertEquals(1, bookingFacade.getBalanceByUser(user));
        assertTrue(bookingFacade.deleteUserAccount(userAccount.getUserAccountId()));
        assertNotNull(ticket);
        assertEquals(ticket.getUser(), user);
        assertTrue(bookingFacade.cancelTicket(ticket.getTicketId()));
        assertTrue(bookingFacade.deleteUser(user.getUserId()));
        assertTrue(bookingFacade.deleteEvent(event.getEventId()));
        assertNull(bookingFacade.getTicketById(ticket.getTicketId()));
    }

    @Test
    public void cancelTicket_ticketExists() throws Exception {
        Ticket ticket = bookingFacade.createTicket(new Ticket());
        this.mockMvc.perform(post("/ticket/cancel/" + ticket.getTicketId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andReturn();

        assertNull(bookingFacade.getTicketById(ticket.getTicketId()));
    }

    @Test
    public void cancelTicket_doesNotExist() throws Exception {
        this.mockMvc.perform(post("/ticket/cancel/" + Integer.MAX_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andReturn();

        assertNull(bookingFacade.getTicketById(Integer.MAX_VALUE));
    }

    @Test
    public void preloadTickets() throws Exception {
        BookingFacade bookingFacade = spy(BookingFacade.class);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new TicketController(bookingFacade)).build();
        when(bookingFacade.preloadTickets(any(InputStream.class))).thenReturn(true);

        mockMvc.perform(multipart("/ticket/preload").file(new MockMultipartFile("file", new byte[]{})))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andReturn();
    }
}