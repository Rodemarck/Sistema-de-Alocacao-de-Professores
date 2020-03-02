/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Config;

import com.Hirukar.Project.Models.Enums.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author RODEMARCK
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    private static final String[] ARTEFATOS  =  {
            "/css/**",
            "/img/**",
            "/fonts/**",
            "/js/**",
            "/components/**",
            "/templates/**",
            "/resources/**"
    };

    @Autowired
    private Detalhes detalhes;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(detalhes).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(ARTEFATOS)
                    .permitAll()
                .antMatchers(
                        "/",
                        "/sobre",
                        "/disciplinas"
                )
                    .permitAll()
                        .antMatchers(
                        "/professor",
                        "/listaPreferencias"
                )
                    .hasAuthority(TipoUsuario.PROFESSOR.name())
                .antMatchers(
                        "/coordenador",
                        "regras"
                )
                    .hasAuthority(TipoUsuario.COORDENADOR.name())
                .antMatchers(
                        "/menuSupervisor"//,"/disciplinas"
                )
                    .hasAnyAuthority(TipoUsuario.SUPERVISOR.name())
                .antMatchers(
                        "/",
                        "/cadastroProfessor"
                )
                    .anonymous()
            .and()
            .formLogin()
                .loginPage("/")
                    .permitAll()
                        .successHandler(new LoginHandler())
                        .failureHandler(new LoginHandler())
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .invalidSessionUrl("/")
            .and()
            .logout()
                .permitAll()
            .and()
            .csrf()
                .disable();
    }
    


}
