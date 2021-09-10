package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.facade.BookingFacade;
import org.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final BookingFacade bookingFacade;

    @GetMapping("/name/{name}")
    public String getUserByName(@PathVariable String name,
                                @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "1") Integer pageSize,
                                Model model) {
        List<User> usersByName = bookingFacade.getUsersByName(name, pageSize, pageNum);
        model.addAttribute("usersByName", usersByName);

        return "usersByName";
    }
}