package cl.levelupgamer.service;

import cl.levelupgamer.model.Producto;
import cl.levelupgamer.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Producto> listar() { return repository.findAll(); }

    @Transactional(readOnly = true)
    public Producto obtener(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    public Producto crear(Producto producto) {
        if (repository.findByCodigoIgnoreCase(producto.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un producto con ese código");
        }
        producto.setId(null);
        return repository.save(producto);
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto actual = obtener(id);
        repository.findByCodigoIgnoreCase(datos.getCodigo()).ifPresent(otro -> {
            if (!otro.getId().equals(id)) {
                throw new IllegalArgumentException("El código ya pertenece a otro producto");
            }
        });
        actual.setNombre(datos.getNombre());
        actual.setCategoria(datos.getCategoria());
        actual.setPrecio(datos.getPrecio());
        actual.setStock(datos.getStock());
        actual.setCodigo(datos.getCodigo());
        actual.setImagen(datos.getImagen());
        actual.setDescripcion(datos.getDescripcion());
        return repository.save(actual);
    }

    public void eliminar(Long id) {
        Producto producto = obtener(id);
        repository.delete(producto);
    }
}
