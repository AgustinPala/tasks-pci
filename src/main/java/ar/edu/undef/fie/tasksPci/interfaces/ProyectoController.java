package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.Proyecto;
import ar.edu.undef.fie.tasksPci.domain.entities.Responsable;
import ar.edu.undef.fie.tasksPci.domain.entities.Tarea;
import ar.edu.undef.fie.tasksPci.infrastructure.ProyectoRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.Hibernate.size;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1")
public class ProyectoController {
    private final ProyectoRepository repository;

    public ProyectoController(ProyectoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("proyectos")
    public CollectionModel<EntityModel<Proyecto>> obtenerTodosLosProyectos() {
        List<EntityModel<Proyecto>> proyectos = repository.findAll().stream()
                .map(proyecto -> EntityModel.of(proyecto,
                        linkTo(methodOn(ProyectoController.class).obtenerProyecto(proyecto.getId())).withSelfRel(),
                        linkTo(methodOn(ProyectoController.class).obtenerTodosLosProyectos()).withRel("proyectos")))
                .collect(Collectors.toList());

        return CollectionModel.of(proyectos, linkTo(methodOn(ProyectoController.class).obtenerTodosLosProyectos()).withSelfRel());
    }

    @PostMapping("proyectos")
    public EntityModel<Proyecto> crearProyecto(@RequestBody Proyecto proyecto) {
        Proyecto nuevoProyecto = repository.save(proyecto);
        return EntityModel.of(nuevoProyecto,
                linkTo(methodOn(ProyectoController.class).obtenerProyecto(nuevoProyecto.getId())).withSelfRel());
    }

    @GetMapping("proyectos/{id}")
    public EntityModel<Proyecto> obtenerProyecto(@PathVariable Long id) {
        Proyecto proyecto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
        return EntityModel.of(proyecto,
                linkTo(methodOn(ProyectoController.class).obtenerProyecto(id)).withSelfRel(),
                linkTo(methodOn(ProyectoController.class).obtenerTodosLosProyectos()).withRel("proyectos"));
    }

    @PatchMapping("proyectos/{id}")
    public EntityModel<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyectoActualizado) {
        Proyecto proyecto = repository.findById(id)
                .map(proyectoExistente -> {
                    Optional.ofNullable(proyectoActualizado.getNombre()).ifPresent(proyectoExistente::setNombre);
                    Optional.ofNullable(proyectoActualizado.getDescripcion()).ifPresent(proyectoExistente::setDescripcion);
                    Optional.ofNullable(proyectoActualizado.getTareas()).ifPresent(tareas -> {
                        for (Tarea tarea : tareas) {
                            proyectoExistente.getTareas().add(tarea);
                        }
                    });
                    return repository.save(proyectoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
        return EntityModel.of(proyecto,
                linkTo(methodOn(ProyectoController.class).obtenerProyecto(id)).withSelfRel(),
                linkTo(methodOn(ProyectoController.class).obtenerTodosLosProyectos()).withRel("proyectos"));
    }

    @DeleteMapping("proyectos/{id}")
    public ResponseEntity<String> eliminarProyecto(@PathVariable Long id) {
        Proyecto proyecto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
        repository.deleteById(id);
        return ResponseEntity.ok("Proyecto con id " + id + " eliminado correctamente.");
    }
}