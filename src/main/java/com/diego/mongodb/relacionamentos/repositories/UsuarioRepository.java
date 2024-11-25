package com.diego.mongodb. relacionamentos. repositories;

import org. springframework. data.mongodb. repository. MongoRepository;

import com.diego. mongodb. relacionamentos.models .Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

}