package org.example.controller;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.example.facade.BookingFacade;
import org.example.model.Event;
import org.example.model.Ticket;
import org.example.model.User;
import org.example.util.PdfCreator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class PdfTicketController {

    private final BookingFacade bookingFacade;
    private final PdfCreator pdfCreator;

    @ResponseBody
    @GetMapping(value = "/user/{id}", headers = "accept=application/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> getBookedTicketsByUser(@PathVariable Integer id,
                                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize)
            throws DocumentException, IOException {

        User user = bookingFacade.getUserById(id);
        List<Ticket> ticketsByUser = bookingFacade.getBookedTickets(user, pageSize, pageNum);

        byte[] pdfForTickets = pdfCreator.createPdfForTickets(ticketsByUser);

        return createResponseEntityForPdf(pdfForTickets);
    }

    @ResponseBody
    @GetMapping(value = "/event/{id}", headers = "accept=application/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> getBookedTicketsByEvent(@PathVariable Integer id,
                                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize)
            throws DocumentException, IOException {

        Event event = bookingFacade.getEventById(id);
        List<Ticket> ticketsByEvent = bookingFacade.getBookedTickets(event, pageSize, pageNum);

        byte[] pdfForTickets = pdfCreator.createPdfForTickets(ticketsByEvent);

        return createResponseEntityForPdf(pdfForTickets);
    }

    private ResponseEntity<byte[]> createResponseEntityForPdf(byte[] pdfForTickets) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline; filename=" + "tickets.pdf");
        return new ResponseEntity<>(
                pdfForTickets, headers, HttpStatus.OK);
    }
}
