/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Connection.DAO;

import com.Hirukar.Project.Connection.ConnectionFactory.DatabaseConnection;
import com.Hirukar.Project.Models.Beans.Disciplina;
import com.Hirukar.Project.Models.Beans.Espaco;
import com.Hirukar.Project.Models.Beans.Ministra;
import com.Hirukar.Project.Models.Beans.Periodo;
import com.Hirukar.Project.Models.Beans.Slotss;
import com.Hirukar.Project.Models.Beans.SubSlot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author RODEMARCK
 */
public abstract class DisciplinasDAO {

    public static Periodo getPeriodo(int idCurso, int nPeriodo) throws ClassNotFoundException, SQLException {
        LinkedList<Periodo> lista = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT * FROM slots
                    INNER JOIN periodo
                    ON periodo.FK_ID_slot=slots.ID
                        INNER JOIN curso
                        ON periodo.FK_ID_curso=curso.ID
                WHERE curso.ID=? AND periodo.n_periodo=?
                        """,
                new Object[]{
                        idCurso,
                        nPeriodo
                },
                rs -> lista.add(new Periodo(rs))
        );
        return lista.getFirst();
    }

    public static Disciplina getDisciplina(int idDisciplina) throws ClassNotFoundException, SQLException {
        LinkedList<Disciplina> lista = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT * FROM disciplina
                WHERE disciplina.ID=?
                """,
                new Object[]{idDisciplina},
                rs->lista.add(new Disciplina(rs))
        );
        return lista.getFirst();
    }

    public static void cadastrar(Disciplina d) throws ClassNotFoundException, SQLException {
        DatabaseConnection.getInstance().connect(
                """
                 INSERT INTO disciplina
                    (Nome,Tipo,Area,Codigo) VALUES
                 (?,?,?,?)
                        """,
                new Object[]{
                    d.getNome(),
                    d.getTipo().name(),
                    d.getArea().name(),
                    d.getCodigo()
                }, 
                null
        );
    }

    public static LinkedList<Disciplina> listar() throws ClassNotFoundException, SQLException {
        LinkedList<Disciplina> lista = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
            "SELECT * FROM disciplina",
                null, 
                rs->{
                    do lista.add(new Disciplina(rs));
                    while(rs.next());
        });
        return lista;
    }

    public static LinkedList<SubSlot> listarSubSlot (int idSlot) throws ClassNotFoundException, SQLException {
        LinkedList<SubSlot> lista = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT * FROM sub_slot
                WHERE slot_id=?
                """,
                new Object[]{idSlot}, 
                rs->{
                    do lista.add(new SubSlot(rs));
                    while(rs.next());
        });
        return lista;
    }

    public static LinkedList<Espaco> listarEspaco (int idSlot) throws ClassNotFoundException, SQLException{
        LinkedList<Espaco> lista = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT * FROM espaco
                WHERE slot_id=?
                """,
                new Object[]{idSlot}, 
                rs->{
                    do lista.add(new Espaco(rs));
                    while(rs.next());
        });
        return lista;
    }

    public static void trocaDisciplina(int idPeriodo, int n1, int n2) throws ClassNotFoundException, SQLException{
        DatabaseConnection.getInstance().connect(
            "CALL troca_disciplinas(?,?,?)",
                new Object[]{
                        idPeriodo,
                        n1,
                        n2
                },
                null
        );
    }
    public static int getIdEspaco (int idPeriodo, int numero) throws ClassNotFoundException, SQLException{
        LinkedList<Integer> lista = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT id FROM espaco
                WHERE periodo_id=? AND numero =?
                """,
            new Object[]{
                    idPeriodo,
                    numero
            },
            rs->lista.add(rs.getInt("id"))
        );
        return lista.getFirst();
    }

    public static Slotss getSlots(int id) throws ClassNotFoundException, SQLException {
        LinkedList<Slotss> lista = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT * FROM slots
                WHERE slots.ID=?
                """,
                new Object[]{id}, 
                rs->lista.add(new Slotss(rs))
        );
        return lista.getFirst();
    }


    public static  int getIdHorarioDisciplina(int idCurso, int nPeriodo, int numero)  throws ClassNotFoundException, SQLException{
        LinkedList<Integer> lista = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT horario_disciplinas.ID FROM horario_disciplinas
                    INNER JOIN periodo
                    ON horario_disciplinas.FK_ID_periodo=periodo.ID
                        INNER JOIN curso
                        ON periodo.FK_ID_curso=curso.ID
                WHERE curso.ID=? AND periodo.n_periodo=? AND horario_disciplinas.numero=?
                """,
                new Object[]{
                        idCurso,
                        nPeriodo,
                        numero
                },
                rs->lista.add(rs.getInt("horario_disciplinas.ID"))
        );
        return lista.getFirst();
    }

	public static ArrayList<Integer> listarPeriodos() {
		return null;
	}

    public static LinkedList<Ministra> listarAlocacoes()  throws ClassNotFoundException, SQLException{
        return null;
    }

    public static void aloca(int idPeriodo, int idProfessor, int iDisciplina)  throws ClassNotFoundException, SQLException{
    }

    public static void atualiza(String nome, Disciplina disciplina) {
    }
}
