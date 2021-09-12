package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlType(propOrder = {"eventId", "title", "date"})
public class Event implements Entity {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyy");

    public Event(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    private long eventId;
    private String title;
    private Date date;

    @Override
    public String toString() {
        return eventId + " " + title + " " + simpleDateFormat.format(date);
    }
}
