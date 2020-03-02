package com.Hirukar.Project.Models.Beans;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Models.Users_.Professor;
import com.Hirukar.Project.Models.constantes.Constantes;
import com.google.gson.reflect.TypeToken;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author RODEMARCK
 */
import java.util.LinkedList;
import java.util.List;
public class Slots {
    private final int id;
    private String nome;
    private int turno;
    private List<SubSlot> subSlots;

    public Slots(ResultSet rs) throws SQLException, ClassNotFoundException {
        this.id = rs.getInt("id");
        this.nome = rs.getString("nome");
        this.subSlots = DisciplinasDAO.listarSubSlot(id);
    }

    /**
     * @return the subSlots
     */
    public List<SubSlot> getSubSlots() {
        return subSlots;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the turno
     */
    public int getTurno() {
        return turno;
    }

    /**
     * @param turno the turno to set
     */
    public void setTurno(int turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Slots{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", turno=" + turno +
                ", subSlots=" + subSlots +
                '}';
    }

    public static Slots get(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), Slots.class);
    }

    public static LinkedList<Slots> getList(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), new TypeToken<LinkedList<Slots>>(){}.getType());

    }
}
