package cl.levelupgamer.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(nullable = false)
    private String estado = "PENDIENTE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    private Usuario cliente;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<DetalleOrden> detalles = new ArrayList<>();

    public Orden() {}

    public void agregarDetalle(DetalleOrden detalle) {
        detalles.add(detalle);
        detalle.setOrden(this);
    }

    public Long getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public BigDecimal getTotal() { return total; }
    public String getEstado() { return estado; }
    public Usuario getCliente() { return cliente; }
    public List<DetalleOrden> getDetalles() { return detalles; }

    public Long getClienteId() {
        return cliente != null ? cliente.getId() : null;
    }

    public String getClienteNombre() {
        return cliente != null ? cliente.getNombre() : null;
    }

    public String getClienteEmail() {
        return cliente != null ? cliente.getEmail() : null;
    }

    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }
}

