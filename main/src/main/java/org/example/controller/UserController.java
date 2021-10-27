package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.facade.BookingFacade;
import org.example.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final BookingFacade bookingFacade;

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUserByName(@PathVariable String name,
                                    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return bookingFacade.getUsersByName(name, pageSize, pageNum);
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByEmail(@PathVariable String email) {
        return bookingFacade.getUserByEmail(email);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Integer id) {
        return bookingFacade.getUserById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String email) {
        return bookingFacade.createUser(new User(name, email));
    }

    @PostMapping(value = "/update/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String email,
                           @PathVariable Integer userId) {
        User user = Optional.of(bookingFacade.getUserById(userId)).orElseThrow(NullPointerException::new);
        user.setEmail(email);
        user.setName(name);
        return bookingFacade.updateUser(user);
    }

    @ResponseBody
    @PostMapping(value = "/delete/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean deleteUser(@PathVariable Integer userId) {
        return bookingFacade.deleteUser(userId);
    }
}