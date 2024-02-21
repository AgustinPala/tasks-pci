package ar.edu.undef.fie.tasksPci.interfaces;

import ar.edu.undef.fie.tasksPci.domain.entities.Proyecto;
import ar.edu.undef.fie.tasksPci.domain.entities.Tarea;
import ar.edu.undef.fie.tasksPci.infrastructure.ProyectoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.hibernate.Hibernate.size;

@RestController
@RequestMapping("api/v1")
public class ProyectoController {
    private final ProyectoRepository repository;

    public ProyectoController(ProyectoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("proyectos")
    public List<Proyecto> obtenerTodosLosProyectos() {
        return repository.findAll();
    }

    @PostMapping("proyectos")
    private void crearProyecto(@RequestBody Proyecto proyecto) {
        repository.save(proyecto);
    }

    @GetMapping("proyectos/{id}")
    private Optional<Proyecto> obtenerProyecto(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PatchMapping("proyectos/{id}")
    private Proyecto actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyectoActualizado) {
        return repository.findById(id)
                .map(proyecto -> {
                    if(proyectoActualizado.getNombre() != null) {
                        proyecto.setNombre(proyectoActualizado.getNombre());
                    }
                    if(proyectoActualizado.getDescripcion() != null) {
                        proyecto.setDescripcion(proyectoActualizado.getDescripcion());
                    }
                    if(proyectoActualizado.getTareas()!= null) {
                        for (int i = 0; i < size(proyectoActualizado.getTareas()); i++) {
                            proyecto.getTareas().add(proyectoActualizado.getTareas().get(i));
                        }
                    }
                    return repository.save(proyecto);
                })
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
    }

    @DeleteMapping("proyectos/{id}")
    private void eliminarProyecto(@PathVariable Long id) {
        repository.deleteById(id);
    }


}