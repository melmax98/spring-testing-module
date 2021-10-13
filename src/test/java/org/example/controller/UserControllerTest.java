package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.facade.BookingFacade;
import org.example.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private BookingFacade bookingFacade;

    private static final Gson GSON = new GsonBuilder().create();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getUsersByNameTest() throws Exception {
        List<User> users = Collections.singletonList(bookingFacade.createUser(new User("test", "testmail")));
        String jsonResult = GSON.toJson(users);

        this.mockMvc.perform(get("/user/name/test"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult))
                .andReturn();

        assertTrue(bookingFacade.deleteUser(users.iterator().next().getUserId()));
    }

    @Test
    public void getUsersByEmailTest() throws Exception {
        User user = bookingFacade.createUser(new User("test", "testmail"));
        String jsonResult = GSON.toJson(user);

        this.mockMvc.perform(get("/user/email/testmail"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult))
                .andReturn();

        assertTrue(bookingFacade.deleteUser(user.getUserId()));
    }

    @Test
    public void createUser() throws Exception {
        this.mockMvc.perform(post("/user")
                        .param("name", "America")
                        .param("email", "america!@#$%%^"))
                .andExpect(status().isOk())
                .andReturn();

        User createdUser = bookingFacade.getAllUsers().stream().max(Comparator.comparing(User::getUserId)).orElseThrow(NullPointerException::new);

        assertEquals("America", createdUser.getName());
        assertEquals("america!@#$%%^", createdUser.getEmail());
        assertTrue(bookingFacade.deleteUser(createdUser.getUserId()));
    }

    @Test
    public void updateUser() throws Exception {
        User user = bookingFacade.createUser(new User("test", "testmail"));
        User updatedUser = new User(user.getUserId(), "America", "america!@#$%%^");
        String jsonResult = GSON.toJson(updatedUser);

        this.mockMvc.perform(post("/user/update/" + user.getUserId())
                        .param("name", "America")
                        .param("email", "america!@#$%%^"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult))
                .andReturn();

        assertTrue(bookingFacade.deleteUser(updatedUser.getUserId()));
    }

    @Test
    public void deleteUser_userExists() throws Exception {
        User user = bookingFacade.createUser(new User("test", "testmail"));

        this.mockMvc.perform(post("/user/delete/" + user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andReturn();

        assertNull(bookingFacade.getUserById(user.getUserId()));
    }

    @Test
    public void deleteUser_userDoesNotExist() throws Exception {
        this.mockMvc.perform(post("/user/delete/" + Integer.MAX_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andReturn();

        assertNull(bookingFacade.getUserById(Integer.MAX_VALUE));
    }
}