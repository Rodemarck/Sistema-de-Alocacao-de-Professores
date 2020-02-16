package com.Hirukar.Project.Connection.DAO;

import com.Hirukar.Project.Connection.ConnectionFactory.DatabaseConnection;
import com.Hirukar.Project.Models.Users_.Professor;

import java.sql.SQLException;
import java.util.LinkedList;

public final class ProfessorDAO {
    
    public static Professor getPeloNome(String nome) throws ClassNotFoundException, SQLException {
        LinkedList<Professor> list = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT * FROM professor
                WHERE professor.login=?
                """,
                new Object[]{nome},
                rs->list.add(new Professor(rs))
        );
        System.out.println('*');
        return list.getFirst();
    }
    
    public static LinkedList<Professor> listar() throws ClassNotFoundException, SQLException {
        LinkedList<Professor> list = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                "SELECT * FROM professor",
                null,
                rs -> {
                    do list.add(new Professor(rs));
                    while(rs.next());
                }
        );
        return list;
    }
    
    public static void cadastrar(Professor professor) throws ClassNotFoundException, SQLException {
        DatabaseConnection.getInstance().connect(
                """
                 INSERT INTO professor
                    (CPF, Nome, Area,login,senha,cargo) VALUES
                 (?,?,?,?,?,?)
                 """,
                new Object[]{
                        professor.getCPF(),
                        professor.getNome(),
                        professor.getLogin(),
                        professor.getSenha(),
                        professor.getArea().name(),
                        professor.getCargo().name()
                },
                null
        );
    }
    
    public static void atualiza(String login, Professor profNovo) throws ClassNotFoundException, SQLException {
        DatabaseConnection.getInstance().connect(
                """
                UPDATE professor
                    SET
                        Nome = ?,
                        CPF = ?,
                        Area = ?,
                        FK_Disciplina_Preferencia_1 = ?,
                        FK_Disciplina_Preferencia_2 = ?,
                        login = ?,
                        senha = ?
                WHERE 	professor.nome=?
                        """,
                new Object[]{
                        profNovo.getNome(),
                        profNovo.getCPF(),
                        profNovo.getArea().name(),
                        profNovo.getPreferencia1().getId(),
                        profNovo.getPreferencia2().getId(),
                        login,
                        profNovo.getSenha()
                },
                null
        );
        
    }

    public static void deletar(Professor professor) throws SQLException, ClassNotFoundException {
        DatabaseConnection.getInstance().connect(
                """
                 DELETE FROM professor \\n" +
                 WHERE professor.cpf=? "
                 """,
                new Object[]{professor.getCPF()},
                null
        );
    }
    
    
    public static Professor getPeloID(int professorID) throws SQLException, ClassNotFoundException {
        LinkedList<Professor> list = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                SELECT * FROM professor
                WHETE professor.id=?
                """,
                new Object[]{professorID},
                rs->list.add(new Professor(rs))
        );
        return list.getFirst();
    }
    
    public static LinkedList<Professor> listarProfessoresAptos(int anoLetivo) throws ClassNotFoundException, SQLException {
        LinkedList<Professor> list = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                """
                 select * from professor as P
                    where 1 and 3> (SELECT COUNT(*) FROM disciplina
                        INNER JOIN ministra
                        ON ministra.FK_ID_disciplina=disciplina.ID
                            INNER JOIN periodo
                            ON ministra.FK_ID_periodo=periodo.ID
                                INNER JOIN professor
                                ON ministra.FK_CPF_professor=professor.CPF
                 WHERE periodo.ano_letivo=? AND professor.CPF=P.CPF)
                 """,
                new Object[]{anoLetivo},
                rs -> {
                    do list.add(new Professor(rs));
                    while (rs.next());
                }
        );
        return list;
    }

    public static void definirPreferencia(int cpf, int idDisciplina1, int idDisciplina2)  throws ClassNotFoundException, SQLException{
        DatabaseConnection.getInstance().connect(
                """
                 UPDATE professor
                    SET
                        FK_Disciplina_Preferencia_1=?,
                        FK_Disciplina_Preferencia_2=?
                 WHERE professor.CPF=?
                 """,
                new Object[]{
                        idDisciplina1,
                        idDisciplina2,
                        cpf
                },
                null
        );
    }
}
