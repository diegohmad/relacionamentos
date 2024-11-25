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
import com.diego.mongodb.relacionamentos.repositories.PerfilRepository;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    // Retorna todos os perfis
    @GetMapping
    public List<Perfil> getAll() {
        return perfilRepository.findAll();
    }

    // Retorna um perfil pelo ID
    @GetMapping("/{id}")
    public Perfil getById(@PathVariable String id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
    }

    // Cria um novo perfil
    @PostMapping
    public Perfil create(@RequestBody Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    // Atualiza um perfil existente pelo ID
    @PutMapping("/{id}")
    public Perfil update(@PathVariable String id, @RequestBody Perfil perfil) {
        Perfil existingPerfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        existingPerfil.setBio(perfil.getBio());
        existingPerfil.setAvatarUrl(perfil.getAvatarUrl());
        return perfilRepository.save(existingPerfil);
    }

    // Deleta um perfil pelo ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        perfilRepository.deleteById(id);
    }
}
