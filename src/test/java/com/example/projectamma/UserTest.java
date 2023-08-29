package com.example.projectamma;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.projectamma.entities.User;


public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User(1, "username", "password123");
    }

    @Test
    public void testGetters() {
        assertEquals(1, user.getUserID());
        assertEquals("username", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetters() {
        user.setUserID(2);
        user.setUsername("username");
        user.setPassword("newPassword");

        assertEquals(2, user.getUserID());
        assertEquals("username", user.getUsername());
        assertEquals("newPassword", user.getPassword());
    }
}
