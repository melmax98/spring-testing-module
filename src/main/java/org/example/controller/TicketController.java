package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.facade.BookingFacade;
import org.example.model.Event;
import org.example.model.Ticket;
import org.example.model.TicketCategory;
import org.example.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final BookingFacade bookingFacade;

    @GetMapping("/user/{id}")
    public List<Ticket> getBookedTicketsByUser(@PathVariable Integer id,
                                               @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        User user = bookingFacade.getUserById(id);
        return bookingFacade.getBookedTickets(user, pageSize, pageNum);
    }

    @GetMapping("/event/{id}")
    public List<Ticket> getBookedTicketsByEvent(@PathVariable Integer id,
                                                @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        Event event = bookingFacade.getEventById(id);
        return bookingFacade.getBookedTickets(event, pageSize, pageNum);
    }

    @GetMapping("{id}")
    public Ticket getTicketById(@PathVariable Integer id) {
        return bookingFacade.getTicketById(id);
    }

    @ResponseBody
    @PostMapping("/cancel/{ticketId}")
    public Boolean cancelTicket(@PathVariable Integer ticketId) {
        return bookingFacade.cancelTicket(ticketId);
    }

    @PostMapping
    public Ticket bookTicket(@RequestParam Integer userId,
                             @RequestParam Integer eventId,
                             @RequestParam Integer place,
                             @RequestParam String categoryName) {
        return bookingFacade.bookTicket(userId, eventId, place, TicketCategory.valueOf(categoryName));
    }

    @ResponseBody
    @PostMapping("/preload")
    public Boolean preloadTickets(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] byteArr = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        return bookingFacade.preloadTickets(inputStream);
    }

}
