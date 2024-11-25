package com.diego.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.diego.mongodb.relacionamentos.models.Postagem;

public interface PostagemRepository extends MongoRepository<Postagem, String> {

}
