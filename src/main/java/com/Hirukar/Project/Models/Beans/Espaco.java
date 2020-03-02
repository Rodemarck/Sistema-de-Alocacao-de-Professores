package com.Hirukar.Project.Models.Beans;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Models.Users_.Professor;
import com.Hirukar.Project.Models.constantes.Constantes;
import com.google.gson.reflect.TypeToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

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

   public Espaco(int id, Disciplina disciplina, short numero) {
      this.id = id;
      this.disciplina = disciplina;
      this.numero = numero;
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

   @Override
   public String toString() {
      return "Espaco{" +
              "id=" + id +
              ", disciplina=" + disciplina +
              ", numero=" + numero +
              '}';
   }

   public void setDisciplina(Disciplina disciplina) {
      this.disciplina = disciplina;
   }

   public void setNumero(short numero) {
      this.numero = numero;
   }

   public static Espaco get(String raw){
      return Constantes.gson.fromJson(Constantes.formatarJson(raw), Espaco.class);
   }

   public static LinkedList<Espaco> getList(String raw){
      return Constantes.gson.fromJson(Constantes.formatarJson(raw), new TypeToken<LinkedList<Espaco>>(){}.getType());

   }
}
