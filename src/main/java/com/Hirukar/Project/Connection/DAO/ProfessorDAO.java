package com.Hirukar.Project.Connection.DAO;

import com.Hirukar.Project.Connection.ConnectionFactory.DatabaseConnection;
import com.Hirukar.Project.Models.Users_.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class ProfessorDAO {
    
    public static Professor getPeloNome(String nome) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Professor prof = new Professor();
        try{
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.prepareStatement(
                    "SELECT * FROM professor " +
                    "WHERE professor.login=?"
            );
            stmt.setString(1, nome);
            rs = stmt.executeQuery();
            if(rs.next())
                prof = new Professor(rs);
        }catch(ClassNotFoundException | SQLException e){
            throw e;
        }finally{
            DatabaseConnection.getInstance().close(con, rs, stmt);
        }
        return prof;
    }
    
    public static ArrayList<Professor> listar() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Professor> profesores = new ArrayList<>();
        try {
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.prepareStatement(
                    "SELECT * FROM professor WHERE 1"
            );
            rs = stmt.executeQuery();
            while (rs.next()) {
                profesores.add(new Professor(rs));
            }
        }catch(ClassNotFoundException | SQLException e){
            throw e;
        }finally {
            DatabaseConnection.getInstance().close(con, rs, stmt);
        }
        return profesores;
    }
    
    public static void cadastrar(Professor professor) throws ClassNotFoundException, SQLException {
        Connection con  = null;
        PreparedStatement stmt = null;
        
        try{
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.prepareStatement(
                    "INSERT INTO professor\n" +
                    "( \n" +
                    " 	CPF, Nome, Area,login,senha,cargo\n" +
                    ")\n" +
                    "VALUES\n" +
                    "(\n" +
                    "	?,?,?,?,?,?\n" +
                    ")"
            );
            stmt.setString(1, professor.getCPF());
            stmt.setString(2, professor.getNome());
            stmt.setString(4, professor.getLogin());
            stmt.setString(5, professor.getSenha());
            stmt.setString(3, professor.getArea().name());
            stmt.setString(6, professor.getCargo().name());

            stmt.execute();
        }catch(ClassNotFoundException | SQLException e){
            throw e;
        }finally{
            DatabaseConnection.getInstance().close(con, stmt);
        }   
    }
    
    public static void atualiza(String login, Professor profNovo) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = null;
        Connection con  = null;
        
        try{
        con = DatabaseConnection.getInstance().getConnection();
        stmt = con.prepareStatement(
                "UPDATE professor" +
                "SET \n" +
                "	Nome = ?, \n" +
                "	CPF = ?, \n" +
                "	Area = ? \n," +
                "	FK_Disciplina_Preferencia_1 = ?, \n" +
                "	FK_Disciplina_Preferencia_2 = ?, \n" +
                "   login = ? \n"+
                "   senha = ? \n"+
                "WHERE 	professor.nome=?"
        );
        stmt.setString(1, profNovo.getNome());
        stmt.setString(2, profNovo.getCPF());
        stmt.setString(3, profNovo.getArea().name());
        stmt.setInt(4, profNovo.getPreferencia1().getID());
        stmt.setInt(5, profNovo.getPreferencia2().getID());
        stmt.setString(6, login);
        stmt.setString(7, profNovo.getSenha());
        stmt.execute();
        }catch(ClassNotFoundException | SQLException e){
            throw e;
        }finally{
            DatabaseConnection.getInstance().close(con, stmt);
        }
        
    }

    public static void deletar(Professor professor) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.getInstance().getConnection();
        PreparedStatement stmt = null;

        con = DatabaseConnection.getInstance().getConnection();
        stmt = con.prepareStatement(
                    "DELETE FROM professor \n" +
                         "WHERE professor.cpf=? "
        );
        stmt.execute();
        DatabaseConnection.getInstance().close(con, stmt);
    }
    
    
    public static Professor getPeloID(int professorID) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Professor prof = new Professor();
        try{
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.prepareStatement(
                    "SELECT * FROM professor " +
                    "WHERE professor.CPF=?"
            );
            stmt.setInt(1, professorID);
            rs = stmt.executeQuery();
            if(rs.next())
                prof = new Professor(rs);
        }catch(ClassNotFoundException | SQLException e){
            throw e;
            
        }finally{
            DatabaseConnection.getInstance().close(con, rs, stmt);
        }
        return prof;
    }
    
    public static ArrayList<Professor> listarProfessoresAptos(int anoLetivo) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Professor> profesores = new ArrayList<>();
        try {
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.prepareStatement(
                    "select * from professor as P\n" +
                    "where 1 and 3> (SELECT COUNT(*) FROM disciplina\n" +
                    "            INNER JOIN ministra\n" +
                    "            ON ministra.FK_ID_disciplina=disciplina.ID\n" +
                    "                INNER JOIN periodo\n" +
                    "                ON ministra.FK_ID_periodo=periodo.ID\n" +
                    "                    INNER JOIN professor\n" +
                    "                    ON ministra.FK_CPF_professor=professor.CPF\n" +
                    "        WHERE periodo.ano_letivo=? AND professor.CPF=P.CPF)"
            );
            stmt.setInt(1, anoLetivo);
            rs = stmt.executeQuery();
            while (rs.next()) {
                profesores.add(new Professor(rs));
            }
        }catch(ClassNotFoundException | SQLException e){
            throw e;
        }finally {
            DatabaseConnection.getInstance().close(con, rs, stmt);
        }
        return profesores;
    }

    public static void definirPreferencia(int cpf, int idDisciplina1, int idDisciplina2)  throws ClassNotFoundException, SQLException{
        Connection con = null;
        PreparedStatement stmt = null;
        
        try{
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.prepareStatement(
                    "UPDATE professor\n" +
                    "SET \n" +
                    "	FK_Disciplina_Preferencia_1=?\n" +
                            (idDisciplina2!=0 ? ",	FK_Disciplina_Preferencia_2=?\n" : "") +
                    "WHERE professor.CPF=?"
            );
            stmt.setInt(1, idDisciplina1);
            if(idDisciplina2!=0){
                stmt.setInt(2, idDisciplina2);
                stmt.setInt(3, cpf);
            }
            else
                stmt.setInt(2, cpf);
            stmt.execute();
        }catch(ClassNotFoundException | SQLException e){
            throw e;
        }finally {
            DatabaseConnection.getInstance().close(con, stmt);
        }
    }
}
