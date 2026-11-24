package cl.levelupgamer.repository;

import cl.levelupgamer.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigoIgnoreCase(String codigo);
}
