package org.example.model;

import lombok.Data;

@Data
public class User implements Entity {
    private long id;
    private String name;
    private String email;
}
