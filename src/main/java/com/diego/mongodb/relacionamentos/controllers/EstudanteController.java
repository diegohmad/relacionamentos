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

import com.diego.mongodb.relacionamentos.models.Curso;
import com.diego.mongodb.relacionamentos.models.Estudante;
import com.diego.mongodb.relacionamentos.repositories.CursoRepository;
import com.diego.mongodb.relacionamentos.repositories.EstudanteRepository;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteRepository estudanteRepository;
    private CursoRepository cursoRepository;

    @GetMapping
    public List<Estudante> getAll() {
        return estudanteRepository.findAll(); // Cursos devem ser carregados automaticamente
    }

    // Retorna um estudante pelo ID
    @GetMapping("/{id}")
    public Estudante getById(@PathVariable String id) {
        return estudanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
    }

    // Cria um novo estudante
    @PostMapping
    public Estudante create(@RequestBody Estudante estudante) {
        return estudanteRepository.save(estudante);
    }

    // Atualiza um estudante existente pelo ID
    @PutMapping("/{id}")
    public Estudante update(@PathVariable String id, @RequestBody Estudante estudante) {
        // Buscar estudante existente
        Estudante existingEstudante = estudanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));

        // Buscar os cursos antigos associados ao estudante
        List<Curso> cursosAntigos = existingEstudante.getCursos();

        // Atualizar os dados do estudante
        existingEstudante.setNome(estudante.getNome());
        existingEstudante.setCursos(estudante.getCursos());

        // Atualizar o estudante no repositório
        Estudante updatedEstudante = estudanteRepository.save(existingEstudante);

        // Atualizar os cursos antigos: remover a referência ao estudante
        if (cursosAntigos != null) {
            for (Curso curso : cursosAntigos) {
                curso.getEstudantes().removeIf(e -> e.getId().equals(id));
                cursoRepository.save(curso);
            }
        }

        // Atualizar os cursos novos: adicionar o estudante
        if (estudante.getCursos() != null) {
            for (Curso curso : estudante.getCursos()) {
                Curso cursoDoBanco = cursoRepository.findById(curso.getId())
                        .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
                cursoDoBanco.getEstudantes().add(updatedEstudante);
                cursoRepository.save(cursoDoBanco);
            }
        }

        return updatedEstudante;
    }

    // Deleta um estudante pelo ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        estudanteRepository.deleteById(id);
    }
}
