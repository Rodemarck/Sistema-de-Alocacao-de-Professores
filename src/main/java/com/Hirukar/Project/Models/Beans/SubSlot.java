package com.Hirukar.Project.Models.Beans;

import com.Hirukar.Project.Models.Users_.Professor;
import com.Hirukar.Project.Models.constantes.Constantes;
import com.google.gson.reflect.TypeToken;

import java.sql.ResultSet;
import java.util.LinkedList;

public class SubSlot {
    private final int id;
    private int slots_id;
    private int dia;
    private int horario;
    private int numero;

    public SubSlot(int id, int slots_id, int dia, int horario, int numero) {
        this.id = id;
        this.slots_id = slots_id;
        this.dia = dia;
        this.horario = horario;
        this.numero = numero;
    }

    public SubSlot(ResultSet rs) {
        id = 0;
    }

    public int getId() {
        return id;
    }

    public int getSlots_id() {
        return slots_id;
    }

    public void setSlots_id(int slots_id) {
        this.slots_id = slots_id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "SubSlot{" +
                "id=" + id +
                ", slots_id=" + slots_id +
                ", dia=" + dia +
                ", horario=" + horario +
                ", numero=" + numero +
                '}';
    }

    public static SubSlot get(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), SubSlot.class);
    }

    public static LinkedList<SubSlot> getList(String raw){
        return Constantes.gson.fromJson(Constantes.formatarJson(raw), new TypeToken<LinkedList<SubSlot>>(){}.getType());
    }
}
