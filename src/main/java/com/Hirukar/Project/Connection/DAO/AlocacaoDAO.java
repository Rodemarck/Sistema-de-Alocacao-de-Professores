package com.Hirukar.Project.Connection.DAO;

import com.Hirukar.Project.Connection.ConnectionFactory.DatabaseConnection;
import com.Hirukar.Project.Models.Beans.Ministra;

import java.sql.SQLException;
import java.util.LinkedList;


public class AlocacaoDAO {
    
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