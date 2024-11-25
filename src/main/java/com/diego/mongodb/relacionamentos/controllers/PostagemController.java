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

import com.diego.mongodb.relacionamentos.models.Postagem;
import com.diego.mongodb.relacionamentos.repositories.PostagemRepository;

@RestController
@RequestMapping("/postagens")
public class PostagemController {

    @Autowired
    private PostagemRepository postagemRepository;

    // Retorna todas as postagens
    @GetMapping
    public List<Postagem> getAll() {
        return postagemRepository.findAll();
    }

    // Retorna uma postagem pelo ID
    @GetMapping("/{id}")
    public Postagem getById(@PathVariable String id) {
        return postagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
    }

    // Cria uma nova postagem
    @PostMapping
    public Postagem create(@RequestBody Postagem postagem) {
        return postagemRepository.save(postagem);
    }

    // Atualiza uma postagem existente pelo ID
    @PutMapping("/{id}")
    public Postagem update(@PathVariable String id, @RequestBody Postagem postagem) {
        Postagem existingPostagem = postagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
        existingPostagem.setTitulo(postagem.getTitulo());
        existingPostagem.setConteudo(postagem.getConteudo());
        return postagemRepository.save(existingPostagem);
    }

    // Deleta uma postagem pelo ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        postagemRepository.deleteById(id);
    }
}
