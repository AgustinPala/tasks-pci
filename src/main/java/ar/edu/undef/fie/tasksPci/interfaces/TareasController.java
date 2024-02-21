package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.Responsable;
import ar.edu.undef.fie.tasksPci.domain.entities.SlackMessage;
import ar.edu.undef.fie.tasksPci.domain.entities.Tarea;
import ar.edu.undef.fie.tasksPci.infrastructure.ProyectoRepository;
import ar.edu.undef.fie.tasksPci.infrastructure.TareasRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class TareasController {
    private final TareasRepository repository;

    public TareasController(TareasRepository repository) {
        this.repository = repository;
    }

    @GetMapping("tareas")
    public List<Tarea> obtenerTodosLosResponsables() {
        return repository.findAll();
    }

    @PostMapping("tareas")
    private void crearTarea(@RequestBody Tarea tarea) {
        repository.save(tarea);
    }

    @GetMapping("tareas/{id}")
    private Optional<Tarea> obtenerTarea(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PatchMapping("tareas/{id}")
    private Tarea actualizarTarea(@PathVariable Long id, @RequestBody Tarea tareaActualizada) {
        return repository.findById(id)
                .map(tarea -> {
                    if(tareaActualizada.getNumeroDeRequerimiento() != null) {
                        tarea.setNumeroDeRequerimiento(tareaActualizada.getNumeroDeRequerimiento());
                    }
                    if(tareaActualizada.getDescripcion() != null) {
                        tarea.setDescripcion(tareaActualizada.getDescripcion());
                    }
                    if(tareaActualizada.getTitulo() != null) {
                        tarea.setTitulo(tareaActualizada.getTitulo());
                    }
                    if(tareaActualizada.getResponsables() != null){
                        tarea.setResponsables(tareaActualizada.getResponsables());
                    }
                    if(tareaActualizada.getEstado() != null){
                        tarea.setEstado(tareaActualizada.getEstado());
                    }
                    return repository.save(tarea);
                })
                .orElseThrow(() -> new RuntimeException("Tarea no encontrado con id: " + id));
    }

    @DeleteMapping("tareas/{id}")
    private void eliminarTarea(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // Porque un POST, si no estamos creando nada?
    // Porque estamos modificando el estado de la aplicación
    // Estamos creando una nueva acción en el servidor
    // En términos de REST, estamos creando un nuevo recurso
    // por lo que veo correcto utilizar un POST

    @PostMapping("tareas/slack")
    public void enviarMensajesSlack() {
        List<Tarea> tareas = repository.findAll();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (Tarea tarea : tareas) {
            SlackMessage mensajeSlack = new SlackMessage();
            mensajeSlack.setMessage("Descripción de la tarea: " + tarea.getDescripcion() + ", Responsables: " + tarea.getResponsables());
            mensajeSlack.setEmoji(":memo:");

            HttpEntity<SlackMessage> request = new HttpEntity<>(mensajeSlack, headers);
            restTemplate.postForObject("http://localhost:8081/api/v1/slack", request, String.class);
        }
    }

    @PostMapping("tareas/pendientes")
    private void enviarMensajeTareasPendientes() {
        List<Tarea> tareas = repository.findAll();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (Tarea tarea : tareas) {
            if (tarea.getEstado() == Tarea.Estado.PENDIENTE) {
                try {
                    SlackMessage mensajeSlack = new SlackMessage();
                    mensajeSlack.setMessage("Descripción de la tarea: " + tarea.getDescripcion() + ", Responsables: " + tarea.getResponsables());
                    mensajeSlack.setEmoji(":memo:");

                    HttpEntity<SlackMessage> request = new HttpEntity<>(mensajeSlack, headers);
                    restTemplate.postForObject("http://localhost:8081/api/v1/slack", request, String.class);
                } catch (Exception e) {
                    System.out.println("Error al enviar el mensaje a Slack: " + e.getMessage());
                }
            }
        }
    }

    @PostMapping("tareas/completas")
    private void enviarMensajeTareasCompletas() {
        List<Tarea> tareas = repository.findAll();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (Tarea tarea : tareas) {
            if (tarea.getEstado() == Tarea.Estado.COMPLETA) {
                try {
                    SlackMessage mensajeSlack = new SlackMessage();
                    mensajeSlack.setMessage("Descripción de la tarea: " + tarea.getDescripcion() + ", Responsables: " + tarea.getResponsables());
                    mensajeSlack.setEmoji(":memo:");

                    HttpEntity<SlackMessage> request = new HttpEntity<>(mensajeSlack, headers);
                    restTemplate.postForObject("http://localhost:8081/api/v1/slack", request, String.class);
                } catch (Exception e) {
                    System.out.println("Error al enviar el mensaje a Slack: " + e.getMessage());
                }
            }
        }
    }
}