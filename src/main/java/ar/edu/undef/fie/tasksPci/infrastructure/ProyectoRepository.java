package ar.edu.undef.fie.tasksPci.infrastructure;

import ar.edu.undef.fie.tasksPci.domain.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
}