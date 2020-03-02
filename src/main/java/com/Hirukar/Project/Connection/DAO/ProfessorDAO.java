package com.Hirukar.Project.Connection.DAO;

import com.Hirukar.Project.Connection.ConnectionFactory.DatabaseConnection;
import com.Hirukar.Project.Models.Enums.Area;
import com.Hirukar.Project.Models.Enums.TipoUsuario;
import com.Hirukar.Project.Models.Users_.Professor;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

public final class ProfessorDAO {

    public static LinkedList<Professor> listar() throws ClassNotFoundException, SQLException {
        LinkedList<Professor> list = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                "SELECT list_professores()",
                null,
                rs -> list.addAll(Professor.getList(rs.getString(1)))
        );
        return list;
    }
    
    public static void cadastrar(String cpf, String nome, String area, String login, String senha, String cargo) throws ClassNotFoundException, SQLException {
        DatabaseConnection.getInstance().connect(
                """
                 INSERT INTO professor
                    (CPF, Nome, Area,login,senha,cargo) VALUES
                 (?,?,?,?,?,?)
                 """,
                new Object[]{
                        cpf,
                        nome,
                        area,
                        login,
                        senha,
                        cargo
                }
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

    public static Professor getPeloNome(String username) throws SQLException, ClassNotFoundException {
        LinkedList<Professor> list = new LinkedList<>();
        DatabaseConnection.getInstance().connect(
                "SELECT findByName_professor(?)",
                new Object[]{username},
                rs -> list.addAll(Professor.getList(rs.getString(1)))
        );
        return list.getFirst();
    }
}
