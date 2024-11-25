package com.diego.mongodb.relacionamentos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.mongodb.relacionamentos.models.Perfil;
import com.diego.mongodb.relacionamentos.models.Usuario;
import com.diego.mongodb.relacionamentos.repositories.PerfilRepository;
import com.diego.mongodb.relacionamentos.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        if (usuario.getPerfil() != null && usuario.getPerfil().getId() == null) {
            Perfil perfilSalvo = perfilRepository.save(usuario.getPerfil());
            usuario.setPerfil(perfilSalvo);
        }
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existingUsuario.setNome(usuario.getNome());
        existingUsuario.setPerfil(usuario.getPerfil());
        return usuarioRepository.save(existingUsuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        usuarioRepository.deleteById(id);
    }
}
