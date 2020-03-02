package com.Hirukar.Project.Models.Beans;

import com.Hirukar.Project.Models.Users_.Professor;
import com.Hirukar.Project.Models.constantes.Constantes;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedList;

public class Curso {
    private final int id;
    private String nome;
    private String sigla;

    public Curso(int id, String nome, String sigla) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sigla='" + sigla + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public static Curso get(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), Curso.class);
    }

    public static LinkedList<Curso> getList(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), new TypeToken<LinkedList<Curso>>(){}.getType());

    }
}
