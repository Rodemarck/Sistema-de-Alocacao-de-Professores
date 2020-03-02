/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Models.Beans;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Connection.DAO.ProfessorDAO;
import com.Hirukar.Project.Models.Users_.Professor;
import com.Hirukar.Project.Models.constantes.Constantes;
import com.google.gson.reflect.TypeToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author RODEMARCK
 */
public class Ministra {
    private int ID;
    private Professor professor;
    private Disciplina discplina;
    private int periodoId;

    public Ministra(int ID, Professor professor, Disciplina discplina, int periodoId) {
        this.ID = ID;
        this.professor = professor;
        this.discplina = discplina;
        this.periodoId = periodoId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDiscplina() {
        return discplina;
    }

    public void setDiscplina(Disciplina discplina) {
        this.discplina = discplina;
    }

    public int getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(int periodoId) {
        this.periodoId = periodoId;
    }

    @Override
    public String toString() {
        return "Ministra{" +
                "ID=" + ID +
                ", professor=" + professor +
                ", discplina=" + discplina +
                ", periodoId=" + periodoId +
                '}';
    }

    public static Ministra get(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), Ministra.class);
    }

    public static LinkedList<Ministra> getList(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), new TypeToken<LinkedList<Ministra>>(){}.getType());
    }
}
