package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.Responsable;
import ar.edu.undef.fie.tasksPci.domain.entities.SlackMessage;
import ar.edu.undef.fie.tasksPci.domain.entities.Tarea;
import ar.edu.undef.fie.tasksPci.infrastructure.TareasRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

// https://spring.io/projects/spring-hateoas#samples
// Es el proyecto oficial de Spring para implementar HATEOAS
// Entramos a spring.io > Projects > Spring HATEOAS
// En la parte de 3.3 Affordances esta como lo aplique

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class TareasController {
    private final TareasRepository repository;

    public TareasController(TareasRepository repository) {
        this.repository = repository;
    }

    @GetMapping("tareas")
    public CollectionModel<EntityModel<Tarea>> obtenerTodasLasTareas() {
        List<EntityModel<Tarea>> tareas = repository.findAll().stream()
                .map(tarea -> EntityModel.of(tarea,
                        linkTo(methodOn(TareasController.class).obtenerTarea(tarea.getId())).withSelfRel(),
                        linkTo(methodOn(TareasController.class).obtenerTodasLasTareas()).withRel("tareas")))
                .collect(Collectors.toList());

        return CollectionModel.of(tareas, linkTo(methodOn(TareasController.class).obtenerTodasLasTareas()).withSelfRel());
    }

    @PostMapping("tareas")
    public EntityModel<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Tarea tareaGuardada = repository.save(tarea);

        return EntityModel.of(tareaGuardada,
                linkTo(methodOn(TareasController.class).obtenerTarea(tareaGuardada.getId())).withSelfRel(),
                linkTo(methodOn(TareasController.class).obtenerTodasLasTareas()).withRel("tareas"));
    }

    @GetMapping("tareas/{id}")
    public EntityModel<Tarea> obtenerTarea(@PathVariable Long id) {
        Tarea tarea = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));

        Link selfLink = linkTo(methodOn(TareasController.class).obtenerTarea(id)).withSelfRel();
        Link tareasLink = linkTo(methodOn(TareasController.class).obtenerTodasLasTareas()).withRel("tareas");

        return EntityModel.of(tarea, selfLink, tareasLink);
    }

    @PatchMapping("tareas/{id}")
    public EntityModel<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tareaActualizada) {
        Tarea tareaGuardada = repository.findById(id)
                .map(tarea -> {
                    Optional.ofNullable(tareaActualizada.getNumeroDeRequerimiento())
                            .ifPresent(tarea::setNumeroDeRequerimiento);
                    Optional.ofNullable(tareaActualizada.getDescripcion())
                            .ifPresent(tarea::setDescripcion);
                    Optional.ofNullable(tareaActualizada.getTitulo())
                            .ifPresent(tarea::setTitulo);
                    Optional.ofNullable(tareaActualizada.getResponsables())
                            .ifPresent(tarea::setResponsables);
                    Optional.ofNullable(tareaActualizada.getEstado())
                            .ifPresent(tarea::setEstado);
                    return repository.save(tarea);
                })
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));

        return EntityModel.of(tareaGuardada,
                linkTo(methodOn(TareasController.class).obtenerTarea(tareaGuardada.getId())).withSelfRel(),
                linkTo(methodOn(TareasController.class).obtenerTodasLasTareas()).withRel("tareas"));
    }

    @DeleteMapping("tareas/{id}")
    public ResponseEntity<String> eliminarTarea(@PathVariable Long id) {
        Tarea tarea = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));
        repository.deleteById(id);

        return ResponseEntity.ok("Tarea con id " + id + " eliminada correctamente.");
    }

    // Porque un POST, si no estamos creando nada?
    // Porque estamos modificando el estado de la aplicación
    // Estamos creando una nueva acción en el servidor
    // En términos de REST, estamos creando un nuevo recurso
    // por lo que veo correcto utilizar un POST

    @PostMapping("tareas/slack")
    public RepresentationModel<?> enviarMensajesSlack() {
        enviarMensajeTareasPorEstado(Tarea.Estado.PENDIENTE);
        enviarMensajeTareasPorEstado(Tarea.Estado.COMPLETA);

        Link tareasPendientesLink = linkTo(methodOn(TareasController.class).enviarMensajeTareasPendientes()).withRel("tareasPendientes");
        Link tareasCompletasLink = linkTo(methodOn(TareasController.class).enviarMensajeTareasCompletas()).withRel("tareasCompletas");
        Link allTaskLink = linkTo(methodOn(TareasController.class).obtenerTodasLasTareas()).withRel("tareas");

        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(allTaskLink, tareasPendientesLink, tareasCompletasLink);

        return model;
    }

    @PostMapping("tareas/pendientes")
    public RepresentationModel<?> enviarMensajeTareasPendientes() {
        enviarMensajeTareasPorEstado(Tarea.Estado.PENDIENTE);

        Link tareasPendientesLink = linkTo(methodOn(TareasController.class).enviarMensajeTareasPendientes()).withRel("tareasPendientes");
        Link tareasCompletasLink = linkTo(methodOn(TareasController.class).enviarMensajeTareasCompletas()).withRel("tareasCompletas");
        Link allTaskLink = linkTo(methodOn(TareasController.class).obtenerTodasLasTareas()).withRel("tareas");

        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(allTaskLink, tareasPendientesLink, tareasCompletasLink);

        return model;
    }

    @PostMapping("tareas/completas")
    public RepresentationModel<?> enviarMensajeTareasCompletas() {
        enviarMensajeTareasPorEstado(Tarea.Estado.COMPLETA);

        Link tareasPendientesLink = linkTo(methodOn(TareasController.class).enviarMensajeTareasPendientes()).withRel("tareasPendientes");
        Link tareasCompletasLink = linkTo(methodOn(TareasController.class).enviarMensajeTareasCompletas()).withRel("tareasCompletas");
        Link allTaskLink = linkTo(methodOn(TareasController.class).obtenerTodasLasTareas()).withRel("tareas");

        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(allTaskLink, tareasPendientesLink, tareasCompletasLink);

        return model;
    }

    private void enviarMensajeTareasPorEstado(Tarea.Estado estado) {
        List<Tarea> tareas = repository.findAll();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (Tarea tarea : tareas) {
            if (tarea.getEstado() == estado) {
                try {
                    StringBuilder responsables = new StringBuilder();
                    for (Responsable responsable : tarea.getResponsables()) {
                        responsables.append("<@").append(responsable.getSlackUser()).append(">").append(", ");
                    }
                    if (responsables.length() > 0) {
                        responsables.setLength(responsables.length() - 2);
                    }
                    SlackMessage mensajeSlack = new SlackMessage();
                    mensajeSlack.setMessage("Tarea " + estado + ":\n- Titulo de la tarea: " + tarea.getTitulo() + "\n- Descripción de la tarea: " + tarea.getDescripcion() +
                            "\n- Responsables: " + responsables.toString() + "\n- Prioridad: " + tarea.getPrioridad());
                    mensajeSlack.setEmoji(":memo:");

                    HttpEntity<SlackMessage> request = new HttpEntity<>(mensajeSlack, headers);
                    restTemplate.postForObject("http://localhost:8081/api/v1/slack", request, String.class);
                } catch (Exception e) {
                    System.out.println("Error al enviar el mensaje a Slack: " + e.getMessage());
                }
            } else {
                System.out.println("La tarea " + tarea.getId() + " no está en estado " + estado);
            }
        }
    }

    // El formato aca es el correcto :D
    @Scheduled(cron = "0 00 10 * * ?")
    public void enviarMensajeRecurrentes() {
        List<Tarea> tareas = repository.findAll();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (Tarea tarea : tareas) {
            if (tarea.getEstado() == Tarea.Estado.PENDIENTE) {
                try {
                    // Forzar la inicialización de la colección 'responsables'
                    tarea.getResponsables().size();

                    StringBuilder responsables = new StringBuilder();
                    for (Responsable responsable : tarea.getResponsables()) {
                        responsables.append("<@").append(responsable.getSlackUser()).append(">").append(", ");
                    }

                    // Remove the trailing comma and space
                    if (responsables.length() > 0) {
                        responsables.setLength(responsables.length() - 2);
                    }

                    SlackMessage mensajeSlack = new SlackMessage();
                    mensajeSlack.setMessage("Buen dia son las 10:00hs hora de pasar status de las tareas pendientes!\n*Tarea Pendiente*\n* Titulo de la tarea: " + tarea.getTitulo() + "\n* Descripción de la tarea: " + tarea.getDescripcion() +
                            "\n* Responsables: " + responsables.toString() + "\n* Prioridad: " + tarea.getPrioridad());
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