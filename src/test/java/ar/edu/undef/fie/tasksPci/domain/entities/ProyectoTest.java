package ar.edu.undef.fie.tasksPci.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoTest {

    @Test
    void getId() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        assertEquals(1L, proyecto.getId());
    }

    @Test
    void setId() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        assertEquals(1L, proyecto.getId());
    }

    @Test
    void getNombre() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto Test");
        assertEquals("Proyecto Test", proyecto.getNombre());
    }

    @Test
    void setNombre() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto Test");
        assertEquals("Proyecto Test", proyecto.getNombre());
    }

    @Test
    void getDescripcion() {
        Proyecto proyecto = new Proyecto();
        proyecto.setDescripcion("Descripcion Test");
        assertEquals("Descripcion Test", proyecto.getDescripcion());
    }

    @Test
    void setDescripcion() {
        Proyecto proyecto = new Proyecto();
        proyecto.setDescripcion("Descripcion Test");
        assertEquals("Descripcion Test", proyecto.getDescripcion());
    }

    @Test
    void getTareas() {
        Proyecto proyecto = new Proyecto();
        Tarea tarea1 = new Tarea();
        Tarea tarea2 = new Tarea();
        List<Tarea> tareas = Arrays.asList(tarea1, tarea2);
        proyecto.setTareas(tareas);
        assertEquals(tareas, proyecto.getTareas());
    }

    @Test
    void setTareas() {
        Proyecto proyecto = new Proyecto();
        Tarea tarea1 = new Tarea();
        Tarea tarea2 = new Tarea();
        List<Tarea> tareas = Arrays.asList(tarea1, tarea2);
        proyecto.setTareas(tareas);
        assertEquals(tareas, proyecto.getTareas());
    }
}