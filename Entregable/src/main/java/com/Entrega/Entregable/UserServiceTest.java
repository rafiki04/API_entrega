package com.Entrega.Entregable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Entrega.Entregable.Services.UserService;
import com.Entrega.Entregable.models.User;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void shouldCreateUser() {

        User user = new User();
        user.setName("Test");
        user.setEmail("test@mail.com");
        user.setPhone_number("5551234567");
        user.setPassword("1234");
        user.setTaxId("TEST990101XXX");

        User created = userService.createUser(user);

        assertNotNull(created.getId());
    }
}