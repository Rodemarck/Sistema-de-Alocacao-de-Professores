/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hirukar.Project.Models.Beans;

import com.Hirukar.Project.Models.Enums.Area;
import com.Hirukar.Project.Models.Enums.TipoDisciplina;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author RODEMARCK
 */
public class Disciplina {
    private int id;
    private String nome;
    private String codigo;
    private TipoDisciplina tipo;
    private Area area;
    List<Disciplina> prerequisito;
    
    public boolean remover(int n){
        return this.area == Area.getArea(n);
    }
   


    public Disciplina(ResultSet rs) throws SQLException {
        this.nome = rs.getString("disciplina.Nome");
        this.codigo = rs.getString("disciplina.Codigo");
        this.tipo = TipoDisciplina.valueOf(rs.getString("disciplina.Tipo").toUpperCase());
        this.area = Area.valueOf(rs.getString("disciplina.Area").toUpperCase());
        this.id = rs.getInt("disciplina.ID");
    }
    
    

    public Disciplina(String nome, String codigo, TipoDisciplina tipo, Area area) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.tipo = tipo;
		this.area = area;
	}


	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public TipoDisciplina getTipo() {
        return tipo;
    }

    public void setTipo(TipoDisciplina tipo) {
        this.tipo = tipo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }


    public List<Disciplina> getPrerequisito() {
        return prerequisito;
    }

    public void setPrerequisito(List<Disciplina> prerequisito) {
        this.prerequisito = prerequisito;
    }

    public int getId() {
        return id;
    }
    
    public void serID(int ID){
        this.id = ID;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Disciplina other = (Disciplina) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Disciplina{" +
                "ID=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", tipo=" + tipo +
                ", area=" + area +
                ", preRequisito=" + prerequisito +
                '}';
    }
}
