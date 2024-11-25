package com.diego.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.diego.mongodb.relacionamentos.models.Perfil;

public interface PerfilRepository extends MongoRepository<Perfil, String> {

}
