package ar.edu.undef.fie.tasksPci.infrastructure;

import ar.edu.undef.fie.tasksPci.domain.entities.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsablesRepository extends JpaRepository<Responsable, Long> {
}