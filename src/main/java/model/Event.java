package model;

import lombok.Data;

import java.util.Date;

@Data
public class Event {
    private long id;
    private String title;
    private Date date;
}
