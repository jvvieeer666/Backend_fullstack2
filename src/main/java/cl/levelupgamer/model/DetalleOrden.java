package cl.levelupgamer.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_orden")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false)
    @JsonIgnore
    private Orden orden;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonIgnore
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    public DetalleOrden() {}

    public Long getId() { return id; }
    public Orden getOrden() { return orden; }
    public Producto getProducto() { return producto; }
    public Integer getCantidad() { return cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }

    public void setOrden(Orden orden) { this.orden = orden; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    // Datos del producto que se mostrarán en la respuesta JSON.
    public Long getProductoId() {
        return producto != null ? producto.getId() : null;
    }

    public String getProductoNombre() {
        return producto != null ? producto.getNombre() : null;
    }

    public String getProductoCodigo() {
        return producto != null ? producto.getCodigo() : null;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
