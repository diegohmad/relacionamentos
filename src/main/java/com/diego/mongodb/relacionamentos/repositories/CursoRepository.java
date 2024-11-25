package com.diego.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.diego.mongodb.relacionamentos.models.Curso;

public interface CursoRepository extends MongoRepository<Curso, String> {

}
