package com.diego.mongodb.relacionamentos.models;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CursoDeserializer extends JsonDeserializer<Curso> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Curso deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String id = p.getText(); // Captura o ID como texto
        return mongoTemplate.findById(id, Curso.class); // Busca o curso no MongoDB
    }
}



