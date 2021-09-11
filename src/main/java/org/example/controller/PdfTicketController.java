package org.example.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.example.facade.BookingFacade;
import org.example.model.Event;
import org.example.model.Ticket;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class PdfTicketController {

    private final BookingFacade bookingFacade;

    @Value("${data.pdf.output.path}")
    private String pdfOutputPath;

    private static final String TICKETS_ATTRIBUTE = "tickets";
    private static final String TICKETS_VIEW = "ticketList";

    @GetMapping(value = "/user/{id}", headers = "accept=application/pdf")
    public String getBookedTicketsByUser(@PathVariable Integer id,
                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         Model model) {

        User user = bookingFacade.getUserById(id);
        List<Ticket> ticketsByUser = bookingFacade.getBookedTickets(user, pageSize, pageNum);
        model.addAttribute(TICKETS_ATTRIBUTE, ticketsByUser);

        return TICKETS_VIEW;
    }

    @ResponseBody
//    @GetMapping(value = "/event/{id}", headers = "accept=application/pdf")
    @GetMapping(value = "/event/{id}")
    public Document getBookedTicketsByEvent(@PathVariable Integer id,
                                            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                            Model model) throws Exception {

        Event event = bookingFacade.getEventById(id);
        List<Ticket> ticketsByEvent = bookingFacade.getBookedTickets(event, pageSize, pageNum);

        Document document = showHelp(ticketsByEvent);
        return document;
    }


    public Document showHelp(List<Ticket> tickets) throws Exception {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyy h:m:s");
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(pdfOutputPath + "tickets.pdf"));
        document.open();

        document.add(new Paragraph("Report date: " + simpleDateFormat.format(new Date())));

        Paragraph title = new Paragraph("Ticket list");
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(30);
        document.add(title);

        for (Ticket ticket : tickets) {
            Paragraph paragraph = new Paragraph(ticket.toString());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setSpacingAfter(10);
            paragraph.setFont(new Font(Font.FontFamily.SYMBOL, 10, Font.FontStyle.BOLD.ordinal()));
            document.add(paragraph);
        }

        document.close();

        return document;
    }
}
