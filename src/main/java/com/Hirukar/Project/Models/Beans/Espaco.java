package com.Hirukar.Project.Models.Beans;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Models.Enums.DiasDaSemana;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author RODEMARCK
 */
public class Espaco {
   private final int id;
   private Disciplina disciplina;
   private short numero;

   public Espaco(ResultSet rs) throws SQLException, ClassNotFoundException {
       this.id = rs.getShort("id");
       this.numero = rs.getShort("numero");
       this.disciplina = DisciplinasDAO.getDisciplina(rs.getInt("disciplina_id"));
   }
   /**
    * @return the id
    */
   public int getId() {
      return id;
   }

   /**
    * @return the numero
    */
   public short getNumero() {
      return numero;
   }

   /**
    * @return the disciplina
    */
   public Disciplina getDisciplina() {
      return disciplina;
   }

}
