package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.Tarea;
import ar.edu.undef.fie.tasksPci.infrastructure.TareasRepository;
import ar.edu.undef.fie.tasksPci.services.SlackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
class TareasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TareasRepository tareasRepository;

    @MockBean
    private SlackService slackService;

    @Test
    void obtenerTodasLasTareas() throws Exception {
        mockMvc.perform(get("/api/v1/tareas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerTarea() throws Exception {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Tarea Test");

        when(tareasRepository.findById(1L)).thenReturn(Optional.of(tarea));

        mockMvc.perform(get("/api/v1/tareas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarTarea() throws Exception {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Tarea Test");

        when(tareasRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(tareasRepository.save(tarea)).thenReturn(tarea);

        mockMvc.perform(patch("/api/v1/tareas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Tarea Test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarTarea() throws Exception {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Tarea Test");

        when(tareasRepository.findById(1L)).thenReturn(Optional.of(tarea));

        mockMvc.perform(delete("/api/v1/tareas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}