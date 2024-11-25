package com.diego.mongodb.relacionamentos.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Document(collection = "cursos")
public class Curso {
    @Id
    private String id;
    private String nome;

    @DBRef
    @JsonManagedReference
    private List<Estudante> estudantes;
}
