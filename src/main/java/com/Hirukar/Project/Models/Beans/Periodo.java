/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Models.Beans;
import com.Hirukar.Project.Models.constantes.Constantes;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author RODEMARCK
 */
public class Periodo {
    private final int id;
    private Slots slot;
    private int nPeriodo;
    private int anoLetivo;
    private List<Espaco> espacos;
    private List<Ministra> ministras;
    private Curso curso;

    public Periodo(int id, Slots slot, int nPeriodo, int anoLetivo, List<Espaco> espacos, Curso curso) {
        this.id = id;
        this.slot = slot;
        this.nPeriodo = nPeriodo;
        this.curso = curso;
        this.anoLetivo = anoLetivo;
        this.espacos = espacos;
        this.curso = curso;
    }

    public List<Ministra> getMinistras() {
        return ministras;
    }

    public void setMinistras(List<Ministra> ministras) {
        this.ministras = ministras;
    }

    public Periodo(ResultSet rs) {
        id = 0;
    }

    public int getId() {
        return id;
    }

    public Slots getSlot() {
        return slot;
    }

    public void setSlot(Slots slot) {
        this.slot = slot;
    }

    public int getnPeriodo() {
        return nPeriodo;
    }

    public void setnPeriodo(int nPeriodo) {
        this.nPeriodo = nPeriodo;
    }


    public int getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public List<Espaco> getEspacos() {
        if(espacos != null)
            espacos.sort((o1, o2) -> o1.getNumero() - o2.getNumero());
        return espacos;
    }

    public void setEspacos(List<Espaco> espacos) {
        this.espacos = espacos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Disciplina getDisicplina(int numero){
        for(Espaco e: espacos)
            if(e.getNumero() == numero)
                return e.getDisciplina();
        return null;
    }

    @Override
    public String toString() {
        return "Periodo{" +
                "id=" + id +
                ", slot=" + slot +
                ", nPeriodo=" + nPeriodo +
                ", anoLetivo=" + anoLetivo +
                ", espacos=" + espacos +
                ", ministras=" + ministras +
                ", curso=" + curso +
                '}';
    }

    public static Periodo get (String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), Periodo.class);
    }

    public static LinkedList<Periodo> getList(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), new TypeToken<LinkedList<Periodo>>(){}.getType());
    }

    public static class SubPeriodo{
        private int id;
        private int nPeriodo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getnPeriodo() {
            return nPeriodo;
        }

        public void setnPeriodo(int nPeriodo) {
            this.nPeriodo = nPeriodo;
        }

        public static LinkedList<SubPeriodo> getList(String raw){
            return Constantes.gson.fromJson(Constantes.formatarJson(raw), new TypeToken<LinkedList<SubPeriodo>>(){}.getType());
        }
    }
}
