package org.example.util;

import lombok.Setter;
import org.example.model.TicketListContainer;
import org.example.storage.DataSaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;

public class XMLConverter {
    @Value("${data.xml.path}")
    private String xmlFilePath;
    @Setter
    private Unmarshaller unmarshaller;
    @Setter
    private DataSaver dataSaver;

    // Converting XML to an object graph (unmarshalling)
    public void xmlToObj() throws IOException {
        TicketListContainer ticketList;
        try (FileInputStream is = new FileInputStream(xmlFilePath)) {
            ticketList = (TicketListContainer) this.unmarshaller.unmarshal(new StreamSource(is));
        }
        ticketList.getTicketList().forEach(ticket -> dataSaver.createTicketSaveUserAndEvent(ticket));
    }
}