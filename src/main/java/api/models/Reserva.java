package main.java.api.models;

import java.util.List;

public class Reserva {
	public static final String BLOQUEJADA = "Bloquejada";
	int id;
	float preu;
	Usuario usuario;
	String estado;
	String horaRealizacion;
	String horaReserva;
	List<Producto> llistaProductos;
	List<Menu> llistaMenus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario Usuario) {
		this.usuario = Usuario;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getHoraRealizacion() {
		return horaRealizacion;
	}
	public void setHoraRealizacion(String horaRealizacion) {
		this.horaRealizacion = horaRealizacion;
	}
	public String getHoraReserva() {
		return horaReserva;
	}
	public void setHoraReserva(String horaReserva) {
		this.horaReserva = horaReserva;
	}
	public List<Producto> getLlistaProductos() {
		return llistaProductos;
	}
	public void setLlistaProductos(List<Producto> llistaProductos) {
		this.llistaProductos = llistaProductos;
	}
	public List<Menu> getLlistaMenus() {
		return llistaMenus;
	}
	public void setLlistaMenus(List<Menu> llistaMenus) {
		this.llistaMenus = llistaMenus;
	}
	public float getPreu() {
		return preu;
	}
	public void setPreu(float preu) {
		this.preu = preu;
	}
}
