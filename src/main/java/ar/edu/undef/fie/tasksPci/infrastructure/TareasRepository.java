package ar.edu.undef.fie.tasksPci.infrastructure;

import ar.edu.undef.fie.tasksPci.domain.entities.Tarea;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareasRepository extends JpaRepository<Tarea, Long> {
    @Override
    @EntityGraph(attributePaths = {"responsables"})
    List<Tarea> findAll();
}