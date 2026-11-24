package cl.levelupgamer.service;

import cl.levelupgamer.model.*;
import cl.levelupgamer.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public OrdenService(
            OrdenRepository ordenRepository,
            ProductoRepository productoRepository,
            UsuarioRepository usuarioRepository) {
        this.ordenRepository = ordenRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Orden> listar() {
        return ordenRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Orden obtener(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Orden no encontrada: " + id));
    }

    public Orden crear(String emailCliente, List<ItemOrden> items) {

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException(
                    "La orden debe contener al menos un producto");
        }

        Usuario cliente = usuarioRepository
                .findByEmailIgnoreCase(emailCliente)
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado"));

        Orden orden = new Orden();
        orden.setCliente(cliente);
        orden.setFecha(LocalDateTime.now());
        orden.setEstado("PENDIENTE");

        BigDecimal total = BigDecimal.ZERO;

        for (ItemOrden item : items) {

            if (item.cantidad() == null || item.cantidad() <= 0) {
                throw new IllegalArgumentException(
                        "La cantidad debe ser mayor que cero");
            }

            Producto producto = productoRepository
                    .findById(item.productoId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Producto no encontrado: " + item.productoId()));

            if (producto.getStock() < item.cantidad()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para: " + producto.getNombre());
            }

            BigDecimal precio = producto.getPrecio();
            BigDecimal subtotal = precio.multiply(
                    BigDecimal.valueOf(item.cantidad()));

            DetalleOrden detalle = new DetalleOrden();
            detalle.setProducto(producto);
            detalle.setCantidad(item.cantidad());
            detalle.setPrecioUnitario(precio);

            orden.agregarDetalle(detalle);
            total = total.add(subtotal);

            producto.setStock(producto.getStock() - item.cantidad());
            productoRepository.save(producto);
        }

        orden.setTotal(total);

        return ordenRepository.save(orden);
    }

    public record ItemOrden(Long productoId, Integer cantidad) {}
}