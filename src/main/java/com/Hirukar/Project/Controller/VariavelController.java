/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Controller;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Connection.DAO.ProfessorDAO;
import com.Hirukar.Project.Models.Beans.Disciplina;
import com.Hirukar.Project.Models.Beans.Ministra;
import com.Hirukar.Project.Models.Enums.Area;
import com.Hirukar.Project.Models.Enums.TipoDisciplina;
import com.Hirukar.Project.Models.Enums.TipoUsuario;
import com.Hirukar.Project.Models.Users_.Professor;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.Hirukar.Project.Models.constantes.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author RODEMARCK
 */
@RestController("/variavel")
public class VariavelController {
    @RequestMapping(value = "/variavel/professores", method = RequestMethod.GET)
    public List<Professor> getProfessores() throws ClassNotFoundException, SQLException {
        return ProfessorDAO.listar();
    }

    @RequestMapping(value = "/variavel/professores", method = RequestMethod.POST)
    public ResponseEntity<String> putProfessores(String cpf, String nome, Area area, String login, String senha,TipoUsuario tipo) throws ClassNotFoundException, SQLException {
       try {
            ProfessorDAO.cadastrar(cpf, nome, area.name(), login, Constantes.bCrypt.encode(senha), tipo.name());
            return new ResponseEntity<>("Registrado com sucesso", HttpStatus.OK);
        } catch (ClassNotFoundException | SQLException e) {
           System.out.println(e.getMessage());
           return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/variavel/professores", method = RequestMethod.PATCH)
    public void patchProfessores(String login, Professor professor) throws ClassNotFoundException, SQLException {
        // ProfessorDAO.atualiza(login, professor);
    }

    @RequestMapping(value = "/variavel/professores", method = RequestMethod.DELETE)
    public void deleteProfessores(Professor professor) {
        //
    }

    @RequestMapping(value = "/variavel/disciplinas", method = RequestMethod.GET)
    public LinkedList<Disciplina> getDisciplinas() throws ClassNotFoundException, SQLException {
        return DisciplinasDAO.listar();
    }
     
     
    @RequestMapping(value="/variavel/disciplinas", method = RequestMethod.POST)
    public ResponseEntity<String> putDisciplina(String disciplina, String codigo,String area,String tipo) throws ClassNotFoundException, SQLException{
        Disciplina d = new Disciplina(disciplina,codigo,TipoDisciplina.valueOf(tipo),Area.valueOf(area));
    	try{
            System.out.println(d);
            DisciplinasDAO.cadastrar(d);
            return new ResponseEntity<>("Registrado com sucesso",HttpStatus.OK);
        }catch(ClassNotFoundException | SQLException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
    
    @RequestMapping(value="/variavel/disciplinas", method = RequestMethod.PATCH)
    public void patchDisciplina(String nome,Disciplina disciplina) throws ClassNotFoundException, SQLException{
        DisciplinasDAO.atualiza(nome, disciplina);
    }
    
    @RequestMapping(value="/variavel/disciplinas", method = RequestMethod.DELETE)
    public void deleteDisciplina(Disciplina disciplina){
        //
    }
    
    
    
    
    
    
    
    
    
    
    
    @RequestMapping(value="/variavel/ministra", method = RequestMethod.GET)
    public List<Ministra> getAlocacoes() throws ClassNotFoundException, SQLException{
        return DisciplinasDAO.listarAlocacoes();
    }
     
     
    @RequestMapping(value="/variavel/ministra", method = RequestMethod.POST)
    public ResponseEntity<String> postAlocacoes(int idPeriodo,int idProfessor, int iDisciplina) throws ClassNotFoundException, SQLException{
    	try{
            DisciplinasDAO.aloca(idPeriodo,idProfessor,iDisciplina);
            return new ResponseEntity<>("Registrado com sucesso",HttpStatus.OK);
        }catch(ClassNotFoundException | SQLException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
    
    
    @RequestMapping(value="/variavel/ministra", method = RequestMethod.PATCH)
    public void patchAlocacoes(String nome,Disciplina disciplina) throws ClassNotFoundException, SQLException{
        DisciplinasDAO.atualiza(nome, disciplina);
    }
    
    @RequestMapping(value="/variavel/ministra", method = RequestMethod.DELETE)
    public void deleteAlocacoes(Disciplina disciplina){
        //
    }

    @RequestMapping(value="/variavel/preferencia", method = RequestMethod.POST)
    public ResponseEntity<String> definirPreferencia(int cpf, int idDisciplina1, int idDisciplina2){
        try{
            ProfessorDAO.definirPreferencia(cpf, idDisciplina1, idDisciplina2);
            return new ResponseEntity<>("Registrado com sucesso",HttpStatus.OK);
        }catch(ClassNotFoundException | SQLException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
    @RequestMapping(value="/variavel/conta", method = RequestMethod.GET)
    public Professor getConta(@AuthenticationPrincipal UserDetails userDetails) throws Exception{
        //return ProfessorDAO.getPeloNome(userDetails.getUsername());
        return null;
    }
}
