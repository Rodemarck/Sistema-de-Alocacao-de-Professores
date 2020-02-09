/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Controller;

import com.Hirukar.Project.Connection.DAO.DisciplinasDAO;
import com.Hirukar.Project.Models.Beans.Periodo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RODEMARCK
 */
@RestController
public class IndexController {
    @RequestMapping("/")
    public ModelAndView index(@AuthenticationPrincipal UserDetails userDetails){
       if(userDetails==null)
            return new ModelAndView("index");
       return switch (userDetails.getAuthorities().toArray()[0].toString()){
           case "PROFESSOR" -> new ModelAndView("redirect:/menuProfessor");
           case "COORDENADOR" -> new ModelAndView("redirect:/menuCoordenador");
           case "SUPERVISOR" -> new ModelAndView("redirect:/menuSupervisor");
           default -> new ModelAndView("index");
       };
    }

    @RequestMapping("/sobre")
    public String sobre(){
        return "sobre";
    }

    @RequestMapping("/menuCoordenador")
    public ModelAndView menuCoordenador(@AuthenticationPrincipal UserDetails userDetails){
        if(userDetails == null  || userDetails.getAuthorities().toArray()[0].toString().equals(""))
            return new ModelAndView("redirect:/");
        return new ModelAndView("menuCoordenador");
    }

    @RequestMapping("/menuProfessor")
    public ModelAndView menuProfesso(@AuthenticationPrincipal UserDetails userDetails){
        if(userDetails == null  || userDetails.getAuthorities().toArray()[0].toString().equals(""))
            return new ModelAndView("redirect:/");
        return new ModelAndView("menuProfessor");
    }

    @RequestMapping("/menuSupervisor")
    public ModelAndView menuSupervisor(){
        return new ModelAndView("menuSupervisor");
    }

    @RequestMapping("/regras")
    public ModelAndView regras(){
        return new ModelAndView("regras");
    }

    @RequestMapping("teste")
    public ModelAndView teste() throws ClassNotFoundException, SQLException {
        ModelAndView mv = new ModelAndView("Teste");
        Periodo p = DisciplinasDAO.getPeriodo(1, 1);
        mv.addObject("h", p);
        return mv;
    }

    @RequestMapping("/verificarAlocacoes")
    public ModelAndView VerificarAlocacoes(){
        return new ModelAndView("verificarAlocacoes");
    }

    @RequestMapping("/alocarProfessor")
    public ModelAndView alocacaoProfessor(){
        ModelAndView mv = new ModelAndView("alocarProfessor");
        mv.addObject("professores",null);
        mv.addObject("aulas", new ArrayList<>());
        return mv;
    }

    @RequestMapping("/preferencias")
    public ModelAndView DefenirPreferencias(@AuthenticationPrincipal UserDetails userDetails){
        if(userDetails == null  || userDetails.getAuthorities().toArray()[0].toString().equals(""))
            return new ModelAndView("redirect:/");
        return new ModelAndView("preferencias");
    }

    @RequestMapping("/historicoAlocacao")
    public ModelAndView HistoricoAlocacao(){
        return new ModelAndView("historicoAlocacao");
    }

    @RequestMapping("/listaPreferencias")
    public ModelAndView ListaPreferencias(){
        return new ModelAndView("listaPreferencias");
    }






    @RequestMapping("/cadastroProfessor")
    public String cadastroProfessor(){
        return "cadastroProfessor";
    }
    @RequestMapping("/cadastroDisciplina")
    public String cadastroDisciplina(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails == null  || userDetails.getAuthorities().toArray()[0].toString().equals(""))
            return "redirect:/";
        return "cadastroDisciplina";
    }

    @RequestMapping("/deslogar")
    public String deslogar(HttpSession session){
        System.out.println("deslogando");
        session.invalidate();
        return "redirect:/";
    }


}
