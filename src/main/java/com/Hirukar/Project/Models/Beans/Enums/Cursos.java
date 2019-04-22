/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Models.Beans.Enums;

/**
 *
 * @author RODEMARCK
 */
public enum Cursos {
    BCC(0),LC(1),BSI(2);
    
    private final int value;
    public int getValue(){
        return value;
    }
    public static Cursos getCurso(int v) throws IllegalAccessException{
        switch(v){
            case 0: return Cursos.BCC;
            case 1: return Cursos.LC;
            case 2: return Cursos.BSI;
        }
        throw new IllegalAccessException("Curso não registrado!");
    }
    private Cursos(int n){
        value = n;
    }
}
