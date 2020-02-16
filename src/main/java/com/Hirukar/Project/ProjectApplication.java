package com.Hirukar.Project;

import com.Hirukar.Project.Connection.ConnectionFactory.DatabaseConnection;
import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Models.Beans.Disciplina;
import com.Hirukar.Project.Models.constantes.Constantes;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) throws Exception {
		//SpringApplication.run(ProjectApplication.class, args);
		DatabaseConnection.getInstance().connect("SELECT get_disciplinas(15)",null,rs->{
			Gson g = new Gson();

			System.out.println(g.fromJson(Constantes.formatarJson(rs.getString(1)), Disciplina.class));
		});
	}

}
