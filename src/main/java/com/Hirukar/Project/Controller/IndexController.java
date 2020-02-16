/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Controller;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Models.Beans.Periodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RODEMARCK
 */
@Controller
public class IndexController implements ErrorController {
    @Autowired
    private HttpSession session;

    @RequestMapping("/")
    public String index(){
        if(session.getAttribute("conta") != null)
            return "redirect:/" + session.getAttribute("cargo");
        return "index";
    }

    @RequestMapping("/sobre")
    public String sobre(){
        return "sobre";
    }

    @RequestMapping("/coordenador")
    public String menuCoordenador(ModelMap map){
        map.addAttribute("nome", session.getAttribute("login"));
        return "menuCoordenador";
    }

    @RequestMapping("/professor")
    public String menuProfesso(){
        return "menuProfessor";
    }

    @RequestMapping("/supervisor")
    public String menuSupervisor(){
        return "menuSupervisor";
    }

    @RequestMapping("/regras")
    public String regras(){
        return "regras";
    }

    @RequestMapping("teste")
    public ModelAndView teste() throws ClassNotFoundException, SQLException {
        ModelAndView mv = new ModelAndView("Teste");
        Periodo p = DisciplinasDAO.getPeriodo(1, 1);
        mv.addObject("h", p);
        return mv;
    }

    @RequestMapping("/verificarAlocacoes")
    public String VerificarAlocacoes(){
        return "verificarAlocacoes";
    }

    @RequestMapping("/alocarProfessor")
    public String alocacaoProfessor(ModelMap map){
        map.addAttribute("professores", null);
        map.addAttribute("aulas", new ArrayList<>());
        return "alocarProfessor";
    }

    @RequestMapping("/preferencias")
    public String DefenirPreferencias(){
        return "preferencias";
    }

    @RequestMapping("/historicoAlocacao")
    public String HistoricoAlocacao(){
        return "historicoAlocacao";
    }

    @RequestMapping("/listaPreferencias")
    public String ListaPreferencias(){
        return "listaPreferencias";
    }

    @RequestMapping("/cadastroProfessor")
    public String cadastroProfessor(){
        return "cadastroProfessor";
    }

    @RequestMapping("/cadastroDisciplina")
    public String cadastroDisciplina() {
        return "cadastroDisciplina";
    }

    @RequestMapping("/deslogar")
    public String deslogar(){
        System.out.println("deslogando");
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/Error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/Error";
    }
}
