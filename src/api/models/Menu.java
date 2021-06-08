package api.models;

public class Menu extends Item {
	public static final int MIDA_GRAN = 1;
	public static final int MIDA_PETIT = 2;
	String dia;
	int mida;
	float preu;
	Plato primerPlat;
	Plato segonPlat;
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public int getMida() {
		return mida;
	}
	public void setMida(int mida) {
		this.mida = mida;
	}
	public float getPreu() {
		return preu;
	}
	public void setPreu(float preu) {
		this.preu = preu;
	}
	public Plato getPrimerPlat() {
		return primerPlat;
	}
	public void setPrimerPlat(Plato primerPlat) {
		this.primerPlat = primerPlat;
	}
	public Plato getSegonPlat() {
		return segonPlat;
	}
	public void setSegonPlat(Plato segonPlat) {
		this.segonPlat = segonPlat;
	}
	public static int getMidaGran() {
		return MIDA_GRAN;
	}
	public static int getMidaPetit() {
		return MIDA_PETIT;
	}
}
