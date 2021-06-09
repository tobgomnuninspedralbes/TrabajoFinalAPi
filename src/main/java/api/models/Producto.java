package main.java.api.models;

import java.util.List;

public class Producto extends Item{
	String nom;
	float preu;
	String descripcion;
	int categoria;
	String foto;
	List<Complemento> complementos;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public float getPreu() {
		return preu;
	}
	public void setPreu(float preu) {
		this.preu = preu;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<Complemento> getComplementos() {
		return complementos;
	}
	public void setComplementos(List<Complemento> complementos) {
		this.complementos = complementos;
	}
}
