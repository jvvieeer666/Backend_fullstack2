package cl.levelupgamer.config;
import cl.levelupgamer.model.Producto;
import cl.levelupgamer.model.Usuario;
import cl.levelupgamer.model.Rol;
import cl.levelupgamer.repository.ProductoRepository;
import cl.levelupgamer.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner inicializarDatos(
            ProductoRepository productoRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder encoder) {

        return args -> {

            // CREAR ADMINISTRADOR DE PRUEBA
            String emailAdmin = "admin@gmail.com";

            if (!usuarioRepository.existsByEmailIgnoreCase(emailAdmin)) {

                Usuario admin = new Usuario();

                admin.setNombre("Administrador");
                admin.setEmail(emailAdmin);
                admin.setPassword(encoder.encode("Admin1234"));
                admin.setRol(Rol.ADMIN);

                usuarioRepository.save(admin);

                System.out.println("ADMINISTRADOR CREADO CORRECTAMENTE");

            } else {
                System.out.println("EL ADMINISTRADOR YA EXISTE");
            }
            // CREAR VENDEDOR DE PRUEBA
            String emailVendedor = "javier@gmail.com";

            if (!usuarioRepository.existsByEmailIgnoreCase(emailVendedor)) {

                Usuario vendedor = new Usuario();

                vendedor.setNombre("Vendedor de prueba");
                vendedor.setEmail(emailVendedor);
                vendedor.setPassword(encoder.encode("Vendedor123"));
                vendedor.setRol(Rol.VENDEDOR);

                usuarioRepository.save(vendedor);

                System.out.println("VENDEDOR CREADO CORRECTAMENTE");

            } else {
                System.out.println("EL VENDEDOR YA EXISTE");
            }
            // CARGAR PRODUCTOS SI LA BASE DE DATOS ESTÁ VACÍA
            if (productoRepository.count() > 0) {
                System.out.println("La base de datos ya tiene productos.");
                return;
            }

            guardar(productoRepository, "Control Gamer Inalámbrico",
                    "Accesorios", 59990, 15, "AC001",
                    "https://i5.walmartimages.cl/asr/f2dc7333-726e-46d4-9eed-e66de629fb61.9b79ad009e52a9b2e103d030d98c9160.jpeg",
                    "Control cómodo con botones mapeables y respuesta táctil.");

            guardar(productoRepository, "Auriculares HyperX Cloud II",
                    "Accesorios", 79990, 10, "AC002",
                    "https://media.spdigital.cl/thumbnails/products/ruuqaor9_dac549f9_thumbnail_4096.jpg",
                    "Sonido envolvente, micrófono desmontable y almohadillas cómodas.");

            guardar(productoRepository, "Mouse Logitech G502 HERO",
                    "Accesorios", 49990, 20, "MS001",
                    "https://media.falabella.com/falabellaCL/156495581_01/w=1200,h=1200,fit=pad",
                    "Sensor de alta precisión y botones personalizables.");

            guardar(productoRepository, "Mousepad Razer Goliathus Extended",
                    "Accesorios", 29990, 25, "MP001",
                    "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcR10h795ngPs0Vf3x94aZLNo9I2ZxarYuIRkjIE0aKIHzke8DAb90xHa7M093Vy3VAeokY4-v18_SzhBokVJ9HdPHyqBs6ZhVQhXI9VmdSZZY4s6BUL89n0",
                    "Superficie amplia y suave para movimientos precisos.");

            guardar(productoRepository, "Consola PlayStation 5",
                    "Consolas", 549990, 5, "CO001",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-SpQ9c7n4ZDaBkZz-pKy0NEswPV7iu_tCZMV7c2kljw&s",
                    "Consola de última generación con gráficos impresionantes.");

            guardar(productoRepository, "Silla Gamer Secretlab Titan",
                    "Sillas", 349990, 8, "SG001",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5WmcwZr4f2avB3ZdZbVimkHRhSs7uNbSLRHnQMB5Lzg&s=10",
                    "Silla ergonómica para sesiones de juego prolongadas.");

            guardar(productoRepository, "Polera Gamer Personalizada Level-Up",
                    "Vestuario", 14990, 30, "PP001",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRv3XixjADXXicAg9AHDnWwum48WguHtPI_cU6NZQ7XOQ&s=10",
                    "Camiseta cómoda y personalizable.");

            guardar(productoRepository, "Catan (juego de mesa)",
                    "Juegos de Mesa", 29990, 12, "JM001",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2WnrovxaRwBhbW4MiBUX1IyWvIo2qAvAf7m_sYHeXHw&s=10",
                    "Juego de estrategia para 3 a 4 jugadores.");

            guardar(productoRepository, "Carcassonne (juego de mesa)",
                    "Juegos de Mesa", 24990, 14, "JM002",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbEtJm-tmJgbMtByrwgnuRm97Rdul-D4GqLQbuZ7N6kA&s=10",
                    "Juego de colocación de fichas para 2 a 5 jugadores.");

            guardar(productoRepository, "PC Gamer ASUS ROG Strix",
                    "Computadores", 1299990, 3, "CG001",
                    "https://media.falabella.com/falabellaCL/80719928_01/w=1200,h=1200,fit=pad",
                    "Equipo de alto rendimiento ideal para gaming y streaming.");

            System.out.println("¡10 productos cargados automáticamente!");
        };
    }

    private void guardar(
            ProductoRepository repository,
            String nombre,
            String categoria,
            double precio,
            int stock,
            String codigo,
            String imagen,
            String descripcion) {

        Producto producto = new Producto();

        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(BigDecimal.valueOf(precio));
        producto.setStock(stock);
        producto.setCodigo(codigo);
        producto.setImagen(imagen);
        producto.setDescripcion(descripcion);

        repository.save(producto);
    }
}