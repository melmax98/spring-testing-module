package org.example.model;

import lombok.Data;

import java.util.Date;

@Data
public class Event implements Entity {
    private long id;
    private String title;
    private Date date;
}
