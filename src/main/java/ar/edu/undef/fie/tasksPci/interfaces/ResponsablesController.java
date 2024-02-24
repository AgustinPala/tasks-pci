package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.Responsable;
import ar.edu.undef.fie.tasksPci.infrastructure.ResponsablesRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1")
public class ResponsablesController {
    private final ResponsablesRepository repository;

    public ResponsablesController(ResponsablesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("responsables")
    public CollectionModel<EntityModel<Responsable>> obtenerTodosLosResponsables() {
        List<EntityModel<Responsable>> responsables = repository.findAll().stream()
                .map(responsable -> EntityModel.of(responsable,
                        linkTo(methodOn(ResponsablesController.class).obtenerResponsable(responsable.getId())).withSelfRel(),
                        linkTo(methodOn(ResponsablesController.class).obtenerTodosLosResponsables()).withRel("responsables")))
                .collect(Collectors.toList());

        return CollectionModel.of(responsables,
                linkTo(methodOn(ResponsablesController.class).obtenerTodosLosResponsables()).withSelfRel());
    }

    @PostMapping("responsables")
    public EntityModel<Responsable> crearResponsable(@RequestBody Responsable responsable) {
        Responsable nuevoResponsable = repository.save(responsable);
        return EntityModel.of(nuevoResponsable,
                linkTo(methodOn(ResponsablesController.class).obtenerResponsable(nuevoResponsable.getId())).withSelfRel(),
                linkTo(methodOn(ResponsablesController.class).obtenerTodosLosResponsables()).withRel("responsables"));
    }

    @GetMapping("responsables/{id}")
    public EntityModel<Responsable> obtenerResponsable(@PathVariable Long id) {
        Responsable responsable = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable no encontrado con id: " + id));
        return EntityModel.of(responsable,
                linkTo(methodOn(ResponsablesController.class).obtenerResponsable(id)).withSelfRel(),
                linkTo(methodOn(ResponsablesController.class).obtenerTodosLosResponsables()).withRel("responsables"));
    }

    @PatchMapping("responsables/{id}")
    public EntityModel<Responsable> actualizarResponsable(@PathVariable Long id, @RequestBody Responsable responsableActualizado) {
        Responsable responsable = repository.findById(id)
                .map(responsableExistente -> {
                    Optional.ofNullable(responsableActualizado.getName()).ifPresent(responsableExistente::setName);
                    Optional.ofNullable(responsableActualizado.getSlackUser()).ifPresent(responsableExistente::setSlackUser);
                    Optional.ofNullable(responsableActualizado.getRole()).ifPresent(responsableExistente::setRole);
                    return repository.save(responsableExistente);
                })
                .orElseThrow(() -> new RuntimeException("Responsable no encontrado con id: " + id));
        return EntityModel.of(responsable,
                linkTo(methodOn(ResponsablesController.class).obtenerResponsable(id)).withSelfRel(),
                linkTo(methodOn(ResponsablesController.class).obtenerTodosLosResponsables()).withRel("responsables"));
    }

    @DeleteMapping("responsables/{id}")
    public ResponseEntity<String> eliminarResponsable(@PathVariable Long id) {
        Responsable responsable = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable no encontrado con id: " + id));
        repository.deleteById(id);
        return ResponseEntity.ok("Responsable con id " + id + " eliminada correctamente.");
    }
}