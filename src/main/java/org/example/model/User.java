package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlType(propOrder = {"userId", "name", "email"})
public class User implements Entity {

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    private long userId;
    private String name;
    private String email;

    @Override
    public String toString() {
        return userId + " " + name + " " + email;
    }
}
