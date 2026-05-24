package com.tecsup.Evalucion2.config;

import com.tecsup.Evalucion2.entity.Usuario;
import com.tecsup.Evalucion2.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        crearUsuarioSiNoExiste("admin", "admin123", "ROLE_ADMIN");
        crearUsuarioSiNoExiste("user", "user123", "ROLE_USER");
    }

    private void crearUsuarioSiNoExiste(String username, String password, String rol) {
        if (usuarioRepository.findByUsername(username).isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(passwordEncoder.encode(password));
            usuario.setRol(rol);

            usuarioRepository.save(usuario);
        }
    }
}