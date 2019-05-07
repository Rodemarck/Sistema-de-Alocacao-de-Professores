/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Models.Beans;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Models.Beans.Enums.Area;
import com.Hirukar.Project.Models.Beans.Enums.Cursos;
import com.Hirukar.Project.Models.Beans.Enums.TipoDisciplina;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author RODEMARCK
 */
public class Horarios__ {
    private final int id;
    private int periodo;
    private Slots slot;
    private Cursos curso;
    private Disciplina__[] disciplinas = new Disciplina__[6];
    
    public Horarios__(){    
        this.id = 0;
        slot = new Slots();
        periodo = 3;
        curso = Cursos.BCC;
        disciplinas[0] = new Disciplina__();
        disciplinas[1] = new Disciplina__("prog", "0001", "#373a07", Cursos.BCC, Area.ENCISO, TipoDisciplina.OBRIGATORIA);
        disciplinas[2] = new Disciplina__("alg", "0002", "#700202", Cursos.BCC, Area.ARC, TipoDisciplina.OBRIGATORIA);
        disciplinas[3] = new Disciplina__("calc", "0004", "#144b03", Cursos.BCC, Area.ARC, TipoDisciplina.OBRIGATORIA);
        disciplinas[4] = new Disciplina__("icc", "0005", "#9e6105", Cursos.BCC, Area.ARC, TipoDisciplina.OBRIGATORIA);
        disciplinas[5] = new Disciplina__("disc", "0006", "#140458", Cursos.BCC, Area.FC, TipoDisciplina.OBRIGATORIA);
    }

    public int getId() {
        return id;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
    
    public Horarios__(int id) throws SQLException, ClassNotFoundException{
        DisciplinasDAO dao = new DisciplinasDAO();
        ResultSet rs = null;
        try {
            dao.criaConexao();
            rs = dao.getHorario(id);
            if(!rs.next())
                throw new SQLException("Critical error");
            this.id = rs.getInt("Horario.id");
            this.slot = new Slots(dao.getSlots(rs.getInt("Horario.slots")));
            rs = dao.getDisciplina(rs.getInt("Horario.aulas"));
            for(Disciplina__ d : this.disciplinas)
                d = new Disciplina__(rs);
        }catch(SQLException | ClassNotFoundException e){
            throw e;
        }finally{
            dao.fechaConexao();
        }
        
    }
    
    public Disciplina__ get(Object index){
        return disciplinas[Integer.parseInt(""+index)];
    }
    
    public Slots getSlot() {
        return slot;
    }

    public void setSlot(Slots slot) {
        this.slot = slot;
    }

    public Disciplina__[] getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Disciplina__[] disciplinas) {
        this.disciplinas = disciplinas;
    }
    
    public boolean troca(int n1, int n2){
        return this.slot.troca(n1,n2);
    }
    
}
