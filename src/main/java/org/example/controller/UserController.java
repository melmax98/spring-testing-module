package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.facade.BookingFacade;
import org.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final BookingFacade bookingFacade;

    private static final String USERS_ATTRIBUTE = "users";
    private static final String USERS_VIEW = "userList";

    @GetMapping("/name/{name}")
    public String getUserByName(@PathVariable String name,
                                @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                Model model) {
        List<User> usersByName = bookingFacade.getUsersByName(name, pageSize, pageNum);
        model.addAttribute(USERS_ATTRIBUTE, usersByName);

        return USERS_VIEW;
    }

    @GetMapping("/email/{email}")
    public String getUserByEmail(@PathVariable String email, Model model) {
        List<User> userByEmail = Collections.singletonList(bookingFacade.getUserByEmail(email));
        model.addAttribute(USERS_ATTRIBUTE, userByEmail);

        return USERS_VIEW;
    }

    @GetMapping("{id}")
    public String getUserById(@PathVariable Integer id, Model model) {
        User user = bookingFacade.getUserById(id);
        model.addAttribute("user", user);
        return "userEdit";
    }

    @GetMapping("/new")
    public String createUserForm() {
        return "createUser";
    }

    @PostMapping
    public String createUser(@RequestParam String name,
                             @RequestParam String email,
                             Model model) {
        User user = bookingFacade.createUser(new User(name, email));
        model.addAttribute(USERS_ATTRIBUTE, Collections.singletonList(user));
        return USERS_VIEW;
    }

    @PostMapping("/update/{userId}")
    public String updateUser(@RequestParam String name,
                             @RequestParam String email,
                             @PathVariable Integer userId,
                             Model model) {
        User user = bookingFacade.getUserById(userId);
        if (user != null) {
            user.setEmail(email);
            user.setName(name);
            User updatedUser = bookingFacade.updateUser(user);
            List<User> updatedUserForModel = Collections.singletonList(updatedUser);
            model.addAttribute(USERS_ATTRIBUTE, updatedUserForModel);
        }
        return USERS_VIEW;
    }

    @ResponseBody
    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        if (bookingFacade.deleteUser(userId)) {
            return "User was successfully deleted";
        }
        return "User was not deleted";
    }
}