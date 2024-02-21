package ar.edu.undef.fie.tasksPci.infrastructure;

import ar.edu.undef.fie.tasksPci.domain.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareasRepository extends JpaRepository<Tarea, Long> {
}