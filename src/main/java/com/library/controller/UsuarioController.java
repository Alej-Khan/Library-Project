package com.library.controller;

import com.library.dto.RegistroRequest;
import com.library.dto.UsuarioResponse;
import com.library.entity.Usuario;
import com.library.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    public ResponseEntity<UsuarioResponse> registrarUsuario(@RequestBody RegistroRequest registroRequest) {
        Usuario usuario = usuarioService.registrarUsuario(
                registroRequest.getUsername(),
                registroRequest.getPassword()
        );
        
        UsuarioResponse response = new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRol()
        );
        
        return ResponseEntity.ok(response);
    }
}
