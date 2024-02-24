package ar.edu.undef.fie.tasksPci.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponsableTest {

    @Test
    void getId() {
        Responsable responsable = new Responsable();
        responsable.setId(1L);
        assertEquals(1L, responsable.getId());
    }

    @Test
    void setId() {
        Responsable responsable = new Responsable();
        responsable.setId(1L);
        assertEquals(1L, responsable.getId());
    }

    @Test
    void getName() {
        Responsable responsable = new Responsable();
        responsable.setName("Responsable Test");
        assertEquals("Responsable Test", responsable.getName());
    }

    @Test
    void setName() {
        Responsable responsable = new Responsable();
        responsable.setName("Responsable Test");
        assertEquals("Responsable Test", responsable.getName());
    }

    @Test
    void getSlackUser() {
        Responsable responsable = new Responsable();
        responsable.setSlackUser("slackUserTest");
        assertEquals("slackUserTest", responsable.getSlackUser());
    }

    @Test
    void setSlackUser() {
        Responsable responsable = new Responsable();
        responsable.setSlackUser("slackUserTest");
        assertEquals("slackUserTest", responsable.getSlackUser());
    }

    @Test
    void getRole() {
        Responsable responsable = new Responsable();
        responsable.setRole("roleTest");
        assertEquals("roleTest", responsable.getRole());
    }

    @Test
    void setRole() {
        Responsable responsable = new Responsable();
        responsable.setRole("roleTest");
        assertEquals("roleTest", responsable.getRole());
    }
}