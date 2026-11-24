package cl.levelupgamer.dto;

public record AuthResponse(String token, String tipo, String nombre, String email, String rol) {}
