package ar.edu.undef.fie.tasksPci.interfaces.request;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoRecordTest {

    @Test
    void proyecto() {
        ProyectoRecord proyectoRecord = new ProyectoRecord(List.of(new ProyectoRecord(List.of(), "Test")), "Test");
        assertNotNull(proyectoRecord.proyecto());
        assertEquals(1, proyectoRecord.proyecto().size());
        assertEquals("Test", proyectoRecord.proyecto().get(0).name());
    }

    @Test
    void name() {
        ProyectoRecord proyectoRecord = new ProyectoRecord(List.of(), "Test");
        assertEquals("Test", proyectoRecord.name());
    }
}