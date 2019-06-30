/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Controller;

import com.Hirukar.Project.Connection.DAO.ProfessorDAO;
import com.Hirukar.Project.Models.Beans.Enums.Cursos;
import com.Hirukar.Project.Models.Beans.Horarios__;
import com.Hirukar.Project.Models.Beans.Slots;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RODEMARCK
 */
@RestController
public class IndexController {
    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }


    /*@RequestMapping(value = "fazerLogin", method = RequestMethod.POST, produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ModelAndView formLogin(String login,String senha,Model model){
        try {
            UserDAO dao = new UserDAO();
            ResponseEntity<String> re = null;
            User c = dao.logar(login, senha);
            ModelAndView mv = new ModelAndView ("sobre");
            
            System.out.println("login:"+login+";  senha:"+senha);
            return  mv;
        } catch (SQLException ex) {
            model.addAttribute("loginError", true);
            return  new ModelAndView ("index");
        }
    }*/
    
    
    @RequestMapping(value = "fazerLogin", method = RequestMethod.GET, produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ModelAndView indexS() throws SQLException{
        return new ModelAndView ("index");
    }

    
    @RequestMapping(value = "logar", method = RequestMethod.POST, produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ModelAndView disciplinas(String request){    
         
        return new ModelAndView("logado");
    }
    
    
    @RequestMapping("index_testes")
    public String index_testes(){
        return "index_testes";
    }
}
