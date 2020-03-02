package com.Hirukar.Project;


import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class ProjectApplication {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SpringApplication.run(ProjectApplication.class, args);
		//System.out.println(DisciplinasDAO.getPeriodo(1));
	}
}
