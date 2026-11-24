package cl.levelupgamer.repository;

import cl.levelupgamer.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
import java.util.Optional;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Override
    @EntityGraph(attributePaths = {"cliente", "detalles", "detalles.producto"})
    List<Orden> findAll();

    @Override
    @EntityGraph(attributePaths = {"cliente", "detalles", "detalles.producto"})
    Optional<Orden> findById(Long id);
}
