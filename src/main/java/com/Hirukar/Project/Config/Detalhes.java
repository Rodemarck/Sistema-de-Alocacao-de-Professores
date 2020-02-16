package com.Hirukar.Project.Config;

import com.Hirukar.Project.Connection.DAO.ProfessorDAO;
import java.sql.SQLException;

import com.Hirukar.Project.Models.Users_.Professor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Detalhes implements UserDetailsService{
    
    
    @Override
    public UserDetails loadUserByUsername(String username){
        try{
            System.out.println("carregando : [" + username + "]");
            Professor u = ProfessorDAO.getPeloNome(username);
            System.out.println("e....");
            List<GrantedAuthority> grantedAuthority = AuthorityUtils.createAuthorityList(u.getCargo().name());
            return new org.springframework.security.core.userdetails.User(u.getLogin(),u.getSenha(),grantedAuthority);
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("login erro :"+e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
