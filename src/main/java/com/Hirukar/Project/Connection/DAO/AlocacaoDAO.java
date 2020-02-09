package com.Hirukar.Project.Connection.DAO;

import com.Hirukar.Project.Connection.ConnectionFactory.DatabaseConnection;
import com.Hirukar.Project.Models.Beans.Ministra;

import java.sql.SQLException;
import java.util.LinkedList;


public class AlocacaoDAO {
    public static LinkedList<Ministra> listarAlocacoes(int anoLetivo, int cpf) throws ClassNotFoundException, SQLException {
        LinkedList<Ministra> list = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT * FROM professor
                    INNER JOIN ministra
                    ON ministra.FK_CPF_professor=professor.CPF
                        INNER JOIN periodo
                        ON ministra.FK_ID_periodo=periodo.ID
                            INNER JOIN disciplina
                            ON ministra.FK_ID_disciplina=disciplina.ID
                """,
                new Object[]{
                        anoLetivo,
                        cpf
                },
                rs -> {
                    do list.add(new Ministra(rs));
                    while (rs.next());
                }
        );
        return list;
    }
    
	public static void aloca(int idPeriodo, int idProfessor, int idDisciplina)  throws ClassNotFoundException, SQLException {
        DatabaseConnection.getInstance().connect(
                """
                 INSERT INTO ministra
                    (FK_id_periodo,FK_CPF_professor,FK_ID_disciplina) VALUES
                 (?,?,?)
                        """,
                new Object[]{
                        idPeriodo,
                        idProfessor,
                        idDisciplina
                },
                null
        );
	}
}