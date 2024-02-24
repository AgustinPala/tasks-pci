package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.Responsable;
import ar.edu.undef.fie.tasksPci.infrastructure.ResponsablesRepository;
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
class ResponsablesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResponsablesRepository responsablesRepository;

    @Test
    void obtenerTodosLosResponsables() throws Exception {
        mockMvc.perform(get("/api/v1/responsables")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerResponsable() throws Exception {
        Responsable responsable = new Responsable();
        responsable.setId(1L);
        responsable.setName("Responsable Test");

        when(responsablesRepository.findById(1L)).thenReturn(Optional.of(responsable));

        mockMvc.perform(get("/api/v1/responsables/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarResponsable() throws Exception {
        Responsable responsable = new Responsable();
        responsable.setId(1L);
        responsable.setName("Responsable Test");

        when(responsablesRepository.findById(1L)).thenReturn(Optional.of(responsable));
        when(responsablesRepository.save(responsable)).thenReturn(responsable);

        mockMvc.perform(patch("/api/v1/responsables/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Responsable Test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarResponsable() throws Exception {
        Responsable responsable = new Responsable();
        responsable.setId(1L);
        responsable.setName("Responsable Test");

        when(responsablesRepository.findById(1L)).thenReturn(Optional.of(responsable));

        mockMvc.perform(delete("/api/v1/responsables/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}