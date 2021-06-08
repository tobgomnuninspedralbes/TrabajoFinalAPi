package api.models;

import java.util.List;

public class Reserva {
	int id;
	float preu;
	Usuario usuario;
	String estado;
	String horaRealizacion;
	String horaReserva;
	List<Item> llistaItems;
	
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
	public List<Item> getLlistaItems() {
		return llistaItems;
	}
	public void setLlistaItems(List<Item> llistaItems) {
		this.llistaItems = llistaItems;
	}
	public float getPreu() {
		return preu;
	}
	public void setPreu(float preu) {
		this.preu = preu;
	}
}
