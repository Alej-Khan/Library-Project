package com.library.config;

import com.library.entity.Usuario;
import com.library.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create a bibliotecario user if not exists
            if (usuarioRepository.findByUsername("bibliotecario").isEmpty()) {
                Usuario bibliotecario = new Usuario();
                bibliotecario.setUsername("bibliotecario");
                bibliotecario.setPassword(passwordEncoder.encode("password123"));
                bibliotecario.setRol("BIBLIOTECARIO");
                usuarioRepository.save(bibliotecario);
                logger.info("Bibliotecario user created: username=bibliotecario, password=password123");
            }

            // Create a miembro user if not exists
            if (usuarioRepository.findByUsername("miembro").isEmpty()) {
                Usuario miembro = new Usuario();
                miembro.setUsername("miembro");
                miembro.setPassword(passwordEncoder.encode("password123"));
                miembro.setRol("MIEMBRO");
                usuarioRepository.save(miembro);
                logger.info("Miembro user created: username=miembro, password=password123");
            }
        };
    }
}
