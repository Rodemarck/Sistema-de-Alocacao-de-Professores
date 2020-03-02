/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Controller;

import com.Hirukar.Project.Connection.DAO.AlocacaoDAO;
import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Connection.DAO.ProfessorDAO;
import com.Hirukar.Project.Models.Beans.Curso;
import com.Hirukar.Project.Models.Beans.Disciplina;
import com.Hirukar.Project.Models.Beans.Periodo;

import java.sql.SQLException;
import java.util.LinkedList;

import com.Hirukar.Project.Models.Enums.Area;
import com.Hirukar.Project.Models.Users_.Professor;
import com.Hirukar.Project.Models.constantes.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RODEMARCK
 */
@Controller
public class DisciplinaController {

    @RequestMapping("/disciplinas")
    public String disciplinas(){
        return "disciplinas";
    }

    @RequestMapping(value = "/atualizarSlots", method = RequestMethod.GET)
    public String atualizarSlots(int idPeriodo, ModelMap map) throws  SQLException, ClassNotFoundException {
        Periodo periodo = DisciplinasDAO.getPeriodo(idPeriodo);
        LinkedList<Periodo.SubPeriodo> subPeriodos = DisciplinasDAO.subPeriodos(periodo.getCurso().getId(),periodo.getAnoLetivo());
        LinkedList<Professor> profesores = ProfessorDAO.listar();
        LinkedList<Curso> cursos = DisciplinasDAO.listarCurso();

        LinkedList<Professor> profesoresARC = new LinkedList<>(profesores);
        LinkedList<Professor> profesoresFC = new LinkedList<>(profesores);
        LinkedList<Professor> profesoresENSISO = new LinkedList<>(profesores);

        profesoresARC.removeIf(t -> !t.getArea().equals(Area.ARC));
        profesoresFC.removeIf(t -> !t.getArea().equals(Area.FC));
        profesoresENSISO.removeIf(t -> !t.getArea().equals(Area.ENSISO));

        map.addAttribute("subPeriodos",subPeriodos);
        map.addAttribute("cursos",cursos);
        map.addAttribute("prof",profesores);
        map.addAttribute("prof_ARC",profesoresARC);
        map.addAttribute("prof_FC",profesoresFC);
        map.addAttribute("prof_ENSISO",profesoresENSISO);
        map.addAttribute("periodo", periodo);
        map.addAttribute("horarios", Constantes.horarios);
        map.addAttribute("diasDaSemana", Constantes.diasDaSemana);
        return "tabela";
    }

    @RequestMapping(value = "/mudaCurso")
    public String mudaCurso(int idCurso, int ano, int nPeriodo, ModelMap map) throws SQLException, ClassNotFoundException {
        int numero = 0;
        try{
            numero = DisciplinasDAO.getIdPeriodo(idCurso,ano,nPeriodo);
            return atualizarSlots(numero,map);
        }catch (SQLException|ClassNotFoundException e) {
            try{
                numero = DisciplinasDAO.getIdPeriodo(idCurso,ano,1);
                return atualizarSlots(numero,map);
            } catch (SQLException | ClassNotFoundException ex) {
                throw ex;
            }
        }
    }

    @RequestMapping(value = "/professor/alocar")
    public ResponseEntity<String> alocarProfessor(int idPeriodo, int idProfessor, int iDisciplina){
        try{
            AlocacaoDAO.aloca(idPeriodo,idProfessor,iDisciplina);
            return new ResponseEntity<>("",HttpStatus.OK);
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/trocarDiscplina" , method = RequestMethod.POST, produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> trocarDiscplina(int idPeriodo, int n1, int n2){
        try{
            DisciplinasDAO.trocaDisciplina(idPeriodo, n1,n2);
            return new ResponseEntity<>("OK",HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("",HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
;