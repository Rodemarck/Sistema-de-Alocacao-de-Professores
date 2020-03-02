package com.Hirukar.Project.Models.Users_;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Models.Beans.Disciplina;
import com.Hirukar.Project.Models.Enums.Area;
import com.Hirukar.Project.Models.Enums.TipoUsuario;
import com.Hirukar.Project.Models.constantes.Constantes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Professor implements Serializable{
    private final static long serialVersionUID = -1;
    private final int id;
    private String CPF;
    private String nome;
    private Area area;
    private String login;
    private String senha;
    private TipoUsuario cargo;
    private Disciplina preferencia1;
    private Disciplina preferencia2;


    public Professor(){
        this.id = 0;
        this.login = "";
        this.senha = "";
        this.CPF = "";
        this.cargo = TipoUsuario.PROFESSOR;
    }
    public Professor(int id,String CPF, String nome, Area area, String login, String senha, TipoUsuario cargo) {
        this.id = id;
        this.CPF = CPF;
        this.nome = nome;
        this.area = area;
        this.login = login;
        this.senha = senha;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public TipoUsuario getCargo() {
        return cargo;
    }

    public void setCargo(TipoUsuario cargo) {
        this.cargo = cargo;
    }

    public Disciplina getPreferencia1() {
        return preferencia1;
    }

    public void setPreferencia1(Disciplina preferencia1) {
        this.preferencia1 = preferencia1;
    }

    public Disciplina getPreferencia2() {
        return preferencia2;
    }

    public void setPreferencia2(Disciplina preferencia2) {
        this.preferencia2 = preferencia2;
    }

    @JsonIgnore
    public String getLogin() {
        return login;
    }

    @JsonIgnore
    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    public String getSenha() {
        return senha;
    }

    @JsonIgnore
    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "CPF='" + CPF + '\'' +
                ", nome='" + nome + '\'' +
                ", area=" + area +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", cargo=" + cargo +
                ", preferencia1=" + preferencia1 +
                ", preferencia2=" + preferencia2 +
                '}';
    }

    public static Professor get(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw) , Professor.class);
    }

    public static LinkedList<Professor> getList(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), new TypeToken<LinkedList<Professor>>(){}.getType());
    }

}
