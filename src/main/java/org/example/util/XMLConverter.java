package org.example.util;

import lombok.Setter;
import org.example.model.TicketListContainer;
import org.example.storage.DataSaver;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;

public class XMLConverter {
    @Setter
    private Unmarshaller unmarshaller;
    @Setter
    private DataSaver dataSaver;

    // Converting XML to an object graph (unmarshalling)
    public void xmlToObj(InputStream inputStream) throws IOException {
        TicketListContainer ticketList = (TicketListContainer) this.unmarshaller.unmarshal(new StreamSource(inputStream));
        ticketList.getTicketList().forEach(ticket -> dataSaver.createTicketSaveUserAndEvent(ticket));
    }
}