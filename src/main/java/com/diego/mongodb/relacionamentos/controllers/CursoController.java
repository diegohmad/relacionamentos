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
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;
    private EstudanteRepository estudanteRepository;

    // Retorna todos os cursos
    @GetMapping
    public List<Curso> getAll() {
        return cursoRepository.findAll();
    }

    // Retorna um curso pelo ID
    @GetMapping("/{id}")
    public Curso getById(@PathVariable String id) {
        return cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    // Cria um novo curso
    @PostMapping
    public Curso create(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    // Atualiza um curso existente pelo ID
    @PutMapping("/{id}")
public Curso update(@PathVariable String id, @RequestBody Curso curso) {
    // Buscar curso existente no banco
    Curso existingCurso = cursoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

    // Buscar os estudantes antigos associados ao curso
    List<Estudante> estudantesAntigos = existingCurso.getEstudantes();

    // Atualizar o nome do curso e a lista de estudantes
    existingCurso.setNome(curso.getNome());
    existingCurso.setEstudantes(curso.getEstudantes());

    // Atualizar o curso no repositório
    Curso updatedCurso = cursoRepository.save(existingCurso);

    // Atualizar os estudantes antigos: remover o curso
    if (estudantesAntigos != null) {
        for (Estudante estudante : estudantesAntigos) {
            estudante.getCursos().removeIf(c -> c.getId().equals(id));
            estudanteRepository.save(estudante);
        }
    }

    // Atualizar os estudantes novos: adicionar o curso
    if (curso.getEstudantes() != null) {
        for (Estudante estudante : curso.getEstudantes()) {
            Estudante estudanteDoBanco = estudanteRepository.findById(estudante.getId())
                    .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
            estudanteDoBanco.getCursos().add(updatedCurso);
            estudanteRepository.save(estudanteDoBanco);
        }
    }

    return updatedCurso;
}


    // Deleta um curso pelo ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        cursoRepository.deleteById(id);
    }
}
