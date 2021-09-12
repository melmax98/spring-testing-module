package org.example.controller;

import org.example.facade.BookingFacade;
import org.example.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.GenericXmlContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"}, loader = GenericXmlContextLoader.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private BookingFacade bookingFacade;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void createUserViewTest() throws Exception {
        this.mockMvc.perform(get("/user/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("createUser"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getUsersByNameTest() throws Exception {
        this.mockMvc.perform(get("/user/name/test"))
                .andExpect(status().isOk())
                .andExpect(view().name("userList"))
                .andReturn();
    }

    @Test
    public void getUsersByEmailTest() throws Exception {
        User user = bookingFacade.createUser(new User("test", "testmail"));
        this.mockMvc.perform(get("/user/email/testmail"))
                .andExpect(status().isOk())
                .andExpect(view().name("userList"))
                .andReturn();
        bookingFacade.deleteUser(user.getUserId());
    }

    @Test
    public void getUserEditForm() throws Exception {
        this.mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("userEdit"))
                .andReturn();
    }

    @Test
    public void createUserForm() throws Exception {
        this.mockMvc.perform(post("/user")
                        .param("name", "America")
                        .param("email", "america!@#$%%^"))
                .andExpect(status().isOk())
                .andExpect(view().name("userList"))
                .andReturn();

        User userByEmail = bookingFacade.getUserByEmail("america!@#$%%^");
        assertNotNull(userByEmail);
        assertTrue(bookingFacade.deleteUser(userByEmail.getUserId()));
    }
}