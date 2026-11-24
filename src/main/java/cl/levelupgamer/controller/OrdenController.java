package cl.levelupgamer.controller;

import cl.levelupgamer.model.Orden;
import cl.levelupgamer.service.OrdenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdenController {

    private final OrdenService service;

    public OrdenController(OrdenService service) {
        this.service = service;
    }

    // ADMIN y VENDEDOR pueden consultar todas las órdenes.
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public List<Orden> listar() {
        return service.listar();
    }

    // ADMIN y VENDEDOR pueden consultar el detalle de una orden.
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public Orden obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    // CLIENTE puede generar una orden.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CLIENTE')")
    public Orden crear(
            @RequestBody CrearOrdenRequest request,
            Authentication authentication) {

        return service.crear(
                authentication.getName(),
                request.items()
        );
    }

    public record CrearOrdenRequest(
            List<OrdenService.ItemOrden> items
    ) {}
}
