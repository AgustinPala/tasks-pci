package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.Responsable;
import ar.edu.undef.fie.tasksPci.infrastructure.ResponsablesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class ResponsablesController {
    private final ResponsablesRepository repository;


    public ResponsablesController(ResponsablesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("responsables")
    public List<Responsable> obtenerTodosLosResponsables() {
        return repository.findAll();
    }

    @PostMapping("responsables")
    private void crearTarea(@RequestBody Responsable responsable) {
        repository.save(responsable);
    }

    @GetMapping("responsables/{id}")
    private Optional<Responsable> obtenerResponsable(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PatchMapping("responsables/{id}")
    private Responsable actualizarResponsable(@PathVariable Long id, @RequestBody Responsable responsableActualizado) {
        return repository.findById(id)
                .map(responsable -> {
                    if (responsableActualizado.getName() != null) {
                        responsable.setName(responsableActualizado.getName());
                    }
                    if (responsableActualizado.getSlackUser() != null) {
                        responsable.setSlackUser(responsableActualizado.getSlackUser());
                    }
                    if (responsableActualizado.getRole() != null) {
                        responsable.setRole(responsableActualizado.getRole());
                    }
                    return repository.save(responsable);
                })
                .orElseThrow(() -> new RuntimeException("Responsable no encontrado con id: " + id));
    }

    @DeleteMapping("responsables/{id}")
    private ResponseEntity<String> eliminarResponsable(@PathVariable Long id) {
        return repository.findById(id)
                .map(responsable -> {
                    String nombre = responsable.getName();
                    repository.deleteById(id);
                    return ResponseEntity.ok("Se eliminó el responsable " + nombre + " con el id " + id);
                })
                .orElseThrow(() -> new RuntimeException("Responsable no encontrado con id: " + id));
    }
}