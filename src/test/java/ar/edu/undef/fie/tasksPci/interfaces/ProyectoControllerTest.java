package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.Proyecto;
import ar.edu.undef.fie.tasksPci.infrastructure.ProyectoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProyectoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProyectoRepository proyectoRepository;

    @Test
    void obtenerTodosLosProyectos() throws Exception {
        mockMvc.perform(get("/api/v1/proyectos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerProyecto() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Proyecto Test");

        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));

        mockMvc.perform(get("/api/v1/proyectos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarProyecto() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Proyecto Test");

        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));
        when(proyectoRepository.save(proyecto)).thenReturn(proyecto);

        mockMvc.perform(patch("/api/v1/proyectos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Proyecto Test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarProyecto() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Proyecto Test");

        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));

        mockMvc.perform(delete("/api/v1/proyectos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}