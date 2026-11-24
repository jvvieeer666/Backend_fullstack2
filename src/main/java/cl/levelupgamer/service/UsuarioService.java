package cl.levelupgamer.service;

import cl.levelupgamer.dto.AuthResponse;
import cl.levelupgamer.dto.LoginRequest;
import cl.levelupgamer.dto.RegistroRequest;
import cl.levelupgamer.model.Rol;
import cl.levelupgamer.model.Usuario;
import cl.levelupgamer.repository.UsuarioRepository;
import cl.levelupgamer.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder,
                          AuthenticationManager authenticationManager, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse registrar(RegistroRequest request) {
        if (repository.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese correo");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre().trim());
        usuario.setEmail(request.email().trim().toLowerCase());
        usuario.setPassword(encoder.encode(request.password()));
        usuario.setRol(Rol.CLIENTE); // El registro público nunca permite crear administradores.
        repository.save(usuario);
        return respuesta(usuario);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email().trim().toLowerCase(), request.password())
        );
        Usuario usuario = repository.findByEmailIgnoreCase(request.email().trim())
            .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));
        return respuesta(usuario);
    }

    private AuthResponse respuesta(Usuario usuario) {
        return new AuthResponse(jwtService.generarToken(usuario), "Bearer",
            usuario.getNombre(), usuario.getEmail(), usuario.getRol().name());
    }
}
