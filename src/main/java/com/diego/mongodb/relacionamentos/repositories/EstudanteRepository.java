package com.diego.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.diego.mongodb.relacionamentos.models.Estudante;

public interface EstudanteRepository extends MongoRepository<Estudante, String> {

}
