package ar.edu.undef.fie.tasksPci.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TareaTest {

    @Test
    void getId() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        assertEquals(1L, tarea.getId());
    }

    @Test
    void setId() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        assertEquals(1L, tarea.getId());
    }

    @Test
    void getNumeroDeRequerimiento() {
        Tarea tarea = new Tarea();
        tarea.setNumeroDeRequerimiento("REQ-123");
        assertEquals("REQ-123", tarea.getNumeroDeRequerimiento());
    }

    @Test
    void setNumeroDeRequerimiento() {
        Tarea tarea = new Tarea();
        tarea.setNumeroDeRequerimiento("REQ-123");
        assertEquals("REQ-123", tarea.getNumeroDeRequerimiento());
    }

    @Test
    void getDescripcion() {
        Tarea tarea = new Tarea();
        tarea.setDescripcion("Descripcion Test");
        assertEquals("Descripcion Test", tarea.getDescripcion());
    }

    @Test
    void setDescripcion() {
        Tarea tarea = new Tarea();
        tarea.setDescripcion("Descripcion Test");
        assertEquals("Descripcion Test", tarea.getDescripcion());
    }

    @Test
    void getTitulo() {
        Tarea tarea = new Tarea();
        tarea.setTitulo("Titulo Test");
        assertEquals("Titulo Test", tarea.getTitulo());
    }

    @Test
    void setTitulo() {
        Tarea tarea = new Tarea();
        tarea.setTitulo("Titulo Test");
        assertEquals("Titulo Test", tarea.getTitulo());
    }

    @Test
    void getResponsables() {
        Tarea tarea = new Tarea();
        Responsable responsable1 = new Responsable();
        Responsable responsable2 = new Responsable();
        List<Responsable> responsables = Arrays.asList(responsable1, responsable2);
        tarea.setResponsables(responsables);
        assertEquals(responsables, tarea.getResponsables());
    }

    @Test
    void setResponsables() {
        Tarea tarea = new Tarea();
        Responsable responsable1 = new Responsable();
        Responsable responsable2 = new Responsable();
        List<Responsable> responsables = Arrays.asList(responsable1, responsable2);
        tarea.setResponsables(responsables);
        assertEquals(responsables, tarea.getResponsables());
    }

    @Test
    void getPrioridad() {
        Tarea tarea = new Tarea();
        tarea.setPrioridad(Tarea.Prioridad.ALTA);
        assertEquals(Tarea.Prioridad.ALTA, tarea.getPrioridad());
    }

    @Test
    void setPrioridad() {
        Tarea tarea = new Tarea();
        tarea.setPrioridad(Tarea.Prioridad.ALTA);
        assertEquals(Tarea.Prioridad.ALTA, tarea.getPrioridad());
    }

    @Test
    void getEstado() {
        Tarea tarea = new Tarea();
        tarea.setEstado(Tarea.Estado.PENDIENTE);
        assertEquals(Tarea.Estado.PENDIENTE, tarea.getEstado());
    }

    @Test
    void setEstado() {
        Tarea tarea = new Tarea();
        tarea.setEstado(Tarea.Estado.PENDIENTE);
        assertEquals(Tarea.Estado.PENDIENTE, tarea.getEstado());
    }
}