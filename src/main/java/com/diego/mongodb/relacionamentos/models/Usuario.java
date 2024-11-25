package com.diego.mongodb.relacionamentos.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nome;

    @DBRef
    private Perfil perfil;

    @DBRef
    private List<Postagem> postagens;
}
