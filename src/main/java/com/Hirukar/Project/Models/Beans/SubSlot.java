package com.Hirukar.Project.Models.Beans;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Models.Enums.DiasDaSemana;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;/*

/**
 *
 * @author RODEMARCK
 */
public class SubSlot {
    private final int id;
    public short dia;
    public short hora;
    public short numero;

    public SubSlot(ResultSet rs) throws SQLException{
        this.id = rs.getInt("id");
        this.dia = rs.getShort("dia");
        this.hora = rs.getShort("hora");
        this.numero = rs.getShort("numero");
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
}
