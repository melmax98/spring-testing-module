package org.example.controller;

import org.example.facade.BookingFacade;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private BookingFacade bookingFacade;

    @GetMapping("/name/{name}")
    public List<User> getUserByName(@PathVariable String name) {
        System.out.println("Here");
        return bookingFacade.getUsersByName(name, 1, 1);
    }
}